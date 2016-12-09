package seng301.assn4;

public class Funds {
	int treasury;
	
	public Funds(){
		treasury = 0;
	}
	
	public int checkFunds(){
		return treasury;
	}
	
	public void addFunds(int amount){
		treasury += amount;
	}

	public int takeFunds(int amount){
		if (treasury >= amount){
			treasury -= amount;
			return amount;
		}
		else {
			return treasury;
			// TODO notify system that funds are empty now, and how much money is left over to take!
		}
	}
}
