package components;

import java.awt.*;
import javax.swing.*;
import gui.*;

public class ResultLabel extends JLabel
{
	public Row master;
	public int id;
	public GameScreen gs;
	public Color defaultColor = Color.WHITE;
	public Color activeColor = Color.LIGHT_GRAY;
	public Color color;

	public ResultLabel(Row master, int id) {
		super(String.valueOf(id));
		this.master = master; this.id = id;
		this.gs = master.master;
		
		setFont(new Font("Serif", Font.BOLD, 10));
       	paint(defaultColor);
       	setOpaque(true);
       	setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
	}
	
	public void paint(Color color) {
		setBackground(color); setForeground(color); // set fg to bg color so text is not visible
		this.color = color;
	}
	
	public void paintTemporarily(Color color) {
		setBackground(color); setForeground(color); // set fg to bg color so text is not visible
	}
}
