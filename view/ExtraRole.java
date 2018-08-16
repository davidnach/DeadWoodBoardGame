package view;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;
import java.awt.*;
import javax.swing.*;
public class ExtraRole extends JPanel
      implements model.SceneRoom.ExtraRoleListener {
      private model.SceneRoom sceneRoom;
      private model.GameController game;
      private JLabel dice;
      private String name;
      private model.Area area;
      JLayeredPane boardPane;
      
      public ExtraRole(int x, int y,int h, int w, String name,model.SceneRoom sr,
      model.GameController game){
         this.game = game;
         this.sceneRoom = sr;
         this.game = game;
         this.name = name;
         this.area = new model.Area(x,y,h,w);
         dice = new JLabel();
         dice.setBounds(0,0,h,w);
         setOpaque(false);
         setBounds(x,y,h,w);
         add(dice,new Integer(0));
         sceneRoom.erSubscribe(this);
      }
      
    
      public void erChanged(model.SceneRoom sceneRoom,String name){
         if(sceneRoom.isTaken(name)){
            model.Actor currentActor = game.getCurrentActor();
            ImageIcon icon = null;
            String color = currentActor.getColor();
            int rank = currentActor.getRank();
            String fileName = "./resources/images/dice/"+ color.charAt(0) + Integer.toString(rank) + ".png";
            icon = new ImageIcon(fileName);
            dice.setIcon(icon);
            dice.setVisible(true);
          } else {
            dice.setVisible(false);
          }
       }
     
     public String getErName(){
      return this.name;
     };
     
  }
            