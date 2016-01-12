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
        final float x, y;
        elementViewGroup = (ElementContainer) rootView.findViewById(R.id.elementContainer);
        if (mmModel != null) {
            elementViewGroup.setMmModel(mmModel);
            for (MindMapElement element :
                    mmModel.getElements()) {
                elementViewGroup.addElement(element);
            }
        }
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
