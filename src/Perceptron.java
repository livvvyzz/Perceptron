import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Perceptron {

	ArrayList<Image> images;
	ArrayList<Feature> features;
	public Perceptron(String fname){
		readFile(fname);
		//initialise features
		initFeatures(images.get(0)); /////////////////////////////////////////////////////////////////temporary
	}

	public void readFile(String fname){
		images = new ArrayList<Image>();
		/*
		 * format of names file: names of categories, separated by spaces names
		 * of attributes category followed by true's and false's for each
		 * instance
		 */
		System.out.println("Reading data from file " + fname);
		try {
			Scanner sc = new Scanner(new File(fname));
			while(sc.hasNext()){
				sc.next(); //p1
				char classType = sc.next().toCharArray()[1]; //class type
				//test
				String a = sc.next();
				String b = sc.next();

				int w = Integer.parseInt(a);
				int h = Integer.parseInt(b);
				boolean[] pixels = new boolean[w*h];
				String st = sc.next();

				char[] chars = st.toCharArray();
				int index = 0;
				for(char c : chars){
					boolean sgn;
					if(Character.getNumericValue(c) == 0) sgn = false;
					else sgn = true;
					pixels[index] = sgn;
				}
				Image img = new Image(classType, pixels);
				images.add(img);
				sc.next();

			}

			sc.close();
		} catch (IOException e) {
			throw new RuntimeException("Data File caused IO exception");
		}
	}

	public void initFeatures(Image img) {
		//dummy feature
		features = new ArrayList<Feature>();
		features.add(new Feature(1));

		//49 random features
		for(int i = 0; i < 49; i++) {
			 int [] pixelNums = new int[4];
			 boolean[] guess = new boolean[4];
			 for(int j = 0; j < 4; j++) {
				 Random r = new Random();
				 pixelNums[j] = r.nextInt(50);
				 guess[j] = r.nextBoolean();
			 }
			 features.add(new Feature(pixelNums, guess, img.getPixels()));
		}
	}
}
