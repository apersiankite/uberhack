package com.example.prateek.uberhack.network;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by Prateek on 12/01/16.
 */
public class RequestManager {

    private static RequestManager mInstance;
    private RequestQueue mRequestQueue;

    private RequestManager(Context context) {
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
    }

    public synchronized static RequestManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RequestManager(context);
        }
        return mInstance;
    }

    public <T> void addRequest(Request<T> request, String tag) {
        request.setTag(tag);
        mRequestQueue.add(request);
    }

    public  void addRequest(StringRequest request, String tag) {
        request.setTag(tag);
        mRequestQueue.add(request);
    }


    public void cancelAll(String tag) {
        mRequestQueue.cancelAll(tag);
    }
}
