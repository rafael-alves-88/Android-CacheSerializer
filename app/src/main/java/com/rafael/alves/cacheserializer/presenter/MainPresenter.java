package com.rafael.alves.cacheserializer.presenter;

import com.rafael.alves.cacheserializer.R;
import com.rafael.alves.cacheserializer.listener.InformationLoadListener;
import com.rafael.alves.cacheserializer.listener.InformationSaveListener;
import com.rafael.alves.cacheserializer.model.Information;
import com.rafael.alves.cacheserializer.repository.contract.InformationContract;
import com.rafael.alves.cacheserializer.repository.interactor.InformationInteractor;

public class MainPresenter {

    private InformationContract mView;
    private InformationInteractor mInformationInteractor;

    public MainPresenter(InformationContract view) {
        mView = view;
        mInformationInteractor = new InformationInteractor();
    }

    public void saveInformationToCache(String field1, String field2) {
        mView.showLoadingDialog();

        Information information = new Information();
        information.Field1 = field1;
        information.Field2 = field2;
        mInformationInteractor.saveToCache(mView.getContext(), information, new InformationSaveListener() {
            @Override
            public void onInformationSaved(boolean success) {
                if (success) {
                    mView.showToast(mView.getContext().getString(R.string.success_save_to_cache));
                } else {
                    mView.showToast(mView.getContext().getString(R.string.error_save_to_cache));
                }
            }

            @Override
            public void onRequestStarted() {
                mView.showLoadingDialog();
            }

            @Override
            public void onRequestFinished() {
                mView.hideLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                mView.showToast(e.getMessage());
            }  
        });
        
        mView.hideLoadingDialog();
    }

    public void loadInformationFromCache() {
        mInformationInteractor.readFromCache(mView.getContext(), new InformationLoadListener() {
            @Override
            public void onInformationLoaded(Information information) {
                mView.onInformationLoadedFromCache(information);
            }

            @Override
            public void onRequestStarted() {
                mView.showLoadingDialog();
            }

            @Override
            public void onRequestFinished() {
                mView.hideLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                mView.showToast(e.getMessage());
            }
        });
    }
}
