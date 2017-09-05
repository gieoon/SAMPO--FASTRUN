package com.example.jun.sampo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Jun on 1/08/2017.
 */

public class ShapeHandler {

    //holds array of all shapes in the game
    private LinkedList<Shape> shapes_list;
    private Iterator<Shape> iter;
    public static ShapeHandler shapeHandler;
    private static Random rand = new Random();
    private int count = 0;
    private final int MAX_COUNT = 150;//distance between spawns
    private final int SPAWN_PERCENTAGE = 4;
    private Context context;
    private String TAG = ShapeHandler.class.getSimpleName();

    private ShapeHandler(Context context){
        this.shapes_list = new LinkedList<>();
        this.context = context;
    }

    public static void initShapeGenerator(Context context){
        shapeHandler = new ShapeHandler(context);
    }

    public synchronized void addShapeToList(Shape shape){
        shapes_list.add(shape);
    }

    public synchronized void removeShapeFromList(Shape shape){
        shapes_list.remove(shape);
    }

    public void emptyList(){
        this.shapes_list.clear();
    }

    public void update(float delta){

        count++;
        if(count == MAX_COUNT){
            count = 0;
            //three times for each grid.
            for(int location = 0; location < 3; location++) {
                if (new Random().nextInt(10) > SPAWN_PERCENTAGE) {
                    switch (rand.nextInt(3)) {//no){
                        case 0: {
                            addShapeToList(new Square(context, location));
                            break;
                        }
                        case 1: {
                            addShapeToList(new Circle(context, location));
                            break;
                        }
                        case 2: {
                            addShapeToList(new Triangle(context, location));
                            break;
                        }
                    }
                    Log.i(TAG, "Shape spawned");
                }
            }
        }

//        this.iter = shapes_list.iterator();
//        while(iter.hasNext()){
//            Shape shape = iter.next();
//            shape.update();
//            if(shape.b_remove){
//                removeShapeFromList(shape);
//                //shape = null;
//            }
//        }

        for (int i = shapes_list.size() - 1; i >= 0; i--){
            Shape shape = shapes_list.get(i);
            shape.update(delta);
            if(shape.b_remove){
                removeShapeFromList(shape);
            }
        }

        //Iterate through, and update all shapes in the list.
    }

    public void draw(Canvas canvas, Paint paint, float delta){
        for(int i = 0; i < shapes_list.size(); i++){
            shapes_list.get(i).draw(canvas, paint, delta);
        }
    }

    public LinkedList<Shape> getShapesList(){
        return this.shapes_list;
    }

}
