package com.example.reversequestions;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

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
    private ProgressBar progressLvl;
    private int lvl, exp, nextExp;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    home.setVisibility(View.VISIBLE);
                    homeText.setVisibility(View.VISIBLE);
                    conScoreList.setVisibility(View.GONE);
                    profile.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_dashboard:
                    prepareProfile();
                    home.setVisibility(View.GONE);
                    homeText.setVisibility(View.GONE);
                    conScoreList.setVisibility(View.GONE);
                    profile.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_notifications:
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

        profile.setVisibility(View.GONE);

        but1.setEnabled(true);
        but2.setEnabled(true);

        //TODO wczytywanie danych z bazy do profileTmp
        lvl = ProfileTmp.lvl;
        exp = ProfileTmp.currentExp;
        nextExp = ProfileTmp.nextExp;

        initRecycler();
        conScoreList.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initRecycler();
        conScoreList.setVisibility(View.GONE);
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
        }

        textLvl.setText("LVL: " + lvl);
        textExp.setText("EXP: " + exp + "/" + nextExp);
        double progres = (double) exp / (double) nextExp;
        int progress = (int) (progres * 100);
        progressLvl.setProgress(progress);
        textName.setText(ProfileTmp.name);
    }
}


