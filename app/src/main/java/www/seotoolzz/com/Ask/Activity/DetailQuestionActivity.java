package www.seotoolzz.com.Ask.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import www.seotoolzz.com.Ask.Helper.Helper;
import www.seotoolzz.com.Ask.R;
import www.seotoolzz.com.Ask.RequestController.AsksController;
import www.seotoolzz.com.Ask.Model.Answer;
import www.seotoolzz.com.Ask.Adapter.AnswerListAdapter;
import android.widget.LinearLayout;

public class DetailQuestionActivity extends AppCompatActivity {

    private ListView lvAnswer;
    private AnswerListAdapter adapter;
    private List<Answer> myArrayAnswer;
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvUserName;
    private TextView tvNumberVote;
    private TextView tvDate;
    private String questionId;
    private int previousVote;
    private LinearLayout btnLayout;
    private String getQuestionUrl = "https://laravel-demo-deploy.herokuapp.com/api/v0/questions/";
    private String voteUrl = "https://laravel-demo-deploy.herokuapp.com/api/v0/questions/vote";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_question);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvTitle = (TextView) findViewById(R.id.txtTitle);
        tvContent = (TextView) findViewById(R.id.txtContent);
        tvUserName = (TextView) findViewById(R.id.txtUserName);
        tvNumberVote = (TextView) findViewById(R.id.txtVoteNumber);
        tvDate = (TextView) findViewById(R.id.tvDate);
        btnLayout = (LinearLayout) findViewById(R.id.btnLayout);

        this.lvAnswer = (ListView)findViewById(R.id.listAnswer);
        myArrayAnswer = new ArrayList<>();

        myArrayAnswer.add(new Answer(1,"good","Huy","1","2/5/2018"));
        myArrayAnswer.add(new Answer(1,"good","Thao","2","2/5/2018"));

        myArrayAnswer.add(new Answer(1,"good","Huy","1","2/5/2018"));
        myArrayAnswer.add(new Answer(1,"good","Thao","2","2/5/2018"));

        adapter = new AnswerListAdapter(lvAnswer.getContext(),myArrayAnswer);
        lvAnswer.setAdapter(adapter);
        Intent recIntent = getIntent();
        questionId = recIntent.getStringExtra("id");
        getQuestion(questionId);



        Button addAnswer = (Button) findViewById(R.id.btnAddAnswer);
        addAnswer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Helper.isLogin(DetailQuestionActivity.this)) {
                    Toast.makeText(getApplicationContext(),
                            "Please login before do this",
                            Toast.LENGTH_LONG
                    ).show();
                } else {
                    Intent intent = new Intent(DetailQuestionActivity.this, AddAnswerActivity.class);
                    intent.putExtra("id", String.valueOf(questionId));
                    startActivity(intent);
                }
            }
        });

        Button btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        Button btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        ImageButton btnUpVote = (ImageButton) findViewById(R.id.upVote);
        btnUpVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Helper.isLogin(DetailQuestionActivity.this)) {
                    Toast.makeText(getApplicationContext(),
                            "Please login before do this",
                            Toast.LENGTH_LONG
                    ).show();
                } else {
                    updateVote(1);
                }
            }
        });

        ImageButton btnDownVote = (ImageButton) findViewById(R.id.downVote);
        btnDownVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Helper.isLogin(DetailQuestionActivity.this)) {
                    Toast.makeText(getApplicationContext(),
                            "Please login before do this",
                            Toast.LENGTH_LONG
                    ).show();
                } else {
                    updateVote(-1);
                }
            }
        });
    }

    private void getQuestion(String questionId)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.getQuestionUrl + questionId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            try {
                JSONObject res = new JSONObject(response);
                int code = res.getJSONObject("meta").getInt("status");
                if (code == 700) {
                    // Get token and save in local storage
                    JSONObject data = res.getJSONObject("data");
                    tvTitle.setText(data.getString("title"));
                    tvContent.setText(data.getString("content"));
                    tvNumberVote.setText(String.valueOf(data.getInt("voteCount")));
                    if (data.getInt("status") == 2) {
                        tvNumberVote.setBackgroundColor(Color.parseColor("#00C853"));
                        tvNumberVote.setTextColor(Color.WHITE);
                    }
                    tvDate.setText(data.getString("updatedAt"));
                    tvUserName.setText("Asks by: " + data.getJSONObject("user").getJSONObject("data").getString("username"));
                    previousVote = data.getInt("voteCount");
                    SharedPreferences sharePrefs = DetailQuestionActivity.this.getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE);
                    int userId = sharePrefs.getInt("userId", 0);
                    if (userId == data.getJSONObject("user").getJSONObject("data").getInt("id")) {
                        btnLayout.setVisibility(View.VISIBLE);
                    }
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

    private void updateVote(int value)
    {
        final int inputValue = value;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, this.voteUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    Log.d("LOGIN_RES", res.toString());
                    int code = res.getJSONObject("meta").getInt("status");
                    if (code == 700) {
                        int newValue = previousVote + inputValue;
                        tvNumberVote.setText(String.valueOf(newValue));
                        Toast.makeText(getApplicationContext(), "Vote success", Toast.LENGTH_LONG).show();
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
                params.put("value", String.valueOf(inputValue));
                params.put("id", String.valueOf(questionId));

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences sharePrefs = DetailQuestionActivity.this.getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE);
                params.put("Authorization", sharePrefs.getString("token", null));

                return params;
            }
        };
        AsksController.getmInstance(this).addToRequestQueue(stringRequest);
    }
}
