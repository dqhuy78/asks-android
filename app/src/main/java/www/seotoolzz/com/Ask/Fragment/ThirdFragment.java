package www.seotoolzz.com.Ask.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import www.seotoolzz.com.Ask.Activity.LoginActivity;
import www.seotoolzz.com.Ask.Activity.MyQuestionActivity;
import www.seotoolzz.com.Ask.Activity.SignUpActivity;
import www.seotoolzz.com.Ask.Helper.Helper;
import www.seotoolzz.com.Ask.R;

import static android.content.Context.MODE_PRIVATE;

public class ThirdFragment extends Fragment implements View.OnClickListener{

    public static final String ARG_PAGE = "ARG_PAGE";
    public int mPageNo;
    public View view;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);

        Button btnToLogin = (Button) view.findViewById(R.id.btnLogin);
        btnToLogin.setOnClickListener(this);

        Button btnToSignUp = (Button) view.findViewById(R.id.btnToSignUp);
        btnToSignUp.setOnClickListener(this);

        Button btnLogout = (Button) view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);

        Button btnMyQuestion = (Button) view.findViewById(R.id.btnMyQuestion);
        btnMyQuestion.setOnClickListener(this);

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
            case R.id.btnMyQuestion:
                Intent changeViewQuestion = new Intent(getActivity(), MyQuestionActivity.class);
                startActivity(changeViewQuestion);
                break;
        }
    }
}