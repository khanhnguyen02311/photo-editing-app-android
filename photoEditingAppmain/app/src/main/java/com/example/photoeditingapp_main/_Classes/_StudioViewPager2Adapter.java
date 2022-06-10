package com.example.photoeditingapp_main._Classes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.photoeditingapp_main.Activity_Mainpage.studio_my_album_page;
import com.example.photoeditingapp_main.Activity_Mainpage.studio_recent_page;

import java.util.ArrayList;

public class _StudioViewPager2Adapter extends FragmentStateAdapter {
    ArrayList<Fragment> listFragment;
    public _StudioViewPager2Adapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> fragments) {
        super(fragmentActivity);
        listFragment = fragments;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
