package com.flashdin.fastandroidnetworkingnew.view;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.bumptech.glide.Glide;
import com.flashdin.fastandroidnetworkingnew.R;
import com.flashdin.fastandroidnetworkingnew.model.entity.TUsers;
import com.flashdin.fastandroidnetworkingnew.presenter.BaseView;
import com.flashdin.fastandroidnetworkingnew.presenter.TUserPresenter;
import com.flashdin.fastandroidnetworkingnew.presenter.impl.TUserPresenterImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;

public class FormActivity extends AppCompatActivity implements BaseView {

    private TUserPresenter tUserPresenter;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    @BindView(R.id.imvUserPhoto)
    ImageView imvUserPhoto;
    @BindView(R.id.txtUserPhotopath)
    TextView txtUserPhotopath;
    @BindView(R.id.txtUserName)
    TextView txtUserName;
    @BindView(R.id.txtUserDate)
    TextView txtUserDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_test);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();
        AndroidNetworking.initialize(getApplicationContext(), okHttpClient);
        tUserPresenter = new TUserPresenterImpl();
        onAttachView();
        ButterKnife.bind(this);
    }

    @Override
    public void onAttachView() {
        tUserPresenter.onAttach(this);
    }

    @Override
    public void onDetachView() {
        tUserPresenter.onDetach();
    }

    @Override
    public void onShowFragment(TUsers data) {
        RecyclerViewFragment frg = new RecyclerViewFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction =
                manager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.frLayout, frg);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        onDetachView();
        super.onDestroy();
    }

    @OnClick(R.id.btnSave)
    public void btnSaveClick() {
        try {
            TUsers tUsers = new TUsers();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(txtUserDate.getText().toString());
            tUsers.setUserName(txtUserName.getText().toString());
            tUsers.setUserDate(date);
            Bitmap bitmap = ((BitmapDrawable) imvUserPhoto.getDrawable()).getBitmap(); //from imageView
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] ar = bos.toByteArray();
            String mFoto = Base64.encodeToString(ar, Base64.DEFAULT);
            tUsers.setUserPhoto(mFoto);
            tUsers.setUserPhotopath(txtUserPhotopath.getText().toString());
            tUserPresenter.insert(tUsers);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btnUpdate)
    public void btnUpdateClick() {
        try {
            TUsers tUsers = new TUsers();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(txtUserDate.getText().toString());
            tUsers.setUserId(3);
            tUsers.setUserName(txtUserName.getText().toString());
            tUsers.setUserDate(date);
            Bitmap bitmap = ((BitmapDrawable) imvUserPhoto.getDrawable()).getBitmap(); //from imageView
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] ar = bos.toByteArray();
            String mFoto = Base64.encodeToString(ar, Base64.DEFAULT);
            tUsers.setUserPhoto(mFoto);
            tUsers.setUserPhotopath(txtUserPhotopath.getText().toString());
            tUserPresenter.update(tUsers);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btnDelete)
    public void btnDeleteClick() {
        TUsers tUsers = new TUsers();
        tUsers.setUserId(5);
        tUserPresenter.delete(tUsers);
    }

    @OnClick(R.id.btnRefresh)
    public void btnRefreshClick(Button button) {
//        button.setVisibility(BaseView.GONE);
        tUserPresenter.showFragment();
    }

    @OnClick({R.id.btnSelectImg})
    public void btnSelectImgClick() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Foto"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                Bitmap decodeByte = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                decodeByte.compress(Bitmap.CompressFormat.PNG, 75, bos);
                Glide.with(this).load(decodeByte).into(imvUserPhoto);
                txtUserPhotopath.setText(filePath.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.txtUserDate)
    public void txtUserDate(final TextView txtUserDate) {
        Calendar newCalendar = Calendar.getInstance();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                txtUserDate.setText(dateFormat.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

}
