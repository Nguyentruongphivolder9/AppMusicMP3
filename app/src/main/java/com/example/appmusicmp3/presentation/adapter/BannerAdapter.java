package com.example.appmusicmp3.presentation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.appmusicmp3.Activity.DanhsachbaihatActivity;
import com.example.appmusicmp3.R;
import com.example.appmusicmp3.data.models.QuangCao;

import java.util.ArrayList;


public class BannerAdapter  extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {
    Context context;
    ArrayList<QuangCao> arrayListBanner;

    public BannerAdapter(Context context, ArrayList<QuangCao> arrayListBanner) {
        this.context = context;
        this.arrayListBanner = arrayListBanner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_banner, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuangCao quangCao = arrayListBanner.get(position);
        holder.tvTitleQuangcao.setText(quangCao.getTenbaihat());
        Glide.with(context).load(quangCao.getHinhAnh())
                .apply(new RequestOptions().transform(new CenterCrop()).transform(new RoundedCorners(15)))
                .into(holder.imgBackgroundBanner);
        Glide.with(context).load(quangCao.getHinhbaihat())
                .apply(new RequestOptions().transform(new CenterCrop()).transform(new RoundedCorners(15)))
                .into(holder.imgSongquangcao);
    }

    @Override
    public int getItemCount() {
        return arrayListBanner.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBackgroundBanner, imgSongquangcao;
        TextView tvTitleQuangcao;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBackgroundBanner = itemView.findViewById(R.id.imageViewBackgroundBanner);
            imgSongquangcao = itemView.findViewById(R.id.imageSongQuangcao);
            tvTitleQuangcao = itemView.findViewById(R.id.tvTitleQuangcao);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(context, DanhsachbaihatActivity.class);
                    intent.putExtra("banner",arrayListBanner.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }

//    Context context;
//    ArrayList<QuangCao> arrayListBanner;
//
//    public BannerAdapter(Context context, ArrayList<QuangCao> arrayListBanner){
//        this.context = context;
//        this.arrayListBanner = arrayListBanner;
//    }
//
//    @Override
//    public int getCount() {
//        return arrayListBanner.size();
//    }
//
//    @Override
//    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//        return view == object;
//    }
//
//    @NonNull
//    @Override
//    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        View view = LayoutInflater.from(context).inflate(R.layout.row_banner, null);
//        ImageView imgBackgroundBanner = view.findViewById(R.id.imageViewBackgroundBanner);
//        ImageView imgSongquangcao = view.findViewById(R.id.imageSongQuangcao);
//        TextView tvTitleQuangcao = view.findViewById(R.id.tvTitleQuangcao);
//
//        Glide.with(context).load(arrayListBanner.get(position).getHinhbaihat())
//                .apply(new RequestOptions().transform(new CenterCrop()).transform(new RoundedCorners(15)))
//                .into(imgSongquangcao);
//        Glide.with(context).load(arrayListBanner.get(position).getHinhAnh())
//                .apply(new RequestOptions().transform(new CenterCrop()).transform(new RoundedCorners(20)))
//                .into(imgBackgroundBanner);
//        tvTitleQuangcao.setText(arrayListBanner.get(position).getTenbaihat());
//
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent =new Intent(context, DanhsachbaihatActivity.class);
//                intent.putExtra("banner",arrayListBanner.get(position));
//                context.startActivity(intent);
//            }
//        });
//        container.addView(view);
//        return view;
//    }
//
//    @Override
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        container.removeView((View) object);
//    }
}
