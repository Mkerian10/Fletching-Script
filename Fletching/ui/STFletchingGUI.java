package ShowtimeScripts.Fletching.ui;

import STRepo.ST.api.STLogger.Logger;
import STRepo.ST.api.STai.amplified_api.ClientContext;
import STRepo.ST.gui.st_gui.util.TextUtils;
import ShowtimeScripts.Fletching.FletchingEnumContract;
import ShowtimeScripts.Fletching.tasks.options.*;
import ShowtimeScripts.Fletching.utils.TaskQueueManager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class STFletchingGUI extends JFrame{
	
	private static final Color TEXT_GREY = new Color(220, 220, 220);
	private static final Color BACKGROUND_COLOR = new Color(3, 3, 3, 235);
	
	private final TextUtils utils = new TextUtils();
	
	public STFletchingGUI(){
		init();
		this.setVisible(true);
	}
	
	public TaskQueueManager fetchManager(ClientContext ctx){
		while(this.isVisible()){
			try{
				Thread.sleep(500);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		if(enumList.getModel().getSize() == 0){
			ctx.controller.stop();
			Logger.fatal("Failed to find any task items!");
		}
		
		TaskQueueManager q = new TaskQueueManager(ctx, sortItems.isSelected());
		q.add(((FletchingListModel)enumList.getModel()).dumpList());
		return q;
	}
	
	private void init(){
		this.setSize(480, 295);
		this.setResizable(false);
		
		this.setLayout(null);
		
		JPanel mainPane = new JPanel();
		mainPane.setBounds(0, 0, 480, 350);
		mainPane.setOpaque(false);
		mainPane.setLayout(null);
		
		Container cont = getContentPane();
		cont.setBackground(BACKGROUND_COLOR);
		
		cont.add(mainPane);
		
		JLabel title = new JLabel("STFletching");
		title.setFont(new Font("Droid Sans", Font.PLAIN, 36));
		title.setBounds(utils.centerX(mainPane.getBounds(), title.getFontMetrics(title.getFont()), title.getText()), 6, 480, 50);
		title.setForeground(TEXT_GREY);
		mainPane.add(title);
		
		
		enumList.setBounds(4, 2, 198, 200);
		enumList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		enumList.setBackground(TEXT_GREY);
		
		JScrollPane listPane = new JScrollPane(enumList);
		listPane.setBorder(new Border(){
			@Override
			public void paintBorder(Component c, Graphics g, int x, int y, int width, int height){
				g.setColor(new Color(0, 0, 0));
				g.fillRect(x, y, width, height);
				g.clearRect(x + 2, y + 2, width - 3, height - 3);
			}
			
			@Override
			public Insets getBorderInsets(Component c){
				return new Insets(2, 3, 2, 2);
			}
			
			@Override
			public boolean isBorderOpaque(){
				return false;
			}
		});
		listPane.setBounds(10, 80, 200, 100);
		listPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		listPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listPane.setBackground(TEXT_GREY);
		mainPane.add(listPane);
		
		typeBox.setBounds(280, 80, 180, 20);
		typeBox.addActionListener(this::onTypeBoxChange);
		typeBox.setFocusable(false);
		typeBox.setBackground(TEXT_GREY);
		typeBox.getEditor().getEditorComponent().setFocusable(false);
		
		mainPane.add(typeBox);
		
		//valueBox.setVisible(false);
		valueBox.setBounds(280, 110, 180, 20);
		valueBox.setBackground(TEXT_GREY);
		onTypeBoxChange(null);
		mainPane.add(valueBox);
		
		addListItem.setBounds(270, 150, 200, 20);
		addListItem.addActionListener(this::onAddListItemButtonPressed);
		addListItem.setBackground(TEXT_GREY);
		mainPane.add(addListItem);
		
		removeListItem.setBounds(270, 182, 90, 20);
		removeListItem.addActionListener(this::onRemoveListItemButtonPressed);
		removeListItem.setBackground(TEXT_GREY);
		mainPane.add(removeListItem);
		
		clearListItem.setBounds(380, 182, 90, 20);
		clearListItem.addActionListener(this::onClearListItemButtonPressed);
		clearListItem.setBackground(TEXT_GREY);
		mainPane.add(clearListItem);
		
		sortItems.setBounds(5, 185, 180, 17);
		sortItems.addActionListener(e -> sortListItems());
		sortItems.setForeground(new Color(220, 220, 220));
		mainPane.add(sortItems);
		
		startButton.setBounds(5, 210, 470, 60);
		startButton.setFont(startButton.getFont().deriveFont(20f));
		startButton.addActionListener(this::startButtonPressed);
		startButton.setBackground(TEXT_GREY);
		mainPane.add(startButton);
		
		
		this.setLocationRelativeTo(null);
	}
	
	private void startButtonPressed(ActionEvent e){
		this.setVisible(false);
	}
	
	private void sortListItems(){
		if(!sortItems.isSelected()){
			return;
		}
		
		((FletchingListModel) enumList.getModel()).sort(Comparator.comparingInt(FletchingEnumContract::levelReq));
	}
	
	private void onClearListItemButtonPressed(ActionEvent e){
		((FletchingListModel) enumList.getModel()).clear();
		sortListItems();
	}
	
	private void onRemoveListItemButtonPressed(ActionEvent e){
		((FletchingListModel) enumList.getModel()).remove(getCurrentlySelectedEnum());
		sortListItems();
	}
	
	private void onAddListItemButtonPressed(ActionEvent e){
		((FletchingListModel) enumList.getModel()).add(getCurrentlySelectedEnum());
		sortListItems();
	}
	
	private FletchingEnumContract getCurrentlySelectedEnum(){
		if(valueBox.getSelectedItem() == null) throw new NullPointerException();
		
		return matchValue(valueBox.getSelectedItem().toString());
	}
	
	private void onTypeBoxChange(ActionEvent e){
		if(typeBox.getSelectedItem() == null) return;
		
		setListModifiersVisible();
		
		FletchingEnumContract[] typeArr = getTypeValues(typeBox.getSelectedItem().toString());
		Object[] valueArr = Arrays.stream(typeArr).map(FletchingEnumContract::prettyName).toArray();
		valueBox.setModel(new DefaultComboBoxModel<>(valueArr));
	}
	
	private JButton startButton = new JButton("Start");
	
	private JCheckBox sortItems = new JCheckBox("Use Level Progression");
	
	private JButton clearListItem = new JButton("Clear");
	
	private JButton removeListItem = new JButton("Remove");
	
	private JButton addListItem = new JButton("Add");
	
	private String[] types = new String[]{"Arrows", "Bolts", "Fletching", "Darts", "Jewel Bolts", "Stringing"};
	
	private JComboBox<String> typeBox = new JComboBox<>(types);
	
	private JComboBox<Object> valueBox = new JComboBox<>();
	
	private JList<FletchingEnumContract> enumList = new JList<>(new FletchingListModel());
	
	private FletchingEnumContract[] getTypeValues(String typeInput){
		switch(typeInput){
			case "Arrows":
				return ArrowOption.values();
			
			case "Bolts":
				return BoltOption.values();
			
			case "Fletching":
				return CuttingOption.values();
			
			case "Darts":
				return DartOption.values();
			
			case "Jewel Bolts":
				return JewelBoltOptions.values();
			
			case "Stringing":
				return StringingOption.values();
		}
		throw new NullPointerException();
	}
	
	private void setListModifiersVisible(){
		valueBox.setVisible(true);
		addListItem.setVisible(true);
	}
	
	private FletchingEnumContract matchValue(String prettyName){
		for(FletchingEnumContract f : allValues()){
			if(f.prettyName().equalsIgnoreCase(prettyName)){
				return f;
			}
		}
		throw new NullPointerException();
	}
	
	private FletchingEnumContract[] allValues(){
		ArrayList<FletchingEnumContract> list = new ArrayList<>();
		for(String s : types){
			list.addAll(Arrays.asList(getTypeValues(s)));
		}
		
		FletchingEnumContract[] arr = new FletchingEnumContract[list.size()];
		for(int i = 0; i < list.size(); i++){
			arr[i] = list.get(i);
		}
		return arr;
	}
	
}
