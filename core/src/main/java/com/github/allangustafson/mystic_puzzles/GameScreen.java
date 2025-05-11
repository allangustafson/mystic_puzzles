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
    //float progress;
    //float time;
    //float duration;



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

        //progress = 0f;  // reset after target is reached
        //time = 0f;      // reset after target is reached
        //duration = 1f;  // target will be reached in 1 second

        //glowPos = new Vector2(glowFrame.getX(), glowFrame.getY());

    }

    public boolean reachedTarget(Vector2 vector) {
        return Math.abs(vector.x) - Math.floor(Math.abs(vector.x)) > .9499;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        input();
        logic();

        if (!startingPos.equals(increment) && reachedTarget(increment)) {
            increment.x = ((float)Math.ceil(increment.x))-.05f;
            increment.y = (float)Math.ceil(increment.y)-.05f;
            startingPos.set(increment);
        }

        targetPos.x = MathUtils.clamp(targetPos.x, 4.95f, 9.95f);
        targetPos.y = MathUtils.clamp(targetPos.y, 1.95f, 5.95f);

        //time += delta / duration;

        //if (time >= .5f) time = .5f;
        increment.interpolate(targetPos, .1f, Interpolation.circleOut);

        glowFrame.setPosition(increment.x, increment.y);


        draw();
        }

    private void input() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            targetPos.add(0f,1f);
            //time = 0f;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            targetPos.sub(1f,0f);
            //time = 0f;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            targetPos.sub(0f,1f);
            //time = 0f;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            targetPos.add(1f,0f);
            //time = 0f;
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
