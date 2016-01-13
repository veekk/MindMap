package com.example.veek.mindmap.model;

import android.graphics.Color;
import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by veek on 30.11.15.
 */
public class MindMapModel implements Serializable{
    ArrayList<MindMapElement> elements = new ArrayList<>();
    ArrayList<Pair> relations = new ArrayList<>();
    long id;
    String name;
    int backgroundColor;



    public MindMapModel(String name, long id) {
        this.id = id;
        this.name = name;
        this.backgroundColor = Color.WHITE;
    }

    public void addRelation (long id1, long id2){
        relations.add(new Pair(id1, id2));
    }

    public  void removeRelation (long id1){
        relations.remove(relations.contains(id1));
    }

    public ArrayList<Pair> getRelations() {
        return relations;
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

    public void delElement(long id){
        elements.remove(getElementById(id));
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

    public class Pair implements Serializable{
        long first;
        long second;

        public Pair(long first, long
                     second){
            this.first = first;
            this.second = second;
        }

        public long getFirst() {
            return first;
        }

        public long getSecond() {
            return second;
        }
    }
}

