package tile_interactive;

import main.GamePanel;

public class cave extends InteractiveTile{
     GamePanel gp;
    
    public cave(GamePanel gp, int col ,int row) {
        super(gp,col,row);
        this.gp = gp;
        
        this.worldX = gp.tileSize*col;
        this.worldY = gp.tileSize*row;
        
        down1 = setup("/tile_interactive/cave.1",gp.tileSize*5,gp.tileSize*5);
        
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    
}