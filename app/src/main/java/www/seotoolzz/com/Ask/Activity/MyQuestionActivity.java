package www.seotoolzz.com.Ask.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import www.seotoolzz.com.Ask.Model.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.seotoolzz.com.Ask.Adapter.QuestionListAdapter;

import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import www.seotoolzz.com.Ask.R;
import www.seotoolzz.com.Ask.RequestController.AsksController;

public class MyQuestionActivity extends AppCompatActivity {

    private ListView lvQuestion;
    private QuestionListAdapter adapter;
    private List<Question> myArrayQuestion;
    private boolean isLoading = true;
    private int currentPage = 1;
    private String getQuestionUrl = "https://laravel-demo-deploy.herokuapp.com/api/v0/questions/user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_question);
        myArrayQuestion = new ArrayList<>();
        lvQuestion = (ListView) findViewById(R.id.lvQuestion);
        if (myArrayQuestion.size() > 1) {
            adapter = new QuestionListAdapter(getApplicationContext(), myArrayQuestion);
            isLoading = false;
        } else {
            getQuestionList(1);
        }

        lvQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idQuestion = myArrayQuestion.get(position).getId() + "";
                Intent intent = new Intent(MyQuestionActivity.this, DetailQuestionActivity.class);
                intent.putExtra("id", String.valueOf(idQuestion));
                startActivity(intent);
            }
        });

        lvQuestion.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                if (firstItem + visibleItem == totalItem && totalItem != 0) {
                    if (!isLoading) {
                        isLoading = true;
                        currentPage += 1;
                        getQuestionList(currentPage);
                    }
                }
            }
        });

        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) findViewById(R.id.questionSwipe);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("SWIPE_LAYOUT", "onRefresh: Refresh");
                myArrayQuestion.clear();
                getQuestionList(1);
                swipeLayout.setRefreshing(false);
            }
        });
    }

    private void getQuestionList(int pageNo) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.getQuestionUrl + "?page=" + pageNo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getJSONObject("meta").getInt("status");

                    if (code == 700) {
                        // Get token and save in local storage
                        JSONArray data = res.getJSONArray("data");
                        Log.d("MQUES_1", data.toString());
                        if (data.length() > 0) {
                            for(int i = 0; i < data.length(); i++) {
                                JSONObject q;
                                q = data.getJSONObject(i);
                                String username = q.getJSONObject("user").getJSONObject("data").getString("username");
                                myArrayQuestion.add(new Question(
                                        q.getInt("id"),
                                        q.getString("title"),
                                        username,
                                        q.getInt("voteCount"),
                                        q.getString("updatedAt"),
                                        q.getInt("status")
                                ));
                            }
                            if (currentPage == 1) {
                                adapter = new QuestionListAdapter(getApplicationContext(), myArrayQuestion);
                                lvQuestion.setAdapter(adapter);
                            } else {
                                adapter.notifyDataSetChanged();
                            }
                        }
                        if (myArrayQuestion.size() == 0) {
                            Toast.makeText(MyQuestionActivity.this.getApplicationContext(), "You don't have any question", Toast.LENGTH_LONG).show();
                        }

                        isLoading = false;
                        Log.d("MQUES_2", isLoading + "");
                    } else {
                        Toast.makeText(MyQuestionActivity.this.getApplicationContext(), res.getJSONObject("meta").getJSONObject("message").getString("main"), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(MyQuestionActivity.this.getApplicationContext(), "Unknow error", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("VOLLEY_ERROR", "ERROR: " + error.getMessage());
                    Toast.makeText(MyQuestionActivity.this.getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences sharePrefs = MyQuestionActivity.this.getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE);
                params.put("Authorization", sharePrefs.getString("token", null));

                return params;
            }
        };
        AsksController.getmInstance(MyQuestionActivity.this).addToRequestQueue(stringRequest);
    }
}
