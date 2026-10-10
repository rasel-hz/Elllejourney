package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Axe extends Entity{
    
    public OBJ_Axe(GamePanel gp) {
        super(gp);
        
        type = type_axe;
        name = "Woodcutter's Axe";
        down1 = setup("/object/Axe",gp.tileSize,gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height= 30;
        description = "["+ name +"]\nA bit rusty but still can\n cut some trees.";
        price = 75;
    }
    
}
