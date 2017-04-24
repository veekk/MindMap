package com.example.veek.mindmap.model;

import com.example.veek.mindmap.descr.Shape;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by veek on 30.11.15.
 */
public class MindMapElement implements Serializable{
    long id;
    ArrayList<Long> relations = new ArrayList<>();

    String text;
    int shape;
    int color;
    int x,y;
    int width, height;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public MindMapElement(long id, String text, int shape, int color, int x, int y, int width, int height) {
        this.id = id;
        this.text = text;
        this.shape = shape;
        this.color = color;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public void addRelation(long id){
        relations.add(id);
    }

    public void delRelation(long id){
        relations.remove(id);
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getShape() {
        return shape;
    }

    public int getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setShape(int shape) {
        this.shape = shape;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
