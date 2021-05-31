package com.onet.onet.utils;

import java.lang.reflect.Array;
import com.onet.onet.GameConfiguration;
import android.graphics.Point;

import java.util.ArrayList;

public class MTMap {

    public static int XSTART = 0;
    public static int YSTART = 0;
    public static int column = 0;
    public static int[][] mt;
    public static int row = 0;

    public static void MTTest() {
        MTMap.mt = new int[][]{{-1, -1, -1, -1, -1, -1}, {-1, -1, -1, -1, -1, -1}, {-1, -1, -1, -1, -1, -1}, {-1, -1, -1, -1, -1, -1}, {-1, -1, -1, -1, -1, -1}, {-1, -1, -1, -1, -1, -1}, {-1, -1, -1, -1, -1, -1}};
    }

    public static void addIJ(final int n, final ArrayList<Point> list) {
        final int randomIndex = IUtil.getRandomIndex(0, -1 + list.size());
        final Point point = list.get(randomIndex);
        MTMap.mt[point.x][point.y] = n;
        list.remove(randomIndex);
    }

    /*public static void getTotalRowColumn(int startDollarX, int endDollarY, int hintX) {
     MTMap.XSTART = startDollarX + 2;
     int GameHeight = GameConfiguration.SCREENHIEGHT - endDollarY;
     int GameWidth = hintX - MTMap.XSTART;
     MTMap.row = GameHeight / GameConfiguration.ITEM_HEIGHT;
     MTMap.column = GameWidth / GameConfiguration.ITEM_WIDTH;
     if (MTMap.row * MTMap.column % 2 != 0) {
     MTMap.column -= 1;
     }
        
        
     //Optimized Screen Design Process Started!
         
     if (MTMap.row > 6 || MTMap.column > 15) {
     if (MTMap.row > 6) {
     MTMap.row = 6;
     }
     if (MTMap.column > 15) {
     MTMap.column = 15;
     }
     if (MTMap.row * MTMap.column % 2 != 0) {
     MTMap.column -= 1;
     }
     }

     //scaleDirection : true --> scale theo chieu WIDTH, ngang man hinh
     boolean scaleDirection = GameHeight * 1.0f / (GameConfiguration.ITEM_HEIGHT * MTMap.row * 1.0f) > GameWidth * 1.0f / (GameConfiguration.ITEM_WIDTH * MTMap.column * 1.0f);
     float scaleFactor = GameHeight * 1.0f / (GameConfiguration.ITEM_HEIGHT * MTMap.row * 1.0f) > GameWidth * 1.0f / (GameConfiguration.ITEM_WIDTH * MTMap.column * 1.0f)
     ? GameWidth * 1.0f / (GameConfiguration.ITEM_WIDTH * MTMap.column * 1.0f) : GameHeight * 1.0f / (GameConfiguration.ITEM_HEIGHT * MTMap.row * 1.0f);

     GameConfiguration.ITEM_HEIGHT = (int) (GameConfiguration.ITEM_HEIGHT * scaleFactor);
     GameConfiguration.ITEM_WIDTH = (int) (GameConfiguration.ITEM_WIDTH * scaleFactor);

     if (scaleDirection) {
     //scale theo chieu WIDTH --> HEIGHT, row bo ngo.
     MTMap.row = GameHeight / GameConfiguration.ITEM_HEIGHT;
     } else {
     //scale theo chieu HEIGHT --> WIDTH, column bo ngo.
     MTMap.column = GameWidth / GameConfiguration.ITEM_WIDTH;
     }
     if (MTMap.row * MTMap.column % 2 != 0) {
     MTMap.column -= 1;
     }

     scaleFactor = GameHeight * 1.0f / (GameConfiguration.ITEM_HEIGHT * MTMap.row * 1.0f) > GameWidth * 1.0f / (GameConfiguration.ITEM_WIDTH * MTMap.column * 1.0f)
     ? GameWidth * 1.0f / (GameConfiguration.ITEM_WIDTH * MTMap.column * 1.0f) : GameHeight * 1.0f / (GameConfiguration.ITEM_HEIGHT * MTMap.row * 1.0f);
     GameConfiguration.ITEM_HEIGHT = (int) (GameConfiguration.ITEM_HEIGHT * scaleFactor);
     GameConfiguration.ITEM_WIDTH = (int) (GameConfiguration.ITEM_WIDTH * scaleFactor);
        
     // Optimized Screen Design Process Ended!
         

     MTMap.YSTART = endDollarY + (GameHeight - MTMap.row * GameConfiguration.ITEM_HEIGHT) / 2 - 5;
     ILogger.LogInfo("row = " + MTMap.row);
     ILogger.LogInfo("column = " + MTMap.column);
     ILogger.LogInfo("YSTART = " + MTMap.YSTART);
     ILogger.LogInfo("XSTART = " + MTMap.XSTART);
     reRandomMT();
     }*/
    public static void getTotalRowColumn(int startDollarX, int endDollarY, int hintX) {
        MTMap.XSTART = startDollarX;
        int GameHeight = 0;

        if (GameConfiguration.isConnected) {
            //co internet --> co request banner --> design trong phan banner height!
            GameHeight = GameConfiguration.SCREENHIEGHT - endDollarY - GameConfiguration.AdBannerHeight;
        } else {
            //kohng co internet --> khong request banner --> full luon!
            GameHeight = GameConfiguration.SCREENHIEGHT - endDollarY;
        }

        int GameWidth = hintX - MTMap.XSTART - 5;
        MTMap.row = GameHeight / GameConfiguration.ITEM_HEIGHT;
        MTMap.column = GameWidth / GameConfiguration.ITEM_WIDTH;
        if (MTMap.row * MTMap.column % 2 != 0) {
            MTMap.column -= 1;
        }

        /*
         * Optimized Screen Design Process Started!
         */
        if (MTMap.row > 6 || MTMap.column > 15) {
         if (MTMap.row > 6) {
         MTMap.row = 6;
         }
         if (MTMap.column > 15) {
         MTMap.column = 15;
         }
         if (MTMap.row * MTMap.column % 2 != 0) {
         MTMap.column -= 1;
         }
         }
        /*if (MTMap.row > 2 || MTMap.column > 2) {
            if (MTMap.row > 2) {
                MTMap.row = 2;
            }
            if (MTMap.column > 2) {
                MTMap.column = 2;
            }
            if (MTMap.row * MTMap.column % 2 != 0) {
                MTMap.column -= 1;
            }
        }*/

        //scaleDirection : true --> scale theo chieu WIDTH, ngang man hinh
        boolean scaleDirection = GameHeight * 1.0f / (GameConfiguration.ITEM_HEIGHT * MTMap.row * 1.0f) > GameWidth * 1.0f / (GameConfiguration.ITEM_WIDTH * MTMap.column * 1.0f);
        float scaleFactor = GameHeight * 1.0f / (GameConfiguration.ITEM_HEIGHT * MTMap.row * 1.0f) > GameWidth * 1.0f / (GameConfiguration.ITEM_WIDTH * MTMap.column * 1.0f)
                ? GameWidth * 1.0f / (GameConfiguration.ITEM_WIDTH * MTMap.column * 1.0f) : GameHeight * 1.0f / (GameConfiguration.ITEM_HEIGHT * MTMap.row * 1.0f);

        GameConfiguration.ITEM_HEIGHT = (int) (GameConfiguration.ITEM_HEIGHT * scaleFactor);
        GameConfiguration.ITEM_WIDTH = (int) (GameConfiguration.ITEM_WIDTH * scaleFactor);

        if (scaleDirection) {
            //scale theo chieu WIDTH --> HEIGHT, row bo ngo.
            MTMap.row = GameHeight / GameConfiguration.ITEM_HEIGHT;
        } else {
            //scale theo chieu HEIGHT --> WIDTH, column bo ngo.
            MTMap.column = GameWidth / GameConfiguration.ITEM_WIDTH;
        }
        if (MTMap.row * MTMap.column % 2 != 0) {
            MTMap.column -= 1;
        }

        GameConfiguration.ITEM_HEIGHT = (int) (GameConfiguration.ITEM_HEIGHT * (GameHeight * 1.0f / (GameConfiguration.ITEM_HEIGHT * MTMap.row * 1.0f)));
        GameConfiguration.ITEM_WIDTH = (int) (GameConfiguration.ITEM_WIDTH * (GameWidth * 1.0f / (GameConfiguration.ITEM_WIDTH * MTMap.column * 1.0f)));

        /*
         * Optimized Screen Design Process Ended!
         */
        MTMap.YSTART = endDollarY + (GameHeight - MTMap.row * GameConfiguration.ITEM_HEIGHT) / 2 - 3;

        ILogger.LogInfo("row = " + MTMap.row);
        ILogger.LogInfo("column = " + MTMap.column);
        ILogger.LogInfo("YSTART = " + MTMap.YSTART);
        ILogger.LogInfo("XSTART = " + MTMap.XSTART);
        reRandomMT();
    }

    public static Point getXYByIJ(final int n, final int n2) {
        return new Point(MTMap.XSTART + n2 * GameConfiguration.ITEM_WIDTH, MTMap.YSTART + n * GameConfiguration.ITEM_HEIGHT);
    }

    public static void iniMaxItem(final ArrayList<Integer> list) {
        for (int i = 0; i < GameConfiguration.numberItemThemes[-1 + GameConfiguration.THEMES]; ++i) {
            list.add(i);
        }
    }

    public static void randomMT() {
        final ArrayList<Integer> list = new ArrayList<Integer>();
        iniMaxItem(list);
        final ArrayList<Point> list2 = new ArrayList<Point>();
        for (int i = 0; i < MTMap.row; ++i) {
            for (int j = 0; j < MTMap.column; ++j) {
                list2.add(new Point(i, j));
            }
        }
        for (int n = MTMap.row * MTMap.column / 2, k = 0; k < n; ++k) {
            final int randomIndex = IUtil.getRandomIndex(0, -1 + list.size());
            final int intValue = list.get(randomIndex);
            list.remove(randomIndex);
            addIJ(intValue, list2);
            addIJ(intValue, list2);
            if (list.size() == 0) {
                iniMaxItem(list);
            }
        }
        showMT();
    }

    public static void reRandomMT() {
        MTMap.mt = (int[][]) Array.newInstance(Integer.TYPE, MTMap.row, MTMap.column);
        ArrayList list = new ArrayList<Point>();
        do {
            randomMT();
            OnclickChecker.search(list);
        } while (list.size() != 2);
    }

    public static void showMT() {
        if (ILogger.islog) {
            ILogger.LogInfo("---------------------------");
            ILogger.LogInfo("------------MT-------------");
            for (int i = 0; i < MTMap.row; ++i) {
                for (int j = 0; j < MTMap.column; ++j) {
                    if (MTMap.mt[i][j] > 9) {
                        System.out.print(String.valueOf(MTMap.mt[i][j]) + " ");
                    } else {
                        System.out.print(String.valueOf(MTMap.mt[i][j]) + "  ");
                    }
                }
                System.out.println();
            }
            ILogger.LogInfo("---------------------------");
        }
    }
}
