package com.onet.onet.utils;

import android.graphics.Point;
import java.util.ArrayList;
import com.onet.onet.GameMainActivity;

public class OnclickChecker {

    public static boolean isOnClickItem = true;
    public static ItemPikachuSprite item1 = null;
    public static ItemPikachuSprite item2 = null;

    public static void activeSearhHint() {
        while (!searchHint() && GameMainActivity.mPlay.mManagerItemPikachu.list_itemPikachu.size() != 0) {
            GameMainActivity.mPlay.swapItem();
            ILogger.LogInfo("----------swapItem----------");
            MTMap.showMT();
            GameMainActivity.mSound.playRandom();
        }
        OnclickChecker.isOnClickItem = true;
    }

    public static void addIJToListPoint(final int n, final int n2, final ArrayList<Point> list) {
        final Point point = new Point(n, n2);
        if (!list.contains(point)) {
            list.add(point);
        }
    }

    public static boolean checkCondition(final int n, final int n2, final int n3, final int n4, final ArrayList<Point> list) {
        boolean b = true;
        ILogger.LogInfo("----------checkCondition----------");
        if (MTMap.mt[n][n2] == -1 || MTMap.mt[n3][n4] == -1) {
            b = false;
        } else {
            list.clear();
            if (checkLine(n, n2, n3, n4, list)) {
                addIJToListPoint(n, n2, list);
                addIJToListPoint(n3, n4, list);
                ILogger.LogInfo("checkLine = true");
            } else {
                ILogger.LogInfo("checkLine = false");
                list.clear();
                if (checkL(n, n2, n3, n4, list)) {
                    ILogger.LogInfo("checkL = true");
                } else {
                    ILogger.LogInfo("checkL = false");
                    list.clear();
                    if (checkU(n, n2, n3, n4, b, list)) {
                        ILogger.LogInfo("checkU = true");
                    } else {
                        ILogger.LogInfo("checkU = false");
                        list.clear();
                        if (checkUL(n, n2, n3, n4, list)) {
                            if (list.size() < 5) {
                                ILogger.LogInfo("checkUL = true");
                                return b;
                            }
                        } else {
                            ILogger.LogInfo("checkUL = false");
                        }
                        list.clear();
                        if (checkZ(n, n2, n3, n4, list)) {
                            ILogger.LogInfo("checkZ = true");
                        } else {
                            ILogger.LogInfo("checkZ = false");
                            ILogger.LogInfo("---------------------------");
                            b = false;
                        }
                    }
                }
            }
        }
        return b;
    }

    public static void checkEat() {
        OnclickChecker.isOnClickItem = false;
        if (!(OnclickChecker.item1.getGT_ITEM() != OnclickChecker.item2.getGT_ITEM() || (OnclickChecker.item1.getI() == OnclickChecker.item2.getI() && OnclickChecker.item1.getJ() == OnclickChecker.item2.getJ()))) {
            final int i = OnclickChecker.item1.getI();
            final int j = OnclickChecker.item1.getJ();
            final int k = OnclickChecker.item2.getI();
            final int l = OnclickChecker.item2.getJ();
            final ArrayList<Point> list = new ArrayList<Point>();
            if (checkCondition(i, j, k, l, list)) {
                ILogger.LogInfo("line_point.size() = " + list.size());
                if (list.size() < 5) {
                    GameMainActivity.mSound.playGood();
                    GameMainActivity.mPlay.mDollar.addDollar(10);
                    GameMainActivity.mPlay.mHint.setVisiable(false);
                    GameMainActivity.mPlay.mDrawLine.addLine(i, j, k, l, list);
                    GameMainActivity.mPlay.removeItem(OnclickChecker.item1.I, OnclickChecker.item1.J);
                    GameMainActivity.mPlay.removeItem(OnclickChecker.item2.I, OnclickChecker.item2.J);
                    MTMap.mt[OnclickChecker.item1.I][OnclickChecker.item1.J] = -1;
                    MTMap.mt[OnclickChecker.item2.I][OnclickChecker.item2.J] = -1;
                    OnclickChecker.item1.removeItem();
                    OnclickChecker.item2.removeItem();
                    OnclickChecker.item1 = null;
                    OnclickChecker.item2 = null;
                    MTMap.showMT();
                    if (GameMainActivity.mPlay.mManagerItemPikachu.list_itemPikachu.size() == 0) {
                        GameMainActivity.mPlay.GameOver = true;
                        GameMainActivity.mPlay.showDialogCompleted();
                    } else {
                        GameMainActivity.mPlay.mManagerItemPikachu.moveItem(i, j, k, l);
                        MTMap.showMT();
                    }
                    return;
                }
            }
        }
        resetItem();
        OnclickChecker.isOnClickItem = true;
        GameMainActivity.mSound.playBad();
    }

    public static boolean checkL(final int n, final int n2, final int n3, final int n4, final ArrayList<Point> list) {
        boolean b = true;
        final Point point = new Point();
        final Point point2 = new Point();
        if (n > n3 && n2 < n4) {
            point.x = n3;
            point.y = n2;
            point2.x = n;
            point2.y = n4;
        } else if (n < n3 && n2 < n4) {
            point.x = n;
            point.y = n4;
            point2.x = n3;
            point2.y = n2;
        } else if (n < n3 && n2 > n4) {
            point.x = n3;
            point.y = n2;
            point2.x = n;
            point2.y = n4;
        } else if (n > n3 && n2 > n4) {
            point.x = n3;
            point.y = n2;
            point2.x = n;
            point2.y = n4;
        }
        if (MTMap.mt[point.x][point.y] == -1 && checkLine(n, n2, point.x, point.y, list) && checkLine(point.x, point.y, n3, n4, list)) {
            addIJToListPoint(n, n2, list);
            addIJToListPoint(n3, n4, list);
            addIJToListPoint(point.x, point.y, list);
        } else if (MTMap.mt[point2.x][point2.y] == -1 && checkLine(n, n2, point2.x, point2.y, list) && checkLine(point2.x, point2.y, n3, n4, list)) {
            addIJToListPoint(n, n2, list);
            addIJToListPoint(n3, n4, list);
            addIJToListPoint(point2.x, point2.y, list);
        } else {
            b = false;
        }
        return b;
    }

    public static boolean checkLine(final int n, final int n2, final int n3, final int n4, final ArrayList<Point> list) {
        boolean b = false;
        if (n == n3) {
            int n5 = -1;
            int n6 = -1;
            if (n2 < n4) {
                n5 = n2 + 1;
                n6 = n4;
            }
            if (n2 > n4) {
                n5 = n4 + 1;
                n6 = n2;
            }
            for (int i = n5; i < n6; ++i) {
                if (MTMap.mt[n][i] != -1) {
                    return b;
                }
            }
            b = true;
        } else if (n2 == n4) {
            int n7 = -1;
            int n8 = -1;
            if (n < n3) {
                n7 = n + 1;
                n8 = n3;
            }
            if (n > n3) {
                n7 = n3 + 1;
                n8 = n;
            }
            for (int j = n7; j < n8; ++j) {
                if (MTMap.mt[j][n2] != -1) {
                    return b;
                }
            }
            b = true;
        }
        return b;
    }

    public static boolean checkU(final int n, final int n2, final int n3, final int n4, final boolean b, final ArrayList<Point> list) {
        boolean b2 = true;
        Label_0328:
        {
            if (b) {
                if (n == n3 && n == 0) {
                    addIJToListPoint(n, n2, list);
                    addIJToListPoint(n3, n4, list);
                    addIJToListPoint(n - 1, n2, list);
                    addIJToListPoint(n3 - 1, n4, list);
                } else if (n == n3 && n == -1 + MTMap.row) {
                    addIJToListPoint(n, n2, list);
                    addIJToListPoint(n3, n4, list);
                    addIJToListPoint(n + 1, n2, list);
                    addIJToListPoint(n3 + 1, n4, list);
                } else if (n2 == n4 && n2 == 0) {
                    addIJToListPoint(n, n2, list);
                    addIJToListPoint(n3, n4, list);
                    addIJToListPoint(n, n2 - 1, list);
                    addIJToListPoint(n3, n4 - 1, list);
                } else {
                    if (n2 != n4 || n2 != -1 + MTMap.column) {
                        break Label_0328;
                    }
                    addIJToListPoint(n, n2, list);
                    addIJToListPoint(n3, n4, list);
                    addIJToListPoint(n, n2 + 1, list);
                    addIJToListPoint(n3, n4 + 1, list);
                }
            } else if (n == n3 && n == 0) {
                addIJToListPoint(n - 1, n2, list);
                addIJToListPoint(n3 - 1, n4, list);
            } else if (n == n3 && n == -1 + MTMap.row) {
                addIJToListPoint(n + 1, n2, list);
                addIJToListPoint(n3 + 1, n4, list);
            } else if (n2 == n4 && n2 == 0) {
                addIJToListPoint(n, n2 - 1, list);
                addIJToListPoint(n3, n4 - 1, list);
            } else {
                if (n2 != n4 || n2 != -1 + MTMap.column) {
                    break Label_0328;
                }
                addIJToListPoint(n, n2 + 1, list);
                addIJToListPoint(n3, n4 + 1, list);
            }
            return b2;
        }
        if (n == n3) {
            ILogger.LogInfo("checkU i1 == i2");
            for (int n5 = n - 1; n5 > -1 && MTMap.mt[n5][n2] == -1 && MTMap.mt[n5][n4] == -1; --n5) {
                if (checkLine(n5, n2, n5, n4, list) || n5 == 0) {
                    if (b) {
                        addIJToListPoint(n, n2, list);
                        addIJToListPoint(n3, n4, list);
                    }
                    if (n5 == 0) {
                        --n5;
                    }
                    addIJToListPoint(n5, n2, list);
                    addIJToListPoint(n5, n4, list);
                    return b2;
                }
            }
            for (int i = n + 1; i < MTMap.row; ++i) {
                ILogger.LogInfo("MT.mt[i][j1] = " + MTMap.mt[i][n2]);
                ILogger.LogInfo("MT.mt[i][j2] = " + MTMap.mt[i][n4]);
                if (MTMap.mt[i][n2] != -1 || MTMap.mt[i][n4] != -1) {
                    break;
                }
                if (checkLine(i, n2, i, n4, list) || i == -1 + MTMap.row) {
                    if (b) {
                        addIJToListPoint(n, n2, list);
                        addIJToListPoint(n3, n4, list);
                    }
                    if (i == -1 + MTMap.row) {
                        ++i;
                    }
                    addIJToListPoint(i, n2, list);
                    addIJToListPoint(i, n4, list);
                    return b2;
                }
            }
        } else if (n2 == n4) {
            ILogger.LogInfo("checkU j1 == j2");
            for (int n6 = n2 - 1; n6 > -1 && MTMap.mt[n][n6] == -1 && MTMap.mt[n3][n6] == -1; --n6) {
                if (checkLine(n, n6, n3, n6, list) || n6 == 0) {
                    if (b) {
                        addIJToListPoint(n, n2, list);
                        addIJToListPoint(n3, n4, list);
                    }
                    if (n6 == 0) {
                        --n6;
                    }
                    addIJToListPoint(n, n6, list);
                    addIJToListPoint(n3, n6, list);
                    return b2;
                }
            }
            for (int n7 = n2 + 1; n7 < MTMap.column && MTMap.mt[n][n7] == -1 && MTMap.mt[n3][n7] == -1; ++n7) {
                if (checkLine(n, n7, n3, n7, list) || n7 == -1 + MTMap.column) {
                    if (b) {
                        addIJToListPoint(n, n2, list);
                        addIJToListPoint(n3, n4, list);
                    }
                    if (n7 == -1 + MTMap.column) {
                        ++n7;
                    }
                    addIJToListPoint(n, n7, list);
                    addIJToListPoint(n3, n7, list);
                    return b2;
                }
            }
        }
        b2 = false;
        return b2;
    }

    public static boolean checkUL(final int n, final int n2, final int n3, final int n4, final ArrayList<Point> list) {
        boolean b = false;
        final Point point = new Point();
        final Point point2 = new Point();
        if (n > n3 && n2 < n4) {
            point.x = n3;
            point.y = n2;
            point2.x = n;
            point2.y = n4;
        } else if (n < n3 && n2 < n4) {
            point.x = n;
            point.y = n4;
            point2.x = n3;
            point2.y = n2;
        } else if (n < n3 && n2 > n4) {
            point.x = n3;
            point.y = n2;
            point2.x = n;
            point2.y = n4;
        } else if (n > n3 && n2 > n4) {
            point.x = n3;
            point.y = n2;
            point2.x = n;
            point2.y = n4;
        }
        Label_0313:
        {
            if (MTMap.mt[point.x][point.y] != -1) {
                break Label_0313;
            }
            if (checkU(point.x, point.y, n3, n4, false, list) && checkLine(n, n2, point.x, point.y, list)) {
                addIJToListPoint(n, n2, list);
                addIJToListPoint(n3, n4, list);
                b = true;
            } else {
                list.clear();
                if (!checkU(point.x, point.y, n, n2, false, list) || !checkLine(n3, n4, point.x, point.y, list)) {
                    break Label_0313;
                }
                addIJToListPoint(n, n2, list);
                addIJToListPoint(n3, n4, list);
                b = true;
            }
            return b;
        }
        if (MTMap.mt[point2.x][point2.y] != -1) {
            return b;
        }
        list.clear();
        if (checkU(point2.x, point2.y, n3, n4, false, list) && checkLine(n, n2, point2.x, point2.y, list)) {
            addIJToListPoint(n, n2, list);
            addIJToListPoint(n3, n4, list);
            b = true;
            return b;
        }
        list.clear();
        if (checkU(point2.x, point2.y, n, n2, false, list) && checkLine(n3, n4, point2.x, point2.y, list)) {
            addIJToListPoint(n, n2, list);
            addIJToListPoint(n3, n4, list);
            b = true;
            return b;
        }
        return b;
    }

    public static boolean checkZ(final int n, final int n2, final int n3, final int n4, final ArrayList<Point> list) {
        boolean b = true;
        addIJToListPoint(n, n2, list);
        addIJToListPoint(n3, n4, list);
        if (n < n3) {
            for (int n5 = n + 1; n5 < n3 && MTMap.mt[n5][n2] == -1; ++n5) {
                if (MTMap.mt[n5][n4] == -1 && checkLine(n5, n4, n3, n4, list) && checkLine(n5, n4, n5, n2, list)) {
                    addIJToListPoint(n5, n4, list);
                    addIJToListPoint(n5, n2, list);
                    return b;
                }
            }
        }
        if (n > n3) {
            for (int n6 = n3 + 1; n6 < n && MTMap.mt[n6][n4] == -1; ++n6) {
                if (MTMap.mt[n6][n2] == -1 && checkLine(n6, n2, n, n2, list) && checkLine(n6, n4, n6, n2, list)) {
                    addIJToListPoint(n6, n4, list);
                    addIJToListPoint(n6, n2, list);
                    return b;
                }
            }
        }
        if (n2 < n4) {
            for (int n7 = n2 + 1; n7 < n4 && MTMap.mt[n][n7] == -1; ++n7) {
                if (MTMap.mt[n3][n7] == -1 && checkLine(n3, n7, n3, n4, list) && checkLine(n, n7, n3, n7, list)) {
                    addIJToListPoint(n3, n7, list);
                    addIJToListPoint(n, n7, list);
                    return b;
                }
            }
        }
        if (n2 > n4) {
            for (int n8 = n4 + 1; n8 < n2 && MTMap.mt[n3][n8] == -1; ++n8) {
                if (MTMap.mt[n][n8] == -1 && checkLine(n, n8, n, n2, list) && checkLine(n, n8, n3, n8, list)) {
                    addIJToListPoint(n3, n8, list);
                    addIJToListPoint(n, n8, list);
                    return b;
                }
            }
        }
        b = false;
        return b;
    }

    public static void click(final ItemPikachuSprite onetItemPikachu) {
        if (OnclickChecker.item1 == null) {
            OnclickChecker.item1 = onetItemPikachu;
        } else {
            OnclickChecker.item2 = onetItemPikachu;
            checkEat();
        }
    }

    public static void resetItem() {
        if (OnclickChecker.item1 != null) {
            OnclickChecker.item1.setScale(1.0f);
            OnclickChecker.item1.setRotation(0);
        }
        if (OnclickChecker.item2 != null) {
            OnclickChecker.item2.setScale(1.0f);
            OnclickChecker.item2.setRotation(0);
        }
        OnclickChecker.item1 = null;
        OnclickChecker.item2 = null;
    }

    public static ArrayList<Point> search(final ArrayList<Point> list) {
        for (int i = 0; i < MTMap.row; ++i) {
            for (int j = 0; j < MTMap.column; ++j) {
                final int n = MTMap.mt[i][j];
                if (n != -1) {
                    for (int k = 0; k < MTMap.row; ++k) {
                        for (int l = 0; l < MTMap.column; ++l) {
                            if (n == MTMap.mt[k][l] && (i != k || j != l)) {
                                final ArrayList list2 = new ArrayList<Point>();
                                if (checkCondition(i, j, k, l, list2) && list2.size() < 5) {
                                    list.add(new Point(i, j));
                                    list.add(new Point(k, l));
                                    return list;
                                }
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    public static boolean searchHint() {
        boolean b = true;
        final ArrayList<Point> list = new ArrayList<Point>();
        search(list);
        if (list.size() == 2) {
            final Point point = list.get(0);
            final Point point2 = list.get(b ? 1 : 0);
            GameMainActivity.mPlay.setHint(point.x, point.y, point2.x, point2.y);
        } else {
            b = false;
        }
        return b;
    }
}
