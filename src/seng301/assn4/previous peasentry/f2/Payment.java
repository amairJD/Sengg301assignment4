package seng301.assn4;

public interface Payment {
	
	/**
	 * To Process Payment, the total value of the payment must be first converted to int
	 * and then used in processing the payment. This ensures that, in the future, a debit/credit card, etc
	 * can be used for purchase. 
	 * 
	 * @param totalPayment is the total value of the payment in int
	 * @return the total value of change
	 */
	public abstract int processPayment(int totalPayment);
	
}
