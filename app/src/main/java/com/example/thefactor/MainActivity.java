package com.example.thefactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Vibrator;
import android.widget.Toast;


import java.util.Random;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    Vibrator v ;
    LinearLayout bg;
    int number = 0 , max_streak ,current_Streak =0,prev_number;
    int choice, counter,score =0;
    boolean OptionChosen = false;

    Button optionA, optionB, optionC;
    TextView time, answer,scoreview,max_Streak,curr_Streak;
    EditText value;
    CountDownTimer timer;

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

    }

    public void done(View view)
    {
        prev_number = number;
        number = Integer.parseInt(value.getText().toString());

        if(prev_number != number)
        {
            if(number > 0)
            {
                reset();
                factors();

            }
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        SharedPreferences sh
                = getSharedPreferences("MySharedPref",
                MODE_PRIVATE);

        max_streak = sh.getInt("streak",0);

        // Setting the fetched data
        // in the EditTexts
        max_Streak.setText(Integer.toString(max_streak));

    }


    @Override
    protected void onPause()
    {
        super.onPause();

        SharedPreferences sharedPreferences
                = getSharedPreferences("MySharedPref",
                MODE_PRIVATE);
        SharedPreferences.Editor myEdit
                = sharedPreferences.edit();

        myEdit.putInt("streak", max_streak);
        myEdit.apply();
    }


    public void factors() {
        Vector<Integer> factors = new Vector<>();
        Vector<Integer> non_factors = new Vector<>();


        for (int i = 1; i <= number; i++) {

            if (number % i == 0)
                factors.add(i);

            else
                non_factors.add(i);

        }

        if(number <= 6)
        {
            for(int i = 6;i<12;i++)
                non_factors.add(i);
        }



        Random rand = new Random();
        choice = rand.nextInt(3);

        int temporary;

        if (choice == 0) {

            temporary = factors.get(rand.nextInt(factors.size()));
            optionA.setText(Integer.toString(temporary));
        } else {
            temporary = non_factors.get(rand.nextInt(non_factors.size()));
            optionA.setText(Integer.toString(temporary));
            non_factors.removeElement(temporary);
        }


        if (choice == 1) {

            temporary = factors.get(rand.nextInt(factors.size()));
            optionB.setText(Integer.toString(temporary));
        } else {
            temporary = non_factors.get(rand.nextInt(non_factors.size()));
            optionB.setText(Integer.toString(temporary));
            non_factors.removeElement(temporary);
        }

        if (choice == 2) {

            temporary = factors.get(rand.nextInt(factors.size()));
            optionC.setText(Integer.toString(temporary));
        } else {
            temporary = non_factors.get(rand.nextInt(non_factors.size()));
            optionC.setText(Integer.toString(temporary));
            non_factors.removeElement(temporary);
        }

        counter = 0;
         timer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                time.setText(String.valueOf(counter));
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
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                score = 0;
                scoreview.setText(" " + score);

                value.setText("5");
                value.setHint("Enter a number ");
                optionA.setText("A");
                optionB.setText("B");
                optionC.setText("C");

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











