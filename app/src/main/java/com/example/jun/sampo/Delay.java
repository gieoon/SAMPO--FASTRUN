package com.example.jun.sampo;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;

/**
 * Created by Jun on 22/08/2017.
 * static class to create generic delays
 */

public class Delay extends MenuObject{

    private int delayAmount;
    private long startTime;
    private SAMPO_MAIN.GAMESTATE finalState;

    public Delay(int delayAmount, SAMPO_MAIN.GAMESTATE gamestate){
        super();
        this.finalState = gamestate;
        this.delayAmount = delayAmount;
        this.startTime = SystemClock.elapsedRealtime();
    }

    @Override
    public void setLocation() {
        //NO ACTION
    }

    @Override
    public void update(){
        long difference = SystemClock.elapsedRealtime() - this.startTime;
        if (difference >= this.delayAmount){
            SAMPO_MAIN.gamestate = finalState;
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        //NO ACTION
    }

}
