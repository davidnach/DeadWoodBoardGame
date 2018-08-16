package model;
   public class Area {
      int x;
      int y;
      int w;
      int h;     
   public Area(int x, int y, int w, int h){
      this.x = x;
      this.y = y;
      this.w = w;
      this.h = h;
   }
   
   
      public int getX(){
         return this.x;
      }
      public int getY(){
         return this.y;
      }
      public int getW(){
         return this.w;
      }
      public int getH(){
         return this.h;
      }
 }