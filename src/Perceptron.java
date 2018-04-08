import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Perceptron {

	ArrayList<Image> images;
	ArrayList<Feature> features;

	// accuracy changes depending on these
	public double learningRate;
	public double randomNum;
	public int seed;

	int numCycles;

	public Perceptron(String fname) {

		// create img array
		readFile(fname);
		
		/*
		 * uncomment testAccuracy to find accuracy over 100 runs
		 */
		
		//testAccuracy();
		runOnce();
	}
	
	public void testAccuracy(){
		int numConverges = 0;
		int totalConverges = 0;
		int minCycle = 999;
		int maxCycle = 0;
		double averageCycle = 0; 
		
		for(int i = 0; i < 100; i++){
			//initialise everything
			Random r = new Random();
			learningRate = r.nextDouble();
			randomNum = r.nextDouble();
			seed = r.nextInt(50);
			// initialise features
			initFeatures();
			run();
			test();
			
			if(numCycles != 1000){
				numConverges++;
				totalConverges += numCycles;
				if(numCycles > maxCycle) maxCycle = numCycles;
				else if(numCycles <minCycle) minCycle = numCycles;
			}
		}
		
		averageCycle = totalConverges / numConverges;
		System.out.println("Converged on " + numConverges + " runs");
		System.out.println("Average number of Cycles: " + averageCycle);
		System.out.println("Max number of Cycles: " + maxCycle);
		System.out.println("Min number of Cycles: " + minCycle);
	}
	
	public void runOnce(){
		//initialise everything
		Random r = new Random();
		learningRate = r.nextDouble();
		randomNum = r.nextDouble();
		seed = r.nextInt(50);
		// initialise features
		initFeatures();
		run();
		test();
		printFeatures();
	}

	public void readFile(String fname) {
		images = new ArrayList<Image>();

		System.out.println("Reading data from file " + fname);
		try {
			Scanner sc = new Scanner(new File(fname));
			while (sc.hasNext()) {
				sc.next(); // p1
				char classType = sc.next().toCharArray()[1]; // class type
				// test
				String a = sc.next(); // width
				String b = sc.next(); // height

				int w = Integer.parseInt(a);
				int h = Integer.parseInt(b);

				// 2 lines of data for pixels
				boolean[] pixels = new boolean[w * h];
				String st = sc.next(); // first line
				String s2 = sc.next(); // second line
				String s3 = st + s2;
				char[] chars = s3.toCharArray();
				int index = 0;

				for (char c : chars) {
					boolean sgn = true;
					;
					if (c == '0') {
						sgn = false;
					} else if (c == '1') {
						sgn = true;
					}
					pixels[index] = sgn;
					index++;
				}
				Image img = new Image(classType, pixels);
				images.add(img);

			}
			sc.close();
		} catch (IOException e) {
			throw new RuntimeException("Data File caused IO exception");
		}

	}

	public void initFeatures() {
		// dummy feature
		features = new ArrayList<Feature>();
		features.add(new Feature(1));

		// 50 random features
		for (int i = 0; i < 50; i++) {
			int[] pixelNums = new int[4];
			boolean[] guess = new boolean[4];
			Random r = new Random(seed + i);
			for (int j = 0; j < 4; j++) {
				pixelNums[j] = r.nextInt(50);
				guess[j] = r.nextBoolean();
			}

			Feature f = new Feature(pixelNums, guess);
			f.setWeight(r.nextDouble() * randomNum);
			features.add(f);
		}
	}

	public void run() {

		int incorrect = images.size();
		int correct = 0;
		numCycles = 0;
		for (int i = 0; i < 1000 && incorrect != 0; i++) {
			int sum = 0;
			// iterate through each image
			for (Image img : images) {

				// get prediction for image
				double prediction = 0;
				double realClass = img.getClassType();
				for (Feature feat : features) {
					prediction += feat.calculateValue(img) * feat.getWeight();
				}

				if (prediction <= 0)
					prediction = 0;
				else
					prediction = 1;

				double error = prediction - realClass;

				if ((prediction <= 0 && realClass == 0) || (prediction > 0 && realClass == 1)) {
					correct++;
				} else {
					for (Feature feat : features) {
						double w = feat.getWeight() - (learningRate * feat.calculateValue(img) * error);
						feat.setWeight(w);
					}
				}
			}
			incorrect = images.size() - correct;
			correct = 0;
			numCycles++;
		}
		
	}

	public double test() {
		double incorrect = 0;
		double correct = 0;

		for (int i = 0; i < images.size(); i++) {
			// get prediction for image
			double prediction = 0;
			for (Feature feat : features) {
				prediction += feat.evaluate(images.get(i));
			}
			// check if prediction was 0 (O)
			if (prediction <= 0) {
				// check if actually was 0
				if (images.get(i).getClassType() == 0) {
					correct++;
				} else {
					incorrect++;
				}
			} else { // prediction was 1/X
						// check if actually is X
				if (images.get(i).getClassType() == 1) {
					correct++;
				} else {
					incorrect++;
				}
			}
		}
		System.out.println("------------ Output Results ---------------");

		if (numCycles == 1000) {
			double accuracy = correct / 100;
			System.out.println("Correct: " + correct + " -Incorrect: " + incorrect);
			System.out.println("Accuracy: " + accuracy);
		} else {
			System.out.println("Number of training cycles till convergence: " + numCycles);
		}
		return correct / 100;
	}

	public void printFeatures() {
		String s = "\nFeature output: \n[pixel1, pixel2, pixel3, pixel4, end weight]\n";
		System.out.println(s);
		for (Feature feat : features) {
			if (feat != features.get(0))
				feat.print();
		}
	}

}
