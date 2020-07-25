package com.example.loginapp.Fragments;

import com.example.loginapp.Model.Images;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.loginapp.API.APIClient;
import com.example.loginapp.API.APIInterface;
import com.example.loginapp.Adapter.DataAdapter;

import com.example.loginapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class View_Images extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_photos_api, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        APIInterface service;
        RecyclerView mRecyclerView;
        DataAdapter adapter;
        LinearLayoutManager linearLayoutManager;
        ProgressBar progressBar;
        LinearLayout mainLayout;
        mRecyclerView = view.findViewById(R.id.recycleViewer);
        progressBar = view.findViewById(R.id.main_progress);
        adapter = new DataAdapter(view.getContext());
        mainLayout = view.findViewById(R.id.searchLinearLayout);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        service = APIClient.getClient().create(APIInterface.class);
        progressBar.setVisibility(View.VISIBLE);
        LinearLayout finalMainLayout = mainLayout;
        service.getUserDetails().enqueue(new Callback<List<Images>>() {
            @Override
            public void onResponse(Call<List<Images>> call, Response<List<Images>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.e("Response Code", response.code() + "");
                if (response.isSuccessful() && response.code() == 200) {
                    List<Images> results = response.body();
                    if (results.size() == 0) {
                        LinearLayout mainLayout = view.findViewById(R.id.searchLinearLayout);
                        mainLayout.removeAllViews();
                        // inflate (create) another copy of our custom layout
                        LayoutInflater inflater = getLayoutInflater();
                        View myLayout = inflater.inflate(R.layout.no_results, mainLayout, false);
                        mainLayout.addView(myLayout);
                    }
                    adapter.addAll(results);

                    for (int i = 0; i < results.size(); i++) {

                    }
                } else {
                    // error case
                    switch (response.code()) {
                        case 404:
                            Toast.makeText(getContext(), "Not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(getContext(), "Server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getContext(), "Unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Images>> call, Throwable throwable) {
                finalMainLayout.removeAllViews();
                // inflate (create) another copy of our custom layout
                LayoutInflater inflater = getLayoutInflater();
                View myLayout = inflater.inflate(R.layout.network_failure, finalMainLayout, false);
                finalMainLayout.addView(myLayout);
                Toast.makeText(getContext(), "Following error occurred-: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}