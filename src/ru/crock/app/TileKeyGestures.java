package ru.crock.app;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import ru.crock.app.tiles.Tile;
import ru.crock.app.tiles.TileCoords;
import ru.crock.app.tiles.TileDuration;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class TileKeyGestures  {

    private List<TileCoords> coords;
    private TileDuration[] durations;
    private int cp;
    private MouseGestures mouse;
    public TileKeyGestures(MouseGestures gestures){
        durations = new TileDuration[6];
         Arrays.stream(TileDuration.values()).sorted((x,y) ->{
             return Double.compare(x.getV(), y.getV());
         }).collect(Collectors.toList()).toArray(durations);
         cp = 0;
         this.mouse = gestures;
    }

    public void setCoords(List<TileCoords> coords){
        this.coords = coords;
    }

    public EventHandler<KeyEvent> keyPressed = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            //System.out.println(event.getSource());
            if(event.getSource() instanceof Tile) {
                Tile t = ((Tile) event.getSource());
                cp = 0;
                for(TileDuration d: durations){
                    if(d.getV() == t.getDuration().getV())
                        break;
                    cp++;
                }
                //System.out.println("Tile witdh: "+t.getWidth());
                //System.out.println("Tile activity: "+t.isActive());
                //System.out.println("Current duration: "+cp);
                if (event.getCode() == KeyCode.LEFT && t.isActive() && cp > 0) {
                    cp--;
                    t.setDuration(durations[cp]);
                } else if (event.getCode() == KeyCode.RIGHT && t.isActive() && cp < 5) {
                    cp++;
                    t.setDuration(durations[cp]);
                } else if (event.getCode() == KeyCode.DELETE && t.isActive()) {
                    t.setActive(false);
                    mouse.setActiveTile(null);
                    coords.removeAll(coords.stream().filter(x -> x.getId().equals(t.getId())).collect(Collectors.toList()));
                    Pane parent = ((Pane) t.getParent());
                    parent.getChildren().removeAll(t);
                }
            }
        }
    };
    public EventHandler<KeyEvent> released = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getSource() instanceof Tile) {
                Tile t = ((Tile) event.getSource());
                if(t.isActive()) {
                    Iterator<TileCoords> i = coords.iterator();
                    int idx = -1;
                    TileCoords c = null;
                    while (i.hasNext()) {
                        idx += 1;
                        c = i.next();
                        if (c.getId().equals(t.getId())) {
                            c.setNewBounds(t.getWidth(), t.getHeight());
                            break;
                        }
                    }
                    coords.set(idx, c);
                }
            }
        }
    };
}
