package com.example.photoeditingapp_main.Activity_Mainpage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.photoeditingapp_main.R;
import com.example.photoeditingapp_main._Classes.AlbumItem;
import com.example.photoeditingapp_main._Classes.GeneralPictureItem;
import com.example.photoeditingapp_main._Classes._StudioAlbumAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link studio_my_album_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class studio_my_album_page extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public studio_my_album_page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment studio_my_album_page.
     */
    // TODO: Rename and change types and number of parameters
    public static studio_my_album_page newInstance(String param1, String param2) {
        studio_my_album_page fragment = new studio_my_album_page();
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
        return inflater.inflate(R.layout.fragment_studio_my_album_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<GeneralPictureItem> sliderItems1 = new ArrayList<>();
        ArrayList<GeneralPictureItem> sliderItems2 = new ArrayList<>();


        ArrayList<AlbumItem> albumItems = new ArrayList<>();
        albumItems.add(new AlbumItem("Album 1", sliderItems1));
        albumItems.add(new AlbumItem("Album 2", sliderItems2));

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_studio_my_album_page);

        _StudioAlbumAdapter adapter = new _StudioAlbumAdapter(albumItems,getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}