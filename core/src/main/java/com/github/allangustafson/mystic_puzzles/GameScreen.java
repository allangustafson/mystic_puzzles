package com.github.allangustafson.mystic_puzzles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final Main game;

    Texture background;
    Texture boardFrame;
    Texture glowFrameTexture;
    Sprite glowFrame;
    Vector2 currentPos;
    Vector2 targetPos;
    float seconds = 1f;
    float totalDelta = 0f;

    public GameScreen(final Main game) {
        this.game = game;

        background = new Texture("sky.png");
        boardFrame = new Texture("BoardFrame.png");
        glowFrameTexture = new Texture("glowFrame.png");

        glowFrame = new Sprite(glowFrameTexture);
        glowFrame.setSize(1,1);
        currentPos = new Vector2(5,2);
        targetPos = new Vector2(5,2);
        targetPos.set(MathUtils.clamp(targetPos.x, 5, 11),
                      MathUtils.clamp(targetPos.y, 2, 7));
        currentPos.set(MathUtils.clamp(currentPos.x, 5, 11),
                       MathUtils.clamp(currentPos.y, 2, 7));
        glowFrame.setPosition(currentPos.x,currentPos.y);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        input();
        logic();
        //totalDelta and lerp don't seem to be working as intended. Perhaps I may need a larger viewport?
        totalDelta += delta;
        glowFrame.setPosition(currentPos.lerp(targetPos, totalDelta/seconds).x, currentPos.lerp(targetPos, totalDelta/seconds).y);
        draw();
    }

    private void input() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            targetPos.add(0f,1f);
            currentPos = targetPos;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            targetPos.sub(1f,0f);
            currentPos = targetPos;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            targetPos.sub(0f,1f);
            currentPos = targetPos;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            targetPos.add(1f,0f);
            currentPos = targetPos;
        }
    }

    private void logic() {
        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();
        float frameWidth = glowFrame.getWidth();
        float frameHeight = glowFrame.getHeight();

        glowFrame.setX(MathUtils.clamp(glowFrame.getX(),5, 10 ));
        glowFrame.setY(MathUtils.clamp(glowFrame.getY(),2, 6 ));

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
