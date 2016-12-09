package seng301.assn4;

import java.util.ArrayList;
import java.util.List;

import org.lsmr.vending.frontend4.Coin;

public class CashPayment implements Payment {
	
	ArrayList<Coin> coins;			// A list of coins used as payment
	
	public CashPayment(List<Coin> inCoins){
		coins = new ArrayList<> (inCoins);
	}
	
	@Override
	public int processPayment(int totalPayment) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/*
	 * Counts the total value of all the coins and returns as an int
	 * Useful to use in processPayment
	 */
	public int coinsToInt(){
		int totValue = 0;
		for(Coin coin: coins){
			totValue += coin.getValue().getValue();
		}
		return totValue;
	}

}
