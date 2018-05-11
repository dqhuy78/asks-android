package www.seotoolzz.com.Ask.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import www.seotoolzz.com.Ask.Activity.LoginActivity;
import www.seotoolzz.com.Ask.Activity.SignUpActivity;
import www.seotoolzz.com.Ask.R;

public class ThirdFragment extends Fragment implements View.OnClickListener{

    public static final String ARG_PAGE = "ARG_PAGE";
    public int mPageNo;
    public View view;
    public ThirdFragment() {
    }

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

        //getFragmentManager().beginTransaction().add(R.id.tabUser, fragmentToBeAdded, tag).commit();



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
        //LoginSuccess();



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
                Toast.makeText(getContext(),"Nga",Toast.LENGTH_LONG).show();
                Intent changeView = new Intent(getActivity(), LoginActivity.class);
                startActivity(changeView);
                break;
            case R.id.btnToSignUp:
                Intent changeViewSignUp = new Intent(getActivity(), SignUpActivity.class);
                startActivity(changeViewSignUp);
                break;
        }

    }
}
