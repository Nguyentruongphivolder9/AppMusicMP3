package com.example.appmusicmp3.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appmusicmp3.R;
import com.example.appmusicmp3.data.models.Album;

import java.util.ArrayList;

public class AlbumHotAdapter extends RecyclerView.Adapter<AlbumHotAdapter.ViewHolder>{

    Context context;
    ArrayList<Album> mangAlbum;

    public AlbumHotAdapter(Context context, ArrayList<Album> mangAlbum) {
        this.context = context;
        this.mangAlbum = mangAlbum;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_album, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = mangAlbum.get(position);
        holder.tvTencasiAlbum.setText(album.getTenCaSiAlbum());
        holder.tvTenAlbum.setText(album.getTenAlbum());
        Glide.with(context).load(album.getHinhAnhAlbum()).into(holder.imgHinhAlbum);
    }

    @Override
    public int getItemCount() {
        return mangAlbum.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinhAlbum;
        TextView tvTenAlbum, tvTencasiAlbum;
        public ViewHolder(View itemView) {
            super(itemView);
            imgHinhAlbum = itemView.findViewById(R.id.imageviewAlbum);
            tvTenAlbum = itemView.findViewById(R.id.textviewtenAlbum);
            tvTencasiAlbum = itemView.findViewById(R.id.textviewtencasiAlbum);
        }
    }
}
