package com.example.reversequestions;

import android.app.Activity;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends Activity {


    AlertDialog alert;

    private TextView mTextMessage;
    private Chronometer stoper;
    private ConstraintLayout pytanie;
    private ConstraintLayout odpowiedzi;
    private Button odpowiedz1;
    private Button odpowiedz2;
    private TextView question;
    private TextView questCount;
    private EditText name;
    private String ans1, ans2;
    private long base;
    private int goodAns = 1;
    private int wynik = 0;
    private final String result = "Punkty: ";
    private int currentQuest = 1;
    private Random rand = new Random();
    private QuestBase questBase = new QuestBase();
    private int r;
    private int reverse;
    private MediaPlayer goodSound, wrongSound, tictacSound;
    private ScoreDB db;
    private ProfileDB dbP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        db = new ScoreDB(this);
        dbP = new ProfileDB(this);

        pytanie = (ConstraintLayout) findViewById(R.id.pyt1);
        odpowiedzi = (ConstraintLayout) findViewById(R.id.pyt2);
        mTextMessage = findViewById(R.id.message);
        odpowiedz1 = (Button) findViewById(R.id.button1);
        odpowiedz2 = (Button) findViewById(R.id.button2);
        question = (TextView) findViewById(R.id.pytanie);
        questCount = (TextView) findViewById(R.id.questCount);
        name = (EditText) findViewById(R.id.editText);

        stoper = (Chronometer) findViewById(R.id.stoper);
        stoper.setFormat("%s");

        stoper.setVisibility(View.GONE);
        pytanie.setVisibility(View.GONE);
        odpowiedzi.setVisibility(View.GONE);

        goodSound = MediaPlayer.create(this, R.raw.correct);
        wrongSound = MediaPlayer.create(this, R.raw.wrong);
        tictacSound = MediaPlayer.create(this, R.raw.tictac);

        questCount.setText(result+wynik);
        getNewQuestion();
        startQuest();
//        startStoper();
    }

    public void startStoper(){
        stoper.setBase(SystemClock.elapsedRealtime());
        stoper.start();

        stoper.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 1000) {
                    odpowiedz1.setEnabled(true);
                    odpowiedz2.setEnabled(true);
                        odpowiedz1.setText(ans1);
                        odpowiedz2.setText(ans2);
                    System.out.println("\n\nGood ans in clock: "+goodAns+"\n\n");
                }
                if((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 4000){
                        tictacSound.start();
                        resetStoper();
                        gameOver();
                }
            }
        });
    }

    public void resetStoper(){
        stoper.stop();
        stoper.setBase(SystemClock.elapsedRealtime());
    }

    public void clickAns1(View view){
        currentQuest++;
        resetStoper();
        if(goodAns == 0){
            goodSound.start();
            ProfileTmp.currentExp++;
            dbP.updateData(ProfileTmp.id, ProfileTmp.name, ProfileTmp.lvl, ProfileTmp.currentExp, ProfileTmp.avatar);
            wynik++;
                showScore("Brawo!");
            Toast.makeText(getApplicationContext(), "Dobra odpowiedź", Toast.LENGTH_SHORT).show();
        }else{
            wrongSound.start();
            gameOver();
            Toast.makeText(getApplicationContext(), "Zła odpowiedź", Toast.LENGTH_SHORT).show();
        }
    }

    public void clickAns2(View view){
        currentQuest++;
        resetStoper();
        if(goodAns == 1){
            goodSound.start();
            ProfileTmp.currentExp++;
            dbP.updateData(ProfileTmp.id, ProfileTmp.name, ProfileTmp.lvl, ProfileTmp.currentExp, ProfileTmp.avatar);
            wynik++;
                showScore("Brawo!");
            Toast.makeText(getApplicationContext(), "Dobra odpowiedź", Toast.LENGTH_SHORT).show();
        }else{
            wrongSound.start();
            gameOver();
            Toast.makeText(getApplicationContext(), "Zła odpowiedź", Toast.LENGTH_SHORT).show();
        }
    }

    public void showScore(String title) {
                        questCount.setText(result+wynik);
                        startStoper();
                        getNewQuestion();
    }

    public void gameOver() {
        odpowiedz1.setEnabled(false);
        odpowiedz2.setEnabled(false);
        ScoreList.scoreList.add(new Score(ProfileTmp.name, wynik));
        db.insertData(ProfileTmp.name, wynik, ProfileTmp.avatar);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("Kniec Gry");

        // set dialog message
        alertDialogBuilder
                .setMessage("Twoje osiągnięcia zostaną zapisane do tablicy wyników.\n\nWynik: "+wynik)
                .setCancelable(false)
                .setPositiveButton("Powrót do menu", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        GameActivity.this.finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void getNewQuestion(){
        r = rand.nextInt(questBase.questList.size());
        reverse = rand.nextInt(2);
        question.setText(questBase.questList.get(r).quest);
        odpowiedz1.setText("");
        odpowiedz2.setText("");
        odpowiedz1.setEnabled(false);
        odpowiedz2.setEnabled(false);
        goodAns = questBase.questList.get(r).goodAns;

        System.out.println("\n\nReverse: "+reverse+"\n\n");
        System.out.println("\n\nGood ans: "+goodAns+"\n\n");
        if(reverse == 1){
            System.out.println("\n\nIn revers IF\n\n");
            goodAns = 0;
            ans1 = questBase.questList.get(r).ans2;
            ans2 = questBase.questList.get(r).ans1;
        }else {
            System.out.println("\n\nIn revers ELSE\n\n");
            ans1 = questBase.questList.get(r).ans1;
            ans2 = questBase.questList.get(r).ans2;
            goodAns = questBase.questList.get(r).goodAns;
        }

        System.out.println("\n\nGood ans after if: "+goodAns+"\n\n");
    }

    public void startQuest(){
            pytanie.setVisibility(View.VISIBLE);
            odpowiedzi.setVisibility(View.VISIBLE);
            stoper.setVisibility(View.VISIBLE);
            startStoper();
    }

}
