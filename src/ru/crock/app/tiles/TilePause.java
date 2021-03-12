package ru.crock.app.tiles;

public enum TilePause {
    NONE(0,0),TINY(1/32D,6.25),HEX(1/16D,12.5),OCTA(1/8D,25),QUART(1/4D,50),HALF(1/2D,100),ONE(1.0,200);
    private double p;
    private double offset;

    TilePause(double pause, double offset){
        this.p = pause;
        this.offset = offset;
    }

    public double getOffset() {
        return offset;
    }

    public double getP() {
        return p;
    }
}
