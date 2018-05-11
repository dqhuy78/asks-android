package com.thaopham.ask.Question;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.thaopham.ask.Ask.Ask_Controller;
import com.thaopham.ask.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateQuestionActivity extends AppCompatActivity {
    EditText edtTitle, edtTag, edtQues;
    String urlCreatQues = "https://laravel-demo-deploy.herokuapp.com/api/v0/questions";
    String urlUpdateQues = "https://laravel-demo-deploy.herokuapp.com/api/v0/questions/update";
    String urlRemoveQues = "https://laravel-demo-deploy.herokuapp.com/api/v0/questions/remove";
    String urlChangeSttQues = "https://laravel-demo-deploy.herokuapp.com/api/v0/questions/change-status";

    protected void onCreate(Bundle savedInsatanceState) {
        super.onCreate(savedInsatanceState);
        setContentView(R.layout.activity_creat_new_question);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button btnPublish = (Button) findViewById(R.id.button);
        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeView = new Intent(CreateQuestionActivity.this, DetailQuestionActivity.class);
                CreateQuestionActivity.this.startActivity(changeView);
            }
        });

        edtTitle = (EditText) findViewById(R.id.editTitle);
        edtTag = (EditText) findViewById(R.id.editTag);
        edtQues = (EditText) findViewById(R.id.editQues);
    }

    private void CreateQuestion() {
        final String title = edtTitle.getText().toString().trim();
        final String tag = edtTag.getText().toString().trim();
        final String question = edtQues.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlCreatQues, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONObject meta = obj.getJSONObject("meta");
                    int status = meta.getInt("status");
                    if (status == 700) {

                    } else {
                        Toast.makeText(getApplicationContext(), obj.getJSONObject("meta").getJSONObject("message").getString("main"), Toast.LENGTH_SHORT).show();
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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("tags", tag);
                params.put("question", question);
                return params;
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences sharePrefs = getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE);
                params.put("Authorization", sharePrefs.getString("token", null));

                return params;
            }
        };
        Ask_Controller.getmInstance(this).addToRequestQueue(stringRequest);
    }

    private void UpdateQuestion() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateQues, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONObject meta = obj.getJSONObject("meta");
                    int stt = meta.getInt("status");
                    if (stt != 700) {
                        Toast.makeText(getApplicationContext(), meta.getJSONObject("message").getString("main"), Toast.LENGTH_SHORT).show();
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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences sharePrefs = getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE);
                params.put("Authorization", sharePrefs.getString("token", null));

                return params;
            }
        };
        Ask_Controller.getmInstance(this).addToRequestQueue(stringRequest);
    }

    private void RemoveQuestion() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlRemoveQues, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONObject meta = obj.getJSONObject("meta");
                    int stt = meta.getInt("status");
                    if (stt != 700) {
                        Toast.makeText(getApplicationContext(), meta.getJSONObject("message").getString("main"), Toast.LENGTH_SHORT).show();
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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences sharePrefs = getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE);
                params.put("Authorization", sharePrefs.getString("token", null));

                return params;
            }
        };
        Ask_Controller.getmInstance(this).addToRequestQueue(stringRequest);
    }

    private void ChangeSttQuestion() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlChangeSttQues, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONObject meta = obj.getJSONObject("meta");
                    int stt = meta.getInt("status");
                    if (stt != 700) {
                        Toast.makeText(getApplicationContext(), meta.getJSONObject("message").getString("main"), Toast.LENGTH_SHORT).show();
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
        });
        //}
        Ask_Controller.getmInstance(this).addToRequestQueue(stringRequest);
    }

}
