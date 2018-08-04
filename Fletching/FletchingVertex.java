package ShowtimeScripts.Fletching;

import STRepo.ST.STScript.cyclicscript.TaskVertex;
import STRepo.ST.api.STai.amplified_api.ClientContext;
import ShowtimeScripts.Fletching.tasks.banking.Banker;

public abstract class FletchingVertex<K extends FletchingManager> extends TaskVertex<Banker>{
	
	public FletchingVertex(ClientContext ctx, K manager){
		super(ctx);
		this.manager = manager;
	}
	
	protected final K manager;
	
	public K getManager(){
		return manager;
	}
	
	@Override
	public String toString(){
		return manager.option.toString();
	}
}
