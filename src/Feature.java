
public class Feature {

	private int[] pixels;
	private boolean [] guess;
	private boolean[] allPixels;
	private int value;
	private boolean isDummy = false;

	public Feature(int[] pixels, boolean[]guess, boolean[] all) {
		this.pixels = pixels;
		this.guess = guess;
		allPixels = all;
		value = calculateValue();
	}

	public Feature(int i) {
		value = i;
		isDummy = true;
	}

	public int calculateValue() {
		int sum = 0;
		for(int i = 0; i < pixels.length; i++) {
			if(allPixels[i] == guess[i]) sum++;
		}
		if(sum < 3) return 0;
		else return 1;
	}

}
