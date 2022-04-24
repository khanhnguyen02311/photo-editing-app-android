package com.example.photoeditingapp_main._Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.example.photoeditingapp_main.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class _DiscoverAdapter extends RecyclerView.Adapter<_DiscoverAdapter.ViewHolder> {

    private final ArrayList<PictureItem> listItem;
    AssetManager am;
    InputStream is;
    File tempImageStorage;

    public _DiscoverAdapter(ArrayList<PictureItem> list, File file) {listItem = list; tempImageStorage = file;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        am = context.getAssets();
        LayoutInflater li = LayoutInflater.from(context);
        LinearLayout cardLayout = (LinearLayout) li.inflate(R.layout._custom_pictureview, parent, false);
        return new ViewHolder(cardLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PictureItem item = listItem.get(position);

        TextView authorHolder = holder.authorView;
        ImageView imageHolder = holder.imageView;
        CheckBox likeBtnHolder = holder.likeBtnView;

        holder.id = item.getId();

        authorHolder.setText(item.getAuthor());

        File dir = new File(tempImageStorage.getAbsolutePath() + "/" + item.getImageLink());

        if (dir.exists()) imageHolder.setImageBitmap(BitmapFactory.decodeFile(String.valueOf(dir)));
        else {
            try {
                is = am.open("TestImages/" + item.getImageLink());

                Bitmap bitmap = BitmapFactory.decodeStream(is);

                float scaledWidth = (float) Resources.getSystem().getDisplayMetrics().widthPixels / 2;
                float scaledHeight = scaledWidth * (float) bitmap.getHeight() / (float) bitmap.getWidth();

                bitmap = Bitmap.createScaledBitmap(bitmap, Math.round(scaledWidth), Math.round(scaledHeight), true);

                imageHolder.setImageBitmap(bitmap);

                try (FileOutputStream out = new FileOutputStream(dir)) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                } catch (IOException e) {
                    Log.e("ERROR", e.toString());
                    e.printStackTrace();
                }

            } catch (IOException e) {
                Log.e("ERROR", e.toString());
                e.printStackTrace();
            }
        }


        likeBtnHolder.setChecked(item.isLiked());
    }

    @Override
    public int getItemCount() {
        return listItem.size();
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
