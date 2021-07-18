package com.tutorial.main;

import java.awt.*;
import java.util.Random;

public class HardEnemy extends GameObject{

    private Handler handler;

    private Random r = new Random();

    public HardEnemy(float x, float y, ID id, Handler handler) {
        super(x, y, id);

        this.handler = handler;

        velX = 5;
        velY = 5;

    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        if(y <= 0 || y >= Game.HEIGHT - 16) {
            if(velY < 0) velY = -(r.nextInt(7) * -1);
            else velY = (r.nextInt(7) * -1);
        }
        if(x <= 0 || x >= Game.WIDTH - 16) {
            if(velX < 0) velX = -(r.nextInt(7) * -1);
            else velX = (r.nextInt(7) * -1);
        }

        handler.addObject(new Trail((int)x, (int)y,ID.Trail, Color.yellow, 16, 16, 0.01f, handler));
    }

    @Override
    public void render(Graphics g) {

        g.setColor(Color.yellow);
        g.fillRect((int)x, (int)y,16,16);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 16, 16);
    }
}