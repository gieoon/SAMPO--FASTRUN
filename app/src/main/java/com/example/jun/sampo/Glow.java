package com.example.jun.sampo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Jun on 3/09/2017.
 */

public class Glow extends MenuObject {

    //the shape that this glow is bound to.
    private Context context;
    private Shape shape;
    private Bitmap shapeBitmap, alpha;
    private float margin = TileGrid.MARGIN;
    private float halfMargin =  TileGrid.MARGIN / 2.0f;
    private float glowRadius;
    private int glowColor;

    public Glow(Context context, Shape shape){
        this.shape = shape;
        this.context = context;
        this.glowRadius = this.shape.getWidth() + margin;
        this.glowColor = Color.rgb(0, 192, 255);

        //original image to use
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), instantiateBitmap());
        this.alpha = bmp.extractAlpha();
        this.shapeBitmap = Bitmap.createBitmap(bmp.getWidth() + (int)margin, bmp.getHeight() + (int)margin, Bitmap.Config.ARGB_8888);
    }

    private int instantiateBitmap(){
        switch(this.shape.getType()){
            case GREEN : {
               return R.drawable.circle_g;
            }
            case BLUE : {
                return R.drawable.circle_b;
            }
            case YELLOW: {
                return R.drawable.circle_y;
            }
            case RED : {
                return R.drawable.circle_r;
            }
            default : {
                Log.w(TAG, "Error instantiating bitmap for glow");
                return 0;

            }
        }
    }

    @Override
    public void setLocation() {

    }

    @Override
    public void update() {
        //bind the coordinates to the shape
        this.x = this.shape.getX();
        this.y = this.shape.getY();
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas = new Canvas(this.shapeBitmap);
        paint.setColor(glowColor);

        //outer glow
        paint.setMaskFilter(new BlurMaskFilter(glowRadius, BlurMaskFilter.Blur.OUTER));
        canvas.drawBitmap(this.alpha, halfMargin, halfMargin, paint);

        //original icon
        //canvas.drawBitmap(this.bitmap);
    }
}
