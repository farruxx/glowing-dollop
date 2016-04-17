package com.farruxx.glowingdollop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.farruxx.glowingdollop.fragment.MainFragment;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.root, new MainFragment())
                    .commit();
        }
    }
}
