package net.khaibq.javabackend.ultis;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.khaibq.javabackend.dto.PageDataDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
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

    public static String convertToSlug(String input) {
        if (input == null) return null;
        input = UnicodeUtils.removeAccent(input);
        String slug = input.toLowerCase().trim();
        slug = slug.replaceAll("[^a-z0-9\\s-]", "");
        slug = slug.replaceAll("\\s+", "-");
        slug = slug.replaceAll("-+", "-");
        slug = slug.replaceAll("^-+|-+$", "");
        return slug;
    }

    public static String getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1))
                .orElse("");
    }

    public static boolean isValidExtension(List<String> extensions, String input){
        if (extensions == null || input == null) return false;
        return extensions.contains(input.toLowerCase());
    }
}
