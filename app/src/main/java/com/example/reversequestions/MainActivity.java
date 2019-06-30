package com.example.reversequestions;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout home;
    private TextView homeText;
    private RecyclerView scoreList;
    private BottomNavigationView navView;
    private ImageButton but1;
    private ImageButton but2;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    home.setVisibility(View.VISIBLE);
                    homeText.setVisibility(View.VISIBLE);
                    scoreList.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_dashboard:
                    home.setVisibility(View.GONE);
                    homeText.setVisibility(View.GONE);
                    scoreList.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_notifications:
                    home.setVisibility(View.GONE);
                    homeText.setVisibility(View.GONE);
                    scoreList.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecycler();
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        home = (ConstraintLayout) findViewById(R.id.constraintLayout);
        homeText = (TextView) findViewById(R.id.textView);
        but1 = (ImageButton) findViewById(R.id.imageButton);
        but2 = (ImageButton) findViewById(R.id.imageButton2);

        scoreList.setVisibility(View.GONE);

        but1.setEnabled(true);
        but2.setEnabled(true);
    }

    private void initRecycler(){
        scoreList = (RecyclerView) findViewById(R.id.scoreList);
        MyAdapter adapter = new MyAdapter(ScoreList.scoreList);
        scoreList.setAdapter(adapter);
        scoreList.setLayoutManager(new LinearLayoutManager(this));
    }

    public void start(View view) {
        startActivity(new Intent(this, GameActivity.class));
    }

    public void scoreTable(View view) {
        navView.setSelectedItemId(R.id.navigation_notifications);
    }
}


