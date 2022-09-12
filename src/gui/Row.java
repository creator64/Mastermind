package gui;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import components.*;
import java.lang.Math;
import mastermind.*;

public class Row 
{
	public int rowid;
	public boolean active = false;
	public GameScreen master;
	ArrayList<FillLabel> fillerlabels = new ArrayList<FillLabel>();
	ArrayList<ResultLabel> resultlabels = new ArrayList<ResultLabel>();
	
	public Row(GameScreen master, int rowid, boolean answer) 
	{
		this.rowid = rowid;
		this.master = master;
		addFillers();
		if (!answer) {
			addResultLabels(); // we dont need resultlabels if the row is an answerrow
		}
		else {
			handle_answer_row();
		}
	}
	
	public void handle_answer_row() {
		for (FillLabel fl: fillerlabels) {fl.write("?");} // answer labels
	}
	
	public boolean allFilled() {
		for (FillLabel fl: fillerlabels) {
			if (fl.filled() == false) {
				return false;
			}
		}
		return true;
	}
	
	public int extract() 
	{
		int guess = 0; // real guess is red blue yellow green (1234)
		for (FillLabel fl: fillerlabels) // guess: 0 -> 0 -> 1 -> 10 -> 12 -> 120 -> 123 -> 1230 -> 1234
		{
			int n = fl.getPaintedColorId() + 1; // first color returns 0 so well count that as 1
			guess *= 10;
			guess += n;
		}
		return guess;
	}
	
	public void addFillers()
	{
		GridBagConstraints c = master.c;
		c.gridwidth = 2; c.gridheight = 2;
        c.gridy = this.rowid; // Basically the y-index in the gridlayout
        c.weightx = 1.0; c.weighty = 1.0;
        for (int x=0; x < master.length; x++)
        {
        	c.insets = new Insets(0,0,10,10); // add margin to the right and below
        	c.gridx = 2 * x;
        	FillLabel lblfill = new FillLabel(this, x); // x is the id
            master.panel.add(lblfill, c);
            fillerlabels.add(lblfill);
        }
	}
	
	public void addResultLabels() 
	{
		GridBagConstraints c = master.c;
		c.gridwidth = 1; c.gridheight = 1;
		
	    for (int x=0; x < master.length; x++) // small labels (result labels)
	    {
	    	int divider = (int)Math.ceil((double)master.length/2);
	    	ResultLabel lblres = new ResultLabel(this, x); 
	        c.gridx = 2 * master.colorAmount + (x % divider); // start pos for small fillLabels is 2 * colorAmount
	        c.insets = new Insets(0,0,0,5); // add margin to the right
	        master.panel.add(lblres, c);
	        if (x == divider - 1){
	        	c.gridy += 1;
	        }
	        resultlabels.add(lblres);
	    }
	}
	
	public void paintAnswer() {
		fillerlabels.get(0).paint(Color.RED);
		Code c = master.ib.answer; // c.code is the arraylist that contains the numbers in the right order
		for (int x=0; x < c.code.size(); x++) // go through all the numbers (by index)
		{
			int number = c.code.get(x); // get the number with index x
			Color color = master.colorList[number - 1]; // get the color that belongs to this number
			fillerlabels.get(x).paint(color); // paint the filler with index x with the right color
		}
	}
	
	public void activate() 
	{
		this.active = true;
		for (FillLabel fl: fillerlabels) 
		{
			fl.paint(fl.activeColor);
		}
	}
	
	public void deactivate() 
	{
		this.active = false;
	}
}
