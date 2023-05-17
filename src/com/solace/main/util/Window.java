// 
// Decompiled by Procyon v0.5.36
// 

package com.solace.main.util;

import com.solace.main.Game;

import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class Window extends Canvas {
    private BufferedImage img;
    
    public Window(final float width, final float height, final String title, final Game game, Boolean hasInputText) {
        final JFrame frame = new JFrame(title);
        frame.setPreferredSize(new Dimension((int)width, (int)height));
        frame.setMaximumSize(new Dimension((int)width + 100, (int)height + 50));
        frame.setMinimumSize(new Dimension((int)width - 10, (int)height));
        final ImageIcon logo = new ImageIcon(this.getClass().getClassLoader().getResource("icon.png"));
        final InputStream is = this.getClass().getResourceAsStream("/icon.png");
        try {
            this.img = ImageIO.read(is);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        final Taskbar taskbar = Taskbar.getTaskbar();
        try {
            taskbar.setIconImage(this.img);
        }
        catch (UnsupportedOperationException e2) {
            System.out.println("the os does not support taskbar.setIconImage");
        }
        catch (SecurityException e3) {
            System.out.println("Security Exception");
        }
        frame.setIconImage(logo.getImage());
        frame.setDefaultCloseOperation(3);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        if (hasInputText) {

        } else {
            frame.add(game);
        }
        frame.pack();
        frame.setVisible(true);
        game.start();
    }
}
