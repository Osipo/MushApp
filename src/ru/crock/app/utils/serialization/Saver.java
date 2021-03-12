package ru.crock.app.utils.serialization;

import java.io.*;

public class Saver {
    public void save(String fileName, TileEntries obj){
        try {
            //File file = new File(fileName);
            FileOutputStream f = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(f);
            out.writeObject(obj);
            out.close();
        } catch (IOException e){
            System.out.println("File not found or serialization error.");
            e.printStackTrace();
        }
    }

    public TileEntries load(String fileName){
        try {
            FileInputStream f = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(f);
            TileEntries entries = (TileEntries)in.readObject();
            in.close();
            return entries;
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
