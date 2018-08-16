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

public class ShotMarkerInfo {
      HashMap<Area,Integer> AreaToTakeNumber;
      HashMap<Area,String> AreaToRoomName;
      public ShotMarkerInfo(String file){
         AreaToTakeNumber = new HashMap<Area,Integer>();
         AreaToRoomName = new HashMap<Area,String>();
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
            int takeNumber;
            int x, y, h, w;
            Area area;
            for(int i = 0; i < sets.getLength(); i++){
               Element set = (Element)sets.item(i);
               roomName = set.getAttribute("name");
               NodeList takes = set.getElementsByTagName("take");
               for(int j = 0; j < takes.getLength(); j++) {
                     Element take = (Element)takes.item(j);
                     takeNumber = Integer.parseInt(take.getAttribute("number"));
                     NodeList areas = take.getElementsByTagName("area");
                     Element areaDimensions = (Element)areas.item(0);
                     x = Integer.parseInt(areaDimensions.getAttribute("x"));
                     y = Integer.parseInt(areaDimensions.getAttribute("y"));
                     h = Integer.parseInt(areaDimensions.getAttribute("h"));
                     w = Integer.parseInt(areaDimensions.getAttribute("w"));
                     area = new Area(x,y,h,w);
                     AreaToRoomName.put(area,roomName);
                     AreaToTakeNumber.put(area,takeNumber);
             }
            }
        }catch (Exception e){
        
        }
    }
    
    public HashMap<Area,String> getAreaToRoomName(){
     HashMap<Area,String> copy = new HashMap<Area,String>(this.AreaToRoomName);
     return copy;
    }
    
    public HashMap<Area,Integer> getAreaToTakeNumber(){
      HashMap<Area,Integer> copy = new HashMap<Area,Integer>(this.AreaToTakeNumber);
      return copy;   
    }
   
}

  
 