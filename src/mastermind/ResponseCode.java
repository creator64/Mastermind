package mastermind;

public class ResponseCode {
	public int red; // right number right position
	public int white; // right number wrong position
	
	public ResponseCode(int red, int white){
		setRed(red);
		setWhite(white);
	}
	
	public int getRed() {
		return red;
	}
	public void setRed(int red) {
		this.red = red;
	}
	public int getWhite() {
		return white;
	}
	public void setWhite(int white) {
		this.white = white;
	}
}
