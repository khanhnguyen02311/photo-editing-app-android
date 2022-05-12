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

import java.util.ArrayList;
import java.util.List;

public class _adapter_gridview_account_page extends ArrayAdapter<Integer> {

    Context context;
    List<Integer> image;

    public _adapter_gridview_account_page(Context context, List<Integer> image) {
        super(context, R.layout._custom_item_gridview_accountpage, image);
        this.context = context;
        this.image = image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder")
        View row = inflater.inflate(R.layout._custom_item_gridview_accountpage, parent, false);
        ImageView imageView = (ImageView) row.findViewById(R.id.imageView_custom_item_gridview_account_page);
        imageView.setImageResource(image.get(position));
        return row;
    }
}

