/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.flashdin.fastandroidnetworkingnew.model.config;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flashdin.fastandroidnetworkingnew.R;
import com.flashdin.fastandroidnetworkingnew.model.entity.TUsers;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Getter;

/**
 * Provide views to RecyclerView with data from mList.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private List<TUsers> mList;

    public CustomAdapter(List<TUsers> ls) {
        mList = ls;
    }

    @Getter
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.lbUserId)
        TextView mLbId;
        @BindView(R.id.lbUserName)
        TextView mLbUsername;
        @BindView(R.id.imvUserPhoto)
        ImageView mImgUser;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        @OnClick()
        public void itemClick (View v) {
            Toast.makeText(v.getContext(), "Posisi : " + getAdapterPosition()
                    + " ID : " + mLbId.getText().toString(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listcontent, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
//        Log.d(TAG, "Element " + position + " set.");

        TUsers mItem = mList.get(position);
//        byte[] decodeString = Base64.decode(mItem.getUserPhoto(), Base64.DEFAULT);
//        Bitmap decodeByte = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
//        decodeByte = Bitmap.createScaledBitmap(decodeByte, 75, 75, true);
//        viewHolder.getMImgUser().setImageBitmap(decodeByte);
        viewHolder.getMLbId().setText(String.valueOf(mItem.getUserId()));
        viewHolder.getMLbUsername().setText(mItem.getUserName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
