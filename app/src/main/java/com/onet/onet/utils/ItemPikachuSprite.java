package com.onet.onet.utils;

import android.os.Handler;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import com.onet.onet.GameConfiguration;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.Engine;
import android.content.Context;
import org.anddev.andengine.entity.scene.Scene;
import com.onet.onet.GameMainActivity;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.modifier.IEntityModifier;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

public class ItemPikachuSprite extends ISprite {

    int GT_ITEM;
    int I;
    int J;
    int X;
    int Y;
    BitmapTextureAtlas item_BTA;
    Sprite item_SP;
    TextureRegion item_TR;

    public ItemPikachuSprite() {
        super();
        this.I = -1;
        this.J = -1;
        this.GT_ITEM = -1;
        this.X = -1;
        this.Y = -1;
    }

    public int getGT_ITEM() {
        return this.GT_ITEM;
    }

    public int getI() {
        return this.I;
    }

    public int getJ() {
        return this.J;
    }

    public int getX() {
        return this.X;
    }

    public int getY() {
        return this.Y;
    }

    public void move(final int n, final int n2, final int n3) {
        MoveModifier moveModifier;
        if (n3 == 1) {
            moveModifier = new MoveModifier(1.5f, n, this.X, n2, this.Y);
        } else {
            moveModifier = new MoveModifier(1.5f, this.X, n, this.Y, n2);
        }
        this.setVisiable(true);
        this.item_SP.registerEntityModifier(new SequenceEntityModifier(new IEntityModifier[]{moveModifier}));
    }

    public void moveXY(final int x, final int y) {
        this.item_SP.registerEntityModifier(new SequenceEntityModifier(new IEntityModifier.IEntityModifierListener() {
            public void onModifierFinished(final IModifier<IEntity> modifier, final IEntity entity) {
                GameMainActivity.mPlay.mManagerItemPikachu.ActiveSearchHint();
            }

            public void onModifierStarted(final IModifier<IEntity> modifier, final IEntity entity) {
            }
        }, new IEntityModifier[]{new MoveModifier(0.5f, this.X, x, this.Y, y)}));
        this.X = x;
        this.Y = y;
    }

    public void newItemPikachu(final Scene scene, final int i, final int j, final int gt_ITEM, final int x, final int y) {
        this.I = i;
        this.J = j;
        this.GT_ITEM = gt_ITEM;
        this.X = x;
        this.Y = y;
        this.onLoadResources();
        this.onLoadScene(scene);
    }

    @Override
    public void onCreate(final Context context, final Engine engine, final Camera camera) {
        this.ini(context, engine, camera);
    }

    @Override
    public void onDestroy() {
        this.mEngine.runOnUpdateThread(new Runnable() {
            @Override
            public void run() {
                ItemPikachuSprite.this.mScene.detachChild(ItemPikachuSprite.this.item_SP);
                ItemPikachuSprite.this.mScene.unregisterTouchArea((Scene.ITouchArea) ItemPikachuSprite.this.item_SP);
            }
        });
    }

    @Override
    public void onLoadResources() {
        if (!ItemsManager.map_Resources.containsKey(this.GT_ITEM)) {
            this.item_BTA = new BitmapTextureAtlas(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.item_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.item_BTA, this.mContext, String.valueOf(GameConfiguration.pathTHEME) + "item" + this.GT_ITEM + ".png", 0, 0);
            this.mEngine.getTextureManager().loadTextures(this.item_BTA);
            ItemsManager.map_Resources.put(this.GT_ITEM, this.item_TR);
        } else {
            this.item_TR = ItemsManager.map_Resources.get(this.GT_ITEM);
        }
    }

    @Override
    public void onLoadScene(final Scene mScene) {
        this.mScene = mScene;
        this.item_SP = new Sprite(-100.0f, -100.0f, (float) GameConfiguration.ITEM_WIDTH, (float) GameConfiguration.ITEM_HEIGHT, this.item_TR) {
            @Override
            public boolean onAreaTouched(final TouchEvent touchEvent, final float n, final float n2) {
                if (OnclickChecker.isOnClickItem) {
                    if (touchEvent.getAction() == 0) {
                        GameMainActivity.mSound.playClick();
                        ItemPikachuSprite.this.item_SP.setScale(0.8f);
                        ItemPikachuSprite.this.item_SP.setRotation(30.0f);
                    } else if (touchEvent.getAction() == 1) {
                        OnclickChecker.click(ItemPikachuSprite.this);
                    }
                }
                return true;
            }
        };
        this.setVisiable(false);
        try {
            if( this.mScene == null ||  this.item_SP == null){
                int x = 0;
                x = 1;
                x = 2;
            }
            this.mScene.registerTouchArea((Scene.ITouchArea) this.item_SP);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.mScene.attachChild(this.item_SP);
    }

    public void removeItem() {
        new Handler().postDelayed((Runnable) new Runnable() {
            @Override
            public void run() {
                ItemPikachuSprite.this.mEngine.runOnUpdateThread(new Runnable() {
                    @Override
                    public void run() {
                        ItemPikachuSprite.this.mScene.detachChild(ItemPikachuSprite.this.item_SP);
                        ItemPikachuSprite.this.mScene.unregisterTouchArea((Scene.ITouchArea) ItemPikachuSprite.this.item_SP);
                    }
                });
            }
        }, 300L);
    }

    public void setGT_ITEM(final int gt_ITEM) {
        this.GT_ITEM = gt_ITEM;
    }

    public void setI(final int i) {
        this.I = i;
    }

    public void setIJ(final int i, final int j) {
        this.I = i;
        this.J = j;
    }

    public void setJ(final int j) {
        this.J = j;
    }

    public void setPosition(final int x, final int y) {
        this.X = x;
        this.Y = y;
        this.item_SP.setPosition(x, y);
    }

    public void setRotation(final int n) {
        this.item_SP.setRotation(n);
    }

    public void setScale(final float scale) {
        this.item_SP.setScale(scale);
    }

    public void setVisiable(final boolean visible) {
        this.item_SP.setVisible(visible);
    }

    public void setX(final int x) {
        this.X = x;
    }

    public void setY(final int y) {
        this.Y = y;
    }
}
