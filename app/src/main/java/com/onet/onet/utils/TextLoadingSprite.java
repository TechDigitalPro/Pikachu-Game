package com.onet.onet.utils;

import com.onet.onet.GameConfiguration;
import org.anddev.andengine.entity.scene.Scene;

import android.graphics.Typeface;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.Engine;
import android.content.Context;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.font.Font;

public class TextLoadingSprite extends ISprite {

    public int heightFont;
    public boolean isStop;
    public Font mFont;
    public BitmapTextureAtlas mFontTexture;
    public ChangeableText txt_loading;

    public TextLoadingSprite() {
        super();
        this.heightFont = 50;
        this.isStop = false;
    }

    public void hideTextLoading() {
        try {
            this.txt_loading.setVisible(false);
            this.isStop = true;
        } catch (Exception exx) {
            exx.printStackTrace();
        }
    }

    @Override
    public void onCreate(final Context context, final Engine engine, final Camera camera) {
        this.ini(context, engine, camera);
    }

    @Override
    public void onDestroy() {
        this.isStop = true;
        this.mEngine.runOnUpdateThread(new Runnable() {
            @Override
            public void run() {
                try {
                    TextLoadingSprite.this.mScene.detachChild(TextLoadingSprite.this.txt_loading);
                } catch (Exception ex) {
                }
            }
        });
    }

    @Override
    public void onLoadResources() {
        this.mFontTexture = new BitmapTextureAtlas(512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.mFont = new Font(this.mFontTexture, Typeface.createFromAsset(this.mContext.getAssets(), "BrushScriptStd.otf"), this.heightFont, true, -1);
        this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
        this.mEngine.getFontManager().loadFont(this.mFont);
    }

    @Override
    public void onLoadScene(final Scene mScene) {
        this.mScene = mScene;
        this.heightFont *= (int) GameConfiguration.getRaceHeight();
        this.txt_loading = new ChangeableText(GameConfiguration.getCenterX() - this.mFont.getStringWidth("Loading...") / 2, GameConfiguration.getCenterY() - this.heightFont / 2, this.mFont, "Loading...", 30);
        this.mScene.attachChild(this.txt_loading);
        this.threadShowTextLoading();
    }

    public void showTextLoading() {
        try {
            this.txt_loading.setVisible(true);
            this.threadShowTextLoading();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void threadShowTextLoading() {
        this.isStop = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                int n = 0;
                while (!TextLoadingSprite.this.isStop) {
                    try {
                        Thread.sleep(400L);
                        if (n == 0) {
                            TextLoadingSprite.this.txt_loading.setText("Loading");
                            n = 1;
                        } else if (n == 1) {
                            TextLoadingSprite.this.txt_loading.setText("Loading.");
                            n = 2;
                        } else if (n == 2) {
                            TextLoadingSprite.this.txt_loading.setText("Loading..");
                            n = 3;
                        } else {
                            if (n != 3) {
                                continue;
                            }
                            TextLoadingSprite.this.txt_loading.setText("Loading...");
                            n = 0;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
