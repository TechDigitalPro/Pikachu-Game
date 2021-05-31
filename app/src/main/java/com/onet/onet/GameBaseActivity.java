package com.onet.onet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.view.RenderSurfaceView;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import com.google.android.gms.ads.AdView;

public class GameBaseActivity extends BaseGameActivity {

    public AdView adView = null;
    public FrameLayout mFrameLayout;
    private int contactUsID;
    private int group1Id;
    public Camera mCamera;
    public Context mContext;
    public Engine mEngine;
    public Scene mScene;
    private int moreAppID;
    private int newAppID;
    private int rateID;
    private int shareID;
    private int storeID;

    public GameBaseActivity() {
        super();
        this.group1Id = 1;
        this.storeID = 1;
        this.rateID = 2;
        this.newAppID = 3;
        this.moreAppID = 4;
        this.shareID = 5;
        this.contactUsID = 6;
    }

    private void showToastNoConn() {
        //Toast.makeText((Context) this, (CharSequence) this.getString(2131427358), 0).show();
    }

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);

        GameConfiguration.AdBannerHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        GameConfiguration.AdBannerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 320, getResources().getDisplayMetrics());
        GameConfiguration.isConnected = hasConnection();

        this.mContext = (Context) this;
        this.getWindow().getAttributes().windowAnimations = 2131492957;
    }

    /**
     * Checks if the device has Internet connection.
     *
     * @return <code>true</code> if the phone is connected to the Internet.
     */
    public boolean hasConnection() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiNetwork != null && wifiNetwork.isConnected()) {
                return true;
            }
            NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobileNetwork != null && mobileNetwork.isConnected()) {
                return true;
            }
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnected()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        try {
            if (GameBaseActivity.this.adView != null) {
                if (GameBaseActivity.this.adView.getParent() != null) {
                    ((ViewGroup) GameBaseActivity.this.adView.getParent()).removeView(GameBaseActivity.this.adView);
                }
                GameBaseActivity.this.adView.destroy();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (GameBaseActivity.this.adView != null) {
                GameBaseActivity.this.adView.resume();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        try {
            if (GameBaseActivity.this.adView != null) {
                GameBaseActivity.this.adView.pause();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        super.onPause();
    }

    @Override
    public void onLoadComplete() {
    }

    @Override
    public Engine onLoadEngine() {
        GameConfiguration.getDisplayScreen(this);
        this.mCamera = new Camera(0.0f, 0.0f, GameConfiguration.SCREENWIDTH, GameConfiguration.SCREENHIEGHT);
        return this.mEngine = new Engine(new EngineOptions(true, GameConfiguration.ScreenOrientation_Default, new RatioResolutionPolicy(GameConfiguration.SCREENWIDTH, GameConfiguration.SCREENHIEGHT), this.mCamera).setNeedsSound(true).setNeedsMusic(true));
    }

    @Override
    public void onLoadResources() {
    }

    @Override
    public Scene onLoadScene() {
        (this.mScene = new Scene()).setTouchAreaBindingEnabled(true);
        return this.mScene;
    }

    @Override
    protected void onSetContentView() {
        final FrameLayout mFrameLayout = new FrameLayout((Context) this);
        final FrameLayout.LayoutParams frameLayout$LayoutParams = new FrameLayout.LayoutParams(-1, -1, 17);
        (this.mRenderSurfaceView = new RenderSurfaceView((Context) this)).setRenderer(this.mEngine);
        mFrameLayout.addView((View) this.mRenderSurfaceView, new ViewGroup.LayoutParams((ViewGroup.LayoutParams) super.createSurfaceViewLayoutParams()));

        this.setContentView((View) mFrameLayout, (ViewGroup.LayoutParams) frameLayout$LayoutParams);
        mFrameLayout.refreshDrawableState();
        GameBaseActivity.this.mFrameLayout = mFrameLayout;
    }
}
