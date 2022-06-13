package com.example.batman.sellmode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.batman.R;
import com.example.batman.sellmode.customer.CustomerListFragment;
import com.example.batman.sellmode.sellingprice.SellingPriceTableFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class SellMainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private SellingPriceTableFragment sellingPriceTableFragment;
    private CustomerListFragment customerListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_main);
        tabLayout = findViewById(R.id.bottomNaviSell);

        sellingPriceTableFragment = new SellingPriceTableFragment();
        addFragment(sellingPriceTableFragment);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        if (sellingPriceTableFragment == null) {
                            sellingPriceTableFragment = new SellingPriceTableFragment();
                            addFragment(sellingPriceTableFragment);
                        } else
                            showFragment(sellingPriceTableFragment);
                        break;
                    case 1:
                        if (customerListFragment == null) {
                            customerListFragment = new CustomerListFragment();
                            addFragment(customerListFragment);
                        } else
                            showFragment(customerListFragment);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

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