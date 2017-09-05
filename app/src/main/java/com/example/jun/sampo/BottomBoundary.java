package com.example.jun.sampo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;

import java.util.Random;

/**
 * Created by Jun on 21/08/2017.
 * Bottom boundary that keeps changing colour slowly based on the points it gained.
 */

public class BottomBoundary extends MenuObject {

    private int y;
    private int color;
    private Random rand = new Random();
    public BottomBoundary(Context context, DisplayMetrics displayMetrics){
        super(context, displayMetrics);
        this.y = TileGrid.BOTTOM_BOUNDARY;
        this.color = Color.BLACK;//set to a random colour? or no need?
    }

    @Override
    public void setLocation() {

    }

//    public BottomBoundary initBottomBoundary(Context context){
//        this.context = context;
//        return new BottomBoundary();
//    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
//        paint.setColor(Color.WHITE);
//        canvas.drawLine(0, this.y, this.displayMetrics.widthPixels, this.y, paint);
//        canvas.drawLine(0, TileGrid.TOP_BOUNDARY, this.displayMetrics.widthPixels, TileGrid.TOP_BOUNDARY, paint);
    }
}
