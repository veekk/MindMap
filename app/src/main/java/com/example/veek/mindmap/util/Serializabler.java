package com.example.veek.mindmap.util;

import android.content.Context;
import android.util.Base64;

import com.example.veek.mindmap.model.MindMapModel;

import java.io.File;
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
        String filenameReal = String.valueOf(model.getId()) + "_" + model.getName();
        String filenameCoded = Base64.encodeToString(filenameReal.getBytes(), Base64.URL_SAFE);
        FileOutputStream fos = context.openFileOutput(filenameCoded, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(model);
        oos.flush();
        oos.close();
    }

    public static MindMapModel loadObject(long id, Context context)  throws IOException, ClassNotFoundException{
        String mapName = AccountManager.getInstance().getCurrentAccount().getMapNameById(id);
        String filenameReal = String.valueOf(id) + "_" + mapName;
        String filename = Base64.encodeToString(filenameReal.getBytes(), Base64.URL_SAFE);
        FileInputStream fis = context.openFileInput(filename);
        ObjectInputStream oin = new ObjectInputStream(fis);
        return (MindMapModel) oin.readObject();
    }

    public static void removeObject(long id, Context context){
        String filenameReal = String.valueOf(id) + "_" + AccountManager.getInstance().getCurrentAccount().getMapNameById(id);
        String filenameCoded = Base64.encodeToString(filenameReal.getBytes(), Base64.URL_SAFE);
        context.getFileStreamPath(filenameCoded).delete();
    }
}
