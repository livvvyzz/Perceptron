
public class Feature {

	private double INITIAL_WEIGHT = 0.5;
	private int[] pixels;
	private boolean[] guess;
	private double value;
	private double weight;
	private boolean isDummy = false;

	public Feature(int[] pixels, boolean[] guess) {
		this.pixels = pixels;
		this.guess = guess;
		if (isDummy)
			value = 1;
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
	
	public void print(){
		StringBuilder s = new StringBuilder();
		s.append("[");
		for(int i = 0; i < pixels.length; i++){
			s.append(pixels[i]+",");
		}
		s.append(weight +"]");
		s.toString();
		System.out.println(s);
		
	}


}
