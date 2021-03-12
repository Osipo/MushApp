package ru.crock.app.utils.serialization;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.LinkedList;

public class TileEntries implements Externalizable {
    LinkedList<TileEntry> tiles;
    public TileEntries(){}

    public void setTiles(LinkedList<TileEntry> tiles) {
        this.tiles = tiles;
    }

    public LinkedList<TileEntry> getTiles() {
        return tiles;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(tiles.size());
        for(TileEntry t: tiles){
            out.writeObject(t);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int size = in.readInt();
        tiles = new LinkedList<>();
        int c = 0;
        while(c < size){
            TileEntry item = (TileEntry)in.readObject();
            tiles.add(item);
            c++;
        }
    }
}