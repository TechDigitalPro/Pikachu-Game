package com.onet.onet.utils;

import org.anddev.andengine.input.touch.TouchEvent;
import com.onet.onet.GameConfiguration;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.Engine;
import android.content.Context;
import android.widget.Toast;
import com.onet.onet.GameMainActivity;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

public class HintButton extends ISprite {

    public BitmapTextureAtlas buttonhint_BTA;
    public Sprite buttonhint_SP;
    public TextureRegion buttonhint_TR;

    public float getSize() {
        try {
            return buttonhint_SP.getHeight();
        } catch (Exception e) {
            return 70.0f;
        }
    }

    public float getStartX() {
        try {
            return buttonhint_SP.getInitialX();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 70.0f;
        }
    }

    public void onClickButtonHint() {

        if (GameMainActivity.mPlay.dollar_current >= 50) {
            OnclickChecker.activeSearhHint();
            if (!GameMainActivity.mPlay.mHint.visiable()) {
                GameMainActivity.mPlay.mDollar.addDollar(-50);
                GameMainActivity.mPlay.mDollar.addTextSubDollar("-50 $");
            }
            GameMainActivity.mPlay.mHint.setVisiable(true);
        } else {
            Toast.makeText(GameMainActivity.mPlay, (CharSequence) "Not enough money to buy hint! Each hint costs 50$!!!", 0).show();
        }
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
        this.buttonhint_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.buttonhint_BTA, this.mContext, "question.png", 0, 0);
        this.mEngine.getTextureManager().loadTextures(this.buttonhint_BTA);
    }

    @Override
    public void onLoadScene(final Scene mScene) {
        this.mScene = mScene;
        final int n = (int) (this.buttonhint_TR.getWidth() * GameConfiguration.getRaceWidth());
        final int n2 = n * this.buttonhint_TR.getHeight() / this.buttonhint_TR.getWidth();
        this.buttonhint_SP = new Sprite((float) (GameConfiguration.SCREENWIDTH - n), (float) (-5 + (GameConfiguration.SCREENHIEGHT - n2)), (float) n, (float) n2, this.buttonhint_TR) {
            @Override
            public boolean onAreaTouched(final TouchEvent touchEvent, final float n, final float n2) {
                if (touchEvent.getAction() == 0) {
                    GameMainActivity.mSound.playClick();
                    HintButton.this.buttonhint_SP.setScale(1.3f);
                } else if (touchEvent.getAction() == 1) {
                    HintButton.this.buttonhint_SP.setScale(1.0f);
                    HintButton.this.onClickButtonHint();
                }
                return true;
            }
        };
        this.mScene.registerTouchArea((Scene.ITouchArea) this.buttonhint_SP);
        this.mScene.attachChild(this.buttonhint_SP);
    }
}
