package com.github.allangustafson.mystic_puzzles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;




public class Board {
    private final int ROWS = 5;
    private final int COLS = 6;
    public Orb[][] orbArray = new Orb[ROWS][COLS];
    public Orb temp;

    public Board () {
        initializeBoard();
    }

    public void initializeBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int color = MathUtils.random(1,6);
                this.orbArray[row][col] = new Orb(color);
            }
        }
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (hasMatchAt(row,col)) {
                    this.orbArray[row][col] = new Orb(MathUtils.random(1,6));
                }
            }
        }

    }

    public void swap(float originRow, float originCol, float destinationRow, float destinationCol) {
        int iOriginRow = (int)(Math.ceil(originRow) - 2);
        int iOriginCol = (int)(Math.ceil(originCol) - 5);
        int iDestinationRow = MathUtils.clamp((int)(Math.ceil(destinationRow) - 2), 0, ROWS -1);
        int iDestinationCol = MathUtils.clamp((int)(Math.ceil(destinationCol) - 5), 0, COLS -1);

        temp = orbArray[iOriginRow][iOriginCol];
        orbArray[iOriginRow][iOriginCol] = orbArray[iDestinationRow][iDestinationCol];
        orbArray[iDestinationRow][iDestinationCol] = temp;

        if (hasMatchAt(iOriginRow,iOriginCol) || hasMatchAt(iDestinationRow, iDestinationCol)) {
            System.out.println("matched");
        } else {
            temp = orbArray[iOriginRow][iOriginCol];
            orbArray[iOriginRow][iOriginCol] = orbArray[iDestinationRow][iDestinationCol];
            orbArray[iDestinationRow][iDestinationCol] = temp;
        }
    }

    public boolean hasMatchAt(int row, int col) {
        int orbColor = orbArray[row][col].color;
        int horzMatch = 1;
        int vertMatch = 1;

        for (int i = row - 1; i >= 0 && orbArray[i][col].color == orbColor; i--) {
            vertMatch++;
        }
        for (int i = row + 1; i < ROWS && orbArray[i][col].color == orbColor; i++) {
            vertMatch++;
        }

        for (int i = col - 1; i >= 0 && orbArray[row][i].color == orbColor; i--) {
            horzMatch++;
        }
        for (int i = col + 1; i < COLS && orbArray[row][i].color == orbColor; i++) {
            horzMatch++;
        }

        System.out.println(horzMatch + "," + vertMatch);

        return horzMatch >= 3 || vertMatch >= 3;
    }


    public void draw (SpriteBatch batch) {
        for (int row = 0; row < (ROWS); row++) {
            for (int col = 0; col < (COLS); col++) {
                Orb orb = orbArray[row][col];
                batch.draw(orb.orbTexture, col +5.05f, row +2.05f, .9f, .9f);
            }
        }
    }
}
