package model;
import java.lang.Math;
import java.util.ArrayList;
public class SceneRoom extends Room{

   public interface Listener {
      void changed(SceneRoom room);
   }
   private ArrayList<Listener> listeners;
   
   public void subscribe(Listener l){
      listeners.add(l);
   }
   
   protected void changed(){
      for(Listener l : listeners){
         l.changed(this);
      }
   }
   
   public interface ExtraRoleListener {
      void erChanged(SceneRoom room,String name);
      String getErName();
   }
   
   private ArrayList<ExtraRoleListener> erListeners;
   
   private String name;
   
   public void erSubscribe(ExtraRoleListener er){
      erListeners.add(er);
   }
   
    protected void erChanged(SceneRoom room,String name){
      for(ExtraRoleListener er : erListeners){
         if(er.getErName().compareTo(name) == 0){
            er.erChanged(this,name);
         }
      }
   }
   
   
   public String getErName(){
      return this.name;
   }
   
	private ArrayList<Integer> extraRanks;
   private ArrayList<String> extraNames;
   private Actor[] extraSpots;
   private Scene scene;
   private int shotsAvailable; 
   private int shotsComplete;
   private final Area setArea;
   private boolean sceneActive;
   private String lastExtraRoleFulfilled;
	
	public SceneRoom(String name,int shotsAvailable,Area setArea){
        super(name);
        this.setArea = setArea;
        this.listeners = new ArrayList<Listener>();
        this.erListeners = new ArrayList<ExtraRoleListener>();
        this.extraNames = new ArrayList<String>();
        this.extraRanks = new ArrayList<Integer>();
        this.scene = new Scene();
		  this.shotsComplete = 0;
		  this.shotsAvailable = shotsAvailable;
        this.sceneActive = false;
     }
       
    
	
	public boolean hasScene(){
		return this.scene != null;
	}
	
	public void completeShot(){
		this.shotsComplete++;
      changed();
	}
   
   
   public int getShotsAvailabe(){
      return this.shotsAvailable;
   }
   
   public void addExtraName(String name){
      this.extraNames.add(name);
   }
   
   public int getShotsCompleted(){
      return this.shotsComplete;
   }
   
   public boolean isTaken(String name){
      for(int i = 0; i < extraNames.size(); i++){
         if(name.equals(extraNames.get(i))){
            if(extraSpots[i] != null){
               return true;
            }
         }
      }
      return false;
   }
	
	public boolean addExtra (Actor actor, int rank,String extraName){
        if((hasScene() && !shotsComplete())){
            if(actor.getRank() >= rank){
                if(addExtraHelper(actor,rank,extraName)){
                    actor.becomeExtra();
                    erChanged(this,extraName);
                    return true;
                }
             }
        }
        System.out.println("Actor could not acquire extra spot");
        return false;
     }
                
           
    private boolean addExtraHelper(Actor actor, int rank,String extraName){
        for(int i = 0; i < this.extraRanks.size(); i++){
            if((this.extraRanks.get(i) <= rank && this.extraNames.get(i).equals(extraName)) &&
                this.extraSpots[i] == null){
                this.extraSpots[i] = actor;
                return true;
            }
        }
        return false;
    }
    
    public void resetSceneRoom(){
        this.scene = null;
    }
    
    public ArrayList<String> getMainRoleOptions(int rank){
      return this.scene.getMainRoleOptions(rank);
    }
    
    public boolean shotsComplete(){
        return this.shotsAvailable == this.shotsComplete;
    }
    
    public ArrayList<String> getExtraRoles(int rank){
        ArrayList<String> extraRoles = new ArrayList<String>();
        for(int i = 0; i < extraRanks.size();i++){
            if(rank >= extraRanks.get(i) && extraSpots[i] == null){
                extraRoles.add(extraNames.get(i));
            }
        }
        return extraRoles;
    }
	
	
	public boolean hasExtraRoleAvailable(int rank){
	    for(int i = 0; i < this.extraRanks.size(); i++){
            if(this.extraRanks.get(i) <= rank && this.extraSpots[i] == null){
                return true;
            }
        }
        return false;
	}
	
	public Scene getScene(){
	    return this.scene;
	}
   

	public void payExtraBonuses(){
	    for(int i = 0; i < extraRanks.size(); i++){
	        if(extraSpots[i] != null){
	            extraSpots[i].updateWallet(extraRanks.get(i),0);
	        }
	    }
	}
	
    public void addExtraRank(int rank){
        this.extraRanks.add(rank);
    }
    
    public void clearRoleSpots(){
        Actor actor;
        for(int i = 0; i < this.extraSpots.length;i++){
            if(extraSpots[i] != null){
               actor = extraSpots[i];
               extraSpots[i] = null;
               actor.becomeUnemployed();
               actor.clearRehearsals();
               erChanged(this,extraNames.get(i));
            }
         }
         this.scene.clearMainActors();
     }
    
    public void setNumExtras(int numExtras){
        this.extraSpots = new Actor[numExtras];
    }
    
    public int getSceneBudget(){
        return this.scene.getBudget();
    }
    
    
    public void updateScene(String sceneName, int sceneBudget){
        scene.setBudget(sceneBudget);
        scene.setSceneName(sceneName);
    }
    public void addActor(Actor actor){
		actorsInRoom.add(actor);
        this.sceneActive = true;
        changed();
	}
    
   public String getSceneName(){
        return scene.getSceneName();
   }
   
   public void resetScene(){
      this.sceneActive = false;
      this.shotsComplete = 0;
      changed();
   }
   public SceneCard getSceneCard(){
      return this.scene.getSceneCard();
   
   }
   
   public boolean sceneActive(){
    return this.sceneActive;
   }
   
   public int getSetX(){
    return setArea.getX();
   }
   
   public int getSetY(){
    return setArea.getY();
   }
   
   public int getSetW(){
    return setArea.getW();
   }
   
   public int getSetH(){
    return setArea.getH();
   }

}	
