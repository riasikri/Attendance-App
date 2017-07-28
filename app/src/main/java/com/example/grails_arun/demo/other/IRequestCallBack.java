package com.example.grails_arun.demo.other;

import com.android.volley.VolleyError;

import org.json.JSONObject;


public  interface IRequestCallBack {
    void onSuccess(JSONObject response, int resCode);
    void onError(VolleyError error, int resCode);
}
