package www.seotoolzz.com.Ask.model;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import www.seotoolzz.com.Ask.Activity.AddAnswerActivity;
import www.seotoolzz.com.Ask.R;

public class DetailQuestionActivity extends AppCompatActivity {

    private ListView lvAnswer;
    private AnswerListAdapter adapter;
    private List<Answer> myArrayAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_question);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView tvTitle = (TextView) findViewById(R.id.txtTitle);
        TextView tvContent = (TextView) findViewById(R.id.txtContent);
        TextView tvTag = (TextView) findViewById(R.id.tag1);
        TextView tvUserName = (TextView) findViewById(R.id.txtUserName);
        TextView tvNumberVote = (TextView) findViewById(R.id.txtVoteNumber);


        this.lvAnswer = (ListView)findViewById(R.id.listAnswer);
        myArrayAnswer = new ArrayList<>();

        myArrayAnswer.add(new Answer(1,"good","Huy","1","2/5/2018"));
        myArrayAnswer.add(new Answer(1,"good","Thao","2","2/5/2018"));

        adapter = new AnswerListAdapter(lvAnswer.getContext(),myArrayAnswer);
        lvAnswer.setAdapter(adapter);
        Intent recIntent = getIntent();

        String title = recIntent.getStringExtra("title");
        String content = recIntent.getStringExtra("content");
        String username = recIntent.getStringExtra("username");
        String vote = recIntent.getStringExtra("vote");

        int temp = recIntent.getIntExtra("id", 0);

        tvTitle.setText(title);
        tvContent.setText(content);
       /* tvTag.setText();*/
        tvUserName.setText(username);
        tvNumberVote.setText(vote);

        Toast.makeText(getApplicationContext(),String.valueOf(temp),Toast.LENGTH_LONG).show();

        Button addAnswer = (Button)findViewById(R.id.btnAddAnswer);
        addAnswer.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), AddAnswerActivity.class);
                startActivity(intent);
            }
        });
    }
}
