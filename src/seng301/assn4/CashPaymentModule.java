package seng301.assn4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.lsmr.vending.frontend4.hardware.CoinRack;
import org.lsmr.vending.frontend4.Cents;
import org.lsmr.vending.frontend4.Coin;
import org.lsmr.vending.frontend4.hardware.AbstractHardware;
import org.lsmr.vending.frontend4.hardware.AbstractHardwareListener;
import org.lsmr.vending.frontend4.hardware.CapacityExceededException;
import org.lsmr.vending.frontend4.hardware.CoinSlot;
import org.lsmr.vending.frontend4.hardware.CoinSlotListener;
import org.lsmr.vending.frontend4.hardware.DisabledException;
import org.lsmr.vending.frontend4.hardware.EmptyException;
import org.lsmr.vending.frontend4.hardware.HardwareFacade;

public class CashPaymentModule implements PaymentModule, CoinSlotListener {
	
	Funds funds;
	HardwareFacade hf;
	List<Integer> coinKinds;
	
	public CashPaymentModule(HardwareFacade hardwareFacade, Cents[] ckList){
		hf = hardwareFacade;
		hf.getCoinSlot().register(this);
		
		coinKinds = new ArrayList<Integer>();
		for (Cents cent: ckList){
			coinKinds.add(cent.getValue());
		}
		funds = new Funds();
	}
	
	@Override
	public int checkFunds() {
		return funds.checkFunds();
	}
	
	@Override
	public void addFunds(int money) {
		funds.addFunds(money);
	}
	
	@Override
	public void makePurchase(int totalCost) {
		try{
			int change = funds.processPayment(totalCost);
			dispenseChange(change);
		} catch (NotEnoughMoneyException e){
			// TODO NOTIFY USER THAT HE DOES NOT HAVE ENOUGH MONEY!!!!
		}
	}

	/**
	 * Coins entered will be returned. The function knows which coins to return because it checks 
	 * the coins in the coinHistory ArrayList
	 * @param change
	 */
	private void dispenseChange(int change) {
		if (change < 0)
			throw new InternalError("Change was less than zero! That is not allowed.");
		
		/**
		 * Based on available coinKinds,  release Coins until ChangeDue is 0 
		 */
		
		List<Integer> availableCoinKinds = new ArrayList<Integer> (coinKinds); 
		
		while (!availableCoinKinds.isEmpty()){
			Integer maxSize = Collections.max(availableCoinKinds);
			
			// If the size of the largest Coin Kind is more than the change desired
			if (maxSize > change){
				availableCoinKinds.remove(maxSize);
				continue;
			}
			
			CoinRack cr = hf.getCoinRackForCoinKind(maxSize); 
			
			// If there is no coin Rack of that value
			if (cr == null){
				availableCoinKinds.remove(maxSize);
				continue;
			}
			
			try {
				cr.releaseCoin();
			} catch (CapacityExceededException | DisabledException | EmptyException e) {
				// Coin Rack is disabled, empty, or it's capacity has been exceeded.
				// therefore, move on to the next rack
				availableCoinKinds.remove(maxSize);
				continue;
			}
			
			// Update change to be dispensed
			change -= maxSize;
			
		}
		
		if (change > 0){
			// TODO If there is change still required to be dispensed,
			// notify user that no more coins are available. 
		}
		
	}

	@Override
	public void enabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {		
	}

	@Override
	public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {		
	}

	@Override
	public void validCoinInserted(CoinSlot slot, Coin coin) {
		funds.addFunds(coin.getValue().getValue());
	}

	@Override
	public void coinRejected(CoinSlot slot, Coin coin) {		
	}

}
