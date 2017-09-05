package com.example.jun.sampo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Jun on 1/08/2017.
 */

public abstract class Shape {
    //BitMap to get character from image
    protected Bitmap bitmap;
    protected Context context;

    protected int scaled_width, scaled_height, location;
    protected float x, y;
    protected boolean b_movable, b_remove, b_updated_score, b_drawGlow;
    protected Number number;
    protected Glow glow;
    protected enum TYPE {
        GREEN,
        BLUE,
        YELLOW,
        RED,
        //added new values in here.
        //TRIANGLE,
        //CIRCLE,
        //SQUARE
    }
    protected TYPE type;
    protected enum SHAPE {
        TRIANGLE,
        CIRCLE,
        SQUARE,
        NONE
    }

    protected SHAPE shapeType;

    protected static final Random rand = new Random();
    public static final List<TYPE> colorTypes = Collections.unmodifiableList(Arrays.asList(TYPE.values()));
    public static final int SIZE = colorTypes.size();
    public static final List<SHAPE> shapeTypes = Collections.unmodifiableList(Arrays.asList(SHAPE.values()));
    public static final int SHAPE_TYPE_SIZE = shapeTypes.size();

    protected final int SIZE_DEDUCTION = 2;//number to divide all shapes by
    protected final int SPAWN_Y = -45;

    public Shape(Context context, int location){
        this.context = context;
        this.location = location;
        this.b_updated_score = false;
        Log.i("SHAPE","x: " + this.x);
        this.y = SPAWN_Y;
        this.b_movable = false;
        this.b_remove = false;
        this.number = new Number(this);
        this.b_drawGlow = false;

        //Getting bitmap from drawable resource
        //bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.shape);
        this.instantiateType();
        this.instantiateBitMap();
        this.dealWithLocation();
        //TODO temporarily removed
        //this.glow = new Glow(this.context, this);
    }

    protected abstract void instantiateBitMap();

    protected void instantiateType(){
        //this.type = TYPE.values()[rand.nextInt(TYPE.values().length)];
        //keeps it from caching it in memory all the time.
        this.type = colorTypes.get(rand.nextInt(SIZE - 3));//minus the last three.
    }

    protected void dealWithLocation(){
        switch(this.location){
            case 0 : {
                this.x = TileGrid.TILE_LEFT - (this.getWidth() / 2);
                break;
            }
            case 1 : {
                this.x = TileGrid.TILE_MIDDLE - (this.getWidth() / 2);
                break;
            }
            case 2 : {
                this.x = TileGrid.TILE_RIGHT - (this.getWidth() / 2);
                break;
            }
        }
    }

    public void update(float delta){
        this.y += SAMPO_MAIN.speed * delta;

        if(this.y > TileGrid.TOP_BOUNDARY){
            this.b_movable = true;
        }
        if(this.y > TileGrid.BOTTOM_BOUNDARY){
            if(!this.b_updated_score){
                updateScore();
            }
            this.b_movable = false;
            this.b_updated_score = true;
            //this.b_remove = true;
        }
        //stops moving if bottom touches the line
        if(this.y + this.getHeight() > TileGrid.BOTTOM_BOUNDARY){
            this.b_movable = false;
            if(this.number.getValue() < 1){
                SAMPO_MAIN.b_gameover = true;
                new Delay(60000, SAMPO_MAIN.GAMESTATE.MENU);
            }
        }
        if(this.y > TileGrid.REMOVAL_BOUNDARY){
            //delete and remove this shape.
            this.b_remove = true;
        }

        if(this.x < 0 || this.x > SAMPO_MAIN.WIDTH){
            this.b_movable = false;
            this.b_remove = true;
        }
    }

    protected void updateScore(){
        //only update the combo if the score is positive!!!

        SAMPO_MAIN.combo++;
        this.number.addToScore();

        //otherwise restart the combo
        if(this.number.getValue() < 0) {
            SAMPO_MAIN.combo = 1;
        }
    }

    public void draw(Canvas canvas, Paint paint, float delta){
        canvas.drawBitmap(this.getBitmap(), this.getX(), this.getY(), paint);
        this.number.draw(canvas, paint);
        if(this.b_drawGlow){
            this.glow.draw(canvas, paint);
        }
    }

    protected Bitmap getResizedBitmap(Bitmap bitMap, int newWidth, int newHeight){
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        this.scaled_width = newWidth;
        this.scaled_height = newHeight;
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



    //gets/sets
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public final float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public final boolean getAlive(){
        return this.b_movable;
    }

    public final Shape.TYPE getType(){
        return this.type;
    }

    public final int getWidth(){
        return this.scaled_width;
    }
    public final int getHeight(){
        return this.scaled_height;
    }
    public void setGlowBoolean(boolean isGlowing){
        this.b_drawGlow = isGlowing;
    }
    public final boolean isGlowing(){
        return this.b_drawGlow;
    }
}
