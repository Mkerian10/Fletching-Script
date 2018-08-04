package ShowtimeScripts.Fletching.ui;

import ShowtimeScripts.Fletching.FletchingEnumContract;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;

public class FletchingListModel extends AbstractListModel<FletchingEnumContract>{
	
	private ArrayList<FletchingEnumContract> modelList = new ArrayList<>();
	
	@Override
	public int getSize(){
		return modelList.size();
	}
	
	public ArrayList<FletchingEnumContract> dumpList(){
		return modelList;
	}
	
	public void sort(Comparator<FletchingEnumContract> comparator){
		modelList.sort(comparator);
		fireContentsChanged(this, 0, 0);
	}
	
	public void clear(){
		modelList.clear();
		fireContentsChanged(this, 0, 0);
	}
	
	public void add(FletchingEnumContract addition){
		if(modelList.contains(addition)) return;
		modelList.add(addition);
		fireIntervalAdded(this, indexOf(addition),  indexOf(addition));
	}
	
	public void remove(FletchingEnumContract removal){
		modelList.remove(removal);
		fireIntervalRemoved(this, getSize() -  1, getSize() - 1);
	}
	
	@Override
	public FletchingEnumContract getElementAt(int index){
		return modelList.get(index);
	}
	
	public int indexOf(FletchingEnumContract f){
		return modelList.indexOf(f);
	}
}
