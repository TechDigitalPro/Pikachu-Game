package com.onet.onet.utils;

public class LevelManager {

    public static int levelCurrent = 1;
    public static int totalLevel = Integer.MAX_VALUE;

    public static int getStarByLevel(final int n, final int n2) {
        int n3 = 0;
        final int n4 = getTimeLevel() / 5;
        if (n2 <= n4 * 2) {
            n3 = 3;
        } else if (n2 <= n4 * 3) {
            n3 = 2;
        } else if (n2 <= n4 * 4) {
            n3 = 1;
        }
        return n3;
    }

    public static int getTimeLevel() {
        if (LevelManager.levelCurrent <= 15) {
            return (int) (3 * (MTMap.row * MTMap.column) - 4 * (-1 + LevelManager.levelCurrent));
        }

        if (LevelManager.levelCurrent <= 20) {
            return (int) (0.95 * (3 * (MTMap.row * MTMap.column) - 4 * (-1 + 15)));
        }

        if (LevelManager.levelCurrent <= 40) {
            return (int) (0.90 * (3 * (MTMap.row * MTMap.column) - 4 * (-1 + 15)));
        }

        if (LevelManager.levelCurrent <= 80) {
            return (int) (0.85 * (3 * (MTMap.row * MTMap.column) - 4 * (-1 + 15)));
        }

        return (int) (0.80 * (3 * (MTMap.row * MTMap.column) - 4 * (-1 + 15)));
    }
}