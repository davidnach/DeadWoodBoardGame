package view;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
public class DayDisplay extends JPanel
      implements model.GameController.gameListener {
      private model.GameController game;
      private JLabel dayLabel;
      public DayDisplay(model.GameController game){
         this.game = game;
         setLayout(new GridBagLayout());
         setOpaque(true);
         setBounds(1550,0,260,80);
         setBackground(new Color(142,71,0));
         dayLabel = new JLabel();
         //shotMarker.setBounds(0,0,75,75);
         //shotMarker.setText("text");
         dayLabel.setFont(new Font("Serif", Font.PLAIN, 35));
         dayLabel.setVisible(true);
         add(dayLabel);
         //shotMarker.setBounds(x,y,h,w);
         //add(shotMarker,new Integer(0));
         game.subscribe(this);
         gameChanged(game);
      }
      
      // private static ImageIcon getIcon(String file){
//          ImageIcon icon = null;
//          try {
//             Class cls = ExtraRole.class;
//             icon = new ImageIcon(ImageIO.read(cls.getResourceAsStream(file)));
//              } catch (Exception e) {
//                e.printStackTrace();
//                System.exit(1);
//              }
//              return icon;
//          }
      public void gameChanged(model.GameController game){
              dayLabel.setText("Day " + (game.getDay() + 1));
           // ImageIcon icon = null;
          // 
//             String fileName = "./view/shot.png";
//             icon = new ImageIcon(fileName);
//             //paintComponent(null);
//             shotMarker.setIcon(icon);
//             shotMarker.setVisible(this.takeNumber <= sceneRoom.getShotsCompleted());
       }
     
    
  }
           