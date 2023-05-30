// 
// Decompiled by Procyon v0.5.36
// 

package com.solace.main.objects;

import com.solace.main.util.GameObject;
import com.solace.main.util.Handler;
import com.solace.main.util.enums.ID;
import com.solace.main.util.TrailS;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;
import java.util.Random;

public class MenuParticle extends GameObject
{
    private Handler handler;
    Random r;
    private int red;
    private int blue;
    private int green;
    private int alpha;
    private Color col;
    
    public MenuParticle(final float x, final float y, final ID id, final Handler handler) {
        super(x, y, id);
        this.r = new Random();
        this.red = this.r.nextInt(105);
        this.blue = this.r.nextInt(105);
        this.green = this.r.nextInt(105);
        this.alpha = this.r.nextInt(205);
        this.handler = handler;
        this.velX = (float)this.r.nextInt(7);
        this.velY = (float)this.r.nextInt(7);
        if (this.velX == 0.0f) {
            this.velX = 1.0f;
        }
        if (this.velY == 0.0f) {
            this.velY = 1.0f;
        }
        this.col = new Color(this.red + 150, this.green + 150, this.blue + 150);
    }
    
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)this.x, (int)this.y, 16, 16);
    }
    
    @Override
    public void tick() {
        this.x += this.velX;
        this.y += this.velY;
        if (this.y <= 0.0f || this.y >= 448.0f) {
            this.velY *= -1.0f;
        }
        if (this.x <= 0.0f || this.x >= 624.0f) {
            this.velX *= -1.0f;
        }
        this.handler.addObject(new TrailS(this.x, this.y, ID.Trail, this.col, 16.0f, 16.0f, 0.08f, this.handler));
    }
    
    @Override
    public void render(final Graphics g) {
        g.setColor(this.col);
        g.fillRect((int)this.x, (int)this.y, 16, 16);
    }
}
