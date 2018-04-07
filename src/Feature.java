
public class Feature {

	private double INITIAL_WEIGHT = 0.5;
	private int[] pixels;
	private boolean[] guess;
	private double value;
	private double weight;
	private boolean isDummy = false;

	public Feature(int[] pixels, boolean[] guess) {
		weight = INITIAL_WEIGHT;
		this.pixels = pixels;
		this.guess = guess;
		if (isDummy)
			value = 1;
		printPixels();
	}

	public Feature(int i) {
		value = i;
		isDummy = true;
	}

	public int calculateValue(Image img) {
		if (isDummy)
			return 1;
		else {
			int sum = 0;
			for (int i = 0; i < pixels.length; i++) {
				if (img.getPixels()[pixels[i]] == guess[i]) {
					sum++;
				}
			}
			if (sum < 3)
				return 0;
			else
				return 1;
		}
	}

	public void setWeight(double w) {
		this.weight = w;
	}

	public double getWeight() {
		return this.weight;
	}

	public double evaluate(Image img) {
		return this.weight * calculateValue(img);
	}

	public void printPixels(){
		String a = Integer.toString(pixels[0]);
		String b = Integer.toString(pixels[1]);
		String c = Integer.toString(pixels[2]);
		String d = Integer.toString(pixels[3]);
		//System.out.println(a+b+c+d);
	}
}
