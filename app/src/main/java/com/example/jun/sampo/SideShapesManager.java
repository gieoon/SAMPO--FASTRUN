package com.example.jun.sampo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by Jun on 3/09/2017.
 * Checks if the shape is the same as current filter, if it is, then move it.
 */

public class SideShapesManager extends MenuObject {
    private Bitmap leftShape, rightShape;
    private float shapeXLeft;
    private float shapeXRight;
    private float shapeY;
    private Context context;
    private Shape.SHAPE currentShapeType;
    private final int SIZE_DEDUCTION_MINI = 6;

    public SideShapesManager(Context context, DisplayMetrics displayMetrics){
        Bitmap dummyShape = BitmapFactory.decodeResource(context.getResources(), R.drawable.triangle_back);
        dummyShape = this.getResizedBitmap(dummyShape, dummyShape.getWidth() / SIZE_DEDUCTION_MINI, dummyShape.getHeight() / SIZE_DEDUCTION_MINI);
        this.shapeY = displayMetrics.heightPixels / 2 - dummyShape.getHeight() / 2;
        this.shapeXLeft = TileGrid.MARGIN * 2;
        this.shapeXRight = displayMetrics.widthPixels - dummyShape.getWidth() - TileGrid.MARGIN * 2;
        this.context = context;
        this.currentShapeType = Shape.SHAPE.NONE;
    }

    @Override
    public void setLocation() {}

    @Override
    public void update() {
        setNeighbouringShapes();
    }

    private void setNeighbouringShapes(){
        int index = Shape.SHAPE.valueOf(this.currentShapeType.name()).ordinal();
        int left_index = index - 1;
        if(left_index < 0){
            left_index = Shape.SHAPE_TYPE_SIZE - 1;
        }
        this.leftShape = getCurrentShape(Shape.shapeTypes.get(left_index));

        int right_index = index + 1;
        if(right_index == Shape.SHAPE_TYPE_SIZE){
            right_index = 0;
        }
        this.rightShape = getCurrentShape(Shape.shapeTypes.get(right_index));

    }

        public final Bitmap getCurrentShape(Shape.SHAPE shapeType){
        //declare that a shape has been assigned.
        Bitmap b;
        switch(shapeType){
            case TRIANGLE : {
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.triangle_back);
                break;
            }
            case CIRCLE : {
                b =BitmapFactory.decodeResource(context.getResources(), R.drawable.circle_back);
                break;
            }
            case SQUARE : {
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.square_back);
                break;
            }
            case NONE : {
                b = null;
                break;
            }
            default : {
                Log.w(TAG, "Error loading shape background image");
                return null;
            }
        }
        if(b != null)
            return getResizedBitmap(b, b.getWidth() / SIZE_DEDUCTION_MINI, b.getHeight() / SIZE_DEDUCTION_MINI);
        return null;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        //draw another big one of the current shape in the middle!!!
        if(this.leftShape != null)
            canvas.drawBitmap(this.leftShape, this.shapeXLeft, this.shapeY, paint);
        if(this.rightShape != null)
            canvas.drawBitmap(this.rightShape, this.shapeXRight, this.shapeY, paint);
    }

    public final Shape.SHAPE getCurrentShape(){
        return this.currentShapeType;
    }
    public void setShapeType(Shape.SHAPE shape){
        this.currentShapeType = shape;
    }
}
