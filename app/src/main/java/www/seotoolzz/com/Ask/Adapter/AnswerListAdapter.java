package www.seotoolzz.com.Ask.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import www.seotoolzz.com.Ask.R;
import www.seotoolzz.com.Ask.Model.Answer;

public class AnswerListAdapter extends BaseAdapter {
    private Context myContext;
    private List<www.seotoolzz.com.Ask.Model.Answer> myAnswer;

    public AnswerListAdapter(Context myContext, List<Answer> myAnswer) {
        this.myContext = myContext;
        this.myAnswer = myAnswer;
    }

    @Override
    public int getCount() {
        return myAnswer.size();
    }

    @Override
    public Object getItem(int position) {
        return myAnswer.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(myContext, R.layout.item_answer_list,null);

        TextView tvContent = (TextView)v.findViewById(R.id.txtContentAnswer);
        TextView tvUserName = (TextView)v.findViewById(R.id.txtUserNameAnswer);
        TextView tvTime = (TextView)v.findViewById(R.id.txtTimeAnswer);

        tvContent.setText(myAnswer.get(position).getContent());
        tvUserName.setText(myAnswer.get(position).getUserName());
        tvTime.setText(myAnswer.get(position).getTime());

        v.setTag(myAnswer.get(position).getId());
        return v;
    }
}
