package ShowtimeScripts.Fletching.tasks.options;

import STRepo.ST.api.STai.amplified_api.ClientContext;
import STRepo.ST.api.STai.amplified_api.ItemIdentifier;
import ShowtimeScripts.Fletching.FletchingStackableContract;
import ShowtimeScripts.Fletching.tasks.zmousekeyables.FletchingMKManager;
import ShowtimeScripts.Fletching.tasks.zmousekeyables.MouseKeyVertex;
import ShowtimeScripts.Fletching.utils.FletchingConstants;

public enum DartOption implements FletchingStackableContract<MouseKeyVertex, FletchingMKManager>{
	
	BRONZE(819, 10, "Bronze Darts"),
	IRON(820, 22, "Iron Darts"),
	STEEL(821, 37, "Steel Darts"),
	MITHRIL(822, 52, "Mithril Darts"),
	ADAMANT(823, 67, "Adamant Darts"),
	RUNE(824, 81, "Rune Darts"),
	DRAGON(11232, 95, "Dragon Darts");
	
	DartOption(int tipId, int levelReq, String prettyName){
		this.tipId = tipId;
		this.levelReq = levelReq;
		this.prettyName = prettyName;
	}
	
	private final int tipId;
	private final int levelReq;
	private final String prettyName;
	
	public int levelReq(){
		return levelReq;
	}
	
	
	@Override
	public int quantity(){
		return 15;
	}
	
	@Override
	public ItemIdentifier firstItem(){
		return new ItemIdentifier(tipId);
	}
	
	@Override
	public ItemIdentifier secondItem(){
		return new ItemIdentifier(FletchingConstants.FEATHER_ID);
	}
	
	@Override
	public MouseKeyVertex methodVertex(ClientContext ctx){
		return new MouseKeyVertex(ctx, new FletchingMKManager(this));
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
