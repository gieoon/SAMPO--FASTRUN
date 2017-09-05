package com.example.jun.sampo;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Created by Jun on 19/08/2017.
 */

public class SwipeListener extends GestureDetector.SimpleOnGestureListener {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    public static boolean b_ShapeMoveMode = false;

    private SwipeHandler swipeHandler;
    private Context context;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT;

        /**
         * Returns a direction given an angle.
         * Directions are defined as follows:
         *
         * Up: [45, 135]
         * Right: [0,45] and [315, 360]
         * Down: [225, 315]
         * Left: [135, 225]
         *
         * @param angle an angle from 0 to 360 - e
         * @return the direction of an angle
         *
         * localized class method, but can also add data into a constructor to use at the same time as well!!!
         */
        public static Direction get(double angle){
            if(inRange(angle, 45, 135)){
                return Direction.UP;
            }
            else if(inRange(angle, 0, 45) || inRange(angle, 315, 360)){
                return Direction.RIGHT;
            }
            else if(inRange(angle, 225, 315)){
                return Direction.DOWN;
            }
            else{
                return Direction.LEFT;
            }
        }

        private static boolean inRange(double angle, float init, float end){
            return (angle >= init) && (angle < end);
        }
    }

    public SwipeListener(SwipeHandler swipeHandler, Context context){
        super();
        this.swipeHandler = swipeHandler;
        this.context = context;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
        Log.w("ONFLING: ", "onFling called!!!");
        float x1 = e1.getX();
        float y1 = e1.getY();

        float x2 = e2.getX();
        float y2 = e2.getY();

        Direction direction = getDirection(x1, y1, x2, y2);
        if(!b_ShapeMoveMode) {
            moveShapes(direction);
        }
        else if(b_ShapeMoveMode){
            if(direction == Direction.LEFT || direction == Direction.RIGHT){
                move(direction);
            }
        }
        return true;
    }

//    @Override
//    public boolean onSwipe(){
//        Log.w("HIHI", "onSwipe called");
//        return false;
//    };

    //Using onLongPress to 'load' the player is really bad for this game, it's extremely confusing and not intuitive at all.
    @Override
    public void onLongPress(MotionEvent e){
        Log.w("Long Press Detected", "Long Press Called!!!");
        //activate shape drag mode!!!
        b_ShapeMoveMode = true;
        //play sound effect of all able to move
        MediaPlayer.create(this.context, MediaManager.loaded_id).start();
        //make the ones that will move glow.
        //TODO add in glow effects
        // this.swipeHandler.setGlows();
    }

    //a simple tap to disable the drag action
    @Override
    //public boolean onDown(MotionEvent e){
    public boolean onSingleTapUp(MotionEvent e){
        if(b_ShapeMoveMode){
            b_ShapeMoveMode = false;
        }
        return true;
    }



    public Direction getDirection(float x1, float y1, float x2, float y2){
        double angle = getAngle(x1, y1, x2, y2);
        return Direction.get(angle);
    }

    public double getAngle(float x1, float y1, float x2, float y2){
        double rad = Math.atan2(y1 - y2, x2 - x1) + Math.PI;
        return (rad * 180 / Math.PI + 180) % 360;
    }

    public boolean moveShapes(Direction direction){
        this.swipeHandler.moveShapes(direction);
        return true;
    }

    public boolean move(Direction direction){
        this.swipeHandler.move(direction);
        return true;
    }



}
