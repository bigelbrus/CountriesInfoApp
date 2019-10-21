package com.example.countriesinfoapp.data.network;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.countriesinfoapp.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkDataSource {
    private static final String BASE_URL = "https://restcountries.eu";

    private static NetworkDataSource sInstance;
    private static final Object LOCK = new Object();
    private final Context mContext;

    private Retrofit mRetrofit;

    private AppExecutors mExecutors;
    private LiveData<List<Country>> mListCountry;
    private final MutableLiveData<Country> mCountry;


    private NetworkDataSource(Context context,AppExecutors executors) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mContext = context;
        mExecutors = executors;
        mListCountry = new MutableLiveData<>();
        mCountry = new MutableLiveData<>();
    }

    public LiveData<Country> getCurrentCountry(){
        return mCountry;
    }

    void downloadCountry(String name){
        getJSONApi().getCountryByName(name).enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                mCountry.setValue(response.body().get(0));
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {

            }
        });
    }

    public static NetworkDataSource getInstance(Context context, AppExecutors executors) {
        if (sInstance == null) {
            synchronized (LOCK){
                sInstance = new NetworkDataSource(context,executors);
            }
        }
        return sInstance;
    }

    public JSONCountriesApi getJSONApi() {
        return mRetrofit.create(JSONCountriesApi.class);
    }
}
