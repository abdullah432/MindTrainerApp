package com.example.mindtrainerapplication;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView currentQuestion;
    TextView remainingTime;
    Button button0;
    Button button1;
    Button button2;
    Button button3;

    int correctAnswer;

    ArrayList<Integer> optionList;

    TextView resultField;
    TextView noOfQuestion;

    int noOfCorrectAnswer = 0;
    int totalQuestionAsk = 0;

    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentQuestion = findViewById(R.id.currentQuestion);
        remainingTime = findViewById(R.id.remainingTime);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        noOfQuestion = findViewById(R.id.noOfQuestion);
        result = String.valueOf(noOfCorrectAnswer)+"/"+String.valueOf(totalQuestionAsk);
        noOfQuestion.setText(result);

        questionGenerator();
        timer();
    }

    public void timer(){

        new CountDownTimer(10000,1000){
            @Override
            public void onTick(long m) {
                remainingTime.setText(String.valueOf(m/1000));
            }

            @Override
            public void onFinish() {
                result = String.valueOf("Score: "+noOfCorrectAnswer)+"/"+String.valueOf(totalQuestionAsk);
                resultField.setText(result);

                button0.setClickable(false);
                button1.setClickable(false);
                button2.setClickable(false);
                button3.setClickable(false);
            }
        }.start();
    }

    public void questionGenerator(){

        Random random = new Random();
        int first = random.nextInt(10);
        int second = random.nextInt(10);
        String question = first+" + "+second;

        currentQuestion.setText(question);

        correctAnswer = first + second;

        int incorrectAnswer;

        int correctAnswerLocation = random.nextInt(4);

        optionList = new ArrayList<>();

        for (int i=0; i<4; i++) {
            if (i == correctAnswerLocation)
                optionList.add(correctAnswer);
            incorrectAnswer = random.nextInt(20);
            while (correctAnswer == incorrectAnswer){
                incorrectAnswer = random.nextInt(20);
            }
            optionList.add(incorrectAnswer);
        }

            button0.setText(String.valueOf(optionList.get(0)));
            button1.setText(String.valueOf(optionList.get(1)));
            button2.setText(String.valueOf(optionList.get(2)));
            button3.setText(String.valueOf(optionList.get(3)));

    }

    public void nextQuestion(View view){

        resultField = findViewById(R.id.result);
        int tappedButton = Integer.parseInt(view.getTag().toString());
        if(correctAnswer == optionList.get(tappedButton)){
            resultField.setText("Correct");
            noOfCorrectAnswer ++;
        }
        else
            resultField.setText("Incorrect");

       /* Log.i("CorrectAnswer: ",String.valueOf(correctAnswer));
        Log.i("UserAnswer: ",String.valueOf(optionList.get(tappedButton)));*/

        totalQuestionAsk ++;

        noOfQuestion = findViewById(R.id.noOfQuestion);
        String noOfQue = String.valueOf(noOfCorrectAnswer)+"/"+String.valueOf(totalQuestionAsk);
        noOfQuestion.setText(noOfQue);

        questionGenerator();
    }

    public void playAgain(View view){
        noOfCorrectAnswer = 0;
        totalQuestionAsk = 0;

        button0.setClickable(true);
        button1.setClickable(true);
        button2.setClickable(true);
        button3.setClickable(true);

        questionGenerator();
        timer();

        resultField.setText(null);

    }
}
