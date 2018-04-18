package www.seotoolzz.com.Ask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ngant on 4/16/2018.
 */

public class SignUpActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView txtToLogin = (TextView) findViewById(R.id.txtToLogin);
        txtToLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent changeView = new Intent(SignUpActivity.this, MainActivity.class);
                SignUpActivity.this.startActivity(changeView);
            }
        });
    }
}
