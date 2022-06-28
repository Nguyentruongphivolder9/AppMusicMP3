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
import com.example.appmusicmp3.Activity.DanhsachallAlbumActivity;
import com.example.appmusicmp3.Activity.DanhsachbaihatActivity;
import com.example.appmusicmp3.R;
import com.example.appmusicmp3.data.models.Album;

import java.util.ArrayList;

public class DanhsachAllAlbumAdapter extends RecyclerView.Adapter<DanhsachAllAlbumAdapter.ViewHolder>{

    Context context;
    ArrayList<Album> mangallalbum;

    public DanhsachAllAlbumAdapter(Context context, ArrayList<Album> mangallalbum) {
        this.context = context;
        this.mangallalbum = mangallalbum;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_all_album, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = mangallalbum.get(position);
        Glide.with(context).load(album.getHinhAnhAlbum()).into(holder.imgAllAlbum);
        holder.tvTenallAlbum.setText(album.getTenAlbum());
    }

    @Override
    public int getItemCount() {
        return mangallalbum.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgAllAlbum;
        TextView tvTenallAlbum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAllAlbum = itemView.findViewById(R.id.imageviewAllAlbum);
            tvTenallAlbum = itemView.findViewById(R.id.textviewtenAlbum);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DanhsachbaihatActivity.class);
                    intent.putExtra("album", mangallalbum.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
