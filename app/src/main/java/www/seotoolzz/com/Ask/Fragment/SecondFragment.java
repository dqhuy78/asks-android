package www.seotoolzz.com.Ask.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import www.seotoolzz.com.Ask.Adapter.NotificationListAdapter;
import www.seotoolzz.com.Ask.R;
import www.seotoolzz.com.Ask.Model.Notification;

public class SecondFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    private ListView lvNotification;
    private NotificationListAdapter adapter;
    private List<Notification> myArrayNotification;
    public int mPageNo;

    public SecondFragment() {
    }

    public static SecondFragment newInstance(int pageNo) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        SecondFragment fragment = new SecondFragment();
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
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        /*this.lvNotification = (ListView)view.findViewById(R.id.lvNotification);
        myArrayNotification = new ArrayList<>();
        myArrayNotification.add(new Notification(1,"Nguyen Nga","da tra loi cauhoi cua ban","2h"));
        myArrayNotification.add(new Notification(2,"Nguyen Nga","da tra loi cauhoi cua ban","3h"));
        myArrayNotification.add(new Notification(3,"Nguyen Nga","da tra loi cauhoi cua ban","4h"));
        myArrayNotification.add(new Notification(4,"Nguyen Nga","da tra loi cauhoi cua ban","5h"));
        myArrayNotification.add(new Notification(5,"Nguyen Nga","da tra loi cauhoi cua ban","6h"));
        myArrayNotification.add(new Notification(6,"Nguyen Nga","da tra loi cauhoi cua ban","7h"));
        myArrayNotification.add(new Notification(7,"Nguyen Nga","da tra loi cauhoi cua ban","8h"));

        //init adapter
        adapter = new NotificationListAdapter(this.getContext(),myArrayNotification);
        lvNotification.setAdapter(adapter);
        lvNotification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"Item",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(),DetailQuestionActivity.class);
                startActivity(intent);
            }
        });*/
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
