
public class Item {
	private long itemNumber;
	private String itemName;
	private String description;
	private long sellerId;
	private int quantity;
	private double price;
	private String category; //instances
	//two constructor
	public Item() {
		itemNumber = System.currentTimeMillis();
	}
	
	public Item(long itemNumber, String itemName, String description,
			long sellerId, int quantity, double price, String category) {
		this.itemNumber = itemNumber;
		this.itemName = itemName;
		this.description = description;
		this.sellerId = sellerId;
		this.quantity = quantity;
		this.price = price;
		this.category = category;
	}
//get item number
	public long getItemNumber() {
		return itemNumber;
	}
//set item number
	public void setItemNumber(long itemNumber) {
		this.itemNumber = itemNumber;
	}
//get itemname
	public String getItemName() {
		return itemName;
	}
//set item name
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
//get description
	public String getDescription() {
		return description;
	}
//set description
	public void setDescription(String description) {
		this.description = description;
	}
//get seller id
	public long getSellerId() {
		return sellerId;
	}
//set seller id
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
//get quantity
	public int getQuantity() {
		return quantity;
	}
//set quantity
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
//get price
	public double getPrice() {
		return price;
	}
//set price 
	public void setPrice(double price) {
		this.price = price;
	}
//get category
	public String getCategory() {
		return category;
	}
//set category
	public void setCategory(String category) {
		this.category = category;
	}
//get the item information	
	@Override
	public String toString() {
		return "[ itemNumber: " + itemNumber + "; itemName: " + itemName +
				"; description: " + description + "; sellerId: " + sellerId +
				"; quantity: " + quantity + "; price: $" + price + "; category: " + category + " ]";
	}
}
