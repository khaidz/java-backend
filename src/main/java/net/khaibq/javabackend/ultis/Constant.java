package net.khaibq.javabackend.ultis;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constant {
    public static final String REGEX_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String REGEX_PHONE_NUMBER = "^(84|0)[0-9]{9}$";
    public static final int SUCCESS_STATUS = 1;
    public static final int FAILED_STATUS = 0;
}
