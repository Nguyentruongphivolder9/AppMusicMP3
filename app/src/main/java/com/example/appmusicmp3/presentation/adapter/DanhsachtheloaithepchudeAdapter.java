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
import com.example.appmusicmp3.Activity.DanhsachbaihatActivity;
import com.example.appmusicmp3.R;
import com.example.appmusicmp3.data.models.TheLoai;

import java.util.ArrayList;

public class DanhsachtheloaithepchudeAdapter extends RecyclerView.Adapter<DanhsachtheloaithepchudeAdapter.ViewHolder>{

    Context context;
    ArrayList<TheLoai> mangTheloai;

    public DanhsachtheloaithepchudeAdapter(Context context, ArrayList<TheLoai> mangTheloai) {
        this.context = context;
        this.mangTheloai = mangTheloai;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_the_loai_theo_chu_de, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TheLoai theLoai = mangTheloai.get(position);
        Glide.with(context).load(theLoai.getHinhTheLoai()).into(holder.imgHinhnen);
        holder.tvTentheloai.setText(theLoai.getTenTheLoai());
    }

    @Override
    public int getItemCount() {
        return mangTheloai.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgHinhnen;
        TextView tvTentheloai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhnen = itemView.findViewById(R.id.imageviewTheloaitheochude);
            tvTentheloai = itemView.findViewById(R.id.textviewTentheloaitheochude);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DanhsachbaihatActivity.class);
                    intent.putExtra("idtheloai", mangTheloai.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
