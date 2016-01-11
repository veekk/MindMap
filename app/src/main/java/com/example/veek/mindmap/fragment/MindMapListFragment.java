package com.example.veek.mindmap.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.veek.mindmap.R;
import com.example.veek.mindmap.model.Account;
import com.example.veek.mindmap.model.MindMapModel;
import com.example.veek.mindmap.util.AccountManager;
import com.example.veek.mindmap.util.MindMapAdapter;
import com.example.veek.mindmap.util.Serializabler;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Crafted by veek on 30.11.15 with love ♥
 */

public class MindMapListFragment extends Fragment {
    private MindMapAdapter adapter;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.mind_map_list_fragment, null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.mapRecycler);
        adapter = new MindMapAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.initData(AccountManager.getInstance().getCurrentAccount().getMaps());
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mind_map_list_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View dView = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_map, null);
                final long id = System.currentTimeMillis();
                builder.setView(dView)
                        .setCancelable(true)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText etMmnAdd = (EditText) dView.findViewById(R.id.etMmnadd);
                                MindMapModel model = new MindMapModel(etMmnAdd.getText().toString(), id);
                                AccountManager.getInstance().addMindMap(model.getId(), model.getName());
                                try {
                                    Serializabler.saveObject(model, getActivity());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getActivity(), "File not found", Toast.LENGTH_LONG).show();
                                }
                                adapter.addRow(etMmnAdd.getText().toString(), id);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
        }
        return true;
    }
}
