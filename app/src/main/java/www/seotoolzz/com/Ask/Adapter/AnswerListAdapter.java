package www.seotoolzz.com.Ask.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import java.util.List;
import java.util.Map;
import www.seotoolzz.com.Ask.Activity.DetailQuestionActivity;
import www.seotoolzz.com.Ask.R;
import www.seotoolzz.com.Ask.Model.Answer;
import www.seotoolzz.com.Ask.RequestController.AsksController;

public class AnswerListAdapter extends BaseAdapter {
    private Context myContext;
    private List<www.seotoolzz.com.Ask.Model.Answer> myAnswer;
    private int userId;
    private AlertDialog.Builder alert;
    private String token;
    private int ownerId;
    private String questionId;
    private String removeAnswerUrl = "https://laravel-demo-deploy.herokuapp.com/api/v0/answers/remove";
    private String markAsSolveUrl = "https://laravel-demo-deploy.herokuapp.com/api/v0/answers/solve";

    public AnswerListAdapter(Context myContext, List<Answer> myAnswer, int userId, AlertDialog.Builder alert, String token, String questionId, int ownerId) {
        this.myContext = myContext;
        this.myAnswer = myAnswer;
        this.userId = userId;
        this.alert = alert;
        this.token = token;
        this.questionId = questionId;
        this.ownerId = ownerId;
    }

    @Override
    public int getCount() {
        return myAnswer.size();
    }

    @Override
    public Object getItem(int position) {
        return myAnswer.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(myContext, R.layout.item_answer_list,null);

        final int pos = position;
        final int oId = ownerId;
        TextView tvContent = (TextView)v.findViewById(R.id.txtContentAnswer);
        TextView tvUserName = (TextView)v.findViewById(R.id.txtUserNameAnswer);
        TextView tvTime = (TextView)v.findViewById(R.id.txtTimeAnswer);
        Button btnSolve = (Button) v.findViewById(R.id.btnSolve);

        tvContent.setText(myAnswer.get(position).getContent());
        tvUserName.setText(myAnswer.get(position).getUserName());
        tvTime.setText(myAnswer.get(position).getTime());

        Log.d("STATUS", myAnswer.get(position).getVoteNumber() + "");
        if (String.valueOf(myAnswer.get(position).getVoteNumber()).equals("true")) {
            btnSolve.setTextColor(Color.WHITE);
            btnSolve.setBackgroundColor(Color.parseColor("#00C851"));
            btnSolve.setHeight(30);
        }

        btnSolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SOLVE", oId + "");
                if (userId == oId) {
                    alert.setTitle("Info");
                    alert.setMessage("Mark this answer as result?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            markAsSolve(myAnswer.get(pos).getId());
                            Log.d("MARK_ANS", "OK");;
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alert.show();
                } else {
                    Toast.makeText(myContext.getApplicationContext(),
                            "You not own this question",
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });

        Button btnDeleteAnswer = (Button) v.findViewById(R.id.btnDeleteAnswer);

        btnDeleteAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DELETE", myAnswer.get(pos).getUserId() + "");
                if (userId == myAnswer.get(pos).getUserId()) {
                    alert.setTitle("Warning");
                    alert.setMessage("Are you sure to delete this answer?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteQuestion(myAnswer.get(pos).getId());
                            Log.d("DEL_ANS", "OK");;
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alert.show();
                } else {
                    Toast.makeText(myContext.getApplicationContext(),
                            "You not own this answer",
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });

        v.setTag(myAnswer.get(position).getId());

        return v;
    }

    private void deleteQuestion(int answerId)
    {
        final int id = answerId;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, this.removeAnswerUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    Log.d("LOGIN_RES", res.toString());
                    int code = res.getJSONObject("meta").getInt("status");
                    if (code == 700) {
                        Toast.makeText(myContext, "Delete success", Toast.LENGTH_LONG).show();
                        Intent changeView = new Intent(myContext, DetailQuestionActivity.class);
                        changeView.putExtra("id", String.valueOf(questionId));
                        myContext.startActivity(changeView);
                    } else {
                        Toast.makeText(myContext, res.getJSONObject("meta").getJSONObject("message").getString("main"), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(myContext, "Unknow error", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("VOLLEY_ERROR", "ERROR: " + error.getMessage());
                    Toast.makeText(myContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", token);

                return params;
            }
        };
        AsksController.getmInstance(myContext).addToRequestQueue(stringRequest);
    }

    private void markAsSolve(int answerId)
    {
        final int id = answerId;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, this.markAsSolveUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    Log.d("LOGIN_RES", res.toString());
                    int code = res.getJSONObject("meta").getInt("status");
                    if (code == 700) {
                        Toast.makeText(myContext, "Mark as solve success", Toast.LENGTH_LONG).show();
                        Intent changeView = new Intent(myContext, DetailQuestionActivity.class);
                        changeView.putExtra("id", String.valueOf(questionId));
                        myContext.startActivity(changeView);
                    } else {
                        Toast.makeText(myContext, res.getJSONObject("meta").getJSONObject("message").getString("main"), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(myContext, "Unknow error", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("VOLLEY_ERROR", "ERROR: " + error.getMessage());
                    Toast.makeText(myContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", token);

                return params;
            }
        };
        AsksController.getmInstance(myContext).addToRequestQueue(stringRequest);
    }
}
