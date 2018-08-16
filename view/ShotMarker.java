package view;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;
import java.awt.*;
import javax.swing.*;
public class ShotMarker extends JPanel
      implements model.SceneRoom.Listener {
      private model.SceneRoom sceneRoom;
      private JLabel shotMarker;
      private int takeNumber;
      model.Area area;
      public ShotMarker(int x,int y, int h, int w,int takeNumber,model.SceneRoom sceneRoom){
         this.area = area;
         this.takeNumber = takeNumber;
         this.sceneRoom = sceneRoom;
         setOpaque(false);
         setBounds(x,y,h,w);
         shotMarker = new JLabel();
         add(shotMarker,new Integer(0));
         sceneRoom.subscribe(this);
         changed(this.sceneRoom);
      }
      

      public void changed(model.SceneRoom sceneRoom){
          
            ImageIcon icon = null;
            String fileName = "./resources/images/shot.png";
            icon = new ImageIcon(fileName);
            shotMarker.setIcon(icon);
            shotMarker.setVisible(this.takeNumber <= sceneRoom.getShotsCompleted());
       }
     
 
  }
            