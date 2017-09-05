package com.example.jun.sampo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.DisplayMetrics;

import java.util.ArrayList;

/**
 * Created by Jun on 21/08/2017.
 * abstract class containing the menu objects that should be upated and drawn.
 */

public abstract class MenuObject {

    protected static ArrayList<MenuObject> menuObjects = new ArrayList<>();
    protected float x, y;
    protected Bitmap bitmap;
    protected Context context;
    protected DisplayMetrics displayMetrics;

    public MenuObject(Context context, DisplayMetrics displayMetrics){
        this.context = context;
        this.displayMetrics = displayMetrics;
        this.menuObjects.add(this);
        setLocation();
    }

    //used by delay class
    public MenuObject(){
        this.menuObjects.add(this);
    }

    public abstract void setLocation();
    public abstract void update();
    public abstract void draw(Canvas canvas, Paint paint);

    //complete copy of the shape class...so bad...
    protected Bitmap getResizedBitmap(Bitmap bitMap, int newWidth, int newHeight){
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        //create a matrix for the manipulation
        Matrix matrix = new Matrix();
        //resize the bitmap
        matrix.postScale(scaleWidth, scaleHeight);

        //recreate the new bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, false);
        bitMap.recycle();
        return resizedBitmap;
    }
}
