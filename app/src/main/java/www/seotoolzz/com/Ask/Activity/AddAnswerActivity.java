package www.seotoolzz.com.Ask.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

public class AddAnswerActivity extends AppCompatActivity {

    private String questionId;
    private EditText edtContent;
    private String createAnswerUrl = "https://laravel-demo-deploy.herokuapp.com/api/v0/answers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_answer);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent recIntent = getIntent();
        questionId = recIntent.getStringExtra("id");

        edtContent = (EditText) findViewById(R.id.editAnswer);

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        Button postAnswer = (Button) findViewById(R.id.btnPushAnswer);
        postAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postQuestion(questionId);
            }
        });
    }

    private void postQuestion(String questionId)
    {
        final String id = questionId;
        final String content = edtContent.getText().toString();

        if (content.trim().length() < 1) {
            Toast.makeText(getApplicationContext(), "Please fill your answer", Toast.LENGTH_LONG).show();
        } else {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, this.createAnswerUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject res = new JSONObject(response);
                        Log.d("CREATE_QUESTION_RES", res.toString());
                        int code = res.getJSONObject("meta").getInt("status");

                        if (code == 700) {
                            Toast.makeText(getApplicationContext(), "Create answer success", Toast.LENGTH_LONG).show();
                            Intent changeView = new Intent(AddAnswerActivity.this, DetailQuestionActivity.class);
                            changeView.putExtra("id", id);
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
                    params.put("questions_id", id);
                    params.put("content", content);

                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    SharedPreferences sharePrefs = AddAnswerActivity.this.getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE);
                    params.put("Authorization", sharePrefs.getString("token", null));

                    return params;
                }
            };
            AsksController.getmInstance(this).addToRequestQueue(stringRequest);
        }
    }
}
