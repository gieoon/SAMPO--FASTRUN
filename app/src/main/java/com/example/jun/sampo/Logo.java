package com.example.jun.sampo;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;

/**
 * Created by Jun on 22/08/2017.
 * Displays logo during gameplay
 */

public class Logo extends MenuObject{


    public Logo(Context context, DisplayMetrics displayMetrics){
        super(context,displayMetrics);
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sampo_logo);
        this.bitmap = getResizedBitmap(this.bitmap, this.bitmap.getWidth() / 4, this.bitmap.getHeight() / 4);
    }

    @Override
    public void setLocation(){
        this.x = 5;
        this.y = 5;
    }

    @Override
    public void update(){

    }

    @Override
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(this.bitmap, this.x, this.y, paint);
    }
}
