package com.thaopham.ask;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.thaopham.ask.Login.LoginActivity;
import com.thaopham.ask.Login.SignUpActivity;

public class ThirdFragment  extends Fragment implements View.OnClickListener{
    public static final String ARG_PAGE = "ARG_PAGE";
    public  int mPageNo;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_main, container, false);

        Button btnToLogin = (Button) view.findViewById(R.id.btnToLogin);
        btnToLogin.setOnClickListener((View.OnClickListener) this);

        Button btnToSignUp = (Button) view.findViewById(R.id.btnToSignUp);
        btnToSignUp.setOnClickListener((View.OnClickListener) this);
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

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnToLogin :
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
