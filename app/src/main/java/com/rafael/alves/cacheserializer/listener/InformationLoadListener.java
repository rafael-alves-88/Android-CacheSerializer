package com.rafael.alves.cacheserializer.listener;

import com.rafael.alves.cacheserializer.model.Information;

public interface InformationLoadListener extends BaseListener {

    void onInformationLoaded(Information information);
}
