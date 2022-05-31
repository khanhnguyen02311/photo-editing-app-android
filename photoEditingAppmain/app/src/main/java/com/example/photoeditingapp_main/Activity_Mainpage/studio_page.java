package com.example.photoeditingapp_main.Activity_Mainpage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.photoeditingapp_main.Activity_Design.DesignActivity;
import com.example.photoeditingapp_main.R;
import com.example.photoeditingapp_main._Classes._StudioViewPager2Adapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.nambimobile.widgets.efab.FabOption;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link studio_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class studio_page extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    FabOption importBtn, cameraBtn;

    ActivityResultLauncher<String> imageContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
        new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                if (uri != null) {
                    Intent designActivity = new Intent(getActivity(), DesignActivity.class);
                    designActivity.putExtra("image_uri", uri);
                    startActivity(designActivity);
                }
            }
        });

    //final int IMAGE_COMPLETED = 200, CAMERA_COMPLETED = 201;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public studio_page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment studio_page.
     */
    // TODO: Rename and change types and number of parameters
    public static studio_page newInstance(String param1, String param2) {
        studio_page fragment = new studio_page();
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
        return inflater.inflate(R.layout.fragment_studio_page, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView pageName = requireActivity().findViewById(R.id.pageName);
        pageName.setText("STUDIO");

        // Initialize the ViewPager and set an adapter
        tabLayout = view.findViewById(R.id.tab_layout_studio_page);
        viewPager = view.findViewById(R.id.view_pager_studio_page);
        importBtn = view.findViewById(R.id.importBtn);
        cameraBtn = view.findViewById(R.id.cameraBtn);

        // Create an adapter that knows which fragment should be shown on each page
        _StudioViewPager2Adapter adapter = new _StudioViewPager2Adapter(requireActivity());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        //Disabled the swipe gesture
        viewPager.setUserInputEnabled(false);

        // Give the TabLayout the ViewPager
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("My Album");
                        break;
                    case 1:
                        tab.setText("Recent");
                        break;
                    default:
                        tab.setText("");
                }
            }
        }).attach();

        importBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageContent.launch("image/*");
            }
        });
    }
}