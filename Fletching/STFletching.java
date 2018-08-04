package ShowtimeScripts.Fletching;

import STRepo.ST.STScript.STScript;
import STRepo.ST.api.STLogger.Logger;
import STRepo.ST.api.STai.CustomException;
import STRepo.ST.api.STai.amplified_api.ClientContext;
import STRepo.ST.database.STDB;
import ShowtimeScripts.Fletching.ui.STFletchingGUI;
import ShowtimeScripts.Fletching.ui.STFletchingPaint;
import ShowtimeScripts.Fletching.utils.FletchingExpWatcher;
import ShowtimeScripts.Fletching.utils.TaskQueueManager;
import org.powerbot.script.Script;

import java.util.concurrent.Callable;

@Script.Manifest(name = "STFletching",
		description = "Trains fletching, incorporating all possible methods easily.",
		properties = "author=ShowtimeScripts; topic = 1346482; client = 4;")
public class STFletching extends STScript<ClientContext>{
	
	private final int FLETCHING = 9;
	
	private TaskQueueManager taskQueueManager;
	
	private final STDB stdb = new STDB("STFletching");
	
	@Override
	public void start(){
		super.start();
		
		this.taskQueueManager = new STFletchingGUI().fetchManager(ctx);
		new STFletchingPaint(ctx, runtime);
		
		addDatabaseFields();
		
		new Thread(new FletchingExpWatcher(ctx)).start();
		FletchingExpWatcher.pushTime(5000);
	}
	
	@Override
	public void poll(){
		super.poll();
		
		taskQueueManager.execute();
		
		if(taskQueueManager.isEmpty()){
			Logger.warning("RAN OUT OF TASKS, ENDING SCRIPT!");
			throw new CustomException("All Tasks Complete.", "Task-Completion Notice");
		}
	}
	
	@Override
	public void stop(){
		stdb.send();
		super.stop();
	}
	
	private void addDatabaseFields(){
		stdb.add("userID", powerbotID);
		stdb.add("runtime", (Callable<Integer>) () -> runtime.getValue());
		stdb.add("epoch", (Callable<Long>) System::currentTimeMillis);
		stdb.add("experience", (Callable<Integer>) () -> ctx.skills.experience(FLETCHING) - skillStartExperienceMap.get(FLETCHING));
		stdb.add("startLevel", (Callable<Integer>) () -> skillStartLevelMap.get(FLETCHING));
		stdb.add("endLevel", (Callable<Integer>) () -> ctx.skills.level(FLETCHING));
		stdb.add("queueSize", taskQueueManager.size());
		stdb.add("methods", taskQueueManager.toString());
	}
}
