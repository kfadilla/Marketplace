import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Buyer extends User {
	private List<TransactionRecord> purchaseHistory;
	//three constructor
	public Buyer() {
		super(Constants.BUYERS);
	}
	
	public Buyer(long buyerId, String name, String password, String email) {
		this(buyerId, name, password, email, new ArrayList<TransactionRecord>());
	}
	
	public Buyer(long buyerId, String name, String password, String email,
			List<TransactionRecord> purchaseHistory) {
		super(buyerId, name, password, email, Constants.BUYERS);
		this.purchaseHistory = purchaseHistory;
	}
//get purchase history
	public List<TransactionRecord> getPurchaseHistory() {
		return purchaseHistory;
	}
//set the purchase history
	public void setPurchaseHistory(List<TransactionRecord> purchaseHistory) {
		this.purchaseHistory = purchaseHistory;
	}
//view the inventory	
	public void viewInventory(){
		getSyncListener().onViewInventory();
	}
//search the item	
	public void searchItem(){
		System.out.println(Constants.SEARCH_ITEMS_MENU);
		String selectString = Constants.scanner.next();
		int select = CommonUtils.getValidSelection(selectString, Constants.SEARCH_ITEMS_MENU_SELECTS);
		while(select != 0){ //calling function by different option
			switch (select) {
			case 1:
				searchItemByItemNumber();
				break;
			case 2:
				searchItemByItemName();
				break;
			}
			System.out.println(Constants.SEARCH_ITEMS_MENU);
			selectString = Constants.scanner.next();
			select = CommonUtils.getValidSelection(selectString, Constants.SEARCH_ITEMS_MENU_SELECTS);
		}
	}
//searching the item by item name	
	private void searchItemByItemName() {
		System.out.println(Constants.ENTER_ITEM_NAME_TIPS);
		String itemNameString = Constants.scanner.next();
		if(!"0".equals(itemNameString)){
			List<Item> findItems = getSyncListener().onSearchItemByItemName(itemNameString);
			if(findItems.size() > 0){ //printing each item if found
				for(Item item : findItems){
					System.out.println(item);
				}
			}else{
				System.out.println(Constants.NO_FIND_TIPS);
			}
		}
	}
//search item by id
	private Item searchItemByItemNumber() {
		Item item = null;
		System.out.println(Constants.ENTER_ITEM_NUMBER_TIPS);
		String itemNumberString = Constants.scanner.next();
		if(!"0".equals(itemNumberString)){ //if the ID matches
			item = getSyncListener().onSearchItemByItemNumber(itemNumberString);
			if(item != null){ //print the item
				System.out.println(item);
			}else{
				System.out.println(Constants.NO_FIND_TIPS);
			}
		}
		return item;
	}
//purchasing the item
	public void purchaseItem(){
		Item item = searchItemByItemNumber();
		if(item != null){
			System.out.println(Constants.ENTER_PURCHASE_QUANTITY_TIPS);
			String purchaseQuantityString = Constants.scanner.next();
			while(!"0".equals(purchaseQuantityString) && !CommonUtils.isInteger(purchaseQuantityString)){ //if the input is invalid
				System.out.print(Constants.INVALID_INPUT_TIPS);
				purchaseQuantityString = Constants.scanner.next();
			}
			if(!"0".equals(purchaseQuantityString)){ //if the item is out of stock
				int purchaseQuantity = Integer.valueOf(purchaseQuantityString);
				if(item.getQuantity() < purchaseQuantity){
					System.out.println(Constants.OUT_OF_STOCK_TIPS);
				}else{ //buying the item
					TransactionRecord transactionRecord = new TransactionRecord(getUserId(), item.getSellerId(), item.getItemNumber(),
							purchaseQuantity, item.getPrice(), new SimpleDateFormat(Constants.DATE_FORMAT).format(new Date()),
							Constants.SHIPPING_STATUS_NO_SHIP, System.currentTimeMillis()); //setting a new transaction record
					purchaseHistory.add(transactionRecord);
					getSyncListener().onTransaction(item.getItemNumber(), purchaseQuantity, transactionRecord);
					getSyncListener().onWriteItemsToFile();
					System.out.println(Constants.TRANSACTION_SUCCESSFUL_TIPS);
				}
			}
		}
	}
//update information while receive the item	
	public void receiveItems(){
		if(purchaseHistory.size() > 0){
			List<TransactionRecord> shippingItems = new ArrayList<TransactionRecord>();
			for(TransactionRecord transactionRecord : purchaseHistory){ 
				if(transactionRecord.getShippingStatus() == Constants.SHIPPING_STATUS_SHIPPING_BUYER_ALREADY_KNOW){ //update the transaction record
					shippingItems.add(transactionRecord);
				}
			}
			int size = shippingItems.size();
			if(size > 0){
				for(int i = 1;i <= size;i++){
					System.out.println(i + "\t" + shippingItems.get(i - 1));
				}
				System.out.println((size + 1) + "\tReceive all.");
				System.out.println(Constants.SELECT_ITEM_TIPS); 
				String selectString = Constants.scanner.next();//selecting item
				while(!"0".equals(selectString)){
					if(!CommonUtils.isInteger(selectString) || Integer.valueOf(selectString) < 0 || Integer.valueOf(selectString) > size + 1){
						System.out.println(Constants.INVALID_INPUT_TIPS);
						selectString = Constants.scanner.next();
					}else{
						break;
					}
				}
				if(!"0".equals(selectString)){ 
					int select = Integer.valueOf(selectString);
					if(size + 1 == select){
						for(TransactionRecord transactionRecord : shippingItems){ //update shipping status
							transactionRecord.setShippingStatus(Constants.SHIPPING_STATUS_RECEIVED);
						}
					}else{
						shippingItems.get(select - 1).setShippingStatus(Constants.SHIPPING_STATUS_RECEIVED);
					}
					getSyncListener().onWriteTransactionToFile();
					System.out.println(Constants.RECEIVE_SUCCESSFUL_TIPS);
				}
			}else{
				System.out.println(Constants.ALL_RECEIVED_TIPS);
			}
		}else{
			System.out.println(Constants.NO_PURCHASE_HISTORY_TIPS);
		}
	}
}
