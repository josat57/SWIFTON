package com.swifton.swifton;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.swifton.swifton.Fragments.FavoriteFragment;
import com.swifton.swifton.Fragments.MeasurementFragment;
import com.swifton.swifton.Fragments.MyCartFragment;
import com.swifton.swifton.Fragments.ProfileFragment;
import com.swifton.swifton.Fragments.UserHomeFragment;


public class UserDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mdrawaerLayout;
    private ActionBarDrawerToggle mdrawertoggle;
    private Toolbar mtoolbar;
    private String mActivityTitle;

    TextView navusername, navemail,navuserid, navdeviceuid;
    ImageView navprofilepic;

    LinearLayout editprofile, viewprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        mtoolbar = findViewById(R.id.dashToolbar);
        setSupportActionBar(mtoolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        viewprofile = findViewById(R.id.viewuserprofile);
        editprofile = findViewById(R.id.edituserprofile);

        mdrawaerLayout = findViewById(R.id.drawerLayout);
//        mdrawertoggle = new ActionBarDrawerToggle(this,mdrawaerLayout,R.string.open,R.string.close);
//        mdrawaerLayout.addDrawerListener(mdrawertoggle);
//        mdrawertoggle.syncState();

        mActivityTitle = "Dashboard";

        NavigationView navigationView =  findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        setupDrawer();

        UserHomeFragment homeFragment = new UserHomeFragment();
        FragmentManager managerhm = getSupportFragmentManager();
        managerhm.beginTransaction().replace(R.id.linearLayout_for_fragment, homeFragment, homeFragment.getTag()).commit();


        navuserid =  headerView.findViewById(R.id.headeruserid);
        navprofilepic = headerView.findViewById(R.id.userprofpic);
        navusername = headerView.findViewById(R.id.headerusername1);
        navdeviceuid = headerView.findViewById(R.id.headerdeviceid);
        navemail = headerView.findViewById(R.id.headeruseremail);

        Bundle detailsbundle = getIntent().getExtras();
        //int mindex = detailsbundle.getInt("id");
        String  memail = detailsbundle.getString("email"),
                musername = detailsbundle.getString("username"),
                mprofilepic = detailsbundle.getString("profilepic"),
                mdeviceids = detailsbundle.getString("deviceuid");


        Toast.makeText(this, "this is "  + mdeviceids, Toast.LENGTH_LONG).show();

        //navprofilepic.setImageBitmap(mprofilepic.toString());
        navusername.setText(musername);
        navdeviceuid.setText(mdeviceids);
        navemail.setText(memail);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mdrawertoggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mdrawertoggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawer() {

        mdrawertoggle = new ActionBarDrawerToggle(this, mdrawaerLayout,
                R.string.open, R.string.close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if( getSupportActionBar() !=null ) {
                    getSupportActionBar().setTitle(mActivityTitle);
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
                    getSupportActionBar().setIcon(R.drawable.ic_dashboard);
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }else{
                    getSupportActionBar().setTitle("Changed");
                }
            }
        };
        mdrawertoggle.setDrawerIndicatorEnabled(true);
        mdrawaerLayout.addDrawerListener(mdrawertoggle);
    }
//

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
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        if (id == R.id.edit_logout) {
//
//        }
        switch (id) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(UserDashboardActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                overridePendingTransition(R.anim.fade_in_left, R.anim.fade_in_right);
                Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show();
                break;

            case R.id.edit_profile:
                if(viewprofile.getVisibility() == View.GONE){

                    viewprofile.setVisibility(View.VISIBLE);
                }else {
                    viewprofile.setVisibility(View.GONE);
                }
                break;

            case R.id.edit_logout:
                Intent logoutIntent = new Intent(UserDashboardActivity.this, MainActivity.class);
                startActivity(logoutIntent);
                overridePendingTransition(R.anim.fade_in_left, R.anim.fade_in_right);
                finish();
                Toast.makeText(UserDashboardActivity.this, "Logout successful", Toast.LENGTH_LONG).show();
                break;
        }

        // Activate the navigation drawer toggle
        if (mdrawertoggle.onOptionsItemSelected(item)) {
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
                mActivityTitle = "My Profile";
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentManager manager = getSupportFragmentManager();
                //using Bundle to send data
                String myemail = String.valueOf(navemail.getText());
                Bundle bundle = new Bundle();
                bundle.putString("useremail", myemail);
                profileFragment.setArguments(bundle); //data being send to SecondFragment

                manager.beginTransaction().replace(R.id.linearLayout_for_fragment, profileFragment, profileFragment.getTag()).commit();
                break;

            case R.id.nav_cart:
                mActivityTitle = "My Cart";
                MyCartFragment cartFragment = new MyCartFragment();
                FragmentManager managert = getSupportFragmentManager();
                managert.beginTransaction().replace(R.id.linearLayout_for_fragment, cartFragment, cartFragment.getTag()).commit();
                break;

            case R.id.nav_measurement:
                mActivityTitle = "My Measurement";
                MeasurementFragment measurementFragment = new MeasurementFragment();
                FragmentManager managerh = getSupportFragmentManager();
                managerh.beginTransaction().replace(R.id.linearLayout_for_fragment, measurementFragment, measurementFragment.getTag()).commit();
                break;

            case R.id.nav_favorite:
                mActivityTitle = "Favorite Tailors";
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                FragmentManager managerv = getSupportFragmentManager();
                managerv.beginTransaction().replace(R.id.linearLayout_for_fragment, favoriteFragment, favoriteFragment.getTag()).commit();

                break;

            case R.id.nav_share:

                break;

            case R.id.nav_orders:
                    mActivityTitle = "My Orders";
                break;

            case R.id.nav_settings:
                    mActivityTitle = "Settings";
//                  SettingsFragment settingsFragment = new SettingsFragment();
//                  FragmentManager manager1 = getSupportFragmentManager();
//                  manager1.beginTransaction().replace(R.id.linearLayout_for_fragment, settingsFragment, settingsFragment.getTag()).commit();

                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
