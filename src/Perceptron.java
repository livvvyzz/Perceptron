import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Perceptron {
	
	ArrayList<Image> images;

	public Perceptron(String fname){
		readFile(fname);
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
				int[] pixels = new int[w*h];
				String st = sc.next();

				char[] chars = st.toCharArray();
				int index = 0;
				for(char c : chars){
					pixels[index] = Character.getNumericValue(c);
				}
				Image img = new Image(classType, pixels);
				images.add(img);
				sc.next();
				
			}
			
			sc.close();
			System.out.println(images.size());
		} catch (IOException e) {
			throw new RuntimeException("Data File caused IO exception");
		}
	}
}
