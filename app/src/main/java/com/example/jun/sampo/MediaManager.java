package com.example.jun.sampo;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Jun on 5/09/2017.
 * Plays all the musics
 */

public class MediaManager {

    private Context context;
    public static int loaded_id, loss_id, shape_break_id, goal_id;

    public MediaManager(Context context) {
        this.context = context;
        this.loaded_id = context.getResources().getIdentifier("loaded", "raw", context.getPackageName());
        this.loss_id = context.getResources().getIdentifier("loss", "raw", context.getPackageName());
        this.shape_break_id = context.getResources().getIdentifier("shape_break", "raw", context.getPackageName());
        this.goal_id = context.getResources().getIdentifier("goal", "raw", context.getPackageName());
    }
}
