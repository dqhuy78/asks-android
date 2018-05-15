package www.seotoolzz.com.Ask.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import www.seotoolzz.com.Ask.R;
import www.seotoolzz.com.Ask.Model.Question;

/**
 * Created by ngant on 4/15/2018.
 */

public class QuestionListAdapter extends BaseAdapter {

    private Context myContext;
    private List<Question> myQuestion;

    public QuestionListAdapter(Context myContext, List<Question> myQuestion) {
        this.myContext = myContext;
        this.myQuestion = myQuestion;
    }

    @Override
    public int getCount() {
        return myQuestion.size();
    }

    @Override
    public Object getItem(int position) {
        return myQuestion.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(myContext, R.layout.item_question_list,null);
        TextView tvTitle = (TextView)v.findViewById(R.id.txtTitle);
        TextView tvUserName = (TextView)v.findViewById(R.id.txtUserName);
        TextView tvNumberVote = (TextView)v.findViewById(R.id.txtVoteNumber);
        TextView tvDate = (TextView) v.findViewById(R.id.createDate);
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.voteBgr);

        tvTitle.setText(myQuestion.get(position).getTitle());
        tvUserName.setText("Ask by: " + myQuestion.get(position).getUsername());
        tvNumberVote.setText(myQuestion.get(position).getVote() + "");
        tvDate.setText(myQuestion.get(position).getDate());
        if (myQuestion.get(position).isSolve() == 2) {
            layout.setBackgroundColor(Color.parseColor("#00C851"));
            tvNumberVote.setTextColor(Color.WHITE);
        }
        if (myQuestion.get(position).isSolve() == 0) {
            layout.setBackgroundColor(Color.parseColor("#FFBB33"));
            tvNumberVote.setTextColor(Color.WHITE);
        }

        v.setTag(myQuestion.get(position).getId());

        return v;
    }

}
