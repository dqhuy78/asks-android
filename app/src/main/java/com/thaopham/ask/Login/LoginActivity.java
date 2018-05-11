package com.thaopham.ask.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.thaopham.ask.Ask.Ask_Controller;
import com.thaopham.ask.MainActivity;
import com.thaopham.ask.Question.DetailQuestionActivity;
import com.thaopham.ask.Question.QuestionListAdapter;
import com.thaopham.ask.R;
import com.thaopham.ask.Ask.SharedPrefmanger;
import com.thaopham.ask.Ask.User;
import com.thaopham.ask.ThirdFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static android.util.Base64.encodeToString;
import static java.util.Base64.getEncoder;

public class LoginActivity extends AppCompatActivity {
    TextView txtToSignup;
    EditText edtEmail, edtPassword;
    //    ProgressBar progressBar;
    String urlLogin = "https://laravel-demo-deploy.herokuapp.com/api/v0/auth/login";
    String urlUser = "https://laravel-demo-deploy.herokuapp.com/api/v0/auth/user";

    private static String TAG = MainActivity.class.getSimpleName();
    private Button btnToLogin;

    protected void onCreate(final Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.login_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharePrefs = getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE);
        if (sharePrefs.getString("token", null) != null) {
            //Toast.makeText(getApplicationContext(), sharePrefs.getString("token", null), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, ProfileActivity.class));
        }

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        findViewById(R.id.btnToLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getProfile();
            }
        });
        txtToSignup = (TextView) findViewById(R.id.txtToSignup);
        txtToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeview = new Intent (LoginActivity.this, SignUpActivity.class);
                LoginActivity.this.startActivity(changeview);
            }
        });
    }

    private void userLogin() {
        final String email = edtEmail.getText().toString();
        final String password = edtPassword.getText().toString();

        //xac nhan dau vao
        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Please enter your email");
            edtEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Please enter your password");
            edtPassword.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlLogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray obj = new JSONArray(response);
                    JSONObject res = (JSONObject) obj.get(0);
                    int code = res.getJSONObject("meta").getInt("status");
                    if (code == 700) {
                        String token = res.getJSONObject("data").getString("refresh_token");
                        SharedPreferences.Editor sharePrefs = getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE).edit();
                        sharePrefs.putString("token", token);
                        sharePrefs.commit();
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), res.getJSONObject("meta").getJSONObject("message").getString("main"), Toast.LENGTH_SHORT).show();
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        Ask_Controller.getmInstance(this).addToRequestQueue(stringRequest);
    }

   private void getProfile()
   {
       StringRequest stringRequest = new StringRequest(Request.Method.GET, urlUser, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               try {
                   JSONObject res = new JSONObject(response);
                   Toast.makeText(getApplicationContext(), res.toString(), Toast.LENGTH_SHORT).show();
                   int code = res.getJSONObject("meta").getInt("status");
                   if (code == 700) {
                       Toast.makeText(getApplicationContext(), res.toString(), Toast.LENGTH_SHORT).show();
                   } else {
                       Toast.makeText(getApplicationContext(), res.getJSONObject("meta").getJSONObject("message").getString("main"), Toast.LENGTH_SHORT).show();
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
       }) {
           @Override
           public Map<String, String> getHeaders() throws AuthFailureError {
               Map<String, String> params = new HashMap<String, String>();
               SharedPreferences sharePrefs = getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE);
               params.put("Authorization", sharePrefs.getString("token", null));

               return params;
           }
       };
       Ask_Controller.getmInstance(this).addToRequestQueue(stringRequest);
   }

    private class NO_WRAP {
    }
}
