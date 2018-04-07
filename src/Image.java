
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
	
	public void testPixels(){
		int on = 0;
		int off = 0;
		for(boolean b : pixels){
			if(b) on++;
			else off++;
		}
		System.out.println(on + "  " + off + " test pixels in mages");
	}
}
