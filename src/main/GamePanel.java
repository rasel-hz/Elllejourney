package main;

import AI.PathFinder;
import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;
import tile.Map;
import tile.TileManager;
import tile_interactive.InteractiveTile;


public class GamePanel extends JPanel implements Runnable{
    
    // SCREEN SITTINGS
    final int originalTileSize = 16; //16x16 pixle tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;//45x45 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize *maxScreenCol;//960 pixle
    public final int screenHight = tileSize *maxScreenRow;//576 pixle
    
    //world sittings 
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;
    

    //Frame by second
    int FBS = 60;
    
    //SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI uii = new UI (this);
    public EventHandler eHandler = new EventHandler(this);
    public PathFinder pFinder = new PathFinder(this);
    EnvironmentManager eManager = new EnvironmentManager(this);
    Map map = new Map (this);
    Thread gameThread;
    
    //ENTITY AND OBJECT
    public Player player = new Player(this,keyH);
    public Entity obj[][] = new Entity[maxMap][20];
    public Entity npc [][] = new Entity [maxMap][10];
    public Entity monster [][] = new Entity [maxMap][20];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();
    
    
    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int sleepState = 9;
    public final int mapState = 10;
    public final int finalState = 11;
    public final int endState = 12;
    
    //AREA
    public int currentArea;
    public final int outside = 50;
    public final int inside = 51;
    public final int cave = 52;
    
    
 
    public GamePanel() {
         
         this.setPreferredSize(new Dimension (screenWidth,screenHight));
         this.setBackground(Color.black);
         this.setDoubleBuffered(true);
         this.addKeyListener(keyH);
         this.setFocusable(true);
         
     }
    public void setupGame(){
         
      aSetter.setObject();
      aSetter.setNPC();
      aSetter.setMonster();
      aSetter.setInteractiveTile();
      eManager.setup();
      
      gameState = titleState;
      currentArea = outside;
      
     }
    public void retry(){
        
        player.setDefaultPosistion();
        player.restoreLifeAndMana();
        aSetter.setNPC();
        aSetter.setMonster();

    }
    public void restart(){
        
        player.setDefaultPosistion();
        player.setDefaultValues();
        player.restoreLifeAndMana();
        player.setItems();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
    }
    public void startGameThread(){
    
 gameThread = new Thread(this);   
 gameThread.start();
}
    @Override
    public void run(){
    
    double drawInterval = 1000000000/FBS;
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    long timer = 0;
    int drawCount = 0;
    
    
    while (gameThread != null){
        
        currentTime = System.nanoTime();
        
        delta += (currentTime - lastTime )/ drawInterval;
        timer += (currentTime - lastTime );
        lastTime = currentTime;
        
         if (delta>=1){
             update();
             repaint();
             delta--;
             drawCount++;
         } 
         
         if (timer >= 1000000000){
            // System.out.println("FBS"+drawCount);
             drawCount = 0;
             timer =0;
         }
    }
}
    public void update(){
         
         if(gameState == playState){
            player.update(); 
            for(int i = 0 ; i < npc[1].length; i++  ){
                if(npc[currentMap][i] != null ){
                    npc[currentMap][i].update();
                }
            }
            for(int i = 0; i< monster[1].length; i++){
                if(monster[currentMap][i] != null){
                    if(monster[currentMap][i].alive == true && monster[currentMap][i].dying == false){
                        monster[currentMap][i].update();

                    }
                    if(monster[currentMap][i].alive == false){
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] =null;
                    }
                }
            }
            for(int i = 0; i < projectileList.size(); i++){
                if(projectileList.get(i) != null){
                    if(projectileList.get(i).alive == true ){
                        projectileList.get(i).update();
                    }
                    if(projectileList.get(i).alive == false){
                        projectileList.remove(i);
                    }
                }
            }
            for(int i = 0; i < particleList.size(); i++){
                if(particleList.get(i) != null){
                    if(particleList.get(i).alive == true ){
                        particleList.get(i).update();
                    }
                    if(particleList.get(i).alive == false){
                        particleList.remove(i);
                    }
                }
            }
        
         for (int i = 0 ; i<iTile[1].length; i ++){
             if(iTile[currentMap][i] != null){
                 iTile[currentMap][i].update();
             }
         }
         eManager.update();
         }
         if (gameState == pauseState){
             
         }
     }
    @Override
    public void paintComponent(Graphics g ){
         
     super.paintComponent(g);
     
     Graphics2D g2 = (Graphics2D)g;
      
     //DEBUG
     
     long drawStart = 0;
     if (keyH.checkDrawTime == true){
      drawStart = System.nanoTime();     
     }
     //TITLE SCREEN 
     if (gameState == titleState){
         uii.draw(g2);
     }
     //map screen
     else if(gameState == mapState && currentMap == 0){
         map.drawFullMapScreen(g2);
     }
     else {
         
     //tile
     tileM.draw(g2);
     
     //INTERACTIVE TILE
     for(int i =0 ; i<iTile[1].length; i++){
         if(iTile[currentMap][i] != null){
             iTile[currentMap][i].draw(g2);
         }
     }
     //ADD ENTITY TO THE LIST
     entityList.add(player);
     
     for(int i =0 ; i <npc[1].length; i++){
         if(npc[currentMap][i] != null){
             entityList.add(npc[currentMap][i]);
         }
     }
     
     for ( int i =0 ; i < obj[1].length; i++){
         if(obj[currentMap][i] != null){
             entityList.add(obj[currentMap][i]);
         }
     }
         for (int i = 0 ; i < monster[1].length; i++){
             if(monster[currentMap][i] != null){
                 entityList.add(monster[currentMap][i]);
             }
         }
         for(int i = 0 ; i < projectileList.size(); i++){
             if(projectileList.get(i)!= null){
                 entityList.add(projectileList.get(i));
             }
         }
         for(int i = 0 ; i < particleList.size(); i++){
             if(particleList.get(i)!= null){
                 particleList.add(particleList.get(i));
             }
         }
    
         //SORT
         Collections.sort(entityList, new Comparator<Entity>() {
             @Override
             public int compare(Entity e1, Entity e2) {
                 int result = Integer.compare(e1.worldY, e2.worldY);
                 return result ;
             }
         });
         
         // DRAW ENTITIES
         for(int x = 0; x < entityList.size(); x++){
             entityList.get(x).draw(g2);
         }
         //EMPTY THE LIST
         entityList.clear();
         
     //ENVIRONMENT
     eManager.draw(g2);
     
     //MINIMAP
     if(currentMap == 0){
      map.drawMiniMap(g2);   
     }
     
   
     //UI
     uii.draw(g2);
     }
     
     
              

     //DEBUG
     
     if(keyH.checkDrawTime == true){
     long drawEnd = System.nanoTime();
     long passed = drawEnd - drawStart ;
     g2.setColor(Color.white);
     g2.drawString("Draw time: "+passed, 10, 400);
     System.out.println("Draw time: "+passed);
     }
     g2.dispose();
     
     }
    public void playMusic (int i ){
         music.setFile(i);
         music.play();
         music.loop();
         
     }
    public void stopMusic(){
         music.stop();
     }
    public void playSE(int i ){
     se.setFile(i);
     se.play();
     
}
}
