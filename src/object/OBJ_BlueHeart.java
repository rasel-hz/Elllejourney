package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BlueHeart extends Entity{
    
    GamePanel gp;
    
    public OBJ_BlueHeart(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        type = type_pickupOnly;
        name = "blue heart";
        down1 = setup("/object/blueheart",gp.tileSize,gp.tileSize);
        
        setDialogues();
    }
    public void setDialogues(){
        
    }
    public boolean use(Entity entity){
        
        int counter =0;
        if(counter == 0){
         gp.gameState = gp.finalState;
            if(gp.gameState == gp.finalState){
                gp.uii.drawFinalScreen();
         counter++;
        }
        if(counter == 2){
            }
        }
        

        return true;
    }
    
}
