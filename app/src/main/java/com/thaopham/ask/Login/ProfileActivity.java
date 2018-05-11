package com.thaopham.ask.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.thaopham.ask.Ask.Ask_Controller;
import com.thaopham.ask.Ask.SharedPrefmanger;
import com.thaopham.ask.Ask.User;
import com.thaopham.ask.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    TextView textViewId, textViewUsername, textViewEmail;
    String urlUser = "https://laravel-demo-deploy.herokuapp.com/api/v0/auth/user";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences sharePrefs = getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE);

        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewId.setText(sharePrefs.getString("token", null));

        Button btn = (Button) findViewById(R.id.buttonLogout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProfile();
            }
        });
    }

    public void getProfile()
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
                Map<String, String> params = new HashMap<>();
                SharedPreferences sharePrefs = getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE);
                params.put("Authorization", "Bearer " +  sharePrefs.getString("token", null));
                return params;
            }
        };
        Ask_Controller.getmInstance(this).addToRequestQueue(stringRequest);
    }
}
