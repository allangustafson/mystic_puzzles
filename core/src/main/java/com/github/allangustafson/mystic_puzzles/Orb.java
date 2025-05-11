package com.github.allangustafson.mystic_puzzles;

import com.badlogic.gdx.graphics.Texture;


public class Orb {
    int color;
    Texture orbTexture;
    public Orb(int color) {
        this.color = color;

        switch (color) {
            case 1:
                orbTexture = new Texture("Blue64_1.png");
                break;
            case 2:
                orbTexture = new Texture("Green64_1.png");
                break;
            case 3:
                orbTexture = new Texture("Purple64_1.png");
                break;
            case 4:
                orbTexture = new Texture("Red64_1.png");
                break;
            case 5:
                orbTexture = new Texture("White64_1.png");
                break;
            case 6:
                orbTexture = new Texture("Yellow64_1.png");
                break;
        }
    }

    //private void draw () {
    //
    //}
}
