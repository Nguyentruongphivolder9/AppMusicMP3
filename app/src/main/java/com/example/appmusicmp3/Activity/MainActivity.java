package com.example.appmusicmp3.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.appmusicmp3.presentation.adapter.ViewPagerAdapter;
import com.example.appmusicmp3.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    TabLayout mTabLayout;
    ViewPager2 mViewPager2;
    ViewPagerAdapter mViewPagerAdapter;
    private int[] tabIcons = {
            R.drawable.icontrangchu,
            R.drawable.iconsearch,
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager2 = findViewById(R.id.viewPager);

        mViewPagerAdapter = new ViewPagerAdapter(this);
        mViewPager2.setAdapter(mViewPagerAdapter);

        final int[] tabIcons = {R.drawable.icontrangchu, R.drawable.icontimkiem};

        new TabLayoutMediator(mTabLayout, mViewPager2, (tab, position) -> {
            tab.setIcon(tabIcons[position]);
            switch (position) {
                case 0:
                    tab.setText("Home");
                    break;

                case 1:
                    tab.setText("Search");
                    break;
            }

        }).attach();

    }
}
