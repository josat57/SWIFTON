package com.swifton.swifton;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;


public class JustTest extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_test);
        imageView = findViewById(R.id.action_image);

        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                setColor(view);
            }
        });
    }

    public void setColor(View v){
        imageView.setColorFilter(Color.BLUE);
    }
    public void redClick(View v) {
        imageView.setColorFilter(Color.RED);
    }

    public void greenClick(View v) {
        //imageView.setColorFilter(Color.GREEN);
        imageView.setColorFilter(R.color.colorPrimaryDark);
    }

    public void yellowClick(View v) {
        imageView.setColorFilter(Color.YELLOW);}
}
