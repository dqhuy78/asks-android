package www.seotoolzz.com.Ask.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import www.seotoolzz.com.Ask.R;
import www.seotoolzz.com.Ask.Model.Notification;

/**
 * Created by ngant on 4/18/2018.
 */

public class NotificationListAdapter extends BaseAdapter{

    private Context myContext;
    private List<Notification> myNotification;

    public NotificationListAdapter(Context myContext, List<Notification> myNotification) {
        this.myContext = myContext;
        this.myNotification = myNotification;
    }

    public Context getMyContext() {
        return myContext;
    }

    public void setMyContext(Context myContext) {
        this.myContext = myContext;
    }

    public List<Notification> getMyNotification() {
        return myNotification;
    }

    public void setMyNotification(List<Notification> myNotification) {
        this.myNotification = myNotification;
    }

    @Override
    public int getCount() {
        return myNotification.size();
    }

    @Override
    public Object getItem(int position) {
        return myNotification.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(myContext, R.layout.item_notification_list,null);

        TextView tvUserName = (TextView)v.findViewById(R.id.txtUserName);
        TextView tvTime = (TextView)v.findViewById(R.id.txtTime);
        TextView tvInfoAction = (TextView)v.findViewById(R.id.txtinfoAction);

        tvUserName.setText(myNotification.get(position).getNameUser());
        tvTime.setText(myNotification.get(position).getInfoTime());
        tvInfoAction.setText(myNotification.get(position).getInfoAction());


        v.setTag(myNotification.get(position).getId());


        return v;
    }
}
