
package com.flashdin.fastandroidnetworkingnew.view;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.OkHttpResponseAndJSONArrayRequestListener;
import com.flashdin.fastandroidnetworkingnew.R;
import com.flashdin.fastandroidnetworkingnew.model.config.CustomAdapter;
import com.flashdin.fastandroidnetworkingnew.model.config.config;
import com.flashdin.fastandroidnetworkingnew.model.entity.TUsers;
import com.flashdin.fastandroidnetworkingnew.presenter.TUserPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Response;

public class RecyclerViewFragment extends Fragment {

    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected List<TUsers> ls;
    protected TUserPresenter tUserPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ls = new ArrayList<>();
        TUsers tUsers = new TUsers();
        tUsers.setUserName("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.recycler_view_frag, container, false);
        rootView.setTag(TAG);
        AndroidNetworking.get(config.restUrl.getUrl() + "/userse/{userName}")
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
                                ls.add(tUsers);
                            }
                            mRecyclerView = rootView.findViewById(R.id.recycler_view);
                            mLayoutManager = new LinearLayoutManager(getActivity());
                            mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                            if (savedInstanceState != null) {
                                mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                                        .getSerializable(KEY_LAYOUT_MANAGER);
                            }
                            setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
                            mAdapter = new CustomAdapter(ls);
                            mRecyclerView.setAdapter(mAdapter);
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
        return rootView;
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }

}
