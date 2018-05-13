package www.seotoolzz.com.Ask.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import www.seotoolzz.com.Ask.RequestController.AsksController;
import www.seotoolzz.com.Ask.Activity.DetailQuestionActivity;
import www.seotoolzz.com.Ask.model.QuestionListAdapter;
import www.seotoolzz.com.Ask.R;
import www.seotoolzz.com.Ask.model.Question;

public class FirstFragment extends Fragment {

    public int mPageNo;
    public static final String ARG_PAGE = "ARG_PAGE";
    private ListView lvQuestion;
    private QuestionListAdapter adapter;
    private List<Question> myArrayQuestion;
    private int currentPage = 1;
    private boolean isLoading = true;
    private String getQuestionUrl = "https://laravel-demo-deploy.herokuapp.com/api/v0/questions";

    public static FirstFragment newInstance(int pageNo) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        FirstFragment fragment = new FirstFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNo = getArguments().getInt(ARG_PAGE);
        myArrayQuestion = new ArrayList<>();
        getQuestionList(1);
        Log.d("STATUS", isLoading + "");

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("STATUS_2", isLoading + "");
        View view = inflater.inflate(R.layout.listview_question, container, false);

        this.lvQuestion = (ListView)view.findViewById(R.id.lv_question);
        if (myArrayQuestion.size() < 1) {
            getQuestionList(1);
        } else {
            adapter = new QuestionListAdapter(getContext(), myArrayQuestion);
            lvQuestion.setAdapter(adapter);
        }


        lvQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idQuestion = myArrayQuestion.get(position).getId() + "";
                Intent intent = new Intent(getActivity(),DetailQuestionActivity.class);
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

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
                        Log.d("QUESTION_RES", data.toString());
                        for(int i = 0; i < data.length(); i++) {
                            JSONObject q = new JSONObject();
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
                        // Init adapter
                        adapter = new QuestionListAdapter(getContext(), myArrayQuestion);
                        lvQuestion.setAdapter(adapter);
                        isLoading = false;
                        Log.d("STATUS_3", isLoading + "");
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), res.getJSONObject("meta").getJSONObject("message").getString("main"), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getActivity().getApplicationContext(), "Unknow error", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("VOLLEY_ERROR", "ERROR: " + error.getMessage());
                    Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        AsksController.getmInstance(getActivity()).addToRequestQueue(stringRequest);
    }
}
