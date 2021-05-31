package com.onet.onet.utils;

import org.anddev.andengine.entity.scene.Scene;
import android.graphics.Typeface;

import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.Engine;
import android.content.Context;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.entity.modifier.IEntityModifier;
import org.anddev.andengine.entity.IEntity;
import com.onet.onet.GameConfiguration;
import com.onet.onet.GameMainActivity;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

public class DollarButton extends ISprite {

    public BitmapTextureAtlas dollar_BTA;
    public Sprite dollar_SP;
    public TextureRegion dollar_TR;
    public int heightFont;
    public Font mFont;
    public BitmapTextureAtlas mFontTexture;
    public ChangeableText txt_dollar;
    public int x_end_dollar;

    public DollarButton() {
        super();
        this.heightFont = 30;
        this.x_end_dollar = 0;
    }

    public void addDollar(final int n) {
        GameMainActivity.mPlay.dollar_current += n;
        if (GameMainActivity.mPlay.dollar_current < 0) {
            GameMainActivity.mPlay.dollar_current = 0;
        }
        this.updateDollar();
    }

    public void addTextSubDollar(final String s) {
        try {
            final int n = GameConfiguration.getCenterX() - this.mFont.getStringWidth(s) / 2;
            final int n2 = GameConfiguration.getCenterY() - this.heightFont / 2;
            final ChangeableText changeableText = new ChangeableText(n, n2, this.mFont, s);
            this.mScene.attachChild(changeableText);
            changeableText.registerEntityModifier(new SequenceEntityModifier(new IEntityModifier.IEntityModifierListener() {
                public void onModifierFinished(final IModifier<IEntity> modifier, final IEntity entity) {
                    DollarButton.this.removeChangeableText(changeableText);
                }

                public void onModifierStarted(final IModifier<IEntity> modifier, final IEntity entity) {
                }
            }, new IEntityModifier[]{new MoveModifier(2.0f, n, n, n2, -100.0f)}));
        } catch (Exception ex) {
        }
    }

    public int getEndY() {
        return (int) (this.dollar_SP.getY() + this.dollar_SP.getHeight());
    }

    public int getStartX() {
        return (int) this.dollar_SP.getX();
    }

    public int getXendDollar() {
        return this.x_end_dollar;
    }

    @Override
    public void onCreate(final Context context, final Engine engine, final Camera camera) {
        this.ini(context, engine, camera);
    }

    @Override
    public void onDestroy() {
    }

    public void resetChild() {
        try {
            this.mScene.attachChild(this.dollar_SP);
            this.mScene.attachChild(this.txt_dollar);
        } catch (Exception exx) {
            exx.printStackTrace();
        }
    }

    @Override
    public void onLoadResources() {
        this.heightFont *= (int) GameConfiguration.getRaceHeight();
        this.dollar_BTA = new BitmapTextureAtlas(64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.dollar_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.dollar_BTA, this.mContext, "scores.png", 0, 0);
        this.mEngine.getTextureManager().loadTextures(this.dollar_BTA);
        this.mFontTexture = new BitmapTextureAtlas(1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.mFont = new Font(this.mFontTexture, Typeface.createFromAsset(this.mContext.getAssets(), "BrushScriptStd.otf"), this.heightFont, true, -1);
        this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
        this.mEngine.getFontManager().loadFont(this.mFont);
    }

    @Override
    public void onLoadScene(final Scene mScene) {
        this.mScene = mScene;
        final int n = (int) (this.dollar_TR.getWidth() * GameConfiguration.getRaceWidth());
        final int n2 = n * this.dollar_TR.getHeight() / this.dollar_TR.getWidth();
        this.dollar_SP = new Sprite(5, 0, n, n2, this.dollar_TR);
        this.mScene.attachChild(this.dollar_SP);
        final int n3 = 5 + n;
        this.txt_dollar = new ChangeableText(n3, 0 + (n2 / 2 - this.heightFont / 2), this.mFont, "", 30);
        this.txt_dollar.setColor((255.0f / 255.0f), (0.0f / 255.0f), (0.0f / 255.0f));
        this.x_end_dollar = n3 + this.mFont.getStringWidth("0000000 - L.00");
        this.updateDollar();
        this.mScene.attachChild(this.txt_dollar);
    }

    public void removeChangeableText(final ChangeableText changeableText) {
        this.mEngine.runOnUpdateThread(new Runnable() {
            @Override
            public void run() {
                try {
                    DollarButton.this.mScene.detachChild(changeableText);
                } catch (Exception ex) {
                }
            }
        });
    }

    public void reset() {
        GameMainActivity.mPlay.dollar_current = GameMainActivity.mPlay.total_dollar;
        this.updateDollar();
    }

    public void updateDollar() {
        String value = String.valueOf(GameMainActivity.mPlay.dollar_current);
        if (GameMainActivity.mPlay.dollar_current == 0) {
            value = "000";
        }

        if (LevelManager.levelCurrent < 10) {
            this.txt_dollar.setText(String.valueOf(value) + "$ - Lv." + String.valueOf(LevelManager.levelCurrent));
        } else if (LevelManager.levelCurrent < 100) {
            this.txt_dollar.setText(String.valueOf(value) + "$ - L." + String.valueOf(LevelManager.levelCurrent));
        } else if (LevelManager.levelCurrent < 1000) {
            this.txt_dollar.setText(String.valueOf(value) + "$ - " + String.valueOf(LevelManager.levelCurrent));
        } else {
            this.txt_dollar.setText(String.valueOf(value) + "$ - " + String.valueOf(LevelManager.levelCurrent));
        }
    }
}
