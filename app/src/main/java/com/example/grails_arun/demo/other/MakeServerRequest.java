package com.example.grails_arun.demo.other;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import parq.com.parq.application.MainApplicationClass;
//import parq.com.parq.constant.Constants;

public class MakeServerRequest {

    private static String sec_token;
    private static JSONObject result = null;
    private static int mStatusCode=0,mErrorCode=0;
    private static final String TAG = "MakeServerRequest";
//    private static MainApplicationClass appInstance = MainApplicationClass.getInstance();

    public static void makeAPIRequest(final String eventName, final ArrayList<String> details, String apiName,
                                      int methodType, final IRequestCallBack callback) {

//        sec_token = appInstance.getSharedData("user_details", Constants.SHARED_SECURITY_TOKEN);
        Log.i(TAG, "make api request entered--->" + sec_token);
        JsonObjectRequest request = null;

        try {
            request = new JsonObjectRequest(
                    methodType,
                    apiName,
                    checkdetails(eventName, details),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Log.i(TAG, "response json--->" + eventName + "---" + mStatusCode + "---" + jsonObject);
                            callback.onSuccess(jsonObject, mStatusCode);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            callback.onError(volleyError, mErrorCode);
                        }
                    }
            )
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
//                    headers.put(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);
//                    headers.put(Constants.ACCEPT, Constants.APPLICATION_JSON);
                    return headers;
                }

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    try {
                        Log.i(TAG, "response network--" + eventName + "--" + response.statusCode + "--" + response.toString());
                        mStatusCode = response.statusCode;
                        String jsonString = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

                        Log.i(TAG, "check jsonString--->" + jsonString);

                        if (jsonString.length() > 0)
                            result = new JSONObject(jsonString);
                    } catch (Exception e) {
                        Log.e(TAG, "exception--->" + e.getMessage());
                    }
                    return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
                }

                @Override
                protected VolleyError parseNetworkError(VolleyError volleyError) {
                    try {
                        Log.i(TAG, "Volley error---" + eventName + "--" + volleyError.networkResponse.statusCode + "--" +
                                volleyError.getMessage());
                        if (volleyError.networkResponse != null) {
                            mErrorCode = volleyError.networkResponse.statusCode;
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "exception--ERROR-->" + e.getMessage());
                    }
                    return super.parseNetworkError(volleyError);
                }
            };

        } catch (JSONException e) {
            Log.e(TAG, "jsonException--" + e.getMessage());
        }

//        MainApplicationClass.getInstance().addToRequestQueue(request);
        request.setRetryPolicy(new DefaultRetryPolicy(60000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public static JSONObject checkdetails(String eventName, ArrayList<String> details) throws JSONException {
        try {
            Map<String, String> jsonParams = new HashMap<>();
            JSONObject jsonObject = new JSONObject();
            JSONObject jsonLevel1 = new JSONObject();

            if (eventName.equals("saveCategory")) {
                Log.i(TAG, "final Post Json--if--" + jsonObject);
                return jsonObject.put("", jsonParams);
            } else {
                switch (eventName) {
                    case "signup":
                        jsonObject.put("username", details.get(0));
                        jsonObject.put("firstName", details.get(1));
                        jsonObject.put("lastName", details.get(2));
                        jsonObject.put("email", details.get(3));
                        jsonObject.put("phone", details.get(4));
                        jsonObject.put("password", details.get(5));
                        jsonObject.put("secToken", details.get(6));
                        jsonObject.put("gender", details.get(8));
                        jsonObject.put("deviceId", details.get(9));
                        jsonObject.put("registrationType", details.get(10));
                        jsonObject.put("photo", details.get(7));
                        break;

                    case "verifyMobile":
                        jsonObject.put("user", Integer.parseInt(details.get(0)));
                        jsonObject.put("phoneNumber", details.get(1));
                        jsonObject.put("countryCode", details.get(2));
                        break;

                    case "validateOtp":
                        jsonObject.put("user", Integer.parseInt(details.get(0)));
                        jsonObject.put("phoneNumber", details.get(1));
                        jsonObject.put("verificationCode", details.get(2));
                        jsonObject.put("countryCode", details.get(3));
                        break;

                    case "login":
                        jsonObject.put("username", details.get(0));
                        jsonObject.put("password", details.get(1));
                        jsonObject.put("secToken", "");
                        break;

                    case "vehicleUpdate":
                        jsonObject.put("make", details.get(0));
                        jsonObject.put("model", details.get(1));
                        jsonObject.put("color", details.get(2));
                        jsonObject.put("lpn", details.get(3));
                        jsonObject.put("user", Integer.parseInt(details.get(4)));
                        break;

                    case "spaceDetails":
                        jsonObject.put("spaceType", details.get(0));
                        jsonObject.put("locationType", details.get(1));
                        jsonObject.put("isOwner", details.get(2));
                        jsonObject.put("numberOfSpaces", Integer.parseInt(details.get(3)));
                        jsonObject.put("user", Integer.parseInt(details.get(4)));
                        jsonObject.put("name", details.get(9));
                        jsonObject.put("address", details.get(10));
                        jsonObject.put("inUse", "no");
                        jsonObject.put("description", "");
                        jsonObject.put("isOpen", "");
                        jsonObject.put("spacePhoto1", details.get(5));
                        jsonObject.put("spacePhoto2", details.get(6));
                        jsonObject.put("spacePhoto3", details.get(7));
                        jsonObject.put("spacePhoto4", details.get(8));
                        jsonObject.put("spacePhoto5", "");
                        break;

                    case "addSpaces":
                        if (details.get(11) == null || details.get(12) == null) {
                        } else {
                        }
                        jsonObject.put("spaceType", details.get(0));
                        jsonObject.put("locationType", details.get(1));
                        jsonObject.put("isOwner", Boolean.parseBoolean(details.get(2)));
                        jsonObject.put("numberOfSpaces", Integer.parseInt(details.get(3)));
                        jsonObject.put("user", Integer.parseInt(details.get(4)));
                        jsonObject.put("name", details.get(9));
                        jsonObject.put("address", details.get(10));
                        jsonObject.put("latitude", Double.parseDouble(details.get(11)));
                        jsonObject.put("longitude", Double.parseDouble(details.get(12)));
                        jsonObject.put("inUse", false);
                        jsonObject.put("isOpen", true);
                        jsonObject.put("description", "");
                        jsonObject.put("spacePhoto1", details.get(5));
                        jsonObject.put("spacePhoto2", details.get(6));
                        jsonObject.put("spacePhoto3", details.get(7));
                        jsonObject.put("spacePhoto4", details.get(8));
                        jsonObject.put("spacePhoto5", "");
                        break;

                    case "loadSpace":
                        jsonObject.put("user", Integer.parseInt(details.get(0)));
                        break;

                    case "quesSubmit":
                        jsonObject.put("user", Integer.parseInt(details.get(0)));
                        jsonObject.put("question", details.get(1));
                        jsonObject.put("something", "");
                        break;

                    case "aboutList":
                        break;

                    case "tryOptions":
                        jsonObject.put("latitude", Double.parseDouble(details.get(0)));
                        jsonObject.put("longitude", Double.parseDouble(details.get(1)));
                        jsonObject.put("distance", Double.parseDouble(details.get(2)));
                        jsonObject.put("locationType", details.get(3));
                        break;

                    case "searchNearBy":
                        jsonObject.put("latitude", Double.parseDouble(details.get(0)));
                        jsonObject.put("longitude", Double.parseDouble(details.get(1)));
                        jsonObject.put("distance", Double.parseDouble(details.get(2)));
                        jsonObject.put("user", Integer.parseInt(details.get(3)));
                        break;

                    case "makeReservation":
                        jsonObject.put("space", Integer.valueOf(details.get(0)));
                        jsonObject.put("driver", Integer.valueOf(details.get(1)));
                        jsonObject.put("reservationDateString", details.get(2));
                        jsonObject.put("reservationStartTime", Integer.valueOf(details.get(3)));
                        jsonObject.put("durationInHrs", Integer.valueOf(details.get(4)));
                        jsonObject.put("reservationStatus", details.get(5));
                        jsonObject.put("timezone", details.get(6));
                        jsonObject.put("payKey", "AP-4UTO#3");
                        break;

                    case "history_list":
                        jsonObject.put("user", Integer.parseInt(details.get(0)));
                        jsonObject.put("isDriver", details.get(1));
                        break;

                    case "fetchSales":
                        jsonObject.put("user", Integer.parseInt(details.get(0)));
                        jsonObject.put("month", Integer.parseInt(details.get(1)));
                        break;

                    case "loadFavSpaces":
                        jsonObject.put("user", Integer.parseInt(details.get(0)));
                        jsonObject.put("isDriver", details.get(1));
                        break;

                    case "setFavSpace":
                        jsonObject.put("user", Integer.parseInt(details.get(0)));
                        jsonObject.put("space", Integer.parseInt(details.get(1)));
                        break;

                    case "linkPay":
                        jsonObject.put("user", Integer.parseInt(details.get(0)));
                        jsonObject.put("paypalEmail", details.get(1));
                        jsonObject.put("paypalPassword", details.get(2));
                        jsonObject.put("creditCardNumber", details.get(3));
                        jsonObject.put("creditCardName", details.get(4));
                        jsonObject.put("creditCardExpiry", details.get(5));
                        break;

                    case "identityCheck":
                        jsonObject.put("id", Integer.parseInt(details.get(0)));
                        jsonObject.put("idNumber", details.get(1));
                        jsonObject.put("idExpirationDate", details.get(2));
                        jsonObject.put("idType", details.get(3));
                        jsonObject.put("idImage", details.get(4));
                        break;

                    case "updateProfile":
                        jsonObject.put("username", details.get(0));
                        jsonObject.put("photo", details.get(1));
                        break;

                    case "disableSpace":
                        break;

                    case "saveSpace":
                        jsonObject.put("space", Integer.valueOf(details.get(0)));
                        jsonObject.put("startDate", details.get(1));
                        jsonObject.put("startTime", Integer.valueOf(details.get(2)));
                        jsonObject.put("endTime", Integer.valueOf(details.get(3)));
                        jsonObject.put("ratePerHour", Integer.valueOf(details.get(4)));
                        jsonObject.put("timezone", details.get(5));
                        break;

                    case "fcmToken":
                        jsonObject.put("id", details.get(0));
                        jsonObject.put("fcmToken", details.get(1));
                        break;

                    case "ageVerify":
                        jsonObject.put("id", Integer.parseInt(details.get(0)));
                        jsonObject.put("dob", details.get(1));
                        break;

                    case "authorizePayment":
                        jsonObject.put("preapprovalkey", details.get(0));
                        break;

                    case "enableSpace":
                        jsonObject.put("id", Integer.parseInt(details.get(0)));
                        break;
                }
            }
            Log.i(TAG, "final Post Json--->" + eventName + "--->" + jsonObject);
            return jsonObject;
        }catch (Exception e ){
            Log.e(TAG,"exception--"+e.getMessage());
        }
        return null;
    }
}