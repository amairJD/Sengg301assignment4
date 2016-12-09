package seng301.assn4;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.lsmr.vending.frontend4.Cents;
import org.lsmr.vending.frontend4.hardware.AbstractHardware;
import org.lsmr.vending.frontend4.hardware.AbstractHardwareListener;
import org.lsmr.vending.frontend4.hardware.HardwareFacade;
import org.lsmr.vending.frontend4.hardware.SelectionButton;
import org.lsmr.vending.frontend4.hardware.SelectionButtonListener;
import org.lsmr.vending.frontend4.hardware.SimulationException;

public class SelectionButtonHandler implements SelectionButtonListener{
    private Map<SelectionButton, Integer> buttonToIndex = new HashMap<>();
    private Map<Cents, Integer> valueToIndexMap = new HashMap<>();
    private HardwareFacade hf;


    public SelectionButtonHandler(HardwareFacade hf){
    	for(int i = 0; i < hf.getNumberOfSelectionButtons(); i++) {
		    SelectionButton sb = hf.getSelectionButton(i);
		    sb.register(this);
		    buttonToIndex.put(sb, i);
		}
    	for(int i = 0; i < hf.getNumberOfCoinRacks(); i++) {
		    Cents value = hf.getCoinKindForCoinRack(i);
		    valueToIndexMap.put(value, i);
		}
    }

	@Override
	public void enabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pressed(SelectionButton button) {
		
		
		Integer index = buttonToIndex.get(button);

		if(index == null)
		    throw new SimulationException("An invalid selection button was pressed");

		int cost = vendingMachine.getPopKindCost(index);

		if(cost <= availableFunds) {
		    PopCanRack pcr = vendingMachine.getPopCanRack(index);
		    if(pcr.size() > 0) {
			try {
			    pcr.dispensePopCan();
			    vendingMachine.getCoinReceptacle().storeCoins();
			    availableFunds = deliverChange(cost, availableFunds);
			}
			catch(DisabledException | EmptyException | CapacityExceededException e) {
			    throw new SimulationException(e);
			}
		    }
		}
		else {
		    Display disp = vendingMachine.getDisplay();
		    disp.display("Cost: " + cost + "; available funds: " + availableFunds);
		    final Timer timer = new Timer();
		    timer.schedule(new TimerTask() {
			@Override
			public void run() {
			    timer.cancel();
			}
		    }, 5000);
		}
		
	}

}
