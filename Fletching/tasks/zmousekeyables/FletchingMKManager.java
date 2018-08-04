package ShowtimeScripts.Fletching.tasks.zmousekeyables;

import STRepo.ST.api.STai.amplified_api.ClientContext;
import STRepo.ST.api.STai.amplified_api.ItemIdentifier;
import ShowtimeScripts.Fletching.FletchingManager;
import ShowtimeScripts.Fletching.FletchingStackableContract;

public class FletchingMKManager extends FletchingManager<FletchingStackableContract>{
	
	public FletchingMKManager(FletchingStackableContract option){
		super(option);
	}
	
	@Override
	public boolean shouldQuit(ClientContext ctx){
		return ctx.bank.opened() && (!ownsQuantity(firstItem(), option.quantity()) || !ownsQuantity(secondItem(), option.quantity()));
	}
	
	private boolean ownsQuantity(ItemIdentifier i, int quantity){
		return i.hasItemInventory(quantity) || i.hasItemBank(15);
	}
}
