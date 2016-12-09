package seng301.assn4;

import org.lsmr.vending.frontend4.Cents;
import org.lsmr.vending.frontend4.ProductKind;
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
				
    }
   

	public void configureMachine(ProductKind... kinds){
    	hf.configure(kinds);
    }
    
}
