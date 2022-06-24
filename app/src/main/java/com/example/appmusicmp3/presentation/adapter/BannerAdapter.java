package com.example.appmusicmp3.presentation.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.appmusicmp3.Activity.DanhsachbaihatActivity;
import com.example.appmusicmp3.R;
import com.example.appmusicmp3.data.models.QuangCao;

import java.util.ArrayList;


public class BannerAdapter extends PagerAdapter {

    Context context;
    ArrayList<QuangCao> arrayListBanner;

    public BannerAdapter(Context context, ArrayList<QuangCao> arrayListBanner){
        this.context = context;
        this.arrayListBanner = arrayListBanner;
    }

    @Override
    public int getCount() {
        return arrayListBanner.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_banner, null);
        ImageView imgBackgroundBanner = view.findViewById(R.id.imageViewBackgroundBanner);
        ImageView imgSongquangcao = view.findViewById(R.id.imageSongQuangcao);
        TextView tvTitleQuangcao = view.findViewById(R.id.tvTitleQuangcao);

        Glide.with(context).load(arrayListBanner.get(position).getHinhbaihat()).into(imgSongquangcao);
        Glide.with(context).load(arrayListBanner.get(position).getHinhAnh()).into(imgBackgroundBanner);
        tvTitleQuangcao.setText(arrayListBanner.get(position).getTenbaihat());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, DanhsachbaihatActivity.class);
                intent.putExtra("banner",arrayListBanner.get(position));
                context.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
