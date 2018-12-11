package com.swifton.swifton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.swifton.swifton.ToolBars.CollapsingToolbarFabs;
import com.swifton.swifton.ToolBars.CollapsingToolbarTabs;
import com.swifton.swifton.ToolBars.ToolbarAndFab;
import com.swifton.swifton.ToolBars.ToolbarTabs;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonToolbarFab = findViewById(R.id.buttonToolbarFab);
        Button buttonLogin = findViewById(R.id.cmdLogin);
        Button buttonSignup = findViewById(R.id.cmdSignup);
        buttonLogin.setOnClickListener(this);
        buttonSignup.setOnClickListener(this);
        buttonToolbarFab.setOnClickListener(this);
        Button buttonCollapsingToolbarFab = findViewById(R.id.buttonCollapsingToolbarFab);
        buttonCollapsingToolbarFab.setOnClickListener(this);
        Button buttonCollapsingToolbarTabs = findViewById(R.id.buttonCollapsingToolbarTabs);
        buttonCollapsingToolbarTabs.setOnClickListener(this);
        Button buttonToolbarTabs = findViewById(R.id.buttonToolbarTabs);
        buttonToolbarTabs.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonToolbarFab:
                Intent ToolbarAndFabIntent = new Intent(this, ToolbarAndFab.class);
                startActivity(ToolbarAndFabIntent);
                overridePendingTransition(R.anim.righttranslate, R.anim.fade_out);
                break;
            case R.id.buttonCollapsingToolbarFab:
                Intent CollapsingToolbarFabIntent = new Intent(this, CollapsingToolbarFabs.class);
                startActivity(CollapsingToolbarFabIntent);
                overridePendingTransition(R.anim.righttranslate, R.anim.fade_out);
                break;
            case R.id.buttonToolbarTabs:
                Intent ToolbarTabsIntent = new Intent(this, ToolbarTabs.class);
                startActivity(ToolbarTabsIntent);
                overridePendingTransition(R.anim.righttranslate, R.anim.fade_out);
                break;
            case R.id.buttonCollapsingToolbarTabs:
                Intent CollapsingToolbarTabsIntent = new Intent(this, CollapsingToolbarTabs.class);
                startActivity(CollapsingToolbarTabsIntent);
                overridePendingTransition(R.anim.righttranslate, R.anim.fade_out);
                break;
            case R.id.cmdLogin:
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                overridePendingTransition(R.anim.righttranslate, R.anim.fade_out);
                break;
            case R.id.cmdSignup:
                Intent signUpIntent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(signUpIntent);
                overridePendingTransition(R.anim.righttranslate, R.anim.fade_out);
                break;

        }

    }
}
