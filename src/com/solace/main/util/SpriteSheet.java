// 
// Decompiled by Procyon v0.5.36
// 

package com.solace.main.util;

import java.awt.image.BufferedImage;

public class SpriteSheet
{
    private BufferedImage sprite;
    
    public SpriteSheet(final BufferedImage ss) {
        this.sprite = ss;
    }
    
    public BufferedImage grabImage(final int col, final int row, final int width, final int height) {
        final BufferedImage img = this.sprite.getSubimage(row * 32 - 32, col * 32 - 32, width, height);
        return img;
    }
}
