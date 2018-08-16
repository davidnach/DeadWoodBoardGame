package view;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.util.HashMap;
public class SceneCard extends javax.swing.JLayeredPane
      implements model.SceneRoom.Listener, model.Scene.Listener, model.GameController.gameListener {
      private model.SceneRoom sceneRoom;
      private model.SceneCard sceneCard;
      private model.GameController game;
      private int setX;
      private int setY;
      private JLabel card;
      private Set<String> mainActors;
      private ArrayList<JLabel> mainActorSpots;
      private ArrayList<String> mainActorNames;
      
      JLayeredPane boardPane;
      public SceneCard(model.SceneRoom sceneRoom, model.GameController game){
         this.game = game;
         this.sceneRoom = sceneRoom;
         this.sceneCard = sceneRoom.getSceneCard();
         this.mainActors = this.sceneCard.getActors();
         this.mainActorSpots = new ArrayList<JLabel>();
         this.mainActorNames = new ArrayList<String>();
         this.setX = sceneRoom.getSetX();
         this.setY = sceneRoom.getSetY();
         
         ImageIcon icon = new ImageIcon("./resources/images/cards/01.png");
         setBounds(sceneRoom.getSetX(),sceneRoom.getSetY(),icon.getIconWidth(),icon.getIconHeight());
         card = new JLabel();
         card.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
         add(card,new Integer(0));
         sceneRoom.subscribe(this);
         sceneRoom.getScene().subscribe(this);
         game.subscribe(this);
         createMainActorSpots();
         changed(sceneRoom);
         
       
      }
      

      private void createMainActorSpots(){
         ImageIcon icon = new ImageIcon("./resources/images/dice/b1.png");
         JLabel spot;
         Iterator iter = mainActors.iterator();
         String actor;
         model.Area actorArea;
         while (iter.hasNext()) {
             actor = (String)iter.next();
             actorArea = this.sceneCard.getAreaFromActor(actor);
             spot = new JLabel();
             spot.setBounds(actorArea.getX(),actorArea.getY(),icon.getIconWidth(),icon.getIconHeight());
             spot.setIcon(icon);
             spot.setVisible(false);
             mainActorNames.add(actor);
             mainActorSpots.add(spot);
             add(spot,new Integer(1));
         }

      }
      
      public void gameChanged(model.GameController game){
         if(this.sceneCard != sceneRoom.getSceneCard()){
            this.sceneCard = sceneRoom.getSceneCard();
            mainActors = this.sceneCard.getActors();
            mainActorSpots.clear();
            mainActorNames.clear();
            createMainActorSpots();
         }
      }
      
      public void changed(model.SceneRoom sceneRoom){
         
         String name = sceneRoom.getRoomName();
         ImageIcon icon = new ImageIcon("./resources/images/cards/" + this.sceneCard.getImageFilePath());
         card.setIcon(icon);
         card.setVisible(sceneRoom.sceneActive());
   
     }
     
     
     public void sceneChanged(model.Scene scene){
         model.Actor actor;
         ImageIcon icon = null;
         for(int i = 0; i < mainActorNames.size(); i++){
            if(scene.hasActorAtRoleName(mainActorNames.get(i))){
               actor = scene.getActorAtRoleName(mainActorNames.get(i));
               icon = new ImageIcon("./resources/images/dice/"+ actor.getColor().charAt(0)
                + Integer.toString(actor.getRank()) + ".png");
               mainActorSpots.get(i).setIcon(icon);
               mainActorSpots.get(i).setVisible(true);
            } else {
               mainActorSpots.get(i).setVisible(false);
            }
         }
     }
 }

     
    