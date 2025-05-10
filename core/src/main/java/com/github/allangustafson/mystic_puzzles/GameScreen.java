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
    float progress;
    float time;
    float duration;


    public GameScreen(final Main game) {
        this.game = game;

        background = new Texture("sky.png");
        boardFrame = new Texture("BoardFrame.png");
        glowFrameTexture = new Texture("glowFrame.png");

        glowFrame = new Sprite(glowFrameTexture);
        glowFrame.setSize(1f,1f);
        startingPos = new Vector2(5f,2f);
        targetPos = new Vector2(5f,2f);
        increment = new Vector2();

        //progress = 0f;  // reset after target is reached
        //time = 0f;      // reset after target is reached
        duration = 10f;  // target will be reached in 1 second

        //glowPos = new Vector2(glowFrame.getX(), glowFrame.getY());

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        input();
        logic();

        targetPos.x = MathUtils.clamp(targetPos.x, 5, 10);
        targetPos.y = MathUtils.clamp(targetPos.y, 2, 6);

        increment.set(startingPos.interpolate(targetPos, .0001f, Interpolation.circleOut));

        glowFrame.setX(increment.x);
        glowFrame.setY(increment.y);

        draw();
        }

    private void input() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            targetPos.add(0f,1f);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            targetPos.sub(1f,0f);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            targetPos.sub(0f,1f);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            targetPos.add(1f,0f);
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
