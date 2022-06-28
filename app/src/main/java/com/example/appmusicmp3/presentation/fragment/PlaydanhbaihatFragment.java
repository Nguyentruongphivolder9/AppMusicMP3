package com.example.appmusicmp3.presentation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusicmp3.Activity.PlayNhacActivity;
import com.example.appmusicmp3.R;
import com.example.appmusicmp3.presentation.adapter.PlaynhacAdapter;

public class PlaydanhbaihatFragment extends Fragment {

    View view;
    RecyclerView recyclerViewPlaynhac;
    PlaynhacAdapter playnhacAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_danh_sach_bai_hat, container, false);
        recyclerViewPlaynhac = view.findViewById(R.id.rcvPlaybaihat);
        if (PlayNhacActivity.mangbaihat.size() > 0) {
            playnhacAdapter = new PlaynhacAdapter(getActivity(), PlayNhacActivity.mangbaihat);
            recyclerViewPlaynhac.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewPlaynhac.setAdapter(playnhacAdapter);
        }
        return view;
    }
}
