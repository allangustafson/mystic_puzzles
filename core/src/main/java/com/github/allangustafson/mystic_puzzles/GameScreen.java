package com.github.allangustafson.mystic_puzzles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final Main game;

    Texture background;
    Texture boardFrame;
    Texture glowFrameTexture;
    Sprite glowFrame;
    Vector2 startingPos;
    Vector2 targetPos;
    //Vector2 glowPos;
    Vector2 increment;
    Board board;
    boolean readyState;
    float elapsedTime;
    float duration;
    float progress;
    float startRow;
    float startCol;



    public GameScreen(final Main game) {
        this.game = game;

        background = new Texture("sky.png");
        boardFrame = new Texture("BoardFrame.png");
        glowFrameTexture = new Texture("glowFrame.png");

        glowFrame = new Sprite(glowFrameTexture);
        glowFrame.setSize(1.1f,1.1f);
        startingPos = new Vector2(4.95f,1.95f);
        targetPos = new Vector2(4.95f,1.95f);
        increment = new Vector2();
        increment.set(startingPos);
        board = new Board();
        board.initializeBoard();
        readyState = true;
        elapsedTime = 0f;
        duration = 2f;// target will be reached in 2 seconds
        //startRow = 0f;
        //startCol = 0f;

        //glowPos = new Vector2(glowFrame.getX(), glowFrame.getY());

    }

    public boolean reachedTarget(Vector2 vector) {
        // check if vector is close enough to manually finish it.
        // used to occasionally get stuck without this.
        if ((Math.abs(vector.x) - Math.floor(Math.abs(vector.x)) >= .9499) && (Math.abs(vector.y) - Math.floor(Math.abs(vector.y)) >= .9499)) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;
        progress = Math.min(elapsedTime/duration, 1f);

        if (readyState) {
            input();
        }
        logic();

        // if not awaiting input, and have reached your target: finish the increment, set new startingPos.
        if ((!readyState) && (reachedTarget(increment))) {        //&& (!startingPos.equals(increment)) do I need this?
            increment.x = ((float)Math.ceil(increment.x))-.05f;
            increment.y = ((float)Math.ceil(increment.y))-.05f;
            startingPos.set(increment);  //may take out later if we don't use
            readyState = true;
        }

        targetPos.x = MathUtils.clamp(targetPos.x, 4.95f, 9.95f);
        targetPos.y = MathUtils.clamp(targetPos.y, 1.95f, 5.95f);


        increment.interpolate(targetPos, progress, Interpolation.circleOut);

        glowFrame.setPosition(increment.x, increment.y);

        System.out.println(startRow + "," + startCol);
        //System.out.println(startingPos.x + "," + startingPos.y);

        draw();
        }


    private void input() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            targetPos.add(0f,1f);
            readyState = false;
            //time = 0f;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            targetPos.sub(1f,0f);
            readyState = false;
            //time = 0f;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            targetPos.sub(0f,1f);
            readyState = false;
            //time = 0f;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            targetPos.add(1f,0f);
            readyState = false;
            //time = 0f;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            //startRow = 0f;
            //startCol = 0f;
            startRow = glowFrame.getY();
            startCol = glowFrame.getX();
            board.swap(startRow,startCol,startRow + 1,startCol);
            readyState = false;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            startRow = glowFrame.getY();
            startCol = glowFrame.getX();
            board.swap(startRow,startCol,startRow ,startCol - 1);
            readyState = false;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            startRow = glowFrame.getY();
            startCol = glowFrame.getX();
            board.swap(startRow,startCol,startRow - 1,startCol);
            readyState = false;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            startRow = glowFrame.getY();
            startCol = glowFrame.getX();
            board.swap(startRow,startCol,startRow ,startCol + 1);
            readyState = false;
        }
    }

    private void logic() {
        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();
        float frameWidth = glowFrame.getWidth();
        float frameHeight = glowFrame.getHeight();


    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        // store the worldWidth and worldHeight as local variables for brevity
        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();

        // add lines to draw stuff here
        game.batch.draw(background, 0, 0, worldWidth, worldHeight); // draw the background
        game.batch.draw(boardFrame, 4, 1, 8,7); // draw the board frame
        board.draw(game.batch);
        glowFrame.draw(game.batch);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true); // true centers the camera
    }

    @Override
    public void hide() {
    }
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        background.dispose();
        boardFrame.dispose();
        glowFrameTexture.dispose();
    }
}
