package com.example.quizesss.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.quizesss.controller.AppController;
import com.example.quizesss.model.Questions;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    ArrayList<Questions> questionsArrayList = new ArrayList<>();

    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    public List<Questions> getQuestions(final AnswerListAsyncResponse callback){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,url,null,
                response -> {
                    try {
                        Log.d("RES", "getQuestions: " + response.getJSONArray(0).getString(0));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for(int i = 0; i < response.length(); i++) {
                        try {
                            Questions question = new Questions(response.getJSONArray(i).getString(0), response.getJSONArray(i).getBoolean(1));

                            questionsArrayList.add(question);
                            Log.d("ques", "getQuestions: " + questionsArrayList);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if(null != callback){
                        callback.processFinished(questionsArrayList);
                    }
                },
                error -> {

                });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return questionsArrayList;

    }
}
