package com.example.appmusicmp3.presentation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.appmusicmp3.Activity.PlayNhacActivity;
import com.example.appmusicmp3.R;
import com.example.appmusicmp3.data.datasources.remote.APIService;
import com.example.appmusicmp3.data.datasources.remote.AppConstant;
import com.example.appmusicmp3.data.models.BaiHat;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaiHatHotAdapter extends RecyclerView.Adapter<BaiHatHotAdapter.ViewHolder>{

    Context context;
    ArrayList<BaiHat> mangbaihat;

    public BaiHatHotAdapter(Context context, ArrayList<BaiHat> baiHatArrayList) {
        this.context = context;
        this.mangbaihat = baiHatArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_bai_hat_hot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat = mangbaihat.get(position);
        holder.tvCasi.setText(baiHat.getCasi());
        holder.tvTen.setText(baiHat.getTenbaihat());
        Glide.with(context).load(baiHat.getHinhbaihat())
                .apply(new RequestOptions().transform(new CenterCrop()).transform(new RoundedCorners(15)))
                .into(holder.imgHinh);
    }

    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTen, tvCasi;
        ImageView imgHinh, imgLuotthich;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.textviewTenbaihathot);
            tvCasi = itemView.findViewById(R.id.textviewCasibaihathot);
            imgHinh = itemView.findViewById(R.id.imageviewBaihathot);
            imgLuotthich = itemView.findViewById(R.id.imageviewLuotthich);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PlayNhacActivity.class);
                    intent.putExtra("cakhuc", mangbaihat.get(getPosition()));
                    context.startActivity(intent);
                }
            });
            imgLuotthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgLuotthich.setImageResource(R.drawable.iconloved);
                    APIService apiService = AppConstant.getService();
                    Call<String> callback = apiService.updateLuotThich("1", mangbaihat.get(getPosition()).getIdbaihat());
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String ketqua = response.body();
                            if (ketqua.equals("Success")) {
                                Toast.makeText(context, "Đã Thích", Toast.LENGTH_SHORT);
                            }else {
                                Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT);
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    imgLuotthich.setEnabled(false);
                }
            });
        }
    }
}
