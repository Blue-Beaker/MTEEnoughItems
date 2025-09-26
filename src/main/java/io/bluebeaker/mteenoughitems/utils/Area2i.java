package io.bluebeaker.mteenoughitems.utils;

public class Area2i {
    public final int x1;
    public final int x2;
    public final int y1;
    public final int y2;

    public Area2i(int x1, int y1, int w, int h){
        this.x1 = x1;
        this.x2 = x1+w;
        this.y1 = y1;
        this.y2 = y1+h;
    }

    public boolean isInBounds(int x, int y){
        return x>=x1 && y>=y1 && x<x2 && y<y2;
    }
}
