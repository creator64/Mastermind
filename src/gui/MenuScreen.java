package gui;

import javax.swing.BoxLayout;
import java.awt.Container;
import javax.swing.border.EmptyBorder;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Font;


public class MenuScreen extends JFrame
{
	JPanel panel = new JPanel();
	// define components
	private JLabel lblMain = new JLabel("Welkom Bij MasterMind");
	private JButton btnPlay = new JButton("Spelen");
	private JLabel lblinfo1 = new JLabel("Kies de lengte van de code:");
	private JLabel lblinfo2 = new JLabel("Kies het aantal mogelijke kleuren van de code:");
	private JLabel lblinfo3 = new JLabel("Kies het maximaal aantal pogingen:");
	private JSlider sllength = new JSlider(JSlider.HORIZONTAL, 1, 9, 4);
	private JSlider slamount = new JSlider(JSlider.HORIZONTAL, 1, 9, 6);
	private JSlider slattempt = new JSlider(JSlider.HORIZONTAL, 1, 12, 10);
	JSlider[] sliders = {sllength, slamount, slattempt};
	
	public MenuScreen()
	{
        // Set the BoxLayout to be Y_AXIS: from top to down
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);
         
        // Set border for the panel
        panel.setBorder(new EmptyBorder(new Insets(100, 100, 100, 100)));  
        
        //change components
        for (JSlider slider: sliders) {
        	slider.setMinorTickSpacing(1);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);				
            slider.setLabelTable(slider.createStandardLabels(1));
        }
        
        lblMain.setFont(new Font("Serif", Font.BOLD, 25));
        btnPlay.setFont(new Font("calibri", Font.PLAIN, 17));
        lblinfo1.setFont(new Font("open sans", Font.ITALIC, 14));
        lblinfo2.setFont(new Font("open sans", Font.ITALIC, 14));
        lblinfo3.setFont(new Font("open sans", Font.ITALIC, 14));
        
        // add components
        panel.add(lblMain);
        panel.add(Box.createRigidArea(new Dimension(20, 20)));
        panel.add(btnPlay);
        panel.add(Box.createRigidArea(new Dimension(20, 20)));
        panel.add(lblinfo1);
        panel.add(sllength);
        panel.add(lblinfo2);
        panel.add(slamount); 
        panel.add(lblinfo3);
        panel.add(slattempt); 
        
        // add listeners
        addListeners();
         
        // Set the window properties
        this.add(panel);
        this.pack();
        this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void addListeners()
	{
		btnPlay.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int length = sllength.getValue(); int amount = slamount.getValue(); int attempts = slattempt.getValue();
				GameScreen gs = new GameScreen(length, amount, attempts);
			}
		}	
		);
	}
}
