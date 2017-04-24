package com.example.veek.mindmap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.veek.mindmap.fragment.LoginScreen;
import com.example.veek.mindmap.fragment.MindMapListFragment;
import com.example.veek.mindmap.fragment.MindMapViewFragment;
import com.example.veek.mindmap.util.AccountManager;
import com.example.veek.mindmap.util.CustomFragmentManager;

public class MainActivity extends AppCompatActivity {

    CustomFragmentManager fragmentManager = CustomFragmentManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AccountManager.getInstance().init(this);
        fragmentManager.init(this, R.id.frameContainer);
        fragmentManager.setFragment(new LoginScreen(), false);
    }
}
