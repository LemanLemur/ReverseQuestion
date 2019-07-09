package com.example.reversequestions;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class AfterGame extends Activity {
    private InterstitialAd mInterstitialAd;
    private Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_game);

        exit = (Button) findViewById(R.id.exit);
        exit.setEnabled(false);

        MobileAds.initialize(this,
                "ca-app-pub-3443485931200995~4334429354");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3443485931200995/4470160729");
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener(){
            public void onAdLoaded(){
                mInterstitialAd.show();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                goToMenuAd();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                exit.setEnabled(true);
            }
        });
    }

    public void goToMenu(View view){
        this.finish();
    }

    public void goToMenuAd(){
        this.finish();
    }

}
