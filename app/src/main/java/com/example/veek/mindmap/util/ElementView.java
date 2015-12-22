package com.example.veek.mindmap.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import android.view.ViewGroup;

import com.example.veek.mindmap.descr.Shape;
import com.example.veek.mindmap.model.MindMapElement;
import com.example.veek.mindmap.model.MindMapModel;

/**
 * Crafted by veek on 21.12.15 with love ♥
 */
public class ElementView extends View {

    Paint paint_fill = new Paint();
    Paint paint_stroke = new Paint();
    MindMapElement element;
    Path triangle = new Path();

    public ElementView(Context context, MindMapElement element) {
        super(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(element.getWidth(), element.getHeight());
        this.setLayoutParams(params);
        this.element = element;
        paint_fill.setAntiAlias(true);
        paint_fill.setStyle(Paint.Style.FILL);
        paint_fill.setColor(element.getColor());
        paint_stroke.setAntiAlias(true);
        paint_stroke.setStyle(Paint.Style.STROKE);
        paint_stroke.setColor(Color.BLACK);
        paint_stroke.setStrokeWidth(4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        triangle.rewind();
        triangle.moveTo(getWidth() / 2, 4);
        triangle.lineTo(4, getHeight() - 4);
        triangle.lineTo(getWidth() - 4, getHeight() - 4);
        triangle.lineTo(getWidth() / 2, 4);
        triangle.close();
        switch(element.getShape()){
            case Shape.CIRCLE:
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, element.getHeight() / 2 - 4, paint_fill);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, element.getHeight() / 2 - 4, paint_stroke);
                break;

            case Shape.TRIANGLE:
                canvas.drawPath(triangle, paint_fill);
                canvas.drawPath(triangle, paint_stroke);
                break;

            case Shape.RECTANGLE:
                canvas.drawRect(4, 4, getWidth() - 4, getHeight() - 4, paint_fill);
                canvas.drawRect(4, 4, getWidth() - 4, getHeight() - 4, paint_stroke);
                break;
        }
        super.onDraw(canvas);
    }
}
