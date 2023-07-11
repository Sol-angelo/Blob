// 
// Decompiled by Procyon v0.5.36
// 

package com.solace.main;

import com.solace.main.objects.MenuParticle;
import com.solace.main.util.*;
import com.solace.main.util.enums.ID;

import javax.sound.sampled.*;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Font;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.awt.Canvas;

public class Game extends Canvas implements Runnable
{
    public static final float WIDTH = 640.0f;
    public static final float HEIGHT = 480.0f;
    public static boolean ARROWKEYS = false;
    private Thread thread;
    private boolean running;
    private Random r;
    private Handler handler;
    private HUD hud;
    private Menu menu;
    private Spawn spawner;
    public Window window;
    public static BufferedImage sprite_sheet;
    public static BufferedImage boss_1;
    public static boolean paused;
    public static STATE gameState;
    public static int gameStateAsInt;
    public static boolean boss1Killed;
    public static boolean scrollDirection = true;
    public static boolean regen = false;
    public static int inGameSaveInt;
    
    public Game() {
        this.running = false;
        gameState = STATE.Menu;
        this.handler = new Handler(this);
        this.hud = new HUD(this, this.handler);
        this.menu = new Menu(this, this.handler, this.hud);
        this.addKeyListener(new KeyInput(this.handler, this));
        this.addMouseListener(this.menu);
        window = new Window(640.0f, 480.0f, "Blob", this, false);
        final BufferedImageLoader loader = new BufferedImageLoader();
        Game.sprite_sheet = loader.loadImage("/assets/textures/sprites.png");
        Game.boss_1 = loader.loadImage("/assets/textures/sprites.png");
        this.spawner = new Spawn(this.handler, this.hud, this);
        HUD.setHealth(100);
        HUD.setLevel(1);
        HUD.setScore(0);
        this.r = new Random();
        LoadSave.ReadOnLoad();
        if (gameState == STATE.Menu || gameState == STATE.Death || gameState == STATE.Help || gameState == STATE.Difficulty) {
            for (int i = 0; i < 20; ++i) {
                this.handler.addObject(new MenuParticle((float)this.r.nextInt(640), (float)this.r.nextInt(480), ID.MenuParticle, this.handler));
            }
        }
        //LoadSave.getFileByOS("test");
        System.out.println(System.getProperty("os.name"));
        //System.out.println(System.getProperty("user.home")+"/Library/Application Support/Solangelo/Blob/saves");
    }

    public static int getArrowkeysToInt() {
        if (ARROWKEYS) {
            return 1;
        } else {
            return 0;
        }
    }

    public void playSound(String name) {
        String soundFile = "res/sound/"+name+".wav";
        try {
            File f = new File(soundFile);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopSound(String name) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        String soundFile = "res/sound/"+name+".wav";
        File f = new File(soundFile);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.stop();
    }

    public static int getCurrentGameStateToInt() {
        if (gameState == STATE.Easy) {
            gameStateAsInt = 1;
        }
        if(gameState == STATE.Medium) {
            gameStateAsInt = 2;
        }
        if(gameState == STATE.Hard) {
            gameStateAsInt = 3;
        }
        return gameStateAsInt;
    }

    public static String getStateIntToString(int state) {
        String states = "";
        if (state == 1) {
            states = "Easy";
        }
        if (state == 2) {
            states = "Medium";
        }
        if (state == 3) {
            states = "Hard";
        }
        return states;
    }
    
    public synchronized void start() {
        (this.thread = new Thread(this)).start();
        this.running = true;
    }
    
    public synchronized void stop() {
        try {
            this.thread.join();
            this.running = false;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        final double ns = 1.0E9 / amountOfTicks;
        double delta = 0.0;
        long timer = System.currentTimeMillis();
        float frames = 0.0f;
        while (this.running) {
            final long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1.0) {
                this.tick();
                --delta;
            }
            if (this.running) {
                this.render();
            }
            ++frames;
            if (System.currentTimeMillis() - timer > 1000L) {
                timer += 1000L;
                System.out.println(frames);
                frames = 0.0f;
            }
        }
        this.stop();
    }
    
    private void tick() {
        if (!paused) {
            this.handler.tick();
            if (gameState == STATE.Easy) {
                this.hud.tick();
                spawner.difficulty = Spawn.Difficulty.Easy;
                this.spawner.tick();
            }
            else if (gameState == STATE.Medium) {
                this.hud.tick();
                this.spawner.difficulty = Spawn.Difficulty.Medium;
                this.spawner.tick();
            }
            else if (gameState == STATE.Hard) {
                this.hud.tick();
                this.spawner.difficulty = Spawn.Difficulty.Hard;
                this.spawner.tick();
            }
            else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.SelectGame || gameState == STATE.GameCreation || gameState == STATE.Settings || gameState == STATE.ConfirmReset) {
                this.menu.tick();
                HUD.setLevel(1);
                HUD.setScore(0);
            }
            else if (gameState == STATE.Death) {
                this.menu.tick();
            }
        } else {
            this.menu.tick();
        }
    }
    
    private void render() {
        final BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        final Graphics g = bs.getDrawGraphics();
        g.setColor(new Color(17, 17, 17));
        g.fillRect(0, 0, 740, 530);
        if (gameState == STATE.SaveloadIG) {
            this.menu.render(g);
        }
        this.handler.render(g);
        if (gameState == STATE.Easy || gameState == STATE.Medium || gameState == STATE.Hard) {
            this.hud.render(g);
        }
        else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.Death || gameState == STATE.Difficulty || gameState == STATE.Saveload || gameState == STATE.Settings || gameState == STATE.SelectGame || gameState == STATE.GameCreation || gameState == STATE.ConfirmReset) {
            this.menu.render(g);
        }
        if (paused) {
            if (gameState != STATE.SaveloadIG) {
                final Font fnt = new Font("arial", 1, 50);
                g.setFont(fnt);
                g.setColor(new Color(255, 64, 39));
                //g.drawString("PAUSED", 210, 120);

                this.menu.render(g);
            }
        }
        g.dispose();
        bs.show();
    }
    
    public static float clamp(float var, final float min, final float max) {
        if (var >= max) {
            var = max;
            return max;
        }
        if (var <= min) {
            var = min;
            return min;
        }
        return var;
    }
    
    public static void main(final String[] args) {
        new Game();
    }
    
    static {
        Game.paused = false;
    }
    
    public enum STATE
    {
        Menu, 
        Easy,
        Medium,
        Hard,
        Difficulty,
        Help, 
        Death,
        Saveload,
        SaveloadIG,
        Save1,
        Save2,
        Save3,
        Settings,
        Achievements,
        GameCreation,
        SelectGame,
        ConfirmReset
    }
}
