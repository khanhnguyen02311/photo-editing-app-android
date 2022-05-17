package com.example.photoeditingapp_main._Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoeditingapp_main.R;

import java.util.List;

public class _StudioSliderAdapter extends RecyclerView.Adapter<_StudioSliderAdapter.StudioSliderViewHolder> {

    private List<ImageItem> sliderItems;

    public _StudioSliderAdapter(List<ImageItem> sliderItems) {
        this.sliderItems = sliderItems;
    }

    @NonNull
    @Override
    public StudioSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudioSliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout._custom_slider_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudioSliderViewHolder holder, int position) {
        holder.sliderItemImage.setImageResource(sliderItems.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class StudioSliderViewHolder extends RecyclerView.ViewHolder {
        ImageView sliderItemImage;

        public StudioSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            sliderItemImage = itemView.findViewById(R.id.image_view_slider_item);
        }

        public void setSliderItemImage(ImageItem sliderItem) {
            sliderItemImage.setImageResource(sliderItem.getImage());
        }
    }
}
