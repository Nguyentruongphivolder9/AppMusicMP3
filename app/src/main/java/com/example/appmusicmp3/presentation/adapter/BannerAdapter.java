package com.example.appmusicmp3.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appmusicmp3.R;
import com.example.appmusicmp3.common.AppConstant;
import com.example.appmusicmp3.data.models.QuangCao;

import java.util.ArrayList;
import java.util.List;


public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {

    List<QuangCao> listQuangcao;
    Context context;

    public BannerAdapter() {
        listQuangcao = new ArrayList<>();
    }

    public void updateListProduct(List<QuangCao> data) {
        if (listQuangcao != null && listQuangcao.size() > 0) {
            listQuangcao.clear();
        }
        listQuangcao.addAll(data);
        notifyDataSetChanged();
    }

    public List<QuangCao> getListFoods(){
        return listQuangcao;
    }

    @NonNull
    @Override
    public BannerAdapter.BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_banner, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        holder.bind(context, listQuangcao.get(position));
    }

    @Override
    public int getItemCount() {
        return listQuangcao.size();
    }


    class BannerViewHolder extends RecyclerView.ViewHolder {

        ImageView imgRowBanner;

        public BannerViewHolder(@NonNull View view) {
            super(view);
            imgRowBanner = view.findViewById(R.id.imageViewBackgroundBanner);

        }

        public void bind(Context context, QuangCao quangCao) {
            Glide.with(context)
                    .load(AppConstant.BASE_URL + quangCao.getHinhAnh())
                    .into(imgRowBanner);
        }
    }
}
