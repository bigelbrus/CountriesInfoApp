package com.example.countriesinfoapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.countriesinfoapp.R;
import com.example.countriesinfoapp.ui.details.DetailActivity;
import com.example.countriesinfoapp.utilities.FactoryUtils;

public class MainActivity extends AppCompatActivity implements CountryAdapter.CountryAdapterClickHandler {

    private MainViewModel mViewModel;
    private RecyclerView mCounriesList;
    private CountryAdapter mAdapter;
    private ProgressBar mLoading;
    private TextView mError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCounriesList = findViewById(R.id.rv_countries_list);
        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        mCounriesList.addItemDecoration(decoration);
        mLoading = findViewById(R.id.pb_loading_indicator);
        mError = findViewById(R.id.tv_error);
        mCounriesList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CountryAdapter(this,this);
        mCounriesList.setAdapter(mAdapter);

        MainViewModelFactory factory = FactoryUtils.getMainViewModelFactory(this);
        showLoading();
        mViewModel = new ViewModelProvider(this,factory).get(MainViewModel.class);
        mViewModel.getList().observe(this, countryList ->{
            if (countryList == null){
                showError();
            } else {
                mAdapter.setCountryList(countryList);
                showData();
            }
        });
    }

    @Override
    public void onItemClicked(String countryName) {
        Intent detailActivityStart = new Intent(this, DetailActivity.class);
        detailActivityStart.putExtra(Intent.EXTRA_COMPONENT_NAME,countryName);
        startActivity(detailActivityStart);
    }

    void showError(){
        mError.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.INVISIBLE);
        mCounriesList.setVisibility(View.INVISIBLE);
    }

    void showLoading(){
        mLoading.setVisibility(View.VISIBLE);
        mError.setVisibility(View.INVISIBLE);
        mCounriesList.setVisibility(View.INVISIBLE);
    }

    void showData(){
        mLoading.setVisibility(View.INVISIBLE);
        mError.setVisibility(View.INVISIBLE);
        mCounriesList.setVisibility(View.VISIBLE);
    }
}
