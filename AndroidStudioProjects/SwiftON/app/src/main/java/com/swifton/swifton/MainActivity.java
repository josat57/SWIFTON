package com.swifton.swifton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button exitbtn,cmd_login,cmd_signup,cmd_shop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exitbtn = findViewById(R.id.cmdExit);
        cmd_login = findViewById(R.id.cmdLogin);
        cmd_signup = findViewById(R.id.cmdSignup);
        cmd_shop = findViewById(R.id.cmdShops);

        cmd_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                overridePendingTransition(R.anim.righttranslate, R.anim.fade_out);
            }
        });

        cmd_signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent signUpIntent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(signUpIntent);
                overridePendingTransition(R.anim.righttranslate, R.anim.fade_out);
            }
        });

        cmd_shop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent shopsIntent = new Intent(MainActivity.this, ShopsActivity.class);
                startActivity(shopsIntent);
                overridePendingTransition(R.anim.righttranslate, R.anim.fade_out);
            }
        });

        exitbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                finish();
            }
        });
    }
}
