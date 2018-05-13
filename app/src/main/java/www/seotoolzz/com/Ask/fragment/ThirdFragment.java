package www.seotoolzz.com.Ask.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import www.seotoolzz.com.Ask.Activity.LoginActivity;
import www.seotoolzz.com.Ask.Activity.MainActivity;
import www.seotoolzz.com.Ask.Activity.SignUpActivity;
import www.seotoolzz.com.Ask.Helper.Helper;
import www.seotoolzz.com.Ask.R;
import www.seotoolzz.com.Ask.RequestController.AsksController;

import static android.content.Context.MODE_PRIVATE;

public class ThirdFragment extends Fragment implements View.OnClickListener{

    public static final String ARG_PAGE = "ARG_PAGE";
    public int mPageNo;
    public View view;
    private String getUserUrl = "http://laravel-demo-deploy.herokuapp.com/api/v0/auth/user";

    public static ThirdFragment newInstance(int pageNo) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        ThirdFragment fragment = new ThirdFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNo = getArguments().getInt(ARG_PAGE);
        if (Helper.isLogin((AppCompatActivity) getActivity())) {
            SharedPreferences sharePrefs = getActivity().getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE);
            Log.d("TEST_COND", sharePrefs.getString("username", "EMPTY").equals("EMPTY") + "");
            if (sharePrefs.getString("username", "EMPTY").equals("EMPTY")) {
                getUserInfo();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);

        Button btnToLogin = (Button) view.findViewById(R.id.btnLogin);
        btnToLogin.setOnClickListener(this);

        Button btnToSignUp = (Button) view.findViewById(R.id.btnToSignUp);
        btnToSignUp.setOnClickListener(this);

        Button btnLogout = (Button) view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);

        if (Helper.isLogin((AppCompatActivity) getActivity())) {
            SharedPreferences sharePrefs = getActivity().getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE);
            String username = sharePrefs.getString("username", null);
            String userEmail = sharePrefs.getString("email", null);

            Log.d("TXTV_DATA", username + "");
            TextView edtUsername = (TextView) view.findViewById(R.id.username);
            TextView edtUserEmail = (TextView) view.findViewById(R.id.userEmail);

            edtUsername.setText(username);
            edtUserEmail.setText(userEmail);

            LoginSuccess();
        }

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

    public void LoginSuccess(){
        LinearLayout layOutLoginSuccess = (LinearLayout) view.findViewById(R.id.layOutLoginSucess);
        layOutLoginSuccess.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                Intent changeView = new Intent(getActivity(), LoginActivity.class);
                startActivity(changeView);
                break;
            case R.id.btnToSignUp:
                Intent changeViewSignUp = new Intent(getActivity(), SignUpActivity.class);
                startActivity(changeViewSignUp);
                break;
            case R.id.btnLogout:
                SharedPreferences.Editor sharePrefs = getActivity().getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE).edit();
                sharePrefs.clear();
                sharePrefs.commit();
                Toast.makeText(getContext(),"Logout success",Toast.LENGTH_LONG).show();
                getActivity().recreate();
                break;
        }
    }

    private void getUserInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.getUserUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getJSONObject("meta").getInt("status");

                    if (code == 700) {
                        // Get token and save in local storage
                        JSONObject data = res.getJSONObject("data");
                        Log.d("USER_PROFILE", data.getString("username"));
                        SharedPreferences.Editor sharePrefs = getActivity().getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE).edit();
                        sharePrefs.putInt("userId", data.getInt("id"));
                        sharePrefs.putString("username", data.getString("username"));
                        sharePrefs.putString("email", data.getString("email"));
                        sharePrefs.putString("role", data.getString("role"));
                        sharePrefs.commit();

                        TextView edtUsername = (TextView) view.findViewById(R.id.username);
                        TextView edtUserEmail = (TextView) view.findViewById(R.id.userEmail);

                        edtUsername.setText(data.getString("username"));
                        edtUserEmail.setText(data.getString("email"));
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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences sharePrefs = getActivity().getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE);
                params.put("Authorization", sharePrefs.getString("token", null));

                return params;
            }
        };
        AsksController.getmInstance(getActivity()).addToRequestQueue(stringRequest);
    }
}
