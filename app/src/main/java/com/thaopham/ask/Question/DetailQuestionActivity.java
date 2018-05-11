package com.thaopham.ask.Question;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.thaopham.ask.Ask.Ask_Controller;
import com.thaopham.ask.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailQuestionActivity extends AppCompatActivity{

    String urlQuesPage = "https://laravel-demo-deploy.herokuapp.com/api/v0/questions";
    String urlQuesVote = "https://laravel-demo-deploy.herokuapp.com/api/v0/question-vote";
    String urlQuesList = "https://laravel-demo-deploy.herokuapp.com/api/v0/test/question";
    String urlQues = "https://laravel-demo-deploy.herokuapp.com/api/v0/questions/120";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_question);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void getQuestionByPage()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlQuesPage, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    Toast.makeText(getApplicationContext(), obj.toString(), Toast.LENGTH_SHORT).show();

//                    Object meta
                    int code = obj.getJSONObject("meta").getInt("status");
                    if (code == 700) {
                        Toast.makeText(getApplicationContext(), obj.getJSONObject("meta").getString("message"), Toast.LENGTH_SHORT).show();
                        JSONObject pag = obj.getJSONObject("meta");
                        int total = pag.getInt("total");
                        int count = pag.getInt("count");
                        int per_page = pag.getInt("per_page");
                        int total_page = pag.getInt("total_page");
                        String next = pag.getJSONObject("links").getString("next");

                        //                    Object data_lon - day la mang
                        JSONArray jsonArray = obj.getJSONArray("data");
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject object;
                            JSONObject user;
                            JSONObject tag;
                            JSONObject data2;
                            JSONArray data3;
                            JSONObject object1;
                            try {
                                object = jsonArray.getJSONObject(i);
                                int id_data = object.getInt("id");
                                String title = object.getString("title");
                                String content = object.getString("content");
                                int status = object.getInt("statust");
                                int viewcount = object.getInt("viewcount");
                                int votecount = object.getInt("votecount");
                                String createAt = object.getString("createAt");
                                String updateAt = object.getString("updateAt");
                                String deleteAt = object.getString("deleteAt");

//                            Object user
                                user = jsonArray.getJSONObject(i).getJSONObject("user");
                                data2 = user.getJSONObject("data");
                                int id_user = data2.getInt("id");
                                String username = data2.getString("username");
                                String email = data2.getString("email");
                                int status_2 = data2.getInt("status");
                                int role = data2.getInt("role");

//                            Object tags
                                tag = jsonArray.getJSONObject(i).getJSONObject("tag");
                                data3 = tag.getJSONArray("data");
                                for (int j = 0; j< data3.length(); i++){
                                    object1 = jsonArray.getJSONObject(i).getJSONObject("data");
                                    int id_tag = object1.getInt("id");
                                    String name = object1.getString("name");
                                }

                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }

                    }else {
                        Toast.makeText(getApplicationContext(), obj.getJSONObject("meta").getJSONObject("messgage").getString("main"), Toast.LENGTH_SHORT).show();
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
        Ask_Controller.getmInstance(this).addToRequestQueue(stringRequest);
    }

//-----------------------------------------------------------------
    private void getQuestionVoteList(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlQuesVote, new Response.Listener<String>() {
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
          });
        Ask_Controller.getmInstance(this).addToRequestQueue(stringRequest);
    }

    //-----------------------------------------------------------------

    private void getQuestionList(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlQuesList, new Response.Listener<String>() {
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
        });
        Ask_Controller.getmInstance(this).addToRequestQueue(stringRequest);
    }

    //-----------------------------------------------------------------

    private void getQuestion(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlQues, new Response.Listener<String>() {
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
        });
        Ask_Controller.getmInstance(this).addToRequestQueue(stringRequest);
    }
}

