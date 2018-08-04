package ShowtimeScripts.Fletching;

import STRepo.ST.api.STai.amplified_api.ClientContext;
import STRepo.ST.api.STai.amplified_api.ItemIdentifier;
import STRepo.ST.api.STai.amplified_api.ItemMap;

import java.util.Arrays;

public abstract class FletchingManager<K extends FletchingEnumContract>{
	
	public FletchingManager(K option){
		this.option = option;
	}
	
	protected final K option;
	
	public ItemMap items(){
		return option.items();
	}
	
	public boolean hasItems(){
		ItemMap items = items();
		for(ItemIdentifier i: items.keySet()){
			if(!i.hasItemInventory(items.get(i))){
				return false;
			}
		}
		return true;
	}
	
	public ItemIdentifier firstItem(){
		return option.firstItem();
	}
	
	public ItemIdentifier secondItem(){
		return option.secondItem();
	}
	
	public abstract boolean shouldQuit(ClientContext ctx);
	
	public boolean hovering(ClientContext ctx){
		return Arrays.stream(ctx.menu.commands()).anyMatch(menuCommand -> menuCommand != null && menuCommand.action.contains("Talk-to"));
	}
	
	@Override
	public String toString(){
		return option.prettyName();
	}
}
