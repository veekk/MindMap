package com.example.veek.mindmap.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;

import com.example.veek.mindmap.descr.Shape;
import com.example.veek.mindmap.model.MindMapElement;

/**
 * Crafted by veek on 21.12.15 with love ♥
 */
public class ElementView extends View {

    Paint paintFill = new Paint();
    Paint paintStroke = new Paint();
    Paint paintFillWhite = new Paint();
    Paint paint = new Paint();
    MindMapElement element;
    Path triangle = new Path();
    ViewGroup.LayoutParams params;


    public long getElementId() {
        return element.getId();
    }

    public ElementView(Context context, MindMapElement element) {
        super(context);
        params = new ViewGroup.LayoutParams(element.getWidth(), element.getHeight());
        this.setLayoutParams(params);
        this.element = element;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
        layoutParams.height = element.getHeight();
        layoutParams.width = element.getWidth();
        this.requestLayout();


        triangle.rewind();
        triangle.moveTo(getWidth() / 2, 4);
        triangle.lineTo(4, getHeight() - 4);
        triangle.lineTo(getWidth() - 4, getHeight() - 4);
        triangle.lineTo(getWidth() / 2, 4);
        triangle.close();

        paintFill.setAntiAlias(true);
        paintFill.setStyle(Paint.Style.FILL);
        paintFill.setColor(element.getColor());

        paintFillWhite.setAntiAlias(true);
        paintFillWhite.setStyle(Paint.Style.FILL);
        paintFillWhite.setColor(Color.WHITE);

        paintStroke.setAntiAlias(true);
        paintStroke.setStyle(Paint.Style.STROKE);
        paintStroke.setColor(Color.BLACK);
        paintStroke.setStrokeWidth(4);

        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextSize(24f);
        paint.setTextAlign(Paint.Align.CENTER);

        switch(element.getShape()){
            case Shape.CIRCLE:
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, element.getHeight() / 2 - 4, paintFillWhite);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, element.getHeight() / 2 - 4, paintFill);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, element.getHeight() / 2 - 4, paintStroke);
                canvas.drawText(element.getText(), element.getWidth() / 2, element.getHeight() / 2, paint);
                break;

            case Shape.TRIANGLE:
                canvas.drawPath(triangle, paintFillWhite);
                canvas.drawPath(triangle, paintFill);
                canvas.drawPath(triangle, paintStroke);
                canvas.drawText(element.getText(), element.getWidth() / 2, (element.getHeight() / 3) * 2, paint);
                break;

            case Shape.RECTANGLE:
                canvas.drawRect(4, 4, getWidth() - 4, getHeight() - 4, paintFillWhite);
                canvas.drawRect(4, 4, getWidth() - 4, getHeight() - 4, paintFill);
                canvas.drawRect(4, 4, getWidth() - 4, getHeight() - 4, paintStroke);
                canvas.drawText(element.getText(), element.getWidth() / 2, element.getHeight() / 2, paint);
                break;
        }
        super.onDraw(canvas);
    }
}
