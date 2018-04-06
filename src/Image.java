
public class Image {

	private char classType;
	private int[] pixels;
	
	public Image(char classType, int[] pixels){
		this.classType = classType;
		this.pixels = pixels;
	}
	
	public char getClassType(){
		return classType;
	}
	
	public int[] getPixels(){
		return pixels;
	}
}
