// 
// Decompiled by Procyon v0.5.36
// 

package com.solace.main.objects;

import com.solace.main.Game;
import com.solace.main.util.*;

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
        this.x += this.velX;
        this.y += this.velY;
        this.x = Game.clamp(this.x, 0.0f, 600.0f);
        this.y = Game.clamp(this.y, 0.0f, 420.0f);
        if (hud.getLevel() == 20) {
            this.x = Game.clamp(this.x, 64.0f, 548.0f);
            this.y = Game.clamp(this.y, 64.0f, 448.0f);
        }
        if (this.id == ID.Player) {
            this.handler.addObject(new Trail(this.x, this.y, ID.Trail, 32.0f, 32.0f, 0.09f, this.handler, this.player_image));
        }
        this.collision();
    }
    
    private void collision() {
        for (int i = 0; i < this.handler.object.size(); ++i) {
            final GameObject tempObject = this.handler.object.get(i);
            if ((tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.FastEnemy || tempObject.getId() == ID.TargetEnemy || tempObject.getId() == ID.BasicEnemyT) && this.getBounds().intersects(tempObject.getBounds())) {
                if (Game.gameState == Game.STATE.Easy) {
                        --HUD.HEALTH;
                }
                else if (Game.gameState == Game.STATE.Medium) {
                    HUD.HEALTH -= 2;
                }
                else if (Game.gameState == Game.STATE.Hard) {
                    HUD.HEALTH -= 4;
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
