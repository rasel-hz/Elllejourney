package main;

import entity.Entity;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;



public class UI {
    
    GamePanel gp;
    Graphics2D g2;
    public Font maruMonica , PurisaB;
    BufferedImage heart_full , heart_half , heart_blank , crystal_full , crystal_blank , coin;
    public boolean messageOn = false;
    //public String message = "";
    //int messageCounter=0;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titlescreenState = 0;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    int substate = 0;
    int counter = 0;
    public Entity npc;
   
    
    public UI(GamePanel gp){
        this.gp = gp;
       
        
        try{
            InputStream is = getClass().getResourceAsStream("/Fonts/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);      
            is = getClass().getResourceAsStream("/Fonts/Purisa Bold.ttf");
            PurisaB = Font.createFont(Font.TRUETYPE_FONT, is);   
        }catch(FontFormatException e ){
            e.printStackTrace();
        }catch(IOException e ){
            e.printStackTrace();
        }

        //CREATE HUD OBJECT
       Entity heart = new OBJ_Heart(gp);
       heart_full = heart.image;
       heart_half = heart.image2;
       heart_blank = heart.image3;
       Entity crystal = new OBJ_ManaCrystal(gp);
       crystal_full = crystal.image;
       crystal_blank = crystal.image2;
       Entity broonzCoin = new OBJ_Coin_Bronze(gp);
       coin = broonzCoin.down1;
       
    }
    public void  addMessage (String text){
        
        message.add(text);
        messageCounter.add(0);
    }
    public void draw (Graphics2D g2){
      
        this.g2 = g2;
        
        g2.setFont(maruMonica);
        g2.setColor(Color.white);
        //titlestate
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        //playstate
        if (gp.gameState == gp.playState){
            drawPlayerLife();
            drawMessage();
        }
        //pausestate
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
            drawPlayerLife();
        }
        //dialogestate
        if(gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawDialogeScreen();
        }
        // characterState
        if(gp.gameState == gp.characterState){
            drawCharacterScreen();
            drawInventory(gp.player,true);
        }
        //option state
        if(gp.gameState == gp.optionsState){
            drawOptionsScreen();
        }
        //game over state
        if(gp.gameState == gp.gameOverState){
            darwGameOverScreen();
        }
        if(gp.gameState == gp.transitionState){
            drawTransitionState();
        }
        if(gp.gameState == gp.tradeState){
            drawTradeScreen();
        }
        if(gp.gameState == gp.sleepState){
            drawTSleepScreen();
        }
        if(gp.gameState == gp.finalState){
            drawFinalScreen();
        }
    }
    public void drawFinalScreen(){
        gp.stopMusic();
        counter++;
        
        if(counter < 120){
            gp.eManager.lighting.filterAlpha += 0.01f;
            if(gp.eManager.lighting.filterAlpha > 1f){
                gp.eManager.lighting.filterAlpha = 1f;
            }
            
        }
        if(counter >= 120){
            drawendScreen();
            }
        if(counter >= 500){
            finalScreen2();
        }
        if(counter >= 700){
           System.exit(0); 
        }
        
    }
    public void drawendScreen(){
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHight);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
        String text = "The End";
        int x =getXforCenteredText(text) ;
        int y = gp.tileSize*5;
        //SHADOW
        g2.setColor(Color.black);
        g2.drawString(text,x+5,y+5);
        //MAIN COLOR
        g2.setColor(Color.white);
        g2.drawString(text,x,y);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,50));
        text = "elle finally found the treasure.";
        x =getXforCenteredText(text) ;
        y += gp.tileSize;
        g2.drawString(text, x, y);
        text = "But it's not the end of her journey.";
        x =getXforCenteredText(text) ;
        y += gp.tileSize;
        g2.drawString(text, x, y);
        text = "elle's journey just begun.";
        x =getXforCenteredText(text) ;
        y += gp.tileSize;
        g2.drawString(text, x, y);
        
    }
    public void finalScreen2(){
        
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHight);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
        String text = "Elle's journey";
        int x = getXforCenteredText(text) ;
        int y = gp.tileSize*5;
        //SHADOW
        g2.setColor(Color.black);
        g2.drawString(text,x+5,y+5);
        //MAIN COLOR
        g2.setColor(Color.white);
        g2.drawString(text,x,y);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,30F));
        text = "@rasel&joury";
        x =getXforCenteredText(text) ;
        y += gp.tileSize*2;
        g2.drawString(text, x, y);
        
    }
    public void drawPlayerLife(){
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i =0;
        
        //DRAW MAX HEART
        while (i < gp.player.maxLife/2){
            g2.drawImage(heart_blank, x,y, null);
            i++;
            x+=gp.tileSize;
        }
        //RESET
         x = gp.tileSize/2;
         y = gp.tileSize/2;
         i =0;
         //DRAW CURRENT LIFE
         while (i < gp.player.life){
            g2.drawImage(heart_half, x,y, null);
            i++;
            if ( i< gp.player.life){
             g2.drawImage(heart_full, x,y, null);   
            }
            i++;
            x+=gp.tileSize;
        }
         
         //DRAW MAX MANA
         x = (gp.tileSize/2)-5;
         y = (int) (gp.tileSize*1.5);
         i =0 ;
         while(i < gp.player.maxMana){
             g2.drawImage(crystal_blank, x,y, null);
             i++;
             x += 35;
         }
         //DRAW MANA
         x = (gp.tileSize/2)-5;
         y = (int) (gp.tileSize*1.5);
         i =0 ;
         while(i < gp.player.mana){
             g2.drawImage(crystal_full, x,y, null);
             i++;
             x += 35;
         }
    }
    public void drawMessage(){
    
        int messageX = gp.tileSize;
        int messageY = gp.tileSize*4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        
        for(int i= 0; i< message.size(); i++){
            
            if(message.get(i) != null){
                
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX+2, messageY+2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);
                
                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;
                
                if(messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
        
        
}
    public void drawTitleScreen(){
        
        g2.setColor(new Color(255, 182, 193));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHight);
        
        //TITLE NAME 
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
        String text = "ELLE'S JOURNEY";
        int x =getXforCenteredText(text) ;
        int y = gp.tileSize*3;
        //SHADOW
        g2.setColor(Color.black);
        g2.drawString(text,x+5,y+5);
        //MAIN COLOR
        g2.setColor(Color.white);
        g2.drawString(text,x,y);
        
        //IMAGE
        x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y += gp.tileSize*2;
        g2.drawImage(gp.player.down1, x,y, gp.tileSize*2 , gp.tileSize*2 , null);
        
        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
        
        text = "NEW GAME";
        x =getXforCenteredText(text) ; 
        y += gp.tileSize*3.5;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">",x-gp.tileSize,y);
        }
        text = "LOAD GAME";
        x =getXforCenteredText(text) ; 
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">",x-gp.tileSize,y);
        }
        text = "QUIT";
        x =getXforCenteredText(text) ; 
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 2){
            g2.drawString(">",x-gp.tileSize,y);
        }
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));
        text = "BY : RASIL & JOURY";
        x =getXforCenteredText(text) ; 
        y += gp.tileSize;
        g2.drawString(text, x, y);
    }
    public void drawPauseScreen(){
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text) ;
         int y = gp.screenHight/2;
        g2.drawString(text, x, y);
    }
    public void drawDialogeScreen(){
        
        //WINDOW
        int x = gp.tileSize*3;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*6);
        int height = gp.tileSize*4;
        
        drawSubwIndow(x , y , width ,height);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
        x+= gp.tileSize;
        y+= gp.tileSize;
        
        for(String line : currentDialogue.split("\n")){
        g2.drawString(line, x, y);   
        y += 40;
        }
       
    }
    public void drawCharacterScreen(){
        
        // create a frame
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*5;
        final int frameheight = gp.tileSize*10;
        drawSubwIndow(frameX, frameY, frameWidth, frameheight);
        
        //text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        
        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;
        
        // names
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 10;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Sheild", textX, textY);
        textY += lineHeight; 
        
        // Values
        int tailX =(frameX + frameWidth) - 30;
        // reset textY
        textY = frameY + gp.tileSize;
        String value;
        
        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight; 

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight; 

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight; 

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight; 

        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight; 

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight; 
        
        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight; 
        
        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight; 
        
        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY-24, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY-24, null);
          
    }
    public void drawInventory(Entity entity , boolean cursor){
        
        int frameX ;
        int frameY ;
        int frameWidth ;
        int frameHeight ;
        int slotCol = 0;
        int slotRow = 0;
        
        if(entity == gp.player){
            frameX = gp.tileSize*9;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize*6;
            frameHeight = gp.tileSize*5; 
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
       
        }
        else {
            frameX = gp.tileSize*2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize*6;
            frameHeight = gp.tileSize*5; 
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }
        
        
        drawSubwIndow(frameX, frameY, frameWidth, frameHeight);
    
        // Slot
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3 ;
        
        //DRAW PLAYER'S ITEMS
        for(int i = 0; i < entity.inventory.size(); i++){
            
            //EQUIP CURSOR
            if(entity.inventory.get(i) == entity.currentWeapon || 
                    entity.inventory.get(i) == entity.currentShield ||
                    entity.inventory.get(i)== entity.currentLight){
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }
            
            g2.drawImage(entity.inventory.get(i).down1,slotX,slotY,null);
            
            slotX += slotSize;
            
            if( i == 4 || i == 9 || i == 14){
                slotX = slotXstart;
                slotY += slotSize;
            }
        }
        // Cursor
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;
        
        // draw cursor
        if(cursor == true){
          g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10,10);
         g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10,10);
        
        //DESCRIPTION FRAME
        int dFrameX = frameX;
        int dFrameY = frameY +frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize*3;
        
        //DRAW DESCRIPTION TEXT
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize ;
        g2.setFont(g2.getFont().deriveFont(28F));
        
        int itemIndex = getItemIndexOnSlot(slotCol , slotRow);
        
        if(itemIndex < entity.inventory.size()){
            
            drawSubwIndow(dFrameX,dFrameY,dFrameWidth,dFrameHeight);
            
            for(String line: entity.inventory.get(itemIndex).description.split("\n")){
             g2.drawString(line, textX, textY);   
             textY += 32; 
        }
        
            }
            
        }
        
    }
    public void darwGameOverScreen(){
        
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHight);
        
        int x  ;
        int y  ;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,110f));
        
        text = "Game Over";
        //SHADOW
        g2.setColor(Color.black);
        x= getXforCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text, x, y);
        //MAIN
        g2.setColor(Color.white);
        x= getXforCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text, x-4, y-4);
        
        //retry
        g2.setFont(g2.getFont().deriveFont(50F));
        text = "Retry";
        x= getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">",x-40,y);
        }
       
        //back to the title screen
        text = "Quit";
        x= getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">",x-40,y);
        }
    }
    public void drawOptionsScreen(){
        
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        
        //SUB WINDOW
        int frameX= gp.tileSize*4;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*10;
        
        drawSubwIndow(frameX,frameY,frameWidth,frameHeight);
        
        switch(substate){
            case 0 : options_top(frameX,frameY);break;
            case 1 : option_control(frameX,frameY);break;
            case 2 : option_endGameConfirmation(frameX,frameY);break;
        }
        gp.keyH.enterPressed = false;
    }
    public void options_top(int frameX , int frameY){
        
        int textX;
        int textY;
        
        //TITLE
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);
        
        //FULL SCREEN ON/OFF
        /*textX = frameX + gp.tileSize;
        textY += gp.tileSize*2;
        g2.drawString("Full Screen", textX , textY);
        if(commandNum == 0 ){
            g2.drawString(">", textX - 25, textY);
        }*/
        
        //MUSIC
        textX = frameX + gp.tileSize;
        textY += gp.tileSize*2;
        g2.drawString("Music", textX, textY);
        if(commandNum == 0 ){
            g2.drawString(">", textX - 25, textY);
        }
        //SE
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if(commandNum == 1 ){
            g2.drawString(">", textX - 25, textY);
        }
        //CONTROL
        textY += gp.tileSize;
        g2.drawString("CONTROL", textX, textY);
        if(commandNum == 2 ){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true){
                substate = 1;
                commandNum = 0;
            }
        }
        //ENDGAME
        textY += gp.tileSize;
        g2.drawString("End game", textX, textY);
        if(commandNum == 3 ){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true ){
                substate = 2;
                commandNum=0;
            }
        }
        //BACK
        textY += gp.tileSize*2;
        g2.drawString("Back", textX, textY);
        if(commandNum == 4 ){
            g2.drawString(">", textX - 25, textY);
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true){
                gp.gameState = gp.playState;
            }
        }
        
        //FULL SCREEN CHECK BOX
        /*textX = frameX + (int)(gp.tileSize*4.5);
        textY = frameY + gp.tileSize*2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        */
        //MUSIC VOLUME
        textX = frameX + (int)(gp.tileSize*4.5);
        textY = frameY + gp.tileSize*2 + 24;
        g2.drawRect(textX, textY, 120, 24);//120/5 = 24
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);
        
        //SE VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);
    }
    public void option_control(int frameX, int frameY){
        
        int textX;
        int textY;
        
        //TITLE
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);
        
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY); textY += gp.tileSize;
        g2.drawString("Confirm/Attack", textX, textY); textY += gp.tileSize;
        g2.drawString("Shoot", textX, textY); textY += gp.tileSize;
        g2.drawString("Character Screen", textX, textY); textY += gp.tileSize;
        g2.drawString("Pause", textX, textY); textY += gp.tileSize;
        g2.drawString("options", textX, textY); textY += gp.tileSize;  
        g2.drawString("World Map", textX, textY); textY += gp.tileSize; 
        
        textX = frameX +  gp.tileSize*6;
        textY = frameY + gp.tileSize*2;
        g2.drawString("WASD", textX, textY); textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
        g2.drawString("F", textX, textY); textY += gp.tileSize;
        g2.drawString("C", textX, textY); textY += gp.tileSize;
        g2.drawString("P", textX, textY); textY += gp.tileSize;
        g2.drawString("ESC", textX, textY); textY += gp.tileSize;
        g2.drawString("M", textX, textY); textY += gp.tileSize;
        
        //BACK
        textX = frameX + gp.tileSize;
        textY = frameY +gp.tileSize*9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true){
                substate = 0;
                commandNum = 2;
            }
        }
    }
    public void drawTransitionState(){
        
        counter++;
        g2.setColor(new Color(0,0,0,counter+5));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHight);
        if(counter == 50){
            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eHandler.tempMap;
            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
            
        }
    }
    public void drawTradeScreen(){
        
        switch(substate){
            case 0 :trade_select(); break;
            case 1 :trade_buy(); break;
            case 2 :trade_sell(); break;
        }
        gp.keyH.enterPressed =false;
    }
    public void drawTSleepScreen(){
        
        counter++;
        
        if(counter < 120){
            gp.eManager.lighting.filterAlpha += 0.01f;
            if(gp.eManager.lighting.filterAlpha > 1f){
                gp.eManager.lighting.filterAlpha = 1f;
            }
        }
        if(counter >= 120 ){
            gp.eManager.lighting.filterAlpha -= 0.01f;
            if(gp.eManager.lighting.filterAlpha <= 0f){
                gp.eManager.lighting.filterAlpha = 0f;
                counter = 0;
                gp.eManager.lighting.dayState = gp.eManager.lighting.day;
                gp.eManager.lighting.dayCounter = 0 ;
                gp.gameState = gp.playState;
                gp.player.getPlayerImage();
            }
        }
    }
    public void trade_select(){
        
        drawDialogeScreen();
        
        //DRAW WINDOW
        int x = gp.tileSize*10;
        int y = gp.tileSize*4;
        int width = gp.tileSize*3;
        int height = (int)(gp.tileSize * 3.5);
        drawSubwIndow(x,y,width,height);
        
        //DRAW TEXT
        x+= gp.tileSize;
        y+= gp.tileSize;
        g2.drawString("Buy", x, y);
        if(commandNum == 0){
            g2.drawString(">", x-24, y);
            if(gp.keyH.enterPressed == true){
                substate = 1;
            }
        }
        y += gp.tileSize;
        
        g2.drawString("Sell", x, y);
        if(commandNum == 1){
            g2.drawString(">", x-24, y);
            if(gp.keyH.enterPressed == true){
                substate = 2;
            }
        }
        y += gp.tileSize;
        
        g2.drawString("Leave", x, y);
        if(commandNum == 2){
            g2.drawString(">", x-24, y);
            if(gp.keyH.enterPressed == true){
                commandNum = 0;
                gp.gameState = gp.dialogueState;
                currentDialogue = "Come again , hehe!";
            }
        }
        y += gp.tileSize;
        
    }
    public void trade_buy(){
        
        //DRAW PLAYER INVENTORY
        drawInventory(gp.player,false);
        //DRAW NPC INVENTORY
        drawInventory(npc,true);
        
        //DRAW HINT WINDOW 
        int x = gp.tileSize*2;
        int y = gp.tileSize*9;
        int width = gp.tileSize*6;
        int height = gp.tileSize*2;
        drawSubwIndow(x,y,width,height);
        g2.drawString("[ESC] Back" , x+24, y+60);
        
        //DRAW PLAYER COIN WINDOW 
        x = gp.tileSize*9;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubwIndow(x,y,width,height);
        g2.drawString("Your Coin :"+gp.player.coin , x+24, y+60);
        
        //DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(npcSlotCol , npcSlotRow);
        if(itemIndex < npc.inventory.size()){
            
            x = (int)(gp.tileSize*5.5);
            y = (int)(gp.tileSize*5.5);
            width = (int)(gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubwIndow(x,y,width,height);
            g2.drawImage(coin, x+10, y+8,32,32,null);
            
            int price = npc.inventory.get(itemIndex).price;
            String text = ""+price;
            x = getXforAlignToRightText(text,gp.tileSize*8-20);
            g2.drawString(text, x, y+34);
            
        //BUY ITEM
        if(gp.keyH.enterPressed == true){
            if(npc.inventory.get(itemIndex).price > gp.player.coin){
                substate = 0;
                gp.gameState = gp.dialogueState;
                currentDialogue = "You need more coins to buy it!";
                drawDialogeScreen();
            }
            else if(gp.player.inventory.size() == gp.player.maxInventorySize){
               substate = 0;
                gp.gameState = gp.dialogueState;
                currentDialogue = "Inventory is full!";
                drawDialogeScreen(); 
            }
            else {
                gp.player.coin -= npc.inventory.get(itemIndex).price;
                gp.player.inventory.add(npc.inventory.get(itemIndex));
            }
        }
        }
    }
    public void trade_sell(){
        
        
        int x ;
        int y ;
        int width ;
        int height ;
        //DRAW PLAYER INVENTORY
        drawInventory(gp.player , true);
        //DRAW HINT WINDOW 
        x = gp.tileSize*2;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubwIndow(x,y,width,height);
        g2.drawString("[ESC] Back" , x+24, y+60);
        
        //DRAW PLAYER COIN WINDOW 
        x = gp.tileSize*9;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubwIndow(x,y,width,height);
        g2.drawString("Your Coin :"+gp.player.coin , x+24, y+60);
        
        //DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(playerSlotCol , playerSlotRow);
        if(itemIndex < gp.player.inventory.size()){
            
            x = (int)(gp.tileSize*12.5);
            y = (int)(gp.tileSize*5.5);
            width = (int)(gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubwIndow(x,y,width,height);
            g2.drawImage(coin, x+10, y+8,32,32,null);
            
            int price = gp.player.inventory.get(itemIndex).price/2;
            String text = ""+price;
            x = getXforAlignToRightText(text,gp.tileSize*15-20);
            g2.drawString(text, x, y+34);
            
        //SELL ITEM
        if(gp.keyH.enterPressed == true){
            if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon ||
                 gp.player.inventory.get(itemIndex) == gp.player.currentShield ){
                commandNum = 0;
                substate = 0;
                gp.gameState = gp.dialogueState;
                currentDialogue = "You can't sell an equipped item!"; 
            }
            else{
                gp.player.inventory.remove(itemIndex);
                gp.player.coin += price;
            }
        }
        }
    }
    public void option_endGameConfirmation(int frameX , int frameY){
        int textX = frameX + gp.tileSize/2;
        int textY = frameY + gp.tileSize*3;
        
        currentDialogue = "Sure want to quit the game?";
        
        for(String line: currentDialogue.split("\n")){
            g2.drawString(line, textX, textY);
            textY += 40;
        }
        //YES
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gp.tileSize*3; 
        g2.drawString(text, textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true){
                substate =0;
                gp.gameState = gp.titleState;
                gp.music.stop();
            }
        }
        //NO
        text = "No";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;        
        g2.drawString(text, textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true){
                substate =0;
                commandNum = 3;
            }
        }
        
    }
    public int getItemIndexOnSlot(int slotCol , int slotRow){
        int itemIndex = slotCol + (slotRow*5);
        return itemIndex;
    }
    public void drawSubwIndow(int x , int y , int width , int height){
        
        Color c = new Color (0,0,0,210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        
        c = new Color (255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
    public int getXforCenteredText (String text){
        
      int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
      int   x = gp.screenWidth/2 - length/2;
      return x;
    }
    public int getXforAlignToRightText (String text, int tailX){
        
      int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
      int   x = tailX - length;
      return x;
    }
}