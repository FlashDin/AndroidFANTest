package com.flashdin.fastandroidnetworkingnew.model.dao.impl;

import android.os.Looper;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.OkHttpResponseAndJSONArrayRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseAndStringRequestListener;
import com.flashdin.fastandroidnetworkingnew.model.config.config;
import com.flashdin.fastandroidnetworkingnew.model.dao.TUserDAO;
import com.flashdin.fastandroidnetworkingnew.model.entity.TUsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class TUserDAOImpl implements TUserDAO {

    public static final String restUrl = config.restUrl.getUrl();

    @Override
    public List<TUsers> views(TUsers params) {
        final List<TUsers> tUsersList = new ArrayList<>();
        AndroidNetworking.get(restUrl + "/userse/{userName}")
                .addPathParameter("userName", "j")
                .setTag(this)
                .setPriority(Priority.LOW)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                })
                .getAsOkHttpResponseAndJSONArray(new OkHttpResponseAndJSONArrayRequestListener() {
                    @Override
                    public void onResponse(Response okHttpResponse, JSONArray response) {
                        Log.d(TAG, "onResponse object : " + response.toString());
                        Log.d(TAG, "onResponse isMainThread : " + String.valueOf(Looper.myLooper() == Looper.getMainLooper()));
                        try {
                            for (int i = 0; i < response.length(); ++i) {
                                JSONObject rec = response.getJSONObject(i);
                                TUsers tUsers = new TUsers();
                                tUsers.setUserId(Integer.parseInt(rec.get("userId").toString()));
                                tUsers.setUserName(rec.get("userName").toString());
                                tUsers.setUserDate(new Date());
                                tUsers.setUserPhoto(rec.get("userPhoto").toString());
                                tUsers.setUserPhotopath(rec.get("userPhotopath").toString());
                                tUsersList.add(tUsers);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (okHttpResponse.isSuccessful()) {
                            Log.d(TAG, "onResponse success headers : " + okHttpResponse.headers().toString());
                        } else {
                            Log.d(TAG, "onResponse not success headers : " + okHttpResponse.headers().toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error : ", anError.getMessage());
                    }
                });
        return tUsersList;
    }

    @Override
    public String insert(TUsers params) {
        AndroidNetworking.post(restUrl + "/userse")
                .addBodyParameter(params)
                .setTag(this)
                .setPriority(Priority.LOW)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                })
                .getAsOkHttpResponseAndString(new OkHttpResponseAndStringRequestListener() {
                    @Override
                    public void onResponse(Response okHttpResponse, String response) {
                        Log.d(TAG, "onResponse object : " + response);
                        Log.d(TAG, "onResponse isMainThread : " + String.valueOf(Looper.myLooper() == Looper.getMainLooper()));
                        if (okHttpResponse.isSuccessful()) {
                            Log.d(TAG, "onResponse success headers : " + okHttpResponse.headers().toString());
                        } else {
                            Log.d(TAG, "onResponse not success headers : " + okHttpResponse.headers().toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error : ", anError.getMessage());
                    }
                });
        return "";
    }

    @Override
    public String update(TUsers params) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", String.valueOf(params.getUserId()));
            jsonObject.put("user_name", params.getUserName());
            jsonObject.put("user_date", params.getUserDate());
            jsonObject.put("user_photo", params.getUserPhoto());
            jsonObject.put("user_photopath", params.getUserPhotopath());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.put(restUrl + "/userse")
                .addJSONObjectBody(jsonObject)
                .setTag(this)
                .setPriority(Priority.LOW)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                })
                .getAsOkHttpResponseAndString(new OkHttpResponseAndStringRequestListener() {
                    @Override
                    public void onResponse(Response okHttpResponse, String response) {
                        Log.d(TAG, "onResponse object : " + response.toString());
                        Log.d(TAG, "onResponse isMainThread : " + String.valueOf(Looper.myLooper() == Looper.getMainLooper()));
                        if (okHttpResponse.isSuccessful()) {
                            Log.d(TAG, "onResponse success headers : " + okHttpResponse.headers().toString());
                        } else {
                            Log.d(TAG, "onResponse not success headers : " + okHttpResponse.headers().toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error : ", anError.getMessage());
                    }
                });
        return "";
    }

    @Override
    public String delete(TUsers params) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", String.valueOf(params.getUserId()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.delete(restUrl + "/userse")
                .addJSONObjectBody(jsonObject)
                .setTag(this)
                .setPriority(Priority.LOW)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                })
                .getAsOkHttpResponseAndString(new OkHttpResponseAndStringRequestListener() {
                    @Override
                    public void onResponse(Response okHttpResponse, String response) {
                        Log.d(TAG, "onResponse object : " + response);
                        Log.d(TAG, "onResponse isMainThread : " + String.valueOf(Looper.myLooper() == Looper.getMainLooper()));
                        if (okHttpResponse.isSuccessful()) {
                            Log.d(TAG, "onResponse success headers : " + okHttpResponse.headers().toString());
                        } else {
                            Log.d(TAG, "onResponse not success headers : " + okHttpResponse.headers().toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Error : ", anError.getMessage());
                    }
                });
        return "";
    }
}
