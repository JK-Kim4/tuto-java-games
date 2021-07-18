package com.tutorial.main;

import org.lwjgl.Sys;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.Random;

public class Game extends Canvas implements Runnable{

    public static final int WIDTH = 640, HEIGHT = WIDTH  / 12 * 9;
    private Thread thread;
    private boolean running = false;
    public static boolean paused = false;

    //0 = normal
    //1 = hard
    public int diff = 0;

    private Handler handler;
    private Random r;
    private HUD hud;
    private Spawn spawner;
    private Menu menu;

    public enum STATE{
      Menu,
      Help,
      Game,
        Select,
      End
    };

    public static BufferedImage sprite_sheet;

    public static STATE gameState = STATE.Menu;
    //생성자
    public Game(){

        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            sprite_sheet = loader.loadImage("/sprite_sheet.png");
            System.out.println("loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }

        handler = new Handler();
        hud = new HUD();
        menu = new Menu(this, handler, hud);
        this.addKeyListener(new KeyInput(handler, this));
        this.addMouseListener(menu);

        AudioPlayer.load();

        AudioPlayer.getMusic("music").loop();

        new Window(WIDTH, HEIGHT, "Let's Build a Game", this);

        hud = new HUD();
        spawner = new Spawn(handler, hud, this);

        r = new Random();

        if(gameState == STATE.Game){
            handler.addObject(new Player(WIDTH/2 -32, HEIGHT/2 -32, ID.Player, handler));
            handler.addObject(new BasicEnemy(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.BasicEnemy, handler));
        }else{
            for(int i = 0; i < 10; i++){
                handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MenuParticle, handler));
            }
        }

        //handler.addObject(new BossEnemy((Game.WIDTH / 2) - 48, -120, ID.BossEnemy, handler));

    }

    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta--;
            }
            if(running) render();
            frames++;
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                //System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick(){

        if(gameState == STATE.Game){
            if(!paused){
                hud.tick();
                spawner.tick();
                handler.tick();
                if(HUD.HEALTH <= 0){
                    HUD.HEALTH = 100;
                    gameState = STATE.End;
                    handler.clearEnemy();
                    for(int i = 0; i < 10; i++){
                        handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MenuParticle, handler));
                    }
                }
            }
        }else if(gameState == STATE.Menu || gameState == STATE.End || gameState == STATE.Select){
            menu.tick();
            handler.tick();
        }


    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0,0, WIDTH, HEIGHT);

        handler.render(g);

        if(paused){
            g.setColor(Color.WHITE);
            g.drawString("PAUSED", 100, 100);
        }

        if(gameState == STATE.Game){
            hud.render(g);
        }else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End || gameState == STATE.Select) {
            menu.render(g);
        }

        g.dispose();
        bs.show();

    }

    public static float clamp (float var, float min, int max){
        if(var >= max)
            return var = max;
        else if (var <= min)
            return var = min;
        else
            return var;
    }

    public static void main(String args[]){
        new Game();
    }


}
