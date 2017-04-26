package com.rafael.alves.cacheserializer.repository.contract;

import android.content.Context;

public interface BaseContract {

    void showLoadingDialog();

    void hideLoadingDialog();

    void showToast(String message);

    Context getContext();
}
