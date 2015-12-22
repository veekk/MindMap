package com.example.veek.mindmap.model;

import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by veek on 30.11.15.
 */
public class MindMapModel implements Serializable{
    ArrayList<MindMapElement> elements = new ArrayList<>();
    long id;
    int backgroundColor;

    final Random random = new Random();

//    public MindMapModel(ArrayList<MindMapElement> elements, long id, int backgroundColor) {
//        this.elements = elements;
//        this.id = id;
//        this.backgroundColor = backgroundColor;
//    }

    public MindMapModel(long id) {
        //this.elements = elements;
        this.id = id;
        this.backgroundColor = Color.WHITE;
    }

    public MindMapElement getElementById(long id){
        for (MindMapElement element :
                elements) {
            if (element.getId() == id) {
                return element;
            }
        }
        return  null;
    }

    public void addElement(int x, int y, int shape, String text, int color, int width, int height){
        long id = random.nextInt(10000);
        elements.add(new MindMapElement(id, text, color, shape, x, y, width, height));
    }

    public long getId() {
        return id;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }
}

