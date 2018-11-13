package com.swifton.swifton;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class dashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        HomeFragment homeFragment = new HomeFragment();
        FragmentManager managerhm = getSupportFragmentManager();
        managerhm.beginTransaction().replace(R.id.linearLayout_for_fragment, homeFragment, homeFragment.getTag()).commit();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //mDrawerList = findViewById(R.id.navList);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        //addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        NavigationView navigationView =  findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawer() {

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if( getSupportActionBar() !=null ) {
                    getSupportActionBar().setTitle("Navigation!");
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }else{
                    getSupportActionBar().setTitle("Changed");
                }
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if( getSupportActionBar() !=null ) {
                    getSupportActionBar().setTitle(mActivityTitle);
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }else{
                    getSupportActionBar().setTitle("Changed");
                }
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }
//    private void addDrawerItems() {
//        String[] osArray = { "Android", "iOS", "Windows", "OS X", "Linux" };
//        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
//        mDrawerList.setAdapter(mAdapter);
//
//        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(dashboardActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch(id){
            case  R.id.nav_profile:
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.linearLayout_for_fragment, profileFragment, profileFragment.getTag()).commit();
                break;

            case R.id.nav_cart:

                CartFragment cartFragment = new CartFragment();
                FragmentManager managert = getSupportFragmentManager();
                managert.beginTransaction().replace(R.id.linearLayout_for_fragment, cartFragment, cartFragment.getTag()).commit();
                break;

            case R.id.nav_measurement:

                MeasurementFragment measurementFragment = new MeasurementFragment();
                FragmentManager managerh = getSupportFragmentManager();
                managerh.beginTransaction().replace(R.id.linearLayout_for_fragment, measurementFragment, measurementFragment.getTag()).commit();
                break;

            case R.id.nav_vendour:

                ProfileFragment vendorprofileFragment = new ProfileFragment();
                FragmentManager managerv = getSupportFragmentManager();
                managerv.beginTransaction().replace(R.id.linearLayout_for_fragment, vendorprofileFragment, vendorprofileFragment.getTag()).commit();

                break;

            case R.id.nav_share:

                break;

            case R.id.nav_send:

                break;

            case R.id.nav_settings:
                SettingsFragment settingsFragment = new SettingsFragment();
                FragmentManager manager1 = getSupportFragmentManager();
                manager1.beginTransaction().replace(R.id.linearLayout_for_fragment, settingsFragment, settingsFragment.getTag()).commit();

                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
