package com.example.grails_arun.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity implements View.OnClickListener {
    private EditText Password;
    //private TextView show;
    private CheckBox Showpassword;
    private Button login;
    private AutoCompleteTextView Username;
    public static final String LOGIN_URL = "http://5.189.157.55:7070/attendance/api/login";
    public static final String USERS_URL = "http://5.189.157.55:7070/attendance/api/users";

    public static final String USERNAME="username";
    public static final String PASSWORD="password";
    public static final String TAG="MainActivity";
    private String username="username";
    private String password="password";
    private static String json="";
    private String json1="";
    JSONObject result;
    private static int mStatusCode=0;
    private static int mErrorCode=0;
    HttpGet httpGet;
    HttpResponse response1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Showpassword=(CheckBox)findViewById(R.id.checkbox);
        Password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.email_sign_in_button);
        Username=(AutoCompleteTextView)findViewById(R.id.email);

        //show=(TextView)findViewById(R.id.textView3);

        login.setOnClickListener(this);

        Showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    //Show password
                    Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    //Hide password
                    Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }
    private void userLogin(){

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", Username.getText().toString());
        params.put("password", Password.getText().toString());

        HashMap<String, String> params1 = new HashMap<String, String>();
        params.put("access_token", json);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                LOGIN_URL,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG,"response from server--" + response.toString());
                        try {
                            json=response.getString("access_token");
                            Log.i(TAG, "access_token...........>"+json);

                            Toast.makeText(getApplicationContext(), json, Toast.LENGTH_SHORT).show();
                        }catch(Exception e){
                            Log.i(TAG,"exception--e" + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG,"error---" + error.getMessage());
                    }
                });

        /*final HttpClient httpclient = new DefaultHttpClient();

        final HttpPost request  = new HttpPost(USERS_URL);
//        request.addHeader(new BasicHeader("X-Auth-token",json));
        request.setHeader(new BasicHeader("Content-Type","application/json"));
        request.setHeader(new BasicHeader("X-Auth-token",json));

        try {
            HttpResponse response1 = httpclient.execute(request);
            Log.i(TAG,"response---------"+response1);
        }
        catch (Exception e)
        {
            Log.i(TAG,"exception---------"+e.getMessage());
        }*/

        final StringBuilder body = new StringBuilder();
        final DefaultHttpClient httpclient = new DefaultHttpClient(); // create new httpClient
        httpGet = new HttpGet("http://5.189.157.55:7070/attendance/api/users"); // create new httpGet object
//        httpGet.setHeader(new BasicHeader("Content-Type","application/json"));
//        httpGet.setHeader(new BasicHeader("X-Auth-token",json));
        httpGet.setHeader("X-Auth-token","eyJhbGciOiJIUzI1NiJ9.eyJwcmluY2lwYWwiOiJINHNJQUFBQUFBQUFBSlZTUDBcL2JRQlJcL0RrRlFJU2dnZ2RRQkZtQkRqdFNPV1VwUVFFSVdJTklzVktLNjJBXC8zNEh4bjdzNlFMQ2hUT3pDQVdwQlEreFg0Sm1YcEI2aGdZR1h1Mm5lRzRMUUw2azMydTU5XC9cLzU2djdtSFFhSGdUYThhRjhWT1J4Vno2SnRWY3hnYkRUSFBiOFRPRE9rS2JJMVp6WUpNbThIQzhFbmdCbEhoa1lUTFlZNGVzSXBpTUt4dXRQUXh0dGEzaHRkTHhJK091WmdrZUtiM3ZQM0dIU3VOZkFnVzE5NjBFUTlzd3djSlFaZEt1SzFsdnAxeGp0QTNqeFN4UTRiNGJUWVYwZzlKeUprd1wvZEFnbGF3bU1BaGhobWYyb1NKV2pzZkR5d1d4bXVhZzAwRllER0U2Wk1lVHVueVFONjZ5N2UyZFRVb0lET0laeU9cL1hvVUhjTER1bzdIbjlaQ1VHcHVaSm12aWtURmZGZDdzU0p2enZ6NWVmcDkyNnpCRUNkTEQ3XC9UVEZcL1ZZUHVqNTNmczNuUlhtaGh1czk2QWF1MlUzSXpVVENcLzAraVVmMTF1ZnIyNFwvXC94K2dKUWRZdVhcLzl6R1wvOU5oY1oxa2xLZFBNcXI0ZEVlMVIyVDBUZWUxNTh0NFdPbjZESjZsQStxT2t4ZWhKb2lDbXVHV3RSSzl2Q3krMk5vTDZoMmFqdnVYZVJqV3l0M0ZDTG9rNElmR3hQTHRibWg4b1d0bkozZG4xNmR3TkVhM0I0Q0VUR1ZMMTR3Vm9QVXRhcUQ5ZFhjeU1uTitlNUVGNlBcL1Vmc3p2aFFCZ0RBQUE9Iiwic3ViIjoicmVhQGdtYWlsLmNvbSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJleHAiOjE1MDEyNDk4OTYsImlhdCI6MTUwMTI0NjI5Nn0.K7ELEWvhEYD5si2HcqsglRXg7OSvJ2FV-bp1u2Xsb6o");

        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(
                Request.Method.GET,
                "http://5.189.157.55:7070/attendance/api/users",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG,"response from server--" + response.toString());

                        try {
                            response1 = httpclient.execute(httpGet); // execute httpGet
                            StatusLine statusLine = response1.getStatusLine();
                            int statusCode = statusLine.getStatusCode();
                            if (statusCode == HttpStatus.SC_OK) {
                                // System.out.println(statusLine);
                                body.append(statusLine + "\n");
                                HttpEntity e = response1.getEntity();
                                String entity = EntityUtils.toString(e);
                                body.append(entity);
                            } else {
                                body.append(statusLine + "\n");
                            }
//                              json1=response.getString("username");
                            Log.i(TAG, "username...........>"+body);

//                            Toast.makeText(getApplicationContext(), json1, Toast.LENGTH_SHORT).show();
                        }catch(Exception e){
                            e.printStackTrace();
                            Log.i(TAG,"exception--e" + e.getStackTrace());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e(TAG,"error---" + error.getMessage());
                    }
                }){
        @Override
        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
            try {
                Log.i(TAG, "response network--" + "--" + response.statusCode + "--" + response.toString());
                mStatusCode = response.statusCode;
                String jsonString = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

                Log.i(TAG, "check jsonString--->" + jsonString);

                if (jsonString.length() > 0)
                    result = new JSONObject(jsonString);
            } catch (Exception e) {
                Log.e(TAG, "exceptionnnnnnnnnnnn--->" + e.getMessage());
            }
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
        }

        @Override
        protected VolleyError parseNetworkError(VolleyError volleyError) {
            try {
                Log.i(TAG, "Volley error---" + "--" + volleyError.networkResponse.statusCode + "--" +
                        volleyError.getMessage());
                if (volleyError.networkResponse != null) {
                    mErrorCode = volleyError.networkResponse.statusCode;
                }
            } catch (Exception e) {
                Log.e(TAG, "exception--ERROR-->" + e.getMessage());
            }
            return super.parseNetworkError(volleyError);
        }};

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
//        requestQueue.add(jsonObjectRequest1);
    }

    private void access()
    {
        final StringBuilder body = new StringBuilder();
        final DefaultHttpClient httpclient = new DefaultHttpClient(); // create new httpClient
        httpGet = new HttpGet("http://5.189.157.55:7070/attendance/api/users"); // create new httpGet object
//        httpGet.setHeader(new BasicHeader("Content-Type","application/json"));
//        httpGet.setHeader(new BasicHeader("X-Auth-token",json));
        httpGet.setHeader("X-Auth-token",json);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                USERS_URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.i(TAG,"response from server--" + response.toString());
                        try {
                            response1 = httpclient.execute(httpGet); // execute httpGet
//                            JSONObject jsonObject=new JSONObject(response.toString());
//                            json1=jsonObject.getString("username");
                            Log.i(TAG, "access_token...........>"+json1);

//                            Toast.makeText(getApplicationContext(), json, Toast.LENGTH_SHORT).show();
                        }catch(Exception e){
                            Log.i(TAG,"exception--e" + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG,"error---" + error.getMessage());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
//                String mApiKey = "123";
                headers.put("X-Auth-token",json);
                Log.i(TAG,"token......>"+headers.get("X-Auth-token"));
                return headers;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void openProfile(){
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra(USERNAME, username);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        userLogin();
        access();
    }


   /* class ExecuteTask extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params) {

            String res=PostData(params);

            return res;
        }

        @Override
        protected void onPostExecute(String result) {

            //progess_msz.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), result,Toast.LENGTH_LONG).show();
            show.setText(result);

        }

    }
    public String PostData(String[] valuse) {
        String s="";
        try
        {
            HttpClient httpClient=new DefaultHttpClient();
            HttpPost httpPost=new HttpPost("http://www.google.com/url?q=http%3A%2F%2Flocalhost%3A8087%2Flogin%2Fauthenticate&sa=D&sntz=1&usg=AFQjCNHu4jlyON5Pw4IHP5UmcO9aQ0k3xA");

            List<NameValuePair> list=new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("username", valuse[0]));
            list.add(new BasicNameValuePair("password",valuse[1]));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse=  httpClient.execute(httpPost);

            HttpEntity httpEntity=httpResponse.getEntity();
            s= readResponse(httpResponse);


        }
        catch(Exception exception)  {}
        return s;


    }
    public String readResponse(HttpResponse res) {
        InputStream is=null;
        String return_text="";
        try {
            is=res.getEntity().getContent();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
            String line="";
            StringBuffer sb=new StringBuffer();
            while ((line=bufferedReader.readLine())!=null)
            {
                sb.append(line);
            }
            return_text=sb.toString();
        } catch (Exception e)
        {

        }
        return return_text;

    }*/


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        *//*if (id == R.id.action_settings) {
            return true;
        }*//*

        return super.onOptionsItemSelected(item);
    }*/
}
