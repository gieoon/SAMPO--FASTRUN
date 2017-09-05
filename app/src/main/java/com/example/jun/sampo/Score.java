package com.example.jun.sampo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;

/**
 * Created by Jun on 21/08/2017.
 */

public class Score extends MenuObject {

//    private final int SPEED_THRESHOLD = 25;
//    private final float SPEED_INCREASE = 0.001f;
//    private final float SPEED_INCREASE_LIMIT = 100;
    private int speed_count = 0;

    public Score(Context context, DisplayMetrics displayMetrics){
        super(context, displayMetrics);
        SAMPO_MAIN.b_gameover = false;
    }

    @Override
    public void setLocation() {
        this.x = displayMetrics.widthPixels / 2;
        this.y = displayMetrics.heightPixels - 50;
    }

    @Override
    public void update() {
//        this.speed_count++;
//        if(SAMPO_MAIN.speed < SPEED_THRESHOLD){
//            if(this.speed_count == SPEED_INCREASE_LIMIT){
//                SAMPO_MAIN.speed += SPEED_INCREASE;
//                this.speed_count = 0;
//            }
//        }

        if(SAMPO_MAIN.score <= 0){
            SAMPO_MAIN.b_gameover = true;
            new Delay(3000, SAMPO_MAIN.GAMESTATE.HIGHSCORE);
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);
        canvas.drawText("" + SAMPO_MAIN.score, this.x, this.y, paint);

        paint.setTextSize(25);
        canvas.drawText("X" + SAMPO_MAIN.combo, 5, this.y, paint);

        if(SAMPO_MAIN.b_gameover){
            paint.setTextSize(80);
            canvas.drawText("GAME OVER", 5, this.displayMetrics.heightPixels / 2, paint);
        }
    }
}
