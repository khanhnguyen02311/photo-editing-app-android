package com.example.photoeditingapp_main._Classes;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoeditingapp_main.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class _AdjustAdapter extends RecyclerView.Adapter<_AdjustAdapter.ViewHolder> {
    private final ArrayList<AdjustItem> listAdjust;

    public _AdjustAdapter(ArrayList<AdjustItem> list) {listAdjust = list;}

    @NonNull
    @Override
    public _AdjustAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater li = LayoutInflater.from(context);
        LinearLayout view = (LinearLayout) li.inflate(R.layout._custom_design_adjust_itemview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull _AdjustAdapter.ViewHolder holder, int position) {
        AdjustItem item = listAdjust.get(position);
        ImageView iconHolder = holder.iconView;
        TextView textHolder = holder.textView;

        iconHolder.setImageDrawable(item.getIcon());
        textHolder.setText(item.getText());
    }

    @Override
    public int getItemCount() {
        return listAdjust.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iconView;
        private final TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconView = itemView.findViewById(R.id.icon);
            textView = itemView.findViewById(R.id.text);
        }
    }
}
