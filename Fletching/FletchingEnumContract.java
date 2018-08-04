package ShowtimeScripts.Fletching;

import STRepo.ST.api.STai.amplified_api.ClientContext;
import STRepo.ST.api.STai.amplified_api.ItemIdentifier;
import STRepo.ST.api.STai.amplified_api.ItemMap;

public interface FletchingEnumContract<K extends FletchingVertex<T>, T extends FletchingManager>{
	
	int levelReq();
	
	ItemIdentifier firstItem();
	
	ItemIdentifier secondItem();
	
	ItemMap items();
	
	K methodVertex(ClientContext ctx);
	
	String prettyName();
	
}
