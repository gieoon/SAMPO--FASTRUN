package com.example.jun.sampo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.GestureDetectorCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by Jun on 1/08/2017.
 * game loop, updating, and drawing machine
 * Also contains the view.
 */

public class SAMPO_LOOP extends SurfaceView implements Runnable {

    //boolean variable to track if the game is playing or not
    //volatile = always changing and never cached thread locally. Different according to each thread.
    volatile boolean playing;

    //the game thread
    private Thread thread = null;
    private int fps = 60;
    private int frameCount = 0;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private GestureDetectorCompat detector;
    private BackgroundManager backgroundManager;
    private Logo logo;
    private ColorWheel colorWheel;
    private SideShapesManager sideShapesManager;
    private BottomBoundary bottomBoundary;
    private Score score;
    private DisplayMetrics displayMetrics;

    public SAMPO_LOOP(Context context, DisplayMetrics displayMetrics){
        super(context);
        this.displayMetrics = displayMetrics;
        initMenuObjects();
        this.backgroundManager = new BackgroundManager(this, this.colorWheel);
        this.sideShapesManager = new SideShapesManager(this.getContext(), this.displayMetrics);
        this.detector= new GestureDetectorCompat(context, new SwipeListener(new SwipeHandler(this.backgroundManager, this.sideShapesManager), context));
        this.surfaceHolder = this.getHolder();
        this.paint = new Paint();
        this.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                Log.w("TOUCH", "Touch Event Received");
                return detector.onTouchEvent(event);
            }

        });
//        @Override
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            return detector.onTouchEvent(motionEvent);
//        }
//
//        OnSwipeListener onSwipeListener = new OnSwipeListener(){
//            @Override
//            public boolean onSwipe(SwipeListener.Direction direction){
//
//                return false;
//            }
//        }
//        ((Activity) getContext()).getWindowManager()
//                .getDefaultDisplay()
//                .getMetrics(displayMetrics);
//        WIDTH = this.getWidth();
//        HEIGHT = this.getHeight();
        //Log.i("SAMPO_LOOP", "WIDTH: " + WIDTH);

    }

    private void initMenuObjects(){
        this.colorWheel = new ColorWheel(this.getContext(), this.displayMetrics);
        //this.sideShapesManager = new SideShapesManager(this.getContext(), this.displayMetrics);
        this.logo = new Logo(this.getContext(), this.displayMetrics);
        this.bottomBoundary = new BottomBoundary(this.getContext(), this.displayMetrics);
        this.score = new Score(this.getContext(), this.displayMetrics);
    }

    @Override
    public void run(){

        final double GAME_HERTZ = 30.0;
        final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
        final int MAX_UPDATES_BEFORE_RENDER = 5;
        double lastUpdateTime = System.nanoTime();
        double lastRenderTime = System.nanoTime();

        final double TARGET_FPS = 60;
        final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

        int lastSecondTime = (int) (lastUpdateTime / 1000000000);

        while(playing){
            double now = System.nanoTime();
            int updateCount = 0;
            float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
            while(now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER){
                update(interpolation);
                lastUpdateTime += TIME_BETWEEN_UPDATES;
                updateCount++;
            }
            if(now - lastUpdateTime > TIME_BETWEEN_UPDATES){
                lastUpdateTime = now - TIME_BETWEEN_UPDATES;
            }

            draw(interpolation);
            lastRenderTime = now;

            int thisSecond = (int) (lastUpdateTime / 1000000000);
            if(thisSecond > lastSecondTime){
                Log.w("NEW SECOND", thisSecond + " " + frameCount);
                fps = frameCount;
                frameCount = 0;
                lastSecondTime = thisSecond;
            }

            //control();
        }
    }

    private void update(float delta){

        ShapeHandler.shapeHandler.update(delta);

        for(int i = 0; i < MenuObject.menuObjects.size(); i++){
            MenuObject mo = MenuObject.menuObjects.get(i);
            mo.update();
        }

        //choose the activity to show
        if(SAMPO_MAIN.b_gameover){
            this.getContext().startActivity(new Intent(this.getContext(), SAMPO_HIGHSCORE.class));
        }


        this.colorWheel.setCurrentType(this.backgroundManager.getCurrentType());
    }

    private void draw(float delta){
        //checking if surface is valid
        if(surfaceHolder.getSurface().isValid()){
            //locking the canvas
            this.canvas = surfaceHolder.lockCanvas();
            //drawing background colour for canvas
            drawBackground(this.backgroundManager.getBackgroundColor(this.backgroundManager.getCurrentType()));//recursive loop, will work???
            ShapeHandler.shapeHandler.draw(canvas, this.paint, delta);
            for(int i = 0 ; i < MenuObject.menuObjects.size(); i++){
                MenuObject mo = MenuObject.menuObjects.get(i);
                mo.draw(this.canvas, this.paint);
            }
            //should draw a menu class, draws the color rouletteon the right, and the score at the top, and the end at the bottom.
            this.surfaceHolder.unlockCanvasAndPost(this.canvas);
        }
    }

    //get the current background colour!!!
    private void drawBackground( int color){
        this.canvas.drawColor(color);
    }

    //delays the game loop.
    //use a proper java game loop that checks elapsed time...
    private void control(){
        try{
            thread.sleep(17);//tried slowing this down and increasing speed...
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void pause(){
        playing = false;
        try{
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void resume(){
        playing = true;
        thread = new Thread(this);
        thread.start();
    }

    public final GestureDetectorCompat getDetector(){
        return this.detector;
    }

}
