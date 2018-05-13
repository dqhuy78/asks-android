package www.seotoolzz.com.Ask.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    Button btnPublish;
    EditText edTitle;
    EditText edTags;
    EditText edQuestion;

    private String questionUrl = "http://laravel-demo-deploy.herokuapp.com/api/v0/questions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_new_question);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.edTitle = (EditText)findViewById(R.id.edTitle);
        this.edTags = (EditText)findViewById(R.id.edTags);
        this.edQuestion = (EditText)findViewById(R.id.edQuestion);

        btnPublish = (Button)findViewById(R.id.btnPublish);
        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publish();
            }
        });
    }

    private void publish() {
        final String title = this.edTitle.getText().toString();
        final String tags = this.edTags.getText().toString();
        final String question = this.edQuestion.getText().toString();

        if (title.trim().length() < 1 || question.trim().length() < 1) {
            Toast.makeText(getApplicationContext(), "Please fill at least title and questions field", Toast.LENGTH_LONG).show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, this.questionUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject res = new JSONObject(response);
                        Log.d("CREATE_QUESTION_RES", res.toString());
                        int code = res.getJSONObject("meta").getInt("status");

                        if (code == 700) {
                            Toast.makeText(getApplicationContext(), "Publish success", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
    }
}
