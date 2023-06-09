// 
// Decompiled by Procyon v0.5.36
// 

package com.solace.main.objects.enemies;

import com.solace.main.*;
import com.solace.main.util.GameObject;
import com.solace.main.util.Trail;
import com.solace.main.util.Handler;
import com.solace.main.util.enums.ID;
import com.solace.main.util.SpriteSheet;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class BasicEnemy extends GameObject
{
    private Handler handler;
    private Game game;
    private BufferedImage benemy_image;
    
    public BasicEnemy(final float x, final float y, final ID id, final Handler handler, final Game game) {
        super(x, y, id);
        this.handler = handler;
        this.game = game;
        final SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
        this.benemy_image = ss.grabImage(1, 2, 21, 21);
        if (game.gameState == Game.STATE.Easy) {
            this.velX = 3.0f;
            this.velY = 3.0f;
        }
        else if (game.gameState == Game.STATE.Medium) {
            this.velX = 5.0f;
            this.velY = 5.0f;
        }
        else if (game.gameState == Game.STATE.Hard) {
            this.velX = 6.0f;
            this.velY = 6.0f;
        }
    }
    
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)this.x, (int)this.y, 21, 21);
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
        this.handler.addObject(new Trail(this.x, this.y, ID.Trail, 20.0f, 20.0f, 0.09f, this.handler, this.benemy_image));
    }
    
    @Override
    public void render(final Graphics g) {
        g.drawImage(this.benemy_image, (int)this.x, (int)this.y, null);
    }
}
