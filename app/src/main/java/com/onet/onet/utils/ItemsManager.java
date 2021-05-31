package com.onet.onet.utils;

import android.graphics.Point;
import com.onet.onet.GameConfiguration;
import org.anddev.andengine.entity.scene.Scene;
import com.onet.onet.GameMainActivity;
import org.anddev.andengine.engine.Engine;
import android.content.Context;
import org.anddev.andengine.engine.camera.Camera;
import java.util.ArrayList;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import java.util.HashMap;

public class ItemsManager {

    public static HashMap<Integer, TextureRegion> map_Resources;
    public int count_total_activesearchhint;
    public ArrayList<ItemPikachuSprite> list_itemPikachu = new ArrayList<ItemPikachuSprite>();
    public Camera mCamera;
    public Context mContext;
    public Engine mEngine;
    public GameMainActivity mPlay;
    public Scene mScene;
    public int total_activesearchhint;

    static {
        ItemsManager.map_Resources = new HashMap<Integer, TextureRegion>();
    }

    public ItemsManager(final Context mContext, final Engine mEngine, final Camera mCamera) {
        super();
        this.total_activesearchhint = -1;
        this.count_total_activesearchhint = -1;
        this.mContext = mContext;
        this.mEngine = mEngine;
        this.mCamera = mCamera;
        this.mPlay = (GameMainActivity) mContext;
        ItemsManager.map_Resources.clear();
    }

    public void ActiveSearchHint() {
        synchronized (this) {
            this.count_total_activesearchhint += 1;
            if (this.count_total_activesearchhint >= this.total_activesearchhint) {
                this.count_total_activesearchhint = 0;
                this.total_activesearchhint = 0;
                OnclickChecker.activeSearhHint();
            }
        }
    }

    public void addItem() {
        int ystart = MTMap.YSTART;
        for (int i = 0; i < MTMap.row; ++i) {
            int xstart = MTMap.XSTART;
            for (int j = 0; j < MTMap.column; ++j) {
                if (MTMap.mt[i][j] != -1) {
                    final ItemPikachuSprite onetItemPikachu = new ItemPikachuSprite();
                    onetItemPikachu.onCreate(this.mContext, this.mEngine, this.mCamera);
                    onetItemPikachu.newItemPikachu(this.mScene, i, j, MTMap.mt[i][j], xstart, ystart);
                    this.list_itemPikachu.add(onetItemPikachu);
                }
                xstart += GameConfiguration.ITEM_WIDTH;
            }
            ystart += GameConfiguration.ITEM_HEIGHT;
        }
    }

    public void move2(final ItemPikachuSprite onetItemPikachu, final int n) {
        final int i = onetItemPikachu.I;
        final int j = onetItemPikachu.J;
        MTMap.mt[i][j] = -1;
        MTMap.mt[i + n][j] = onetItemPikachu.GT_ITEM;
        onetItemPikachu.setIJ(i + n, j);
        final Point xyByIJ = MTMap.getXYByIJ(i + n, j);
        onetItemPikachu.moveXY(xyByIJ.x, xyByIJ.y);
    }

    public void move3(final ItemPikachuSprite onetItemPikachu, final int n) {
        final int i = onetItemPikachu.I;
        final int j = onetItemPikachu.J;
        MTMap.mt[i][j] = -1;
        MTMap.mt[i - n][j] = onetItemPikachu.GT_ITEM;
        onetItemPikachu.setIJ(i - n, j);
        final Point xyByIJ = MTMap.getXYByIJ(i - n, j);
        onetItemPikachu.moveXY(xyByIJ.x, xyByIJ.y);
    }

    public void move4(final ItemPikachuSprite onetItemPikachu, final int n) {
        final int i = onetItemPikachu.I;
        final int j = onetItemPikachu.J;
        MTMap.mt[i][j] = -1;
        MTMap.mt[i][j + n] = onetItemPikachu.GT_ITEM;
        onetItemPikachu.setIJ(i, j + n);
        final Point xyByIJ = MTMap.getXYByIJ(i, j + n);
        onetItemPikachu.moveXY(xyByIJ.x, xyByIJ.y);
    }

    public void move5(final ItemPikachuSprite onetItemPikachu, final int n) {
        final int i = onetItemPikachu.I;
        final int j = onetItemPikachu.J;
        MTMap.mt[i][j] = -1;
        MTMap.mt[i][j - n] = onetItemPikachu.GT_ITEM;
        onetItemPikachu.setIJ(i, j - n);
        final Point xyByIJ = MTMap.getXYByIJ(i, j - n);
        onetItemPikachu.moveXY(xyByIJ.x, xyByIJ.y);
    }

    public void move67(final ItemPikachuSprite onetItemPikachu, final int n) {
        final int i = onetItemPikachu.I;
        final int j = onetItemPikachu.J;
        MTMap.mt[i][j] = -1;
        Point point;
        if (n == 0) {
            MTMap.mt[i][j + 1] = onetItemPikachu.GT_ITEM;
            onetItemPikachu.setIJ(i, j + 1);
            point = MTMap.getXYByIJ(i, j + 1);
        } else {
            MTMap.mt[i][j - 1] = onetItemPikachu.GT_ITEM;
            onetItemPikachu.setIJ(i, j - 1);
            point = MTMap.getXYByIJ(i, j - 1);
        }
        onetItemPikachu.moveXY(point.x, point.y);
    }

    public void move89(final ItemPikachuSprite onetItemPikachu, final int n) {
        final int i = onetItemPikachu.I;
        final int j = onetItemPikachu.J;
        MTMap.mt[i][j] = -1;
        Point point;
        if (n == 0) {
            MTMap.mt[i + 1][j] = onetItemPikachu.GT_ITEM;
            onetItemPikachu.setIJ(i + 1, j);
            point = MTMap.getXYByIJ(i + 1, j);
        } else {
            MTMap.mt[i - 1][j] = onetItemPikachu.GT_ITEM;
            onetItemPikachu.setIJ(i - 1, j);
            point = MTMap.getXYByIJ(i - 1, j);
        }
        onetItemPikachu.moveXY(point.x, point.y);
    }

    public void moveItem(final int n, final int n2, final int n3, final int n4) {
        switch (LevelManager.levelCurrent % 9) {
            default: {
                OnclickChecker.activeSearhHint();
                break;
            }
            case 1: {
                OnclickChecker.activeSearhHint();
                break;
            }
            case 2: {
                this.moveLevel2(n, n2, n3, n4);
                break;
            }
            case 3: {
                this.moveLevel3(n, n2, n3, n4);
                break;
            }
            case 4: {
                this.moveLevel4(n, n2, n3, n4);
                break;
            }
            case 5: {
                this.moveLevel5(n, n2, n3, n4);
                break;
            }
            case 6: {
                this.moveLevel67(n, n2, n3, n4);
                break;
            }
            case 7: {
                this.moveLevel67(n3, n4, n, n2);
                break;
            }
            case 8: {
                this.moveLevel89(n, n2, n3, n4);
                break;
            }
            case 9: {
                this.moveLevel89(n3, n4, n, n2);
                break;
            }
        }
    }

    public void moveLevel2(final int n, final int n2, final int n3, final int n4) {
        int n5 = 1;
        if (n2 == n4) {
            n5 = 2;
        }
        final HashMap<Integer, ItemPikachuSprite> hashMap = new HashMap<Integer, ItemPikachuSprite>();
        final HashMap<Integer, ItemPikachuSprite> hashMap2 = new HashMap<Integer, ItemPikachuSprite>();
        final HashMap<Integer, Integer> hashMap3 = new HashMap<Integer, Integer>();
        final HashMap<Integer, Integer> hashMap4 = new HashMap<Integer, Integer>();
        for (int i = 0; i < this.list_itemPikachu.size(); ++i) {
            final ItemPikachuSprite onetItemPikachu = this.list_itemPikachu.get(i);
            if ((onetItemPikachu.I != n || onetItemPikachu.J != n2) && (onetItemPikachu.I != n3 || onetItemPikachu.J != n4)) {
                int n6 = n5;
                if (onetItemPikachu.J == n2 && onetItemPikachu.I < n) {
                    if (n5 == 2 && onetItemPikachu.I > n3) {
                        n6 = 1;
                    }
                    hashMap3.put(onetItemPikachu.I, n6);
                    hashMap.put(onetItemPikachu.I, onetItemPikachu);
                } else if (onetItemPikachu.J == n4 && onetItemPikachu.I < n3) {
                    if (n5 == 2 && onetItemPikachu.I > n) {
                        n6 = 1;
                    }
                    hashMap4.put(onetItemPikachu.I, n6);
                    hashMap2.put(onetItemPikachu.I, onetItemPikachu);
                }
            }
        }
        this.total_activesearchhint = hashMap.size();
        this.total_activesearchhint += hashMap2.size();
        this.count_total_activesearchhint = 0;
        for (int j = -1 + MTMap.row; j > -1; --j) {
            final ItemPikachuSprite onetItemPikachu2 = hashMap.get(j);
            if (onetItemPikachu2 != null) {
                this.move2(onetItemPikachu2, hashMap3.get(j));
            }
            final ItemPikachuSprite onetItemPikachu3 = hashMap2.get(j);
            if (onetItemPikachu3 != null) {
                this.move2(onetItemPikachu3, hashMap4.get(j));
            }
        }
        if (this.total_activesearchhint == 0) {
            OnclickChecker.activeSearhHint();
        }
    }

    public void moveLevel3(final int n, final int n2, final int n3, final int n4) {
        int n5 = 1;
        if (n2 == n4) {
            n5 = 2;
        }
        final HashMap<Integer, ItemPikachuSprite> hashMap = new HashMap<Integer, ItemPikachuSprite>();
        final HashMap<Integer, ItemPikachuSprite> hashMap2 = new HashMap<Integer, ItemPikachuSprite>();
        final HashMap<Integer, Integer> hashMap3 = new HashMap<Integer, Integer>();
        final HashMap<Integer, Integer> hashMap4 = new HashMap<Integer, Integer>();
        for (int i = 0; i < this.list_itemPikachu.size(); ++i) {
            final ItemPikachuSprite onetItemPikachu = this.list_itemPikachu.get(i);
            if ((onetItemPikachu.I != n || onetItemPikachu.J != n2) && (onetItemPikachu.I != n3 || onetItemPikachu.J != n4)) {
                int n6 = n5;
                if (onetItemPikachu.J == n2 && onetItemPikachu.I > n) {
                    if (n5 == 2 && onetItemPikachu.I < n3) {
                        n6 = 1;
                    }
                    hashMap3.put(onetItemPikachu.I, n6);
                    hashMap.put(onetItemPikachu.I, onetItemPikachu);
                } else if (onetItemPikachu.J == n4 && onetItemPikachu.I > n3) {
                    if (n5 == 2 && onetItemPikachu.I < n) {
                        n6 = 1;
                    }
                    hashMap4.put(onetItemPikachu.I, n6);
                    hashMap2.put(onetItemPikachu.I, onetItemPikachu);
                }
            }
        }
        this.total_activesearchhint = hashMap.size();
        this.total_activesearchhint += hashMap2.size();
        this.count_total_activesearchhint = 0;
        for (int j = 0; j < MTMap.row; ++j) {
            final ItemPikachuSprite onetItemPikachu2 = hashMap.get(j);
            if (onetItemPikachu2 != null) {
                this.move3(onetItemPikachu2, hashMap3.get(j));
            }
            final ItemPikachuSprite onetItemPikachu3 = hashMap2.get(j);
            if (onetItemPikachu3 != null) {
                this.move3(onetItemPikachu3, hashMap4.get(j));
            }
        }
        if (this.total_activesearchhint == 0) {
            OnclickChecker.activeSearhHint();
        }
    }

    public void moveLevel4(final int n, final int n2, final int n3, final int n4) {
        int n5 = 1;
        if (n == n3) {
            n5 = 2;
        }
        final HashMap<Integer, ItemPikachuSprite> hashMap = new HashMap<Integer, ItemPikachuSprite>();
        final HashMap<Integer, ItemPikachuSprite> hashMap2 = new HashMap<Integer, ItemPikachuSprite>();
        final HashMap<Integer, Integer> hashMap3 = new HashMap<Integer, Integer>();
        final HashMap<Integer, Integer> hashMap4 = new HashMap<Integer, Integer>();
        for (int i = 0; i < this.list_itemPikachu.size(); ++i) {
            final ItemPikachuSprite onetItemPikachu = this.list_itemPikachu.get(i);
            if ((onetItemPikachu.I != n || onetItemPikachu.J != n2) && (onetItemPikachu.I != n3 || onetItemPikachu.J != n4)) {
                int n6 = n5;
                if (onetItemPikachu.I == n && onetItemPikachu.J < n2) {
                    if (n5 == 2 && onetItemPikachu.J > n4) {
                        n6 = 1;
                    }
                    hashMap3.put(onetItemPikachu.J, n6);
                    hashMap.put(onetItemPikachu.J, onetItemPikachu);
                } else if (onetItemPikachu.I == n3 && onetItemPikachu.J < n4) {
                    if (n5 == 2 && onetItemPikachu.J > n2) {
                        n6 = 1;
                    }
                    hashMap4.put(onetItemPikachu.J, n6);
                    hashMap2.put(onetItemPikachu.J, onetItemPikachu);
                }
            }
        }
        this.total_activesearchhint = hashMap.size();
        this.total_activesearchhint += hashMap2.size();
        this.count_total_activesearchhint = 0;
        for (int j = -1 + MTMap.column; j > -1; --j) {
            final ItemPikachuSprite onetItemPikachu2 = hashMap.get(j);
            if (onetItemPikachu2 != null) {
                this.move4(onetItemPikachu2, hashMap3.get(j));
            }
            final ItemPikachuSprite onetItemPikachu3 = hashMap2.get(j);
            if (onetItemPikachu3 != null) {
                this.move4(onetItemPikachu3, hashMap4.get(j));
            }
        }
        if (this.total_activesearchhint == 0) {
            OnclickChecker.activeSearhHint();
        }
    }

    public void moveLevel5(final int n, final int n2, final int n3, final int n4) {
        int n5 = 1;
        if (n == n3) {
            n5 = 2;
        }
        final HashMap<Integer, ItemPikachuSprite> hashMap = new HashMap<Integer, ItemPikachuSprite>();
        final HashMap<Integer, ItemPikachuSprite> hashMap2 = new HashMap<Integer, ItemPikachuSprite>();
        final HashMap<Integer, Integer> hashMap3 = new HashMap<Integer, Integer>();
        final HashMap<Integer, Integer> hashMap4 = new HashMap<Integer, Integer>();
        for (int i = 0; i < this.list_itemPikachu.size(); ++i) {
            final ItemPikachuSprite onetItemPikachu = this.list_itemPikachu.get(i);
            if ((onetItemPikachu.I != n || onetItemPikachu.J != n2) && (onetItemPikachu.I != n3 || onetItemPikachu.J != n4)) {
                int n6 = n5;
                if (onetItemPikachu.I == n && onetItemPikachu.J > n2) {
                    if (n5 == 2 && onetItemPikachu.J < n4) {
                        n6 = 1;
                    }
                    hashMap3.put(onetItemPikachu.J, n6);
                    hashMap.put(onetItemPikachu.J, onetItemPikachu);
                } else if (onetItemPikachu.I == n3 && onetItemPikachu.J > n4) {
                    if (n5 == 2 && onetItemPikachu.J < n2) {
                        n6 = 1;
                    }
                    hashMap4.put(onetItemPikachu.J, n6);
                    hashMap2.put(onetItemPikachu.J, onetItemPikachu);
                }
            }
        }
        this.total_activesearchhint = hashMap.size();
        this.total_activesearchhint += hashMap2.size();
        this.count_total_activesearchhint = 0;
        for (int j = 0; j < MTMap.column; ++j) {
            final ItemPikachuSprite onetItemPikachu2 = hashMap.get(j);
            if (onetItemPikachu2 != null) {
                this.move5(onetItemPikachu2, hashMap3.get(j));
            }
            final ItemPikachuSprite onetItemPikachu3 = hashMap2.get(j);
            if (onetItemPikachu3 != null) {
                this.move5(onetItemPikachu3, hashMap4.get(j));
            }
        }
        if (this.total_activesearchhint == 0) {
            OnclickChecker.activeSearhHint();
        }
    }

    public void moveLevel67(final int n, final int n2, final int n3, final int n4) {
        final HashMap<Integer, ItemPikachuSprite> hashMap = new HashMap<Integer, ItemPikachuSprite>();
        final HashMap<Integer, ItemPikachuSprite> hashMap2 = new HashMap<Integer, ItemPikachuSprite>();
        if (n2 < n4) {
            for (int i = 0; i < this.list_itemPikachu.size(); ++i) {
                final ItemPikachuSprite onetItemPikachu = this.list_itemPikachu.get(i);
                if ((onetItemPikachu.I != n || onetItemPikachu.J != n2) && (onetItemPikachu.I != n3 || onetItemPikachu.J != n4)) {
                    if (onetItemPikachu.I == n && onetItemPikachu.J < n2) {
                        hashMap.put(onetItemPikachu.J, onetItemPikachu);
                    }
                    if (onetItemPikachu.I == n3 && onetItemPikachu.J > n4) {
                        hashMap2.put(onetItemPikachu.J, onetItemPikachu);
                    }
                }
            }
        } else {
            for (int j = 0; j < this.list_itemPikachu.size(); ++j) {
                final ItemPikachuSprite onetItemPikachu2 = this.list_itemPikachu.get(j);
                if ((onetItemPikachu2.I != n || onetItemPikachu2.J != n2) && (onetItemPikachu2.I != n3 || onetItemPikachu2.J != n4)) {
                    if (onetItemPikachu2.I == n3 && onetItemPikachu2.J < n4) {
                        hashMap.put(onetItemPikachu2.J, onetItemPikachu2);
                    }
                    if (onetItemPikachu2.I == n && onetItemPikachu2.J > n2) {
                        hashMap2.put(onetItemPikachu2.J, onetItemPikachu2);
                    }
                }
            }
        }
        this.total_activesearchhint = hashMap.size();
        this.total_activesearchhint += hashMap2.size();
        this.count_total_activesearchhint = 0;
        for (int k = n2 - 1; k > -1; --k) {
            final ItemPikachuSprite onetItemPikachu3 = hashMap.get(k);
            if (onetItemPikachu3 != null) {
                this.move67(onetItemPikachu3, 0);
            }
        }
        for (int l = n2 + 1; l < MTMap.column; ++l) {
            final ItemPikachuSprite onetItemPikachu4 = hashMap2.get(l);
            if (onetItemPikachu4 != null) {
                this.move67(onetItemPikachu4, 1);
            }
        }
        if (this.total_activesearchhint == 0) {
            OnclickChecker.activeSearhHint();
        }
    }

    public void moveLevel89(final int n, final int n2, final int n3, final int n4) {
        final HashMap<Integer, ItemPikachuSprite> hashMap = new HashMap<Integer, ItemPikachuSprite>();
        final HashMap<Integer, ItemPikachuSprite> hashMap2 = new HashMap<Integer, ItemPikachuSprite>();
        if (n < n3) {
            for (int i = 0; i < this.list_itemPikachu.size(); ++i) {
                final ItemPikachuSprite onetItemPikachu = this.list_itemPikachu.get(i);
                if ((onetItemPikachu.I != n || onetItemPikachu.J != n2) && (onetItemPikachu.I != n3 || onetItemPikachu.J != n4)) {
                    if (onetItemPikachu.J == n2 && onetItemPikachu.I < n) {
                        hashMap.put(onetItemPikachu.I, onetItemPikachu);
                    }
                    if (onetItemPikachu.J == n4 && onetItemPikachu.I > n3) {
                        hashMap2.put(onetItemPikachu.I, onetItemPikachu);
                    }
                }
            }
        } else {
            for (int j = 0; j < this.list_itemPikachu.size(); ++j) {
                final ItemPikachuSprite onetItemPikachu2 = this.list_itemPikachu.get(j);
                if ((onetItemPikachu2.I != n || onetItemPikachu2.J != n2) && (onetItemPikachu2.I != n3 || onetItemPikachu2.J != n4)) {
                    if (onetItemPikachu2.J == n4 && onetItemPikachu2.I < n3) {
                        hashMap.put(onetItemPikachu2.I, onetItemPikachu2);
                    }
                    if (onetItemPikachu2.J == n2 && onetItemPikachu2.I > n) {
                        hashMap2.put(onetItemPikachu2.I, onetItemPikachu2);
                    }
                }
            }
        }
        this.total_activesearchhint = hashMap.size();
        this.total_activesearchhint += hashMap2.size();
        this.count_total_activesearchhint = 0;
        for (int k = n - 1; k > -1; --k) {
            final ItemPikachuSprite onetItemPikachu3 = hashMap.get(k);
            if (onetItemPikachu3 != null) {
                this.move89(onetItemPikachu3, 0);
            }
        }
        for (int l = n + 1; l < MTMap.row; ++l) {
            final ItemPikachuSprite onetItemPikachu4 = hashMap2.get(l);
            if (onetItemPikachu4 != null) {
                this.move89(onetItemPikachu4, 1);
            }
        }
        if (this.total_activesearchhint == 0) {
            OnclickChecker.activeSearhHint();
        }
    }

    public int removeItem(final int n, final int n2) {
        for (int i = 0; i < this.list_itemPikachu.size(); ++i) {
            try {
                final ItemPikachuSprite onetItemPikachu = this.list_itemPikachu.get(i);
                if (onetItemPikachu.getI() == n && onetItemPikachu.getJ() == n2) {
                    this.list_itemPikachu.remove(i);
                    ILogger.LogInfo("removeItem list_itemPikachu.size() = " + this.list_itemPikachu.size());
                    break;
                }
            } catch (Exception ex) {
            }
        }
        return this.list_itemPikachu.size();
    }

    public void reset() {
        try {
            while (this.list_itemPikachu.size() != 0) {
                final ItemPikachuSprite onetItemPikachu = this.list_itemPikachu.get(0);
                onetItemPikachu.setVisiable(false);
                this.list_itemPikachu.remove(0);
                onetItemPikachu.onDestroy();
            }
            this.list_itemPikachu.clear();
            this.mScene.clearChildScene();
            this.mScene.clearTouchAreas();
            this.mScene.detachChildren();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setScene(final Scene mScene) {
        this.mScene = mScene;
    }

    public void showItemEffect(final int n, final int n2) {
        for (int i = 0; i < this.list_itemPikachu.size(); ++i) {
            final ItemPikachuSprite onetItemPikachu = this.list_itemPikachu.get(i);
            int randomIndex = IUtil.getRandomIndex(0, 7);
            if (n != -1) {
                randomIndex = n;
            }
            switch (randomIndex) {
                case 0: {
                    onetItemPikachu.move(-100, onetItemPikachu.Y, n2);
                    break;
                }
                case 1: {
                    onetItemPikachu.move(100 + GameConfiguration.SCREENWIDTH, onetItemPikachu.Y, n2);
                    break;
                }
                case 2: {
                    onetItemPikachu.move(onetItemPikachu.X, -100, n2);
                    break;
                }
                case 3: {
                    onetItemPikachu.move(onetItemPikachu.X, 100 + GameConfiguration.SCREENHIEGHT, n2);
                    break;
                }
                case 4: {
                    onetItemPikachu.move(-100, -100, n2);
                    break;
                }
                case 5: {
                    onetItemPikachu.move(100 + GameConfiguration.SCREENWIDTH, -100, n2);
                    break;
                }
                case 6: {
                    onetItemPikachu.move(-100, 100 + GameConfiguration.SCREENHIEGHT, n2);
                    break;
                }
                case 7: {
                    onetItemPikachu.move(100 + GameConfiguration.SCREENWIDTH, 100 + GameConfiguration.SCREENHIEGHT, n2);
                    break;
                }
            }
        }
    }

    public void swap(final ItemPikachuSprite onetItemPikachu, final ItemPikachuSprite onetItemPikachu2) {
        final int i = onetItemPikachu.I;
        final int j = onetItemPikachu.J;
        final int x = onetItemPikachu.X;
        final int y = onetItemPikachu.Y;
        onetItemPikachu.setIJ(onetItemPikachu2.I, onetItemPikachu2.J);
        onetItemPikachu.setPosition(onetItemPikachu2.X, onetItemPikachu2.Y);
        MTMap.mt[onetItemPikachu2.I][onetItemPikachu2.J] = onetItemPikachu.GT_ITEM;
        onetItemPikachu2.setIJ(i, j);
        onetItemPikachu2.setPosition(x, y);
        MTMap.mt[i][j] = onetItemPikachu2.GT_ITEM;
    }

    public void swapItem() {
        for (int i = 0; i < this.list_itemPikachu.size(); ++i) {
            final int randomIndex = IUtil.getRandomIndex(0, -1 + this.list_itemPikachu.size());
            if (randomIndex != i) {
                this.swap(this.list_itemPikachu.get(i), this.list_itemPikachu.get(randomIndex));
            }
        }
    }
}
