package com.example.photoeditingapp_main.Activity_Mainpage;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.photoeditingapp_main.Activity_Design.DesignActivity;
import com.example.photoeditingapp_main.R;
import com.example.photoeditingapp_main._Classes._GlobalVariables;
import com.example.photoeditingapp_main._Classes.GeneralPictureItem;
import com.example.photoeditingapp_main._Classes._DiscoverAdapter;
import com.example.photoeditingapp_main._Classes._RecyclerTouchListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link discover_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class discover_page extends Fragment {

    _GlobalVariables gv;
    _DiscoverAdapter adapter;
    RecyclerView rv;
    List<DocumentSnapshot> snapshotList;

    public discover_page() {}

    public static discover_page newInstance(String param1, String param2) {
        return new discover_page();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover_page, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView pageName = requireActivity().findViewById(R.id.pageName);
        pageName.setText("DISCOVER");

        gv = (_GlobalVariables) requireActivity().getApplication();

        rv = view.findViewById(R.id.recyclerview_discover);
        rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        //adapter = new _DiscoverAdapter(gv.listDiscoverItem, gv.studioLocation);
        gv.getFirestoreDB().collection("images").orderBy("timeadded", Query.Direction.DESCENDING).limit(30).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshot) {
                snapshotList = snapshot.getDocuments();
                adapter = new _DiscoverAdapter(getContext(), snapshotList);
                rv.setAdapter(adapter);
                rv.addOnItemTouchListener(new _RecyclerTouchListener(getContext(), rv, new _RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        final Dialog dialog = new Dialog(getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout._dialog_show_big_image);

                        Window window = dialog.getWindow();

                        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        WindowManager.LayoutParams windowAttributes = window.getAttributes();
                        window.setAttributes(windowAttributes);

                        ImageView imageView = window.findViewById(R.id.imageview);

                        Glide.with(requireContext()).load(snapshotList.get(position).get("image_uri")).centerInside().into(imageView);

                        dialog.show();
                    }

                    @Override
                    public void onLongClick(View view, int position) { }
                }));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Snackbar.make(view, "Can't load images.", 1000).show();
            }
        });
    }
}