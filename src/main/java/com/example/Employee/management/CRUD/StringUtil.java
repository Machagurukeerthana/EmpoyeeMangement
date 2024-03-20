package com.example.Employee.management.CRUD;

public class StringUtil {

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0 || s.equals("null") || s.equals("undefined");
    }

    public static boolean isNullObject(String s) {
        return s == null || s.length() == 0 || s.equals("null") || s.equals("undefined");
    }
}
