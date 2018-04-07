import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Perceptron {

	ArrayList<Image> images;
	ArrayList<Feature> features;

	public double learningRate = 0.25;
	
	public double seed = 0.29;
	public int rSeed = 30;

	public Perceptron(String fname) {
		Random r = new Random();
		learningRate = r.nextDouble();
		seed = r.nextDouble();
		rSeed = r.nextInt(50);
		readFile(fname);
		// initialise features
		initFeatures(); 
		run();
		test();

	}

	public void readFile(String fname) {
		images = new ArrayList<Image>();
		/*
		 * format of names file: names of categories, separated by spaces names
		 * of attributes category followed by true's and false's for each
		 * instance
		 */
		System.out.println("Reading data from file " + fname);
		try {
			Scanner sc = new Scanner(new File(fname));
			while (sc.hasNext()) {
				sc.next(); // p1
				char classType = sc.next().toCharArray()[1]; // class type
				// test
				String a = sc.next();
				String b = sc.next();

				int w = Integer.parseInt(a);
				int h = Integer.parseInt(b);
				boolean[] pixels = new boolean[w * h];
				String st = sc.next();
				String s2 = sc.next();

				String s3 = st+s2;
				char[] chars = s3.toCharArray();
				int index = 0;
				int numOn = 0;
				int numOff = 0;
				//System.out.println("newline");
				for (char c : chars) {
					boolean sgn = true;;
					if (c == '0') {
						numOff++;
						sgn = false;
					} else if (c == '1'){
						numOn++;
						sgn = true;
					}
					pixels[index] = sgn;
					index++;
				}
				Image img = new Image(classType, pixels);
				images.add(img);
				//sc.next();

			}
			// System.out.println(images.size());
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
			Random r = new Random(rSeed+i);
			for (int j = 0; j < 4; j++) {
				pixelNums[j] = r.nextInt(50);
				guess[j] = r.nextBoolean();
			}

			Feature f = new Feature(pixelNums, guess);
			f.setWeight(r.nextDouble()*seed);
			features.add(f);
		}
	}

	public void run() {

		int incorrect = images.size();
		int correct = 0;
		int numCycles = 0;
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
				// System.out.println(prediction + "gggffff");
				double error = prediction - realClass;
				// System.out.println(error);

				if ((prediction <= 0 && realClass == 0) || (prediction > 0 && realClass == 1)) {
					correct++;
				} else {
					for (Feature feat : features) {
						double w = feat.getWeight() - (learningRate * feat.calculateValue(img) *error);
						feat.setWeight(w);

					}
				}
			}
			incorrect = images.size() - correct;
			correct = 0;
			numCycles++;
		}
		System.out.println(numCycles);
		
	}

	public double test() {
		int incorrect = 0;
		int correct = 0;

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

		System.out.println("Correct: " + correct + "      -----Incorrect: " + incorrect);
		return correct/100;
	}

	public void testImages() {

		for (Image img : images) {
			int on = 0;
			int off = 0;
			for (boolean b : img.getPixels()) {
				if (b)
					on++;
				else
					off++;
			}
			System.out.println(on + " " + off );
			img.testPixels();
		}
	}
}
