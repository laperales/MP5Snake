package com.example.mp5snake;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private GridLayout board;
    private GradientDrawable emptyTile;
    private GradientDrawable snakePieces;
    private GradientDrawable foodPieces;
    private static final int N = 20;
    private Food food;
    private int HeadX;
    private int HeadY;
    private int score = 0;
    private static int highScore;
    private TextView scoreView;
    private TextView highScoreView;
    private boolean gameStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        board = findViewById(R.id.board);
        TextView timer = findViewById(R.id.timer);
        timer.setTextSize(50.0f);
        TextView scoreText = findViewById(R.id.scoreText);
        scoreText.setTextSize(30.0f);
        TextView highScoreText = findViewById(R.id.highScoreText);
        highScoreText.setTextSize(20.0f);
        scoreView = findViewById(R.id.score);
        scoreView.setTextSize(30.0f);
        highScoreView = findViewById(R.id.highScore);
        highScoreView.setTextSize(30.0f);
        highScoreView.setText(String.valueOf(highScore));
        findViewById(R.id.new_game).setOnClickListener(v -> {
            score = 0;
            scoreView.setText(String.valueOf(score));
            setUpBoard();
            gameStarted = true;
            new CountDownTimer(30000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timer.setText(String.valueOf(millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    board.removeAllViews();
                    gameStarted = false;
                    findViewById(R.id.new_game).setVisibility(View.VISIBLE);
                    if (score > highScore) {
                        highScore = score;
                    }
                    highScoreView.setText(String.valueOf(highScore));
                }
            }.start();
        });
        findViewById(R.id.up).setOnClickListener(v -> {
            advanceUp();
            redraw();
        });
        findViewById(R.id.down).setOnClickListener(v -> {
            advanceDown();
            redraw();
        });
        findViewById(R.id.right).setOnClickListener(v -> {
            advanceRight();
            redraw();
        });
        findViewById(R.id.left).setOnClickListener(v -> {
            advanceLeft();
            redraw();
        });
    }
    public void redraw() {
        if (!gameStarted) {
            return;
        }
        board.removeAllViews();
        Cells cells = new Cells(board);
        emptyTile = cells.getEmptyTile();
        foodPieces = cells.getFoodPieces();
        snakePieces = cells.getSnakePieces();
        board.setRowCount(N);
        board.setColumnCount(N);
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                if (y == HeadY && x == HeadX) {
                    GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                    layoutParams.columnSpec = GridLayout.spec(x);
                    layoutParams.rowSpec = GridLayout.spec(y);
                    ImageView cell = new ImageView(this);
                    cell.setImageDrawable(snakePieces);
                    cell.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    board.addView(cell, layoutParams);
                }
                if (food.getX() == x && food.getY() == y) {
                    GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                    layoutParams.columnSpec = GridLayout.spec(x);
                    layoutParams.rowSpec = GridLayout.spec(y);
                    ImageView cell = new ImageView(this);
                    cell.setImageDrawable(foodPieces);
                    cell.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    board.addView(cell, layoutParams);
                }
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                layoutParams.columnSpec = GridLayout.spec(x);
                layoutParams.rowSpec = GridLayout.spec(y);
                ImageView cell = new ImageView(this);
                cell.setImageDrawable(emptyTile);
                cell.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                board.addView(cell, layoutParams);
            }
        }
    }

    public void setUpBoard() {
        board.removeAllViews();
        Cells cells = new Cells(board);
        emptyTile = cells.getEmptyTile();
        foodPieces = cells.getFoodPieces();
        snakePieces = cells.getSnakePieces();
        board.setRowCount(N);
        board.setColumnCount(N);
        food = new Food();
        HeadX = 10;
        HeadY = 10;
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                if (y == HeadY &&  x == HeadX) {
                    GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                    layoutParams.columnSpec = GridLayout.spec(x);
                    layoutParams.rowSpec = GridLayout.spec(y);
                    ImageView cell = new ImageView(this);
                    cell.setImageDrawable(snakePieces);
                    cell.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    board.addView(cell, layoutParams);
                }
                if (food.getX() == x && food.getY() == y) {
                    GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                    layoutParams.columnSpec = GridLayout.spec(x);
                    layoutParams.rowSpec = GridLayout.spec(y);
                    ImageView cell = new ImageView(this);
                    cell.setImageDrawable(foodPieces);
                    cell.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    board.addView(cell, layoutParams);
                }
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                layoutParams.columnSpec = GridLayout.spec(x);
                layoutParams.rowSpec = GridLayout.spec(y);
                ImageView cell = new ImageView(this);
                cell.setImageDrawable(emptyTile);
                cell.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                board.addView(cell, layoutParams);
            }
        }
        findViewById(R.id.new_game).setVisibility(View.INVISIBLE);
    }
    public void advanceUp() {
        HeadY--;
        if (HeadY < 0) {
            HeadY = 19;
        }
        if (HeadY == food.getY() && HeadX == food.getX()) {
            score++;
            food = new Food();
        }
        scoreView.setText(String.valueOf(score));
    }
    public void advanceDown() {
        HeadY++;
        if (HeadY > N - 1) {
            HeadY = 0;
        }
        if (HeadY == food.getY() && HeadX == food.getX()) {
            score++;
            food = new Food();
        }
        scoreView.setText(String.valueOf(score));
    }
    public void advanceLeft() {
        HeadX--;
        if (HeadX < 0) {
            HeadX = 19;
        }
        if (HeadY == food.getY() && HeadX == food.getX()) {
            score++;
            food = new Food();
        }
        scoreView.setText(String.valueOf(score));
    }
    public void advanceRight() {
        HeadX++;
        if (HeadX >= N) {
            HeadX = 0;
        }
        if (HeadY == food.getY() && HeadX == food.getX()) {
            score++;
            food = new Food();
        }
        scoreView.setText(String.valueOf(score));
    }
}
