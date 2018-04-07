import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Perceptron {

	ArrayList<Image> images;
	ArrayList<Feature> features;

	public double learningRate;
	public double INIT_LR = 0.25;

	public Perceptron(String fname) {
		learningRate = INIT_LR;
		readFile(fname);
		// initialise features
		initFeatures(); ///////////////////////////////////////////////////////////////// temporary
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

				char[] chars = st.toCharArray();
				int index = 0;
				for (char c : chars) {
					boolean sgn;
					if (Character.getNumericValue(c) == 0) {
						sgn = false;
					} else
						sgn = true;
					pixels[index] = sgn;
				}
				Image img = new Image(classType, pixels);
				images.add(img);
				sc.next();

			}
			//System.out.println(images.size());
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
			Random r = new Random(233 + i);
			for (int j = 0; j < 4; j++) {
				pixelNums[j] = r.nextInt(50);
				guess[j] = r.nextBoolean();
			}
			// if(i == 23) System.out.println(pixelNums[0] + " " +pixelNums[1] +
			// " " +pixelNums[2] + " " +pixelNums[3] + " ");
			Feature f = new Feature(pixelNums, guess);
			f.setWeight(r.nextDouble()*0.5);
			features.add(f);
		}
	}

	public void run() {

		int incorrect = images.size();
		int correct = 0;
		
		for(Feature f : features){
			System.out.println(f.getWeight());
		}

		for (int i = 0; i < 10 && incorrect != 0; i++) {
			int sum = 0;
			// iterate through each image
			for (Image img : images) {

				// get prediction for image
				double prediction = 0;
				double realClass = img.getClassType();
				for (Feature feat : features) {
					if(i == 0){
						//System.out.println(feat.getWeight() + "     and then value: " + feat.calculateValue(images.get(i)));
					}
					prediction += feat.calculateValue(img) * feat.getWeight();
				}
				//System.out.println(prediction + "gggffff");
				double error = prediction - realClass;
				//System.out.println(error);

				if ((prediction <= 0 && realClass == 0) || (prediction > 0 && realClass == 1)) {
					correct++;
				} else {
					for (Feature feat : features) {
						double w = feat.getWeight() + (learningRate * feat.calculateValue(img));
						feat.setWeight(w);
					}
				}
				/**
				 * // check if prediction was 0 (O) if (prediction <= 0) { //
				 * check if actually was 0 if (img.getClassType() == 0) {
				 * correct++; } else { // adjust weights for (Feature feat :
				 * features) { double w = feat.getWeight() + (learningRate *
				 * feat.calculateValue(img)); feat.setWeight(w); } } } else { //
				 * prediction was 1/X // check if actually is X if
				 * (img.getClassType() == 1) { correct++; } else { // adjust
				 * weights for (Feature feat : features) { sum++; double w =
				 * feat.getWeight() - (learningRate * feat.calculateValue(img));
				 * feat.setWeight(w); } }
				 */
			}
		}
		incorrect = images.size() - correct;
		correct = 0;

	}

	public void test() {
		int incorrect = 0;
		int correct = 0;

		for (int i = 0; i < images.size(); i++) {
			// get prediction for image
			double prediction = 0;
			for (Feature feat : features) {
				prediction += feat.evaluate(images.get(i));
			}
			System.out.println(prediction);
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
				System.out.println("g");
				if (images.get(i).getClassType() == 1) {
					correct++;
				} else {
					incorrect++;
				}
			}
		}

		System.out.println("Correct: " + correct + "      -----Incorrect: " + incorrect);
	}
}
