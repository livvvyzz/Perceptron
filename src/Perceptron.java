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
			while(sc.hasNextLine()){
				sc.nextLine(); //p1
				char classType = sc.next().toCharArray()[1]; //class type
				int w = sc.nextInt();
				int h = sc.nextInt();
				int[] pixels = new int[w*h];
				char[] chars = sc.next().toCharArray();
				int index = 0;
				for(char c : chars){
					pixels[index] = Character.getNumericValue(c);
				}
				Image img = new Image(classType, pixels);
				images.add(img);
				
			}
			
			sc.close();
		} catch (IOException e) {
			throw new RuntimeException("Data File caused IO exception");
		}
	}
}
