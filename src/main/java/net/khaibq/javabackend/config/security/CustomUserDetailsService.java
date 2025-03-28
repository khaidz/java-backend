package net.khaibq.javabackend.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.khaibq.javabackend.entity.User;
import net.khaibq.javabackend.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component("userDetailsService")
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameIgnoreCase(username);
        if (user != null) {
            List<SimpleGrantedAuthority> grantedAuthorityList = List.of(new SimpleGrantedAuthority("ROLE_USER"));

            return new CustomUser(user.getUsername(), user.getPassword(), !Objects.equals(user.getIsDeleted(), 1),
                    true, true, true, grantedAuthorityList, user.getId(), user.getEmail(), user.getLevel());
        }
        throw new UsernameNotFoundException("Người dùng không tồn tại");
    }
}
