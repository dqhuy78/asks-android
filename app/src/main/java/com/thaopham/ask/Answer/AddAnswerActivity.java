package com.thaopham.ask.Answer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.thaopham.ask.Question.DetailQuestionActivity;
import com.thaopham.ask.R;

public class AddAnswerActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_answer);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button pushAnswer = (Button) findViewById(R.id.btnPushAnswer);
        pushAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DetailQuestionActivity.class);
                startActivity(intent);
            }
        });

        Button drafAnswer = (Button)findViewById(R.id.btnDraf);
        drafAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DetailQuestionActivity.class);
                startActivity(intent);
            }
        });
        }
}
