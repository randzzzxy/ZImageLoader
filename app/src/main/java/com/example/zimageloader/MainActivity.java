package com.example.zimageloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.library.ZImageLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.imageView);
        ZImageLoader.with(getLifecycle()).load("https://seopic.699pic.com/photo/40100/6015.jpg_wh1200.jpg").into(imageView);

    }
}