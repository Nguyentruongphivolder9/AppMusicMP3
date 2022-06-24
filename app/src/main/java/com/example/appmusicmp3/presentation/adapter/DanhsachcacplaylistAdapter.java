package com.example.appmusicmp3.presentation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appmusicmp3.Activity.DanhsachcacplaylistActivity;
import com.example.appmusicmp3.R;
import com.example.appmusicmp3.data.models.Playlist;

import java.util.ArrayList;

import retrofit2.Call;

public class DanhsachcacplaylistAdapter extends RecyclerView.Adapter<DanhsachcacplaylistAdapter.ViewHolder>{

    Context context;
    ArrayList<Playlist> mangPlaylist;

    public DanhsachcacplaylistAdapter(Context context, ArrayList<Playlist> mangPlaylist) {
        this.context = context;
        this.mangPlaylist = mangPlaylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_danh_sach_cac_playlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Playlist playlist = mangPlaylist.get(position);
        Glide.with(context).load(playlist.getIcon()).into(holder.imghinhnen);
        holder.tvTenplaylist.setText(playlist.getTen());
    }

    @Override
    public int getItemCount() {
        return mangPlaylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imghinhnen;
        TextView tvTenplaylist;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imghinhnen = itemView.findViewById(R.id.imageviewDanhsachcacplaylist);
            tvTenplaylist = itemView.findViewById(R.id.textviewTenDanhsachcacplaylist);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DanhsachcacplaylistActivity.class);
                    intent.putExtra("itemplaylist", mangPlaylist.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
