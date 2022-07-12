package com.example.appmusicmp3.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.appmusicmp3.R;
import com.example.appmusicmp3.data.models.Playlist;

import java.util.ArrayList;
import java.util.List;

public class PlaylistAdapter extends ArrayAdapter<Playlist> {
    public PlaylistAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Playlist> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public PlaylistAdapter(FragmentActivity activity, int simple_list_item_1, ArrayList<Playlist> mangplaylist) {
        super(activity, simple_list_item_1, mangplaylist);
    }

    class ViewHolder {
        TextView tvNamePlaylist;
        ImageView imgBackGround, imgPlaylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_playlist, null);
            viewHolder = new ViewHolder();
            viewHolder.tvNamePlaylist = convertView.findViewById(R.id.textviewNamePlaylist);
            viewHolder.imgPlaylist = convertView.findViewById(R.id.imageviewPlaylist);
            viewHolder.imgBackGround = convertView.findViewById(R.id.imageviewBackgroundPlaylist);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Playlist playlist = getItem(position);
        Glide.with(getContext()).load(playlist.getHinhPlaylist())
                .apply(new RequestOptions().transform(new CenterCrop()).transform(new RoundedCorners(20)))
                .into(viewHolder.imgBackGround);
        Glide.with(getContext()).load(playlist.getIcon())
                .apply(new RequestOptions().transform(new CenterCrop()).transform(new RoundedCorners(15)))
                .into(viewHolder.imgPlaylist);
        viewHolder.tvNamePlaylist.setText(playlist.getTen());
        return convertView;
    }
}
