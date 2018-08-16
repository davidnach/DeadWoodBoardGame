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

public class ExtraRoleInfo {
      HashMap<Area,String> AreaToRoleName;
      HashMap<String,String> RoleNameToRoomName;
      HashMap<String,Integer> RoleNameToLevel;
      public ExtraRoleInfo(String file){
         AreaToRoleName = new HashMap<Area,String>();
         RoleNameToRoomName = new HashMap<String,String>();
         RoleNameToLevel = new HashMap<String,Integer>();
         getInfo(file);
      }
      
      public void getInfo(String file){
         try{
            File inputFile = new File(file);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList sets = doc.getElementsByTagName("set");
            String roomName;
            String roleName;
            int level;
            for(int i = 0; i < sets.getLength(); i++){
               Element set = (Element)sets.item(i);
               roomName = set.getAttribute("name");
               int x, y, h, w;
               NodeList parts = set.getElementsByTagName("part");
               for(int j = 0; j < parts.getLength(); j++) {
                     Element part = (Element)parts.item(j);
                     roleName = part.getAttribute("name");
                     level = Integer.parseInt(part.getAttribute("level"));
                     RoleNameToLevel.put(roleName,level);
                     RoleNameToRoomName.put(roleName,roomName);
                     NodeList areas = part.getElementsByTagName("area");
                     Element area = (Element)areas.item(0);
                     x = Integer.parseInt(area.getAttribute("x"));
                     y = Integer.parseInt(area.getAttribute("y"));
                     h = Integer.parseInt(area.getAttribute("h"));
                     w = Integer.parseInt(area.getAttribute("w"));
                     AreaToRoleName.put(new Area(x,y,h,w),roleName);
             }
            }
        }catch (Exception e){
        
        }
    }
    
    public HashMap<Area,String> getAreaToRoleName(){
     HashMap<Area,String> copy = new HashMap<Area,String>(this.AreaToRoleName);
     return copy;
    }
    
    public HashMap<String,String> getRoleNameToRoomName(){
      HashMap<String,String> copy = new HashMap<String,String>(this.RoleNameToRoomName);
      return copy;   
    }
    
    public HashMap<String,Integer> getRoleNameToLevel(){
      HashMap<String,Integer> copy = new HashMap<String,Integer>(this.RoleNameToLevel);
      return copy;
    }
}

  
 