package com.example.veek.mindmap.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.veek.mindmap.R;
import com.example.veek.mindmap.descr.Shape;
import com.example.veek.mindmap.model.MindMapElement;
import com.example.veek.mindmap.util.ElementContainer;

/**
 * Crafted by veek on 21.12.15 with love ♥
 */
public class MindMapViewFragment extends Fragment {

    View rootView;
    ElementContainer elements;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.mind_map_fragment, null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        elements = (ElementContainer) rootView.findViewById(R.id.elementContainer);
        FloatingActionButton fabAdd = (FloatingActionButton) rootView.findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View dView = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_el, null);
                builder.setView(dView)
                        .setCancelable(true)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int shape = 0;
                                RadioButton rbRect = (RadioButton) dView.findViewById(R.id.rbRect);
                                RadioButton rbCircle = (RadioButton) dView.findViewById(R.id.rbCircle);
                                RadioButton rbTriangle = (RadioButton) dView.findViewById(R.id.rbCircle);
                                if (rbRect.isChecked() == true) shape = Shape.RECTANGLE; else
                                if (rbCircle.isChecked() == true) shape = Shape.CIRCLE; else
                                shape = Shape.TRIANGLE;
                                elements.addElement(new MindMapElement(1, "13", shape, Color.BLUE, 330, 330, 322, 322));
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

}