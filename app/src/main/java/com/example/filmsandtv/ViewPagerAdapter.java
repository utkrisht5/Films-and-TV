package com.example.filmsandtv;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    int tabCount;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabCount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new trending();
            case 1: return new topRated();
            case 2: return new action();
            case 3: return new comedy();
            case 4: return new Romance();
            case 5: return new Horror();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

}
