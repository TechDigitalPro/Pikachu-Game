package com.onet.onet.utils;

import com.onet.onet.GameMainActivity;
import com.onet.onet.GameConfiguration;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.Engine;
import android.content.Context;

import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.entity.primitive.Rectangle;

public class ProgressBar extends ISprite {

    public int current_time;
    public boolean isPause;
    public boolean isStop;
    public Rectangle mRectangle;
    public float midYButtonPause;
    public float pX_end;
    public float pX_start;
    public BitmapTextureAtlas prb_BTA;
    public Sprite prb_SP;
    public TextureRegion prb_TR;
    public int total_time;
    public float w;
    public float width_rect;

    public ProgressBar() {
        super();
        this.isPause = false;
        this.isStop = false;
    }

    public int getTimeEnd() {
        return Math.abs(this.current_time - this.total_time);
    }

    @Override
    public void onCreate(final Context context, final Engine engine, final Camera camera) {
        this.ini(context, engine, camera);
    }

    @Override
    public void onDestroy() {
        this.isStop = true;
    }

    @Override
    public void onLoadResources() {
        this.prb_BTA = new BitmapTextureAtlas(512, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.prb_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.prb_BTA, this.mContext, "progress.png", 0, 0);
        this.mEngine.getTextureManager().loadTextures(this.prb_BTA);
    }

    @Override
    public void onLoadScene(final Scene mScene) {
        this.mScene = mScene;
        final int n = (int) (this.prb_TR.getHeight() * GameConfiguration.getRaceHeight());
        final int n2 = (int) (this.midYButtonPause - n / 2);
        final int n3 = (int) (this.pX_end - this.pX_start);
        this.width_rect = n3 - 6;
        (this.mRectangle = new Rectangle(3.0f + this.pX_start, n2, this.width_rect, n)).setColor((48.0f / 255.0f), (203.0f / 255.0f), (0.0f / 255.0f));
        this.mScene.attachChild(this.mRectangle);
        this.prb_SP = new Sprite(this.pX_start, n2, n3, n, this.prb_TR);
        this.mScene.attachChild(this.prb_SP);
    }

    public void reset() {
        try {
            this.mScene.attachChild(this.mRectangle);
            this.mScene.attachChild(this.prb_SP);
        } catch (Exception exx) {
            exx.printStackTrace();
        }
    }

    public void setMidYButtonPause(final float midYButtonPause) {
        this.midYButtonPause = midYButtonPause;
    }

    public void setPause(final boolean isPause) {
        this.isPause = isPause;
    }

    public void setStop(final boolean isStop) {
        this.isStop = isStop;
    }

    public void setTotalTime(final int total_time) {
        this.total_time = total_time;
    }

    public void setXStartEnd(final float px_start, final float px_end) {
        this.pX_start = px_start;
        this.pX_end = px_end;
    }

    public void start() {
        if (this.total_time >= 0) {
            this.mRectangle.setColor((48.0f / 255.0f), (203.0f / 255.0f), (0.0f / 255.0f));
            this.current_time = this.total_time;
            this.w = this.width_rect / this.current_time;
            this.mRectangle.setVisible(true);
            this.mRectangle.setWidth(this.width_rect);
            this.isStop = false;
            this.isPause = false;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!ProgressBar.this.isStop && ProgressBar.this.current_time > 0) {
                        if (!ProgressBar.this.isPause) {
                            try {
                                long oldtime = System.currentTimeMillis();
                                Thread.sleep(1000L);
                                long newtime = System.currentTimeMillis();
                                long timeee = newtime - oldtime;
                                ProgressBar.this.current_time -= 1;
                                ProgressBar.this.updateProgressBar(ProgressBar.this.w);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }

                            if (ProgressBar.this.current_time < 10 && !GameMainActivity.isLoadLevelAd) {
                                try {
                                    GameMainActivity.mHandler_loadINTAd.sendEmptyMessage(0);
                                } catch (Exception exxxx) {
                                    exxxx.printStackTrace();
                                }
                            }
                        }
                    }

                    if (ProgressBar.this.current_time <= 0) {
                        GameMainActivity.mPlay.gameOver();
                    }
                }
            }).start();
        }
    }

    public void updateProgressBar(final float n) {
        if (this.current_time > 0 && !this.isStop) {
            this.mRectangle.setWidth(this.mRectangle.getWidth() - n);

            if ((this.total_time * 1.0f / this.current_time) > 3.0f) {
                this.mRectangle.setColor((249.0f / 255.0f), (78.0f / 255.0f), (78.0f / 255.0f));
            }
        } else {
            this.mRectangle.setVisible(false);
        }
    }
}

/*package com.maopro86.pikachuvuinhon.utils;

 import com.maopro86.pikachuvuinhon.GameMainActivity;
 import org.anddev.andengine.entity.IEntity;
 import com.maopro86.pikachuvuinhon.GameConfiguration;
 import org.anddev.andengine.entity.scene.Scene;
 import org.anddev.andengine.opengl.texture.ITexture;
 import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
 import org.anddev.andengine.opengl.texture.TextureOptions;
 import org.anddev.andengine.engine.camera.Camera;
 import org.anddev.andengine.engine.Engine;
 import android.content.Context;
 import android.util.Log;
 import org.anddev.andengine.opengl.texture.region.TextureRegion;
 import org.anddev.andengine.entity.sprite.Sprite;
 import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
 import org.anddev.andengine.entity.primitive.Rectangle;

 public class ProgressBar extends ISprite {

 public int current_time;
 public boolean isPause;
 public boolean isStop;
 public Rectangle mRectangle;
 public float midYButtonPause;
 public float pX_end;
 public float pX_start;
 public BitmapTextureAtlas prb_BTA;
 public Sprite prb_SP;
 public TextureRegion prb_TR;
 public int total_time;
 public float w;
 public float width_rect;

 public ProgressBar() {
 super();
 this.isPause = false;
 this.isStop = false;
 }

 public int getTimeEnd() {
 return Math.abs(this.current_time - this.total_time);
 }

 @Override
 public void onCreate(final Context context, final Engine engine, final Camera camera) {
 this.ini(context, engine, camera);
 }

 @Override
 public void onDestroy() {
 this.isStop = true;
 }

 @Override
 public void onLoadResources() {
 this.prb_BTA = new BitmapTextureAtlas(512, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
 this.prb_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.prb_BTA, this.mContext, "progressbar.png", 0, 0);
 this.mEngine.getTextureManager().loadTextures(this.prb_BTA);
 }

 @Override
 public void onLoadScene(final Scene mScene) {
 this.mScene = mScene;
 final int n = (int) (this.prb_TR.getHeight() * GameConfiguration.getRaceHeight());
 int n2 = (int) (this.midYButtonPause - n / 2) - 3;
 this.width_rect = (int) (this.pX_end - this.pX_start) - 4;
 this.prb_SP = new Sprite(this.pX_start, n2, width_rect + 4, n, this.prb_TR);
 this.mScene.attachChild(this.prb_SP);
 this.mRectangle = new Rectangle(this.pX_start + 2, n2 + 3, this.width_rect, n - 6);
 this.mScene.attachChild(this.mRectangle);
 }

 public void reset() {
 try {
 this.mScene.attachChild(this.mRectangle);
 this.mScene.attachChild(this.prb_SP);
 } catch (Exception exx) {
 exx.printStackTrace();
 }
 }

 public void setMidYButtonPause(final float midYButtonPause) {
 this.midYButtonPause = midYButtonPause;
 }

 public void setPause(final boolean isPause) {
 this.isPause = isPause;
 }

 public void setStop(final boolean isStop) {
 this.isStop = isStop;
 }

 public void setTotalTime(final int total_time) {
 this.total_time = total_time;
 }

 public void setXStartEnd(final float px_start, final float px_end) {
 this.pX_start = px_start;
 this.pX_end = px_end;
 }

 public void start() {
 if (this.total_time >= 0) {
 this.mRectangle.setColor(0, 0, 0);
 this.current_time = this.total_time;
 this.w = this.width_rect / this.current_time;
 this.mRectangle.setVisible(true);
 this.mRectangle.setWidth(0);
 this.isStop = false;
 this.isPause = false;
 new Thread(new Runnable() {
 @Override
 public void run() {
 while (!ProgressBar.this.isStop && ProgressBar.this.current_time > 0) {
 if (!ProgressBar.this.isPause) {
 try {
 long oldtime = System.currentTimeMillis();
 Thread.sleep(1000L);
 long newtime = System.currentTimeMillis();
 long timeee = newtime - oldtime;
 ProgressBar.this.current_time -= 1;
 ProgressBar.this.updateProgressBar(ProgressBar.this.w);
 } catch (InterruptedException ex) {
 ex.printStackTrace();
 }
                            
 if (ProgressBar.this.current_time < 10 && !GameMainActivity.isLoadLevelAd) {
 try {
 GameMainActivity.mHandler_loadINTAd.sendEmptyMessage(0);
 } catch (Exception exxxx) {
 exxxx.printStackTrace();
 }
 }
 }
 }

 if (ProgressBar.this.current_time <= 0) {
 GameMainActivity.mPlay.gameOver();
 }
 }
 }).start();
 }
 }

 public void updateProgressBar(final float n) {
 if (this.current_time > 0 && !this.isStop) {
 this.mRectangle.setWidth(this.mRectangle.getWidth() + n);
 this.mRectangle.setPosition(this.pX_start + this.width_rect - this.mRectangle.getWidth(), this.mRectangle.getY());

 if ((this.total_time * 1.0f / this.current_time) > 3.0f) {
 //this.mRectangle.setColor((249.0f / 255.0f), (78.0f / 255.0f), (78.0f / 255.0f));
 }
 } else {
 this.mRectangle.setVisible(false);
 }
 }
    
 }*/
