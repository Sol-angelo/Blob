// 
// Decompiled by Procyon v0.5.36
// 

package com.solace.main.objects.enemies.bosses;

import com.solace.main.*;
import com.solace.main.util.GameObject;
import com.solace.main.util.Handler;
import com.solace.main.util.ID;
import com.solace.main.util.SpriteSheet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Boss1Enemy extends GameObject
{
    private Handler handler;
    Random r;
    private BufferedImage boss_1;
    public int timer;
    
    public Boss1Enemy(final float x, final float y, final ID id, final Handler handler) {
        super(x, y, id);
        this.r = new Random();
        this.timer = 60;
        this.handler = handler;
        final SpriteSheet ss = new SpriteSheet(Game.boss_1);
        this.boss_1 = ss.grabImage(1, 1, 96, 96);
        this.velX = 0.0f;
        this.velY = 2.0f;
    }
    
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)this.x, (int)this.y, 96, 96);
    }
    
    @Override
    public void tick() {
        this.x += this.velX;
        this.y += this.velY;
        --this.timer;
        if (this.timer <= 0) {
            this.velY = 0.0f;
        }
        if (this.timer <= -50) {
            if (this.velX == 0.0f) {
                this.velX = 2.0f;
            }
            final int spawn = this.r.nextInt(10);
            if (spawn == 0) {
                this.handler.addObject(new Boss1EnemyBullet((float)((int)this.x + 48), (float)((int)this.y + 48), ID.BasicEnemy, this.handler));
            }
        }
        if (this.timer <= -595 && this.velX == 2.0f) {
            this.velX = 0.0f;
        }
        if (this.timer <= -740 && this.velX == 0.0f) {
            this.velX = 4.0f;
        }
        if (this.timer <= -1285 && this.velX == 4.0f) {
            this.velX = 0.0f;
        }
        if (this.timer <= -1430 && this.velX == 0.0f) {
            this.velX = 8.0f;
        }
        if (this.timer <= -1985 && this.velX == 8.0f) {
            this.velX = 0.0f;
        }
        if (this.timer <= -2035) {
            this.velY = -2.0f;
        }
        if (this.x <= 0.0f || this.x >= 544.0f) {
            this.velX *= -1.0f;
        }
    }
    
    @Override
    public void render(final Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillRect((int)this.x, (int)this.y, 96, 96);
    }
}
