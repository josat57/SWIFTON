package com.swifton.swifton.ToolBars;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.swifton.swifton.Fragments.AllDesignsFragment;
import com.swifton.swifton.Fragments.MyOrdersFragment;
import com.swifton.swifton.Fragments.NativesFragment;
import com.swifton.swifton.R;
import com.swifton.swifton.ViewPagerAdapter;

public class ToolbarTabs extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_tabs);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ViewPager mViewPager = findViewById(R.id.viewpager);
        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(AllDesignsFragment.newInstance(), "All Designs");
        mViewPagerAdapter.addFragment(NativesFragment.newInstance(), "Natives");
        mViewPagerAdapter.addFragment(MyOrdersFragment.newInstance(), "My Orders");
        mViewPager.setAdapter(mViewPagerAdapter);
        TabLayout mTabLayout = findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

    }

}
