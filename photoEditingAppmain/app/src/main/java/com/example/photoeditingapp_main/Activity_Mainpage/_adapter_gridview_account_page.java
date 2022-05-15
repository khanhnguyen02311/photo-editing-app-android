package com.example.photoeditingapp_main.Activity_Mainpage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.photoeditingapp_main.R;
import com.example.photoeditingapp_main._Classes.SliderItem;

import java.util.ArrayList;
import java.util.List;

public class _adapter_gridview_account_page extends ArrayAdapter<SliderItem> {

    Context context;
    List<SliderItem> image;
    List<Boolean> isSelected;

    public _adapter_gridview_account_page(Context context, List<SliderItem> image) {
        super(context, R.layout._custom_item_gridview_accountpage, image);
        this.context = context;
        this.image = image;
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

    public void clearSelected() {
        for (int i = 0; i < isSelected.size(); i++) {
            isSelected.set(i, false);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder")
        View row = inflater.inflate(R.layout._custom_item_gridview_accountpage, parent, false);
        ImageView imageView = (ImageView) row.findViewById(R.id.imageView_custom_item_gridview_account_page);
        imageView.setImageResource(image.get(position).getImage());
        return row;
    }
}

