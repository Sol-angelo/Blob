// 
// Decompiled by Procyon v0.5.36
// 

package com.solace.main.objects;

import com.solace.main.Game;
import com.solace.main.util.*;
import com.solace.main.util.enums.ID;
import com.solace.main.util.enums.PowerUps;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Player extends GameObject
{
    Random r;
    Handler handler;
    HUD hud;
    private Game game;
    private BufferedImage player_image;
    public float speed = 1;
    public int puLength;
    public PowerUps powerUps;
    
    public Player(final float x, final float y, final ID id, final Handler handler, final Game game) {
        super(x, y, id);
        this.r = new Random();
        this.handler = handler;
        this.game = game;
        this.hud = new HUD(game, handler);
        final SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
        this.player_image = ss.grabImage(1, 1, 32, 32);
    }
    
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)this.x, (int)this.y, 32, 32);
    }
    
    @Override
    public void tick() {
        this.x += this.velX * speed;
        this.y += this.velY * speed;
        this.x = Game.clamp(this.x, 0.0f, 600.0f);
        this.y = Game.clamp(this.y, 0.0f, 420.0f);
        if (hud.getLevel() == 20) {
            this.x = Game.clamp(this.x, 64.0f, 548.0f);
            this.y = Game.clamp(this.y, 64.0f, 448.0f);
        }
        if (this.id == ID.Player) {
            this.handler.addObject(new Trail(this.x, this.y, ID.Trail, 32.0f, 32.0f, 0.09f, this.handler, this.player_image));
        }
        if (this.powerUps != PowerUps.None) {
            if (puLength > 0) {
                if (this.powerUps == PowerUps.Speed) {
                    if (puLength < speed * 10) {
                        speed = 1 + (float) puLength / 10;
                    }
                }
            } else {
                this.powerUps = PowerUps.None;
            }
        }
        if (puLength >= 1) {
            puLength--;
        }
        this.collision();
    }
    
    private void collision() {
        for (int i = 0; i < this.handler.object.size(); ++i) {
            final GameObject tempObject = this.handler.object.get(i);
            if ((tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.FastEnemy || tempObject.getId() == ID.TargetEnemy) && this.getBounds().intersects(tempObject.getBounds())) {
                if (powerUps != PowerUps.Kill) {
                    if (Game.gameState == Game.STATE.Easy) {
                        --HUD.HEALTH;
                    } else if (Game.gameState == Game.STATE.Medium) {
                        HUD.HEALTH -= 2;
                    } else if (Game.gameState == Game.STATE.Hard) {
                        HUD.HEALTH -= 4;
                    }
                } else {
                    handler.removeObject(tempObject);
                }
            } else if (tempObject.getId() == ID.SpeedPU && this.getBounds().intersects(tempObject.getBounds())) {
                if (Game.gameState == Game.STATE.Easy) {
                    speed += 1.5;
                    puLength = 300;
                    this.powerUps = PowerUps.Speed;
                    handler.removeObject(tempObject);
                }
                else if (Game.gameState == Game.STATE.Medium) {
                    speed += 1;
                    puLength = 200;
                    this.powerUps = PowerUps.Speed;
                    handler.removeObject(tempObject);
                }
                else if (Game.gameState == Game.STATE.Hard) {
                    speed += .5;
                    puLength = 100;
                    this.powerUps = PowerUps.Speed;
                    handler.removeObject(tempObject);
                }
            }
            else if (tempObject.getId() == ID.KillPU && this.getBounds().intersects(tempObject.getBounds())) {
                if (Game.gameState == Game.STATE.Easy) {
                    puLength = 300;
                    this.powerUps = PowerUps.Kill;
                    handler.removeObject(tempObject);
                }
                else if (Game.gameState == Game.STATE.Medium) {
                    puLength = 200;
                    this.powerUps = PowerUps.Kill;
                    handler.removeObject(tempObject);
                }
                else if (Game.gameState == Game.STATE.Hard) {
                    puLength = 100;
                    this.powerUps = PowerUps.Kill;
                    handler.removeObject(tempObject);
                }
            }
            if (tempObject.getId() == ID.Boss1Enemy && this.getBounds().intersects(tempObject.getBounds())) {
                HUD.HEALTH -= 1000.0f;
            }
        }
    }
    
    @Override
    public void render(final Graphics g) {
        g.drawImage(this.player_image, (int)this.x, (int)this.y, null);
    }
}
