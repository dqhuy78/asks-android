package www.seotoolzz.com.Ask.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
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

public class CreatNewQuestion extends AppCompatActivity {

    Button btnPublish, btnDraft;
    EditText edTitle;
    EditText edQuestion;
    private boolean isLoading = false;
    private FrameLayout layoutLoading;

    private String questionUrl = "http://laravel-demo-deploy.herokuapp.com/api/v0/questions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_new_question);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.edTitle = (EditText)findViewById(R.id.edTitle);
        this.edQuestion = (EditText)findViewById(R.id.edQuestion);

        btnPublish = (Button)findViewById(R.id.btnPublish);
        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLoading) {
                    isLoading = true;
                    layoutLoading.setVisibility(View.VISIBLE);
                    createQuestion(1);
                }
            }
        });
        layoutLoading = (FrameLayout) findViewById(R.id.layoutLoading);

        btnDraft = (Button) findViewById(R.id.btnDraf);
        btnDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLoading) {
                    isLoading = true;
                    layoutLoading.setVisibility(View.VISIBLE);
                    createQuestion(0);
                }

            }
        });
    }

    private void createQuestion(int status) {
        final String title = this.edTitle.getText().toString();
        final String question = this.edQuestion.getText().toString();
        final int questionStatus = status;

        if (title.trim().length() < 1 || question.trim().length() < 1) {
            Toast.makeText(getApplicationContext(), "Please fill at least title and questions field", Toast.LENGTH_LONG).show();
            isLoading = false;
            layoutLoading.setVisibility(View.INVISIBLE);
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, this.questionUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject res = new JSONObject(response);
                        Log.d("CREATE_QUESTION_RES", res.toString());
                        int code = res.getJSONObject("meta").getInt("status");
                        isLoading = false;
                        layoutLoading.setVisibility(View.INVISIBLE);
                        if (code == 700) {
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                            Intent changeView = new Intent(CreatNewQuestion.this, MainActivity.class);
                            startActivity(changeView);
                        } else {
                            Toast.makeText(getApplicationContext(), res.getJSONObject("meta").getJSONObject("message").getString("main"), Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        isLoading = false;
                        layoutLoading.setVisibility(View.INVISIBLE);
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    isLoading = false;
                    layoutLoading.setVisibility(View.INVISIBLE);
                    if (error.getMessage() == null) {
                        Log.d("VOLLEY_ERROR", "Unknow error");
                        Toast.makeText(getApplicationContext(), "Unknow error", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("VOLLEY_ERROR", "ERROR: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("title", title);
                    params.put("content", question);
                    params.put("status", String.valueOf(questionStatus));

                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    SharedPreferences sharePrefs = CreatNewQuestion.this.getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE);
                    params.put("Authorization", sharePrefs.getString("token", null));

                    return params;
                }
            };
            AsksController.getmInstance(this).addToRequestQueue(stringRequest);
        }
    }
}
