package com.example.photoeditingapp_main.Activity_Mainpage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
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

import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.photoeditingapp_main.Activity_Design.DesignActivity;
import com.example.photoeditingapp_main.R;
import com.example.photoeditingapp_main._Classes._GlobalVariables;
import com.example.photoeditingapp_main._Classes._StudioViewPager2Adapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.nambimobile.widgets.efab.FabOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class studio_page extends Fragment {

    _GlobalVariables gv;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    FabOption importBtn, cameraBtn;
    ArrayList<Fragment> listViewPagerFragment;

    ActivityResultLauncher<String[]> imageContent = registerForActivityResult(new ActivityResultContracts.OpenDocument(),
        new ActivityResultCallback<Uri>() {
            @SuppressLint("Range")
            @Override
            public void onActivityResult(Uri uri) {
                String name = null;
                if (uri != null) {
                    Cursor cursor = requireContext().getContentResolver().query(uri, null, null, null, null);
                    try {
                        if (cursor.moveToFirst()) name = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    } finally {
                        Objects.requireNonNull(cursor).close();
                    }
                    if (gv.getLocalDB().addImageToStudio(name, uri)) {
                        Snackbar snackbar = Snackbar.make(requireView(), "Add image successful.", 1000);
                        snackbar.show();
                    }
                }
            }
        });

    public studio_page() {
        // Required empty public constructor
    }

    public static studio_page newInstance() {
        return new studio_page();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gv = (_GlobalVariables) requireActivity().getApplication();
        listViewPagerFragment = new ArrayList<>(Arrays.asList(new studio_recent_page(), new studio_my_album_page()));
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
        _StudioViewPager2Adapter adapter = new _StudioViewPager2Adapter(requireActivity(), listViewPagerFragment);

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
                        tab.setText("Recent");
                        break;
                    case 1:
                        tab.setText("My Albums");
                        break;
                    default:
                        tab.setText("");
                }
            }
        }).attach();

        importBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageContent.launch(new String[]{"image/*"});
            }
        });
    }
}