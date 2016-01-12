package com.example.veek.mindmap.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.renderscript.Element;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

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
    boolean isAddingRelation = false;
    long idFirst, idSecond;
    Paint paint = new Paint();

    public ElementContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        setWillNotDraw(false);
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
        detector.onTouchEvent(event);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                currentElement = getTouchedElement(event.getX(), event.getY());
               if (!isAddingRelation) {
                   if (currentElement == null) {
                       final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                       final int[] shape = {0};
                       final int[] color = {0};
                       final int[] size = {0};
                       final String[] text = new String[1];
                       final EditText input = new EditText(context);
                       final CharSequence[] shapes = {" Rectangle ", " Circle ", " Triangle "};
                       final CharSequence[] colors = {" Red ", " Green ", " Blue ", " Yellow "};
                       final CharSequence[] sizes = {" Small ", " Medium ", " Big "};
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
                               .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                       AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                                       builder.setSingleChoiceItems(colors, -1, new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {
                                               switch (which){
                                                   case 0:
                                                       color[0] = Color.RED;
                                                       break;
                                                   case 1:
                                                       color[0] = Color.GREEN;
                                                       break;
                                                   case 2:
                                                       color[0] = Color.BLUE;
                                                       break;
                                                   case 3:
                                                       color[0] = Color.YELLOW;
                                                       break;
                                               }
                                           }
                                       })
                                               .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                                                   @Override
                                                   public void onClick(DialogInterface dialog, int which) {
                                                       AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                                                       builder2.setSingleChoiceItems(sizes, -1, new DialogInterface.OnClickListener() {
                                                           @Override
                                                           public void onClick(DialogInterface dialog, int which) {
                                                               switch (which){
                                                                   case 0:
                                                                       size[0] = Shape.SMALL;
                                                                       break;
                                                                   case 1:
                                                                       size[0] = Shape.MEDIUM;
                                                                       break;
                                                                   case 2:
                                                                       size[0] = Shape.BIG;
                                                                       break;
                                                               }
                                                           }
                                                       })
                                                               .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                                                   @Override
                                                                   public void onClick(DialogInterface dialog, int which) {
                                                                       AlertDialog.Builder builder3 = new AlertDialog.Builder(context);
                                                                       LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                                                               LinearLayout.LayoutParams.MATCH_PARENT,
                                                                               LinearLayout.LayoutParams.MATCH_PARENT);
                                                                       input.setLayoutParams(lp);
                                                                       builder3.setView(input)
                                                                               .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                                                                   @Override
                                                                                   public void onClick(DialogInterface dialog, int which) {
                                                                                       text[0] = input.getText().toString();
                                                                                       MindMapElement element = new MindMapElement(System.currentTimeMillis(), text[0], shape[0], color[0],
                                                                                               (int) (x - size[0] / 2), (int) (y - size[0] / 2), size[0], size[0]);
                                                                                       mmModel.getElements().add(element);
                                                                                       addElement(element);
                                                                                   }
                                                                               })
                                                                               .show();

                                                                   }
                                                               })
                                                               .show();

                                                   }
                                               })
                                               .show();
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
               }
                return true;

            case MotionEvent.ACTION_MOVE:
                Log.d("motion", "onTouchEvent: ");
                if (!isAddingRelation) {
                    if (currentElement != null) {
                        moveElement(currentElement, event.getX(), event.getY());
                        MindMapElement e = mmModel.getElementById(currentElement.getElementId());
                        e.setY((int) event.getY() - e.getWidth() / 2);
                        e.setX((int) event.getX() - e.getHeight() / 2);
                    }
                }
                invalidate();
                return true;

            case MotionEvent.ACTION_UP:
                if (isAddingRelation) {
                    if (idFirst != currentElement.getElementId()) {
                        idSecond = currentElement.getElementId();
                        mmModel.addRelation(idFirst, idSecond);
                        idFirst = -1;
                        idSecond = -1;
                        isAddingRelation = false;
                    }
                }
                currentElement = null;
                invalidate();
                break;

        }
        return super.onTouchEvent(event);
    }

    GestureDetectorCompat detector = new GestureDetectorCompat(context, new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Toast.makeText(context, "Add new relation by tapping on another huj", Toast.LENGTH_SHORT).show();
            if (currentElement != null) {
                isAddingRelation = true;
                idFirst = currentElement.getElementId();
            }

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    });

    public ElementView getTouchedElement(float x, float y) {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View v = getChildAt(i);
            if (((x >= v.getX()) && (x <= v.getX() + v.getWidth())) && ((y >= v.getY()) && (y <= v.getY() + v.getHeight()))) {
                return (ElementView)v;
            }
        }
        return null;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (MindMapModel.Pair relations :
                mmModel.getRelations()) {
            MindMapElement eFirst = mmModel.getElementById(relations.getFirst());
            MindMapElement eSecond = mmModel.getElementById(relations.getSecond());
            canvas.drawLine(eFirst.getX() + eFirst.getWidth() / 2, eFirst.getY() + eFirst.getHeight() / 2,
                    eSecond.getX() + eSecond.getWidth() / 2, eSecond.getY() + eSecond.getHeight() / 2, paint);
        }
        super.onDraw(canvas);
    }
}


