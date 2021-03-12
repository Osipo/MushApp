package ru.crock.app.tiles;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Tile extends Rectangle implements Serializable {
    private boolean isActive;
    private String note;
    private TileCoords position;
    private TileDuration duration = TileDuration.QUART;

    public void setActive(boolean f){
        this.isActive = f;
        this.setFocused(f);
        this.setFocusTraversable(f);
        this.requestFocus();
    }

    @Override
    public void requestFocus(){
        if(isActive)
            super.requestFocus();
    }

    public void setNote(String t){
        this.note = t;
    }

    public void setPosition(TileCoords coords){
        this.position = coords;
        this.position.setId(this.getId());
        this.position.setType(this.getNote());
    }

    public TileCoords getPosition(){
        return position;
    }

    public String getNote(){
        return note;
    }

    public boolean isActive(){
        return isActive;
    }

    public void setDuration(TileDuration duration) {
        this.duration = duration;
        this.setWidth(duration.getV());
    }

    public TileDuration getDuration(){
        return duration;
    }
}
