package com.example.prateek.uberhack.util;

/**
 * Created by Prateek on 12/01/16.
 */
public class AppUtil {

    public static boolean getNullCheck(String str){
        if(str == null || str.length() == 0 || str.equalsIgnoreCase("null"))
            return false;
        return true;
    }
}
