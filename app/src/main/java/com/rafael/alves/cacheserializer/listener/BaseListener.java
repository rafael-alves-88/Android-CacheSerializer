package com.rafael.alves.cacheserializer.listener;

public interface BaseListener {

    void onRequestStarted();

    void onRequestFinished();

    void onError(Throwable e);
}
