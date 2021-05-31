package com.onet.onet.utils;

import android.app.Dialog;

public class IUtilDialog
{
    public static void iniDialog(final Dialog dialog) {
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawableResource(2130837623);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = 2131492958;
    }
}