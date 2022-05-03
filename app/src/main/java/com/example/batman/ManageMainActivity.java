package com.example.batman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class ManageMainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    StockTableFragment stockTableFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_main);
        tabLayout = findViewById(R.id.bottomNaviManage);
        stockTableFragment = new StockTableFragment();
        addFragment(stockTableFragment);

    }

    private void replaceFragment(Fragment targetFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, targetFragment);
        fragmentTransaction.commit();
    }

    private void addFragment(Fragment targetFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        List<Fragment> fragmentList = fragmentManager.getFragments();

        for(Fragment fragment : fragmentList) {
            if(!(targetFragment.equals(fragment)))
                fragmentTransaction.hide(fragment);
        }

        fragmentTransaction.add(R.id.main_frame, targetFragment,targetFragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    private void showFragment(Fragment targetFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        List<Fragment> fragmentList = fragmentManager.getFragments();

        for(Fragment fragment : fragmentList) {
            if(!(targetFragment.equals(fragment)))
                fragmentTransaction.hide(fragment);
        }

        fragmentTransaction.show(targetFragment);
        fragmentTransaction.commit();
    }
}