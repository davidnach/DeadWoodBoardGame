package view;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;
import java.awt.*;
import javax.swing.*;
public class SceneroomDisplay extends javax.swing.JPanel 
implements model.Actor.ActorListener {
      private String roomname;
      private ArrayList<model.Actor> gamePlayers;
      private JLabel menu;
      private ArrayList<JLabel> playerIcons;
      public SceneroomDisplay(int x, int y,String roomname, ArrayList<model.Actor> gamePlayers){
         this.gamePlayers = gamePlayers;
         this.playerIcons = new ArrayList<JLabel>();
         this.roomname = roomname;
         setLayout(null);
         ImageIcon icon = new ImageIcon("./resources/images/dice/b1.png");
         setBounds(x,y + 125,icon.getIconHeight() + 20 * 7,icon.getIconWidth());
         setOpaque(false);
         subscribeToActors();
         initializePlayerIcons();
       
       
      }
    
     public void actorChanged(model.Actor actor){
         JLabel currentJLabel;
         ImageIcon currentIcon;
         model.Actor currentActor;
         boolean actorFound = false;
        for(int i = 0; i < gamePlayers.size() && actorFound == false;i++){
               currentActor = this.gamePlayers.get(i);
               if(currentActor == actor){
               actorFound = true;
               currentJLabel = playerIcons.get(i);
               currentIcon = new ImageIcon("./resources/images/dice/"+ currentActor.getColor().charAt(0)
                + Integer.toString(currentActor.getRank()) + ".png");
                currentJLabel.setIcon(currentIcon);
               currentJLabel.setVisible(currentActor.getCurrentRoom().equals(this.roomname) &&
             currentActor.getRole().equals("unemployed"));
              }
           
          }
 
        
     }
     
     public void initializePlayerIcons(){
         JLabel currentJLabel;
         ImageIcon currentIcon;
         model.Actor currentActor;
         for(int i = 0; i < gamePlayers.size();i++){
            currentActor = this.gamePlayers.get(i);
            currentJLabel = new JLabel();
            currentIcon = new ImageIcon("./resources/images/dice/"+ currentActor.getColor().charAt(0)
             + Integer.toString(currentActor.getRank()) + ".png");
             currentJLabel.setIcon(currentIcon);
            currentJLabel.setIcon(currentIcon);
            add(currentJLabel);
            currentJLabel.setBounds(i * 20, 0, currentIcon.getIconHeight(), currentIcon.getIconWidth());
            currentJLabel.setVisible(currentActor.getCurrentRoom().equals(this.roomname) &&
           currentActor.getRole().equals("unemployed"));
            
           this.playerIcons.add(currentJLabel);
       }
      }
      
      private void subscribeToActors(){
            for(int i = 0; i < gamePlayers.size(); i++){
               gamePlayers.get(i).subscribe(this);
             }
      
      }
          
          

     
     
  }
       