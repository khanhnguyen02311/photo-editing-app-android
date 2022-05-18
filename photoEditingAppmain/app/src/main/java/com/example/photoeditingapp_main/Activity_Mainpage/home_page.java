package com.example.photoeditingapp_main.Activity_Mainpage;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.photoeditingapp_main.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home_page extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public home_page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home_page.
     */
    // TODO: Rename and change types and number of parameters
    public static home_page newInstance(String param1, String param2) {
        home_page fragment = new home_page();
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
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView pageName = requireActivity().findViewById(R.id.pageName);
        pageName.setText("HOMEPAGE");

        /*if (requireActivity().getIntent() != null) {
            tv.setText(requireActivity().getIntent().getStringExtra("username"));
        }*/
        LinearLayout linearLayout = view.findViewById(R.id.LinearLayoutRecently);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for(int i = 0; i < 10; i++) {
            View view1 = inflater.inflate(R.layout._custom_item_horizontal_scroll_view, linearLayout, false);
            TextView textView = view1.findViewById(R.id.TextViewItemHorizontalScrollView);
            textView.setText("Khang Huynh");
            ImageView imageView = view1.findViewById(R.id.ImageViewItemHorizontalScrollView);
            imageView.setImageResource(R.drawable.img);
            linearLayout.addView(view1);
        }

        linearLayout = view.findViewById(R.id.LinearLayoutTrending);
        for(int i = 0; i < 10; i++) {
            View view1 = inflater.inflate(R.layout._custom_item_horizontal_scroll_view, linearLayout, false);
            TextView textView = view1.findViewById(R.id.TextViewItemHorizontalScrollView);
            textView.setText("Khang Huynh");
            ImageView imageView = view1.findViewById(R.id.ImageViewItemHorizontalScrollView);
            imageView.setImageResource(R.drawable.img);
            linearLayout.addView(view1);
        }

        linearLayout = view.findViewById(R.id.LinearLayoutDiscovery);
        for(int i = 0; i < 10; i++) {
            View view1 = inflater.inflate(R.layout._custom_item_horizontal_scroll_view, linearLayout, false);
            TextView textView = view1.findViewById(R.id.TextViewItemHorizontalScrollView);
            textView.setText("Khang Huynh");
            ImageView imageView = view1.findViewById(R.id.ImageViewItemHorizontalScrollView);
            imageView.setImageResource(R.drawable.img);
            linearLayout.addView(view1);
        }
    }
}