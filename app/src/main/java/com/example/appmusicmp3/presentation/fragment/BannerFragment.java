package com.example.appmusicmp3.presentation.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appmusicmp3.R;
import com.example.appmusicmp3.data.datasources.remote.APIService;
import com.example.appmusicmp3.data.datasources.remote.AppConstant;
import com.example.appmusicmp3.data.models.QuangCao;
import com.example.appmusicmp3.presentation.adapter.BannerAdapter;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BannerFragment extends Fragment {

    View view;
    ViewPager2 viewPager2;
    CircleIndicator3 circleIndicator3;
    BannerAdapter bannerAdapter;
    ArrayList<QuangCao> arrayListQuangcao;
    Handler handler;
    Runnable runnable;
    int currentItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner, container, false);
        init();
        event();
        return view;}

    private void event() {
        viewPager2 = view.findViewById(R.id.bannerViewPager);
        circleIndicator3 = view.findViewById(R.id.bannerCircleIndicator);
    }

    private void init() {
        APIService apiService = AppConstant.getService();
        Call<List<QuangCao>> callBanner = apiService.getDataBanner();
        callBanner.enqueue(new Callback<List<QuangCao>>() {
            @Override
            public void onResponse(Call<List<QuangCao>> call, Response<List<QuangCao>> response) {
                arrayListQuangcao = (ArrayList<QuangCao>) response.body();
                bannerAdapter = new BannerAdapter(getActivity(), arrayListQuangcao);
                viewPager2.setAdapter(bannerAdapter);
                circleIndicator3.setViewPager(viewPager2);
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        currentItem = viewPager2.getCurrentItem();
                        currentItem++;
                        if (currentItem >= viewPager2.getAdapter().getItemCount()) {
                            currentItem = 0;
                        }
                        viewPager2.setCurrentItem(currentItem, true);
                        handler.postDelayed(runnable, 4500);
                    }
                };
                handler.postDelayed(runnable, 4500);
            }

            @Override
            public void onFailure(Call<List<QuangCao>> call, Throwable t) {

            }
        });
    }
}
