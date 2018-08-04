package ShowtimeScripts.Fletching.tasks.options;

import STRepo.ST.api.STai.amplified_api.ClientContext;
import STRepo.ST.api.STai.amplified_api.ItemIdentifier;
import STRepo.ST.api.STai.amplified_api.ItemMap;
import ShowtimeScripts.Fletching.FletchingEnumContract;
import ShowtimeScripts.Fletching.tasks.usable.FletchingWaitManager;
import ShowtimeScripts.Fletching.tasks.usable.UseVertex;

public enum ArrowOption implements FletchingEnumContract<UseVertex, FletchingWaitManager>{

	HEADLESS_ARROWS(52, 53, 1, "Headless Arrows"),
	BRONZE_ARROWS(39, 882, 1, "Bronze Arrows"),
	IRON_ARROWS(40, 884, 15, "Iron Arrows"),
	STEEL_ARROWS(41, 886, 30, "Steel Arrows"),
	MITHRIL_ARROWS(42, 888, 45, "Mithril Arrows"),
	BROAD_ARROWS(11874, 4150, 52, "Broad Arrows"),
	ADAMANT_ARROWS(43, 890, 60, "Adamant Arrows"),
	RUNE_ARROWS(44, 892, 75, "Rune Arrows"),
	AMYTHEST_ARROWS(-1, -1, 82, "Amethyst Arrows");
	
	
	ArrowOption(int combiningId, int resultantId, int levelReq, String prettyName){
		this.combiningId = combiningId;
		this.resultantId = resultantId;
		this.levelReq = levelReq;
		this.prettyName = prettyName;
	}
	
	private final int combiningId;
	private final int resultantId;
	private final int levelReq;
	private final String prettyName;
	
	public ItemIdentifier firstItem(){
		if(this.equals(HEADLESS_ARROWS)){
			return new ItemIdentifier(314);
		}else{
			return new ItemIdentifier(53);
		}
	}
	
	public ItemIdentifier secondItem(){
		return new ItemIdentifier(combiningId);
	}
	
	@Override
	public ItemMap items(){
		ItemMap ret = new ItemMap();
		ret.put(firstItem(), 14);
		ret.put(secondItem(), 14);
		return ret;
	}
	
	@Override
	public UseVertex methodVertex(ClientContext ctx){
		return new UseVertex(ctx, new FletchingWaitManager(this));
	}
	
	@Override
	public String prettyName(){
		return prettyName;
	}
	
	public ItemIdentifier resultant(){
		return new ItemIdentifier(resultantId);
	}
	
	public int levelReq(){
		return levelReq;
	}
	
	@Override
	public String toString(){
		return prettyName;
	}
}
