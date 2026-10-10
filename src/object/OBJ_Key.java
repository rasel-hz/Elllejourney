package object;

import entity.Entity;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;


public class OBJ_Key extends Entity{
    
    GamePanel gp;
    
     public OBJ_Key(GamePanel gp){
           super(gp);
           this.gp = gp;
           
        type = type_consumable;
        name = "key";
        down1 = setup("/object/key", gp.tileSize, gp.tileSize);
        description = "["+ name +"]\nUse it to open the doors.";
        price = 100;
    }
     public boolean use(Entity entity){
         
         gp.gameState = gp.dialogueState;
         
         int objIndex = getDetected(entity , gp.obj , "Door");
         
         if(objIndex != 999){
             gp.uii.currentDialogue = "You use the "+name+" and open the door.";
             gp.playSE(3);
             gp.obj[gp.currentMap][objIndex] = null;
             return true;
         }
         else{
             gp.uii.currentDialogue = "What are you trying to do?";
             return false;
         }
         
     }
}

