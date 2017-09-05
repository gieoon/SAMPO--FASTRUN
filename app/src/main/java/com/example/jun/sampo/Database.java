package com.example.jun.sampo;

/**
 * Created by Jun on 24/08/2017.
 * Using firebase database to manage everything
 */

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Database {
    public static FirebaseDatabase database;
    public static DatabaseReference myRef;

    public Database(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
    }

    public static void addScoreToDatabase(String name){
        Map<String, Integer> score = new HashMap<>();
        score.put(name, SAMPO_MAIN.score);
        myRef.setValue(score);
    }

    public static ArrayList<Integer> getHighscores(){
        //query firebase for the highest 100 scores, and make it scrollable downwards!
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren() ){
                    child.getValue();
                    Log.w(TAG, ""+child.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {}
        });
        //on server-side, delete any that are more than 100.
        return null;
    }

}
