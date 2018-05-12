package www.seotoolzz.com.Ask.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import www.seotoolzz.com.Ask.R;
import www.seotoolzz.com.Ask.RequestController.AsksController;

/**
 * Created by ngant on 4/16/2018.
 */

public class SignUpActivity extends AppCompatActivity{

    EditText edtUsername;
    EditText edtEmail;
    EditText edtPassword;
    EditText getEdtPasswordConf;
    private String signupUrl = "http://laravel-demo-deploy.herokuapp.com/api/v0/signup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        getEdtPasswordConf = (EditText) findViewById(R.id.edtPasswordConfirm);

        TextView txtToLogin = (TextView) findViewById(R.id.txtToLogin);
        txtToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent changeView = new Intent(SignUpActivity.this, MainActivity.class);
                SignUpActivity.this.startActivity(changeView);
            }
        });

        Button btnSignUp = (Button) findViewById(R.id.btnSignup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                signup();
            }
        });
    }

    private void signup() {
        final String username = this.edtUsername.getText().toString();
        final String email = this.edtEmail.getText().toString();
        final String password = this.edtPassword.getText().toString();
        final String passwordConf = this.getEdtPasswordConf.getText().toString();

        if (username.trim().length() < 1
                || email.trim().length() < 1
                || password.trim().length() < 1
                || passwordConf.trim().length() < 1) {
            Toast.makeText(getApplicationContext(), "Please fill all field", Toast.LENGTH_SHORT).show();
        } else if(!password.trim().equals(passwordConf.trim())) {
            Toast.makeText(getApplicationContext(), "Password confirm not match", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, this.signupUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject res = new JSONObject(response);
                        Log.d("SIGNUP_RES", res.toString());
                        int code = res.getJSONObject("meta").getInt("status");

                        if (code == 700) {
                            Toast.makeText(getApplicationContext(), res.getJSONObject("meta").getJSONObject("message").getString("main"), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), res.getJSONObject("meta").getJSONObject("message").getString("main"), Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.getMessage() == null) {
                        Log.d("VOLLEY_ERROR", "Unknow error");
                        Toast.makeText(getApplicationContext(), "Unknow error", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("VOLLEY_ERROR", "ERROR: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("username", username);
                    params.put("password", password);
                    params.put("password_confirm", passwordConf);

                    return params;
                }
            };
            AsksController.getmInstance(this).addToRequestQueue(stringRequest);
        }
    }
}
