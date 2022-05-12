package com.example.photoeditingapp_main.Activity_Mainpage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.photoeditingapp_main.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link studio_photo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class studio_photo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public studio_photo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment studio_photo.
     */
    // TODO: Rename and change types and number of parameters
    public static studio_photo newInstance(String param1, String param2) {
        studio_photo fragment = new studio_photo();
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
        return inflater.inflate(R.layout.fragment_studio_photo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get data from bundle
        int i = 0;
        if (getArguments() != null) {
            i = getArguments().getInt("clicked_photo");
        }

        //set the image
        ImageView imageView = view.findViewById(R.id.studio_photo_image_view);
        imageView.setImageResource(i);

        //set button back
        ImageButton backButton = view.findViewById(R.id.back_image_button_photo);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("album",getArguments().getSerializable("album"));
                Navigation.findNavController(v).navigate(R.id.action_studio_photo_to_studio_album_item_page,bundle);
            }
        });

    }
}