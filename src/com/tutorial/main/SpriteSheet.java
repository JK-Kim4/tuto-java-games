package com.tutorial.main;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class SpriteSheet {

    private BufferedImage sprite;

    public SpriteSheet(BufferedImage ss){
        this.sprite = ss;
    }

    public BufferedImage grabImage(int col, int row, int width, int height){
        System.out.println("grabImage =====> width : "+width + ", height : "+ height);
        BufferedImage img = sprite.getSubimage((row * 32) - 32, (col * 32) - 32, width, height);
        return img;
    }
}
