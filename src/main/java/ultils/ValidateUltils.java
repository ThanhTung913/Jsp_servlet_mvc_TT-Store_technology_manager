package ultils;

import java.util.regex.Pattern;

public class ValidateUltils {

    public static final String PASSWORD_REGEX = "^([a-zA-Z0-9]{8,})";
    public static final String NAME_REGEX = "^([A-Z]+[a-z]*[ ]?)+$";
    public static final String PHONE_REGEX = "^[0][3|9][0-9]{8,9}$";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}$";
    public static final String NUMBER = "^[0-9]{1,10}$";
    public static final String EMPTY = "";

//    public static final String PASSWORD_REGEX = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
//    public static final String NAME_REGEX = "^([A-Z]+[a-z]*[ ]?)+$";
//    public static final String PHONE_REGEX = "^[0][1-9][0-9]{8,9}$";
//    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}$";
//    public static final String NAME_VNI_REGEX = "^([a-z]{1,10})((\\s{1}[a-z]{1,10}){1,4})$";
//    public static final String EMPTY_REGEX = "^([^. ][.]*[ ]?)+$";

    public static boolean isPasswordValid(String password) {
        return Pattern.compile(PASSWORD_REGEX).matcher(password).matches();
    }

    public static boolean isEmpty(String name) {
        return Pattern.compile(EMPTY).matcher(name).matches();
    }

    public static boolean isNumber(String number) {
        return Pattern.compile(NUMBER).matcher(number).matches();
    }


    public static boolean isNameValid(String name) {
        return Pattern.compile(NAME_REGEX).matcher(name).matches();
    }

    public static boolean isPhoneValid(String number) {
        return Pattern.compile(PHONE_REGEX).matcher(number).matches();
    }

    public static boolean isEmailValid(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }
}

