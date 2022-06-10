package com.example.photoeditingapp_main.Activity_Mainpage;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import com.example.photoeditingapp_main.R;
import com.example.photoeditingapp_main._Classes._GlobalVariables;
import com.example.photoeditingapp_main._Classes.GeneralPictureItem;
import com.example.photoeditingapp_main._Classes._DiscoverAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link discover_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class discover_page extends Fragment {

    _GlobalVariables gv;
    _DiscoverAdapter adapter;
    ArrayList<GeneralPictureItem> listDiscoverItem;
    RecyclerView rv;

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
        //rv.setAdapter(adapter);
    }
}