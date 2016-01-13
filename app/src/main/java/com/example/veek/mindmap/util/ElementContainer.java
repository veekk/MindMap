package com.example.veek.mindmap.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.veek.mindmap.R;
import com.example.veek.mindmap.descr.Shape;
import com.example.veek.mindmap.model.MindMapElement;
import com.example.veek.mindmap.model.MindMapModel;
import com.larswerkman.lobsterpicker.sliders.LobsterOpacitySlider;
import com.larswerkman.lobsterpicker.sliders.LobsterShadeSlider;


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
        currentElement = elementView;
        this.addView(elementView);
    }

    public void moveElement(View element, float x, float y) {
        element.setX((int) x - element.getWidth() / 2);
        element.setY((int) y - element.getHeight() / 2);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final float x, y;
        x = event.getX();
        y = event.getY();
        detector.onTouchEvent(event);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                currentElement = getTouchedElement(event.getX(), event.getY());
               if (!isAddingRelation) {
                   if (currentElement == null) {
                       final int[] shape = new int[1];
                       final int[] size = {0};

                       LinearLayout view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_edit_el, null);
                       final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                       final EditText editText = (EditText) view.findViewById(R.id.etEditEl);

                       final RadioButton rbtC = (RadioButton) view.findViewById(R.id.rbtC);
                       final RadioButton rbtR = (RadioButton) view.findViewById(R.id.rbtR);
                       final RadioButton rbtT = (RadioButton) view.findViewById(R.id.rbtT);

                       final RadioButton rbtSmall = (RadioButton) view.findViewById(R.id.rbtSmall);
                       final RadioButton rbtMed = (RadioButton) view.findViewById(R.id.rbtMed);
                       final RadioButton rbtBig = (RadioButton) view.findViewById(R.id.rbtBig);

                       final RadioGroup rGroupShape = (RadioGroup) view.findViewById(R.id.rGroupShape);
                       final RadioGroup rGroupSize = (RadioGroup) view.findViewById(R.id.rGroupSize);


                       final LobsterShadeSlider shadeSlider = (LobsterShadeSlider) view.findViewById(R.id.shadeslider);
                       LobsterOpacitySlider opacitySlider = (LobsterOpacitySlider) view.findViewById(R.id.opacityslider);

                       shadeSlider.addDecorator(opacitySlider);

                       builder.setView(view)
                               .setTitle("Add element")
                               .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                       switch (rGroupShape.getCheckedRadioButtonId()) {
                                           case R.id.rbtR:
                                               shape[0] = Shape.RECTANGLE;
                                               break;

                                           case R.id.rbtC:
                                               shape[0] = Shape.CIRCLE;
                                               break;

                                           case R.id.rbtT:
                                               shape[0] = Shape.TRIANGLE;
                                               break;
                                       }
                                       switch (rGroupSize.getCheckedRadioButtonId()) {
                                           case R.id.rbtSmall:
                                               size[0] = Shape.SMALL;
                                               break;
                                           case R.id.rbtMed:
                                               size[0] = Shape.MEDIUM;
                                               break;
                                           case R.id.rbtBig:
                                               size[0] = Shape.BIG;
                                               break;
                                       }
                                       MindMapElement element = new MindMapElement(System.currentTimeMillis(), editText.getText().toString(), shape[0], shadeSlider.getColor(),
                                               (int) (x - size[0] / 2), (int) (y - size[0] / 2), size[0], size[0]);
                                       mmModel.getElements().add(element);
                                       addElement(element);
                                       dialog.dismiss();
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
                        MindMapElement e = mmModel.getElementById(currentElement.getElementId());
                        float xx, yy;
                        if (event.getX() <= e.getWidth() / 2) xx = - 1 + e.getWidth() / 2; else
                            if (event.getX() >= getWidth() - e.getWidth() / 2)
                                xx = getWidth() + 1- e.getWidth() / 2; else xx = event.getX();
                        if (event.getY() <= e.getHeight() / 2) yy = -1 + e.getHeight() / 2; else
                            if (event.getY() >= getHeight() - e.getHeight() / 2)
                                yy = getHeight() + 1 - e.getHeight() / 2; else yy = event.getY();
                        moveElement(currentElement, xx, yy);
                        e.setY((int) yy - e.getWidth() / 2);
                        e.setX((int) xx - e.getHeight() / 2);
                    }
                }
                invalidate();
                return true;

            case MotionEvent.ACTION_UP:
                if (currentElement != null){
                if (isAddingRelation) {
                    if (idFirst != currentElement.getElementId()) {
                        idSecond = currentElement.getElementId();
                        mmModel.addRelation(idFirst, idSecond);
                        idFirst = -1;
                        idSecond = -1;
                        isAddingRelation = false;
                    }
                }
                } else {
                    if (isAddingRelation) {
                        isAddingRelation = false;
                        Toast.makeText(context, "You can't add relation to empty space, try again", Toast.LENGTH_SHORT).show();
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

            if (currentElement != null) {

                idFirst = currentElement.getElementId();

                final int[] shape = new int[1];
                final int[] size = {0};

                LinearLayout view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_edit_el, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final ElementView ev = currentElement;
                final MindMapElement ee = mmModel.getElementById(ev.getElementId());
                final EditText editText = (EditText) view.findViewById(R.id.etEditEl);

                final RadioButton rbtC = (RadioButton) view.findViewById(R.id.rbtC);
                final RadioButton rbtR = (RadioButton) view.findViewById(R.id.rbtR);
                final RadioButton rbtT = (RadioButton) view.findViewById(R.id.rbtT);

                final RadioButton rbtSmall = (RadioButton) view.findViewById(R.id.rbtSmall);
                final RadioButton rbtMed = (RadioButton) view.findViewById(R.id.rbtMed);
                final RadioButton rbtBig = (RadioButton) view.findViewById(R.id.rbtBig);

                final RadioGroup rGroupShape = (RadioGroup) view.findViewById(R.id.rGroupShape);
                final RadioGroup rGroupSize = (RadioGroup) view.findViewById(R.id.rGroupSize);


                final LobsterShadeSlider shadeSlider = (LobsterShadeSlider) view.findViewById(R.id.shadeslider);
                LobsterOpacitySlider opacitySlider = (LobsterOpacitySlider) view.findViewById(R.id.opacityslider);

                shadeSlider.addDecorator(opacitySlider);
                shadeSlider.setColor(ee.getColor());
                editText.setText(ee.getText());
                switch (ee.getShape()){
                    case Shape.CIRCLE:
                        rbtC.setChecked(true);
                        break;
                    case Shape.RECTANGLE:
                        rbtR.setChecked(true);
                        break;
                    case Shape.TRIANGLE:
                        rbtT.setChecked(true);
                        break;
                }
                switch (ee.getHeight()){
                    case Shape.SMALL:
                        rbtSmall.setChecked(true);
                        break;
                    case Shape.MEDIUM:
                        rbtMed.setChecked(true);
                        break;
                    case Shape.BIG:
                        rbtBig.setChecked(true);
                        break;
                }

                builder.setView(view)
                        .setTitle("Edit element")
                        .setNegativeButton("Save & add relation", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                isAddingRelation = true;
                                switch (rGroupShape.getCheckedRadioButtonId()){
                                    case R.id.rbtR:
                                        shape[0] = Shape.RECTANGLE;
                                        break;

                                    case R.id.rbtC:
                                        shape[0] = Shape.CIRCLE;
                                        break;

                                    case R.id.rbtT:
                                        shape[0] = Shape.TRIANGLE;
                                        break;
                                }
                                switch (rGroupSize.getCheckedRadioButtonId()){
                                    case R.id.rbtSmall:
                                        size[0] = Shape.SMALL;
                                        break;
                                    case R.id.rbtMed:
                                        size[0] = Shape.MEDIUM;
                                        break;
                                    case R.id.rbtBig:
                                        size[0] = Shape.BIG;
                                        break;
                                }
                                ee.setText(editText.getText().toString());
                                ee.setColor(shadeSlider.getColor());
                                ee.setHeight(size[0]);
                                ee.setWidth(size[0]);
                                ee.setShape(shape[0]);
                                ev.invalidate();
                                dialog.dismiss();
                                Toast.makeText(context, "Add new relation by tapping on another element", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (rGroupShape.getCheckedRadioButtonId()){
                                    case R.id.rbtR:
                                        shape[0] = Shape.RECTANGLE;
                                        break;

                                    case R.id.rbtC:
                                        shape[0] = Shape.CIRCLE;
                                        break;

                                    case R.id.rbtT:
                                        shape[0] = Shape.TRIANGLE;
                                        break;
                                }
                                switch (rGroupSize.getCheckedRadioButtonId()){
                                    case R.id.rbtSmall:
                                        size[0] = Shape.SMALL;
                                        break;
                                    case R.id.rbtMed:
                                        size[0] = Shape.MEDIUM;
                                        break;
                                    case R.id.rbtBig:
                                        size[0] = Shape.BIG;
                                        break;
                                }
                                ee.setText(editText.getText().toString());
                                ee.setColor(shadeSlider.getColor());
                                ee.setHeight(size[0]);
                                ee.setWidth(size[0]);
                                ee.setShape(shape[0]);
                                ev.invalidate();

                            }
                        })
                        .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mmModel.removeRelation(ev.getElementId());
                                mmModel.delElement(ev.getElementId());
                                removeView(ev);

                            }
                        })

                        .show();

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
            if (eFirst != null && eSecond != null) {
                canvas.drawLine(eFirst.getX() + eFirst.getWidth() / 2, eFirst.getY() + eFirst.getHeight() / 2,
                        eSecond.getX() + eSecond.getWidth() / 2, eSecond.getY() + eSecond.getHeight() / 2, paint);
            }
        }
        super.onDraw(canvas);
    }
}


