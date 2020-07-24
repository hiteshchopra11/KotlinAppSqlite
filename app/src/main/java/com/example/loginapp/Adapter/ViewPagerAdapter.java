package com.example.loginapp.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.loginapp.Fragments.Contact_Us;
import com.example.loginapp.Fragments.Images;
import com.example.loginapp.Fragments.View_Images;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Contact_Us(); //ChildFragment1 at position 0
            case 1:
                return new Images(); //ChildFragment2 at position 1
            case 2:
                return new View_Images(); //ChildFragment3 at position 2
        }
        return null; //does not happen
    }

    @Override
    public int getCount() {
        return 3; //three fragments
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = getItem(position).getClass().getName();
        title = title.replaceAll("_", " ");

        return title.subSequence(title.lastIndexOf(".") +1, title.length());
    }
}
