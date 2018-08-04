package ShowtimeScripts.Fletching.tasks.options;

import STRepo.ST.api.STai.amplified_api.ClientContext;
import STRepo.ST.api.STai.amplified_api.ItemIdentifier;
import ShowtimeScripts.Fletching.FletchingStackableContract;
import ShowtimeScripts.Fletching.tasks.zmousekeyables.FletchingMKManager;
import ShowtimeScripts.Fletching.tasks.zmousekeyables.MouseKeyVertex;

import static ShowtimeScripts.Fletching.utils.FletchingConstants.FEATHER_ID;

public enum BoltOption implements FletchingStackableContract<MouseKeyVertex, FletchingMKManager>{
	
	BRONZE(9375, FEATHER_ID, 877, 9, "Bronze Bolts"),
	IRON(9377, FEATHER_ID, 9140, 39, "Iron Bolts"),
	STEEL(9378, FEATHER_ID, 9141, 46, "Steel Bolts"),
	MITHRIL(9379, FEATHER_ID, 9142, 54, "Mithril Bolts"),
	BROAD(11876, FEATHER_ID, 11875, 55, "Broad Bolts"),
	ADAMANT(9380, FEATHER_ID, 9143, 61, "Adamant Bolts"),
	RUNE(9381, FEATHER_ID, 9144, 69, "Rune Bolts"),
	DRAGON(21930, FEATHER_ID, 21905, 84, "Dragon Bolts"),;
	
	BoltOption(int firstId, int secondId, int resultId, int skillLevel, String prettyName){
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
		return 0;
	}
	
	public ItemIdentifier firstItem(){
		return new ItemIdentifier(firstId);
	}
	
	public ItemIdentifier secondItem(){
		return new ItemIdentifier(secondId);
	}
	
	@Override
	public MouseKeyVertex methodVertex(ClientContext ctx){
		return new MouseKeyVertex(ctx, new FletchingMKManager(this));
	}
	
	public ItemIdentifier resultItem(){
		return new ItemIdentifier(resultId);
	}
	
	public int getReqLevel(){
		return skillLevel;
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
