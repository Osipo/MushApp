package ru.crock.app.tiles;

public enum TileDuration {
    TINY(6.25,1),HEX(12.5,2),OCTA(25,4),QUART(50,8),HALF(100,16),ONE(200,32);

    private double v;
    private int ticks;
    TileDuration(double v,int t){
         this.v = v;
         this.ticks = t;
    }

    public double getV() {
        return v;
    }

    public int getTicks() {
        return ticks;
    }
}
