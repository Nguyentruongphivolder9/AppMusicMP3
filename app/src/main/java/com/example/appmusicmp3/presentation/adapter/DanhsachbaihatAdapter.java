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

import com.example.appmusicmp3.Activity.PlayNhacActivity;
import com.example.appmusicmp3.R;
import com.example.appmusicmp3.data.datasources.remote.APIService;
import com.example.appmusicmp3.data.datasources.remote.AppConstant;
import com.example.appmusicmp3.data.models.BaiHat;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            imgLuotthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgLuotthich.setImageResource(R.drawable.iconloved);
                    APIService apiService = AppConstant.getService();
                    Call<String> callback = apiService.updateLuotThich("1", mangBaihat.get(getPosition()).getIdbaihat());
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PlayNhacActivity.class);
                    intent.putExtra("cakhuc", mangBaihat.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
