package ShowtimeScripts.Fletching.utils;

import STRepo.ST.STScript.cyclicscript.Graph;
import STRepo.ST.STScript.cyclicscript.TaskVertex;
import STRepo.ST.api.STLogger.Logger;
import STRepo.ST.api.STai.amplified_api.ClientContext;
import ShowtimeScripts.Fletching.FletchingEnumContract;
import ShowtimeScripts.Fletching.FletchingManager;
import ShowtimeScripts.Fletching.FletchingVertex;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class TaskQueueManager{
	
	public TaskQueueManager(ClientContext ctx){
		this.ctx = ctx;
		this.levelProgression = false;
	}
	
	public TaskQueueManager(ClientContext ctx, boolean levelProgression){
		this.ctx = ctx;
		this.levelProgression = levelProgression;
	}
	
	private final boolean levelProgression;
	
	private final ClientContext ctx;
	
	private final LinkedList<FletchingTaskNode> graphList = new LinkedList<>();
	
	public void execute(){
		if(graphList.getFirst().shouldQuit() || graphList.getFirst().contract.levelReq() > ctx.skills.level(9)){
			Logger.push("Removing task queue item from queue: " + graphList.getFirst().contract.prettyName());
			graphList.removeFirst();
		}else{
			if(levelProgression && graphList.get(1) != null && graphList.get(1).contract.levelReq() <= ctx.skills.level(9)){
				Logger.push("Removing task queue item from queue due to level progression: "  + graphList.getFirst().contract.prettyName());
				graphList.removeFirst();
			}
			graphList.getFirst().graph.cycle();
		}
	}
	
	public void add(FletchingEnumContract item){
		graphList.add(new FletchingTaskNode(item));
	}
	
	public void add(List<FletchingEnumContract> list){
		list.forEach(this::add);
		Logger.push("Adding items to taskQueue: " + list.toString());
	}
	
	public void sort(){
		Comparator<FletchingTaskNode> comp = Comparator.comparingInt(value -> value.contract.levelReq());
		comp = comp.reversed();
		graphList.sort(comp);
	}
	
	public boolean isEmpty(){
		return graphList.isEmpty();
	}
	
	public int size(){
		return graphList.size();
	}
	
	private final class FletchingTaskNode{
		
		public FletchingTaskNode(FletchingEnumContract contract){
			this.contract = contract;
			FletchingVertex vertex = contract.methodVertex(ctx);
			this.manager = vertex.getManager();
			this.graph = new Graph<>(vertex);
		}
		
		private final FletchingEnumContract contract;
		private final FletchingManager manager;
		private final Graph<TaskVertex> graph;
		
		public boolean shouldQuit(){
			return manager.shouldQuit(ctx);
		}
		
		@Override
		public String toString(){
			return manager.toString();
		}
	}
	
	@Override
	public String toString(){
		return graphList.toString();
	}
}
