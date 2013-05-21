package com.kyosuke25.AnniversaryMemo;

import java.util.Locale;

import org.apache.cordova.DroidGap;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.LinearLayout;

import com.ad_stir.AdstirTerminate;
import com.ad_stir.AdstirView;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class MainActivity extends DroidGap {

    private Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int anniversaryID = this.getIntent().getIntExtra("anniversaryID", -1);
        if(anniversaryID != -1) {
            super.loadUrl("file:///android_asset/www/index.html?anniversaryID=" + anniversaryID);
        }else{
            super.loadUrl("file:///android_asset/www/index.html");
        }

        // ロケールが日本とそれ以外で分ける。
        // 日本　→　Adstir
        // それ以外　→　Admob
        if(Locale.getDefault().equals(Locale.JAPAN)) {
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    doAdStir();
//                    doAdMob();
                }
            }, 10000);
        } else {
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    doAdMob();
                }
            }, 10000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        com.google.analytics.tracking.android.EasyTracker.getInstance().activityStop(this);

        if(Locale.getDefault().equals(Locale.JAPAN)){
            AdstirTerminate.init(this);
        }
    }

    private void doAdMob() {
        AdView adView = new AdView(this, AdSize.BANNER, "7be328a8b6e8459a");
        LinearLayout layout = super.root;
        layout.addView(adView);
        layout.setHorizontalGravity(android.view.Gravity.CENTER_HORIZONTAL);
        AdRequest request = new AdRequest();
//        request.addTestDevice(AdRequest.TEST_EMULATOR);
        adView.loadAd(request);
    }

    private void doAdStir() {
        AdstirView adstirView = new AdstirView(this, "MEDIA-9bb67ac4", 1);
        LinearLayout layout = super.root;
        layout.addView(adstirView);
        layout.setHorizontalGravity(android.view.Gravity.CENTER_HORIZONTAL);
    }
}
