package seng301.assn4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.lsmr.vending.frontend4.Cents;
import org.lsmr.vending.frontend4.ProductKind;
import org.lsmr.vending.frontend4.hardware.CapacityExceededException;
import org.lsmr.vending.frontend4.hardware.CoinRack;
import org.lsmr.vending.frontend4.hardware.DisabledException;
import org.lsmr.vending.frontend4.hardware.EmptyException;
import org.lsmr.vending.frontend4.hardware.HardwareFacade;

/**
 * Represents vending machines, fully configured and with all software
 * installed.
 * 
 * @author Robert J. Walker
 */
public class VendingMachine {
    private HardwareFacade hf;
    private PaymentModule paymentModule;
    
    private Cents[] coinKinds;

    /* YOU CAN ADD OTHER COMPONENTS HERE */

    /**
     * Accessor for the hardware facade for this vending machine.
     * 
     * @return The hardware.
     */
    public HardwareFacade getHardware() {
    	return hf;
    }

    /**
     * Creates a standard arrangement for the vending machine. All the
     * components are created and interconnected. The hardware is initially
     * empty. The product kind names and costs are initialized to &quot; &quot;
     * and 1 respectively.
     * 
     * @param coinKinds
     *            The values (in cents) of each kind of coin. The order of the
     *            kinds is maintained. One coin rack is produced for each kind.
     *            Each kind must have a unique, positive value.
     * @param selectionButtonCount
     *            The number of selection buttons on the machine. Must be
     *            positive.
     * @param coinRackCapacity
     *            The maximum capacity of each coin rack in the machine. Must be
     *            positive.
     * @param productRackCapacity
     *            The maximum capacity of each product rack in the machine. Must
     *            be positive.
     * @param receptacleCapacity
     *            The maximum capacity of the coin receptacle, storage bin, and
     *            delivery chute. Must be positive.
     * @throws IllegalArgumentException
     *             If any of the arguments is null, or the size of productCosts
     *             and productNames differ.
     */
    public VendingMachine(Cents[] coinKinds, int selectionButtonCount, int coinRackCapacity, int productRackCapacity, int receptacleCapacity) {
		hf = new HardwareFacade(coinKinds, selectionButtonCount, coinRackCapacity, productRackCapacity, receptacleCapacity);
		
		/* YOU CAN BUILD AND INSTALL THE HARDWARE HERE */
		
		paymentModule = new CashPaymentModule(hf, coinKinds);
		this.coinKinds = Arrays.copyOf(coinKinds, coinKinds.length);
				
    }
   

	public void configureMachine(ProductKind... kinds){
    	hf.configure(kinds);
    }
	
	
	
	public void makePurchase(PaymentModule pM){
		int totalCost = 345; // TODO Change to the totalCost reflecting a wanted item.
		
		int change = pM.makePurchase(totalCost);
		
	}
	
	public void returnChange(PaymentModule pM){
		
	}
	
	
	private void dispenseCoins(int change){
		if (change < 0)
			throw new InternalError("Change was less than zero! That is not allowed.");
		
		/**
		 * Based on available coinKinds,  release Coins until ChangeDue is 0 
		 */
		
		List<Integer> availableCoinKinds = null;// = new ArrayList<Integer> (coinKinds); 
		
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
	}
    
}
