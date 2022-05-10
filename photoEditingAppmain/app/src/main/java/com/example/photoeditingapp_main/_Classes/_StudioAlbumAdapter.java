package com.example.photoeditingapp_main._Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.photoeditingapp_main.Activity_Mainpage.MainpageActivity;
import com.example.photoeditingapp_main.Activity_Mainpage.studio_album_item_page;
import com.example.photoeditingapp_main.R;


import java.util.List;

public class _StudioAlbumAdapter extends RecyclerView.Adapter<_StudioAlbumAdapter.ViewHolder> {
    private List<AlbumItem> _AlbumList;
    private Context _Context;

    public _StudioAlbumAdapter(List<AlbumItem> albumList, Context context) {
        _AlbumList = albumList;
        _Context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout._custom_recycle_view_item_my_album, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textViewAlbumName.setText(_AlbumList.get(position).getAlbumName());
        holder.textViewAlbumCount.setText(_AlbumList.get(position).getAlbumImages().size() + " Photos");
        holder.viewPager.setClipToPadding(false);
        holder.viewPager.setClipChildren(false);
        holder.viewPager.setOffscreenPageLimit(3);

        holder.viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        holder.viewPager.setAdapter(new _StudioSliderAdapter(_AlbumList.get(position).getAlbumImages()));
        holder.viewPager.setPageTransformer(new _SliderTransformer(3));

        holder.imageButtonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("album", _AlbumList.get(position));
                Navigation.findNavController(v).navigate(R.id.action_studio_album_list_page_to_studio_album_item_page, bundle);
            }
        });
       /* holder.setData(_AlbumList.get(position),_AlbumList.size());*/
    }

    @Override
    public int getItemCount() {
        return _AlbumList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewPager2 viewPager;
        TextView textViewAlbumName;
        TextView textViewAlbumCount;
        ImageButton imageButtonOpen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.view_pager_slider_album_studio_page);
            textViewAlbumName = itemView.findViewById(R.id.text_view_album_name);
            textViewAlbumCount = itemView.findViewById(R.id.text_view_album_count);
            imageButtonOpen = itemView.findViewById(R.id.image_button_open_album);
        }

        public void setData(AlbumItem albumItem, int count) {
            textViewAlbumName.setText(albumItem.getAlbumName());
            textViewAlbumCount.setText(count+ " photos");
            viewPager.setAdapter(new _StudioSliderAdapter(albumItem.getAlbumImages()));
        }
    }
}
