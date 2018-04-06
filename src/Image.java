
public class Image {

	private char classType;
	private boolean[] pixels;
	
	public Image(char classType, boolean[] pixels){
		this.classType = classType;
		this.pixels = pixels;
	}
	
	public char getClassType(){
		return classType;
	}
	
	public boolean[] getPixels(){
		return pixels;
	}
}
