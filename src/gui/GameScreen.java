package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mastermind.*;
import components.*;

import java.util.ArrayList;

public class GameScreen extends JFrame implements KeyListener
{
	static final long serialVersionUID = 1L;
	public int length;
	public int colorAmount;
	public int attempts;
	int gridwidth = 100;
	public Color colorList[] = {Color.red, Color.blue, Color.yellow, Color.green, Color.cyan, Color.orange, Color.MAGENTA, Color.pink, new Color(17, 125, 66)};
	public InnerBoard ib;
	public JPanel panel = new JPanel();
	public PaintGroup pg;
	public ArrayList<Row> rows = new ArrayList<Row>();
    public GridBagLayout gbl = new GridBagLayout();
    public GridBagConstraints c = new GridBagConstraints();
    public int currentRowId = 0;
    StopWatch stopwatch = new StopWatch();
	
	private void addFixedComponents()
	{
		// Define components
		JButton btnEnter = new JButton("ENTER");
		JLabel lblMm = new JLabel("       MASTERMIND       ");
				
		// Change components
		lblMm.setFont(new Font("Serif", Font.BOLD, 8*colorAmount));
		btnEnter.setFont(new Font("Serif", Font.BOLD, 20));
		btnEnter.setFocusable(false);
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				handle_guess();
		    }
		});
		        
		 // Add components
		 c.gridwidth = gridwidth;
		 c.gridx = 0; c.gridy = 0;
		 panel.add(lblMm, c);
		 c.gridy = 100;
		 //c.gridwidth = 1;
		 panel.add(btnEnter, c);
	}
	
	public void handle_guess() 
	{
		Row row = rows.get(currentRowId);
		String errorMessage = checkGuess(row);
		if (errorMessage != "#~#VALID#~#")
		{
			JOptionPane.showMessageDialog(this, errorMessage, "FOUTE INVOERING", JOptionPane.ERROR_MESSAGE);
		}
		else 
		{
			int guess = row.extract();
			ResponseCode rc = ib.compareCodeWithInt(guess);
			int amountRed = rc.getRed(); int amountWhite = rc.getWhite();
			// fill the result labels
			for (int x=0; x < amountWhite; x++) // for example 4 whites, 2 reds: fill the reslbls from id 0 till 3
			{
				ResultLabel resLabel = row.resultlabels.get(x);
				resLabel.paint(Color.BLACK); // white is already the default color so were filling it with black
			}
			for (int x=amountWhite; x < amountWhite + amountRed; x++) // fill the reslbls from id 4 till 5
			{
				ResultLabel resLabel = row.resultlabels.get(x);
				resLabel.paint(Color.RED);
			}
			// end: fill the result labels
			if (amountRed == length) {win();}
			else if (currentRowId == attempts - 1) {lose();} // we start at currentRowId = 0
			else 
			{
				row.deactivate(); // deactivate the old row
				currentRowId += 1;
				Row newRow = rows.get(currentRowId); // get the next row and activate it
				newRow.activate();
			}
		}
	}
	
	public void win() {
		Object[] options = {"Ja", "Nee"};
		String mss = stopwatch.getMinuteSecondString();
		int n = JOptionPane.showOptionDialog(
			    this,
			    "Gefeliciteerd! Je hebt het antwoord goed geraden in " + mss + " :)\n" +
			    "Wil je nog een keer spelen?",
			    "GOED GERADEN", // title
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,     //do not use a custom Icon
			    options,  //the titles of buttons
			    options[0]); //default button title;
		if (n == 0) // 0 corresponds with 'yes'
		{
			playAgain();
		}
		else {
			close();
		}
	}
	
	public void lose() {
		Row answerRow = rows.get(attempts);
		answerRow.paintAnswer();
		Object[] options = {"Ja", "Nee"};
		int n = JOptionPane.showOptionDialog(
			    this,
			    "Helaas! Je hebt te veel pogingen gedaan :(\n" +
			    "Wil je het nog een keer proberen?",
			    "TE VEEL POGINGEN", // title
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,     //do not use a custom Icon
			    options,  //the titles of buttons
			    options[0]); //default button title;
		if (n == 0) // 0 corresponds with 'yes'
		{
			playAgain();
		}
		else {close();}
	}
	
	public String checkGuess(Row row)
	{
		int guess = row.extract();
		if (row.allFilled() != true) {
			return "Een of meer hokjes zijn niet gevuld";
		}
		if (ib.checkGuessForDuplicates(guess) == false) {
			return "Geen dubbele kleuren toegestaan";
		}
		return "#~#VALID#~#";
	} 
	
	private void addPaintGroup()  {pg = new PaintGroup(this);} 
	
	private void addRows()
	{
		for (int x=0; x < attempts; x++) {
			Row row = new Row(this, 2*x + 3, false);
			rows.add(row);
			panel.add(new JSeparator(), c);
		}
		// add the answer row
		Row answerRow = new Row(this, 2*attempts + 3, true);
		rows.add(answerRow);
		// activate the first row
		rows.get(0).activate(); 
	}
	
	@SuppressWarnings("unused")
	public void playAgain() {
		GameScreen gs = new GameScreen(length, colorAmount, attempts); // make a new gamescreen with same length of code and amount of colors to guess
		close(); // close this gamescreen
	}
	
	public void close() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)); // closes the window
	}
	
	public void doMouseEnter() // when hovering over a fillLabel and changing the selected painter with arrow this function will make the fillLabel change color
	{
		try 
    	{
    		PointerInfo a = MouseInfo.getPointerInfo();
    		Point b = a.getLocation();
    		int x = (int) b.getX(); int y = (int) b.getY();
    		Robot r = new Robot();
    		r.mouseMove(0, 0); r.mouseMove(x, y);
    	} 
    	catch(Exception ex) {}
	}
	
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

	@Override
    public void keyPressed(KeyEvent e)
	{
        switch (e.getKeyCode()) {
        case '\n': // enter-button
        	handle_guess();
        	break;
        case 37: // left-arrow
        	pg.selectPainterToTheLeft();
        	doMouseEnter();
        	break;
        case 39: // right-arrow
        	pg.selectPainterToTheRight();
        	doMouseEnter();
        	break;
        case 67:  // c
        	String mss = stopwatch.getMinuteSecondString();
        	stopwatch.pause();
        	JOptionPane.showMessageDialog(this, "je bent al " + mss + " bezig", "TIJD", JOptionPane.INFORMATION_MESSAGE);
        	stopwatch.unpause();
        	break;
        }
    }
	
	public GameScreen(int length, int amount, int attempts){
		ib = new InnerBoard(length, amount);
		this.length = ib.answer.length;
		this.colorAmount = ib.answer.colorAmount;
		this.attempts = attempts;
		stopwatch.start();
		
		c.fill = GridBagConstraints.HORIZONTAL;  
	    panel.setLayout(gbl);
	    
	    addFixedComponents();
	    addPaintGroup();
	    addRows();
	    //Row answerRow = rows.get(attempts); answerRow.paintAnswer(); // uncomment if you want to see the answer from the start
	    
        // Set the window properties
        this.add(panel);
        this.addKeyListener(this);
        this.pack();
        this.setVisible(true);
		this.setResizable(false);
		this.setFocusable(true);
	}
}
