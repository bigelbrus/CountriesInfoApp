package com.example.countriesinfoapp.data.database;

import android.content.Context;

import com.example.countriesinfoapp.utilities.RealmUtils;

import io.realm.Realm;

public class DatabaseDataSource{
    private static DatabaseDataSource sInstance;
    private static final Object LOCK = new Object();
    private Realm mRealm;

    public static DatabaseDataSource getInstance(Context context){
        if (sInstance == null){
            synchronized (LOCK){
                sInstance = new DatabaseDataSource(context);
            }
        }
        return sInstance;
    }
    private DatabaseDataSource(Context context){
        Realm.init(context);
        mRealm = Realm.getInstance(RealmUtils.getDefaultConfig());
    }

    public Realm getRealm(){
        return mRealm;
    }




}
