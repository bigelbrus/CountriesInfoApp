package com.example.countriesinfoapp.data;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.countriesinfoapp.AppExecutors;
import com.example.countriesinfoapp.data.database.DBCountry;
import com.example.countriesinfoapp.data.database.DatabaseDataSource;
import com.example.countriesinfoapp.data.network.Country;
import com.example.countriesinfoapp.data.network.CountryNames;
import com.example.countriesinfoapp.data.network.Currency;
import com.example.countriesinfoapp.data.network.JSONCountriesApi;
import com.example.countriesinfoapp.ui.details.DetailActivity;
import com.example.countriesinfoapp.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {
    private static DataRepository sInstance;
    private static final Object LOCK = new Object();
    private Realm mRealm;
    private JSONCountriesApi mApi;
    private AppExecutors mExecutors;
    private MutableLiveData<List<CountryNames>> mList = new MutableLiveData<>();
    private MutableLiveData<Country> mCountry = new MutableLiveData<>();

    public static DataRepository getInstance(JSONCountriesApi api, Realm realm, AppExecutors executors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d("tag", "getInstance data");
                sInstance = new DataRepository(api, realm, executors);
            }
        }
        Log.d("tag", "DataRepository getInstance data return");

        return sInstance;
    }

    private DataRepository(JSONCountriesApi api, Realm realm, AppExecutors executors) {
        mApi = api;
        mRealm = realm;
        mExecutors = executors;
        downloadCountries();

    }

    private void downloadCountries() {
        mExecutors.networkIO().execute(() -> {
            mApi.getAllCountries().enqueue(new Callback<List<CountryNames>>() {
                @Override
                public void onResponse(Call<List<CountryNames>> call, Response<List<CountryNames>> response) {
                    mList.setValue(response.body());
                }

                @Override
                public void onFailure(Call<List<CountryNames>> call, Throwable t) {
                    t.printStackTrace();
                    mList.setValue(null);
                }
            });
        });
        mList.observeForever(newData -> {
            mRealm.beginTransaction();
            for (CountryNames c : newData) {
                mRealm.copyToRealmOrUpdate(new DBCountry(c.getName()));
            }
            mRealm.commitTransaction();
        });

//        getRealmObjects();
    }

    public LiveData<List<CountryNames>> getList() {
        return mList;
    }


    public LiveData<Country> getCountryByName(String countryName) {
        DBCountry currentCountryFromDB = mRealm.where(DBCountry.class).contains("name",countryName).findFirst();
        Log.d("tag",currentCountryFromDB.getName());
        if (currentCountryFromDB.getCapital() != null){
            Log.d("tag","download from db");
            MutableLiveData<Country> data= new MutableLiveData<>();
            List<Currency> currencyList = new ArrayList<>();
            currencyList.add(new Currency(currentCountryFromDB.getCurrency()));

            data.setValue(new Country(currentCountryFromDB.getName(),
                    currentCountryFromDB.getCapital(),
                    currentCountryFromDB.getRegion(),
                    currentCountryFromDB.getPopulation(),
                    currencyList,
                    currentCountryFromDB.getFlag()));
            return data;
        } else {

            getCountryByNameFromNet(countryName);
            return mCountry;
        }

    }

    private void getCountryByNameFromNet(String countryName) {

        mExecutors.networkIO().execute(() -> {
            Log.d("tag", "download from internet start");

            mApi.getCountryByName(countryName).enqueue(new Callback<List<Country>>() {

                @Override
                public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                    mCountry.setValue(response.body().get(0));
                    Log.d("tag", "download from internet");
                }

                @Override
                public void onFailure(Call<List<Country>> call, Throwable t) {

                }
            });
        });
        mCountry.observeForever(country -> {
            Log.d("tag","put to realm start");
            mRealm.beginTransaction();
            DBCountry c = mRealm.where(DBCountry.class).contains("name", country.getName()).findFirst();
            c.setValues(c.getCapital()
                    , c.getCurrency()
                    , c.getRegion()
                    , c.getPopulation()
                    , c.getFlag());
            mRealm.copyToRealmOrUpdate(c);
            mRealm.commitTransaction();

            Log.d("tag","put to realm "+ c.getCapital());

        });
    }

    public LiveData<Country> getCountry (){
        return mCountry;
    }

//    public void getRealmObjects() {
//        long result = mRealm.where(DBCountry.class).count();
//        Log.d("tag", "Countries " + result);
//        RealmResults<DBCountry> results = mRealm.where(DBCountry.class).contains("name","Rus").findAll();
//        for (DBCountry d:results
//             ) {
//               Log.d("tag",d.getName());
//        }
//    }

}
