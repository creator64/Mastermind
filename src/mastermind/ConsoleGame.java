package mastermind;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
//import java.util.ArrayList;

public class ConsoleGame 
{
	InnerBoard ib = null;
	boolean running = true;
	int current_attempt = 1;
	ArrayList<String> saved_results = new ArrayList<String>();
	StopWatch stopwatch = new StopWatch();
	
	public void start(){
		ib = new InnerBoard(4, 6); // create an innerboard
		showWelcomeMessage(); // first step: welcome the user to the game and set the screen up
		stopwatch.start(); // start our stopwatch
		mainLoop(); // second step: play the game
		System.out.println("Spel beeindigd");
	}
	
	private void showWelcomeMessage(){
		System.out.println("Welkom bij Mastermind. Veel plezier!");
		System.out.println("");
		System.out.println("   M A S T E R M I N D");
		System.out.println("--------------------------");
	}
	
	private void mainLoop()
	{
		Scanner sc = new Scanner(System.in);
		//System.out.println(ib.answer.toStr()); // prints the answer at the beginning of every game
		while (running)
		{
			System.out.println("Neem een gok");
			int guess = sc.nextInt();
			String errorMessage = checkGuess(guess);
			if (errorMessage != "#~#VALID#~#") // if sth is wrong with the number, print the error message and continue to the next attempt
			{
				System.out.println(errorMessage);
				continue;
			} 
			ResponseCode rc = ib.compareCodeWithInt(guess);
			addFormatResult(rc, guess);
			printAllResults();
			current_attempt += 1;
			if (rc.red == ib.answer.length) // if you have four times red and the length of the code is 4, you have guessed the right number
			{
				win();
				break;
			}
			if (current_attempt > Main.attempts){
				lose();
				break;
			}
		}
		sc.close();
	}
	
	private void addFormatResult(ResponseCode rc, int guess){
		// make and save format_string in form: 1:  1 2 3 4    RRW	
		String format_string;
		format_string = this.current_attempt + ":  " + guess + "    "; // 1:  1234
		for (int x=0; x < rc.red; x++){
			format_string += "R";
		} // 1: 1234 RR
		for (int x=0; x < rc.white; x++){
			format_string += "W";
		} // 1: 1234 RRW
		saved_results.add(format_string);
	}
	
	private void printAllResults(){
		for (String fr: saved_results){
			System.out.println(fr);
		}
	}
	
	public String checkGuess(int guess)
	{
		if (ib.checkGuessForDuplicates(guess) == false) {
			return "Foute invoering: geen dubbele cijfers toegestaan";
		}
		if (ib.checkGuessForLength(guess) == false) {
			return "Foute invoering: te kort of lang getal";
		}
		return "#~#VALID#~#";
	} 
	
	private void win(){
		String mss = stopwatch.getMinuteSecondString();
		System.out.println("Gefeliciteerd! Je hebt het antwoord goed geraden in " + mss + " :)");
	}
	
	private void lose(){
		System.out.println("Helaas! Je hebt te veel pogingen gedaan :(");
		System.out.println("Het juiste antwoord was: " + ib.answer.toStr()); 
	}
}
