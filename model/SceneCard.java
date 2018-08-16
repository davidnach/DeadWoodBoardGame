package model;
import java.util.HashMap;
import java.util.Set;
public class SceneCard{
   
   private int budget;
   private String name;
   private String imgFilePath;
   private HashMap<String,Area> actorToArea = new HashMap<String,Area>();
   private HashMap<String,Integer> actorToLevel = new HashMap<String,Integer>();

   public SceneCard(String name, String imgFilePath, int budget){
      this.budget = budget;
      this.name = name;
      this.imgFilePath = imgFilePath;
      this.actorToArea = new HashMap<String,Area>();
      this.actorToLevel = new HashMap<String,Integer>();
   }
   
   public void addActor(String actor, Area area, int level){
      this.actorToArea.put(actor,area);
      this.actorToLevel.put(actor,level);
   }
   
   public Area getAreaFromActor(String actor){
      return this.actorToArea.get(actor);
   }
   
   public int getLevelFromActor(String actor){
      return this.actorToLevel.get(actor);
   }
   public String getImageFilePath(){
      return this.imgFilePath;
   }
   
   public int getBudget(){
      return this.budget;
   }
   
   public Set<String> getActors(){
      return actorToArea.keySet();
   }
   
   
   
 }
   
   
   
   