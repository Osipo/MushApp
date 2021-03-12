package ru.crock.app;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.*;
import javafx.application.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.*;
import ru.crock.app.tiles.*;
import ru.crock.app.utils.NoteGenerator;
import ru.crock.app.utils.serialization.Saver;
import ru.crock.app.utils.serialization.TileEntries;
import ru.crock.app.utils.serialization.TileEntry;

import java.io.File;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class App extends Application {

    private MouseGestures mouseGestures;
    private TileKeyGestures keyGestures;
    private ScrollWrapper wrap;
    private NoteGenerator generator;
    private Pane[] notes;
    private Saver saver;
    private ExecutorService threadPool;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        threadPool = Executors.newWorkStealingPool();
        BorderPane root = new BorderPane();//Border layout for root menu.
        wrap = new ScrollWrapper();//Wrapper for Group with Reader_bar and GridPane with Notes (Panels).
        Group gcpane = new Group();//wrap cpane into Group to make it Controllable. (for transformations)
        GridPane cpane = new GridPane();//Central construction panel.
        ColumnConstraints cols = new ColumnConstraints(400D,400D,Double.MAX_VALUE);
        RowConstraints rows = new RowConstraints(20D);
        cols.setHgrow(Priority.ALWAYS);
        cpane.getColumnConstraints().add(cols);

        notes = new Pane[]{new Pane(), new Pane(), new Pane(), new Pane(), new Pane(), new Pane(), new Pane(), new Pane()
        ,new Pane(), new Pane(), new Pane(), new Pane(), new Pane(), new Pane(), new Pane(), new Pane(), new Pane(), new Pane(),new Pane(),
        new Pane(), new Pane(), new Pane(), new Pane(), new Pane(), new Pane()
        ,new Pane(), new Pane(),new Pane(), new Pane(), new Pane(), new Pane(),
                new Pane(), new Pane(), new Pane(), new Pane(), new Pane(), new Pane(),
                new Pane(), new Pane(), new Pane(), new Pane(), new Pane(), new Pane(),
                new Pane(), new Pane(), new Pane(), new Pane(), new Pane(), new Pane(),
                new Pane(), new Pane(), new Pane(), new Pane(), new Pane(), new Pane(),
                new Pane(), new Pane(), new Pane(), new Pane(), new Pane(), new Pane(),
                new Pane(), new Pane(), new Pane(), new Pane(), new Pane(), new Pane(),
                new Pane(), new Pane(), new Pane(), new Pane(), new Pane(), new Pane()
        };//73 notes.
        for(int i = 0; i < notes.length; i++)
            cpane.getRowConstraints().add(rows);//add row for each note.

        //lowpane > lowpane2 > label.
        GridPane lowpane = new GridPane();//Low panel construction details.
        FlowPane lowpane2 = new FlowPane();
        lowpane2.setOrientation(Orientation.VERTICAL);
        lowpane.add(lowpane2,0,0,1,1);
        lowpane2.setPrefSize(100D,100D);
        Label r2 = new Label("About MIDI: "+"https://stackoverflow.com/questions/16462854/midi-beginner-need-to-play-one-note/16463069#16463069");
        lowpane2.getChildren().addAll(r2);

        primaryStage.setTitle("MushApp");
        Scene scene = new Scene(root, 800,600);
        primaryStage.setScene(scene);
        MenuBar mb = new MenuBar();


        //File
        Menu fm = new Menu("File");
        MenuItem fop = new MenuItem("Open");
        MenuItem fsv = new MenuItem("Save");
        MenuItem fex = new MenuItem("Exit");
        fm.getItems().addAll(fop,fsv,new SeparatorMenuItem(),fex);
        mb.getMenus().add(fm);

        //About
        Menu hm = new Menu("Help");
        MenuItem ab = new MenuItem("About");
        hm.getItems().addAll(ab);
        mb.getMenus().add(hm);

        //Run
        Menu run = new Menu("Run");
        MenuItem rst = new MenuItem("Start");
        MenuItem rps = new MenuItem("Pause");
        MenuItem rstp = new MenuItem("Stop");
        run.getItems().addAll(rst,rps,rstp);
        mb.getMenus().add(run);

        Menu edit = new Menu("Edit");
        MenuItem clear = new MenuItem("ClearAll tiles.");
        edit.getItems().add(clear);
        mb.getMenus().add(edit);

        //Central panel content. Hindi_code below...
        notes[0].setId("C_9_1");
        notes[1].setId("B_8_2");
        notes[2].setId("A#_8_3");
        notes[3].setId("A_8_4");
        notes[4].setId("G#_8_5");
        notes[5].setId("G_8_6");
        notes[6].setId("F#_8_7");
        notes[7].setId("F_8_8");
        notes[8].setId("E_8_9");
        notes[9].setId("D#_8_10");
        notes[10].setId("D_8_11");
        notes[11].setId("C#_8_12");
        notes[12].setId("C_8_13");
        notes[13].setId("B_7_14");
        notes[14].setId("A#_7_15");
        notes[15].setId("A_7_16");
        notes[16].setId("G#_7_17");
        notes[17].setId("G_7_18");
        notes[18].setId("F#_7_19");
        notes[19].setId("F_7_20");
        notes[20].setId("E_7_21");
        notes[21].setId("D#_7_22");
        notes[22].setId("D_7_23");
        notes[23].setId("C#_7_24");
        notes[24].setId("C_7_25");
        notes[25].setId("B_6_26");
        notes[26].setId("A#_6_27");
        notes[27].setId("A_6_28");
        notes[28].setId("G#_6_29");
        notes[29].setId("G_6_30");
        notes[30].setId("F#_6_31");
        notes[31].setId("F_6_32");
        notes[32].setId("E_6_33");
        notes[33].setId("D#_6_34");
        notes[34].setId("D_6_35");
        notes[35].setId("C#_6_36");
        notes[36].setId("C_6_37");
        notes[37].setId("B_5_38");
        notes[38].setId("A#_5_39");
        notes[39].setId("A_5_40");
        notes[40].setId("G#_5_41");
        notes[41].setId("G_5_42");
        notes[42].setId("F#_5_43");
        notes[43].setId("F_5_44");
        notes[44].setId("E_5_45");
        notes[45].setId("D#_5_46");
        notes[46].setId("D_5_47");
        notes[47].setId("C#_5_48");
        notes[48].setId("C_5_49");
        notes[49].setId("B_4_50");
        notes[50].setId("A#_4_51");
        notes[51].setId("A_4_52");
        notes[52].setId("G#_4_53");
        notes[53].setId("G_4_54");
        notes[54].setId("F#_4_55");
        notes[55].setId("F_4_56");
        notes[56].setId("E_4_57");
        notes[57].setId("D#_4_58");
        notes[58].setId("D_4_59");
        notes[59].setId("C#_4_60");
        notes[60].setId("C_4_61");
        notes[61].setId("B_3_62");
        notes[62].setId("A#_3_63");
        notes[63].setId("A_3_64");
        notes[64].setId("G#_3_65");
        notes[65].setId("G_3_66");
        notes[66].setId("F#_3_67");
        notes[67].setId("F_3_68");
        notes[68].setId("E_3_69");
        notes[69].setId("D#_3_70");
        notes[70].setId("D_3_71");
        notes[71].setId("C#_3_72");
        notes[72].setId("C_3_73");
        initNotePanel(notes,cpane);
        colorNotes(notes);

        //Red line which follows the track.
        Pane track = new Pane();
        Rectangle trackr = new Rectangle(3D,20D * notes.length);
        trackr.setFill(Color.RED);
        DropShadow sh = new DropShadow();
        sh.setColor(Color.SILVER);
        track.setEffect(sh);
        track.getChildren().add(trackr);

        //Left-side from central-panel.
        FlowPane labels = new FlowPane();
        labels.setOrientation(Orientation.VERTICAL);
        labels.setPadding(new Insets(0,0,0,50));
        labels.setVgap(3);
        for(int j = 0; j < notes.length;j++){
            String[] w = notes[j].getId().split("_+");
            Label l = new Label(w[0]+w[1]);
            l.setMaxHeight(10);
            l.setTextAlignment(TextAlignment.RIGHT);
            l.setTextFill(Color.BLACK);
            labels.getChildren().add(l);
        }
        generator = new NoteGenerator();
        mouseGestures = new MouseGestures(generator);
        keyGestures = new TileKeyGestures(mouseGestures);
        saver = new Saver();

        cpane.setTranslateX(100);
        track.setTranslateX(100);
        labels.setTranslateX(0);
        labels.setPrefHeight(20D * notes.length);
        gcpane.getChildren().addAll(cpane,track,labels);
        wrap.setContent(gcpane);
        wrap.setPrefViewportHeight(200D);
        wrap.setPrefViewportWidth(400D);
        wrap.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        wrap.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        wrap.fitToHeightProperty().set(true);

        wrap.setPannable(false);
        root.setTop(mb);
        root.setCenter(wrap);
        root.setBottom(lowpane);

        //Animation of tracking bar.
        AtomicLong st1 = new AtomicLong(0);
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;
            private int counter = 0;
            @Override
            public void handle(long now) {

                if(now - lastUpdate >= 16_000_000) {//best value: 16_000_000
                    lastUpdate = now;
                    try {
                        initSound(cpane.getChildren(),track.getTranslateX());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    track.setTranslateX(track.getTranslateX() + 1D);
                    counter++;
                    if(counter == 50){
                        long s = System.currentTimeMillis() - st1.get();
                        st1.set(System.currentTimeMillis());
                        counter = 0;
                    }
                    if (track.getTranslateX() >= notes[0].getWidth() + 100D) {
                        track.setTranslateX(100);
                        this.stop();
                        st1.set(0);
                        rst.setDisable(false);
                        rps.setDisable(true);
                        rstp.setDisable(true);
                    }
                }
            }
        };

        //add handlers to menu
        fex.setOnAction(e ->{primaryStage.close(); System.exit(0);});
        fop.setOnAction(e ->{
            FileChooser fileChooser = new FileChooser();
            File f =  fileChooser.showOpenDialog(primaryStage);
            if (f != null) {
                System.out.println(f.getAbsolutePath());
                loadData(notes,f.getAbsolutePath());
            }
        });
        fsv.setOnAction(e ->{
            FileChooser fileChooser = new FileChooser();
            File f = fileChooser.showSaveDialog(primaryStage);
            if(f != null){
                System.out.println(f.getAbsolutePath());
                saveData(notes,f.getAbsolutePath());
            }
        });

        rst.setOnAction(e ->{
            st1.set(System.currentTimeMillis());
            rst.setDisable(true);
            rps.setDisable(false);
            rstp.setDisable(false);
            timer.start();
        });
        rstp.setOnAction(e ->{
            timer.stop();
            track.setTranslateX(100);
            threadPool.shutdownNow();
            threadPool = Executors.newWorkStealingPool();
            rst.setDisable(false);
            rps.setDisable(true);
            rstp.setDisable(true);
        });
        rps.setOnAction(e ->{timer.stop();rps.setDisable(true);rst.setDisable(false);});
        rps.setDisable(true);
        rstp.setDisable(true);

        clear.setOnAction(e ->{for(Pane p : notes)p.getChildren().removeAll(p.getChildren());
        mouseGestures.setTiles(null);mouseGestures.getActiveTile().setActive(false);mouseGestures.setActiveTile(null);
        keyGestures.setCoords(null);List<TileCoords> li = new ArrayList<>(100);mouseGestures.setTiles(li);keyGestures.setCoords(li);});

        primaryStage.show();
    }

    //Clicking on Note Panels.
    private void CanvasClickHandler(MouseEvent event){
        double x = event.getX();
        double y = event.getY();
        Pane p = ((Pane) event.getSource());
        String n = p.getId();
        List<TileCoords> tiles = mouseGestures.getTiles();
        if(isInBound(x,y,tiles,n))
            return;
        Tile t = new Tile();
        t.setX(x - 15D);
        t.setY(p.getTranslateY());
        t.setActive(true);
        t.setWidth(50D);
        t.setHeight(p.getHeight());
        t.setFill(Color.BLUE);
        t.setId(generateId());
        t.setDuration(TileDuration.QUART);
        t.setNote(n);
        keyGestures.setCoords(tiles);
        if(mouseGestures.getActiveTile() != null && !mouseGestures.getActiveTile().equals(t)){
            mouseGestures.getActiveTile().setActive(false);
            mouseGestures.getActiveTile().setFill(Color.GREENYELLOW);
            mouseGestures.setActiveTile(t);
        }
        else if(mouseGestures.getActiveTile() == null){
            mouseGestures.setActiveTile(t);
        }
        mouseGestures.makeDraggable(t);
        t.setPosition(new TileCoords(t.getX(), t.getY(),t.getX() + 50D, t.getY() +  t.getHeight(),n));
        mouseGestures.addCoords(t.getPosition());
        t.addEventFilter(KeyEvent.KEY_PRESSED,keyGestures.keyPressed);
        t.addEventFilter(KeyEvent.KEY_RELEASED,keyGestures.released);

        if(t.getX() + t.getWidth() >= p.getWidth()){
           p.setPrefWidth(p.getWidth() + t.getWidth() * 3D);
        }
        p.getChildren().add(t);
        t.setActive(true);//request focus after addition.
        //show music content of the tile.
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                generator.playSong(p.getId(),(long)(1000 * (t.getDuration().getV()/50D)));
            }
        };
        (new Thread(runnable)).start();

    }

    //FIX DURATION.
    private void initSound(ObservableList<Node> notes, double currentX) throws InterruptedException {
        for(Node n: notes){
            if(n instanceof Pane) {
                Pane np = (Pane) n;
                List<Tile> ntiles = np.getChildren().stream().map(x -> (Tile)x).collect(Collectors.toList());
                ntiles.removeIf(x -> x.getX() + 100D > currentX || x.getX() + 100D < currentX);
                if(ntiles.size() > 0){
                    /* make sound at new thread */
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            //generator.playNote(ntiles.get(0).getNote(),ntiles.get(0).getDuration());
                            generator.playSong(ntiles.get(0).getNote(),(long)(1000 * (ntiles.get(0).getDuration().getV()/50D)));
                        }
                    };
                    //generator.playNote(ntiles.get(0).getNote(),ntiles.get(0).getDuration());
                    threadPool.submit(runnable);
                    //(new Thread(runnable)).start();
//                    Runnable runnable = new Runnable() {
//                        @Override
//                        public void run() {
//                            generator.playNote(np.getId(),ntiles.get(0).getDuration());
//                        }
//                    };
//                    (new Thread(runnable)).start();
                }
            }
        }
    }


    private String generateId(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }



    public static boolean isInBound(double x, double y, List<TileCoords> tiles, String n){//n define panel.
        if(tiles == null || tiles.size() == 0)
            return false;
        for(TileCoords c : tiles){//x,y are relative to panel. if x and y are equal -> check the panels.
            if((x >= c.getX1() && x <= c.getX2()) && (y >= c.getY1() && y <= c.getY2()) && c.getType().equals(n))
                return true;
        }
        return false;
    }


    private void initNotePanel(Pane[] notes, GridPane cpane){
        int r = 0;
        for(Pane n: notes){
            cpane.add(n,0,r);
            n.prefWidthProperty().bindBidirectional(cpane.prefWidthProperty());
            n.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            n.setOnMouseClicked(this::CanvasClickHandler);
            r++;
        }
    }

    private void colorNotes(Pane[] notes){
        BackgroundFill BF1 = new BackgroundFill(Color.WHITE, new CornerRadii(1),
                new Insets(0.0,0.0,0.0,0.0));
        BackgroundFill BF2 = new BackgroundFill(Color.BLACK, new CornerRadii(1),
                new Insets(0.0,0.0,0.0,0.0));
        notes[0].setBackground(new Background(BF1));
        notes[1].setBackground(new Background(BF1));
        Predicate<Integer> c1 = (x) -> x % 2 == 0;
        Predicate<Integer> c2 = (x) -> x % 2 != 0;
        Predicate<Integer> cur = c1;
        for(int i = 2; i < notes.length;i++){
           if(cur.test(i))
                notes[i].setBackground(new Background(BF2));
           else
                notes[i].setBackground(new Background(BF1));
           if(notes[i].getId().split("_+")[0].equals("F"))
               cur = c2;
           else if(notes[i].getId().split("_+")[0].equals("C"))
               cur = c1;
        }
    }

    private void saveData(Pane[] notes,String file){
        LinkedList<TileEntry> items = new LinkedList<>();
        TileEntries saveable = new TileEntries();
        for(Pane n: notes){
            ObservableList<Node> ntiles = n.getChildren();
            for(Node node: ntiles){
                if(node instanceof Tile){
                    Tile t = (Tile)node;
                    TileEntry entry = new TileEntry();
                    entry.setActive(t.isActive());
                    entry.setNote(t.getNote());
                    entry.setId(t.getId());
                    entry.setDuration(t.getDuration().ordinal());
                    entry.setPosition(t.getPosition());
                    entry.setX(t.getX());
                    entry.setY(t.getY());
                    entry.setW(t.getWidth());
                    entry.setH(t.getHeight());
                    items.add(entry);
                }
            }
        }
        saveable.setTiles(items);
        saver.save(file,saveable);
    }

    private void loadData(Pane[] panes,String file){
        List<TileEntry> tiles = saver.load(file).getTiles();
        if(tiles != null) {
            if(mouseGestures.getActiveTile() != null)
                mouseGestures.getActiveTile().setActive(false);
            for (Pane p : panes) {
                p.getChildren().removeAll(p.getChildren());
                p.setPrefWidth(400D);
            }
            mouseGestures.setActiveTile(null);
            mouseGestures.setTiles(null);
            keyGestures.setCoords(null);
        }
        assert tiles != null;
        List<TileCoords> ncoords = new LinkedList<>();
        Pane parent = null;
        for(TileEntry entry : tiles){
            Tile t = new Tile();
            t.setDuration(TileDuration.values()[entry.getDuration()]);
            t.setId(entry.getId());
            t.setNote(entry.getNote());
            Optional<Pane> p = Arrays.stream(panes).filter(x -> x.getId().equals(t.getNote())).findFirst();
            if(p.isPresent()){
                parent = p.get();
                parent.getChildren().add(t);
            }
            t.setPosition(entry.getPosition());
            t.setX(entry.getX());
            t.setY(entry.getY());
            t.setWidth(entry.getW());
            t.setHeight(entry.getH());
            t.setActive(entry.isActive());
            assert parent != null;
            if(t.getX() + t.getWidth() >= parent.getWidth()){
                parent.setPrefWidth(parent.getWidth() + t.getWidth() * 3D);
            }
            t.setFill(Color.BLUE);
            if(t.isActive() && mouseGestures.getActiveTile() == null){
                mouseGestures.setActiveTile(t);
            }
            else if(!t.isActive())
                t.setFill(Color.GREENYELLOW);
            ncoords.add(t.getPosition());
            mouseGestures.makeDraggable(t);
            t.addEventFilter(KeyEvent.KEY_PRESSED,keyGestures.keyPressed);
            t.addEventFilter(KeyEvent.KEY_RELEASED,keyGestures.released);
        }
        mouseGestures.setTiles(ncoords);
        keyGestures.setCoords(ncoords);
    }
}