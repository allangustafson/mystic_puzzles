package com.github.allangustafson.mystic_puzzles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;


public class Board {
    private final int ROWS = 5;
    private final int COLS = 6;
    public Orb[][] orbArray = new Orb[ROWS][COLS];
    public Orb temp;
    public List<Orb> match = new ArrayList<>();
    public List<Orb> horzList = new ArrayList<>();
    public List<Orb> vertList = new ArrayList<>();


    public Board () {
        initializeBoard();
    }

    public void initializeBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int color = MathUtils.random(1,6);
                this.orbArray[row][col] = new Orb(color, row, col);
            }
        }
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                while (hasMatchAt(row,col)) {
                    this.orbArray[row][col] = new Orb(MathUtils.random(1,6), row, col);
                }
            }
        }
        match.clear();
    }

    public void swap(float originRow, float originCol, float destinationRow, float destinationCol) {
        int iOriginRow = (int)(Math.ceil(originRow) - 2);
        int iOriginCol = (int)(Math.ceil(originCol) - 5);
        int iDestinationRow = MathUtils.clamp((int)(Math.ceil(destinationRow) - 2), 0, ROWS -1);
        int iDestinationCol = MathUtils.clamp((int)(Math.ceil(destinationCol) - 5), 0, COLS -1);

        temp = orbArray[iOriginRow][iOriginCol];
        temp.x = iDestinationRow;
        temp.y = iDestinationCol;
        orbArray[iOriginRow][iOriginCol] = orbArray[iDestinationRow][iDestinationCol];
        orbArray[iOriginRow][iOriginCol].x = iOriginRow;
        orbArray[iOriginRow][iOriginCol].y = iOriginCol;
        orbArray[iDestinationRow][iDestinationCol] = temp;

        if (hasMatchAt(iOriginRow,iOriginCol) || hasMatchAt(iDestinationRow, iDestinationCol)) {
            removeMatches(match);
            match.clear();
            fall();
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
            vertList.add(orbArray[i][col]);
            vertMatch++;
        }
        for (int i = row + 1; i < ROWS && orbArray[i][col].color == orbColor; i++) {
            vertList.add(orbArray[i][col]);
            vertMatch++;
        }

        for (int i = col - 1; i >= 0 && orbArray[row][i].color == orbColor; i--) {
            horzList.add(orbArray[row][i]);
            horzMatch++;
        }
        for (int i = col + 1; i < COLS && orbArray[row][i].color == orbColor; i++) {
            horzList.add(orbArray[row][i]);
            horzMatch++;
        }
        if (horzMatch < 3) horzList.clear();
        if (vertMatch < 3) vertList.clear();
        if (horzMatch >= 3 || vertMatch >= 3) {
            match.add(orbArray[row][col]);
            match.addAll(horzList);
            horzList.clear();
            match.addAll(vertList);
            vertList.clear();
        }

        return horzMatch >= 3 || vertMatch >= 3;
    }

    public void removeMatches(List<Orb> match) {
        for (Orb orb : match) {
            orbArray[orb.x][orb.y] = null;
        }
    }

    public void fall() {
        for (int x = 0; x < COLS; x++) {
            int spaceY = -0;
            boolean space = false;

            int y = 0;
            while (y < ROWS) {
                Orb orb = orbArray[y][x];
                if (space) {
                    if (orb!=null) {
                        orbArray[spaceY][x] = orb;
                        orb.y = spaceY;
                        orbArray[y][x] = null;

                        space = false;
                        y = spaceY;
                        spaceY = 0;
                    }
                } else if (orb==null) {
                    space = true;
                    if (spaceY==0) {
                        spaceY = y;
                    }
                }
                y++;
            }
        }
    }



    public void draw (SpriteBatch batch) {
        for (int row = 0; row < (ROWS); row++) {
            for (int col = 0; col < (COLS); col++) {
                Orb orb = orbArray[row][col];
                if (orb != null) {
                    batch.draw(orb.orbTexture, col + 5.05f, row + 2.05f, .9f, .9f);
                }
            }
        }
    }
}
