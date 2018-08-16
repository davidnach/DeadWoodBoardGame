package model;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
public class GameController { 

    public interface gameListener {
        void gameChanged(GameController game);
    }
    private ArrayList<gameListener> listeners;
    
    public void subscribe(gameListener l){
        listeners.add(l);
    }
    
    protected void gameChanged(GameController game){
        for(gameListener l : listeners) {
            l.gameChanged(this);
         }
    }
    private ArrayList<Room> gameMap;
    public ArrayList<Actor> gamePlayers;
    private ArrayList<Integer>turnOrder;
    private int turn;
    private int numRounds;
    private int currentRound;
    private int numPlayers;
    private int completedScenes;
    private SceneCardInfo sceneInfo;
    private HashMap<String,Integer> sceneNameToBudget;
    private ArrayList<model.SceneCard> sceneCards;
    private HashMap<String,Integer> roomNameToRoom;
    private HashMap<String,SceneRoom> extraNameToSceneRoom;
    
    public GameController(int numPlayers,ArrayList<SceneCard> sceneCards) {
        this.sceneCards = sceneCards;
        this.gameMap = new ArrayList <Room>();
        this.gamePlayers = new ArrayList<Actor>();
        this.turnOrder = new ArrayList<Integer>();
        this.listeners = new ArrayList<gameListener>();
        this.sceneCards = sceneCards;
        setNumRounds(numPlayers);
        this.turn = 0;
        this.numPlayers = numPlayers;
        this.currentRound = 0;
        this.completedScenes = 0;
        this.roomNameToRoom = new HashMap<String,Integer>();
        this.extraNameToSceneRoom = new HashMap<String,SceneRoom>();
    }
    
    
    public void setNumRounds (int numPlayers){
        if(numPlayers < 4) {
            this.numRounds = 3;
        }else {
            this.numRounds = 4;
        }
     }
     
     public int getDay(){
        return this.currentRound;
     }
     
     
     public void mapExtraNameToSceneRoom(String extraName, SceneRoom sr){
            this.extraNameToSceneRoom.put(extraName,sr);
     }
     
     
     public void setActorRanks(){
         for(int i = 0; i < gamePlayers.size(); i++){
             if(this.numPlayers > 6){
                 gamePlayers.get(i).upgradeRank(2);
             } else {
                 gamePlayers.get(i).upgradeRank(1);
             }
         }
    }
    
    public void setActorCredits(){
        for(int i = 0; i < gamePlayers.size();i++){
            if(this.numPlayers == 6){
                gamePlayers.get(i).updateWallet(0,4);
            }else if(this.numPlayers == 5){
                gamePlayers.get(i).updateWallet(0,4);
            }
        }
    }
     
    public void addRoom(Room room){
        gameMap.add(room);
        this.roomNameToRoom.put(room.getRoomName(),gameMap.size() - 1);
    } 
    
    public void addActor(Actor actor){
        gamePlayers.add(actor);
    }
    
    public void resetBoard(){
        this.completedScenes = 0;
        this.turn = 0;
        for(int i = 0; i < this.gameMap.size(); i++){
            Room room = gameMap.get(i);
            room.clearRoom();
            if(!(room.getRoomName().equals("Trailer")) && 
                !(room.getRoomName().equals("Casting Office"))){
                   ((SceneRoom) room).resetSceneRoom();
            }
        }
        for(int i = 0; i < this.gamePlayers.size();i++){
            this.gamePlayers.get(i).getName().equals("Trailer");
        }
        addActorsToRoom("Trailer");
    }
   
    
    public boolean checkRoundOver(){
        return (this.gameMap.size() - 10 == this.completedScenes);
    }
    
    public void setTurnOrder(ArrayList <Integer> turnOrder){
     this.turnOrder = turnOrder;   
    }
    
    public boolean checkGameOver(){
        return(this.currentRound == this.numRounds);
    }
    
    public void addActorsToRoom(String roomName){
        Room room = findRoom(roomName);
        for(int i = 0; i < this.numPlayers; i++){
            room.addActor(this.gamePlayers.get(i));
        }
    }
    
    public Room findRoom(String room){
        return gameMap.get(roomNameToRoom.get(room));
    }
    
    public Actor getCurrentActor(){
        return this.gamePlayers.get(this.turn);
    }
    
    public void incrementTurn(){
        this.turn = (this.turn + 1) % this.numPlayers;
        gameChanged(this);
    }
    
    
    public ArrayList<Actor> getActorsSortedByScore(){
        ArrayList<Actor> sorted = new ArrayList<Actor>();
     
        for(int i = 0; i < this.gamePlayers.size(); i++){
            sorted.add(gamePlayers.get(i));
        }
        Collections.sort(sorted);
        return sorted;
    }
    
    public void completeScene(){
        this.completedScenes++;
        if(checkRoundOver()){
            currentRound++;
            if(checkGameOver()){
               String winner = getActorsSortedByScore().get(0).getName();
               JOptionPane.showMessageDialog(null, "The winner is " + winner, "Game Over", JOptionPane.INFORMATION_MESSAGE);
               System.exit(0);
            } else {
               this.completedScenes = 0;
               updateScenes();
               gameChanged(this);
            }
        }
    }
    public void unemployActors(String roomName){
        for(int i = 0; i < gamePlayers.size();i++){
            if(gamePlayers.get(i).getCurrentRoom().equals(roomName)){
                gamePlayers.get(i).becomeUnemployed();
            }
        }
    }
    
    public SceneRoom getSceneRoomFromExtraName(String extraName){
        return this.extraNameToSceneRoom.get(extraName);
     } 
     
     
     public void updateScenes(){
        Collections.shuffle(this.sceneCards);
        ArrayList<SceneRoom> sceneRooms = getSceneRooms();
        String roomName;
        Scene scene;
        SceneRoom sceneRoom;
        
        for(int i = 0; i < sceneRooms.size(); i++){
            sceneRoom = sceneRooms.get(i);
            sceneRoom.resetScene();
            scene = sceneRoom.getScene();
            scene.updateSceneCard(this.sceneCards.get(i));
        }         
      }
        
     
     
     public ArrayList<SceneRoom> getSceneRooms(){
        ArrayList<SceneRoom> sceneRooms = new ArrayList<SceneRoom>();
        String roomName;
        for(int i = 0; i < this.gameMap.size(); i++){
            roomName = gameMap.get(i).getRoomName();
            if(!roomName.equals("Casting Office") && !roomName.equals("Trailer")){
                sceneRooms.add(((SceneRoom)gameMap.get(i)));
            }
        }
        return sceneRooms;
     }
     

     public int getNumPlayers(){
         return this.numPlayers;
     }
     
     public ArrayList<Actor> getGamePlayers(){
         return this.gamePlayers;
     }
    
    
}