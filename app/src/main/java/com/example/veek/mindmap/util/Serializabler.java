package com.example.veek.mindmap.util;

import android.content.Context;

import com.example.veek.mindmap.model.MindMapModel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by veek on 01.12.15.
 */
public class Serializabler {
    public static void saveObject(MindMapModel model, Context context) throws IOException{
        String filename = "map_"+model.getId()+".vmm";
        FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(model);
        oos.flush();
        oos.close();
    }

    public static MindMapModel loadObject(long id, Context context)  throws IOException, ClassNotFoundException{
        String filename = "map_"+id+".vmm";
        FileInputStream fis = context.openFileInput(filename);
        ObjectInputStream oin = new ObjectInputStream(fis);
        return (MindMapModel) oin.readObject();
    }
}
