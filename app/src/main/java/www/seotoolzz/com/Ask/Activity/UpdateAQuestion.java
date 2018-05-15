package www.seotoolzz.com.Ask.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import www.seotoolzz.com.Ask.R;
import www.seotoolzz.com.Ask.RequestController.AsksController;

public class UpdateAQuestion extends AppCompatActivity {

    private Button btnUpdate, btnCancel;
    private EditText edTitle;
    private EditText edTags;
    private EditText edContent;

    private String questionId;

    private String questionUrl = "https://laravel-demo-deploy.herokuapp.com/api/v0/questions/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_new_question);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.edTitle = (EditText)findViewById(R.id.edTitle);
        this.edTags = (EditText)findViewById(R.id.edTags);
        this.edContent = (EditText)findViewById(R.id.edQuestion);

        Intent recIntent = getIntent();
        questionId = recIntent.getStringExtra("id");
        getQuestion(questionId);

        btnUpdate = (Button)findViewById(R.id.btnPublish);
        btnUpdate.setText("Update");
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion(1);
            }
        });

        btnCancel = (Button) findViewById(R.id.btnDraf);
        btnCancel.setText("Cancel");
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeView = new Intent(UpdateAQuestion.this, MainActivity.class);
                startActivity(changeView);
            }
        });
    }

    private void getQuestion(String questionId)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.questionUrl + questionId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getJSONObject("meta").getInt("status");
                    if (code == 700) {
                        // Get token and save in local storage
                        JSONObject data = res.getJSONObject("data");
                        edTitle.setText(data.getString("title"));
                        edContent.setText(data.getString("content"));
                        JSONArray tags = data.getJSONObject("tags").getJSONArray("data");
                        String tagsString = "";
                        for (int i = 0; i < tags.length(); i++) {
                            JSONObject row = tags.getJSONObject(i);
                            int id = row.getInt("id");
                            String name = row.getString("name");
                            if(i==0) tagsString = name;
                            else tagsString += "," + name;
                        }

                        edTags.setText(tagsString);

                        Log.d("QUESTION_DETAIL_RES", data.toString());
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
        });
        AsksController.getmInstance(this).addToRequestQueue(stringRequest);
    }

    private void updateQuestion(int status) {
        final String title = this.edTitle.getText().toString();
        final String question = this.edContent.getText().toString();
        final String tagsString = this.edTags.getText().toString();
        final String[] tags = tagsString.split(",");
        final int questionStatus = status;

        if (title.trim().length() < 1 || question.trim().length() < 1) {
            Toast.makeText(getApplicationContext(), "Please fill at least title and questions field", Toast.LENGTH_LONG).show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, this.questionUrl + "update", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject res = new JSONObject(response);
                        Log.d("CREATE_QUESTION_RES", res.toString());
                        int code = res.getJSONObject("meta").getInt("status");

                        if (code == 700) {
                            Toast.makeText(getApplicationContext(), "Update success", Toast.LENGTH_LONG).show();
                            Intent changeView = new Intent(UpdateAQuestion.this, MainActivity.class);
                            startActivity(changeView);
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
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("title", title);
                    params.put("content", question);
                    params.put("status", String.valueOf(questionStatus));
                    params.put("id", String.valueOf(questionId));
                    for (int i = 0; i < tags.length; i++)
                    {
                        params.put("tags[]", tags[i]);
                    }

                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    SharedPreferences sharePrefs = UpdateAQuestion.this.getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE);
                    params.put("Authorization", sharePrefs.getString("token", null));

                    return params;
                }
            };
            AsksController.getmInstance(this).addToRequestQueue(stringRequest);
        }
    }
}
