package com.rafael.alves.cacheserializer.repository.interactor;

import android.content.Context;
import android.os.AsyncTask;

import com.rafael.alves.cacheserializer.listener.InformationLoadListener;
import com.rafael.alves.cacheserializer.listener.InformationSaveListener;
import com.rafael.alves.cacheserializer.model.Information;
import com.rafael.alves.cacheserializer.utils.Serializer;

import java.io.FileNotFoundException;
import java.io.IOException;

public class InformationInteractor {

    public void readFromCache(final Context context, final InformationLoadListener informationLoadListener)  {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                informationLoadListener.onRequestStarted();
            }

            @Override
            protected Void doInBackground(Void... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try {
                    informationLoadListener.onInformationLoaded((Information) new Serializer(context).Read(String.format("%s", Information.SERIALIZER_UUID)));
                } catch (FileNotFoundException nf) {

                } catch (IOException | ClassNotFoundException e) {
                    informationLoadListener.onError(e);
                }

                informationLoadListener.onRequestFinished();
            }
        }.execute();
    }

    public void saveToCache(final Context context, final Information information, final InformationSaveListener informationSaveListener) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                informationSaveListener.onRequestStarted();
            }

            @Override
            protected Void doInBackground(Void... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try {
                    new Serializer(context).Write(String.format("%s", Information.SERIALIZER_UUID), information);
                    informationSaveListener.onInformationSaved(true);
                } catch (Exception e) {
                    informationSaveListener.onInformationSaved(false);
                }

                informationSaveListener.onRequestFinished();
            }
        }.execute();
    }
}
