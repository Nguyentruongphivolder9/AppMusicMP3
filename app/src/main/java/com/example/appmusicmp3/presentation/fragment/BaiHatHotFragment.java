package com.example.appmusicmp3.presentation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusicmp3.R;
import com.example.appmusicmp3.data.datasources.remote.APIService;
import com.example.appmusicmp3.data.datasources.remote.AppConstant;
import com.example.appmusicmp3.data.models.BaiHat;
import com.example.appmusicmp3.presentation.adapter.BaiHatHotAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BaiHatHotFragment extends Fragment {

    View view;
    RecyclerView recyclerViewBaihathot;
    BaiHatHotAdapter baiHatHotAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_bai_hat_hot, container, false);
        recyclerViewBaihathot = view.findViewById(R.id.recyclerviewBaihathot);
        init();
        return  view;
    }

    private void init() {
        APIService apiService = AppConstant.getService();
        Call<List<BaiHat>> callBaihat = apiService.getBaiHatHot();
        callBaihat.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                ArrayList<BaiHat> baiHatArrayList = (ArrayList<BaiHat>) response.body();
                baiHatHotAdapter = new BaiHatHotAdapter(getActivity(), baiHatArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewBaihathot.setLayoutManager(linearLayoutManager);
                recyclerViewBaihathot.setAdapter(baiHatHotAdapter);
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
}

