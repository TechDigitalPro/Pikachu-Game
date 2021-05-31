/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onet.onet;

import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.content.Context;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IPreferences {

    private static IPreferences instance;
    private Context context;
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd";

    private IPreferences(final Context context) {
        super();
        this.context = context;
    }

    private SharedPreferences.Editor getEditor() {
        return this.getSharedPreferences().edit();
    }

    public static IPreferences getInstance(final Context context) {
        synchronized (IPreferences.class) {
            if (IPreferences.instance == null) {
                IPreferences.instance = new IPreferences(context);
            }
            return IPreferences.instance;
        }
    }

    private SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(this.context);
    }

    public boolean saveRateStatus(final boolean n) {
        final SharedPreferences.Editor editor = this.getEditor();
        editor.putBoolean("IRate", n);
        return editor.commit();
    }

    public boolean getRateStatus() {
        return this.getSharedPreferences().getBoolean("IRate", false);
    }


    public boolean savePolicyStatus(final boolean n) {
        final SharedPreferences.Editor editor = this.getEditor();
        editor.putBoolean("IPolicy", n);
        return editor.commit();
    }

    public boolean getPolicyStatus() {
        return this.getSharedPreferences().getBoolean("IPolicy", false);
    }

    public boolean saveIAdShownStatus(final boolean n) {
        final SharedPreferences.Editor editor = this.getEditor();
        editor.putBoolean("IAdShown", n);
        return editor.commit();
    }

    public boolean getIAdShownStatus() {
        return this.getSharedPreferences().getBoolean("IAdShown", false);
    }


    public boolean saveCurLevel(final int n) {
        final SharedPreferences.Editor editor = this.getEditor();
        editor.putInt("CurLevel", n);
        return editor.commit();
    }

    public int getCurLevel() {
        return this.getSharedPreferences().getInt("CurLevel", 1);
    }

    public boolean saveTotal_dollar(final int n) {
        final SharedPreferences.Editor editor = this.getEditor();
        editor.putInt("total_dollar", n);
        return editor.commit();
    }

    public int getTotal_dollar() {
        return this.getSharedPreferences().getInt("total_dollar", 0);
    }

    public boolean saveCurrent_dollar(final int n) {
        final SharedPreferences.Editor editor = this.getEditor();
        editor.putInt("dollar_current", n);
        return editor.commit();
    }

    public int getCurrent_dollar() {
        return this.getSharedPreferences().getInt("dollar_current", 0);
    }

    public boolean saveStar(final int n) {
        final SharedPreferences.Editor editor = this.getEditor();
        editor.putInt("Star", n);
        return editor.commit();
    }

    public int getStar() {
        return this.getSharedPreferences().getInt("Star", 0);
    }

    public Calendar getLastBonusDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
        String alarm = this.getSharedPreferences().getString("LastBonus", format.format(cal.getTime()));
        try {
            Date date = format.parse(alarm);
            cal.setTime(date);
        } catch (ParseException e) {
            return null;
        }
        return cal;
    }

    public boolean saveLastBonusDate() {
        Calendar cal = Calendar.getInstance();
        final SharedPreferences.Editor editor = this.getEditor();
        SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
        editor.putString("LastBonus", format.format(cal.getTime()));
        return editor.commit();
    }

    public String getEmail() {
        return this.getSharedPreferences().getString("Email", "zhang84@yandex.com");
    }

    public boolean saveEmail(final String email) {
        final SharedPreferences.Editor editor = this.getEditor();
        editor.putString("Email", email);
        return editor.commit();
    }

}
