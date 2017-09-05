package com.example.jun.sampo;

import android.util.Log;

/**
 * Created by Jun on 19/08/2017.
 * Moves the shapes after a swipe is detected
 */

public class SwipeHandler {

    private BackgroundManager backgroundManager;
    private SideShapesManager sideShapesManager;
    private final String TAG = this.getClass().getSimpleName();

    private enum MOVEMENT_DIRECTION {
        RIGHT, LEFT
    }
    private MOVEMENT_DIRECTION currentDirection;

    public SwipeHandler(BackgroundManager backgroundManager, SideShapesManager sideShapesManager){
        this.backgroundManager = backgroundManager;
        this.sideShapesManager = sideShapesManager;
    }

    //for not moving the shapes.
    public void moveShapes(SwipeListener.Direction direction){
        switch(direction){
            case UP : {
                //make colour wheel change
                this.backgroundManager.moveUp();
                //Log.w(TAG, "moveUP called" );
                break;
            }
            case DOWN : {
                //make colour wheel change
                this.backgroundManager.moveDown();
                //Log.w(TAG, "moveDown called" );
                break;
            }
            case LEFT : {
                //move the shapes grid!!
                moveShapesFilterLeft();
//                Log.w(TAG, "moveLeft called" );
                break;
            }
            case RIGHT : {
                //move the shapes grid!!
                moveShapesFilterRight();
                //Log.w(TAG, "moveRight called" );
                break;
            }
        }
    }

    public void moveShapesFilterLeft(){
        Log.w(TAG, "Moving all shapes right");
        int temp_index = Shape.SHAPE.valueOf(this.sideShapesManager.getCurrentShape().name()).ordinal();
        temp_index--;
        if(temp_index < 0){
            temp_index = Shape.SHAPE_TYPE_SIZE - 1;
        }

        this.sideShapesManager.setShapeType(Shape.shapeTypes.get(temp_index));
    }

    public void moveShapesFilterRight(){
        int temp_index = Shape.SHAPE.valueOf(this.sideShapesManager.getCurrentShape().name()).ordinal();
        temp_index ++;
        if(temp_index == Shape.SHAPE_TYPE_SIZE){
            temp_index = 0;
        }

        this.sideShapesManager.setShapeType(Shape.shapeTypes.get(temp_index));

    }

    public void move(SwipeListener.Direction direction){
        switch(direction){
            case LEFT : {
                longMoveLeft();
                break;
            }
            case RIGHT : {
                longMoveRight();
                break;
            }
        }
    }

    private void longMoveLeft(){

        this.currentDirection = MOVEMENT_DIRECTION.LEFT;

        for(int i = 0; i < ShapeHandler.shapeHandler.getShapesList().size(); i++){
            Shape shape = ShapeHandler.shapeHandler.getShapesList().get(i);
            if(shape.getAlive()) {
                if (checkMovementAllowed(shape)) {
                    shape.setX(shape.getX() - TileGrid.TILE_SIZE);
                }
            }
        }
    }

    private void longMoveRight(){

        this.currentDirection = MOVEMENT_DIRECTION.RIGHT;

        for(int i = 0; i < ShapeHandler.shapeHandler.getShapesList().size(); i++){
            Shape shape = ShapeHandler.shapeHandler.getShapesList().get(i);
            if(shape.getAlive()) {//if is in the play area.
                if (checkMovementAllowed(shape)) {
                    shape.setX(shape.getX() + TileGrid.TILE_SIZE);
                }
            }
        }
    }

    public void setGlows(){
        //find the shapes that apply to conditions,
        //shape is of same type
        //shape is of same colour.
        for(int i = 0; i < ShapeHandler.shapeHandler.getShapesList().size(); i++){
            Shape shape = ShapeHandler.shapeHandler.getShapesList().get(i);
            if(shape.getAlive()) {
                //if it is of the same type, or it is of no type, or if of the same colour
                if(shape.shapeType == this.sideShapesManager.getCurrentShape() ||
                        shape.shapeType == Shape.SHAPE.NONE ||
                        this.backgroundManager.getBackgroundColor(shape.getType()) == this.backgroundManager.getBackgroundColor(this.backgroundManager.getCurrentType())){
                    if(shape.getAlive()) {
                        shape.b_drawGlow = true;
                    }
                }
                //if (checkMovementAllowed(shape)) {
                shape.setX(shape.getX() - TileGrid.TILE_SIZE);
                //}
            }
        }
    }

    /**
     * Checks if the movement is allowed,
     * is allowed if the same colour as the background.
     * @return allowance or not
     *
     */
    private boolean checkMovementAllowed(Shape shape){
        //if the type of the shape is the same as the current background...
        if (this.backgroundManager.getBackgroundColor(shape.getType()) == this.backgroundManager.getBackgroundColor(this.backgroundManager.getCurrentType())){
            //check if any shape is adjacent to this shape
            if(!adjacentExists(shape)){
                //allow the movement
                return true;
            }
        }
        return false;
    }

    private boolean adjacentExists(Shape shape){
        switch(this.currentDirection){
            //if on the left, and going left, not need to check
            //if on the right, and going right, then no need to check.
            case LEFT : {
                Log.w(TAG, "left move checking");
                //check the middle and the right.
                if(shape.getX() != TileGrid.TILE_LEFT){
                    for(int i = 0; i < ShapeHandler.shapeHandler.getShapesList().size(); i++) {
                        Shape temp_shape = ShapeHandler.shapeHandler.getShapesList().get(i);
                        if (CollisionManager.contains(shape.getX() - (TileGrid.TILE_SIZE / 2), shape.getY() + (shape.getHeight() / 2), temp_shape.getX(), temp_shape.getY(), temp_shape.getWidth(), temp_shape.getHeight())) {
//                            if(IsAdjacentColorDifferent(shape, temp_shape)){
                                return true;
//                            }
                        }
                    }
                }

                break;
            }
            case RIGHT : {
                Log.w(TAG, "right move checking");
                //check the middle and the left tiles.
                if(shape.getX() != TileGrid.TILE_RIGHT){
                    for(int i = 0; i < ShapeHandler.shapeHandler.getShapesList().size(); i++) {
                        Shape temp_shape = ShapeHandler.shapeHandler.getShapesList().get(i);
                        if (CollisionManager.contains(shape.getX() + TileGrid.TILE_SIZE + (TileGrid.TILE_SIZE / 2), shape.getY() + (shape.getHeight() / 2), temp_shape.getX(), temp_shape.getY(), temp_shape.getWidth(), temp_shape.getHeight())) {
                            if(IsAdjacentColorDifferent(shape, temp_shape)){
                                return true;
                            }
                        }
                    }
                }
                break;
            }
        }
        return false;
    }

    /**
     * Check if the adjacent shape is the same colour,
     * if same colour, allow movement
     * if different colour, stop movement.
     *
     */
    private boolean IsAdjacentColorDifferent(Shape shape_to_move, Shape shape_adjacent){
        //a shape is adjacent,
        //movement is not allowed if the shape is a different colour.

        //movement is allowed if the shape is adjacent and is the same colour.
        if(shape_to_move.getType() == shape_adjacent.getType()){
            return false;
        }
        return true;
    }
}
