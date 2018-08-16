package view;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
public class TurnDisplay extends JPanel
      implements model.GameController.gameListener {
      private model.GameController game;
      private JLabel turnLabel;
      public TurnDisplay(model.GameController game){
         this.game = game;
         this.turnLabel = new JLabel();
         setLayout(new GridBagLayout());
         setOpaque(true);
         setBounds(1550,820,260,80);
         setBackground(new Color(142,71,0));
         turnLabel.setFont(new Font("Serif", Font.PLAIN, 35));
         turnLabel.setVisible(true);
         add(turnLabel);
         game.subscribe(this);
         gameChanged(game);
      }
      

      public void gameChanged(model.GameController game){
            model.Actor currentActor = game.getCurrentActor();
            System.out.println("current actor " + currentActor.getName());
            String color = currentActor.getColor();
            int rank = currentActor.getRank();
            turnLabel.setText(currentActor.getName() + "'s turn");
       }
     
    
  }
