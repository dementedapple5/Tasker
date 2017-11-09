package com.example.usuario.tasker.utilities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Dani on 09/11/2017.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentsList = new ArrayList<>();
    private ArrayList<String> fragmentsTitleList = new ArrayList<>();


    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title){
        fragmentsList.add(fragment);
        fragmentsTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }
}
