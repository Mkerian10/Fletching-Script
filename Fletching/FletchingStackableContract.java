package ShowtimeScripts.Fletching;

import STRepo.ST.api.STai.amplified_api.ItemMap;

public interface FletchingStackableContract<K extends FletchingVertex<V>, V extends FletchingManager> extends FletchingEnumContract<K, V>{
	
	int quantity();
	
	@Override
	default ItemMap items(){
		ItemMap ret = new ItemMap();
		ret.put(firstItem(), quantity());
		ret.put(secondItem(), quantity());
		return ret;
	}
}
