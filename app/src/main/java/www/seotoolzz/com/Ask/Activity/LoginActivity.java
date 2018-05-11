package www.seotoolzz.com.Ask.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import www.seotoolzz.com.Ask.R;
import www.seotoolzz.com.Ask.fragment.ThirdFragment;
import www.seotoolzz.com.Ask.model.DetailQuestionActivity;


public class LoginActivity extends AppCompatActivity {

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String NEWS_FRAGMENT = "news_fragment";
    Button btnLogin;
    EditText edPassword;
    EditText edNameUser;
    TextView txtErr;
    TextView txtToSignUp;
    public boolean loginsuccess = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
//
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        edNameUser = (EditText)findViewById(R.id.edtEmail);
        edPassword = (EditText)findViewById(R.id.edtPassword);
        txtErr = (TextView)findViewById(R.id.txtErr);
        txtErr.setVisibility(View.GONE);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hidden keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                if(edPassword.getText().toString().equals("admin") &&
                        edNameUser.getText().toString().equals("admin")){
                    Toast.makeText(getApplicationContext(),"Login...",Toast.LENGTH_LONG).show();
                    loginsuccess = true;
                    //login sucess
                    Intent newIntent =  new Intent(getBaseContext(),MainActivity.class);
                    startActivity(newIntent);






                }else if(!edPassword.getText().toString().equals("admin")){
                    txtErr.setVisibility(View.VISIBLE);
                    txtErr.setText("Pass err");
                }else if(!edNameUser.getText().toString().equals("admin")){
                    txtErr.setVisibility(View.VISIBLE);
                    txtErr.setText("User err");
                }
            }
        });
        txtToSignUp = (TextView) findViewById(R.id.txtToSignup);
        txtToSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent newIntent =  new Intent(getBaseContext(),SignUpActivity.class);
                startActivity(newIntent);
            }
        });

    }
}
