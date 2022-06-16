package com.example.photoeditingapp_main._Classes;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.photoeditingapp_main.R;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;


public class _MainpageAdapter extends RecyclerView.Adapter<_MainpageAdapter.ViewHolder> {

    Context context;
    List<DocumentSnapshot> userSnapshot;

    public _MainpageAdapter(Context c, List<DocumentSnapshot> snap) {context = c; userSnapshot = snap;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(context);
        LinearLayout cardLayout = (LinearLayout) li.inflate(R.layout._custom_item_horizontal_scroll_view, parent, false);
        return new ViewHolder(cardLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DocumentSnapshot snapshot = userSnapshot.get(position);

        TextView authorHolder = holder.authorView;
        ImageView imageHolder = holder.imageView;

        Glide.with(context).load(Uri.parse((String) snapshot.get("image_uri"))).centerInside().placeholder(R.drawable.stewdioplaceholder).into(imageHolder);
        authorHolder.setText((String) snapshot.get("usr"));

        imageHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout._dialog_show_big_image);

                Window window = dialog.getWindow();

                window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams windowAttributes = window.getAttributes();
                window.setAttributes(windowAttributes);

                ImageView imageView = dialog.findViewById(R.id.imageview);

                Glide.with(context).load(Uri.parse((String) snapshot.get("image_uri"))).centerInside().into(imageView);

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userSnapshot.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView authorView;

        private String id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view);
            authorView = itemView.findViewById(R.id.text_view);
        }
    }
}
