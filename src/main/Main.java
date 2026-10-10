package main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class Main {
    
    
    public static JFrame window;

    public static void main(String[] args) {
      window = new JFrame();
     window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     window.setResizable(false);
     window.setTitle("ELLE'S JOURNEY");
     new Main().setIcon();
     
     GamePanel gamepanel = new GamePanel();
     window.add(gamepanel); 
     
     window.pack();
     
     
     window.setLocationRelativeTo(null);
     window.setVisible(true);
     
     gamepanel.setupGame();
     gamepanel.startGameThread();
     
    }
    public void setIcon (){
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("player/down_1.png"));
        window.setIconImage(icon.getImage());
    }
    
}