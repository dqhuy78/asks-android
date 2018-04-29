package com.thaopham.ask.Ask;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Ask_Controller  {
    private static Context mCtx;
    private RequestQueue mRequestQueue;
    private static Ask_Controller mInstance;

    private Ask_Controller(Context context){
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized Ask_Controller getmInstance(Context context){
        if (mInstance == null){
            mInstance = new Ask_Controller(context);
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

