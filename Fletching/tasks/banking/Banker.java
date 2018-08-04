package ShowtimeScripts.Fletching.tasks.banking;

import STRepo.ST.STScript.cyclicscript.TaskVertex;
import STRepo.ST.api.STLogger.Logger;
import STRepo.ST.api.STai.amplified_api.ClientContext;
import STRepo.ST.api.STai.amplified_api.Condition;
import STRepo.ST.api.STai.amplified_api.ItemMap;
import STRepo.ST.api.antipattern.Regulator;
import ShowtimeScripts.Fletching.FletchingManager;
import ShowtimeScripts.Fletching.FletchingVertex;
import org.powerbot.script.rt4.TileMatrix;

import java.util.concurrent.Callable;

public class Banker<K extends FletchingManager, T extends FletchingVertex<K>> extends TaskVertex<T>{
	
	
	public Banker(final ClientContext ctx, final K manager, final T nextVertex, final Callable<Boolean> withdrawMethod){
		super(ctx);
		this.manager = manager;
		this.nextVertex = nextVertex;
		this.withdrawMethod = withdrawMethod;
	}
	
	protected final T nextVertex;
	
	protected final K manager;
	
	protected final Callable<Boolean> withdrawMethod;
	
	@Override
	public T next(){
		return nextVertex;
	}
	
	@Override
	public String getState(){
		return "Banking";
	}
	
	@Override
	public boolean shouldTerminate(){
		return manager.hasItems() && ctx.bank.close();
	}
	
	@Override
	public void execute(){
		if(Regulator.adjustedBoolean("boolbanker", 0.84, true)){
			Condition.sleep(Regulator.getGaussian("sleepbank", 350, 12000, 4500, 3200, new double[]{0.45, 1.3}), Logger.logger);
		}
		if(openBank()){
			if(deposit()){
				if(withdraw()){
					Logger.push("Banking successful, attempting to end");
				}
			}
		}else{
			Logger.push("Failed to open bank");
		}
	}
	
	protected boolean withdraw(){
		if(withdrawMethod != null){
			try{
				return withdrawMethod.call();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		ItemMap items = manager.items();
		Logger.push("Withdrawing: " + items.toString());
		items.forEach((itemIdentifier, integer) ->
		{
			if(ctx.controller.isStopping() || ctx.controller.isSuspended()) return;
			ctx.bank.withdraw(itemIdentifier.id, integer);
		});
		return items.hasItems(ctx);
	}
	
	protected boolean deposit(){
		Logger.push("Depositing all except: " + manager.items().toString());
		return ctx.bank.depositAllExcept(manager.items());
	}
	
	protected boolean openBank(){
		return ctx.bank.find() && ctx.bank.open();
	}
	
	@Override
	public void anticipate(){
		TileMatrix matrix = ctx.bank.nearest().tile().matrix(ctx);
		if(!ctx.misc.hovering(matrix))
			matrix.hover();
	}
}
