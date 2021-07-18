package com.tutorial.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Menu extends MouseAdapter {

    private Game game;
    private Handler handler;
    private Random r = new Random();
    private HUD hud;

    public Menu(Game game, Handler handler, HUD hud){
        this.game = game;
        this.handler = handler;
        this.hud = hud;

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if(game.gameState == Game.STATE.Menu){
            //Play button
            if(mouseOver(mx, my, 210, 150, 200, 64)){
//                game.gameState = Game.STATE.Game;
//                handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.Player, handler));
//                handler.clearEnemy();
//                handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.BasicEnemy, handler));
                game.gameState = Game.STATE.Select;

                AudioPlayer.getSound("menu_sound").play();
                return;
            }

            //Help button
            if(mouseOver(mx, my, 210, 250, 200, 64)){
                game.gameState = Game.STATE.Help;
                AudioPlayer.getSound("menu_sound").play();
            }

            //quit button
            if(mouseOver(mx, my, 210, 350, 200, 64)){
                System.exit(1);
                AudioPlayer.getSound("menu_sound").play();
            }

        }

        if(game.gameState == Game.STATE.Select){
            //Normal button
            if(mouseOver(mx, my, 210, 150, 200, 64)){
                game.gameState = Game.STATE.Game;
                handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.Player, handler));
                handler.clearEnemy();
                handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.BasicEnemy, handler));

                game.diff = 0;

                AudioPlayer.getSound("menu_sound").play();
            }

            //Hard button
            if(mouseOver(mx, my, 210, 250, 200, 64)){
                game.gameState = Game.STATE.Game;
                handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.Player, handler));
                handler.clearEnemy();
                handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.BasicEnemy, handler));

                game.diff = 1;

                AudioPlayer.getSound("menu_sound").play();
            }

            //Back button
            if(mouseOver(mx, my, 210, 350, 200, 64)){
                game.gameState = Game.STATE.Menu;
                AudioPlayer.getSound("menu_sound").play();
                return;
            }

        }

        if(game.gameState == Game.STATE.Help){
            if(mouseOver(mx, my, 210, 350, 200, 64)){
                game.gameState = Game.STATE.Menu;
                AudioPlayer.getSound("menu_sound").play();
                return;
            }
        }

        // restart button
        if(game.gameState == Game.STATE.End){
            if(mouseOver(mx, my, 210, 350, 200, 64)){

                game.gameState = Game.STATE.Menu;
                hud.setLevel(1);
                hud.setScore(0);
//                handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.Player, handler));
//                handler.clearEnemy();
//                handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.BasicEnemy, handler));
                AudioPlayer.getSound("menu_sound").play();

            }
        }
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
        if(mx > x && mx < x + width){
            if(my > y && my < y + height){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public void tick(){

    }

    public void render(Graphics g){
        if(game.gameState == Game.STATE.Menu){
            Font fnt = new Font("arial",1, 50);
            Font fnt2 = new Font("arial",1, 30);

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Menu", 240, 70);

            g.setFont(fnt2);
            g.setColor(Color.white);
            g.drawRect(210, 150, 200, 64);
            g.drawString("Play", 220, 190);


            g.setColor(Color.white);
            g.drawRect(210, 250, 200, 64);
            g.drawString("Help", 220, 290);

            g.setColor(Color.white);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Quit", 220, 390);
        }else if(game.gameState == Game.STATE.Help){
            Font fnt = new Font("arial",1, 50);
            Font fnt2 = new Font("arial",1, 30);

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Help", 240, 70);

            g.setFont(fnt2);
            g.drawString(" USE WASD keys to move player and dodge enemies", 10, 200);

            g.setFont(fnt2);
            g.setColor(Color.white);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Back", 220,390);

        }else if(game.gameState == Game.STATE.End){
            Font fnt = new Font("arial",1, 50);
            Font fnt2 = new Font("arial",1, 30);
            Font fnt3 = new Font("arial",1, 20);

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Game Over", 190, 70);

            g.setFont(fnt3);
            g.drawString(" You lost with a score of : " + hud.getScore(), 175, 200);

            g.setFont(fnt2);
            g.setColor(Color.white);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Try Again", 245,390);

        }else if(game.gameState == Game.STATE.Select){

            Font fnt = new Font("arial",1, 50);
            Font fnt2 = new Font("arial",1, 30);

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("SELECT DIFFICULTY", 240, 70);

            g.setFont(fnt2);
            g.setColor(Color.white);
            g.drawRect(210, 150, 200, 64);
            g.drawString("Normal", 220, 190);

            g.setColor(Color.white);
            g.drawRect(210, 250, 200, 64);
            g.drawString("Hard", 220, 290);

            g.setColor(Color.white);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Back", 220, 390);
        }

    }

}
