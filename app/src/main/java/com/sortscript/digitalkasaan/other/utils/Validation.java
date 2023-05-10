package com.sortscript.digitalkasaan.other.utils;


public class Validation {

    public static boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}