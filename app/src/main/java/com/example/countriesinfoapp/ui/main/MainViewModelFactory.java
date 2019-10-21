package com.example.countriesinfoapp.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.countriesinfoapp.AppExecutors;
import com.example.countriesinfoapp.data.DataRepository;
import com.example.countriesinfoapp.data.network.Country;

import java.util.List;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final DataRepository mRepository;

    public MainViewModelFactory(DataRepository repository){
        mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(mRepository);
    }
}
