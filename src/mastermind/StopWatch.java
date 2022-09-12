package mastermind;

public class StopWatch extends Thread {
	private int seconds = 0;
	public boolean paused = false;
	
	public void run() {
		while (true) {
			try {Thread.sleep(1000);} // wait a seconds
			catch (Exception e) {System.out.println(e);}
			if (!paused) {seconds += 1;}
		}
	}
	
	public void pause() {paused = true;}
	public void unpause() {paused = false;}

	public int getSeconds() {
		return seconds;
	}
	
	public int[] getMinuteSecond() {
		int minutes = (seconds - (seconds % 60))/60;
		int rest_seconds = seconds % 60;
		int[] ms = {minutes, rest_seconds};
		return ms;
	}
	
	public String getMinuteSecondString() {
		int[] ms = getMinuteSecond();
		String minString = "minuten";
		String secString = "seconden";
		if (ms[0] == 1) {minString = "minuut";} if (ms[1] == 1) {secString = "seconde";}
		return ms[0] + " " + minString + " en " + ms[1] + " " + secString;
	}
}
