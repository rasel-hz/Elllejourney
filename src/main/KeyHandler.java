package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
    
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed , enterPressed,shotKeyPressed;
    //DEBUG
    boolean checkDrawTime = false;
    
    
    public KeyHandler(GamePanel gp){
        this.gp = gp ;
        
    }
    
    @Override
    public void keyTyped(KeyEvent e){
    
    }
    @Override
    public void keyPressed(KeyEvent e){
        
        int code = e.getKeyCode();
        //TITLE STATE
        if(gp.gameState == gp.titleState){
           titleState(code);
        }
        // PLAY STATE
        else if(gp.gameState == gp.playState){
             playState(code);
        }
         //PAUSESTATE
        else if (gp.gameState == gp.pauseState){
           pauseState(code);
        }         
         //DIALOGUESTATE
        else if (gp.gameState == gp.dialogueState){
           dialogueState(code);
        }
        // CHARACTER STATE
        else if(gp.gameState == gp.characterState){
            characterState(code);
        }
        // OPTIONS STATE
        else if(gp.gameState == gp.optionsState){
            optionsState(code);
        }
        //GAME OVER STATE
        else if(gp.gameState == gp.gameOverState){
            gameOverState(code);
        }
        //TRADE STATE
        else if(gp.gameState == gp.tradeState){
            tradeState(code);
        }
        //MAP STATE
        else if(gp.gameState == gp.mapState){
            mapState(code);
        }
    }
    public void titleState(int code){
        if (code == KeyEvent.VK_W){
           gp.uii.commandNum--;
           if(gp.uii.commandNum < 0 ){
               gp.uii.commandNum = 2;
           }
        }
        if (code == KeyEvent.VK_S){
            gp.uii.commandNum++;
            if(gp.uii.commandNum >2 ){
               gp.uii.commandNum = 0;
           }
        } 
        if(code == KeyEvent.VK_ENTER){
            if(gp.uii.commandNum == 0){
                gp.gameState = gp.playState;
                gp.playMusic(0);
            }
            if(gp.uii.commandNum == 1){
                //add later
            }
            if(gp.uii.commandNum == 2){
                System.exit(0);
            }
        }
    }
    public void playState(int code){
        if (code == KeyEvent.VK_W){
          upPressed = true ;  
        }
        if (code == KeyEvent.VK_S){
            downPressed = true;
        }
        if (code == KeyEvent.VK_A){
            leftPressed = true ;
        }
        if (code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if (code == KeyEvent.VK_P){
            gp.gameState = gp.pauseState;  
        }
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.characterState;
        }
        if (code == KeyEvent.VK_ENTER){
            enterPressed = true ;  
        }
        if (code == KeyEvent.VK_F){
            shotKeyPressed = true ;  
        }
        if (code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.optionsState;  
        }
        if (code == KeyEvent.VK_M){
            gp.gameState = gp.mapState;  
        }
        if (code == KeyEvent.VK_X){
            if(gp.map.miniMapOn == false){
                gp.map.miniMapOn = true;
            }  
            else{
                gp.map.miniMapOn = false;
            }
        }
        
        
        //DEBUG
        if (code == KeyEvent.VK_T){
            if(checkDrawTime == false){
                checkDrawTime = true;
            }
            else if (checkDrawTime == true){
                checkDrawTime = false;
            }
        }
    }
    public void pauseState(int code){
        if (code == KeyEvent.VK_P){
            gp.gameState = gp.playState;  
        }
    }
    public void dialogueState(int code){
        if(code == KeyEvent.VK_ENTER){
               gp.gameState = gp.playState;
           }
    }
    public void characterState(int code){
        if(code == KeyEvent.VK_C){
                gp.gameState = gp.playState;
        }
        
        if(code == KeyEvent.VK_ENTER){
            gp.player.selectItem();
        }
        playerInventory(code);
    }
    public void optionsState(int code){
        
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        
        int maxCommandNum =0;
        switch(gp.uii.substate){
            case 0 :maxCommandNum = 4 ;break;
            case 2 :maxCommandNum = 1 ;break;
            
        }
        if (code == KeyEvent.VK_W){
            gp.uii.commandNum--;
            gp.playSE(9);
            if(gp.uii.commandNum < 0){
                gp.uii.commandNum = maxCommandNum;
            }
        }
        if (code == KeyEvent.VK_S){
            gp.uii.commandNum++;
            gp.playSE(9);
            if(gp.uii.commandNum > maxCommandNum){
                gp.uii.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_A){
           if(gp.uii.substate == 0){
               if(gp.uii.commandNum == 0 && gp.music.volumeScale > 0 ){
                   gp.music.volumeScale--;
                   gp.music.checkVolume();
                   gp.playSE(9);
               }
                 if(gp.uii.commandNum == 1 && gp.se.volumeScale > 0 ){
                   gp.se.volumeScale--;
                   gp.playSE(9);
               }
           }
        }
        if (code == KeyEvent.VK_D){
           if(gp.uii.substate == 0){
               if(gp.uii.commandNum == 0 && gp.music.volumeScale < 5 ){
                   gp.music.volumeScale++;
                   gp.music.checkVolume();
                   gp.playSE(9);
               }
                if(gp.uii.commandNum == 1 && gp.se.volumeScale < 5 ){
                   gp.se.volumeScale++;
                   gp.playSE(9);
               }
           }
        }
        
    }
    public void gameOverState(int code){
        
        if(code == KeyEvent.VK_W){
            gp.uii.commandNum--;
            if(gp.uii.commandNum < 0 ){
                gp.uii.commandNum = 1;
            }
            gp.playSE(9);
        }
         if(code == KeyEvent.VK_S){
            gp.uii.commandNum++;
            if(gp.uii.commandNum >1 ){
                gp.uii.commandNum = 0;
                
            }
            gp.playSE(9);
        }
         if(code == KeyEvent.VK_ENTER){
             if(gp.uii.commandNum == 0){
                 gp.gameState = gp.playState;
                 gp.retry();
                 gp.playMusic(0);
             }
             else if(gp.uii.commandNum == 1){
                 gp.gameState = gp.titleState;
                 gp.restart();
             }
         }
    }
    public void tradeState(int code){
        
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        if(gp.uii.substate == 0){
            if(code == KeyEvent.VK_W){
                gp.uii.commandNum--;
                if(gp.uii.commandNum < 0){
                    gp.uii.commandNum = 2;
                }
                gp.playSE(9);
            }
            if(code == KeyEvent.VK_S){
                gp.uii.commandNum++;
                if(gp.uii.commandNum > 2){
                    gp.uii.commandNum = 0;
                }
                gp.playSE(9);
            }            
        }
        if(gp.uii.substate == 1){
            npcInventory(code);
            if(code == KeyEvent.VK_ESCAPE){
                gp.uii.substate = 0;
            }
        }
         if(gp.uii.substate == 2){
            playerInventory(code);
            if(code == KeyEvent.VK_ESCAPE){
                gp.uii.substate = 0;
            }
        }
        
    }
    public void mapState(int code){
        if(code == KeyEvent.VK_M){
            gp.gameState = gp.playState;
        }
    }
    public void playerInventory(int code){
        if(code == KeyEvent.VK_W){
            if(gp.uii.playerSlotRow != 0){
              gp.uii.playerSlotRow--;
            gp.playSE(9);
        }   
            }
           
        if(code == KeyEvent.VK_A){
            if(gp.uii.playerSlotCol != 0){
             gp.uii.playerSlotCol--; 
            gp.playSE(9);   
            } 
        }
        if(code == KeyEvent.VK_S){
            if(gp.uii.playerSlotRow != 3){
              gp.uii.playerSlotRow++; 
            gp.playSE(9);  
            }
            }
        if(code == KeyEvent.VK_D){
            if(gp.uii.playerSlotCol != 4){
             gp.uii.playerSlotCol++;
             gp.playSE(9);   
            }   
        }
    }
    public void npcInventory(int code){
        if(code == KeyEvent.VK_W){
            if(gp.uii.npcSlotRow != 0){
              gp.uii.npcSlotRow--;
            gp.playSE(9);
        }   
            }
           
        if(code == KeyEvent.VK_A){
            if(gp.uii.npcSlotCol != 0){
             gp.uii.npcSlotCol--; 
            gp.playSE(9);   
            } 
        }
        if(code == KeyEvent.VK_S){
            if(gp.uii.npcSlotRow != 3){
              gp.uii.npcSlotRow++; 
            gp.playSE(9);  
            }
            }
        if(code == KeyEvent.VK_D){
            if(gp.uii.npcSlotCol != 4){
             gp.uii.npcSlotCol++;
             gp.playSE(9);   
            }   
        }
    }
     @Override
    public void keyReleased(KeyEvent e){
        
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W){
          upPressed = false ;  
        }
        if (code == KeyEvent.VK_S){
            downPressed = false;
        }
        if (code == KeyEvent.VK_A){
            leftPressed = false ;
        }
        if (code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if (code == KeyEvent.VK_F){
            shotKeyPressed = false ;  
        }
    } 
    
}