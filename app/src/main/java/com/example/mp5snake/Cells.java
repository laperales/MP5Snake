package com.example.mp5snake;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.GridLayout;

public class Cells {
    private final int N = 20;
    private GradientDrawable emptyTile;
    private GradientDrawable snakePieces;
    private GradientDrawable foodPieces;

    Cells(GridLayout board) {
        int tileSize = (int) (board.getWidth() / (double)  N);
        emptyTile = new GradientDrawable();
        emptyTile.setShape(GradientDrawable.RECTANGLE);
        emptyTile.setSize(tileSize, tileSize);
        emptyTile.setStroke(2, Color.WHITE);
        snakePieces = new GradientDrawable();
        snakePieces.setShape(GradientDrawable.RECTANGLE);
        snakePieces.setSize(tileSize, tileSize);
        snakePieces.setColor(Color.BLACK);
        foodPieces = new GradientDrawable();
        foodPieces.setShape(GradientDrawable.RECTANGLE);
        foodPieces.setSize(tileSize, tileSize);
        foodPieces.setColor(Color.BLUE);
    }

    public GradientDrawable getEmptyTile() {
        return emptyTile;
    }

    public GradientDrawable getSnakePieces() {
        return snakePieces;
    }

    public GradientDrawable getFoodPieces() {
        return foodPieces;
    }
}
