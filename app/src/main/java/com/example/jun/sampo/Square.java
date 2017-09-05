package com.example.jun.sampo;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Jun on 12/08/2017.
 */

public class Square extends Shape {

    public Square(Context context, int location){
        super(context, location);
        this.shapeType = SHAPE.SQUARE;
        //this.x -= this.scaled_width / 2;

    }

    @Override
    protected void instantiateBitMap(){
        switch(this.type){
            case GREEN : {
                this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.square_g);
                break;
            }
            case BLUE : {
                this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.square_b);
                break;
            }
            case YELLOW: {
                this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.square_y);
                break;
            }
            case RED : {
                this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.square_r);
                break;
            }
        }
        this.bitmap = getResizedBitmap(this.bitmap, this.bitmap.getWidth() / SIZE_DEDUCTION, this.bitmap.getHeight() / SIZE_DEDUCTION);
    }

    @Override
    public void update(float delta){
        super.update(delta);
    }

    @Override
    public void draw(Canvas canvas, Paint paint, float delta) {
        super.draw(canvas, paint, delta);
    }
}
