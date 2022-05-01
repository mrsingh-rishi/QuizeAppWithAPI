package com.example.quizesss;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.quizesss.controller.AppController;
import com.example.quizesss.data.AnswerListAsyncResponse;
import com.example.quizesss.data.Repository;
import com.example.quizesss.databinding.ActivityMainBinding;
import com.example.quizesss.model.Questions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private int currQuesInd = 0;

    List<Questions> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        questions = new Repository().getQuestions(questionsArrayList ->{
                    binding.questionText.setText(questionsArrayList.get(currQuesInd).getAnswer());
                    binding.outOfText.setText("Question: " + (currQuesInd+1) + "/" + questionsArrayList.size());
                }
        );

        binding.nextButton.setOnClickListener(view -> {
            currQuesInd = (currQuesInd + 1) % questions.size();
            updateQuestion();

        });
        binding.trueButton.setOnClickListener(view -> {
            checkAnswer(true);
            updateQuestion();

        });
        binding.falseButton.setOnClickListener(view -> {
            checkAnswer(false);
            updateQuestion();

        });
    }

    public void nextQues(View view) {
        currQuesInd = (currQuesInd + 1) % questions.size();
        updateQuestion();
    }

    private void updateQuestion() {
        String question  = questions.get(currQuesInd).getAnswer();
        binding.questionText.setText(question);
        binding.outOfText.setText("Question: " + (currQuesInd+1) + "/" + questions.size());
    }

    private void checkAnswer(boolean user){
        boolean answer = questions.get(currQuesInd).getAnswerTrue();
        int snackMessageId = 0;
        if( answer== user){
            snackMessageId = R.string.correct_answer;
            fadeAnimation();
        }
        else{
            snackMessageId = R.string.incorrect_answer;
            shakeAnimation();
        }
        Snackbar.make(binding.cardView, snackMessageId,Snackbar.LENGTH_LONG).show();
    }


    private void fadeAnimation(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        binding.cardView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionText.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionText.setTextColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    void shakeAnimation(){
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,R.anim.shake_animation);
        binding.cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionText.setTextColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionText.setTextColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}