package com.thaopham.ask.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.thaopham.ask.Ask.Ask_Controller;
import com.thaopham.ask.Ask.SharedPrefmanger;
import com.thaopham.ask.Ask.User;
import com.thaopham.ask.MainActivity;
import com.thaopham.ask.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity{
    EditText edtUsername, edtEmail, edtPassword, edtPasswordConfirm;
    String urlSignup ="https://laravel-demo-deploy.herokuapp.com/api/v0/signup";

    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState){
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_sign_up);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView txtToLogin = (TextView) findViewById(R.id.txtToLogin);
        txtToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeView = new Intent(SignUpActivity.this, ProfileActivity.class);
                SignUpActivity.this.startActivity(changeView);
            }
        });

        if (SharedPrefmanger.getmInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
            return;
        }

        edtUsername = (EditText) findViewById(R.id.textViewUsername);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPasswordConfirm =(EditText) findViewById(R.id.edtPasswordConfirm);
    }

    private void findViewById() {
    }

    private void SignUp(){
        final String username = edtUsername.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        final String password = edtPassword.getText().toString().trim();
        final String password_confirm = edtPasswordConfirm.getText().toString().trim();

        if(TextUtils.isEmpty(username)){
           edtUsername.setError("Please enter username");
           edtUsername.requestFocus();
           return;
        }

        if(TextUtils.isEmpty(email)){
            edtEmail.setError("Please enter your email");
            edtEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("Enter a valid email");
            edtEmail.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(password)){
            edtPassword.setError("Please enter your password");
            edtPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password_confirm)){
            edtPasswordConfirm.setError("Please confirm your password");
            edtPasswordConfirm.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSignup, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);

                    //neu khong co loi
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        JSONObject userJson = obj.getJSONObject("user");
                        //tao moi nguoi dung
                        User user = new User(userJson.getInt("id"), userJson.getString("username"), userJson.getString("email"), userJson.getString("password"), userJson.getString("password_confirm"));

                        //luu lai trong shared preferences
                        SharedPrefmanger.getmInstance(getApplicationContext()).userLogin(user);

                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String,String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                params.put("password_confirm", password_confirm);
                return params;
            }
        };
        Ask_Controller.getmInstance(this).addToRequestQueue(stringRequest);
    }
}
