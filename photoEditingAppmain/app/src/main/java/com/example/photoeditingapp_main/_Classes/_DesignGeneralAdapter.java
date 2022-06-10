package com.example.photoeditingapp_main._Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoeditingapp_main.R;

import java.util.ArrayList;

public class _DesignGeneralAdapter extends RecyclerView.Adapter<_DesignGeneralAdapter.ViewHolder> {
    private final ArrayList<DesignGeneralItem> listAdjust;
    int viewResource;

    public _DesignGeneralAdapter(ArrayList<DesignGeneralItem> list, int resource) {listAdjust = list; viewResource = resource;}

    @NonNull
    @Override
    public _DesignGeneralAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater li = LayoutInflater.from(context);
        LinearLayout view = (LinearLayout) li.inflate(viewResource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull _DesignGeneralAdapter.ViewHolder holder, int position) {
        DesignGeneralItem item = listAdjust.get(position);
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
