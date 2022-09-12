package components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gui.*;

public class FillLabel extends JLabel
{
	public Row master;
	public int id;
	public GameScreen gs;
	public Color defaultColor = Color.WHITE;
	public Color activeColor = Color.LIGHT_GRAY;
	public Color color;

	public FillLabel(Row master, int id) {
		super(String.valueOf(id), SwingConstants.CENTER);
		
		this.master = master; this.id = id;
		this.gs = master.master;
		
		setFont(new Font("modak", Font.BOLD, 25));
		paint(defaultColor);
	    setOpaque(true);
	    setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	    addActionListeners();
	}
	
	public void paint(Color color) {
		setBackground(color); setForeground(color); // set fg to bg color so text is not visible
		this.color = color;
	}
	
	public void paintTemporarily(Color color) {
		setBackground(color); setForeground(color); // set fg to bg color so text is not visible
	}
	
	public void write(String text) {
		setForeground(Color.BLACK);
		setText(text);
	}
	
	public boolean filled() 
	{
		if (this.color != defaultColor && this.color != activeColor) {
			return true;
		}
		return false;
	}
	
	public int getPaintedColorId() 
	{
		if (color == defaultColor || color == activeColor) {
			return 0;
		}
		int x = 0;
		while (true) {
			if (gs.colorList[x] == this.color) {
				return x;
			}
			x++;
			if (x >= 1000) {break;} // safety: prevents endless loop
		}
		return 0;
	}
	
	public void addActionListeners()
	{
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) 
			{
				if (master.active) 
				{
					int selectedPainterId = gs.pg.getSelectedPainterId();
					Color color = gs.colorList[selectedPainterId];
					paint(color);
				}
		    }
			
			public void mouseEntered(MouseEvent e) 
			{
				if (master.active) 
				{
					int selectedPainterId = gs.pg.getSelectedPainterId();
					Color color = gs.colorList[selectedPainterId];
					paintTemporarily(color);
				}
		    }
			
			public void mouseExited(MouseEvent e) 
			{
				if (master.active) 
				{
					paint(color);
				}
		    }
		});
	}
}
