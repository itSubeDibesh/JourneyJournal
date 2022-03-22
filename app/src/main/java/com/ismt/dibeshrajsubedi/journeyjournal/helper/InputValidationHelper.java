package com.ismt.dibeshrajsubedi.journeyjournal.helper;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.helper was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 * Sourced from :- {@linkplain https://stackoverflow.com/questions/33072569/best-practice-input-validation-android StackOverflow}
 */
public class InputValidationHelper {

    /**
     * Checks if Email is Valid using Email REGEX
     * "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
     *
     * @param email String
     * @return boolean
     */
    public boolean isValidEmail(String email) {
        // Writing EMAIL REGEX
        final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        // Compiling Pattern
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        // Pattern Matching
        Matcher matcher = pattern.matcher(email.trim());
        // Extracting Result
        return matcher.matches();
    }

    /**
     * Checks if Password is Valid with alphanumeric and between 6-20 text length or also includes special characters
     *
     * @param password               String
     * @param allowSpecialCharacters boolean
     * @return boolean
     */
    public boolean isValidPassword(String password, boolean allowSpecialCharacters) {
        // Extracting Pattern According to #allowSpecialCharacters
        // PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})"; allowSpecialCharacters => true
        // PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})"; allowSpecialCharacters =>false
        String PATTERN = allowSpecialCharacters ? "^[a-zA-Z0-9@#$%]\\w{5,19}$" : "^[a-zA-Z0-9]\\w{5,19}$";
        // Compiling Pattern
        Pattern pattern = Pattern.compile(PATTERN);
        // Pattern Matching
        Matcher matcher = pattern.matcher(password.trim());
        // Extracting Result
        return matcher.matches();
    }

    /**
     * Checks if Input String is Null or Empty
     *
     * @param string String
     * @return boolean
     */
    public boolean isNullOrEmpty(String string) {
        return TextUtils.isEmpty(string.trim());
    }

    /**
     * Checks is Input string is only numbers
     *
     * @param string String
     * @return boolean
     */
    public boolean isNumeric(String string) {
        return TextUtils.isDigitsOnly(string.trim());
    }

    /**
     * Checks if String length is greater than parameter
     *
     * @param string      String
     * @param greaterThan int
     * @return boolean
     */
    public boolean strLenGreaterThan(String string, int greaterThan) {
        return string.trim().length() >= greaterThan;
    }

    /**
     * Checks if String length is less than parameter
     *
     * @param string   String
     * @param lessThan int
     * @return boolean
     */
    public boolean strLenLesserThan(String string, int lessThan) {
        return string.trim().length() <= lessThan;
    }

    /**
     * Checks if Strings are equal
     *
     * @param string1 String
     * @param string2 String
     * @return boolean
     */
    public boolean strIsMatch(String string1, String string2) {
        string1 = string1.trim();
        string2 = string2.trim();
        return string1.equals(string2);
    }
}
