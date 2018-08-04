package ShowtimeScripts.Fletching.utils;

import STRepo.ST.api.STLogger.Stopwatch;
import STRepo.ST.api.STai.amplified_api.ClientContext;
import org.powerbot.script.rt4.Constants;

public class FletchingExpWatcher implements Runnable{
	
	public FletchingExpWatcher(ClientContext ctx){
		this.ctx = ctx;
		cacheExp = getExp();
	}
	
	private final ClientContext ctx;
	
	private final static int FLETCHING = Constants.SKILLS_FLETCHING;
	
	private int cacheExp;
	
	private static Stopwatch sw = new Stopwatch();
	
	@Override
	public void run(){
		while(!ctx.controller.isStopping()){
			
			if(getExp() != cacheExp){
				cacheExp = getExp();
				sw.refreshTime();
			}
			
			try{
				Thread.sleep(220);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	public static void refresh(){
		sw.refreshTime();
	}
	
	private int getExp(){
		return ctx.skills.experience(FLETCHING);
	}
	
	public static boolean trigger(int ms){
		return sw.getTime() < ms;
	}
	
	public static int getTime(){
		return (int)sw.getTime();
	}
	
	public static void pushTime(int ms){
		sw.pushTime(ms);
	}
}
