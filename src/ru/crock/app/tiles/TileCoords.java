package ru.crock.app.tiles;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class TileCoords implements Externalizable {
    private double x1, y1,x2,y2;
    private String type;
    private String id;

    public TileCoords(){}

    public TileCoords(double x, double y, double w, double h,String t){
        this.x1 = x;
        this.y1 = y;
        this.x2 = w;
        this.y2 = h;
        this.type = t;
    }

    public String getId(){
        return id;
    }

    public String getType(){
        return type;
    }

    public double getX1(){
        return x1;
    }

    public double getY1(){
        return y1;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    public void relocate(double x, double y){
        double w = x2 - x1;
        double h = y2 - y1;
        this.x1 = x;
        this.y1 = y;
        this.x2 = x1 + w;
        this.y2 = y1 + h;
    }

    public void setNewBounds(double w, double h){
        this.x2 = x1 + w;
        this.y2 = y1 + h;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setType(String t)
    {this.type = t;}

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(x1);
        out.writeDouble(y1);
        out.writeDouble(x2);
        out.writeDouble(y2);
        out.writeObject(type);
        out.writeObject(id);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x1 = in.readDouble();
        y1 = in.readDouble();
        x2 = in.readDouble();
        y2 = in.readDouble();
        type = (String)in.readObject();
        id = (String)in.readObject();
    }
}
