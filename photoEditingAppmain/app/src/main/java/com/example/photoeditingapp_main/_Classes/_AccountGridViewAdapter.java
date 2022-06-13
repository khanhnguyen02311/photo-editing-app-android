package com.example.photoeditingapp_main._Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.example.photoeditingapp_main.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class _AccountGridViewAdapter extends ArrayAdapter<GeneralPictureItem> {

    Context _Context;
    ArrayList<GeneralPictureItem> listImages;
    List<Boolean> isSelected;

    public _AccountGridViewAdapter(Context context, ArrayList<GeneralPictureItem> image) {
        super(context, R.layout._custom_item_gridview_accountpage, image);
        this._Context = context;
        this.listImages = image;
        isSelected = new ArrayList<>();
        for (int i = 0; i < image.size(); i++) {
            isSelected.add(false);
        }
    }

    // Set selected item
    public void setSelected(int position, boolean isSelected) {
        this.isSelected.set(position, isSelected);
    }

    //  Get selected item at position
    public boolean getSelected(int position) {
        return isSelected.get(position);
    }

    public List<Integer> getPositionSelectedItems() {
        List<Integer> selectedItems = new ArrayList<>();
        for (int i = 0; i < isSelected.size(); i++) {
            if (isSelected.get(i)) {
                selectedItems.add(i);
            }
        }
        return selectedItems;
    }

    public List<Integer> getPositionDeselectedItems() {
        List<Integer> desSelectedItems = new ArrayList<>();
        for (int i = 0; i < isSelected.size(); i++) {
            if (!isSelected.get(i)) {
                desSelectedItems.add(i);
            }
        }
        return desSelectedItems;
    }

    public void clearSelected() {
        for (int i = 0; i < isSelected.size(); i++) {
            isSelected.set(i, false);
        }
    }

    public void setAllSelected() {
        for (int i = 0; i < isSelected.size(); i++) {
            isSelected.set(i, true);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) _Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder")
        View row = inflater.inflate(R.layout._custom_item_gridview_accountpage, parent, false);

        SquareImageView imageView = row.findViewById(R.id.imageView_custom_item_gridview_account_page);
        Log.i("URI", listImages.get(position).getImageUri().toString());
        Glide.with(_Context).load(listImages.get(position).getImageUri()).centerCrop().placeholder(R.drawable.image_placeholder).into(imageView);
        if (isSelected.get(position)) {
            MaterialCardView cardView = row.findViewById(R.id.cardView_custom_item_gridview_account_page);
            cardView.setStrokeColor(Color.parseColor("#646464"));
        }
        return row;
    }
}

