package com.example.thefactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Vibrator;
import java.util.Random;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    Vibrator v ;
    LinearLayout bg;
    int number = 0 , max_streak ,current_Streak =0,prev_number,choice, counter,score =0;
    boolean OptionChosen = true;
    Button optionA, optionB, optionC;
    TextView time, answer,scoreview,max_Streak,curr_Streak;
    EditText value;
    CountDownTimer timer;
    int fixed = 0;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        optionA = (Button) findViewById(R.id.buttonA);
        optionB = (Button) findViewById(R.id.buttonB);
        optionC = (Button) findViewById(R.id.buttonC);
        scoreview = (TextView) findViewById(R.id.score);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        max_Streak = (TextView) findViewById(R.id.max_streak);
        curr_Streak = (TextView) findViewById(R.id.curr_streak);
        value = (EditText) findViewById(R.id.value);
        time = (TextView) findViewById(R.id.time);
        answer = (TextView) findViewById(R.id.Answer);
        bg = (LinearLayout) findViewById(R.id.bg);
        optionA.setVisibility(View.GONE);
        optionB.setVisibility(View.GONE);
        optionC.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("MyBoolean", true);
        savedInstanceState.putString("optionA", optionA.getText().toString());
        savedInstanceState.putInt("counter", counter);
        savedInstanceState.putString("MyString", "Welcome back to Android");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        boolean myBoolean = savedInstanceState.getBoolean("MyBoolean");
        optionA.setText(savedInstanceState.getString("optionA"));
        counter = savedInstanceState.getInt("counter");
        String myString = savedInstanceState.getString("MyString");
        optionA.setVisibility(View.VISIBLE);
        optionB.setVisibility(View.VISIBLE);
        optionC.setVisibility(View.VISIBLE);
    }


    public void done(View view) {

        optionA.setVisibility(View.VISIBLE);
        optionB.setVisibility(View.VISIBLE);
        optionC.setVisibility(View.VISIBLE);

        prev_number = number;


        if (OptionChosen == true) {
            number = Integer.parseInt(value.getText().toString());
            if (prev_number != number) {

                if (number > 0) {
                    counter = 0;
                    OptionChosen = false;
                    reset();
                    value.setFocusable(false);
                    factors();
                }
            }
        }
    }

    public void random(View view)
    {
        optionA.setVisibility(View.VISIBLE);
        optionB.setVisibility(View.VISIBLE);
        optionC.setVisibility(View.VISIBLE);
        Random rand = new Random();
        prev_number = number;

        if(OptionChosen == true) {
            number =  rand.nextInt(500);
            if (prev_number != number) {
                counter = 0;
                OptionChosen = false;
                value.setText(Integer.toString(number));
                if (number > 0) {
                    reset();
                    value.setFocusable(false);
                    factors();
                }

            }
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        max_streak = sh.getInt("streak",0);
        max_Streak.setText(Integer.toString(max_streak));
    }


    @Override
    protected void onPause()
    {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("streak", max_streak);
        myEdit.apply();
    }


    public void factors() {
        Random rand = new Random();
        choice = rand.nextInt(3);
        int temporary= rand.nextInt(number/5)+1;

        if (choice == 0) {
           while(number % temporary != 0)
               temporary++;
            optionA.setText(Integer.toString(temporary));
        } else {
            Log.i(TAG," "+temporary);
            while(number % temporary == 0)
                temporary++;
            optionA.setText(Integer.toString(temporary));
            fixed = temporary;
        }

        temporary = rand.nextInt(number/5) + 1;


        if (choice == 1) {
            while(number % temporary != 0)
            temporary++;
            optionB.setText(Integer.toString(temporary));
        } else {
            Log.i(TAG," "+temporary);
            while(number % temporary == 0  ) {
                temporary++;
                if (temporary == fixed)
                    temporary++;
            }
            optionB.setText(Integer.toString(temporary));
            fixed = temporary;
        }

        temporary= rand.nextInt(number/5)+1;

        if (choice == 2) {
            while(number % temporary != 0)
                temporary++;
            optionC.setText(Integer.toString(temporary));
        } else {
            Log.i(TAG," "+temporary);
            while(number % temporary == 0  ) {
                temporary++;
                if (temporary == fixed)
                    temporary++;
            }
            optionC.setText(Integer.toString(temporary));
        }

        counter = 0;
         timer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                time.setText(String.valueOf(10 - counter));
                counter++;
            }
            public void onFinish() {
                counter = 0;
                time.setText("TIME OUT!!!");
                showResult();
            }
        }.start();
    }


    public void OPTION_A(View view) {
        if (!OptionChosen) {
            if (choice == 0) {

                optionA.setBackgroundColor(Color.parseColor("#98FB98"));
                answer.setText("Correct Answer !!");
                timer.cancel();
                score++;
                scoreview.setText(" " +score);
                current_Streak++;
                background(1);

            }

            else {
                optionA.setBackgroundColor(Color.parseColor("#FF0000"));
                showAnswer();
                answer.setText("Wrong Answer !!");
                v.vibrate(VibrationEffect.createOneShot(1000,200 ));
                timer.cancel();
                current_Streak =0;
                background(0);
            }
            OptionChosen = true;
        }

        if(max_streak < current_Streak)
        {
            max_streak = current_Streak;
            max_Streak.setText(Integer.toString(max_streak));
        }

        curr_Streak.setText("Current STREAK : " + current_Streak);
    }

    public void OPTION_B(View view) {
        if (!OptionChosen) {
            if (choice == 1) {
                optionB.setBackgroundColor(Color.parseColor("#98FB98"));
                answer.setText("Correct Answer !!");
                timer.cancel();
                score++;
                scoreview.setText(" " + score);
                current_Streak++;
                background(1);
            }
            else {
                optionB.setBackgroundColor(Color.parseColor("#FF0000"));
                showAnswer();
                answer.setText("Wrong Answer !!");
                v.vibrate(VibrationEffect.createOneShot(1000,200 ));
                timer.cancel();
                current_Streak = 0;
                background(0);
            }
            OptionChosen = true;
        }

        if(max_streak < current_Streak)
        {
            max_streak = current_Streak;
            max_Streak.setText(Integer.toString(max_streak));
        }

        curr_Streak.setText("Current STREAK : " + current_Streak);
    }

    public void OPTION_C(View view) {
        if (!OptionChosen) {
            if (choice == 2) {
                optionC.setBackgroundColor(Color.parseColor("#98FB98"));
                answer.setText("Correct Answer !!");
                timer.cancel();
                score++;
                scoreview.setText(" " + score);
                current_Streak++;
                background(1);
            } else {
                optionC.setBackgroundColor(Color.parseColor("#FF0000"));
                showAnswer();
                answer.setText("Wrong Answer !!");
                v.vibrate(VibrationEffect.createOneShot(1000,200 ));
                timer.cancel();
                current_Streak =0;
                background(0);
            }

            OptionChosen = true;
        }

        if(max_streak < current_Streak)
        {
            max_streak = current_Streak;
            max_Streak.setText(Integer.toString( max_streak));
        }

        curr_Streak.setText("Current STREAK : " + current_Streak);
    }


    public void reset() {
        optionA.setBackgroundColor(Color.parseColor("#FFFFFF"));
        optionB.setBackgroundColor(Color.parseColor("#FFFFFF"));
        optionC.setBackgroundColor(Color.parseColor("#FFFFFF"));

        OptionChosen = false;
        answer.setText(" ");
    }

    public void showAnswer() {
        if (choice == 0)
            optionA.setBackgroundColor(Color.parseColor("#98FB98"));

        if (choice == 1)
            optionB.setBackgroundColor(Color.parseColor("#98FB98"));

        if (choice == 2)
            optionC.setBackgroundColor(Color.parseColor("#98FB98"));

        OptionChosen = true;

    }


    public void showResult()
    {
        answer.setText("Final Score = " + score);
        OptionChosen=true;
        value.setFocusableInTouchMode(true);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                score = 0;
                scoreview.setText(" " + score);

                value.setText(Integer.toString(number));
                value.setHint("Enter a number ");
                optionA.setVisibility(View.INVISIBLE);
                optionB.setVisibility(View.INVISIBLE);
                optionC.setVisibility(View.INVISIBLE);
                value.setHint("Enter a number");
                value.setText(null);

                answer.setText(" ");
                time.setText("0");
                curr_Streak.setText("Current STREAK : 0");
                scoreview.setText("0");

                    }
                });
            }}, 6000);

    }


    public void background(final int ans)
    {
        value.setFocusableInTouchMode(true);

        if(ans == 0)
        bg.setBackgroundColor(Color.parseColor("#FF0000"));

    else
        bg.setBackgroundColor(Color.parseColor("#98FB98"));


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                bg.setBackgroundColor(Color.parseColor("#FFFFFF"));

            }
        }, 1000);



    }



}











