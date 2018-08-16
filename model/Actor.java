package model;
import java.util.ArrayList;
public class Actor implements Comparable<Actor>{

     public interface ActorListener {
      void actorChanged(Actor actor);
   }
   private ArrayList<ActorListener> listeners;
   
   public void subscribe(ActorListener l){
      listeners.add(l);
   }
   
   protected void actorChanged(Actor actor){
      for(ActorListener l : listeners){
         l.actorChanged(this);
      }
   }

 
	private int rank;
	private int rehearsals;
	private Wallet wallet;
	private String currentRoom;
    private String role;
    private String name;
    private String color;
	
	public Actor(String room,String name,String color,String role,int credits,int dollars,int rank){
		this.rank = rank;
      this.color = color;
		this.wallet = new Wallet();
      this.listeners = new ArrayList<ActorListener>();
		this.currentRoom = room;
      this.name = name;
      this.rehearsals = 0;
      this.role = role;
      updateWallet(credits,dollars);
	}
	
	public int getRank(){
		return this.rank;
	}

    public String getColor(){
        return this.color;
    }
	public int getMoney(){
		return this.wallet.getMoney();
	}
	
	public int getCredit(){
		return this.wallet.getCredit();
	}
	
	public void rehearse(){
	    this.rehearsals++;
        actorChanged(this);
	}
   
   public void clearRehearsals(){
      this.rehearsals = 0;
      actorChanged(this);
   }
	
	public int getRehearsals(){
	    return this.rehearsals;
	}
   
   public boolean isUnemployed(){
      return this.role.equals("unemployed");
   }
    
    public void becomeExtra(){
        this.role = "extra";
        actorChanged(this);
    }
    
    public boolean isExtra(){
      return this.role.equals("extra");
    }
    
    public boolean isMainActor(){
      return this.role.equals("mainActor");
    }
    
    public void becomeMainActor(){
        this.role = "mainActor";
        actorChanged(this);

    }
    
    public String getRole(){
        return this.role;
    }
    
    
    public void becomeUnemployed(){
        this.role = "unemployed";
        actorChanged(this);

    }
    
    public void changeRoom(String room){
        this.currentRoom = room;
        actorChanged(this);
    }
    public String getName(){
        return this.name;
    }
    
    public void upgradeRank(int rank){
        this.rank = rank;
        actorChanged(this);
    }
    
  
    public void updateWallet(int dollars,int credits){
      wallet.addMoney(dollars);
      wallet.addCredit(credits);
      actorChanged(this);
    }
    
    public int getActorPoints(){
      return getCredit() + getMoney() + (5 * getRank());
    }
    
    public String getCurrentRoom(){
        return this.currentRoom;
    }
    
    public boolean isInSceneRoom(){
         return !(this.currentRoom.equals("Trailer")) && !(this.currentRoom.equals("Casting Office"));
    }
    
    public int compareTo(Actor actor) {
	
		int compareQuantity = actor.getActorPoints();
		
		//descending
		return compareQuantity - getActorPoints();
		
	
	}	
    
  

}
