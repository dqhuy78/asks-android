package www.seotoolzz.com.Ask.model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import www.seotoolzz.com.Ask.R;
import www.seotoolzz.com.Ask.model.Question;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(myContext, R.layout.item_question_list,null);
        TextView tvTitle = (TextView)v.findViewById(R.id.txtTitle);
        TextView tvContent = (TextView)v.findViewById(R.id.txtContent);
        TextView tvTag = (TextView)v.findViewById(R.id.tag1);
        TextView tvUserName = (TextView)v.findViewById(R.id.txtUserName);
        TextView tvNumberVote = (TextView)v.findViewById(R.id.txtVoteNumber);

        tvTitle.setText(myQuestion.get(position).getTitle());
        tvContent.setText(myQuestion.get(position).getContent());
        tvTag.setText(myQuestion.get(position).getTag());
        tvUserName.setText(myQuestion.get(position).getUserName());
        tvNumberVote.setText(myQuestion.get(position).getVoteNumber());

        v.setTag(myQuestion.get(position).getId());
        return v;
    }

}
