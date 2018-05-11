package com.thaopham.ask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.thaopham.ask.Login.LoginActivity;
import com.thaopham.ask.Login.SignUpActivity;
import com.thaopham.ask.Question.QuestionListAdapter;
import com.thaopham.ask.Question.QuestionMainActivity;

public class MainActivity extends AppCompatActivity {
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignUp = (Button) findViewById(R.id.btnToSignUp);
        btnSignUp.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent changeView = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(changeView);
            }
        });

        Button btnLogin = findViewById(R.id.btnToLogin);
        btnLogin.setOnClickListener(new OnClickListener(){
            public void onClick(View v)
            {
                Intent changeView = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(changeView);
            }
        });
    }
}
