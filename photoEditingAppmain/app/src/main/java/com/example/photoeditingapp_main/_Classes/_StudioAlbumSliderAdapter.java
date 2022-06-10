package com.example.photoeditingapp_main._Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.photoeditingapp_main.R;

import java.util.ArrayList;

public class _StudioAlbumSliderAdapter extends RecyclerView.Adapter<_StudioAlbumSliderAdapter.StudioSliderViewHolder> {

    private ArrayList<GeneralPictureItem> sliderItems;
    Context _Context;

    public _StudioAlbumSliderAdapter(ArrayList<GeneralPictureItem> sliderItems, Context context) {
        this.sliderItems = sliderItems;
        _Context = context;
    }

    @NonNull
    @Override
    public StudioSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudioSliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout._custom_slider_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudioSliderViewHolder holder, int position) {
        Glide.with(_Context).load(sliderItems.get(position).getImageUri()).into(holder.sliderItemImage);
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    static class StudioSliderViewHolder extends RecyclerView.ViewHolder {
        ImageView sliderItemImage;

        public StudioSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            sliderItemImage = itemView.findViewById(R.id.image_view_slider_item);
        }
    }
}
