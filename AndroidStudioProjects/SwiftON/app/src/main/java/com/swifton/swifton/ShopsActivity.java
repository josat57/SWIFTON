package com.swifton.swifton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ShopsActivity extends AppCompatActivity {

    Button homeButton, dashboardButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        homeButton = findViewById(R.id.cmdHome);
        dashboardButton = findViewById(R.id.cmdDashboard);



        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent homeIntent = new Intent(ShopsActivity.this, MainActivity.class);
                startActivity(homeIntent);
                overridePendingTransition(R.anim.translate, R.anim.fade_out);
                finish();
            }
        });

        dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dashInent = new Intent(ShopsActivity.this, dashboardActivity.class);
                startActivity(dashInent);
                overridePendingTransition(R.anim.righttranslate, R.anim.lefttrslate);
                finish();
            }
        });
    }
}
