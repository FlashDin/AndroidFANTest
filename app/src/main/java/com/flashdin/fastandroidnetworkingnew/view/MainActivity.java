package com.flashdin.fastandroidnetworkingnew.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.flashdin.fastandroidnetworkingnew.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnFast)
    public void btnFastClick() {
        Intent intent = new Intent(this.getApplicationContext(), FormActivity.class);
        startActivity(intent);
    }
}