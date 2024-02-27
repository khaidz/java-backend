package net.khaibq.javabackend.ultis;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.khaibq.javabackend.dto.PageDataDto;
import org.springframework.data.domain.Page;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonUtils {
    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return Pattern.matches(Constant.REGEX_EMAIL, email);
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) return false;
        return Pattern.matches(Constant.REGEX_PHONE_NUMBER, phoneNumber);
    }

    //
    public static <T> PageDataDto<T> convertPageData(Page<T> page) {
        PageDataDto<T> dto = new PageDataDto<>();
        dto.setPageSize(page.getSize());
        dto.setPageNumber(page.getNumber());
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalElements(page.getTotalElements());
        dto.setContent(page.getContent());
        return dto;
    }
}
