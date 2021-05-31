package com.onet.onet.utils;

public class IUtilFormat
{
    public static String getTime(final int n) {
        final int n2 = n / 3600;
        final int n3 = n % 3600;
        final int n4 = n3 / 60;
        final int n5 = n3 % 60;
        String s;
        if (String.valueOf(n2).length() < 2) {
            s = "0" + String.valueOf(n2);
        }
        else {
            s = String.valueOf(n2);
        }
        String s2;
        if (String.valueOf(n4).length() < 2) {
            s2 = "0" + String.valueOf(n4);
        }
        else {
            s2 = String.valueOf(n4);
        }
        String s3;
        if (String.valueOf(n5).length() < 2) {
            s3 = "0" + String.valueOf(n5);
        }
        else {
            s3 = String.valueOf(n5);
        }
        if (n2 == 0) {
            s3 = String.valueOf(s2) + ":" + s3;
        }
        else if (n4 != 0 || n2 != 0) {
            if (s.equals("00")) {
                s3 = String.valueOf(s2) + ":" + s3;
            }
            else {
                s3 = String.valueOf(s) + ":" + s2 + ":" + s3;
            }
        }
        return s3;
    }
}