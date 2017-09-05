package com.example.jun.sampo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

/**
 * Created by Jun on 1/08/2017.
 * Activity where the main page of the game runs
 * Should this be made in Xamarin & C#, or in Cordova? Wow.
 */

public class SAMPO_MAIN extends AppCompatActivity {

    private SAMPO_LOOP sampo_loop;
    public static MediaManager mediaManager;
    public static int WIDTH = 0;
    public static int HEIGHT = 0;
    public static boolean b_gameover;
    public static float speed;
    public static int score = 1;
    public static int combo = 1;//calculates the current combo, keeps building up as a multiplier!!!

    public enum GAMESTATE {
        PLAYING, MENU, HIGHSCORE
    }

    public static GAMESTATE gamestate = GAMESTATE.MENU;

    private void restart(){
        SAMPO_MAIN.speed = 1.0f;
        SAMPO_MAIN.score = 1;
        SAMPO_MAIN.combo = 1;
        if(ShapeHandler.shapeHandler != null)
            ShapeHandler.shapeHandler.emptyList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        this.mediaManager = new MediaManager(this);

        restart();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        WIDTH = displayMetrics.widthPixels;
        HEIGHT = displayMetrics.heightPixels;
        TileGrid.instantiateGameBoundaries(displayMetrics);
        ShapeHandler.initShapeGenerator(this);

        //Initializing game view object
        sampo_loop = new SAMPO_LOOP(this, displayMetrics);
        setContentView(sampo_loop);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.sampo_loop.getDetector().onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onPause(){
        super.onPause();
        sampo_loop.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        sampo_loop.resume();
    }

}
