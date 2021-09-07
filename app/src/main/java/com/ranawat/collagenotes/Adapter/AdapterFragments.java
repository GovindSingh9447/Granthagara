package com.ranawat.collagenotes.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ranawat.Fragments.Chat_Admin_Fragment;
import com.ranawat.Fragments.User_Fragment;

public class AdapterFragments extends FragmentPagerAdapter {
    public AdapterFragments(@NonNull FragmentManager fm) {
        super(fm);
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0: return new User_Fragment();
            case 1: return new Chat_Admin_Fragment();
            default: return new Chat_Admin_Fragment();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position==0) {
            title = "USERS";
        }
        else
            title= "CHATS";
        return title;

    }
}
