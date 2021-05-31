package com.onet.onet.utils;

import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import com.onet.onet.GameConfiguration;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.Engine;
import android.content.Context;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

public class BackgroundImage extends ISprite {

    public BitmapTextureAtlas bg_BTA;
    public TextureRegion bg_TR;
    public int total_bg;

    public BackgroundImage() {
        super();
        this.total_bg = 15;
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
        final int n = LevelManager.levelCurrent % this.total_bg;
        this.bg_BTA = new BitmapTextureAtlas(1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.bg_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.bg_BTA, this.mContext, "background.jpg", 0, 0);
        this.mEngine.getTextureManager().loadTextures(this.bg_BTA);
    }

    @Override
    public void onLoadScene(final Scene mScene) {
        try {
            (this.mScene = mScene).setBackground(new SpriteBackground(new Sprite(0.0f, 0.0f, GameConfiguration.SCREENWIDTH, GameConfiguration.SCREENHIEGHT, this.bg_TR)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetBackground() {
        try {
            this.onLoadResources();
            this.onLoadScene(this.mScene);
        } catch (Exception exx) {
            exx.printStackTrace();
        }
    }
}
