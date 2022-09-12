package mastermind;

import java.util.ArrayList;
import java.util.Collections;
import gui.GuiGame;


public class Main
{
	public static int attempts = 10;
	
	public static void main(String[] args) {
		String mode = "gui";
		
		if (mode=="console"){
			ConsoleGame cg = new ConsoleGame();
			cg.start();
		}
		else if (mode=="gui"){
			GuiGame gg = new GuiGame();
			gg.start();
		}
	}
	
	public static void intToArrayList(int n, ArrayList<Integer> list) // list works as a pointer
	{
		while (n > 0) // if n is 5436 then we will get the ArrayList: [6, 3, 4, 5]
		{
			int digit = n % 10;
			list.add(digit);
			n /= 10;
		}
		Collections.reverse(list); // reverse so the ArrayList becomes [5, 4, 3, 6] and ready to compare
	}
}
