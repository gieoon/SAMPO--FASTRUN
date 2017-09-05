package com.example.jun.sampo;

import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by gieoon on 19/08/2017.
 * Creates the tiles and grid that everything moves in.
 * contains static points that are used in the game.
 */

public class TileGrid {
    private final static float SCREEN_TIME = 15.0f; //takes 15 seconds for a shape to traverse the screen.
    private final static int TILE_MAX = 3;
    private final static int VERTICAL_TILE_MAX = 12;
    public static int MARGIN, BOTTOM_BOUNDARY, TOP_BOUNDARY, TILE_SIZE, REMOVAL_BOUNDARY, TILE_LEFT, TILE_RIGHT, TILE_MIDDLE, TILE_HEIGHT;

    public static void instantiateGameBoundaries(DisplayMetrics displayMetrics){
        //boundaries to determine the live zone, where if under top boundary, can be moved,
        //if touching bottom boundary, then send the spirits through.
        MARGIN = displayMetrics.heightPixels / 100;
        BOTTOM_BOUNDARY = displayMetrics.heightPixels - MARGIN;
        TOP_BOUNDARY = MARGIN;
        //Log.w("BOTTOM BOUNDARY: ", "" + BOTTOM_BOUNDARY);
        //tile size is equal to middle area minus the margins, and a third of those.
        TILE_SIZE = (displayMetrics.widthPixels - (MARGIN * 5)) / TILE_MAX; //the smaller the multiplier is, the wider the tile size.

        //split the grid into 12.
        TILE_HEIGHT = displayMetrics.heightPixels / VERTICAL_TILE_MAX;
        //compare the height of the sprite to the height of the tile.
        //set the size of the sprite equal to this height and a little bit less.

        //shape advances through screen over 20 seconds.
        //calculate the distance for shape to travel this distance.
        // d = s * t;
        int screenHeight = displayMetrics.heightPixels;
        SAMPO_MAIN.speed = screenHeight / (SCREEN_TIME * 60.0f);
        //t = t / SCREEN_TIME //20seconds;
        //d = d / TILE_MAX
        //s = d / t;

        TILE_MIDDLE = displayMetrics.widthPixels / 2; //TILE_SIZE + MARGIN; //displayMetrics.heightPixels / 2;
        TILE_LEFT = TILE_MIDDLE - TILE_SIZE; //MARGIN;
        TILE_RIGHT = TILE_MIDDLE + TILE_SIZE ;//TILE_SIZE * 2 + MARGIN;

        REMOVAL_BOUNDARY = BOTTOM_BOUNDARY + 100;
    }
}
