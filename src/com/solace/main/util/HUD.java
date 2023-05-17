// 
// Decompiled by Procyon v0.5.36
// 

package com.solace.main.util;

import com.solace.main.Game;
import com.solace.main.objects.MenuParticle;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import static com.solace.main.Game.gameState;

public class HUD
{
    private Game game;
    private final Handler handler;
    public static float HEALTH = 100f;
    private float greenValue;
    private float greenValueT;
    private Random r;
    private static int score;
    private static int level;
    private int timerh;
    private static int levelTest;
    
    public HUD(final Game game, final Handler handler) {
        this.greenValue = 255.0f;
        score = 0;
        level = 1;
        this.timerh = 0;
        this.game = game;
        this.handler = handler;
    }
    
    public void tick() {
        HUD.HEALTH = Game.clamp(HUD.HEALTH, 0.0f, 100.0f);
        this.greenValue = (float)(int)Game.clamp(this.greenValue, 0.0f, 255.0f);
        this.greenValueT = (float)(int)Game.clamp(this.greenValueT, 0.0f, 255.0f);
        this.greenValue = (float)((int)HUD.HEALTH * 2);
        ++this.timerh;
        if (gameState == Game.STATE.Difficulty) {
                for (int i = 0; i < 20; ++i) {
                    HUD.HEALTH = 100.0f;
                }
        }
        if (gameState == Game.STATE.Easy && this.timerh >= 100) {
            if (HEALTH < 100) {
                HUD.HEALTH += 2.0f;
            }
            this.timerh = 0;
        }
        else if (gameState == Game.STATE.Medium && this.timerh >= 150) {
            if (HEALTH < 100) {
                ++HUD.HEALTH;
            }
            this.timerh = 0;

        }
        else if (gameState == Game.STATE.Hard && this.timerh >= 200) {
            if (HEALTH < 100) {
                ++HUD.HEALTH;
            }
            this.timerh = 0;
        }
        this.r = new Random();
        ++score;
        if (HUD.HEALTH <= 0.0f) {
            gameState = Game.STATE.Death;
            this.handler.clearAll();
            if (gameState == Game.STATE.Menu || gameState == Game.STATE.Death || gameState == Game.STATE.Help) {
                for (int i = 0; i < 20; ++i) {
                    this.handler.addObject(new MenuParticle((float)this.r.nextInt(640), (float)this.r.nextInt(480), ID.MenuParticle, this.handler));
                    HUD.HEALTH = 100.0f;
                }
            }
        }
    }
    
    public void render(final Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(15, 15, 200, 32);
        g.setColor(new Color(75, (int)this.greenValue, 0));
        g.fillRect(15, 15, (int)HUD.HEALTH * 2, 32);
        g.setColor(Color.WHITE);
        g.drawRect(15, 15, 200, 32);
        g.drawString(("Score: "+score), 15, 64);
        g.drawString(("Level: "+level), 15, 80);
    }
    
    public static void setScore(final int tScore) {
        score = tScore;
    }
    public int getScore() {
        return score;
    }

    public static int getStaticScore() {
        return score;
    }

    public static float getHealth(){
        return HEALTH;
    }

    public static void setHealth(final int tHealth) {
        HEALTH = (float) tHealth;
    }
    public int getLevel() {
        return level;
    }
    public static int getStaticLevel() {
        if (score <= 499) {
            levelTest = 1;
        } else if (score <= 999) {
            levelTest = 2;
        } else if (score <= 1499) {
            levelTest = 3;
        } else if (score <= 1999) {
            levelTest = 4;
        } else if (score <= 2499) {
            levelTest = 5;
        } else if (score <= 2999) {
            levelTest = 6;
        } else if (score <= 3499) {
            levelTest = 7;
        } else if (score <= 3999) {
            levelTest = 8;
        } else if (score <= 4499) {
            levelTest = 9;
        } else if (score <= 4499 + 2180) {
            levelTest = 10;
        } else if (score <= 4999 + 2180) {
            levelTest = 11;
        } else if (score <= 5499 + 2180) {
            levelTest = 12;
        } else if (score <= 5999 + 2180) {
            levelTest = 13;
        } else if (score <= 6499 + 2180) {
            levelTest = 14;
        } else if (score <= 6999 + 2180) {
            levelTest = 15;
        }
        return levelTest;
    }
    
    public static void setLevel(final int tLevel) {
        level = tLevel;
    }
}
