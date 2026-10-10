package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Chest extends Entity{
    
    GamePanel gp;
    Entity loot;
    boolean opend = false;
    
       public OBJ_Chest( GamePanel gp , Entity loot){
        super(gp);
        this.gp = gp;
        this.loot = loot;
        
        type = type_obstacle;
        name = "Chest";
        image = setup("/object/chest",gp.tileSize,gp.tileSize);
        image2 = setup("/object/chest_opened",gp.tileSize,gp.tileSize);
        down1 = image;
        collision = true;
        
        
        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
       }
public void interact(){
    gp.gameState = gp.dialogueState;
    
    if(opend == false){
        gp.playSE(3);
        
        StringBuilder sb = new StringBuilder();
        sb.append("You opened the chest and found a \n"+ loot.name+"!");
        
        if(gp.player.inventory.size() == gp.player.maxInventorySize){
            sb.append("\n... But your inventory is full!");
            
        }
        else{
            sb.append("\nThe "+ loot.name +" is yours!");
            gp.player.inventory.add(loot);
            down1 = image2;
            opend = true;
        }
        gp.uii.currentDialogue = sb.toString();
        
    }
    else{
        gp.uii.currentDialogue = "It's empty.";
    }
}
}
