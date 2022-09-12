package mastermind;

import java.util.ArrayList;
import java.util.Collections; 

public class InnerBoard // rules everything behind the scenes
{ 
	public Code answer = null;
	
	public InnerBoard(int length, int amount){
		getCode(length, amount);
	}
	
	public void getCode(int length, int amount){
		Code c = new Code(length, amount); // make a code that the player has to guess with length <length> and <amount> different numbers
		answer = c;
	}
	
	public boolean checkGuessForDuplicates(int guess)
	{
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		Main.intToArrayList(guess, numbers);
		for (int n: numbers) // check for duplicate numbers
		{ 
			int f = Collections.frequency(numbers, n); 
			if (f > 1) {return false;}
		}
		return true;
	}
	
	public boolean checkGuessForLength(int guess){
		int length = String.valueOf(guess).length();
		if (length == answer.length){
			return true;
		}
		return false;
	}
	
	//public void compareCodeWithString(String guess){}
	
	public ResponseCode compareCodeWithInt(int guess){
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		Main.intToArrayList(guess, numbers);
		return compareCode(numbers); // if guess is 3456 then numbers is [3, 4, 5, 6]
	}
	
	public ResponseCode compareCode(ArrayList<Integer> guess) // guess is the guess of the player in form ArrayList[n1, n2, n3, n4]
	{
		int amount_red = 0;
		int amount_white = 0;
		ArrayList<Integer> real_code = this.answer.code; // the code that the player has to guess in form ArrayList[n1, n2, n3, n4]
		for (int number: guess) // go through all the numbers in the guess of the player
		{
			if (real_code.contains(number) == false) {continue;}
			// this code will only run if the guessed number is in the real code
			int index_real_code = real_code.indexOf(number); // the index of the number in the real code
			int index_guess = guess.indexOf(number); // the index of the number in the guess
			if (index_real_code == index_guess) 
			{
				amount_red += 1;
			}
			else 
			{
				amount_white += 1;
			}
		} 
		return new ResponseCode(amount_red, amount_white);
	}
}
