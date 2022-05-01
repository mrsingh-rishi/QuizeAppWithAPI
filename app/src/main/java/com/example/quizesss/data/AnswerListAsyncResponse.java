package com.example.quizesss.data;

import com.example.quizesss.model.Questions;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {
    void processFinished(ArrayList<Questions> questionsArrayList);
}
