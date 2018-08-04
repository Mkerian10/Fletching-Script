package ShowtimeScripts.Fletching.tasks.options;

import STRepo.ST.api.STai.amplified_api.ClientContext;
import STRepo.ST.api.STai.amplified_api.ItemIdentifier;
import ShowtimeScripts.Fletching.FletchingStackableContract;
import ShowtimeScripts.Fletching.tasks.usable.FletchingWaitManager;
import ShowtimeScripts.Fletching.tasks.usable.UseVertex;

public enum JewelBoltOptions implements FletchingStackableContract<UseVertex, FletchingWaitManager>{
	
	OPAL(877, 45, 879, 11, "Opal Bolts"),
	PEARL(9140, 46, 880, 41, "Pearl Bolts"),
	TOPAZ(9141, 9188, 9336, 48, "Topaz Bolts"),
	SAPPHIRE(9142, 9189, 9337, 56, "Sapphire Bolts"),
	EMERALD(9142, 9190, 9338, 58, "Emerald Bolts"),
	RUBY(9143, 9191, 9339, 63, "Ruby Bolts"),
	DIAMOND(9143, 9192, 9340, 65, "Diamond Bolts"),
	DRAGONSTONE(9144, 9193, 9341, 71, "Dragonstone Bolts"),
	ONYX(9144, 9194, 9342, 73, "Onyx Bolts");
	
	JewelBoltOptions(int firstId, int secondId, int resultId, int skillLevel, String prettyName){
		this.firstId = firstId;
		this.secondId = secondId;
		this.resultId = resultId;
		this.skillLevel = skillLevel;
		this.prettyName = prettyName;
	}
	
	private final int firstId;
	private final int secondId;
	private final int resultId;
	private final int skillLevel;
	private final String prettyName;
	
	
	@Override
	public int levelReq(){
		return skillLevel;
	}
	
	@Override
	public ItemIdentifier firstItem(){
		return new ItemIdentifier(firstId);
	}
	
	@Override
	public ItemIdentifier secondItem(){
		return new ItemIdentifier(secondId);
	}
	
	@Override
	public UseVertex methodVertex(ClientContext ctx){
		return new UseVertex(ctx, new FletchingWaitManager(this));
	}
	
	@Override
	public int quantity(){
		return 10;
	}
	
	@Override
	public String prettyName(){
		return prettyName;
	}
	
	@Override
	public String toString(){
		return prettyName;
	}
}
