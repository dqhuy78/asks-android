package www.seotoolzz.com.Ask.RequestController;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AsksController  {
    private static Context mCtx;
    private RequestQueue mRequestQueue;
    private static AsksController mInstance;

    private AsksController(Context context){
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized AsksController getmInstance(Context context){
        if (mInstance == null){
            mInstance = new AsksController(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if (mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());

        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request <T> req){
        getRequestQueue().add(req);
    }

}

