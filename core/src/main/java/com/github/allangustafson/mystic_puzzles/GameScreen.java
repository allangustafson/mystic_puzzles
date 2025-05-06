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
    Vector2 currentPos = new Vector2();

    public GameScreen(final Main game) {
        this.game = game;

        background = new Texture("sky.png");
        boardFrame = new Texture("BoardFrame.png");
        glowFrameTexture = new Texture("glowFrame.png");

        glowFrame = new Sprite(glowFrameTexture);
        glowFrame.setSize(1,1);
        glowFrame.setPosition(5,2);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        input();
        logic();
        draw();
    }

    private void input() {
        float speed = 1f;
        float delta = Gdx.graphics.getDeltaTime();



        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            glowFrame.translateY(1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            glowFrame.translateX(-1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            glowFrame.translateY(-1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            glowFrame.translateX(1);
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
