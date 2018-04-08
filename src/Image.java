
public class Image {

	private char classType;
	private boolean[] pixels;

	public Image(char classType, boolean[] pixels) {
		this.classType = classType;
		this.pixels = pixels;
	}

	public int getClassType() {
		if (classType == 'X')
			return 1;
		else return 0;
	}

	public boolean[] getPixels() {
		return pixels;
	}
	

}
