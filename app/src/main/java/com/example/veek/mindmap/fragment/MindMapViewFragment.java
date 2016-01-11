package com.example.veek.mindmap.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.veek.mindmap.R;
import com.example.veek.mindmap.descr.Shape;
import com.example.veek.mindmap.model.MindMapElement;
import com.example.veek.mindmap.model.MindMapModel;
import com.example.veek.mindmap.util.AccountManager;
import com.example.veek.mindmap.util.ElementContainer;
import com.example.veek.mindmap.util.Serializabler;

import java.io.IOException;

/**
 * Crafted by veek on 21.12.15 with love ♥
 */
public class MindMapViewFragment extends Fragment {

    View rootView;
    ElementContainer elementViewGroup;
    MindMapModel mmModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.mind_map_fragment, null);
        Bundle bundle = getArguments();
        try {
            mmModel = Serializabler.loadObject(bundle.getLong("id"), getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final float x,y;
        elementViewGroup = (ElementContainer) rootView.findViewById(R.id.elementContainer);
        if (mmModel != null) {
            elementViewGroup.setMmModel(mmModel);
            for (MindMapElement element :
                    mmModel.getElements()) {
                elementViewGroup.addElement(element);
            }
        }

        FloatingActionButton fabAdd = (FloatingActionButton) rootView.findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View dView = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_el, null);
                final int[] shape = {0};
                final CharSequence[] shapes = {" Rectangle "," Circle "," Triangle "};
                builder.setSingleChoiceItems(shapes, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        shape[0] = Shape.RECTANGLE;
                                        break;

                                    case 1:
                                        shape[0] = Shape.CIRCLE;
                                        break;

                                    case 2:
                                        shape[0] = Shape.TRIANGLE;
                                        break;

                                }
                            }
                        })
                        .setTitle("Adding element")
                        .setCancelable(true)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MindMapElement element = new MindMapElement(1, "13", shape[0], Color.BLUE, 330, 330, 322, 322);
                                mmModel.getElements().add(element);
                                elementViewGroup.addElement(element);
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
        });

    }



    @Override
    public void onPause() {
        try {
            Serializabler.saveObject(mmModel, getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onPause();
    }
}
