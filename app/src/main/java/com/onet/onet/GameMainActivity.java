package com.onet.onet;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.engine.Engine;

import com.onet.onet.ads.AdsActivity;
import com.onet.onet.utils.LevelManager;
import com.onet.onet.utils.ILogger;
import android.os.Bundle;
import com.onet.onet.utils.OnclickChecker;
import com.onet.onet.utils.MTMap;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Debug;
import android.os.Message;
import com.onet.onet.utils.MusicBackground;
import com.onet.onet.utils.TextLoadingSprite;
import com.onet.onet.utils.ProgressBar;
import com.onet.onet.utils.ItemsManager;
import com.onet.onet.utils.HintSprite;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.onet.onet.utils.DrawLineSprite;
import com.onet.onet.utils.DollarButton;
import com.onet.onet.utils.PauseButton;
import com.onet.onet.utils.HintButton;
import com.onet.onet.utils.IAdButton;

import com.onet.onet.utils.BackgroundImage;
import com.onet.onet.utils.RateButton;
import com.onet.onet.utils.SoundController;
import java.util.List;

public class GameMainActivity extends GameBaseActivity {

    public boolean isOnPauseBefore = false;
    public boolean isThereAnyPopupDLG = false;
    public static SoundController mSound;
    public static GameMainActivity mPlay;
    public boolean GameOver;
    public static int dollar_current = 0;
    public BackgroundImage mBackground;
    public HintButton mButtonHint;
    public RateButton mButtonRate;
    public IAdButton mButtonAd;

    public PauseButton mButtonPause;
    public DollarButton mDollar;
    public DrawLineSprite mDrawLine;
    public Handler mHandler_gameover;
    public HintSprite mHint;
    public ItemsManager mManagerItemPikachu;
    public ProgressBar mProgressBar;
    public TextLoadingSprite mTextLoading;
    public MusicBackground musicBackground;
    public int total_dollar = 0;
    public static Context instance = null;
    public int LevelResultCODE = GameConfiguration.LevelResultCODE_DEFAULT;
    public int starByLevel = 0;
    public int moneyBonus = 0;

    public InterstitialAd interstitial = null;

    private boolean isGamePaused = false;

    public static Handler mHandler_loadINTAd;
    public static boolean isLoadLevelAd = false;

    public GameMainActivity() {
        super();
        this.GameOver = false;
        this.mHandler_gameover = new Handler() {
            public void handleMessage(final Message message) {
                super.handleMessage(message);
                GameMainActivity.this.mSound.playGameOver();
                GameMainActivity.this.LevelResultCODE = GameConfiguration.LevelResultCODE_FAIL;
                showINTAd();
            }
        };

        this.mHandler_loadINTAd = new Handler() {
            public void handleMessage(final Message message) {
                super.handleMessage(message);
                try {
                    if (GameMainActivity.this.interstitial == null) {
                        GameMainActivity.this.interstitial = new InterstitialAd((Activity) GameMainActivity.this);
                        GameMainActivity.this.interstitial.setAdUnitId(GameMainActivity.this.getString(R.string.admob_int_id));
                    }
                    GameMainActivity.this.isLoadLevelAd = true;
                    GameMainActivity.this.interstitial.loadAd(new AdRequest.Builder().build());
                } catch (Exception eee) {
                    eee.printStackTrace();
                }
            }
        };
    }

    public void gameOver() {
        this.mHandler_gameover.sendEmptyMessage(0);
    }

    public void load() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GameMainActivity.this.mProgressBar.onLoadResources();
                GameMainActivity.this.mButtonPause.onLoadResources();
                GameMainActivity.this.mButtonHint.onLoadResources();
                GameMainActivity.this.mButtonRate.onLoadResources();
                GameMainActivity.this.mButtonAd.onLoadResources();

                GameMainActivity.this.mDollar.onLoadResources();
                GameMainActivity.this.mHint.onLoadResources();
                GameMainActivity.this.mButtonPause.onLoadScene(GameMainActivity.this.mScene);
                GameMainActivity.this.mButtonHint.onLoadScene(GameMainActivity.this.mScene);
                GameMainActivity.this.mButtonRate.onLoadScene(GameMainActivity.this.mScene);
                GameMainActivity.this.mButtonAd.onLoadScene(GameMainActivity.this.mScene);

                GameMainActivity.this.mDollar.onLoadScene(GameMainActivity.this.mScene);
                GameMainActivity.this.mProgressBar.setXStartEnd(GameMainActivity.this.mDollar.getXendDollar(), GameMainActivity.this.mButtonPause.getStartX());
                GameMainActivity.this.mProgressBar.setMidYButtonPause(GameMainActivity.this.mButtonPause.getMidY());
                GameMainActivity.this.mProgressBar.onLoadScene(GameMainActivity.this.mScene);
                MTMap.getTotalRowColumn(GameMainActivity.this.mDollar.getStartX(), GameMainActivity.this.mDollar.getEndY(), (int) GameMainActivity.this.mButtonHint.getStartX());
                GameMainActivity.this.mManagerItemPikachu.setScene(GameMainActivity.this.mScene);
                if (!(GameMainActivity.this.mManagerItemPikachu.list_itemPikachu != null && GameMainActivity.this.mManagerItemPikachu.list_itemPikachu.size() > 0)) {
                    GameMainActivity.this.mManagerItemPikachu.addItem();
                }
                GameMainActivity.this.mDrawLine.onLoadScene(GameMainActivity.this.mScene);
                GameMainActivity.this.mTextLoading.hideTextLoading();
                GameMainActivity.this.mHint.onLoadScene(GameMainActivity.this.mScene);
                OnclickChecker.searchHint();
                GameMainActivity.this.mPlay.startGame();
            }
        }).start();
    }

    public void onBackPressed() {
        GameMainActivity.this.pauseGameWithoutDlg();

        GameMainActivity.this.mSound.playClick();
        if (IPreferences.getInstance((Context) this).getRateStatus()) {
            IConfirmDialog.showRateDialog(this, true, null,
                    new IButtonListener() {
                @Override
                public void onPress(final Dialog dialog) {
                    try {
                        if (GameMainActivity.instance != null) {
                            ((Activity) GameMainActivity.instance).finish();
                        }
                        android.os.Process.killProcess(android.os.Process.myPid());
                        GameMainActivity.this.onBackPressed();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }, new IButtonListener() {
                @Override
                public void onPress(final Dialog dialog) {
                    GameMainActivity.this.resumeGame();
                }
            }
            );
        } else {
            IRateDialog.showRateDialog(this, true, null,
                    new IButtonListener() {
                @Override
                public void onPress(final Dialog dialog) {
                    GameMainActivity.this.resumeGame();
                }
            },
                    new IButtonListener() {
                @Override
                public void onPress(final Dialog dialog) {
                    try {
                        IPreferences.getInstance((Context) GameMainActivity.instance).saveRateStatus(true);
                        try {
                            GameMainActivity.instance.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + GameMainActivity.instance.getPackageName())));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            GameMainActivity.instance.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + GameMainActivity.instance.getPackageName())));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, true
            );
        }
    }

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        instance = this;

        (this.musicBackground = new MusicBackground()).loadMusic((Context) this);
        this.musicBackground.play();
        OnclickChecker.isOnClickItem = true;
        GameMainActivity.mPlay = this;
        getGameStates();

        (this.mBackground = new BackgroundImage()).onCreate(this.mContext, this.mEngine, this.mCamera);
        (this.mTextLoading = new TextLoadingSprite()).onCreate((Context) GameMainActivity.mPlay, this.mEngine, this.mCamera);
        (this.mButtonPause = new PauseButton()).onCreate(this.mContext, this.mEngine, this.mCamera);
        (this.mButtonHint = new HintButton()).onCreate((Context) GameMainActivity.mPlay, this.mEngine, this.mCamera);
        (this.mDollar = new DollarButton()).onCreate(this.mContext, this.mEngine, this.mCamera);
        (this.mProgressBar = new ProgressBar()).onCreate(this.mContext, this.mEngine, this.mCamera);
        (this.mHint = new HintSprite()).onCreate(this.mContext, this.mEngine, this.mCamera);
        this.mManagerItemPikachu = new ItemsManager(this.mContext, this.mEngine, this.mCamera);
        (this.mDrawLine = new DrawLineSprite()).onCreate((Context) GameMainActivity.mPlay, this.mEngine, this.mCamera);
        (this.mButtonRate = new RateButton()).onCreate((Context) GameMainActivity.mPlay, this.mEngine, this.mCamera);
        (this.mButtonAd = new IAdButton()).onCreate((Context) GameMainActivity.mPlay, this.mEngine, this.mCamera);

        (GameMainActivity.this.mSound = new SoundController()).loadSound((Context) this);
        GameMainActivity.this.isThereAnyPopupDLG = false;


        if (IPreferences.getInstance((Context) this).getPolicyStatus()){
//DA SHOW POLICY DIALOG ROIF.
            if (GameConfiguration.isConnected) {
                GameMainActivity.this.adView = new com.google.android.gms.ads.AdView(GameMainActivity.this);
                GameMainActivity.this.adView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);
                GameMainActivity.this.adView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                GameMainActivity.this.adView.setAdUnitId(GameMainActivity.this.getString(R.string.admob_id));
                GameMainActivity.this.adView.setAdListener(new com.google.android.gms.ads.AdListener() {
                    public void onAdLoaded() {
                        try {
                            boolean isAds_Addbefore = false;
                            for (int i = 0; i < GameMainActivity.this.mFrameLayout.getChildCount(); ++i) {
                                if (GameMainActivity.this.mFrameLayout.getChildAt(i) == GameMainActivity.this.adView) {
                                    isAds_Addbefore = true;
                                }
                            }

                            if (!isAds_Addbefore) {
                                FrameLayout.LayoutParams frameLayout$LayoutParams2 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, 81);
                                GameMainActivity.this.mFrameLayout.addView(GameMainActivity.this.adView, (ViewGroup.LayoutParams) frameLayout$LayoutParams2);
                            }
                        } catch (Exception exx) {
                            exx.printStackTrace();
                        }
                    }

                    public void onAdFailedToLoad(int errorCode) {
                        Log.d("onAdFailedToLoad", String.valueOf(errorCode));
                    }

                    public void onAdOpened() {
                        Log.d("onAdOpened", "onAdOpened");
                    }

                    public void onAdClosed() {
                        Log.d("onAdClosed", "onAdClosed");
                    }

                    public void onAdLeftApplication() {
                        Log.d("onAdLeftApplication", "onAdLeftApplication");
                    }

                });
                GameMainActivity.this.adView.loadAd(new AdRequest.Builder().build());
            }
        }else{
            IPolicyDialog.showPolicyDialog(this, true, null,
                    new IButtonListener() {
                        @Override
                        public void onPress(final Dialog dialog) {
                            try {
                                String url = "";
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                GameMainActivity.this.startActivity(i);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }                    }
                    },
                    new IButtonListener() {
                        @Override
                        public void onPress(final Dialog dialog) {
                            try {
                                IPreferences.getInstance((Context) GameMainActivity.instance).savePolicyStatus(true);

                                if (GameConfiguration.isConnected) {
                                    GameMainActivity.this.adView = new com.google.android.gms.ads.AdView(GameMainActivity.this);
                                    GameMainActivity.this.adView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);
                                    GameMainActivity.this.adView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    GameMainActivity.this.adView.setAdUnitId(GameMainActivity.this.getString(R.string.admob_id));
                                    GameMainActivity.this.adView.setAdListener(new com.google.android.gms.ads.AdListener() {
                                        public void onAdLoaded() {
                                            try {
                                                boolean isAds_Addbefore = false;
                                                for (int i = 0; i < GameMainActivity.this.mFrameLayout.getChildCount(); ++i) {
                                                    if (GameMainActivity.this.mFrameLayout.getChildAt(i) == GameMainActivity.this.adView) {
                                                        isAds_Addbefore = true;
                                                    }
                                                }

                                                if (!isAds_Addbefore) {
                                                    FrameLayout.LayoutParams frameLayout$LayoutParams2 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, 81);
                                                    GameMainActivity.this.mFrameLayout.addView(GameMainActivity.this.adView, (ViewGroup.LayoutParams) frameLayout$LayoutParams2);
                                                }
                                            } catch (Exception exx) {
                                                exx.printStackTrace();
                                            }
                                        }

                                        public void onAdFailedToLoad(int errorCode) {

                                        }

                                        public void onAdOpened() {

                                        }

                                        public void onAdClosed() {

                                        }

                                        public void onAdLeftApplication() {

                                        }

                                    });
                                    GameMainActivity.this.adView.loadAd(new AdRequest.Builder().build());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, true
            );
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ILogger.LogInfo("onDestroy");
        try {
            this.mProgressBar.onDestroy();
            this.mHint.onDestroy();
            this.musicBackground.release();
        } catch (Exception ex) {
            ILogger.LogError("Play onDestroy " + ex.toString());
        }
    }

    @Override
    public void onLoadComplete() {
    }

    @Override
    public Engine onLoadEngine() {
        super.onLoadEngine();
        ILogger.LogInfo("onLoadEngine");
        return this.mEngine;
    }

    @Override
    public void onLoadResources() {
        super.onLoadResources();
        this.mBackground.onLoadResources();
        this.mTextLoading.onLoadResources();
        ILogger.LogInfo("onLoadResources");
    }

    @Override
    public Scene onLoadScene() {
        super.onLoadScene();
        this.mBackground.onLoadScene(this.mScene);
        this.mTextLoading.onLoadScene(this.mScene);
        this.load();
        ILogger.LogInfo("onLoadScene");
        return this.mScene;
    }

    protected void onStop() {
        super.onStop();
    }

    public void pauseGameWithoutDlg() {
        this.mProgressBar.setPause(true);
    }

    public void pauseGameWithoutDlg2() {
        this.musicBackground.pause();
        this.mProgressBar.setPause(true);
    }

    public void pauseGame() {
        this.mProgressBar.setPause(true);
        this.musicBackground.pause();
        IPauseDialog.showShareDialog(GameMainActivity.mPlay, GameMainActivity.mPlay.dollar_current, null, null,
                new IButtonListener() {
            @Override
            public void onPress(final Dialog dialog) {
                try {
                    GameMainActivity.this.LevelResultCODE = GameConfiguration.LevelResultCODE_DEFAULT;
                    GameMainActivity.this.isThereAnyPopupDLG = false;
                    GameMainActivity.this.mSound.playClick();
                    GameMainActivity.mPlay.musicBackground.resume();
                    GameMainActivity.mPlay.resumeGame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        );
    }

    public void removeItem(final int n, final int n2) {
        try {
            if (this.mManagerItemPikachu.removeItem(n, n2) <= 18) {
                if (!GameMainActivity.this.isLoadLevelAd) {
                    try {
                        this.mHandler_loadINTAd.sendEmptyMessage(0);
                    } catch (Exception exxxx) {
                        exxxx.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetGame() {
        this.GameOver = false;
        ILogger.LogInfo("resetGame");
        this.mProgressBar.setStop(true);
        this.mTextLoading.showTextLoading();
        this.mHint.setVisiable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                OnclickChecker.isOnClickItem = true;
                GameMainActivity.this.mBackground.resetBackground();
                GameMainActivity.this.mManagerItemPikachu.reset();

                //
                GameMainActivity.this.mProgressBar.reset();
                GameMainActivity.this.mButtonPause.reset();
                GameMainActivity.this.mButtonHint.reset();
                GameMainActivity.this.mButtonRate.reset();
                GameMainActivity.this.mButtonAd.reset();

                GameMainActivity.this.mDollar.resetChild();
                GameMainActivity.this.mHint.resetChild();
                //

                MTMap.reRandomMT();
                GameMainActivity.this.mManagerItemPikachu.addItem();
                try {
                    Thread.sleep(1000L);
                    GameMainActivity.this.mTextLoading.hideTextLoading();
                    GameMainActivity.this.mDollar.reset();
                    GameMainActivity.this.mManagerItemPikachu.showItemEffect(LevelManager.levelCurrent % 8, 1);
                    GameMainActivity.this.mProgressBar.setTotalTime(LevelManager.getTimeLevel());
                    GameMainActivity.this.mProgressBar.start();
                    GameMainActivity.this.sortChildren();
                    OnclickChecker.searchHint();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public void resumeGame() {
        this.mProgressBar.setPause(false);
    }

    public void setHint(final int n, final int n2, final int n3, final int n4) {
        this.mHint.setHint(n, n2, n3, n4);
    }

    public void showDialogCompleted() {
        GameMainActivity.this.mSound.playFinish();
        final int timeEnd = this.mProgressBar.getTimeEnd();
        this.mProgressBar.setStop(true);
        GameMainActivity.mPlay.total_dollar = GameMainActivity.mPlay.dollar_current;
        new Handler().postDelayed((Runnable) new Runnable() {
            @Override
            public void run() {
                starByLevel = LevelManager.getStarByLevel(LevelManager.levelCurrent, timeEnd);
                moneyBonus = 10 * LevelManager.levelCurrent;
                switch (starByLevel) {
                    case 1: {
                        moneyBonus += 100;
                        break;
                    }
                    case 2: {
                        moneyBonus += 150;
                        break;
                    }
                    case 3: {
                        moneyBonus += 300;
                        break;
                    }
                }
                GameMainActivity.mPlay.total_dollar += moneyBonus;
                GameMainActivity.mPlay.dollar_current = GameMainActivity.mPlay.total_dollar;
                GameMainActivity.mPlay.mDollar.updateDollar();
                saveGameStates();

                if (LevelManager.levelCurrent > LevelManager.totalLevel) {
                    GameMainActivity.this.LevelResultCODE = GameConfiguration.LevelResultCODE_WINNER;
                    showINTAd();
                } else {
                    GameMainActivity.this.LevelResultCODE = GameConfiguration.LevelResultCODE_COMPLETE;
                    showINTAd();
                }
            }
        }, 800L);

        /*if (!IPreferences.getInstance((Context) this).getRateStatus() && LevelManager.levelCurrent == 1) {
            IRateDialog.showRateDialog(this, true, null,
                    new IButtonListener() {
                @Override
                public void onPress(final Dialog dialog) {
                    GameMainActivity.this.resumeGame();
                }
            },
                    new IButtonListener() {
                @Override
                public void onPress(final Dialog dialog) {
                    try {
                        IPreferences.getInstance((Context) GameMainActivity.instance).saveRateStatus(true);
                        try {
                            GameMainActivity.instance.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + GameMainActivity.instance.getPackageName())));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            GameMainActivity.instance.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + GameMainActivity.instance.getPackageName())));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, true
            );
        }*/

        LevelManager.levelCurrent += 1;
    }

    public void sortChildren() {
        try {
            this.mScene.sortChildren();
        } catch (Exception ex) {
        }
    }

    public void startGame() {
        OnclickChecker.isOnClickItem = true;
        this.GameOver = false;
        this.mProgressBar.setTotalTime(LevelManager.getTimeLevel());
        this.mProgressBar.start();
        this.mManagerItemPikachu.showItemEffect(LevelManager.levelCurrent % 8, 1);
    }

    public void swapItem() {
        this.mManagerItemPikachu.swapItem();
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            if (isGamePaused) {
                Intent intent = new Intent(this, AdsActivity.class);
                intent.putExtra("IS_GAMEPAUSED", true);
                startActivity(intent);
                isGamePaused = false;
            } else {
                isGamePaused = true;
            }
            if (isOnPauseBefore) {
                isOnPauseBefore = false;
                showLevelContextDialog();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            isOnPauseBefore = true;
            GameMainActivity.mPlay.pauseGameWithoutDlg2();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void showINTAd() {
        GameMainActivity.this.isLoadLevelAd = false;
            if (GameMainActivity.this.interstitial != null) {
                if (GameMainActivity.this.interstitial.isLoaded()) {
                    GameMainActivity.this.interstitial.show();
                } else {
                    showLevelContextDialog();
                    GameMainActivity.this.LevelResultCODE = GameConfiguration.LevelResultCODE_DEFAULT;
                }
            } else {
                showLevelContextDialog();
                GameMainActivity.this.LevelResultCODE = GameConfiguration.LevelResultCODE_DEFAULT;
            }
    }

    public void showLevelContextDialog() {
        if (GameMainActivity.this.LevelResultCODE == GameConfiguration.LevelResultCODE_DEFAULT) {
            if (!GameMainActivity.this.isThereAnyPopupDLG) {
                GameMainActivity.this.isThereAnyPopupDLG = true;
                GameMainActivity.mPlay.pauseGame();
            }
        } else if (GameMainActivity.this.LevelResultCODE == GameConfiguration.LevelResultCODE_COMPLETE) {
            if (!GameMainActivity.this.isThereAnyPopupDLG) {
                GameMainActivity.this.isThereAnyPopupDLG = true;


                if (IPreferences.getInstance((Context) this).getIAdShownStatus()) {
                    //DA SHOW IAD DIALOG ROI. show level context
                    IWinnerDialog.showShareDialog(GameMainActivity.mPlay, GameMainActivity.mPlay.total_dollar, starByLevel, moneyBonus, null, null,
                            new IButtonListener() {
                                @Override
                                public void onPress(final Dialog dialog) {
                                    try {
                                        GameMainActivity.this.isThereAnyPopupDLG = false;
                                        GameMainActivity.this.LevelResultCODE = GameConfiguration.LevelResultCODE_DEFAULT;
                                        GameMainActivity.this.mSound.playClick();
                                        GameMainActivity.mPlay.musicBackground.resume();
                                        GameMainActivity.mPlay.resetGame();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                    );
                } else {
                    //kiem tra level hien tai da hon 5 chua?
                    if (LevelManager.levelCurrent < 5) {
                        //khong hien thi iad. show level context
                        IWinnerDialog.showShareDialog(GameMainActivity.mPlay, GameMainActivity.mPlay.total_dollar, starByLevel, moneyBonus, null, null,
                                new IButtonListener() {
                                    @Override
                                    public void onPress(final Dialog dialog) {
                                        try {
                                            GameMainActivity.this.isThereAnyPopupDLG = false;
                                            GameMainActivity.this.LevelResultCODE = GameConfiguration.LevelResultCODE_DEFAULT;
                                            GameMainActivity.this.mSound.playClick();
                                            GameMainActivity.mPlay.musicBackground.resume();
                                            GameMainActivity.mPlay.resetGame();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                        );
                    } else {

                        //chua shown iad dialog
                        IAdDialog.showIAdDialog(this, true, null,
                                new IButtonListener() {
                                    @Override
                                    public void onPress(final Dialog dialog) {
                                        //luu noi la da hien thi iad.
                                        IPreferences.getInstance((Context) GameMainActivity.this).saveIAdShownStatus(true);

                                        //goi show level context dialog.
                                        IWinnerDialog.showShareDialog(GameMainActivity.mPlay, GameMainActivity.mPlay.total_dollar, starByLevel, moneyBonus, null, null,
                                                new IButtonListener() {
                                                    @Override
                                                    public void onPress(final Dialog dialog) {
                                                        try {
                                                            GameMainActivity.this.isThereAnyPopupDLG = false;
                                                            GameMainActivity.this.LevelResultCODE = GameConfiguration.LevelResultCODE_DEFAULT;
                                                            GameMainActivity.this.mSound.playClick();
                                                            GameMainActivity.mPlay.musicBackground.resume();
                                                            GameMainActivity.mPlay.resetGame();
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }
                                        );
                                    }
                                },
                                new IButtonListener() {
                                    @Override
                                    public void onPress(final Dialog dialog) {
                                        //luu noi la da hien thi iad.
                                        IPreferences.getInstance((Context) GameMainActivity.this).saveIAdShownStatus(true);

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
                                        //goi show level context dialog.
                                        IWinnerDialog.showShareDialog(GameMainActivity.mPlay, GameMainActivity.mPlay.total_dollar, starByLevel, moneyBonus, null, null,
                                                new IButtonListener() {
                                                    @Override
                                                    public void onPress(final Dialog dialog) {
                                                        try {
                                                            GameMainActivity.this.isThereAnyPopupDLG = false;
                                                            GameMainActivity.this.LevelResultCODE = GameConfiguration.LevelResultCODE_DEFAULT;
                                                            GameMainActivity.this.mSound.playClick();
                                                            GameMainActivity.mPlay.musicBackground.resume();
                                                            GameMainActivity.mPlay.resetGame();
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }
                                        );
                                    }
                                }, true
                        );
                    }
                }
            }
        } else if (GameMainActivity.this.LevelResultCODE == GameConfiguration.LevelResultCODE_FAIL) {
            if (!GameMainActivity.this.isThereAnyPopupDLG) {
                GameMainActivity.this.isThereAnyPopupDLG = true;
                ILoserDialog.showShareDialog(GameMainActivity.mPlay, GameMainActivity.mPlay.dollar_current, null,
                        new IButtonListener() {
                    @Override
                    public void onPress(final Dialog dialog) {
                        GameMainActivity.this.isThereAnyPopupDLG = false;
                        GameMainActivity.this.LevelResultCODE = GameConfiguration.LevelResultCODE_DEFAULT;
                        GameMainActivity.this.mSound.playClick();
                        GameMainActivity.mPlay.musicBackground.resume();
                        GameMainActivity.mPlay.dollar_current = GameMainActivity.mPlay.total_dollar;
                        getGameStates();

                        GameMainActivity.mPlay.resetGame();
                    }
                },
                        null
                );
            }
        } else if (GameMainActivity.this.LevelResultCODE == GameConfiguration.LevelResultCODE_WINNER) {
            if (!GameMainActivity.this.isThereAnyPopupDLG) {
                GameMainActivity.this.isThereAnyPopupDLG = true;
                ILevelWinerDialog.showShareDialog(GameMainActivity.mPlay, GameMainActivity.mPlay.total_dollar, starByLevel, moneyBonus, null,
                        new IButtonListener() {
                    @Override
                    public void onPress(final Dialog dialog) {
                        try {
                            GameMainActivity.this.isThereAnyPopupDLG = false;
                            GameMainActivity.this.LevelResultCODE = GameConfiguration.LevelResultCODE_DEFAULT;
                            GameMainActivity.this.mSound.playClick();
                            GameMainActivity.mPlay.musicBackground.resume();
                            LevelManager.levelCurrent = 1;
                            GameMainActivity.mPlay.dollar_current = 0;
                            GameMainActivity.mPlay.total_dollar = 0;
                            GameMainActivity.mPlay.resetGame();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                        new IButtonListener() {
                    @Override
                    public void onPress(final Dialog dialog) {
                        try {
                            GameMainActivity.this.isThereAnyPopupDLG = false;
                            GameMainActivity.this.LevelResultCODE = GameConfiguration.LevelResultCODE_DEFAULT;
                            GameMainActivity.this.mSound.playClick();
                            GameMainActivity.mPlay.musicBackground.resume();
                            LevelManager.levelCurrent = 1;
                            GameMainActivity.mPlay.dollar_current = 0;
                            GameMainActivity.mPlay.total_dollar = 0;
                            GameMainActivity.mPlay.resetGame();

                            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            sharingIntent.putExtra(Intent.EXTRA_TEXT, ("https://play.google.com/store/apps/details?id=" + GameMainActivity.mPlay.getPackageName()));
                            PackageManager packManager = GameMainActivity.mPlay.getPackageManager();
                            List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(sharingIntent, PackageManager.MATCH_DEFAULT_ONLY);

                            boolean resolved = false;
                            for (ResolveInfo resolveInfo : resolvedInfoList) {
                                if (resolveInfo.activityInfo.packageName.startsWith("com.facebook.katana")) {
                                    sharingIntent.setClassName(
                                            resolveInfo.activityInfo.packageName,
                                            resolveInfo.activityInfo.name);
                                    resolved = true;
                                    break;
                                }
                            }
                            if (resolved) {
                                GameMainActivity.mPlay.startActivity(sharingIntent);
                            } else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(GameMainActivity.mPlay);
                                alert.setTitle("Warning");
                                alert.setMessage("Facebook not found on your phone :((. Install it and try again!");
                                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                });
                                alert.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                );
            }
        }
    }

    public boolean getGameStates() {
        try {
            LevelManager.levelCurrent = IPreferences.getInstance((Context) this).getCurLevel();
            GameMainActivity.mPlay.dollar_current = IPreferences.getInstance((Context) this).getCurrent_dollar();
            GameMainActivity.mPlay.total_dollar = IPreferences.getInstance((Context) this).getTotal_dollar();
            GameMainActivity.mPlay.starByLevel = IPreferences.getInstance((Context) this).getStar();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean saveGameStates() {
        try {
            IPreferences.getInstance((Context) this).saveCurLevel(LevelManager.levelCurrent);
            IPreferences.getInstance((Context) this).saveCurrent_dollar(GameMainActivity.mPlay.dollar_current);
            IPreferences.getInstance((Context) this).saveTotal_dollar(GameMainActivity.mPlay.total_dollar);
            IPreferences.getInstance((Context) this).saveStar(GameMainActivity.mPlay.starByLevel);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
