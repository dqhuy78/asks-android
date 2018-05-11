package com.thaopham.ask.Question;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thaopham.ask.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class QuestionListAdapter extends BaseAdapter {

        private Context myContext;
        private List<JSONObject> myQuestion;

        public QuestionListAdapter(List<Question> myContext, Context myQuestion) {
            this.myContext = (Context) myContext;
            this.myQuestion = (List<JSONObject>) myQuestion;
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

            try {
                tvTitle.setText(myQuestion.get(position).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                tvContent.setText(myQuestion.get(position).getInt("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                tvTag.setText(myQuestion.get(position).getString("tag"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                tvUserName.setText(myQuestion.get(position).getString("usename"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                tvNumberVote.setText(myQuestion.get(position).getInt("votenumber"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            v.setTag(myQuestion.get(position).getId());
            return v;
        }
}
