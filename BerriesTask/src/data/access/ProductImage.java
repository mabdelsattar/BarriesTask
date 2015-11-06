package data.access;

public class ProductImage {
	
	public String ImageUrl;
	public int width; 
	public int height;
	
	public ProductImage(String ImageUrl,int width, int height ) {
		this.width=width;
		this.height= height;
		this.ImageUrl=ImageUrl;
	}

}
