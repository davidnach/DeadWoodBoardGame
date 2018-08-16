package model;
import java.lang.Math;
public class Dice {
	private final int numFaces;
        
        //constructors
        public Dice(){
		this(6);
	}
	public Dice (int numFaces) {
		this.numFaces = numFaces;
	}
	
	public int rollDice(){
		return (int)(Math.random() * 6) + 1;
	}
}
