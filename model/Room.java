package model;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
public class Room {
	private String name;
	private HashSet <String> adjacentRooms;
   protected ArrayList <Actor> actorsInRoom;
	public Room(String name){
		this.name = name;
		this.adjacentRooms = new HashSet<String>();
		this.actorsInRoom = new ArrayList<Actor>();
	}
	
	public boolean isAdjacent(String room){
		if(adjacentRooms.contains(room)){
		    return true;
		}
		return false;
	}

	public void addActor(Actor actor){
		actorsInRoom.add(actor);
	}

	public void removeActor(Actor actor){
		actorsInRoom.remove(actor);
	}
    
    public String getRoomName(){
        return this.name;
    }
    
    public void addAdjacentRoom(String room){
        adjacentRooms.add(room);
    }    

    public void addAdjacentRooms(String roomOne, String roomTwo){
        addAdjacentRoom(roomOne);
        addAdjacentRoom(roomTwo);
    }
    public void addAdjacentRooms(String roomOne, String roomTwo,String roomThree){
        addAdjacentRooms(roomOne,roomTwo);
        addAdjacentRoom(roomThree);
    }
    
    public void clearRoom(){
    	actorsInRoom.clear();
    }
    
    public HashSet<String> getAdjacentRooms(){
    	HashSet<String> adjacentRooms = new HashSet<String> (this.adjacentRooms);
    	return adjacentRooms;
    }
    
    
}