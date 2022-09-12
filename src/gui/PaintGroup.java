package gui;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;

import components.PaintLabel;

public class PaintGroup 
{
	public GameScreen master;
	public ArrayList<PaintLabel> painters = new ArrayList<PaintLabel>(); // a list of all the painters in the group
	
	public PaintGroup(GameScreen master) 
	{
		this.master = master;
		addPainters();
		painters.get(0).select(); // painters.get(0) will be the first PaintLabel instance
	}
	
	private void addPainters()
	{
		GridBagConstraints c = master.c;
		c.gridy = 1; // second row (first is the label 'mastermind')
        c.gridwidth = 2; c.gridheight = 2; 
        c.weightx = 1.0; c.weighty = 1.0;
        for (int x=0; x < master.colorAmount; x++){
        	PaintLabel paintlbl = new PaintLabel(this, x); // without text the labels wont show
        	c.insets = new Insets(0,0,20,10); // add margin to the right and bottom
        	c.gridx = 2 * x;
        	master.panel.add(paintlbl, c);
        	painters.add(paintlbl); // add every painter to a list to identify them
        }
	}
	
	public void selectPainterToTheLeft() {
		int id = getSelectedPainterId(); int newId;
		if (id == 0) {newId = master.colorAmount - 1;} // if id is 0 we cant go to the left so we go to the ultimate right
		else {newId = id - 1;}
		PaintLabel nextPainter = painters.get(newId);
		nextPainter.select();
	}
	
	public void selectPainterToTheRight() {
		int id = getSelectedPainterId(); int newId;
		if (id == master.colorAmount - 1) {newId = 0;} // if id is master.colorAmount - 1 we cant go to the right so we go to the ultimate left
		else {newId = id + 1;}
		PaintLabel nextPainter = painters.get(newId);
		nextPainter.select();
	}
	
	public void deselectAllPainters() 
	{
		for (PaintLabel painter: painters) 
		{
			painter.deselect();
		}
	}
	
	public int getSelectedPainterId() 
	{
		for (PaintLabel painter: painters) 
		{
			if (painter.selected == true) 
			{
				return painter.id;
			}
		}
		return 0;
	}
}
