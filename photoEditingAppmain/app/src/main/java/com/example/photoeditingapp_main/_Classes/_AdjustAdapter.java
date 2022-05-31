package com.example.photoeditingapp_main._Classes;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class _AdjustAdapter extends RecyclerView.Adapter<_AdjustAdapter.ViewHolder> {
    ArrayList<Drawable> listIcon;
    ArrayList<String> listName;

    @NonNull
    @Override
    public _AdjustAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull _AdjustAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
