package com.onet.onet;

import android.app.Activity;
import org.anddev.andengine.engine.options.EngineOptions;

public class GameConfiguration {    //tru: connect to internet

    public static boolean isConnected = true;
    public static int AdBannerHeight = 50;
    public static int AdBannerWidth = 320;

    public static final int LevelResultCODE_DEFAULT = 0;
    public static final int LevelResultCODE_COMPLETE = 1;
    public static final int LevelResultCODE_FAIL = 2;
    public static final int LevelResultCODE_WINNER = 3;

    public static int ITEM_HEIGHT = 70;
    public static int ITEM_WIDTH = 60;
    public static float RACE_HEIGHT = 1.0f;
    public static float RACE_WIDTH = 1.0f;
    public static int SCREENHIEGHT = 800;
    public static int SCREENWIDTH = 480;
    public static EngineOptions.ScreenOrientation ScreenOrientation_Default = EngineOptions.ScreenOrientation.LANDSCAPE;
    public static int THEMES = 1;
    public static int[] numberItemThemes = new int[]{30};
    public static String pathTHEME = "items/";// + GameConfiguration.THEMES + "/";

    public static int getCenterX() {
        return GameConfiguration.SCREENWIDTH / 2;
    }

    public static int getCenterY() {
        return GameConfiguration.SCREENHIEGHT / 2;
    }

    public static void getDisplayScreen(final Activity activity) {
        GameConfiguration.SCREENWIDTH = activity.getWindowManager().getDefaultDisplay().getWidth();
        GameConfiguration.SCREENHIEGHT = activity.getWindowManager().getDefaultDisplay().getHeight();
        GameConfiguration.RACE_WIDTH = GameConfiguration.SCREENWIDTH / 800.0f;
        GameConfiguration.RACE_HEIGHT = GameConfiguration.SCREENHIEGHT / 480.0f;
        if (GameConfiguration.SCREENHIEGHT >= 320 && GameConfiguration.SCREENHIEGHT < 480) {
            GameConfiguration.ITEM_WIDTH = 40;
            GameConfiguration.ITEM_HEIGHT = 47;
        } else if (GameConfiguration.SCREENHIEGHT < 320) {
            GameConfiguration.ITEM_WIDTH = 29;
            GameConfiguration.ITEM_HEIGHT = 34;
        }
    }

    public static float getRaceHeight() {
        return GameConfiguration.RACE_HEIGHT;
    }

    public static float getRaceWidth() {
        return GameConfiguration.RACE_WIDTH;
    }
}
