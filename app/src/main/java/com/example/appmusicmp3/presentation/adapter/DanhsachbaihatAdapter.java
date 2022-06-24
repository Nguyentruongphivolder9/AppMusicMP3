package com.example.appmusicmp3.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusicmp3.R;
import com.example.appmusicmp3.data.models.BaiHat;

import java.util.ArrayList;

public class DanhsachbaihatAdapter extends RecyclerView.Adapter<DanhsachbaihatAdapter.ViewHolder>{

    Context context;
    ArrayList<BaiHat> mangBaihat;

    public DanhsachbaihatAdapter(Context context, ArrayList<BaiHat> mangBaihat) {
        this.context = context;
        this.mangBaihat = mangBaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_danh_sach_bai_hat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat = mangBaihat.get(position);
        holder.tvTencasi.setText(baiHat.getCasi());
        holder.tvTenbaihat.setText(baiHat.getTenbaihat());
        holder.tvIndex.setText(position + 1 + "");
    }

    @Override
    public int getItemCount() {
        return mangBaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvIndex, tvTenbaihat, tvTencasi;
        ImageView imgLuotthich;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.textviewDanhsachIndex);
            tvTenbaihat = itemView.findViewById(R.id.textviewTenbaihat);
            tvTencasi = itemView.findViewById(R.id.textviewTencasi);
            imgLuotthich = itemView.findViewById(R.id.imageviewLuotthichdanhsachbaihat);
        }
    }
}
