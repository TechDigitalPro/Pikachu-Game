package com.onet.onet.utils;

import com.onet.onet.GameMainActivity;
import android.graphics.Point;

import com.onet.onet.GameConfiguration;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.Engine;
import android.content.Context;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

public class HintSprite extends ISprite {

    public AnimatedSprite hint1_AS;
    public AnimatedSprite hint2_AS;
    public BitmapTextureAtlas mBTA;
    public TiledTextureRegion mTTR;

    @Override
    public void onCreate(final Context context, final Engine engine, final Camera camera) {
        this.ini(context, engine, camera);
    }

    @Override
    public void onDestroy() {
    }

    public void resetChild() {
        try {
            this.mScene.attachChild(this.hint1_AS);
            this.mScene.attachChild(this.hint2_AS);
        } catch (Exception exx) {
            exx.printStackTrace();
        }
    }

    @Override
    public void onLoadResources() {
        this.mBTA = new BitmapTextureAtlas(1024, 512, TextureOptions.BILINEAR);
        this.mTTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBTA, this.mContext, "hintarrow.png", 0, 0, 3, 1);
        this.mEngine.getTextureManager().loadTexture(this.mBTA);
    }

    @Override
    public void onLoadScene(final Scene mScene) {
        this.mScene = mScene;
        final int item_WIDTH = GameConfiguration.ITEM_WIDTH;
        final int item_HEIGHT = GameConfiguration.ITEM_HEIGHT;
        (this.hint1_AS = new AnimatedSprite(-100.0f, -100.0f, item_WIDTH, item_HEIGHT, this.mTTR.deepCopy())).animate(150L);
        (this.hint2_AS = new AnimatedSprite(-100.0f, -100.0f, item_WIDTH, item_HEIGHT, this.mTTR.deepCopy())).animate(150L);
        this.setVisiable(false);
        this.hint1_AS.setZIndex(200);
        this.hint2_AS.setZIndex(200);
        this.mScene.attachChild(this.hint1_AS);
        this.mScene.attachChild(this.hint2_AS);
    }

    public void setHint(final int n, final int n2, final int n3, final int n4) {
        final Point xyByIJ = MTMap.getXYByIJ(n, n2);
        this.hint1_AS.setPosition(xyByIJ.x, xyByIJ.y);
        final Point xyByIJ2 = MTMap.getXYByIJ(n3, n4);
        this.hint2_AS.setPosition(xyByIJ2.x, xyByIJ2.y);
    }

    public void setVisiable(final boolean b) {
        if (!GameMainActivity.mPlay.GameOver) {
            this.hint1_AS.setVisible(b);
            this.hint2_AS.setVisible(b);
        }
    }

    public boolean visiable() {
        return this.hint1_AS.isVisible();
    }
}
