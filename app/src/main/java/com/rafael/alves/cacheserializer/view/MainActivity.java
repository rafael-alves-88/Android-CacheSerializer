package com.rafael.alves.cacheserializer.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.rafael.alves.cacheserializer.R;
import com.rafael.alves.cacheserializer.repository.contract.InformationContract;
import com.rafael.alves.cacheserializer.model.Information;
import com.rafael.alves.cacheserializer.presenter.MainPresenter;
import com.rafael.alves.cacheserializer.utils.Permissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements InformationContract {

    //region [ ButterKnife ]
    @BindView(R.id.etField1) EditText etField1;
    @BindView(R.id.etField2) EditText etField2;
    @BindView(R.id.rlLoading) RelativeLayout rlLoading;
    //endregion

    //region [ Private Members ]
    private MainPresenter mMainPresenter;
    private boolean mHasStoragePermission;
    //endregion

    //region [ LifeCycle ]
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMainPresenter = new MainPresenter(this);
        checkPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Permissions.PERMISSION_READ_EXTERNAL_STORAGE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mHasStoragePermission = true;
            loadInformationFromCache();
        } else {
            mHasStoragePermission = false;
            showToast(getResources().getString(R.string.permission_revoked_read_external_storage));
        }
    }
    //endregion

    //region [ Contract ]
    @Override
    public void showLoadingDialog() {
        rlLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingDialog() {
        rlLoading.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return MainActivity.this;
    }

    @Override
    public void onInformationLoadedFromCache(Information information) {
        etField1.setText(information.Field1);
        etField2.setText(information.Field2);
    }

    @Override
    public void saveInformationToCache() {
        if (checkFields()) {
            if (mHasStoragePermission) {
                mMainPresenter.saveInformationToCache(etField1.getText().toString(), etField2.getText().toString());
            }
        } else {
            showToast(getResources().getString(R.string.error_empty_field));
        }
    }

    @Override
    public void loadInformationFromCache() {
        if (mHasStoragePermission) {
            mMainPresenter.loadInformationFromCache();
        }
    }
    //endregion

    //region [ Buttons ]
    @OnClick(R.id.btnSave)
    public void onBtnSave() {
        saveInformationToCache();
    }

    @OnClick(R.id.btnLoad)
    public void onBtnLoad() {
        loadInformationFromCache();
    }
    //endregion

    //region [ Private Methods ]
    private boolean checkFields() {
        return etField1.getText().length() > 0 && etField2.getText().length() > 0;
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            mHasStoragePermission = true;
        } else {
            mHasStoragePermission = false;
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Permissions.PERMISSION_READ_EXTERNAL_STORAGE);
        }
    }
    //endregion
}
