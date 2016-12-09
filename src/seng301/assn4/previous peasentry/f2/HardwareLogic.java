package seng301.assn4;

import org.lsmr.vending.frontend4.hardware.AbstractHardwareListener;
import org.lsmr.vending.frontend4.hardware.HardwareFacade;

public interface HardwareLogic extends AbstractHardwareListener{

	/**
	 * Should register the selected hardwareFace with 
	 * @param vm
	 */
	public abstract void registerWith(HardwareFacade hf);
}
