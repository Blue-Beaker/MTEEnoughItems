package io.bluebeaker.mteenoughitems.utils;

public class LogTimer {
    public final long firstTime;
    private long lastTime;
    public LogTimer(){
        firstTime=System.currentTimeMillis();
        lastTime=firstTime;
    }
    public long stagedTime(){
        long t = System.currentTimeMillis();
        long delta = t -lastTime;
        lastTime= t;
        return delta;
    }
}
