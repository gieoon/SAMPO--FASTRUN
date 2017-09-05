package com.example.jun.sampo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;

import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by Jun on 20/08/2017.
 * Manages the colours in the background.
 * processes the new colour
 */

public class BackgroundManager {
    private Random rand;
    private Shape.TYPE currentType;
    private ColorWheel colorWheel;
    private static Context context;
    //public static Bitmap shapeBitmap;
    public static boolean b_shape;

    //initialize on a random colour
    public BackgroundManager(SAMPO_LOOP sampo_loop, ColorWheel colorWheel){
        this.colorWheel = colorWheel;
        this.context = sampo_loop.getContext();
        this.rand = new Random();
        int rand_index = rand.nextInt(Shape.SIZE);
        Log.w(TAG, "Shape.SIZE: " + Shape.SIZE);
        processEnumIndex(rand_index);
        this.colorWheel.setCurrentType(this.getCurrentType());
        b_shape = false;
    }

    public void moveUp(){
        int temp_index = getCurrentEnumIndex();
        temp_index--;
        if(temp_index < 0){
            temp_index = Shape.SIZE - 1;
        }
        processEnumIndex(temp_index);
    }

    public void moveDown(){
        int temp_index = getCurrentEnumIndex();
        temp_index++;
        if(temp_index == Shape.SIZE){
            temp_index = 0;
        }
        processEnumIndex(temp_index);
    }

    private int getCurrentEnumIndex(){
        //get index of current enum name.
        return Shape.TYPE.valueOf(currentType.name()).ordinal();
    }

    private void processEnumIndex(int index){
        Log.w(TAG, "INdex: " + index);
        this.currentType = Shape.colorTypes.get(index);
        //this.colorWheel.setBackgroundColor(this.currentType);
    }

    public static final int getBackgroundColor(Shape.TYPE type){
        //shapeBitmap = null;
        //if exists, will be set to something else other than null!
        //shapeBitmap = getCurrentShape(type);

        switch(type){
            case GREEN : {
                return Color.GREEN;
            }
            case BLUE : {
                return Color.BLUE;
            }
            case YELLOW : {
                return Color.YELLOW;
            }
            case RED : {
                return Color.RED;
            }
            //shapes are all a gray colour in the background
            default : {
                return Color.GRAY;
            }
        }
    }

    public void setBackgroundColor(Shape.TYPE type){
        this.currentType = type;
    }
    public final Shape.TYPE getCurrentType(){
        return this.currentType;
    }//by using this, it makes it pass by value rather than by reference...
}
