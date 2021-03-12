package ru.crock.app.utils.serialization;

import ru.crock.app.tiles.TileCoords;
import ru.crock.app.tiles.TileDuration;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class TileEntry implements Externalizable {
    private boolean isActive;
    private String note;
    private String id;
    private TileCoords position;
    private int duration;
    private double x,y,w,h;

    private static final long serialVersionUID = 1L;
    public TileEntry(){
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPosition(TileCoords position) {
        this.position = position;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setW(double w) {
        this.w = w;
    }

    public void setH(double h) {
        this.h = h;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getNote() {
        return note;
    }

    public TileCoords getPosition() {
        return position;
    }

    public String getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getH() {
        return h;
    }

    public double getW() {
        return w;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeBoolean(isActive);
        out.writeObject(note);
        out.writeObject(id);
        out.writeObject(position);
        out.writeInt(duration);
        out.writeDouble(x);
        out.writeDouble(y);
        out.writeDouble(w);
        out.writeDouble(h);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        isActive = in.readBoolean();
        note = (String)in.readObject();
        id = (String)in.readObject();
        position = (TileCoords)in.readObject();
        duration = in.readInt();
        x = in.readDouble();
        y = in.readDouble();
        w = in.readDouble();
        h = in.readDouble();
    }
}
