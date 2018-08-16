package model;
import java.lang.Math;
import java.util.*;
public class Scene { 

    public interface Listener {
      void sceneChanged(Scene scene);
   }
   private ArrayList<Listener> listeners;
   
   public void subscribe(Listener l){
      listeners.add(l);
   }
   
   protected void sceneChanged(){
      for(Listener l : listeners){
         l.sceneChanged(this);
      }
   }
   private ArrayList<Integer> mainActorRanks;
   private ArrayList<String> mainActorNames;
   private model.Actor[] mainActorSpots;
   private int budget;
   private String cardName;
   private model.SceneCard sceneCard;
	public Scene() {
        this.mainActorRanks = new ArrayList<Integer>();
        this.mainActorNames = new ArrayList<String>();
        this.listeners = new ArrayList<Listener>();
	}
	
    public void payMainActorBonuses(ArrayList<Integer> rolls){
        
        int indexOfActor = mainActorRanks.size() - 1;
        Collections.sort(rolls,Collections.reverseOrder());
         
        for(int i = 0; i < rolls.size(); i++){
            
            if(mainActorSpots[indexOfActor] != null){
               mainActorSpots[indexOfActor].updateWallet(rolls.get(i),0);
            }  
            if(indexOfActor == 0){
                  indexOfActor = mainActorRanks.size() - 1;
             } else {
                  indexOfActor--;
             }
         }   
    }
    
    public void updateSceneCard(model.SceneCard sceneCard){
      this.sceneCard = sceneCard;
      this.mainActorRanks.clear();
      this.mainActorNames.clear();
      this.budget = this.sceneCard.getBudget();
      Set<String> actors = this.sceneCard.getActors();
      Iterator iter = actors.iterator();
      String actor;
      int numActors = 0;
      while (iter.hasNext()) {
          actor = (String)iter.next();
          mainActorNames.add(actor);
          mainActorRanks.add(this.sceneCard.getLevelFromActor(actor));
          numActors++;
      }
      this.mainActorSpots = new model.Actor[numActors];
      System.out.println();
    }
    
    public SceneCard getSceneCard(){
      return this.sceneCard;
    }
    
    public void setBudget(int budget){
        this.budget = budget;
    }
    
    public void addMainActorRank(int rank){
        this.mainActorRanks.add(rank);
    }
    
    public void addRoleName(String name){
        this.mainActorNames.add(name);   
    }
    
    public void setSceneName(String name){
        this.cardName = name;
    }
        
	

	public boolean successfulRoll(int roll){
		if(roll >= this.budget) {
			return true;
		}
		return false;
	}
    
    
    public ArrayList<String> getMainRoleOptions(int rank){
         ArrayList<String> options = new ArrayList<String>();
         for(int i = 0; i < mainActorRanks.size(); i++){
            if(rank >= mainActorRanks.get(i) && mainActorSpots[i] == null){
               options.add(mainActorNames.get(i));
            }
          }
         return options;
        
    }
    
    public int getBudget(){
        return this.budget;
    }
    
    
    public void clearMainActors(){
        for(int i = 0; i < mainActorSpots.length;i++){
            if(mainActorSpots[i] != null){
               mainActorSpots[i].becomeUnemployed();
               mainActorSpots[i].clearRehearsals();
               mainActorSpots[i] = null;
            }
        }
        sceneChanged();
   }
    
    public String getSceneName(){
        return this.cardName;
    }
    
    public boolean hasActorAtRoleName(String roleName){
        for(int i = 0; i < mainActorNames.size(); i++){
            if(roleName.equals(mainActorNames.get(i))){
                return mainActorSpots[i] != null;
             }
         }
         return false;
    
    }
    
    public Actor getActorAtRoleName(String roleName){
      for(int i = 0; i < mainActorNames.size(); i++){
         if(roleName.equals(mainActorNames.get(i))){
                return mainActorSpots[i];
          }
      }
      return null; 
    }
    
    public void addActorAtRoleName(Actor actor, String roleName){
      int i = 0;
      while(!mainActorNames.get(i).equals(roleName))i++;
      mainActorSpots[i] = actor;
      sceneChanged();
    }
    
    public boolean hasMainRoleAvailable(int rank){
      for(int i = 0; i < mainActorRanks.size(); i++){
         if(mainActorRanks.get(i) <= rank && mainActorSpots[i] == null){
            return true;
         }
       }
       return false;
    }
    
    
    

    
}