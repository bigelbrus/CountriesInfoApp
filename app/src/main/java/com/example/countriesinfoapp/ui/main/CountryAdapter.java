package com.example.countriesinfoapp.ui.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countriesinfoapp.R;
import com.example.countriesinfoapp.data.network.Country;
import com.example.countriesinfoapp.data.network.CountryNames;

import java.util.List;
import java.util.zip.Inflater;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    private final CountryAdapterClickHandler mClickHandler;
    private Context mContext;
    private List<CountryNames> mCountryList;

    public CountryAdapter(Context context, CountryAdapterClickHandler clickHandler) {
        mClickHandler = clickHandler;
        mContext = context;
        Log.d("tag","adapter");
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country
                ,parent
        ,false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        holder.countryName.setText(mCountryList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (mCountryList == null) {
            return 0;
        }
            return mCountryList.size();
    }

    public void setCountryList(List<CountryNames> list){
        mCountryList = list;
        notifyDataSetChanged();
    }


    public interface CountryAdapterClickHandler {
        void onItemClicked(String name);
    }

    class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView countryName;

        public CountryViewHolder(View itemView) {
            super(itemView);

            countryName = itemView.findViewById(R.id.tv_country_item);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onItemClicked(countryName.getText().toString());
        }
    }
}
