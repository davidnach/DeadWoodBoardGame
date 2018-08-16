package model;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.*;
import java.util.HashMap;
public class SceneCardInfo{
      
      private HashMap<Area,String> AreaToSceneName;
      private HashMap<String,String> ActorToSceneName;
      private HashMap<String,Integer> ActorToLevel;
      private HashMap<String,Integer> SceneNameToBudget;
      private HashMap<String,String> SceneNameToImage;
      private HashMap<String,String> ImageToSceneName;
      private HashMap<String,Area> ActorToArea;
      
      public SceneCardInfo(String file){
         this.AreaToSceneName = new HashMap<Area,String>();
         this.ActorToArea = new HashMap<String,Area>();
         this.ActorToSceneName = new HashMap<String,String>();
         this.SceneNameToBudget = new HashMap<String,Integer>();
         this.SceneNameToImage = new HashMap<String,String>();
         this.ImageToSceneName = new HashMap<String,String>();
         this.ActorToLevel = new HashMap<String,Integer>();
         getSceneCardInfo(file);
      }
      public void getSceneCardInfo(String file){
         try {
            File inputFile = new File(file);
            DocumentBuilderFactory dbFactory 
               = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList cards = doc.getElementsByTagName("card");
            NodeList parts = doc.getElementsByTagName("part");
            
            for(int i = 0; i < cards.getLength(); i++){
                 Element card = (Element)cards.item(i);
                 int budget = Integer.parseInt(card.getAttribute("budget"));
                 String sceneName = card.getAttribute("name");
                 String img = card.getAttribute("img");
                 SceneNameToBudget.put(sceneName,budget);
                 SceneNameToImage.put(sceneName,img);
                 ImageToSceneName.put(img,sceneName);
            }
            
            for(int i = 0; i < parts.getLength(); i++){
               Element part = (Element)parts.item(i);
               String name = part.getAttribute("name");
               String sceneName = part.getAttribute("cname");
               int level = Integer.parseInt(part.getAttribute("level"));
               ActorToLevel.put(name,level);
               ActorToSceneName.put(name,sceneName);
               NodeList areas = part.getElementsByTagName("area");
               Element area = (Element)areas.item(0);
               int x = Integer.parseInt(area.getAttribute("x"));
               int y = Integer.parseInt(area.getAttribute("y"));
               int h = Integer.parseInt(area.getAttribute("h"));
               int w = Integer.parseInt(area.getAttribute("w"));
               Area iconArea = new Area(x,y,h,w);
               AreaToSceneName.put(iconArea,sceneName);
               ActorToArea.put(name,iconArea);
            }
            
            
         }   catch(Exception e) {
            e.printStackTrace();
         }
    }
    
    public HashMap<Area,String> getAreaToSceneName(){
     HashMap<Area,String> copy = new HashMap<Area,String>(this.AreaToSceneName);
     return copy;
    }
    
    public HashMap<String,String> getActorToSceneName(){
      HashMap<String,String> copy = new HashMap<String,String>(this.ActorToSceneName);
      return copy;   
    }
    
    public HashMap<String,Integer> getSceneNameToBudget(){
      HashMap<String,Integer> copy = new HashMap<String,Integer>(this.SceneNameToBudget);
      return copy;
    }
    
    public HashMap<String,Integer> getActorToLevel(){
        HashMap<String,Integer> copy = new HashMap<String,Integer>(this.ActorToLevel);
        return copy;
    }
    
    public HashMap<String,String> getSceneNameToImage(){
        HashMap<String,String> copy = new HashMap<String,String>(this.SceneNameToImage);
        return copy;
    }

    public HashMap<String,String> getImageToSceneName(){
        HashMap<String,String> copy = new HashMap<String,String>(this.ImageToSceneName);
        return copy;
    }
    
    public HashMap<String,Area> getActorToArea(){
        HashMap<String,Area> copy = new HashMap<String,Area>(this.ActorToArea);
        return copy;
    }
    
 }
 