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
    String name;
    int backgroundColor;

//    public MindMapModel(ArrayList<MindMapElement> elements, long id, int backgroundColor) {
//        this.elements = elements;
//        this.id = id;
//        this.backgroundColor = backgroundColor;
//    }

    public MindMapModel(String name, long id) {
        this.id = id;
        this.name = name;
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
        long id = System.currentTimeMillis();
        elements.add(new MindMapElement(id, text, color, shape, x, y, width, height));
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<MindMapElement> getElements() {
        return elements;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }
}

