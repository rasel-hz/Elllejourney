package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Tent extends Entity{
    
    GamePanel gp;

    public OBJ_Tent(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        type = type_consumable;
        name = "Tent";
        down1 = setup("/object/tent" , gp.tileSize , gp .tileSize);
        down2 = setup("/object/tent" , gp.tileSize +30 , gp .tileSize +30);
        description = "[Tent]\nYou can sleep until\nnext morning.";
        price = 300;
    }
    public boolean use(Entity entity){
        
        gp.gameState = gp.sleepState;
        gp.playSE(14);
        gp.player.life = gp.player.maxLife;
        gp.player.mana = gp.player.maxMana;
        gp.player.getSleepingImage(down2);
        return true;
    }
    
    
}
