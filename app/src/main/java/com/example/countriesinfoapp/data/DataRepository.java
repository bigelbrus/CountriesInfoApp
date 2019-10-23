package com.example.countriesinfoapp.data;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.countriesinfoapp.AppExecutors;
import com.example.countriesinfoapp.data.database.DBCountry;
import com.example.countriesinfoapp.data.database.DatabaseDataSource;
import com.example.countriesinfoapp.data.network.Country;
import com.example.countriesinfoapp.data.network.CountryNames;
import com.example.countriesinfoapp.data.network.Currency;
import com.example.countriesinfoapp.data.network.JSONCountriesApi;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {
    private static DataRepository sInstance;
    private static final Object LOCK = new Object();
    private static final String LOG = DataRepository.class.getSimpleName();
    private Realm mRealm;
    private JSONCountriesApi mApi;
    private AppExecutors mExecutors;
    private MutableLiveData<List<CountryNames>> mList = new MutableLiveData<>();
    private MutableLiveData<Country> mCountry = new MutableLiveData<>();

    public static DataRepository getInstance(JSONCountriesApi api, Realm realm, AppExecutors executors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d("tag", "DataRepository instantiate");
                sInstance = new DataRepository(api, realm, executors);
            }
        }
        Log.d("tag", "DataRepository return instance");

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
    }

    public LiveData<List<CountryNames>> getList() {
        return mList;
    }


    public LiveData<Country> getCountryByName(String countryName) {
        DBCountry currentCountryFromDB = mRealm.where(DBCountry.class).contains("name", countryName).findFirst();
        Log.d(LOG, currentCountryFromDB.getName());
        if (currentCountryFromDB.getCapital() != null) {
            Log.d("tag", "Download Country object from realm");
            MutableLiveData<Country> data = new MutableLiveData<>();
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
            mApi.getCountryByName(countryName).enqueue(new Callback<List<Country>>() {

                @Override
                public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                    mCountry.setValue(response.body().get(0));
                    Log.d("tag", "Download Country object from internet");
                }

                @Override
                public void onFailure(Call<List<Country>> call, Throwable t) {

                }
            });
        });
        mCountry.observeForever(country -> {
            mCountry.removeObserver(r -> {
            });
            mRealm.beginTransaction();
            DBCountry c = mRealm.where(DBCountry.class).contains("name", country.getName()).findFirst();
            c.setValues(country.getCapital()
                    , country.getCurrencies().get(0).getName()
                    , country.getRegion()
                    , country.getPopulation()
                    , country.getFlag());
            mRealm.copyToRealmOrUpdate(c);
            mRealm.commitTransaction();

            Log.d("tag", "Put Country object to realm");

        });
    }

    public LiveData<Country> getCountry() {
        return mCountry;
    }

}
