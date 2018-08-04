package ShowtimeScripts.Fletching.tasks.options;


import STRepo.ST.api.STai.amplified_api.ItemIdentifier;
import STRepo.ST.api.STai.amplified_api.ItemMap;
import ShowtimeScripts.Fletching.FletchingEnumContract;
import ShowtimeScripts.Fletching.tasks.usable.FletchingWaitManager;
import ShowtimeScripts.Fletching.tasks.usable.UseVertex;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Component;

import static ShowtimeScripts.Fletching.utils.FletchingConstants.*;

public enum CuttingOption implements FletchingEnumContract<UseVertex, FletchingWaitManager>{
	
	ARROW_SHAFTS_REG(LOGS_ID, 52, 1, 0, "Arrows Shafts (reg)"),
	JAVELIN_SHAFTS_REG(LOGS_ID, 19584, 3, 1, "Javelin Shafts (reg)"),
	SHORTBOW_REG(LOGS_ID, 50, 5, 2, "Shortbow (reg)"),
	LONGBOW_REG(LOGS_ID, 48, 10, 3, "Longbow (reg)"),
	CROSSBOW_REG(LOGS_ID, 9440, 9, 4, "Crossbow (reg)"),
	
	ARROW_SHAFTS_OAK(OAK_ID, 52, 15, 0, "Arrow Shafts (Oak)"),
	SHORTBOW_OAK(OAK_ID, 54, 20, 1, "Oak Shortbow"),
	LONGBOW_OAK(OAK_ID, 56, 25, 2, "Oak Longbow"),
	CROSSBOW_OAK(OAK_ID, 9442, 24, 3, "Oak Crossbow"),
	SHIELD_OAK(OAK_ID, 22251, 27, 4, "Oak Shield"),
	
	ARROW_SHAFTS_WILLOW(WILLOW_ID, 52, 30, 0, "Arrow Shafts (Willow)"),
	SHORTBOW_WILLOW(WILLOW_ID, 60, 35, 1, "Willow Shortbow"),
	LONGBOW_WILLOW(WILLOW_ID, 58, 40, 2, "Willow Longbow"),
	CROSSBOW_WILLOW(WILLOW_ID, 9444, 39, 3, "Willow Crossbow"),
	SHIELD_WILLOW(WILLOW_ID, 22254, 42, 4, "Willow Shield"),
	
	ARROW_SHAFTS_MAPLE(MAPLE_ID, 52, 45, 0, "Arrow Shafts (Maple)"),
	SHORTBOW_MAPLE(MAPLE_ID, 64, 50, 1, "Maple Shortbow"),
	LONGBOW_MAPLE(MAPLE_ID, 62, 55, 2, "Maple Longbow"),
	CROSSBOW_MAPLE(MAPLE_ID, 9448, 54, 3, "Maple Crossbow"),
	SHIELD_MAPLE(MAPLE_ID, 22257, 57, 4, "Maple Shield"),
	
	ARROW_SHAFTS_YEW(YEW_ID, 52, 60, 0, "Arrow Shafts (Yew)"),
	SHORTBOW_YEW(YEW_ID, 68, 65, 1, "Yew Shortbow"),
	LONGBOW_YEW(YEW_ID, 66, 70, 2, "Yew Longbow"),
	CROSSBOW_YEW(YEW_ID, 9452, 69, 3, "Yew Crossbow"),
	SHIELD_YEW(YEW_ID, 22260, 72, 4, "Yew Shield"),
	
	ARROW_SHAFTS_MAGIC(MAGIC_ID, 52, 75, 0, "Arrow Shafts (Magic)"),
	SHORTBOW_MAGIC(MAGIC_ID, 72, 80, 1, "Magic Shortbow"),
	LONGBOW_MAGIC(MAGIC_ID, 70, 85, 2, "Magic Longbow"),
	CROSSBOW_MAGIC(MAGIC_ID, 21952, 78, 3, "Magic Crossbow"),
	SHIELD_MAGIC(MAGIC_ID, 22263, 87, 4, "Magic Shield")
	
	;
	
	CuttingOption(int logs, int result, int levelRequired, int componentOffset, String prettyName){
		this.logs = logs;
		this.result = result;
		this.levelRequired = levelRequired;
		this.componentOffset = componentOffset;
		this.prettyName = prettyName;
	}
	
	private final int logs;
	public final int result;
	public final int levelRequired;
	private final int componentOffset;
	private final String prettyName;
	
	public Component component(ClientContext ctx){
		return ctx.widgets.widget(270).component(14 + componentOffset);
	}
	
	public String virtualKey(){
		return "" + (componentOffset + 1);
	}
	
	@Override
	public int levelReq(){
		return levelRequired;
	}
	
	@Override
	public ItemIdentifier firstItem(){
		return new ItemIdentifier(logs);
	}
	
	@Override
	public ItemIdentifier secondItem(){
		return KNIFE_ITEM;
	}
	
	@Override
	public ItemMap items(){
		ItemMap ret = new ItemMap();
		ret.put(firstItem(), 27);
		ret.put(secondItem(), 1);
		return ret;
	}
	
	@Override
	public UseVertex methodVertex(STRepo.ST.api.STai.amplified_api.ClientContext ctx){
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
