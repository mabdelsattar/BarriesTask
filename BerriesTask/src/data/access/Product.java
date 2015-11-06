package data.access;

public class Product {
	
	public int ID;
	public String Description;
	public double Price;
	public ProductImage Image;
	
	
	public Product(int ID,String Description, double Price,ProductImage Image) {
		this.ID = ID;
		this.Description=Description;
		this.Price=Price;
		this.Image=Image;
	}


	public int getID() {
		return ID;
	}


	

	public String getDescription() {
		return Description;
	}


	


	public double getPrice() {
		return Price;
	}


	


	public ProductImage getImage() {
		return Image;
	}


	
	
	
	
	
	
	

}
