package com.example.jun.sampo;

/**
 * Created by Jun on 21/08/2017.
 * static class to check for collisions
 */

public class CollisionManager {

    /**
     * returns true if the point p_x, p_y, is in a rectangular area.
     * p_x = point's x;
     * p_y = point's y;
     * r_x, r_y = rectangle's x & y
     * r_w, r_h = rectangle's width and height.
     */
    public static boolean contains(float p_x, float p_y, float r_x, float r_y, float r_w, float r_h){
        if((p_x >r_x) &&
            (p_x < r_x + r_w) &&
            (p_y > r_y) &&
            (p_y < r_y + r_h)){
           return true;
        }
        return false;
    }

}
