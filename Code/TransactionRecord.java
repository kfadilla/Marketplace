
public class TransactionRecord {
	private long buyerId;
	private long sellerId;
	private long itemNumber;
	private int purchaseQuantity;
	private double price;
	private String date;
	private int shippingStatus;
	private long transactionId;
	//constructor
	public TransactionRecord(long buyerId, long sellerId, long itemNumber,
			int purchaseQuantity, double price, String date, int shippingStatus,
			long transactionId) {
		super();
		this.buyerId = buyerId;
		this.sellerId = sellerId;
		this.itemNumber = itemNumber;
		this.purchaseQuantity = purchaseQuantity;
		this.price = price;
		this.date = date;
		this.shippingStatus = shippingStatus;
		this.transactionId = transactionId;
	}
//get buyer id
	public long getBuyerId() {
		return buyerId;
	}
//set buyer id
	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}
//get seller id
	public long getSellerId() {
		return sellerId;
	}
//set seller id
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
//get item number
	public long getItemNumber() {
		return itemNumber;
	}
//set item number
	public void setItemNumber(long itemNumber) {
		this.itemNumber = itemNumber;
	}
//get purchase quantity
	public int getPurchaseQuantity() {
		return purchaseQuantity;
	}
//set purchase quantity
	public void setPurchaseQuantity(int purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}
//get price
	public double getPrice() {
		return price;
	}
//set price
	public void setPrice(double price) {
		this.price = price;
	}
//get date
	public String getDate() {
		return date;
	}
//set date
	public void setDate(String date) {
		this.date = date;
	}
//get ship status
	
	public int getShippingStatus() {
		return shippingStatus;
	}
//set shipping status
	public void setShippingStatus(int shippingStatus) {
		this.shippingStatus = shippingStatus;
	}
//get transaction id
	public long getTransactionId() {
		return transactionId;
	}
//set transaction id
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
//return the information
	@Override
	public String toString() {
		return "[ buyerId: " + buyerId + "; sellerId: " + sellerId +
				"; itemNumber: " + itemNumber + "; purchaseQuantity: " + purchaseQuantity +
				"; price: $" + price + "; date: " + date + "; shippingStatus: " + shippingStatus + "; transactionId: " + transactionId + " ]";
	}
	
	public String print(){
		return "[ buyerId: " + buyerId + "; sellerId: " + sellerId +
				"; itemNumber: " + itemNumber + "; purchaseQuantity: " + purchaseQuantity +
				"; price: $" + price + "; date: " + date + "; " + getShippingStatusString() + "; transactionId: " + transactionId + " ]";
	}
//get shipping status	
	public String getShippingStatusString(){
		switch (shippingStatus) {
		case Constants.SHIPPING_STATUS_NO_SHIP: //is not shipped
		case Constants.SHIPPING_STATUS_NO_SHIP_SELLER_ALREADY_KONW: 
			return Constants.SHIPPING_STATUS_NO_SHIP_TIPS;
		case Constants.SHIPPING_STATUS_SHIPPING: //shipping
		case Constants.SHIPPING_STATUS_SHIPPING_BUYER_ALREADY_KNOW:
			return Constants.SHIPPING_STATUS_SHIPPING_TIPS;
		case Constants.SHIPPING_STATUS_RECEIVED: //received
			return Constants.SHIPPING_STATUS_RECEIVED_TIPS;
		}
		return "";
	}
}
