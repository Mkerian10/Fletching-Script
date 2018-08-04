package ShowtimeScripts.Fletching.tasks.usable;

import STRepo.ST.api.STai.amplified_api.ClientContext;
import STRepo.ST.api.STai.amplified_api.Condition;
import ShowtimeScripts.Fletching.FletchingVertex;
import ShowtimeScripts.Fletching.tasks.banking.Banker;
import ShowtimeScripts.Fletching.utils.FletchingExpWatcher;
import org.powerbot.script.rt4.Game;

import static STRepo.ST.api.STLogger.Logger.push;

public class UseVertex extends FletchingVertex<FletchingWaitManager>{
	
	public UseVertex(ClientContext ctx, FletchingWaitManager manager){
		super(ctx, manager);
	}
	
	@Override
	public String getState(){
		return "Fletching";
	}
	
	@Override
	public Banker next(){
		return new Banker<>(ctx, manager, this, null);
	}
	
	@Override
	public boolean shouldTerminate(){
		return !manager.firstItem().hasItemInventory() || !manager.secondItem().hasItemInventory();
	}
	
	@Override
	public void execute(){
		ctx.game.tab(Game.Tab.INVENTORY);
		
		if(FletchingExpWatcher.getTime() > 2450){
			
			if(manager.component(ctx).visible()){
				if(ctx.input.send(manager.virtualKey())){
					FletchingExpWatcher.refresh();
					push("Pushed virtual key");
				}else{
					push("Failed to send V_K");
				}
			}else{
				if(ctx.inventory.isSelecting()){
					push("Error with use, deselecting");
					ctx.inventory.deselect();
				}else{
					if(ctx.inventory.use(manager.firstItem(), manager.secondItem())){
						Condition.wait(() -> manager.component(ctx).visible(), 50, 15);
					}
				}
			}
		}
	}
	
	@Override
	public boolean shouldAnticipate(){
		return FletchingExpWatcher.getTime() < 1450;
	}
}
