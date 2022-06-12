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
                    case 0: //재고 현황 탭
                        if (stockTableFragment == null) {                   //처음 탭 선택시
                            stockTableFragment = new StockTableFragment();  //객체 생성후
                            addFragment(stockTableFragment);                //화면 전환
                        }
                        else                                                //이미 존재하는 경우
                            showFragment(stockTableFragment);               //기존 객체로 화면 전환
                        break;
                    case 1: //거래 기록 탭
                        if (transactionTableFragment == null) {             //처음 탭 선택시
                            transactionTableFragment = new TransactionTableFragment(); //객체 생성후
                            addFragment(transactionTableFragment);          //화면 전환
                        }
                        else                                                //이미 존재하는 경우
                            showFragment(transactionTableFragment);         //기존 객체로 화면 전환
                        break;
                    case 2: //통계 탭 -> 미구현 //TODO : StaticsFragment 구현 필요
//                        if (staticsFragment == null) {
//                            staticsFragment = new StaticsFragment();
//                            addFragment(staticsFragment);
//                        }
//                        else
//                            showFragment(staticsFragment);
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