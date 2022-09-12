package components;

import java.awt.*;
import javax.swing.*;

import gui.GameScreen;
import gui.PaintGroup;

import java.awt.event.*;

public class PaintLabel extends JLabel 
{
	public int id;
	public PaintGroup master;
	public GameScreen gs; // the gamescreen aka master.master
	public boolean selected = false;
	
	public PaintLabel(PaintGroup master, int id) 
	{
		super(String.valueOf(id));
		this.id = id; 
		this.master = master; this.gs = master.master;
		this.setFont(new Font("Serif", Font.BOLD, 30));
    	this.setBackground(gs.colorList[id]); this.setForeground(gs.colorList[id]); // set fg to bg color so text is not visible
    	this.setOpaque(true);
    	this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    	addActionListeners();
	}
	
	public void select()
	{
		master.deselectAllPainters(); // first deselect all painters then select itself
		selected = true;
		this.setFont(new Font("Serif", Font.BOLD, 35));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
	}
	
	public void deselect() 
	{ 
		selected = false;
		this.setFont(new Font("Serif", Font.BOLD, 30));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
	}

	public void addActionListeners()
	{
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) 
			{
				select();
		    }
		});
	}
}
