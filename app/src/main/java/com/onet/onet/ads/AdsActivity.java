package com.onet.onet.ads;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onet.onet.GameMainActivity;
import com.onet.onet.IPreferences;
import com.onet.onet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AdsActivity extends AppCompatActivity implements View.OnClickListener {
    TextView rateBtn, playBtn;
    RecyclerView recyclerView;
    private List<Object> mAdsItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
        setTransparentStatusBar();
        initView();
    }

    private void initView() {
        rateBtn = findViewById(R.id.ad_rate_us_btn);
        rateBtn.setOnClickListener(this);
        playBtn = findViewById(R.id.ad_play_btn);
        playBtn.setOnClickListener(this);

        addAdsItemsFromJson();
        createAdsView();
    }

    private void createAdsView() {
        recyclerView = findViewById(R.id.ads_recycler_view);
        AdsAdapter adsAdapter = new AdsAdapter(getApplicationContext(), mAdsItems);
        recyclerView.setAdapter(adsAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ad_rate_us_btn:
                Toast.makeText(getApplicationContext(), "Rate Us clicked", Toast.LENGTH_LONG).show();
                showRateUsDialog(getApplicationContext());
                break;
            case R.id.ad_play_btn:
                if (!getIntent().getBooleanExtra("IS_GAMEPAUSED", false)) {
                    startActivity(new Intent(this, GameMainActivity.class));
                }
                finish();
                break;
        }
    }

    private void showRateUsDialog(Context context) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
            }
    }

    public void setTransparentStatusBar(){
        // Set Transparent Status Bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
    private void addAdsItemsFromJson() {
         mAdsItems = new ArrayList<>();

        try {
            String jsonDataString = readJsonDataFromFile();
            JSONArray itemsJsonArray = new JSONArray(jsonDataString);

            for (int i = 0; i < itemsJsonArray.length(); ++i) {

                JSONObject itemObject = itemsJsonArray.getJSONObject(i);

                String adsAppIcon = itemObject.getString("app_icon");
                String adsAppItemName = itemObject.getString("app_name");
                String adsAppItemDescription = itemObject.getString("app_description");
                String adsAppPackageName = itemObject.getString("package_name");

                AdsItemModel adsItemModel = new AdsItemModel(adsAppIcon, adsAppItemName, adsAppItemDescription, adsAppPackageName);
                mAdsItems.add(adsItemModel);
            }
        } catch (IOException | JSONException exception) {
            Log.e(AdsActivity.class.getName(), "Unable to parse JSON file.", exception);
        }
    }

    private String readJsonDataFromFile() throws IOException {

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonDataString = null;
            inputStream = getResources().openRawResource(R.raw.ads_apps);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return new String(builder);
    }
}
