package www.seotoolzz.com.Ask.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import www.seotoolzz.com.Ask.model.DetailQuestionActivity;
import www.seotoolzz.com.Ask.model.QuestionListAdapter;
import www.seotoolzz.com.Ask.R;
import www.seotoolzz.com.Ask.model.Question;

public class FirstFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private ListView lvQuestion;
    private QuestionListAdapter adapter;
    private List<Question> myArrayQuestion;

    private int mPageNo;

    public FirstFragment() {
    }

    public static FirstFragment newInstance(int pageNo) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        FirstFragment fragment = new FirstFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNo = getArguments().getInt(ARG_PAGE);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_question, container, false);

        this.lvQuestion = (ListView)view.findViewById(R.id.lv_question);
        myArrayQuestion = new ArrayList<>();

        myArrayQuestion.add(new Question(1,"How ?",
                " cua do mot chang trai","trai","nguyen Nga","8"));

        myArrayQuestion.add(new Question(2,"What do you do ?",
                "lam the nao ","trai","nguyen Nga","2"));

        myArrayQuestion.add(new Question(3,"Why  ?",
                "co gai met 52","trai","nguyen Nga","10"));

        myArrayQuestion.add(new Question(4,"When ?",
                "co gai met 52","trai","nguyen Nga","100"));
        //init adapter
        adapter = new QuestionListAdapter(this.getContext(),myArrayQuestion);
        lvQuestion.setAdapter(adapter);
        lvQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"Item",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(),DetailQuestionActivity.class);
                startActivity(intent);
            }
        });
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


}
