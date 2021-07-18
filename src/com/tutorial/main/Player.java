package com.tutorial.main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Player extends GameObject{

    Random r = new Random();
    Handler handler;

    private BufferedImage player_image;

    public Player(float x, float y, ID id, Handler handler){
        super(x, y, id);
        this.handler = handler;

        SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);

        //sprite_sheet에서 player image load
        player_image = ss.grabImage(1,1, 32, 32);
        System.out.println("player_image ===> "+player_image.getData());

    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        x = Game.clamp(x, 0, Game.WIDTH -38);
        y = Game.clamp(y, 0, Game.HEIGHT - 61);

//        handler.addObject(new Trail((int)x, (int)y, ID.Trail, Color.white, 32, 32, 0.05f, handler));

        collision();
    }

    private void collision(){
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.FastEnemy || tempObject.getId() == ID.SmartEnemy
                || tempObject.getId() == ID.BossEnemy){
                if(getBounds().intersects(tempObject.getBounds())){
                    //collision code
                    HUD.HEALTH -= 2;
                }

            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(player_image, (int)x, (int)y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32);
    }
}
