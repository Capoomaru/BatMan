package com.example.batman.managemode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.batman.R;
import com.example.batman.managemode.stock.StockTableFragment;
import com.example.batman.managemode.transaction.TransactionTableFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class ManageMainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private StockTableFragment stockTableFragment;
    private TransactionTableFragment transactionTableFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_main);
        tabLayout = findViewById(R.id.bottomNaviManage);
        stockTableFragment = new StockTableFragment();
        addFragment(stockTableFragment);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        if (stockTableFragment == null) {
                            stockTableFragment = new StockTableFragment();
                            addFragment(stockTableFragment);
                        }
                        else
                            showFragment(stockTableFragment);
                        break;
                    case 1:
                        if (transactionTableFragment == null) {
                            transactionTableFragment = new TransactionTableFragment();
                            addFragment(transactionTableFragment);
                        }
                        else
                            showFragment(transactionTableFragment);
                        break;
                    case 2:
//                        if (fragment2 == null) {
//                            fragment2 = new StockTableFragment();
//                            addFragment(fragment2);
//                        }
//                        else
//                            showFragment(fragment2);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
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