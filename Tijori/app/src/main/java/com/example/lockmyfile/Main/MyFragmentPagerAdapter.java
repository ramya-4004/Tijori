package com.example.lockmyfile.Main;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import org.jetbrains.annotations.NotNull;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private int noOfTabs;
    private Context context;

    public MyFragmentPagerAdapter(@NonNull @NotNull FragmentManager fm, Context context, int noOfTabs) {
        super(fm);
        this.context = context;
        this.noOfTabs = noOfTabs;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment =null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new ImageFragment();
                break;
            case 2:
                fragment = new VideoFragment();
                break;
            case 3:
                fragment = new PdfFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }

    @Override
    public int getItemPosition(@NonNull @NotNull Object object) {
        if(object instanceof ImageFragment){
            ImageFragment fragment =  (ImageFragment) object;
            fragment.updateUI();
        } else if(object instanceof PdfFragment){
            PdfFragment fragment =  (PdfFragment) object;
            fragment.updateUI();
        } else if(object instanceof VideoFragment){
            VideoFragment fragment =  (VideoFragment) object;
            fragment.updateUI();
        }
        return super.getItemPosition(object);
    }
}
