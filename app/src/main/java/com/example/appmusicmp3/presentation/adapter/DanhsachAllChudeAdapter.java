package com.example.appmusicmp3.presentation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appmusicmp3.Activity.DanhsachtheloaithechudeActivity;
import com.example.appmusicmp3.R;
import com.example.appmusicmp3.data.models.ChuDe;

import java.util.ArrayList;

public class DanhsachAllChudeAdapter extends RecyclerView.Adapter<DanhsachAllChudeAdapter.ViewHolder>{

    Context context;
    ArrayList<ChuDe> mangchude;

    public DanhsachAllChudeAdapter(Context context, ArrayList<ChuDe> mangchude) {
        this.context = context;
        this.mangchude = mangchude;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_all_chu_de, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChuDe chuDe = mangchude.get(position);
        Glide.with(context).load(chuDe.getHinhChuDe()).into(holder.imgAllChude);
    }

    @Override
    public int getItemCount() {
        return mangchude.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgAllChude;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAllChude = itemView.findViewById(R.id.imageviewAllChude);
            imgAllChude.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DanhsachtheloaithechudeActivity.class);
                    intent.putExtra("chude", mangchude.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
