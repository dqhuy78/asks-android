package com.thaopham.ask.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.thaopham.ask.Ask.SharedPrefmanger;
import com.thaopham.ask.Ask.User;
import com.thaopham.ask.R;

public class ProfileActivity extends AppCompatActivity {
    TextView textViewId, textViewUsername, textViewEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (!SharedPrefmanger.getmInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);

        //geting a current user
        User user = SharedPrefmanger.getmInstance(this).getUser();

        textViewId.setText(String.valueOf(user.getId()));
        textViewUsername.setText(user.getUsername());
        textViewEmail.setText(user.getEmail());

    }
}
