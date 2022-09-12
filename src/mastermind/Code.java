package mastermind;
import java.util.Random; import java.util.ArrayList;

public class Code {
	public int length;
	public int colorAmount;
	public ArrayList<Integer> code = new ArrayList<Integer>();
	
	public Code(int length, int colorAmount){
		if (colorAmount > 9) {colorAmount=9;}
		if (length > colorAmount) {length = colorAmount;} // if length greater than colorAmount it wont work
		
		this.length = length; this.colorAmount = colorAmount;
		generateCode();
	}
	
	public void generateCode() {
		ArrayList<Integer> used_numbers = new ArrayList<Integer>();
		for (int i=0; i<length; i++) // generating random code
		{
			while(true) // preventing two numbers of the same kind
			{
				int int_random = getRandomNumber(); 
				if (used_numbers.contains(int_random)){
					continue;
				}
				used_numbers.add(int_random);
				code.add(int_random);
				break;
			}
		}
	}
	
	public String toStr(){
		String str = "";
		for (int i: code){
			str += i;
		}
		return str;
	}
	

	public int getRandomNumber() {
	    Random random = new Random();
	    int random_int = random.nextInt(colorAmount);
	    return random_int + 1;
	}
}
