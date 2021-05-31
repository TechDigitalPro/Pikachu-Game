package com.onet.onet.utils;

import android.os.Handler;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.Engine;
import android.content.Context;
import android.graphics.Point;
import java.util.ArrayList;

import org.anddev.andengine.entity.primitive.Line;
import com.onet.onet.GameConfiguration;

public class DrawLineSprite extends ISprite
{
    public int heightLine;
    
    public DrawLineSprite() {
        super();
        this.heightLine = 5;
    }
    
    public void addLine(int n, int n2, int n3, int n4) {
        if (n == n3) {
            if (n2 < n4) {
                n2 -= 4;
                n4 += 4;
            }
            else {
                n2 += 4;
                n4 -= 4;
            }
        }
        else if (n2 == n4) {
            if (n < n3) {
                n -= 4;
                n3 += 4;
            }
            else {
                n += 4;
                n3 -= 4;
            }
        }
        final Line line = new Line(n + GameConfiguration.ITEM_WIDTH / 2, n2 + GameConfiguration.ITEM_HEIGHT / 2, n3 + GameConfiguration.ITEM_WIDTH / 2, n4 + GameConfiguration.ITEM_HEIGHT / 2, this.heightLine);
        line.setColor(1.0f, 0.0f, 0.0f);
        this.mScene.attachChild(line);
        this.removeLine(line);
    }
    
    public void addLine(final int n, final int n2, final int n3, final int n4, final ArrayList<Point> list) {
        final Point xyByIJ = MTMap.getXYByIJ(n, n2);
        final Point xyByIJ2 = MTMap.getXYByIJ(n3, n4);
        if (list.size() == 2) {
            this.addLine(xyByIJ.x, xyByIJ.y, xyByIJ2.x, xyByIJ2.y);
        }
        else if (list.size() == 3) {
            Point point = null;
            for (int i = 0; i < list.size(); ++i) {
                point = list.get(i);
                if ((point.x != n || point.y != n2) && (point.x != n3 || point.y != n4)) {
                    break;
                }
            }
            final Point xyByIJ3 = MTMap.getXYByIJ(point.x, point.y);
            this.addLine(xyByIJ.x, xyByIJ.y, xyByIJ3.x, xyByIJ3.y);
            this.addLine(xyByIJ3.x, xyByIJ3.y, xyByIJ2.x, xyByIJ2.y);
        }
        else if (list.size() == 4) {
            Point point2 = null;
            Point point3 = null;
            for (int j = 0; j < list.size(); ++j) {
                final Point point4 = list.get(j);
                if ((point4.x != n || point4.y != n2) && (point4.x != n3 || point4.y != n4)) {
                    if (point2 == null && (point4.x == n || point4.y == n2)) {
                        point2 = list.get(j);
                    }
                    else {
                        point3 = list.get(j);
                    }
                    ILogger.LogInfo("----------------------------");
                    ILogger.LogInfo(" i = " + j);
                    ILogger.LogInfo("p_mid.x = " + point4.x);
                    ILogger.LogInfo("p_mid.y = " + point4.y);
                }
            }
            final Point xyByIJ4 = MTMap.getXYByIJ(point2.x, point2.y);
            final Point xyByIJ5 = MTMap.getXYByIJ(point3.x, point3.y);
            this.addLine(xyByIJ.x, xyByIJ.y, xyByIJ4.x, xyByIJ4.y);
            this.addLine(xyByIJ4.x, xyByIJ4.y, xyByIJ5.x, xyByIJ5.y);
            this.addLine(xyByIJ5.x, xyByIJ5.y, xyByIJ2.x, xyByIJ2.y);
        }
    }
    
    @Override
    public void onCreate(final Context context, final Engine engine, final Camera camera) {
        this.ini(context, engine, camera);
    }
    
    @Override
    public void onDestroy() {
    }
    
    @Override
    public void onLoadResources() {
    }
    
    @Override
    public void onLoadScene(final Scene mScene) {
        this.mScene = mScene;
        if (GameConfiguration.SCREENHIEGHT >= 320 && GameConfiguration.SCREENHIEGHT < 480) {
            this.heightLine = 10;
        }
        else if (GameConfiguration.SCREENHIEGHT < 320) {
            this.heightLine = 6;
        }
    }
    
    public void removeLine(final Line line) {
        new Handler().postDelayed((Runnable)new Runnable() {
            @Override
            public void run() {
                DrawLineSprite.this.mEngine.runOnUpdateThread(new Runnable() {
                    @Override
                    public void run() {
                        DrawLineSprite.this.mScene.detachChild(line);
                    }
                });
            }
        }, 300L);
    }
}