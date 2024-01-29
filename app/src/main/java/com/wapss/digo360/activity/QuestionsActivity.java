package com.wapss.digo360.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.wapss.digo360.R;
import com.wapss.digo360.adapter.QuestionAdapter;
import com.wapss.digo360.adapter.TopDiseaseAdapter2;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.interfaces.AnswerListener;
import com.wapss.digo360.interfaces.TopDiseaseListener2;
import com.wapss.digo360.response.QuestionResponse;
import com.wapss.digo360.response.TopDiseaseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionsActivity extends AppCompatActivity {
    TextView tv_save;
    ImageView back;
    RecyclerView rv_answerList;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String deviceToken;
    CustomProgressDialog progressDialog;
    List<QuestionResponse.DiseaseAnswer> questions;
    QuestionAdapter questionAdapter;
    TextView tv_question;
    private int questionCount = 0;
    TextView tv_count;
    LottieAnimationView iv_noQuestion;
    LinearLayout question;
    int quest;
    TextView txt_submit, tv_skip;
    String gender,diseaseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        if (savedInstanceState != null) {
            quest = savedInstanceState.getInt("question", 0);
            questionCount = quest - 1;
        }

        tv_save = findViewById(R.id.tv_save);
        back = findViewById(R.id.back);
        rv_answerList = findViewById(R.id.rv_answerList);
        tv_question = findViewById(R.id.tv_question);
        tv_count = findViewById(R.id.tv_count);
        iv_noQuestion = findViewById(R.id.iv_noQuestion);
        question = findViewById(R.id.question);
        txt_submit = findViewById(R.id.txt_submit);
        tv_skip = findViewById(R.id.tv_skip);
        progressDialog = new CustomProgressDialog(this);
        //shared Pref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        deviceToken = loginPref.getString("deviceToken", null);
        gender = loginPref.getString("gender",null);
        diseaseId = loginPref.getString("diseaseId",null);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionsActivity.this, SpecialActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callQuestionAPI(diseaseId, "null");
            }
        });
        txt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(QuestionsActivity.this, SpecialActivity.class);
                Intent intent = new Intent(QuestionsActivity.this, AfterQuestionActivity.class);
                startActivity(intent);
            }
        });

        callQuestionAPI(diseaseId, "null");
    }

    private void callQuestionAPI(String diseaseId, String optionId) {
        progressDialog.showProgressDialog();
        String Token = "Bearer " + deviceToken;
        Call<QuestionResponse> banner_apiCall = ApiService.apiHolders().QuestionAPI(Token, gender, diseaseId, optionId);
        banner_apiCall.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                if (response.code() == 404) {
                    progressDialog.dismiss();
                    iv_noQuestion.setVisibility(View.VISIBLE);
                    question.setVisibility(View.GONE);
                    tv_count.setVisibility(View.GONE);
                    rv_answerList.setVisibility(View.GONE);
                    tv_skip.setVisibility(View.GONE);
                    txt_submit.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.hideProgressDialog();
                            Intent intent = new Intent(QuestionsActivity.this, AfterQuestionActivity.class);
                            startActivity(intent);
                        }
                    }, 2000);
                    Toast.makeText(getApplicationContext(), "No Question", Toast.LENGTH_SHORT).show();
                } else if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    String question = response.body().getQuestion().getQuestion();
                    String questionId = response.body().getQuestion().getId();
                    tv_question.setText(question);
                    questions = response.body().getQuestion().getDiseaseAnswer();
                    questionCount++;
                    tv_count.setText("Question Number : " + " " + questionCount);
                    questionAdapter = new QuestionAdapter(getApplicationContext(), questions, questionId, new AnswerListener() {
                        @Override
                        public void onItemClickedItem(QuestionResponse.DiseaseAnswer item, int position, String questionId) {
                            String next = item.getNext();
                            // Toast.makeText(getApplicationContext(), item.getId(), Toast.LENGTH_SHORT).show();
                            String answerId = item.getId();
                            if (next.equals("YES")) {
                                callQuestionAPI(diseaseId, answerId);
                            } else {
                                callQuestionAPI(diseaseId, questionId);
                            }
                        }
                    });
                    rv_answerList.setAdapter(questionAdapter);
                    rv_answerList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                } else {
                    progressDialog.dismiss();
                    iv_noQuestion.setVisibility(View.VISIBLE);
                    question.setVisibility(View.GONE);
                    tv_count.setVisibility(View.GONE);
                    rv_answerList.setVisibility(View.GONE);
                    tv_skip.setVisibility(View.GONE);
                    txt_submit.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {
                progressDialog.dismiss();
                iv_noQuestion.setVisibility(View.VISIBLE);
                question.setVisibility(View.GONE);
                tv_count.setVisibility(View.GONE);
                rv_answerList.setVisibility(View.GONE);
                tv_skip.setVisibility(View.GONE);
                txt_submit.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("question", questionCount);
    }
}