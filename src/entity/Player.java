
package entity;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class Player extends Entity{
    
    
    KeyHandler keyH;
    
    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    public boolean attackCanceled = false;
    public boolean lightUpdated = false;
    
    public Player(GamePanel gp, KeyHandler keyH){
        
        super(gp);
        
        this.keyH = keyH;
        
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHight/2 - (gp.tileSize/2);
        
        solidArea = new Rectangle();
        solidArea.x=8;
        solidArea.y=16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width=28;
        solidArea.height=28;
        
        
        
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }
    public void setDefaultValues(){
        worldX=gp.tileSize * 38;
        worldY=gp.tileSize * 9;
//        worldX=gp.tileSize * 23;
//        worldY=gp.tileSize *20;
        gp.currentMap = 0;
        speed=4;
        direction = "down";
        
        //PLAYERSTATUS
        level = 1;
        maxLife = 8;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        strength = 1; // the more strength she has, the more damage she gives
        dexterity =1;// the more dexterity she has, the less damage she receives
        exp = 0;
        nextLevelExp = 5;
        coin =50;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
        attack = getAttack(); 
        defense = getDefense();
    }
    public void setDefaultPosistion(){
       if(gp.currentMap == 0){
        worldX=gp.tileSize * 38;
        worldY=gp.tileSize * 9;   
       }
        if(gp.currentMap == 2){
        worldX=gp.tileSize * 12;
        worldY=gp.tileSize * 13;
        }
        direction = "down";
    }
    public void restoreLifeAndMana(){
        life = maxLife;
        mana = maxMana;
       invincible = false;
    }
    public void setItems(){
        
        inventory.clear();
//        inventory.add(currentWeapon);
        inventory.add(currentShield);
//         inventory.add(new OBJ_Key(gp));
//         inventory.add(new OBJ_Lantern(gp));
        
    }
    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
        
    }
    public int getDefense(){
        return defense = dexterity * currentShield.defenseValue;
    }
    public void getPlayerImage(){
    
        up1 = setup("/player/up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/player/down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/player/left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/player/right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/right_2", gp.tileSize, gp.tileSize);
    }
    public void getSleepingImage(BufferedImage image){
        up1 = image;
        up2 = image;
        down1 = image;
        down2 = image;
        left1 = image;
        left2 = image;
        right1 = image;
        right2 = image;
    }
    public void getPlayerAttackImage(){
        
        if(currentWeapon.type == type_sword){
        attackUp1 = setup("/player/attack_up_1", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/player/attack_up_2", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("/player/attack_down_1", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/player/attack_down_2", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("/player/attack_left_1", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("/player/attack_left_2", gp.tileSize*2, gp.tileSize);
        attackRight1 = setup("/player/attack_right_1", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("/player/attack_right_2", gp.tileSize*2, gp.tileSize);  
        }
        if(currentWeapon.type == type_axe){
        attackUp1 = setup("/player/axe_up_1", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/player/axe_up_2", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("/player/axe_down_1", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/player/axe_down_2", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("/player/axe_left_1", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("/player/axe_left_2", gp.tileSize*2, gp.tileSize);
        attackRight1 = setup("/player/axe_right_1", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("/player/axe_right_2", gp.tileSize*2, gp.tileSize);  
        } 
    }
    
    public void update(){
        
        if(attacking == true){
            attacking();
            
        }
         
        else if(keyH.upPressed == true || keyH.downPressed == true ||
           keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true){
            
            if (keyH.upPressed == true){
                direction = "up";
            }
            else if (keyH.downPressed == true){
                direction = "down";
            }
            else if (keyH.leftPressed == true){
                direction = "left";
            }
            else if (keyH.rightPressed == true){
                direction = "right";
            }
            
         
            //CHECK TILE COLLISION
            collisionOn = false;
           gp.cChecker.checkTile(this);
          
            //CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject (objIndex); 
        
            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);
        
            // CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);
        
            //CHECK INTERACTIVE TILE COLLISION
            int iTileInex = gp.cChecker.checkEntity(this, gp.iTile);
            
            //CHECK EVENT
            gp.eHandler.checkEvent();
            gp.keyH.enterPressed = false;
        
            //if collison is false, player can move   
            if(collisionOn == false && keyH.enterPressed == false){
                
                switch(direction){
                case"up": worldY -= speed; break;
                case"down": worldY += speed; break;    
                case"left": worldX -= speed; break;    
                case"right": worldX += speed; break;    
                }
            }
       
            gp.keyH.enterPressed = false;
            
            spriteCounter++;
            if(spriteCounter > 12){
                if(spriteNum == 1){
                    spriteNum=2;
                }
                else if(spriteNum == 2){
                    spriteNum =1;
                }
                spriteCounter = 0 ;
            }
        }
        
        if(gp.keyH.shotKeyPressed == true && projectile.alive == false 
                && shotAvilableCounter == 30 && projectile.haveResource(this)==true){
            
            //SET DEFAULT CORDINATES , DIRECTION AND USER
            projectile.set(worldX,worldY,direction , true , this);
            
            //SUBTRACT THE COST (MANA, AMMO ETC)
            projectile.subtractResource(this);
            
            //ADD IT TO THE LIST
            gp.projectileList.add(projectile);
             
            shotAvilableCounter = 0;
            gp.playSE(10);
        }
        //This needs to be outside of key if statement!
        if (invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvilableCounter < 30){
            shotAvilableCounter++;
        }
        if(life > maxLife){
               life = maxLife;
           }
        if(mana > maxMana){
               mana = maxMana;
           }
        if(life <= 0 ){
            gp.gameState = gp. gameOverState;
            gp.uii.commandNum = -1;
            gp.playSE(12);
            gp.stopMusic();
        }
    }
    
    public void pickUpObject (int i){
        
        if(i != 999){
            
            //PICKUP ONLY ITEMS
            if(gp.obj[gp.currentMap][i].type == type_pickupOnly){
              
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
                
            }
            //OBSTACLE
            else if (gp.obj[gp.currentMap][i].type == type_obstacle){
                if(keyH.enterPressed == true){
                    attackCanceled = true;
                  gp.obj[gp.currentMap][i].interact();
                }
            }
            //INVENTORY ITEMS
            else {
             String text;
           if(inventory.size() != maxInventorySize){
               
               inventory.add(gp.obj[gp.currentMap][i]);
               gp.playSE(1);
               text = " Got a "+gp.obj[gp.currentMap][i].name+"!";
           }
           else{
               text = "Inventory is full !";
           }
           gp.uii.addMessage(text);
           gp.obj[gp.currentMap][i] = null;   
            }
        }
    }
    public void interactNPC(int i){
        if(gp.keyH.enterPressed == true){
            if(i != 999){
                //attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak();        
            }
            else{
                gp.playSE(7);
                attacking = true;
                
            }
           
        }  
             
    }
    public void contactMonster(int i ){
        
        if(i != 999){
            if(invincible == false && gp.monster[gp.currentMap][i].dying == false){
                gp.playSE(6);
                
                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if(damage < 0){
                    damage = 0;
                }
                
                life -= damage;
                invincible = true;
            }
        }
    }
    public void damageMonster(int i , int attack){
        if(i != 999){
            if(gp.monster[gp.currentMap][i].invincible == false){
               
                gp.playSE(5);
                
                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if(damage < 0){
                    damage = 0;
                }
                
                gp.monster[gp.currentMap][i].life -= 1;
                gp.uii.addMessage(" damage!");
                
                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();
                
                if(gp.monster[gp.currentMap][i].life <= 0){
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.uii.addMessage("Killed the "+ gp.monster[gp.currentMap][i].name + "!");
                    gp.uii.addMessage("Exp + "+ gp.monster[gp.currentMap][i].exp + "!");
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();
                }
            }
        }
    }
    public void damageInteractiveTile(int i){
        
        if(i != 999 && gp.iTile[gp.currentMap][i].destuctible == true
                && gp.iTile[gp.currentMap][i].isCorrectItem(this) == true && gp.iTile[gp.currentMap][i].invincible == false){
            
            gp.iTile[gp.currentMap][i].playSE();
            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].invincible = true;
            
            //Generate particles
            generateParticle(gp.iTile[gp.currentMap][i],gp.iTile[gp.currentMap][i]);
            
            if(gp.iTile[gp.currentMap][i].life == 0){
            gp.iTile[gp.currentMap][i]=gp.iTile[gp.currentMap][i].getDestroyedForm();
    
            }              
        }
    }
    public void checkLevelUp(){
        
        if(exp >= nextLevelExp){
            
            level++;
            nextLevelExp = nextLevelExp*2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            
            gp.playSE(8);
            gp.gameState = gp.dialogueState;
            gp.uii.currentDialogue = "You are level " + level + "now!\n"
                    +"You feel stronger!";
        }
    }
    public void selectItem(){
        
        int itemIndex = gp.uii.getItemIndexOnSlot(gp.uii.playerSlotCol,gp.uii.playerSlotRow);
        
        if(itemIndex < inventory.size()){
            
            Entity selectedItem = inventory.get(itemIndex);
            
            if(selectedItem.type == type_sword || selectedItem.type == type_axe ){
                
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selectedItem.type == type_light){
                
                if(currentLight == selectedItem){
                    currentLight = null;
                }
                else{
                    currentLight = selectedItem;
                }
                lightUpdated = true;
            }
            if(selectedItem.type == type_shield){
                
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_consumable){
                
                if(selectedItem.use(this)==true){
                    inventory.remove(itemIndex);
                }
            }
        }
    }
    @Override
    public void draw(Graphics2D g2){
    //g2.setColor(Color.white);
     
    //g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    BufferedImage image = null;
    int tempScreenX = screenX;
    int tempScreenY = screenY;
     
    switch (direction){
        case"up":
            if(attacking == false){
                if(spriteNum == 1){image = up1;}
                if(spriteNum == 2){image = up2;}
            }
            if(attacking == true){
                tempScreenY = screenY - gp.tileSize;
                if(spriteNum == 1){image = attackUp1;}
                if(spriteNum == 2){image = attackUp2;}
            }
            break;
        case "down":
            if(attacking == false){
                if(spriteNum == 1){image = down1;}
                if(spriteNum == 2){image = down2;}
            }
            if(attacking == true){
                if(spriteNum == 1){image = attackDown1;}
                if(spriteNum == 2){image = attackDown2;}
            }
            break;
        case "left":
            if(attacking == false){
                if(spriteNum == 1){image = left1;}
                if(spriteNum == 2){image = left2;}
            }
            if(attacking == true){
                tempScreenX = screenX - gp.tileSize;
                if(spriteNum == 1){image = attackLeft1;}
                if(spriteNum == 2){image = attackLeft2;}
            }
            break;
        case "right":
            if(attacking == false){
                if(spriteNum == 1){image = right1;}
                if(spriteNum == 2){image = right2;}
            }
            if(attacking == true){
                if(spriteNum == 1){image = attackRight1;}
                if(spriteNum == 2){image = attackRight2;}
            }
            break;
    }
     
    if (invincible == true){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
    }
    g2.drawImage(image,tempScreenX,tempScreenY,null);
     
    // reset alpha
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

     
    //DEBUG
    //g2.setFont(new Font("Arial", Font.PLAIN, 26));
    //g2.setColor(Color.white);
    //g2.drawString("Invincible: "+invincible, 10, 400);
     
    }
}