package com.rafael.alves.cacheserializer.repository.contract;

import com.rafael.alves.cacheserializer.model.Information;

public interface InformationContract extends BaseContract {

    void onInformationLoadedFromCache(Information information);

    void saveInformationToCache();

    void loadInformationFromCache();
}
