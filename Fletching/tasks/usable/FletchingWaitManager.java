package ShowtimeScripts.Fletching.tasks.usable;

import STRepo.ST.api.STai.amplified_api.ClientContext;
import ShowtimeScripts.Fletching.FletchingEnumContract;
import ShowtimeScripts.Fletching.FletchingManager;
import ShowtimeScripts.Fletching.tasks.options.CuttingOption;
import org.powerbot.script.rt4.Component;

public class FletchingWaitManager extends FletchingManager<FletchingEnumContract>{
	
	public FletchingWaitManager(FletchingEnumContract option){
		super(option);
	}
	
	public Component component(ClientContext ctx){
		if(option instanceof CuttingOption){
			return ((CuttingOption) option).component(ctx);
		}
		
		return ctx.widgets.component(270, 14);
	}
	
	public String virtualKey(){
		if(option instanceof CuttingOption){
			return ((CuttingOption) option).virtualKey();
		}
		
		return " ";
	}
	
	@Override
	public boolean shouldQuit(ClientContext ctx){
		return ctx.bank.opened() && (!option.firstItem().ownsItem() || !option.secondItem().ownsItem());
	}
}
