// 
// Decompiled by Procyon v0.5.36
// 

package com.solace.main.util;

import com.solace.main.Game;

import java.awt.Rectangle;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class TrailS extends GameObject
{
    private Handler handler;
    private float alpha;
    private float life;
    private Color color;
    private float width;
    private float height;
    private BufferedImage img;
    
    public TrailS(final float x, final float y, final ID id, final Color color, final float width, final float height, final float life, final Handler handler) {
        super(x, y, id);
        this.alpha = 1.0f;
        this.width = width;
        this.height = height;
        this.life = life;
        this.handler = handler;
        this.color = color;
        final SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
    }
    
    @Override
    public void render(final Graphics g) {
        final Graphics2D g2d = (Graphics2D)g;
        g2d.setComposite(this.makeTransparent(this.alpha));
        g.setColor(this.color);
        g.fillRect((int)this.x, (int)this.y, (int)this.width, (int)this.height);
        g2d.setComposite(this.makeTransparent(1.0f));
    }
    
    @Override
    public void tick() {
        if (this.alpha > this.life) {
            this.alpha -= this.life - 1.0E-5f;
        }
        else {
            this.handler.removeObject(this);
        }
    }
    
    private AlphaComposite makeTransparent(final float alpha) {
        final float type = 3.0f;
        return AlphaComposite.getInstance((int)type, alpha);
    }
    
    @Override
    public Rectangle getBounds() {
        return null;
    }
}
