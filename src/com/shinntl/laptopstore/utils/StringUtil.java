/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.utils;

import java.util.regex.Pattern;

/**
 *
 * @author shinn
 */
public class StringUtil {

    private static String emailRegex = "^[A-Za-z]+[A-Za-z0-9_]*@[A-Za-z0-9.-]+$";
    private static String userNameRegex = "^[a-zA-Z_][a-zA-Z0-9_]{2,19}$";
    private static String passwordRegex = "^[a-zA-Z_][a-zA-Z0-9_]{2,19}$";
    private static StringUtil stringUtil;

    private StringUtil() {

    }

    public static StringUtil newInstance() {
        if (stringUtil == null) {
            stringUtil = new StringUtil();
        }
        return stringUtil;
    }

    public boolean isValidEmail(String str) {
        return Pattern.compile(emailRegex).matcher(str).matches();
    }

    public boolean isValidPasswod(String str) {
        return Pattern.compile(passwordRegex).matcher(str).matches();
    }

    public boolean isValidUserName(String str) {
        return Pattern.compile(userNameRegex).matcher(str).matches();
    }
}
