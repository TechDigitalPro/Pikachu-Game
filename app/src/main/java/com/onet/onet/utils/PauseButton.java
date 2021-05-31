package com.onet.onet.utils;

import org.anddev.andengine.input.touch.TouchEvent;
import com.onet.onet.GameConfiguration;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.Engine;
import android.content.Context;
import com.onet.onet.GameMainActivity;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

public class PauseButton extends ISprite {

    public BitmapTextureAtlas buttonpause_BTA;
    public Sprite buttonpause_SP;
    public TextureRegion buttonpause_TR;

    public float getMidY() {
        return (10.0f + this.buttonpause_SP.getHeight()) / 2.0f;
    }

    public float getStartX() {
        return this.buttonpause_SP.getX() - 20.0f;
    }

    public float getYPause() {
        return this.buttonpause_SP.getY() + this.buttonpause_TR.getHeight();
    }

    public void onClickButtonPause() {
        GameMainActivity.mPlay.pauseGame();
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
        this.buttonpause_BTA = new BitmapTextureAtlas(64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.buttonpause_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.buttonpause_BTA, this.mContext, "gamepause.png", 0, 0);
        this.mEngine.getTextureManager().loadTextures(this.buttonpause_BTA);
    }

    public void reset() {
        try {
            this.mScene.attachChild(this.buttonpause_SP);
            this.mScene.registerTouchArea((Scene.ITouchArea) this.buttonpause_SP);
        } catch (Exception exx) {
            exx.printStackTrace();
        }
    }

    @Override
    public void onLoadScene(final Scene mScene) {
        this.mScene = mScene;
        final int n = (int) (this.buttonpause_TR.getWidth() * GameConfiguration.getRaceWidth());
        this.buttonpause_SP = new Sprite((float) (-5 + (GameConfiguration.SCREENWIDTH - n)), (float) 0, (float) n, (float) (n * this.buttonpause_TR.getHeight() / this.buttonpause_TR.getWidth()), this.buttonpause_TR) {
            @Override
            public boolean onAreaTouched(final TouchEvent touchEvent, final float n, final float n2) {
                if (touchEvent.getAction() == 0) {
                    GameMainActivity.mSound.playClick();
                    PauseButton.this.buttonpause_SP.setScale(1.3f);
                } else if (touchEvent.getAction() == 1) {
                    PauseButton.this.buttonpause_SP.setScale(1.0f);
                    PauseButton.this.onClickButtonPause();
                }
                return true;
            }
        };
        this.mScene.registerTouchArea((Scene.ITouchArea) this.buttonpause_SP);
        this.mScene.attachChild(this.buttonpause_SP);
    }
}
