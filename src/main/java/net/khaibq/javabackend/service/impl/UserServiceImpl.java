package net.khaibq.javabackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.khaibq.javabackend.dto.auth.ChangePasswordRequestDto;
import net.khaibq.javabackend.dto.auth.ForgotPasswordRequestDto;
import net.khaibq.javabackend.dto.auth.RegisterRequestDto;
import net.khaibq.javabackend.dto.auth.RegisterResponseDto;
import net.khaibq.javabackend.dto.email.EmailDto;
import net.khaibq.javabackend.entity.User;
import net.khaibq.javabackend.exception.BaseException;
import net.khaibq.javabackend.repository.UserRepository;
import net.khaibq.javabackend.service.UserService;
import net.khaibq.javabackend.ultis.EmailUtils;
import net.khaibq.javabackend.ultis.EncryptUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailUtils emailUtils;
    private final ModelMapper modelMapper;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public RegisterResponseDto handleRegister(RegisterRequestDto dto) {
        if (userRepository.findByUsernameIgnoreCase(dto.getUsername()) != null) {
            throw new BaseException("Tên đăng nhập đã được sử dụng");
        }

        if (userRepository.findByEmailIgnoreCase(dto.getEmail()) != null) {
            throw new BaseException("Email đã được sử dụng");
        }

        User user = new User();
        user.setUsername(dto.getUsername().toLowerCase());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail().toLowerCase());
        user.setGender(dto.getGender() == null ? 0 : dto.getGender());
        user.setLevel(0);
        userRepository.save(user);

        return modelMapper.map(user, RegisterResponseDto.class);
    }

    @Override
    public void handleForgotPassword(ForgotPasswordRequestDto dto) {
        User user = userRepository.findByEmailIgnoreCase(dto.getEmail());
        if (user == null) {
            throw new BaseException("Email chưa được đăng ký");
        }

        String key = RandomStringUtils.randomAlphabetic(20);
        redisTemplate.opsForValue().set(key, EncryptUtils.encryptAES256(user.getEmail()), 30, TimeUnit.MINUTES);

        EmailDto emailDto = new EmailDto();
        emailDto.setEmailTo(new String[]{dto.getEmail()});
        emailDto.setSubject("Lấy lại mật khẩu");
        emailDto.setContent("""
                Xin chào %s,
                Chúng tôi nhận được yêu cầu đặt lại mật khẩu của bạn
                Vui lòng click vào link bên dưới để thực hiện thay đổi mật khẩu:
                http://localhost:8080/forgot-password?key=%s
                """.formatted(user.getUsername(), key));
        emailUtils.sendEmail(emailDto);
    }

    @Override
    public String validateKey(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new BaseException("Key không hợp lệ");
        }
        String encryptValue;
        try {
            encryptValue = redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            throw new BaseException("Key không hợp lệ");
        }

        if (encryptValue == null) {
            throw new BaseException("Key không hợp lệ hoặc hết hạn");
        }
        String email = EncryptUtils.decryptAES256(encryptValue);
        User user = userRepository.findByEmailIgnoreCase(email);
        if (user == null || Objects.equals(user.getIsDeleted(), 1)) {
            throw new BaseException("Không tìm thấy người dùng");
        }
        return key;

    }

    @Override
    public void handleChangePassword(ChangePasswordRequestDto dto) {
        String encryptValue;
        try {
            encryptValue = redisTemplate.opsForValue().get(dto.getKey());
        } catch (Exception e) {
            throw new BaseException("Yêu cầu không hợp lệ");
        }
        if (encryptValue == null) {
            throw new BaseException("Key không hợp lệ hoặc hết hạn");
        }
        String email = EncryptUtils.decryptAES256(encryptValue);
        User user = userRepository.findByEmailIgnoreCase(email);
        if (user == null || Objects.equals(user.getIsDeleted(), 1)) {
            throw new BaseException("Không tìm thấy người dùng");
        }
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
        redisTemplate.opsForValue().setIfPresent(dto.getKey(), "", 1, TimeUnit.SECONDS);
    }
}
