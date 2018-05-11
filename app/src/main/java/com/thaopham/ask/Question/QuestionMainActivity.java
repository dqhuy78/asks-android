package com.thaopham.ask.Question;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.thaopham.ask.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionMainActivity extends AppCompatActivity {
    private static final String question_list_url = "https://laravel-demo-deploy.herokuapp.com/api/v0/questions";

    //listview object
    ListView listView;
    List<Question> myQuestion;

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.listview_question);

        listView = (ListView) findViewById(R.id.lv_question);
        myQuestion = new ArrayList<>();

        loadMyQuestion();
    }
    private void loadMyQuestion(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, question_list_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray questionArr = obj.getJSONArray("questions");
                    for (int i = 0; i < questionArr.length(); i++) {
                        //lay index cua question trong arr
                        JSONObject questionObj = questionArr.getJSONObject(i);

                        //tao moi doi tuong question va them vao gia tri tu json obj
                        Question question = new Question(questionObj.getInt("id"), questionObj.getString("title"), questionObj.getString("content"), questionObj.getString("tag"), questionObj.getString("username"), questionObj.getString("votenumber"));
                        myQuestion.add(question);
                    }
                    QuestionListAdapter adapter = new QuestionListAdapter(myQuestion, getApplicationContext());
                    listView.setAdapter(adapter);
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
