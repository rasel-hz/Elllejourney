package entity;

import java.util.Random;

import main.GamePanel;


public class NPC_OldMan extends Entity{
  
    public NPC_OldMan(GamePanel gp){
       super(gp);
       
       direction = "down";
       speed = 2;
       getImage();
       setDialogue();
    }
    public void getImage(){
    
        up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
    }
    public void setDialogue (){
    
        dialogues[0]=" Hello , elle.";
        dialogues[1]=" So you've come to this island\nto find the treasure?";
        dialogues[2]=" I used to be a great wizard but \nnow ... I'm a bit too old for\n takking an adventure.";
        dialogues[3]=" well , good luck on you.";
        
}
    public void setAction(){
        
        if(onPath == true){
            
            int goalCol = 32;
            int goalRow = 33;        
            searchPath(goalCol , goalRow);
        }
        else{
         actionLockCounter ++;
        
        if(actionLockCounter == 120){
          
        Random random = new Random ();
        int i = random.nextInt(100)+1;//pick anumber 1-100
        
        if (i <= 25 ){
            direction = "up";
        }
        if (i > 25 && i<= 50){
            direction = "down";
        }
        if (i > 50 && i<= 75){
            direction = "left";
        }
        if (i > 75 && i<= 100){
            direction = "right";
        }  
        
        actionLockCounter = 0 ;
        }   
        }
        
    }
    public void speak (){
        
     super.speak();
     onPath =true;
     
    }
    
}