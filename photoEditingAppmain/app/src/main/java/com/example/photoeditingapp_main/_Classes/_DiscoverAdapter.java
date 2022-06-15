package com.example.photoeditingapp_main._Classes;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.photoeditingapp_main.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class _DiscoverAdapter extends RecyclerView.Adapter<_DiscoverAdapter.ViewHolder> {

    Context context;
    List<DocumentSnapshot> userSnapshot;

    public _DiscoverAdapter(Context c, List<DocumentSnapshot> snap) {context = c; userSnapshot = snap;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(context);
        LinearLayout cardLayout = (LinearLayout) li.inflate(R.layout._custom_discover_imageview, parent, false);
        return new ViewHolder(cardLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DocumentSnapshot snapshot = userSnapshot.get(position);

        TextView authorHolder = holder.authorView;
        ImageView imageHolder = holder.imageView;
        CheckBox likeBtnHolder = holder.likeBtnView;

        Glide.with(context).load(Uri.parse((String) snapshot.get("image_uri"))).centerInside().placeholder(R.drawable.stewdioplaceholder).into(imageHolder);
        authorHolder.setText((String) snapshot.get("usr"));
    }

    @Override
    public int getItemCount() {
        return userSnapshot.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView authorView;
        private final CheckBox likeBtnView;

        private String id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.image_position);
            authorView = (TextView) itemView.findViewById(R.id.author_position);
            likeBtnView = (CheckBox) itemView.findViewById(R.id.heartbtn_position);

            likeBtnView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                }
            });
        }
    }
}
