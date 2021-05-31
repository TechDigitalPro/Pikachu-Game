/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.onet.onet;

import android.content.Context;

public class IDialogUtils
{
    public static int dp2px(final Context context, final float n) {
        return (int)(0.5f + n * context.getResources().getDisplayMetrics().density);
    }
}