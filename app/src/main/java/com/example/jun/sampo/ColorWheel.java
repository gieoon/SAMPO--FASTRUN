package com.example.jun.sampo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Jun on 20/08/2017.\
 * Appears on the right of the screen and shows three colours at a time.
 */

public class ColorWheel extends MenuObject {

    //private SAMPO_LOOP sampo_loop;
    private Shape.TYPE currentType;
    private int upperColor, lowerColor;
    //private Bitmap upperShape, lowerShape;
    //private int shapeWidth;

    public ColorWheel(Context context, DisplayMetrics displayMetrics){
        super(context, displayMetrics);
        //create a dummy data to input to get the sizes?
        //this.sampo_loop = sampo_loop;
    }

    public void setCurrentType(Shape.TYPE currentType){
        this.currentType = currentType; //so...apparently this doesn't store in memory as coming from the one who set it. It is passed by value, and not by reference...?
    }

    @Override
    public void setLocation() {}

    @Override
    public void update() {
        setNeighbouringColours();
    }

    private Rect upperRectangle(){
        return new Rect(0, 0, this.displayMetrics.widthPixels, TileGrid.TOP_BOUNDARY);

    }

    private Rect lowerRectangle(){
        return new Rect(0,  TileGrid.BOTTOM_BOUNDARY, this.displayMetrics.widthPixels, this.displayMetrics.heightPixels);
    }


    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(lowerColor);
        canvas.drawRect(upperRectangle(), paint);
        paint.setColor(upperColor);
        canvas.drawRect(lowerRectangle(), paint);

        //draw the background shape if it exists.
//        if(this.upperShape != null) {
//            //upper shape
//            canvas.drawBitmap(this.upperShape, this.shapeX, this.shapeTopY, paint);
//        }
//        if(this.lowerShape != null) {
//            //lower shape
//            canvas.drawBitmap(this.lowerShape, this.shapeX, this.shapeBottomY, paint);
//        }
    }

    private void scrollRoulette(){
        //tween the roulette colours
    }

    private void tweenBackground(){
        //tween the background when it changes.
    }

    private void setNeighbouringColours(){
        //get index of current enum name.
        int index = Shape.TYPE.valueOf(currentType.name()).ordinal();
        int u_index = index - 1;
        if(u_index < 0){
            u_index = Shape.SIZE - 1;
        }
        upperColor = BackgroundManager.getBackgroundColor(Shape.colorTypes.get(u_index));
//        if(upperColor == Color.GRAY) {
//            upperShape = getCurrentShape(Shape.colorTypes.get(u_index));
//        }
//        else{
//            upperShape = null;
//        }

        int l_index = index + 1;
        if(l_index == Shape.SIZE){
            l_index = 0;
        }
        lowerColor = BackgroundManager.getBackgroundColor(Shape.colorTypes.get(l_index));
//        if(lowerColor == Color.GRAY) {
//            lowerShape = getCurrentShape(Shape.colorTypes.get(l_index));
//        }
//        else{
//            lowerShape = null;
//        }
        //this.currentType = Shape.colorTypes.get(index);
        //return BackgroundManager.getBackgroundColor(this.currentType);
    }

    //set the roulette to the hardcoded image based on the current colour.
    public void setRouletteImage(Shape.TYPE color){
        switch(color){
            //makes the color fill the whole screen.
//            case GREEN: {
//                //make the background this colour
//                this.sampo_loop.setBackgroundColor(Color.argb(255,0,255,0));
//                break;
//            }
//            case BLUE: {
//                this.sampo_loop.setBackgroundColor(Color.argb(255,0,0,255));
//                break;
//            }
//            case YELLOW: {//could also ocnsider purple...
//                this.sampo_loop.setBackgroundColor(Color.argb(255,255,255,0));
//                break;
//            }
//            case RED : {
//                this.sampo_loop.setBackgroundColor(Color.argb(255,255,0,0));
//                break;
//            }
        }
    }

}
