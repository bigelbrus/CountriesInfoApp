package com.example.countriesinfoapp.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.countriesinfoapp.data.DataRepository;
import com.example.countriesinfoapp.data.network.Country;
import com.example.countriesinfoapp.data.network.CountryNames;

import java.util.List;

public class MainViewModel extends ViewModel {
    private LiveData<List<CountryNames>> mList;
    private DataRepository mRepository;

    public MainViewModel(DataRepository repository){
        mRepository = repository;
        mList = mRepository.getList();
    }

    public LiveData<List<CountryNames>> getList(){
        return mList;
    }

}
