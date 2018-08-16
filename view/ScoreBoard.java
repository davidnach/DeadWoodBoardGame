package view;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;
import java.awt.*;
import javax.swing.*;
public class ScoreBoard extends javax.swing.JPanel implements model.Actor.ActorListener{
       private class PlayerStats{
         
         JPanel container;
         JLabel place;
         JLabel name;
         JLabel money;
         JLabel credits;
         JLabel rehearsals;
         JLabel rank;
 
         public PlayerStats(int x, int y, int w, int h){
            this.container = new JPanel(new GridLayout(0,6));
            this.container.setBounds(x,y,w,h);
            this.container.setBorder(BorderFactory.createLineBorder(Color.black));
            this.place = new JLabel();
            this.name = new JLabel();
            this.money = new JLabel();
            this.credits = new JLabel();
            this.rehearsals = new JLabel();
            this.rank = new JLabel();
            this.container.add(place);
            this.container.add(name);
            this.container.add(money);
            this.container.add(credits);
            this.container.add(rehearsals);
            this.container.add(rank);
         }
      }     
      
      private model.SceneRoom sceneRoom;
      private model.GameController game;
      private JLabel menu;
      private PlayerStats[] playerStats;
      
      public ScoreBoard(int x, int y,int h, int w,model.GameController game){
         this.game = game;
         this.playerStats = new PlayerStats[game.getNumPlayers()];
         setBounds(x,y,h,w);
         setLayout(null);
         setOpaque(false);
         initializePlayerStats();
         subscribeToActors();
      }
      
      private void initializePlayerStats(){
         
         PlayerStats pStats;
         ArrayList<model.Actor> actorsSortedByScore = game.getActorsSortedByScore();
         model.Actor currentActor;
         for(int i = 0; i < game.getNumPlayers(); i++){
            currentActor = actorsSortedByScore.get(i);
            String color = currentActor.getColor();
            int rank = currentActor.getRank();
            pStats = new PlayerStats(0, i * 100, 350, 100);
            pStats.place.setText("#1");
            pStats.name.setText(currentActor.getName());
            pStats.money.setText(Integer.toString(currentActor.getMoney()));
            pStats.credits.setText(Integer.toString(currentActor.getCredit()));
            pStats.rehearsals.setText(Integer.toString(currentActor.getRehearsals()));
            pStats.rank.setIcon(new ImageIcon("./resources/images/dice/"+ color.charAt(0) + Integer.toString(rank) + ".png"));
            add(pStats.container,new Integer(1));
            this.playerStats[i] = pStats;   
         }
      
      }
      
      private void subscribeToActors(){
         ArrayList<model.Actor> actors = game.getGamePlayers();
         for(int i = 0; i < actors.size(); i++){
            actors.get(i).subscribe(this);
         }
      }
      
      public void actorChanged(model.Actor actor){
         ArrayList<model.Actor> actorsSortedByScore = game.getActorsSortedByScore();
         model.Actor currentActor;
         int prevActorPoints = 0;
         int scoreBoardPlacement = 1;
         for(int i = 0; i < actorsSortedByScore.size(); i++){
            currentActor = actorsSortedByScore.get(i);
            String color = currentActor.getColor();
            int rank = currentActor.getRank();
            if(currentActor.getActorPoints() < prevActorPoints){
               scoreBoardPlacement++;
            }
            prevActorPoints = currentActor.getActorPoints();
            playerStats[i].place.setText("#" + Integer.toString(scoreBoardPlacement));
            playerStats[i].name.setText(currentActor.getName());
            playerStats[i].money.setText(Integer.toString(currentActor.getMoney()));
            playerStats[i].credits.setText(Integer.toString(currentActor.getCredit()));
            playerStats[i].rehearsals.setText(Integer.toString(currentActor.getRehearsals()));
            playerStats[i].rank.setIcon(new ImageIcon("./resources/images/dice/"+ color.charAt(0) + Integer.toString(rank) + ".png"));
         
         }  
      }
      
}