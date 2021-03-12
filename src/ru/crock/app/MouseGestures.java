package ru.crock.app;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ru.crock.app.utils.NoteGenerator;
import ru.crock.app.tiles.Tile;
import ru.crock.app.tiles.TileCoords;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MouseGestures {
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    private NoteGenerator generator;
    private Tile activeTile;
    private List<TileCoords> tiles = new ArrayList<>(100);


    public MouseGestures(NoteGenerator gen){
        this.generator = gen;
    }

    public void addCoords(TileCoords c){
        tiles.add(c);
    }

    public List<TileCoords> getTiles(){
        return tiles;
    }

    public void setTiles(List<TileCoords> tiles) {
        this.tiles = tiles;
    }

    public List<TileCoords> getCoords(){
        return tiles;
    }

    public void makeDraggable(Node node) {
        node.setOnMousePressed(rectOnMousePressedEventHandler);
        node.setOnMouseDragged(rectOnMouseDraggedEventHandler);
        node.setOnMouseReleased(released);
    }

    public void setActiveTile(Tile t){
        this.activeTile = t;
    }

    public Tile getActiveTile(){
        return activeTile;
    }

    EventHandler<MouseEvent> rectOnMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            if (t.getSource() instanceof Rectangle) {
                Rectangle p = ((Rectangle) (t.getSource()));
                orgTranslateX = p.getX();
                orgTranslateY = p.getY();
                if(t.getSource() instanceof Tile){
                    Tile tile = ((Tile) (t.getSource()));
                    Pane parent = ((Pane) tile.getParent());
                    tile.setActive(true);
                    tile.setFocusTraversable(true);
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            generator.playSong(parent.getId(),(long)(1000 * (tile.getDuration().getV()/50D)));
                        }
                    };
                    (new Thread(runnable)).start();

                    if(activeTile != null && !activeTile.equals(tile)) {
                        activeTile.setActive(false);
                        activeTile.setFocusTraversable(false);
                        activeTile.setFill(Color.GREENYELLOW);
                    }
                    tile.setFill(Color.BLUE);
                    activeTile = tile;
                }
            } else {
                Node p = ((Node) (t.getSource()));
                orgTranslateX = p.getTranslateX();
                orgTranslateY = p.getTranslateY();
            }
        }
    };

    EventHandler<MouseEvent> rectOnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;
            if (t.getSource() instanceof Tile) {
                Tile tile = ((Tile) (t.getSource()));
                GridPane grandp = ((GridPane)tile.getParent().getParent());
                Group ggparent = ((Group) grandp.getParent());
                if(newTranslateX <= 0d)
                    newTranslateX = 0d;

                tile.setX(newTranslateX);
                Pane p = ((Pane) tile.getParent());

                if(tile.getX() + tile.getWidth() >= p.getWidth()){
                    p.setPrefWidth(p.getWidth() + tile.getWidth() * 3D);
                }

                tile.setActive(true);
                tile.setFocusTraversable(true);
                Iterator<TileCoords> l = tiles.iterator();
                TileCoords i = null;
                int idx = -1;
                while(l.hasNext()){
                    idx += 1;
                    i = l.next();
                    if(i.getId().equals(tile.getId())){
                        i.relocate(tile.getX(),tile.getY());
                        break;
                    }
                }
                tiles.set(idx,i);
                if(activeTile != null && !activeTile.equals(tile)) {
                    activeTile.setActive(false);
                    activeTile.setFocusTraversable(false);
                    activeTile.setFill(Color.GREENYELLOW);
                }
                activeTile = tile;
                checkNeighbours(grandp.getChildren(),tile.getX(),ggparent,p.getId());
            } else {
                Node p = ((Node) (t.getSource()));
                p.setTranslateX(newTranslateX);
                p.setTranslateY(newTranslateY);
            }
        }
    };

    private void checkNeighbours(ObservableList<Node> notes, double currentX, Group parent, String id) {
        Pane p = new Pane();
        boolean nfound = true;
        p.setId("Border");
        int a = Integer.parseInt(id.split("_+")[2]);
        for (Node n : notes) {
            if (n instanceof Pane && !n.getId().equals(id)) {
                Pane np = (Pane) n;
                List<Tile> ntiles = np.getChildren().stream().map(x -> (Tile) x).collect(Collectors.toList());
                ntiles.removeIf(x -> x.getX() > currentX || x.getX() < currentX);
                if (ntiles.size() > 0 && nfound) {
                    //System.out.println("Has neighbours");
                    nfound = false;
                    int b = Integer.parseInt(np.getId().split("_+")[2]);
                    int m = Math.abs(a - b) + 1;
                    Rectangle gray = new Rectangle(3, 20D * m);
                    gray.setFill(Color.GRAY);
                    p.getChildren().add(gray);
                    parent.getChildren().add(p);
                    p.setTranslateX(currentX + 100D);
                    if(a > b){//neighbour from top.
                        p.setTranslateY(20D * (b - 1));
                    }
                    else{//neighbour from bottom.
                        p.setTranslateY(20D * (a - 1));
                    }
                }
            }
        }
        if(nfound){
            Node del = parent.getChildren().get(parent.getChildren().size() - 1);
            if(del.getId() != null && del.getId().equals("Border"))
                parent.getChildren().remove(parent.getChildren().size() - 1);
        }
    }


    EventHandler<MouseEvent> released = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.getSource() instanceof Tile) {
                Tile tile = ((Tile) (event.getSource()));
                GridPane grandp = ((GridPane) tile.getParent().getParent());
                Group ggparent = ((Group) grandp.getParent());
                Node d = ggparent.getChildren().get(ggparent.getChildren().size() - 1);
                //If there is a line -> delete it.
                if(d instanceof Pane && d.getId() != null && d.getId().equals("Border")){
                    ggparent.getChildren().remove(ggparent.getChildren().size() - 1);
                }
            }
        }
    };
}