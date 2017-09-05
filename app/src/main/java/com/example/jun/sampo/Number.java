package com.example.jun.sampo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by Jun on 21/08/2017.
 */

public class Number {
    private Random rand = new Random();
    private int value;
    private Shape shape;

    public Number(Shape shape){
        this.shape = shape;
        this.value = rand.nextInt(12) - 6; //random integer from -3 to 3
    }

    public void addToScore(){

        SAMPO_MAIN.score += this.value * SAMPO_MAIN.combo;
    }

    public void draw(Canvas canvas, Paint paint){
        paint.setColor(Color.GRAY);
        paint.setTextSize(32);
        float shapeMiddleX = this.shape.getX() + this.shape.getWidth() / 2 - 15;
        float shapeMiddleY = this.shape.getY() + this.shape.getHeight() / 2 + 15;
        if(this.value < 1){
            canvas.drawText("Hi Shah!!!", shapeMiddleX, shapeMiddleY, paint);
        }
        else {
            canvas.drawText("" + this.value, shapeMiddleX, shapeMiddleY, paint);
        }
    }

    public final int getValue(){
        return this.value;
    }
}
