package com.github.allangustafson.mystic_puzzles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;




public class Board {
    private final int ROWS = 5;
    private final int COLS = 6;
    Orb[][] orbArray = new Orb[ROWS][COLS];

    public Board () {
        initializeBoard();
    }

    public void initializeBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int color = MathUtils.random(1,6);
                orbArray[row][col] = new Orb(color);
            }
        }
    }



    public void draw (SpriteBatch batch) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Orb orb = orbArray[row][col];
                batch.draw(orb.orbTexture, col +5.05f, row +2.05f, .9f, .9f);
            }
        }
    }
}
