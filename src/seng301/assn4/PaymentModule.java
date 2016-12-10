package seng301.assn4;

public interface PaymentModule {
	
	/**
	 * To Process Payment, the total value of the payment must be first converted to int
	 * and then used in processing the payment. 
	 * This ensures that, in the future, a debit/credit card, etc
	 * can be used for purchase. 
	 * 
	 * @param totalPayment is the total value of the payment in int
	 * @return total value of change required in INT
	 */
	public abstract int makePurchase(int totalPayment);
	
	/**
	 * Inserts money into available funds, that can be used for a purchase
	 * @param money
	 */
	public abstract void addFunds(int money);
	
	/**
	 * Checks available funds and returns it as an int value
	 */
	public abstract int checkFunds();
	

	
}
