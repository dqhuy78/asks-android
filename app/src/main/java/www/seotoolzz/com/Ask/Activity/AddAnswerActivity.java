package www.seotoolzz.com.Ask.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import www.seotoolzz.com.Ask.R;
import www.seotoolzz.com.Ask.model.Answer;
import www.seotoolzz.com.Ask.model.DetailQuestionActivity;

public class AddAnswerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_answer);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button pushAnswer = (Button)findViewById(R.id.btnPushAnswer);
        pushAnswer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), DetailQuestionActivity.class);
                startActivity(intent);
            }
        });

        Button drafAnswer = (Button)findViewById(R.id.btnDraf);
        drafAnswer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), DetailQuestionActivity.class);
                startActivity(intent);
            }
        });
    }
}
