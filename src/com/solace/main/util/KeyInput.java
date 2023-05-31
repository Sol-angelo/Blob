// 
// Decompiled by Procyon v0.5.36
// 

package com.solace.main.util;

import com.solace.main.Game;
import com.solace.main.util.enums.ID;
import com.solace.main.util.enums.Selected;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class KeyInput extends KeyAdapter
{
    private Handler handler;
    private boolean[] keyDown;
    private Game game;
    
    public KeyInput(final Handler handler, final Game game) {
        this.keyDown = new boolean[8];
        this.handler = handler;
        this.game = game;
        this.keyDown[0] = false;
        this.keyDown[1] = false;
        this.keyDown[2] = false;
        this.keyDown[3] = false;
        this.keyDown[4] = false;
        this.keyDown[5] = false;
        this.keyDown[6] = false;
        this.keyDown[7] = false;
    }
    
    @Override
    public void keyPressed(final KeyEvent e) {
        final float key = (float) e.getKeyCode();
        if (Game.gameState == Game.STATE.SelectGame) {
            if (Menu.selected != Selected.None) {
                if (key == KeyEvent.VK_DELETE || key == KeyEvent.VK_B) {
                    if (Menu.selected == Selected.Game1) {
                        LoadSave.DeleteFile("saves/savedata"+(Menu.page-1));
                    } else if (Menu.selected == Selected.Game2) {
                        LoadSave.DeleteFile("saves/savedata"+Menu.page);
                    } else if (Menu.selected == Selected.Game3) {
                        LoadSave.DeleteFile("saves/savedata"+(Menu.page+1));
                    }
                }
            }
        } else {
            if (!Game.ARROWKEYS) {
                for (int i = 0; i < this.handler.object.size(); ++i) {
                    final GameObject tempObject = this.handler.object.get(i);
                    if (tempObject.getId() == ID.Player) {
                        if (key == KeyEvent.VK_W) {
                            tempObject.setVelY(-5.0f);
                            this.keyDown[0] = true;
                        }
                        if (key == KeyEvent.VK_S) {
                            tempObject.setVelY(5.0f);
                            this.keyDown[1] = true;
                        }
                        if (key == KeyEvent.VK_D) {
                            tempObject.setVelX(5.0f);
                            this.keyDown[2] = true;
                        }
                        if (key == KeyEvent.VK_A) {
                            tempObject.setVelX(-5.0f);
                            this.keyDown[3] = true;
                        }
                    }
                }
            } else {
                for (int i = 0; i < this.handler.object.size(); ++i) {
                    final GameObject tempObject = this.handler.object.get(i);
                    if (tempObject.getId() == ID.Player) {
                        if (key == KeyEvent.VK_UP) {
                            tempObject.setVelY(-5.0f);
                            this.keyDown[0] = true;
                        }
                        if (key == KeyEvent.VK_DOWN) {
                            tempObject.setVelY(5.0f);
                            this.keyDown[1] = true;
                        }
                        if (key == KeyEvent.VK_RIGHT) {
                            tempObject.setVelX(5.0f);
                            this.keyDown[2] = true;
                        }
                        if (key == KeyEvent.VK_LEFT) {
                            tempObject.setVelX(-5.0f);
                            this.keyDown[3] = true;
                        }
                    }
                }
            }
            if (key == KeyEvent.VK_ESCAPE) {
                if (Game.gameState == Game.STATE.Difficulty || Game.gameState == Game.STATE.Death || Game.gameState == Game.STATE.Menu || Game.gameState == Game.STATE.Help || Game.gameState == Game.STATE.Settings || Game.gameState == Game.STATE.SelectGame || Game.gameState == Game.STATE.GameCreation || Game.gameState == Game.STATE.Achievements) {
                    System.exit(2);
                }
                if (Game.gameState == Game.STATE.SaveloadIG || Game.gameState == Game.STATE.Save1 || Game.gameState == Game.STATE.Save2 || Game.gameState == Game.STATE.Save3) {
                    if (Game.getCurrentGameStateToInt() == 1) {
                        Game.gameState = Game.STATE.Easy;
                    }
                    if (Game.getCurrentGameStateToInt() == 2) {
                        Game.gameState = Game.STATE.Medium;
                    }
                    if (Game.getCurrentGameStateToInt() == 3) {
                        Game.gameState = Game.STATE.Hard;
                    }
                } else if (Game.paused) {
                    Game.paused = false;
                } else if (!Game.paused) {
                    Game.paused = true;
                }
            }
        }
    }
    
    @Override
    public void keyReleased(final KeyEvent e) {
        final float key = (float)e.getKeyCode();
        if (Game.ARROWKEYS) {
            for (int i = 0; i < this.handler.object.size(); ++i) {
                final GameObject tempObject = this.handler.object.get(i);
                if (tempObject.getId() == ID.Player) {
                    if (key == KeyEvent.VK_UP) {
                        this.keyDown[0] = false;
                    }
                    if (key == KeyEvent.VK_DOWN) {
                        this.keyDown[1] = false;
                    }
                    if (key == KeyEvent.VK_RIGHT) {
                        this.keyDown[2] = false;
                    }
                    if (key == KeyEvent.VK_LEFT) {
                        this.keyDown[3] = false;
                    }
                    if (!this.keyDown[0] && !this.keyDown[1]) {
                        tempObject.setVelY(0.0f);
                    }
                    if (!this.keyDown[2] && !this.keyDown[3]) {
                        tempObject.setVelX(0.0f);
                    }
                }
            }
        } else if (!Game.ARROWKEYS) {
            for (int i = 0; i < this.handler.object.size(); ++i) {
                final GameObject tempObject = this.handler.object.get(i);
                if (tempObject.getId() == ID.Player) {
                    if (key == KeyEvent.VK_W) {
                        this.keyDown[0] = false;
                    }
                    if (key == KeyEvent.VK_S) {
                        this.keyDown[1] = false;
                    }
                    if (key == KeyEvent.VK_D) {
                        this.keyDown[2] = false;
                    }
                    if (key == KeyEvent.VK_A) {
                        this.keyDown[3] = false;
                    }
                    if (!this.keyDown[0] && !this.keyDown[1]) {
                        tempObject.setVelY(0.0f);
                    }
                    if (!this.keyDown[2] && !this.keyDown[3]) {
                        tempObject.setVelX(0.0f);
                    }
                }
            }
        }
    }
}
