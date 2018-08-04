package ShowtimeScripts.Fletching.tasks.options;

import STRepo.ST.api.STai.amplified_api.ClientContext;
import STRepo.ST.api.STai.amplified_api.ItemIdentifier;
import STRepo.ST.api.STai.amplified_api.ItemMap;
import ShowtimeScripts.Fletching.FletchingEnumContract;
import ShowtimeScripts.Fletching.tasks.usable.FletchingWaitManager;
import ShowtimeScripts.Fletching.tasks.usable.UseVertex;
import ShowtimeScripts.Fletching.utils.FletchingConstants;

public enum StringingOption implements FletchingEnumContract<UseVertex, FletchingWaitManager>{

	SHORTBOW_REG(50, 841, 5, "Regular Shortbow (u)"),
	LONGBOW_REG(48, 839, 10, "Regular Longbow (u)"),
	
	SHORTBOW_OAK(54, 843, 20, "Oak Shortbow (u)"),
	LONGBOW_OAK(56, 845, 25, "Oak Longbow (u)"),
	
	SHORTBOW_WILLOW(60, 849, 35, "Willow Shortbow (u)"),
	LONGBOW_WILLOW(58, 847, 40, "Willow Longbow (u)"),
	
	SHORTBOW_MAPLE(64, 853, 50, "Maple Shortbow (u)"),
	LONGBOW_MAPLE(62, 851, 55, "Maple Longbow (u)"),
	
	SHORTBOW_YEW(68, 857, 65, "Yew Shortbow (u)"),
	LONGBOW_YEW(66, 855, 70, "Yew Longbow (u)"),
	
	SHORTBOW_MAGIC(72, 861, 80, "Magic Shortbow (u)"),
	LONGBOW_MAGIC(70, 859, 85, "Magic Longbow (u)")
	
	;
	
	StringingOption(int bowId, int resultantId, int levelReq, String prettyName){
		this.bowId = bowId;
		this.resultantId = resultantId;
		this.levelReq = levelReq;
		this.prettyName = prettyName;
	}
	
	private final int bowId;
	private final int resultantId;
	private final int levelReq;
	private final String prettyName;
	
	@Override
	public int levelReq(){
		return levelReq;
	}
	
	@Override
	public ItemIdentifier firstItem(){
		return new ItemIdentifier(bowId);
	}
	
	@Override
	public ItemIdentifier secondItem(){
		return FletchingConstants.BOWSTRING_ITEM;
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
	
	@Override
	public String toString(){
		return prettyName;
	}
}
