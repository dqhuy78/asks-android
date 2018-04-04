package com.example.dqhuy78.asks_test_1;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtToSignup = findViewById(R.id.txtToSignup);
        txtToSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent changeView = new Intent(MainActivity.this, SignUpActivity.class);
                MainActivity.this.startActivity(changeView);
            }
        });

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent changeView = new Intent(MainActivity.this, CreateQuestionActivity.class);
                MainActivity.this.startActivity(changeView);
            }
        });
    }
}
