package ShowtimeScripts.Fletching.ui;

import STRepo.ST.api.STai.amplified_api.ClientContext;
import STRepo.ST.paint.Illustrator;
import STRepo.ST.paint.MutableInteger;
import STRepo.ST.paint.script_paint.ScriptTemplate;
import org.powerbot.script.PaintListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class STFletchingPaint implements PaintListener{
	
	private MutableInteger integer;
	private long startTime  = System.currentTimeMillis();
	private ClientContext ctx;
	
	public STFletchingPaint(ClientContext ctx, MutableInteger runtime){
		this.illustrator = new Illustrator(ctx);
		this.integer = runtime;
		illustrator.setTotalRuntime(runtime);
		illustrator.setActiveRuntime(runtime);
		ctx.dispatcher.add(this);
		this.ctx = ctx;
		addPaintItems(ctx);
	}
	
	private final Illustrator illustrator;
	
	@Override
	public void repaint(Graphics graphics){
		if(ctx.controller.isStopping()){
			ctx.dispatcher.remove(this);
		}
		
		illustrator.draw((Graphics2D)graphics);
		integer.setValue((int)(System.currentTimeMillis() - startTime));
	}
	
	private void addPaintItems(ClientContext ctx){
		final List<Callable<Object>> callableList = new ArrayList<>();
		
		
		ScriptTemplate template = new ScriptTemplate(new Rectangle(50, 80, 225, 250), "STFletching", callableList, illustrator, ctx);
		template.addBackground(new Color(0, 0, 0, 145));
		template.addBorder(Color.WHITE);
		illustrator.add(template);
	}
}
