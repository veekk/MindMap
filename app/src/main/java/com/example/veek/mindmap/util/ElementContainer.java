package com.example.veek.mindmap.util;

import android.content.Context;
import android.renderscript.Element;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.veek.mindmap.model.MindMapElement;

import java.util.ArrayList;

/**
 * Crafted by veek on 21.12.15 with love ♥
 */
public class ElementContainer extends FrameLayout {

    private static final String TAG = "xyj";
    Context context;
    ElementView currentElement;

    public ElementContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void addElement(MindMapElement element){
        final ElementView elementView = new ElementView(context, element);
        elementView.setX(element.getX());
        elementView.setY(element.getY());
        elementView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        currentElement = elementView;
                        Log.d(TAG, "onTouch: ");
                        return false;
                    case MotionEvent.ACTION_CANCEL:
                        Log.d(TAG, "onTouch: cancle");
                        break;
                }
                return false;
            }
        });
        this.addView(elementView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_MOVE:
                currentElement.setX(event.getX());
                currentElement.setY(event.getY());
                Log.d(TAG, "onTouchEvent: ");
                return true;

        }
        return super.onTouchEvent(event);
    }
}
