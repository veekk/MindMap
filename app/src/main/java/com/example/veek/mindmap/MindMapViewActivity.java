package com.example.veek.mindmap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.veek.mindmap.fragment.MindMapViewFragment;
import com.example.veek.mindmap.util.CustomFragmentManager;
import com.example.veek.mindmap.util.MindMapAdapter;

public class MindMapViewActivity extends AppCompatActivity {

    CustomFragmentManager fragmentManager = CustomFragmentManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mind_map_view);
        MindMapViewFragment fragment = new MindMapViewFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id", getIntent().getLongExtra("id", 0));
        fragment.setArguments(bundle);
        fragmentManager.init(this, R.id.frLay2);
        fragmentManager.setFragment(fragment, false);
    }
}
