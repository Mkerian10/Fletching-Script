package ShowtimeScripts.Fletching.tasks.zmousekeyables;

import STRepo.ST.api.STai.amplified_api.ClientContext;
import STRepo.ST.api.STai.amplified_api.Condition;
import STRepo.ST.api.STai.amplified_api.ItemIdentifier;
import STRepo.ST.api.antipattern.Regulator;
import ShowtimeScripts.Fletching.FletchingVertex;
import ShowtimeScripts.Fletching.tasks.banking.Banker;

import java.awt.*;

public class MouseKeyVertex extends FletchingVertex<FletchingMKManager>{
	
	private static final int HOP_WIDTH = 40;
	
	public MouseKeyVertex(ClientContext ctx, FletchingMKManager manager){
		super(ctx, manager);
	}
	
	@Override
	public String getState(){
		return "Fletching";
	}
	
	@Override
	public Banker next(){
		return new Banker<>(ctx, manager, this, () -> {
			manager.items().forEach((ii, i) -> ctx.bank.withdraw(ii.id, 0));
			return manager.items().hasItems(ctx);
		});
	}
	
	@Override
	public boolean shouldTerminate(){
		return !manager.firstItem().hasItemInventory(15) || !manager.secondItem().hasItemInventory(15);
	}
	
	@Override
	public void execute(){
		if(initSetup()){
			while(shouldContinue()){
				ctx.input.click(true);
				Point p = ctx.input.getLocation();
				p.translate(HOP_WIDTH, 0);
				ctx.input.hop(p);
				ctx.input.click(true);
				Condition.sleep(Regulator.getGaussian("mkmex", 30, 140, 38, 85, new double[]{0.8, 1.2}));
				ctx.input.click(true);
				p.translate(-HOP_WIDTH, 0);
				ctx.input.hop(p);
				ctx.input.click(true);
			}
		}
	}
	
	private boolean shouldContinue(){
		return !ctx.controller.isStopping() && !ctx.controller.isSuspended() && manager.firstItem().hasItemInventory(15)
				&& manager.secondItem().hasItemInventory(15);
	}
	
	private boolean initSetup(){
		ItemIdentifier first = manager.firstItem();
		ItemIdentifier second = manager.secondItem();
		
		if(!inSlot(first, 0)){
			ctx.inventory.drag(first.getItemInventory(), 0);
			return false;
		}
		
		if(!inSlot(second, 1)){
			ctx.inventory.drag(second.getItemInventory(), 1);
			return false;
		}
		
		return manager.firstItem().getItemInventory().hover();
	}
	
	private boolean inSlot(ItemIdentifier i, int index){
		return ctx.inventory.itemAt(index).id() == i.id;
	}
}
