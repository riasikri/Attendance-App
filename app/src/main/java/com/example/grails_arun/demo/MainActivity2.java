package com.example.grails_arun.demo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;


public class MainActivity2 extends Activity {
   // private Button submit;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
     //   submit=(Button)findViewById(R.id.submit);
        textView = (TextView) findViewById(R.id.output);

        Intent intent = getIntent();

        textView.setText("Welcome User " + intent.getStringExtra(MainActivity.USERNAME));
        /*submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String serverURL = "http://localhost:8097/users";
                // Use AsyncTask execute Method To Prevent ANR Problem
                //new LongOperation().execute(serverURL);

            }
        });*/

    }
    /*private class LongOperation  extends AsyncTask<String, Void, Void> {

        // Required initialization

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(MainActivity2.this);
        String data ="";
        TextView uiUpdate = (TextView) findViewById(R.id.output);
        TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
        int sizeData = 0;
        EditText serverText = (EditText) findViewById(R.id.serverText);


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //Start Progress Dialog (Message)

            Dialog.setMessage("Please wait..");
            Dialog.show();

            try{
                // Set Request parameter
                data +="&" + URLEncoder.encode("data", "UTF-8") + "="+serverText.getText();

            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {

            *//************ Make Post Call To Web Server ***********//*
            BufferedReader reader=null;

            // Send data
            try
            {

                // Defined URL  where to send data
                URL url = new URL(urls[0]);

                // Send POST data request

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write( data );
                wr.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    // Append server response in string
                    sb.append(line + "");
                }

                // Append Server Response To Content String
                Content = sb.toString();
            }
            catch(Exception ex)
            {
                Error = ex.getMessage();
            }
            finally
            {
                try
                {

                    reader.close();
                }

                catch(Exception ex) {}
            }

            *//*****************************************************//*
            return null;
        }

        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.

            // Close progress dialog
            Dialog.dismiss();

            if (Error != null) {

                uiUpdate.setText("Output : "+Error);

            } else {

                // Show Response Json On Screen (activity)
                uiUpdate.setText( Content );

                *//****************** Start Parse Response JSON Data *************//*

                String OutputData = "";
                JSONObject jsonResponse;

                try {

                    *//****** Creates a new JSONObject with name/value mappings from the JSON string. ********//*
                    jsonResponse = new JSONObject(Content);

                    *//***** Returns the value mapped by name if it exists and is a JSONArray. ***//*
                    *//*******  Returns null otherwise.  *******//*
                    JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");

                    *//*********** Process each JSON Node ************//*

                    int lengthJsonArr = jsonMainNode.length();

                    for(int i=0; i < lengthJsonArr; i++)
                    {
                        *//****** Get Object for each JSON node.***********//*
                        JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                        *//******* Fetch node values **********//*
                        String username       = jsonChildNode.optString("username").toString();
                        String password     = jsonChildNode.optString("password").toString();



                        OutputData += " UserName   : "+ username +" "   + "Password      : "+ password +"    "  ;


                    }
                    *//****************** End Parse Response JSON Data *************//*

                    //Show Parsed Output on screen (activity)
                    jsonParsed.setText( OutputData );


                } catch (JSONException e) {

                    e.printStackTrace();
                }


            }
        }

    }

*/





    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

}