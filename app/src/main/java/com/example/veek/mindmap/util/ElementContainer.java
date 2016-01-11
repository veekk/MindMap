package com.example.veek.mindmap.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.renderscript.Element;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.veek.mindmap.R;
import com.example.veek.mindmap.descr.Shape;
import com.example.veek.mindmap.model.MindMapElement;
import com.example.veek.mindmap.model.MindMapModel;

import java.util.ArrayList;

/**
 * Crafted by veek on 21.12.15 with love ♥
 */
public class ElementContainer extends FrameLayout {

    Context context;
    MindMapModel mmModel;
    ElementView currentElement;

    public ElementContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void setMmModel(MindMapModel mmModel) {
        this.mmModel = mmModel;
    }

    public void addElement(MindMapElement element) {
        final ElementView elementView = new ElementView(context, element);
        elementView.setX(element.getX());
        elementView.setY(element.getY());
        this.addView(elementView);
    }

    public void moveElement(View element, float x, float y) {
        element.setX((int) x - element.getWidth() / 2);
        element.setY((int) y - element.getHeight() / 2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final float x, y;
        final int width, height;
        x = event.getX();
        y = event.getY();
        width = 100;
        height = 100;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                currentElement = getTouchedElement(event.getX(), event.getY());
                if (currentElement == null) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    final int[] shape = {0};
                    final CharSequence[] shapes = {" Rectangle ", " Circle ", " Triangle "};
                    builder.setSingleChoiceItems(shapes, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
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
                                    MindMapElement element = new MindMapElement(System.currentTimeMillis(), "13", shape[0], Color.BLUE, (int) (x - width / 2), (int) (y - height / 2), width, height);
                                    mmModel.getElements().add(element);
                                    addElement(element);
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

            case MotionEvent.ACTION_MOVE:
                Log.d("motion", "onTouchEvent: ");
                if (currentElement != null) {
                    moveElement(currentElement, event.getX(), event.getY());
                }
                break;

            case MotionEvent.ACTION_UP:
                if (currentElement != null) {
                    MindMapElement e = mmModel.getElementById(currentElement.getElementId());
                    e.setY((int) event.getY() - e.getWidth() / 2);
                    e.setX((int) event.getX() - e.getHeight() / 2);
                }
        }
        return super.onTouchEvent(event);
    }


    public ElementView getTouchedElement(float x, float y) {
        for (int i = getChildCount()- 1; i >= 0; i--) {
            View v = getChildAt(i);
            float xy = v.getX();
            float yx = v.getY();
            float width = v.getWidth();
            if (((x >= v.getX()) && (x <= v.getX() + v.getWidth())) && ((y >= v.getY()) && (y <= v.getY() + v.getHeight()))) {
                return (ElementView)v;
            }
        }
        return null;
    }
}


