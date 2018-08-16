package model;
public class Wallet {
	private int dollars;
	private int credit;
	
       //constructors        
        public Wallet(){
        	this(0,0);
        }
        public Wallet(int credits){
    		this(0,credits);
        }
        public Wallet(int dollars, int credits){
		this.dollars = dollars;
		this.credit = credits;
	}

        //methods

	public void addMoney(int dollars){
		this.dollars = this.dollars + dollars;
	}
	public void addCredit(int credits){
		this.credit = this.credit + credits;
	}
        
    public int getMoney(){
		return this.dollars;
	}
	
 	public int getCredit(){
		return this.credit;
	}
	
	public void chargeMoney(int dollars){
		this.dollars -= dollars;
	}
	
	public void chargeCredit(int credits){
		this.credit -= credits;
	}
        
}