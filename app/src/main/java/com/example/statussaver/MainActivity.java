package com.example.statussaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.example.statussaver.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import adapter.ViewPageradapter;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    TabLayout tab;
    ViewPager viewPager;
    ViewPageradapter viewPageradapter;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar=binding.toolBar;
        setSupportActionBar(toolbar);
        toolbar.setTitle("StatusSaver");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        tab = binding.tab;
        viewPager = binding.viewpager;
        ViewPageradapter adapter=new ViewPageradapter(getSupportFragmentManager());


        viewPager.setAdapter(adapter);
        tab.setupWithViewPager(viewPager);
    }
}