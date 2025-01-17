package com.example.reversequestions;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;
    private ConstraintLayout profile;
    private ConstraintLayout home;
    private TextView homeText;
    private TextView textLvl;
    private TextView textExp;
    private TextView textName;
    private RecyclerView scoreList;
    private ArrayList<Score> tmpScoreList = new ArrayList<Score>();
    private BottomNavigationView navView;
    private ImageButton but1;
    private ImageButton but2;
    private ConstraintLayout conScoreList;
    private ScoreDB db;
    private ProfileDB dbP;
    private ProgressBar progressLvl;
    private int lvl, exp, nextExp;
    private Spinner spinner;
    private ArrayList<Profile> profileList = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mAdView.setVisibility(View.VISIBLE);
                    home.setVisibility(View.VISIBLE);
                    homeText.setVisibility(View.VISIBLE);
                    conScoreList.setVisibility(View.GONE);
                    profile.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_dashboard:
                    prepareProfile();
                    mAdView.setVisibility(View.GONE);
                    home.setVisibility(View.GONE);
                    homeText.setVisibility(View.GONE);
                    conScoreList.setVisibility(View.GONE);
                    profile.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_notifications:
                    mAdView.setVisibility(View.VISIBLE);
                    home.setVisibility(View.GONE);
                    homeText.setVisibility(View.GONE);
                    conScoreList.setVisibility(View.VISIBLE);
                    profile.setVisibility(View.GONE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        db = new ScoreDB(this);
        dbP = new ProfileDB(this);
        getScoreTable();

        conScoreList = (ConstraintLayout) findViewById(R.id.conScoreList);
        home = (ConstraintLayout) findViewById(R.id.constraintLayout);
        homeText = (TextView) findViewById(R.id.textView);
        but1 = (ImageButton) findViewById(R.id.imageButton);
        but2 = (ImageButton) findViewById(R.id.imageButton2);
        profile = (ConstraintLayout) findViewById(R.id.profile);
        progressLvl = (ProgressBar) findViewById(R.id.profileExp);
        textLvl = (TextView) findViewById(R.id.profileLvlName);
        textExp = (TextView) findViewById(R.id.profileLvl);
        textName = (TextView) findViewById(R.id.profileName);
        spinner = (Spinner) findViewById(R.id.spinner);

        profile.setVisibility(View.GONE);

        but1.setEnabled(true);
        but2.setEnabled(true);

        Cursor resP = dbP.getAllData();
        if(resP.moveToNext()) {
            ProfileTmp.id = resP.getString(0);
            ProfileTmp.name = resP.getString(1);
            ProfileTmp.lvl = Integer.parseInt(resP.getString(2));
            ProfileTmp.currentExp = Integer.parseInt(resP.getString(3));
            ProfileTmp.avatar = Integer.parseInt(resP.getString(4));
            profileList.add(new Profile(ProfileTmp.name, ProfileTmp.id, ProfileTmp.lvl, ProfileTmp.currentExp, ProfileTmp.avatar));
            lvl = ProfileTmp.lvl;
            exp = ProfileTmp.currentExp;
            nextExp = 100 + lvl * 2 * 100;
            ProfileTmp.nextExp = nextExp;
        }

        while(resP.moveToNext()){
            profileList.add(new Profile(
                    resP.getString(1),
                    resP.getString(0),
                    Integer.parseInt(resP.getString(2)),
                    Integer.parseInt(resP.getString(3)),
                    Integer.parseInt(resP.getString(4))));
        }

        if(ProfileTmp.name.length()<1){
            createProfile();
        }

        ArrayAdapter<Profile> adapter = new ArrayAdapter<Profile>(this, android.R.layout.simple_spinner_dropdown_item, profileList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Profile profile = (Profile) parent.getSelectedItem();
                ProfileTmp.id = profile.getId();
                ProfileTmp.name = profile.getName();
                ProfileTmp.lvl = profile.getLvl();
                ProfileTmp.currentExp = profile.getExp();
                ProfileTmp.avatar = profile.getAvatar();
                prepareProfile();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initRecycler();
        conScoreList.setVisibility(View.GONE);

        MobileAds.initialize(this,
                "ca-app-pub-3443485931200995~4334429354");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-3443485931200995/4470160729");
//        mInterstitialAd.loadAd(new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .addTestDevice("INSERT_YOUR_TEST_DEVICE_ID_HERE")
//                .build());
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        }
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        initRecycler();
        conScoreList.setVisibility(View.GONE);

        Cursor resP = dbP.getAllData();
        profileList.removeAll(profileList);
        while(resP.moveToNext()){
            profileList.add(new Profile(
                    resP.getString(1),
                    resP.getString(0),
                    Integer.parseInt(resP.getString(2)),
                    Integer.parseInt(resP.getString(3)),
                    Integer.parseInt(resP.getString(4))));
        }
        prepareProfile();
    }

    private void initRecycler() {
        scoreList = (RecyclerView) findViewById(R.id.scoreList);
        Collections.sort(ScoreList.scoreList);
        Collections.reverse(ScoreList.scoreList);
        MyAdapter adapter = new MyAdapter(getApplicationContext(), ScoreList.scoreList);
        scoreList.setAdapter(adapter);
        scoreList.setLayoutManager(new LinearLayoutManager(this));
    }

    public void start(View view) {
        startActivity(new Intent(this, GameActivity.class));
    }

    public void createProfile(){
        startActivity(new Intent(this, CreateProfile.class));
    }

    public void createProfileClick(View view){
        if(profileList.size()<5) {
            startActivity(new Intent(this, CreateProfile.class));
        }else {
            Toast.makeText(getApplicationContext(), "Posiadasz za dużo profili!", Toast.LENGTH_SHORT).show();
        }
    }

    public void scoreTable(View view) {
        navView.setSelectedItemId(R.id.navigation_notifications);
    }

    private void getScoreTable() {
        Cursor res = db.getAllData();

        while (res.moveToNext()) {
            tmpScoreList.add(new Score(res.getString(1), Integer.parseInt(res.getString(2))));
        }

        ScoreList.scoreList = tmpScoreList;

    }

    private void prepareProfile() {
        lvl = ProfileTmp.lvl;
        exp = ProfileTmp.currentExp;
        nextExp = ProfileTmp.nextExp;

        while (exp >= nextExp) {
            exp -= nextExp;
            nextExp = 100 + lvl * 2 * 100;
            lvl++;

            ProfileTmp.currentExp = exp;
            ProfileTmp.nextExp = nextExp;
            ProfileTmp.lvl = lvl;
            dbP.updateData(ProfileTmp.id, ProfileTmp.name, ProfileTmp.lvl, ProfileTmp.currentExp, ProfileTmp.avatar);
        }

        textLvl.setText("LVL: " + lvl);
        textExp.setText("EXP: " + exp + "/" + nextExp);
        double progres = (double) exp / (double) nextExp;
        int progress = (int) (progres * 100);
        progressLvl.setProgress(progress);
        textName.setText(ProfileTmp.name);
    }

    public void deleteProfile(View view){
        dbP.deleteData(ProfileTmp.id);
        Cursor resP = dbP.getAllData();
        if(resP.moveToNext()) {
            ProfileTmp.id = resP.getString(0);
            ProfileTmp.name = resP.getString(1);
            ProfileTmp.lvl = Integer.parseInt(resP.getString(2));
            ProfileTmp.currentExp = Integer.parseInt(resP.getString(3));
            ProfileTmp.avatar = Integer.parseInt(resP.getString(4));
            profileList.add(new Profile(ProfileTmp.name, ProfileTmp.id, ProfileTmp.lvl, ProfileTmp.currentExp, ProfileTmp.avatar));
            lvl = ProfileTmp.lvl;
            exp = ProfileTmp.currentExp;
            nextExp = 100 + lvl * 2 * 100;
            ProfileTmp.nextExp = nextExp;
        }else {
            createProfile();
        }
        prepareProfile();
    }

    private void addAds() {
        // add AdMob
        AdView adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-3443485931200995/4470160729");
        adView.setAdSize(AdSize.SMART_BANNER);

        LinearLayout.LayoutParams adLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        adView.setLayoutParams(adLayoutParams);

        LinearLayout layout = new LinearLayout(this);
        layout.addView(adView);
        layout.setGravity(Gravity.BOTTOM);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addContentView(layout, layoutParams);

        // load ad
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

}


