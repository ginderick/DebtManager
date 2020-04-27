package com.example.debtmanager.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.debtmanager.view.fragment.DebtFragment;
import com.example.debtmanager.view.fragment.LoanFragment;
import com.google.android.material.tabs.TabLayout;


public class PagerAdapter extends FragmentStateAdapter {

    public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return DebtFragment.newInstance();
            case 1:
                return LoanFragment.newInstance();
            default:
                return null;
        }
    }





    @Override
    public int getItemCount() {
        return 2;
    }
}
