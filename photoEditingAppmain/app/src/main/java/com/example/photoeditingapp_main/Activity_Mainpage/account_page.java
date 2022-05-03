package com.example.photoeditingapp_main.Activity_Mainpage;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;

import com.example.photoeditingapp_main.R;
import com.example.photoeditingapp_main._Classes.ExpandableGridView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link account_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class account_page extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public account_page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment account_page.
     */
    // TODO: Rename and change types and number of parameters
    public static account_page newInstance(String param1, String param2) {
        account_page fragment = new account_page();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.drawable.welcome_background2);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.welcome_background);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);
        list.add(R.drawable.img);




        ExpandableGridView gridView = view.findViewById(R.id.GridViewAccount);
        gridView.setExpanded(true);
        _adapter_gridview_account_page adapter = new _adapter_gridview_account_page(getContext(), list);
        gridView.setAdapter(adapter);
    }
}