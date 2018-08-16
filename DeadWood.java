/*Responsible for setting up the game and launching game window*/
import model.*;
import view.*;
import controller.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;
import java.util.Arrays;
import java.lang.Math;
import javax.swing.*;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;

public class DeadWood {


private static class closeWin extends WindowAdapter {
	public void windowClosing(WindowEvent e){
		System.exit(0);
	}
}


public static void main(String[] args)throws Exception {
    Dice dice = new Dice();
    SceneCardInfo scenecardInfo = new model.SceneCardInfo("./resources/imageInfo/scenecardData.xml");
    model.ExtraRoleInfo extraRoleInfo = new model.ExtraRoleInfo("./resources/imageInfo/boardData.xml");
    model.ShotMarkerInfo shotMarkerInfo = new model.ShotMarkerInfo("./resources/imageInfo/boardData.xml");
    ArrayList<model.SceneCard> sceneCards = createSceneCards(scenecardInfo);
    
    /*game setup*/
    int numPlayers = getNumPlayersFromUser(); 
    GameController game = new GameController(numPlayers,sceneCards);
    game.setActorRanks();
    game.setActorCredits();
    determineTurnOrder(game,numPlayers,dice);
    initializeGameBoard(game);
    addActors(game,numPlayers);
    game.updateScenes();
    
    

   /*GUI setup*/
    JFrame frame = new JFrame(); 
    frame.setTitle("Deadwood");   
    frame.setPreferredSize(new Dimension(1815,920));
    frame.setResizable(false);
	 frame.addWindowListener(new closeWin());
    frame.getContentPane().setBackground( new Color(68, 102, 0));
    
    JLayeredPane pane = new JLayeredPane();
    
    view.Board boardView = new view.Board(game,extraRoleInfo,shotMarkerInfo);
    controller.Controller boardControl = new Controller(350,0,1500,900,game,extraRoleInfo);
    
    pane.add(boardView,new Integer(0));
    pane.add(boardControl,new Integer(1));
    pane.setVisible(true); 
    frame.add(pane);
    frame.pack();
    boardView.setVisible(true);
    frame.setVisible(true);   

}



public static void initializeGameBoard(GameController game){
    SceneRoom r1 = new SceneRoom("Main Street",3,new model.Area(969 + 350,28,115,205));
    r1.addExtraRank(1);
    r1.addExtraName("Railroad Worker");
    game.mapExtraNameToSceneRoom("Railroad Worker",r1);
    r1.addExtraRank(2);
    r1.addExtraName("Falls off Roof");
    game.mapExtraNameToSceneRoom("Falls off Roof",r1);
    r1.addExtraRank(2);
    r1.addExtraName("Woman in Black Dress");
    game.mapExtraNameToSceneRoom("Woman in Black Dress",r1);
    r1.addExtraRank(4);
    r1.addExtraName("Mayor McGinty");
    game.mapExtraNameToSceneRoom("Mayor McGinty",r1);
    r1.setNumExtras(4);
    game.addRoom(r1);
    SceneRoom r2 = new SceneRoom("Saloon",2,new model.Area(631+ 350,280,115,205));
    r2.addExtraRank(1);
    r2.addExtraName("Reluctant Farmer");
    game.mapExtraNameToSceneRoom("Reluctant Farmer",r2);
    r2.addExtraRank(2);
    r2.addExtraName("Woman in Red Dress");
    game.mapExtraNameToSceneRoom("Woman in Red Dress",r2);
    r2.setNumExtras(2);
    game.addRoom(r2);
    SceneRoom r3 = new SceneRoom("Jail",1,new model.Area(281 + 350,27,115,205));
    r3.addExtraRank(3);
    r3.addExtraName("Feller in Irons");
    game.mapExtraNameToSceneRoom("Feller in Irons",r3);
    r3.addExtraRank(2);
    r3.addExtraName("Prisoner In Cell");
    game.mapExtraNameToSceneRoom("Prisoner In Cell",r3);
    r3.setNumExtras(2);
    game.addRoom(r3);
    SceneRoom r4 = new SceneRoom("General Store",2,new model.Area(370 + 350,282,115,205));
    r4.addExtraRank(1);
    r4.addExtraName("Man in Overalls");
    game.mapExtraNameToSceneRoom("Man in Overalls",r4);
    r4.addExtraRank(3);
    r4.addExtraName("Mister Keach");
    game.mapExtraNameToSceneRoom("Mister Keach",r4);
    r4.setNumExtras(2);
    game.addRoom(r4);
    SceneRoom r5 = new SceneRoom("Train Station",3,new model.Area(21 + 350,69,115,205));
    r5.addExtraRank(1);
    r5.addExtraName("Dragged by Train");
    game.mapExtraNameToSceneRoom("Dragged by Train",r5);
    r5.addExtraRank(1);
    r5.addExtraName("Crusty Prospector");
    game.mapExtraNameToSceneRoom("Crusty Prospector",r5);
    r5.addExtraRank(2);
    r5.addExtraName("Preacher with Bag");
    game.mapExtraNameToSceneRoom("Preacher with Bag",r5);
    r5.addExtraRank(4);
    r5.addExtraName("Cyrus the Gunfighter");
    game.mapExtraNameToSceneRoom("Cyrus the Gunfighter",r5);
    r5.setNumExtras(4);
    game.addRoom(r5);
    SceneRoom r6 = new SceneRoom("Ranch",2,new model.Area(252 + 350,478,115,205));
    r6.addExtraRank(1);
    r6.addExtraName("Shot in Leg");
    game.mapExtraNameToSceneRoom("Shot in Leg",r6);
    r6.addExtraRank(2);
    r6.addExtraName("Saucy Fred");
    game.mapExtraNameToSceneRoom("Saucy Fred",r6);
    r6.addExtraRank(3);
    r6.addExtraName("Man Under Horse");
    game.mapExtraNameToSceneRoom("Man Under Horse",r6);
    r6.setNumExtras(3);
    game.addRoom(r6);
    SceneRoom r7 = new SceneRoom("Secret Hideout",3 ,new model.Area(27 + 350,732,115,205));
    r7.addExtraRank(1);
    r7.addExtraName("Clumsy Pit Fighter");
    game.mapExtraNameToSceneRoom("Clumsy Pit Fighter",r7);
    r7.addExtraRank(2);
    r7.addExtraName("Thug with Knife");
    game.mapExtraNameToSceneRoom("Thug with Knife",r7);
    r7.addExtraRank(3);
    r7.addExtraName("Dangerous Tom");
    game.mapExtraNameToSceneRoom("Dangerous Tom",r7);
    r7.addExtraRank(4);
    r7.addExtraName("Penny, who is lost");
    game.mapExtraNameToSceneRoom("Penny, who is lost",r7);
    r7.setNumExtras(4);
    game.addRoom(r7);
    SceneRoom r8 = new SceneRoom("Bank",1,new model.Area(623 + 350,475,115,205));
    r8.addExtraRank(2);
    r8.addExtraName("Suspicious Gentleman");
    game.mapExtraNameToSceneRoom("Suspicious Gentleman",r8);
    r8.addExtraRank(3);
    r8.addExtraName("Flustered Teller");
    game.mapExtraNameToSceneRoom("Flustered Teller",r8);
    r8.setNumExtras(2);
    game.addRoom(r8);
    SceneRoom r9 = new SceneRoom("Church",2,new model.Area(623 + 350,734,115,205));
    r9.addExtraRank(1);
    r9.addExtraName("Dead Man");
    game.mapExtraNameToSceneRoom("Dead Man",r9);
    r9.addExtraRank(2);
    r9.addExtraName("Crying Woman");
    game.mapExtraNameToSceneRoom("Crying Woman",r9);
    r9.setNumExtras(2);
    game.addRoom(r9);
    SceneRoom r10 = new SceneRoom("Hotel",3,new model.Area(969 + 350,740,115,205));
    r10.addExtraRank(1);
    r10.addExtraName("Faro Player");
    game.mapExtraNameToSceneRoom("Faro Player",r10);
    r10.addExtraRank(1);
    r10.addExtraName("Sleeping Drunkard");
    game.mapExtraNameToSceneRoom("Sleeping Drunkard",r10);
    r10.addExtraRank(2);
    r10.addExtraName("Falls from Balcony");
    game.mapExtraNameToSceneRoom("Falls from Balcony",r10);
    r10.addExtraRank(3);
    r10.addExtraName("Australian Bartender");
    game.mapExtraNameToSceneRoom("Australian Bartender",r10);
    r10.setNumExtras(4);
    game.addRoom(r10);
    Trailer r11 = new Trailer();
    game.addRoom(r11);
    CastingOffice r12 = new CastingOffice();
    game.addRoom(r12);
    r1.addAdjacentRooms("Trailer","Saloon","Jail");
    r2.addAdjacentRooms("Trailer","Main Street","General Store");
    r2.addAdjacentRoom("Bank");
    r3.addAdjacentRooms("Main Street","General Store","Train Station");
    r4.addAdjacentRooms("Jail","Train Station");
    r4.addAdjacentRooms("Ranch","Saloon");
    r5.addAdjacentRooms("Jail","General Store","Casting Office");
    r6.addAdjacentRooms("Secret Hideout","Casting Office");
    r6.addAdjacentRooms("General Store","Bank");
    r7.addAdjacentRooms("Ranch","Casting Office","Church");
    r8.addAdjacentRooms("Ranch","Hotel","Church");
    r8.addAdjacentRoom("Saloon");
    r9.addAdjacentRooms("Bank","Secret Hideout","Hotel");
    r10.addAdjacentRooms("Trailer","Bank","Church");
    r11.addAdjacentRooms("Hotel","Saloon","Main Street");
    r12.addAdjacentRooms("Train Station","Ranch","Secret Hideout");
   }

 
    public static void determineTurnOrder(GameController game, int numPlayers,Dice dice){
        int random;
        ArrayList<Integer> rollNumber = new ArrayList<Integer>();
        ArrayList<Integer> turnOrder = new ArrayList<Integer>();
        HashSet<Integer> hasBeenRolled = new HashSet<Integer>();
        HashMap<Integer,Integer> rollToActor = new HashMap<Integer,Integer>();

        for(int i = 0; i < numPlayers; i++){
            random = 1 + (int)(Math.random() * 100000);
            while(hasBeenRolled.contains(random)){
                random = 1 + (int)(Math.random() * 100000);
            }
            hasBeenRolled.add(random);
            rollNumber.add(random);
            rollToActor.put(random,i);
        }

        Collections.sort(rollNumber);
        for(int i = numPlayers - 1; i >= 0; i--){
            turnOrder.add(rollToActor.get(rollNumber.get(i)));

        }
        game.setTurnOrder(turnOrder);

    }

   private static int getNumPlayersFromUser(){
      String userInput = JOptionPane.showInputDialog("Enter amount of players in the range of 2-8 you want to play with");
      int numPlayers = Integer.parseInt(userInput);
      while(numPlayers < 2 || numPlayers > 8){
            userInput = JOptionPane.showInputDialog("Make sure to enter a number in the range of 2-8 ;)");
            numPlayers = Integer.parseInt(userInput);   
      }
      return numPlayers;
   }
   
   
   private static ArrayList<model.SceneCard> createSceneCards(SceneCardInfo scenecardInfo){
        
        ArrayList<model.SceneCard> sceneCards = new ArrayList<model.SceneCard>();
        HashMap<String,Integer> sceneNameToBudget = scenecardInfo.getSceneNameToBudget();
        HashMap<String,String> sceneNameToImage = scenecardInfo.getSceneNameToImage();
        HashMap<String,Integer> actorToLevel = scenecardInfo.getActorToLevel();
        HashMap<String,String> actorToSceneName = scenecardInfo.getActorToSceneName();
        HashMap<String,Area> actorToArea = scenecardInfo.getActorToArea();
        model.SceneCard sc;        
        for(String sceneName : sceneNameToBudget.keySet()){
            sc = new model.SceneCard(sceneName,sceneNameToImage.get(sceneName),sceneNameToBudget.get(sceneName));
            for(String actor : actorToSceneName.keySet()){
                if(actorToSceneName.get(actor).equals(sceneName)){
                    sc.addActor(actor,actorToArea.get(actor),actorToLevel.get(actor));
                }
            }
             sceneCards.add(sc);
        }
        return sceneCards;         
     }

   
   private static void addActors(GameController game, int numPlayers){ 
       HashMap<Integer,String> intToColor = new HashMap<Integer,String>();
       intToColor.put(0,"red");
       intToColor.put(1,"cyan");
       intToColor.put(2,"green");
       intToColor.put(3,"orange");
       intToColor.put(4,"pink");
       intToColor.put(5,"red");
       intToColor.put(6,"violet");
       intToColor.put(7,"yellow"); 
       
       Object[] inputMessages = new Object[numPlayers * 2];
       JTextField [] playerNames = new JTextField[numPlayers];
       
       for(int i = 0; i < numPlayers; i++){
          playerNames[i] = new JTextField();
         inputMessages[2 * i] = "player " + (i + 1);
         inputMessages[2 * i + 1] = playerNames[i];
      }
      
     String name;
     Actor actor;
     Color color;
     int option = JOptionPane.showConfirmDialog(null, inputMessages, "Login",JOptionPane.OK_CANCEL_OPTION);
     if (option == JOptionPane.OK_OPTION) {
        for(int i = 0; i < numPlayers; i++){
            name = playerNames[i].getText();
            if(numPlayers < 5){
               actor = new Actor("Trailer",name,intToColor.get(i),"unemployed",0,0,1);
            } else if(numPlayers == 5){
               actor = new Actor("Trailer",name,intToColor.get(i),"unemployed",2,0,1);
            } else if(numPlayers == 6){
               actor = new Actor("Trailer",name,intToColor.get(i),"unemployed",4,0,1);
            } else {
               actor = new Actor("Trailer",name,intToColor.get(i),"unemployed",0,0,2);
            }
            game.addActor(actor);
        }
   } else {
     System.exit(0); 
   }

}
}
