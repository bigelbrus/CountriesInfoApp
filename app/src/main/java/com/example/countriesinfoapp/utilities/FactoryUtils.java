package com.example.countriesinfoapp.utilities;

import android.content.Context;
import android.util.Log;

import com.example.countriesinfoapp.AppExecutors;
import com.example.countriesinfoapp.data.DataRepository;
import com.example.countriesinfoapp.data.database.DatabaseDataSource;
import com.example.countriesinfoapp.data.network.Country;
import com.example.countriesinfoapp.data.network.JSONCountriesApi;
import com.example.countriesinfoapp.data.network.NetworkDataSource;
import com.example.countriesinfoapp.ui.details.DetailViewModelFactory;
import com.example.countriesinfoapp.ui.main.MainViewModelFactory;

public class FactoryUtils {

    public static DataRepository getRepo(Context context){
        AppExecutors executors = AppExecutors.getInstance();
        DatabaseDataSource db = DatabaseDataSource.getInstance(context);
        JSONCountriesApi api = NetworkDataSource.getInstance(context,executors).getJSONApi();

        return DataRepository.getInstance(api,db.getRealm(),executors);
    }

    public static MainViewModelFactory getMainViewModelFactory(Context context){
        DataRepository repository = getRepo(context);
        return new MainViewModelFactory(repository);
    }

    public static DetailViewModelFactory getDetailViewModelFactory(Context context, String countryName){
        DataRepository repository = getRepo(context);
        return new DetailViewModelFactory(repository,countryName);
    }
}
