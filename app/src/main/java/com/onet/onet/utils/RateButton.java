/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onet.onet.utils;

import android.app.Dialog;
import org.anddev.andengine.input.touch.TouchEvent;
import com.onet.onet.GameConfiguration;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.Engine;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.onet.onet.IRateDialog;
import com.onet.onet.GameMainActivity;
import com.onet.onet.IButtonListener;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

public class RateButton extends ISprite {

    public BitmapTextureAtlas buttonhint_BTA;
    public Sprite buttonhint_SP;
    public TextureRegion buttonhint_TR;

    public void onClickButtonHint() {
        IRateDialog.showRateDialog(GameMainActivity.mPlay, true, null, null,
                new IButtonListener() {
                    @Override
                    public void onPress(final Dialog dialog) {
                        try {
                            try {
                                GameMainActivity.mPlay.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + GameMainActivity.mPlay.getPackageName())));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                GameMainActivity.mPlay.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + GameMainActivity.mPlay.getPackageName())));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, false
        );
    }

    @Override
    public void onCreate(final Context context, final Engine engine, final Camera camera) {
        this.ini(context, engine, camera);
    }

    @Override
    public void onDestroy() {
    }

    public void reset() {
        try {
            this.mScene.attachChild(this.buttonhint_SP);
            this.mScene.registerTouchArea((Scene.ITouchArea) this.buttonhint_SP);
        } catch (Exception exx) {
            exx.printStackTrace();
        }
    }

    @Override
    public void onLoadResources() {
        this.buttonhint_BTA = new BitmapTextureAtlas(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.buttonhint_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.buttonhint_BTA, this.mContext, "rater.png", 0, 0);
        this.mEngine.getTextureManager().loadTextures(this.buttonhint_BTA);
    }

    @Override
    public void onLoadScene(final Scene mScene) {
        this.mScene = mScene;
        final int n = (int) (this.buttonhint_TR.getWidth() * GameConfiguration.getRaceWidth());
        final int n2 = n * this.buttonhint_TR.getHeight() / this.buttonhint_TR.getWidth();
        this.buttonhint_SP = new Sprite((float) (GameConfiguration.SCREENWIDTH - n), (float) (-10 + (GameConfiguration.SCREENHIEGHT - 2 * n2)), (float) n, (float) n2, this.buttonhint_TR) {
            @Override
            public boolean onAreaTouched(final TouchEvent touchEvent, final float n, final float n2) {
                if (touchEvent.getAction() == 0) {
                    GameMainActivity.mSound.playClick();
                    RateButton.this.buttonhint_SP.setScale(1.3f);
                } else if (touchEvent.getAction() == 1) {
                    RateButton.this.buttonhint_SP.setScale(1.0f);
                    RateButton.this.onClickButtonHint();
                }
                return true;
            }
        };
        this.mScene.registerTouchArea((Scene.ITouchArea) this.buttonhint_SP);
        this.mScene.attachChild(this.buttonhint_SP);
    }
}
