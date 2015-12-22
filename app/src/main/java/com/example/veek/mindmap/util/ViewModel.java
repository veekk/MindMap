//package com.example.veek.mindmap.util;
//
//
//import android.annotation.TargetApi;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.os.Build;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.RadioButton;
//
//import com.example.veek.mindmap.R;
//import com.example.veek.mindmap.descr.Shape;
//import com.example.veek.mindmap.model.MindMapModel;
//
//import java.util.ArrayList;
//
//import java.util.Random;
//
///**
// * Created by Veek on 23.11.2015.
// */
//public  class ViewModel extends ImageView {
//
//    final ArrayList<Element> elements = new ArrayList<>();
//    final int DIALOG = 1;
//
//    final Random random = new Random();
//
//    MindMapModel model;
//
//    Element findedElement = null;
//    Paint paint;
//
//
//
//    public ViewModel(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        paint = new Paint();
//        paint.setAntiAlias(true);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        for (Element element :
//                elements) {
//            paint.setColor(element.color);
//            canvas.drawCircle(element.x, element.y, element.r, paint);
//        }
//
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        switch (event.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN: // нажатие
//                findedElement = findElement(event.getX(), event.getY());
//                if (findedElement != null) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                    builder.setView(R.layout.dialog_add_el)
//                            .setCancelable(true)
//                            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    final int[] shape = new int[1];
//                                    int x,y;
//                                    String text;
//                                    final RadioButton element = (RadioButton) findViewById(R.id.rBtnCrcl);
//                                    final RadioButton rect = (RadioButton) findViewById(R.id.rBtnRect);
//                                    final RadioButton triangle = (RadioButton) findViewById(R.id.rBtnTri);
//                                    element.setOnClickListener(new OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            element.setChecked(true);
//                                            rect.setChecked(false);
//                                            triangle.setChecked(false);
//                                            shape[0] = Shape.CIRCLE;
//                                        }
//                                    });
//                                    rect.setOnClickListener(new OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            element.setChecked(false);
//                                            rect.setChecked(true);
//                                            triangle.setChecked(false);
//                                            shape[0] = Shape.RECTANGLE;
//                                        }
//                                    });
//                                    triangle.setOnClickListener(new OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            element.setChecked(false);
//                                            rect.setChecked(false);
//                                            triangle.setChecked(true);
//                                            shape[0] = Shape.TRIANGLE;
//                                        }
//                                    });
//                                    x = (int) getX();
//                                    y = (int) getY();
//
//
//
//                                }
//                            });
//                }
//                else {
//                    return true;
//                }
//                invalidate();
//                return true;
//            case MotionEvent.ACTION_MOVE: // движение
//                if (findedElement != null) {
//                    findedElement.setX(event.getX());
//                    findedElement.setY(event.getY());
//                    invalidate();
//                    return true;
//                }
//                else {
//                    Element currentElement = elements.get(elements.size() - 1);
//                    currentElement.setX(event.getX());
//                    currentElement.setY(event.getY());
//                }
//
//                invalidate();
//                return true;
//            case MotionEvent.ACTION_UP: // отпускание
//                return true;
//            case MotionEvent.ACTION_CANCEL:
//                return true;
//        }
//        return super.onTouchEvent(event);
//    }
//
//
//    private Element findElement(float x, float y) {
//        Element result = null;
//        for (Element element :
//                elements) {
//            if (element.x + element.r >= x &&
//                    element.x - element.r <= x &&
//                    element.y - element.r <= y &&
//                    element.y + element.r >= y) {
//                result = element;
//            }
//
//        }
//
//        return result;
//    }
//
//
//    private class Element {
//        int r, color;
//        float x, y;
//        public Element(float x, float y){
//            r = random.nextInt(200);
//            this.color = Color.rgb(random.nextInt(255),
//                    random.nextInt(255),
//                    random.nextInt(255));
//            this.x = x;
//            this.y = y;
//        }
//
//        public void setX(float x) {
//            this.x = x;
//        }
//
//        public void setY(float y) {
//            this.y = y;
//        }
//    }
//
//
//}