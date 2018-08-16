package model;
import java.util.*;
import java.util.Scanner;
public class CastingOffice extends Room {
    private final HashMap<Integer,Integer> dollarsForRank;
    private final HashMap<Integer,Integer> creditsForRank;       
       
 public CastingOffice(){
    super("Casting Office");
    this.dollarsForRank = new HashMap<Integer,Integer>();
    this.creditsForRank = new HashMap<Integer,Integer>();
    addRankRequirements();
 }
 
  public int upgradeRankDollars (int desiredRank){
     return this.dollarsForRank.get(desiredRank);
     
  }
  
  public int upgradeRankCredits(int desiredRank){
      return this.creditsForRank.get(desiredRank);
  }
    

    public boolean canAffordUpgrade(int credits,int dollars,int currentRank){
        for(int i = currentRank + 1; i < 7; i++){
            if(credits >= this.creditsForRank.get(i)){
                return true;
            }
            if(dollars >= this.dollarsForRank.get(i)){
                return true;
            }
        }
        return false;
    }
    
    // public boolean canAffordWithDollars(int dollars,int currentRank){
//         boolean canAfford = false;
//         for(int i = currentRank + 1; i < 7; i++){
//             if(dollars >= this.credits.get(i)){
//                 canAfford = true;
//             }
//         }
//         return canAfford;
//     }
//  
 
 public ArrayList<String> getUpgradeOptions(int credits,int dollars,int currentRank){
    ArrayList<String> options = new ArrayList<String>();
    for(int i = currentRank + 1; i < 7; i++){
        if(creditsForRank.get(i) <= credits){
            options.add("buy rank " + i + " with " + creditsForRank.get(i) + " credits");
        }
        if(dollarsForRank.get(i) <= dollars){
            options.add("buy rank " + i + " with " + dollarsForRank.get(i) + " dollars");
        }      
    }
    return options;
 }
 
 
 private void addRankRequirements(){
    this.dollarsForRank.put(2,4);
    this.dollarsForRank.put(3,10);
    this.dollarsForRank.put(4,18);
    this.dollarsForRank.put(5,28);
    this.dollarsForRank.put(6,40);
    this.creditsForRank.put(2,5);
    this.creditsForRank.put(3,10);
    this.creditsForRank.put(4,15);
    this.creditsForRank.put(5,20);
    this.creditsForRank.put(6,25);
 }
 
}