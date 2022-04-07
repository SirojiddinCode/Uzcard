package com.company.utils;

public class CheckUtil {
    public static boolean checkPhone(String phone) {
        if (phone.startsWith("+998")) {
            char[] array = phone.toCharArray();
            for (int i = 1; i < array.length; i++) {
                if (!Character.isDigit(array[i])) {
                    return false;
                }
            }
            return true;
        }
        return false;

    }

    public static boolean checkPassportNumber(String passportNum) {
        String[] words = passportNum.split(" ");
        char[] arr = words[0].toCharArray();
        if (!(arr[0] >= 'A' && arr[0] <= 'Z' && arr[1] >= 'A' && arr[1] <= 'Z')) {
            return false;
        }
        if (words[1].length() == 7) {
            arr = words[1].toCharArray();
            for (char c : arr) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
            return true;
        } else return false;
    }

}
