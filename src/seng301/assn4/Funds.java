package seng301.assn4;


public class Funds {

	private int availableFunds;
	
	public Funds(){
		availableFunds = 0;
	}
	
	public int checkFunds(){
		return availableFunds;
	}
	
	public void addFunds(int amount){
		availableFunds += amount;
	}
	
	/**
	 * 
	 * @param amount
	 * @return change
	 * @throws NotEnoughMoneyException
	 */
	public int processPayment(int amount) throws NotEnoughMoneyException{
		if (availableFunds >= amount){
			availableFunds -= amount;
			return amount;
		}
		else {
			throw new NotEnoughMoneyException();
		}
	}
	
}
