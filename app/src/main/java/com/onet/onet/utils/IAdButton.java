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

        import com.onet.onet.IAdDialog;
        import com.onet.onet.GameMainActivity;
        import com.onet.onet.IButtonListener;

        import org.anddev.andengine.opengl.texture.region.TextureRegion;
        import org.anddev.andengine.entity.sprite.Sprite;
        import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

public class IAdButton extends ISprite {

    public BitmapTextureAtlas buttoniad_BTA;
    public Sprite buttoniad_SP;
    public TextureRegion buttoniad_TR;

    public void onClickButtonIAd() {

        //chua shown iad dialog
        IAdDialog.showIAdDialog(GameMainActivity.mPlay, true, null,
                new IButtonListener() {
                    @Override
                    public void onPress(final Dialog dialog) {
                    }
                },
                new IButtonListener() {
                    @Override
                    public void onPress(final Dialog dialog) {
                        //open app
                        try {
                            try {
                                GameMainActivity.instance.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("")));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                GameMainActivity.instance.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("")));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, true
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
            this.mScene.attachChild(this.buttoniad_SP);
            this.mScene.registerTouchArea((Scene.ITouchArea) this.buttoniad_SP);
        } catch (Exception exx) {
            exx.printStackTrace();
        }
    }

    @Override
    public void onLoadResources() {
        this.buttoniad_BTA = new BitmapTextureAtlas(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.buttoniad_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.buttoniad_BTA, this.mContext, "iads.png", 0, 0);
        this.mEngine.getTextureManager().loadTextures(this.buttoniad_BTA);
    }

    @Override
    public void onLoadScene(final Scene mScene) {
        this.mScene = mScene;
        final int n = (int) (this.buttoniad_TR.getWidth() * GameConfiguration.getRaceWidth());
        final int n2 = n * this.buttoniad_TR.getHeight() / this.buttoniad_TR.getWidth();
        this.buttoniad_SP = new Sprite((float) (GameConfiguration.SCREENWIDTH - n), (float) (-10 + (GameConfiguration.SCREENHIEGHT - 3 * n2)), (float) n, (float) n2, this.buttoniad_TR) {
            @Override
            public boolean onAreaTouched(final TouchEvent touchEvent, final float n, final float n2) {
                if (touchEvent.getAction() == 0) {
                    GameMainActivity.mSound.playClick();
                    IAdButton.this.buttoniad_SP.setScale(1.3f);
                } else if (touchEvent.getAction() == 1) {
                    IAdButton.this.buttoniad_SP.setScale(1.0f);
                    IAdButton.this.onClickButtonIAd();
                }
                return true;
            }
        };
        this.mScene.registerTouchArea((Scene.ITouchArea) this.buttoniad_SP);
        this.mScene.attachChild(this.buttoniad_SP);
    }
}
