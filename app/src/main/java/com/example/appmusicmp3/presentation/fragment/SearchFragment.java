package com.example.appmusicmp3.presentation.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appmusicmp3.R;
import com.example.appmusicmp3.data.datasources.remote.APIService;
import com.example.appmusicmp3.data.datasources.remote.AppConstant;
import com.example.appmusicmp3.data.models.BaiHat;
import com.example.appmusicmp3.presentation.adapter.SearchbaihatAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    View view;
    Toolbar toolbar;
    RecyclerView recyclerViewSearch;
    TextView tvKhongcodulieu;
    SearchbaihatAdapter searchbaihatAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        toolbar = view.findViewById(R.id.tooolbarSearch);
        recyclerViewSearch = view.findViewById(R.id.rcvSearch);
        tvKhongcodulieu = view.findViewById(R.id.textviewKhongcodulieu);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Tìm kiếm bài hát");
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_view, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchTukhoaBaihat(query);
                Log.d("BBB",query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchTukhoaBaihat(newText);
                Log.d("BBB",newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void SearchTukhoaBaihat(String query) {
        APIService apiService = AppConstant.getService();
        Call<List<BaiHat>> callback = apiService.getSearchBaihat(query);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                ArrayList<BaiHat> mangbaihat = (ArrayList<BaiHat>) response.body();
                if (mangbaihat.size() > 0) {
                    searchbaihatAdapter = new SearchbaihatAdapter(getActivity(), mangbaihat);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerViewSearch.setLayoutManager(linearLayoutManager);
                    recyclerViewSearch.setAdapter(searchbaihatAdapter);
                    tvKhongcodulieu.setVisibility(View.GONE);
                    recyclerViewSearch.setVisibility(View.VISIBLE);
                }else {
                    recyclerViewSearch.setVisibility(View.GONE);
                    tvKhongcodulieu.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
}
