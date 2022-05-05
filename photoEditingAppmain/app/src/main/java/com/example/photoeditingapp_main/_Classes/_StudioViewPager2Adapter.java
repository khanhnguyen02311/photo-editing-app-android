package com.example.photoeditingapp_main._Classes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.photoeditingapp_main.Activity_Mainpage.studio_my_album_page;
import com.example.photoeditingapp_main.Activity_Mainpage.studio_recent_page;

public class _StudioViewPager2Adapter extends FragmentStateAdapter {
    public _StudioViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new studio_my_album_page();
            case 1:
                return new studio_recent_page();
            default:
                return new studio_my_album_page();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
