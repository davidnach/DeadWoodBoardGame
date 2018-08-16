/*This class sets up the view of the gameboard. The view listens to
  changes in the model (game state) and changes accordingly*/
package view;
import model.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.*;
import java.util.HashMap;
import java.util.ArrayList;

public class Board extends JLayeredPane {
  private JLabel boardLabel;
  private model.GameController game;
  private model.SceneCardInfo sceneCardInfo;
  private model.ExtraRoleInfo extraRoleInfo;
  private model.ShotMarkerInfo shotMarkerInfo;

  public Board(model.GameController game,
  model.ExtraRoleInfo extraRoleInfo, model.ShotMarkerInfo shotMarkerInfo) throws Exception{
    this.game = game;
    this.extraRoleInfo = extraRoleInfo;
    this.shotMarkerInfo = shotMarkerInfo;;
    this.boardLabel = new JLabel();
   
    /*Initialize  gameboard*/
    ImageIcon icon = new ImageIcon("./resources/images/board.jpg");
    boardLabel.setIcon(icon);
    boardLabel.setBounds(350,0,icon.getIconWidth(),icon.getIconHeight());
    add(boardLabel,new Integer(0));
    setBounds(0,0,icon.getIconWidth() + 1000, icon.getIconHeight());
    initializeShotMarkers();
    initializeScoreBoard();
    initializeExtraRoles();
    initializeSceneCardDisplay();
    initializeSceneRoomDisplay(); 
    add(new DayDisplay(game), new Integer(3));
    add(new TurnDisplay(game), new Integer(3));

    
   
  }
  
  public void initializeExtraRoles(){
      HashMap<model.Area,String> areaToRoleName = extraRoleInfo.getAreaToRoleName();
      ExtraRole extraRole;
      String roleName;
      for(model.Area area : areaToRoleName.keySet()){
         roleName = areaToRoleName.get(area);
         extraRole = new ExtraRole(area.getX() + 350,area.getY(),
         area.getH(),area.getW(),roleName,game.getSceneRoomFromExtraName(roleName),game);
         add(extraRole,new Integer(3));
      }  
  }
  
  public void initializeSceneCardDisplay(){
    ArrayList<SceneRoom> sceneRooms = game.getSceneRooms();
    SceneCard sc;
    for(int i = 0; i < sceneRooms.size();i++){
        sc = new SceneCard(sceneRooms.get(i),game);
        
        add(sc,new Integer(3));
    }
  }
  
  private void initializeSceneRoomDisplay(){
   
    ArrayList<SceneRoom> sceneRooms = game.getSceneRooms();
    SceneroomDisplay sd;
    for(int i = 0; i < sceneRooms.size();i++){
        model.SceneRoom sr = sceneRooms.get(i);
        sd = new SceneroomDisplay(sr.getSetX(), sr.getSetY(),sr.getRoomName(),this.game.getGamePlayers());
        add(sd,new Integer(3));
    }
    
    add(new SceneroomDisplay(1000 + 350,110,"Trailer",this.game.getGamePlayers()), new Integer(3));
    add(new SceneroomDisplay(100 + 260,520,"Casting Office",this.game.getGamePlayers()), new Integer(3));
  
  }
  
  private void initializeShotMarkers(){
      HashMap<Area,Integer> areaToTakeNumber = this.shotMarkerInfo.getAreaToTakeNumber();
      HashMap<Area,String> areaToRoomName = this.shotMarkerInfo.getAreaToRoomName();
      SceneRoom sr;
      ShotMarker shotMarker;
      for(model.Area area : areaToRoomName.keySet()){
         sr = (SceneRoom)this.game.findRoom(areaToRoomName.get(area));
         shotMarker = new ShotMarker(area.getX() + 350,area.getY(),
         area.getH(),area.getW(),areaToTakeNumber.get(area),sr);
         add(shotMarker,new Integer(3));
       }
    }
    
   private void initializeScoreBoard(){
      JPanel scoreBoardBackground = new JPanel(null);
      scoreBoardBackground.setBounds(0,0,350,100);
      scoreBoardBackground.setBackground(new Color(142,71,0));
      scoreBoardBackground.setOpaque(true);
      JLabel scoreBoard = new JLabel("Scoreboard");
      scoreBoard.setAlignmentX(JLabel.CENTER_ALIGNMENT);
      scoreBoard.setFont(new Font("Serif", Font.PLAIN, 35));
      scoreBoard.setVisible(true);
      scoreBoard.setBounds(100,0,350,80);
      scoreBoardBackground.add(scoreBoard);
      
      
      JPanel scoreBoardParameters = new JPanel(new GridLayout(0,6));
      
      JLabel place = new JLabel("Place");
      scoreBoardParameters.add(place);
      
      JLabel name = new JLabel();
      name.setText("Name");
      name.setAlignmentX(JLabel.CENTER_ALIGNMENT);
      scoreBoardParameters.add(name);
      
      JLabel money = new JLabel("Money");
      money.setAlignmentX(JLabel.CENTER_ALIGNMENT);
      scoreBoardParameters.add(money);
      
      JLabel credits = new JLabel("Credits");
      scoreBoardParameters.add(credits);
      
      JLabel rehearsals = new JLabel("Rehea");
      scoreBoardParameters.add(rehearsals);
      
      JLabel rank = new JLabel("Rank");
      rank.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
      scoreBoardParameters.add(rank);
      
      scoreBoardParameters.setBounds(0,80,350,20);
      scoreBoardBackground.add(scoreBoardParameters);
      add(scoreBoardBackground, new Integer(0));  
      add(new ScoreBoard(0,100,350,820,game), new Integer(0));
   }

      
  
 
 
  
  
  
  
  
}

 
  
  
  
  

   
  
  
  

