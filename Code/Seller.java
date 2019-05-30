import java.util.ArrayList;
import java.util.List;

public class Seller extends User {
	private List<Item> inventory;
	private List<TransactionRecord> salesHistory;
//four constructor	
	public Seller() {
		super(Constants.SELLERS);
	}
	
	public Seller(long sellerId, String name, String password, String email) {
		this(sellerId, name, password, email, new ArrayList<Item>());
	}
	
	public Seller(long sellerId, String name, String password, String email,
			List<Item> inventory) {
		this(sellerId, name, password, email, inventory, new ArrayList<TransactionRecord>());
	}

	public Seller(long sellerId, String name, String password, String email,
			List<Item> inventory, List<TransactionRecord> salesHistory) {
		super(sellerId, name, password, email, Constants.SELLERS);
		this.inventory = inventory;
		this.salesHistory = salesHistory;
	}
//get inventory	
	public List<Item> getInventory() {
		return inventory;
	}
//set inventory list
	public void setInventory(List<Item> inventory) {
		this.inventory = inventory;
	}

//get sales history
	public List<TransactionRecord> getSalesHistory() {
		return salesHistory;
	}
//set saleshistory list
	public void setSalesHistory(List<TransactionRecord> salesHistory) {
		this.salesHistory = salesHistory;
	}
//view inventory	
	public void viewInventory(){
		if(inventory.size() > 0){ //printing each item
			for(Item item : inventory){
				System.out.println(item);
			}
		}else{
			System.out.println(Constants.INVENTORY_EMPTY_TIPS);
		}
	}
//upload the item	
	public void uploadItems(){
		System.out.println(Constants.SELLER_UPLOAD_ITEMS_MENU);
		String selectString = Constants.scanner.next();
		int select = CommonUtils.getValidSelection(selectString, Constants.SELLER_UPLOAD_ITEMS_MENU_SELECTS); //get valid selection
		while(select != 0){
			switch (select) { //calling different function with different menu option
			case 1:
				uploadItemsByEntered();
				break;
			case 2:
				uploadItemsByFile();
				break;
			}
			System.out.println(Constants.SELLER_UPLOAD_ITEMS_MENU);
			selectString = Constants.scanner.next();
			select = CommonUtils.getValidSelection(selectString, Constants.SELLER_UPLOAD_ITEMS_MENU_SELECTS);
		}
	}
//upload items by file	
	private void uploadItemsByFile() {
		List<String> uploadItemsStrings = FileUtils.readFile(Constants.UPLOAD_ITEMS_FILE_NAME); //reading the file, and put string the list
		if(uploadItemsStrings != null && uploadItemsStrings.size() > 0){
			for(String itemStr : uploadItemsStrings){ //generating each item with the item information in the list
				if(!itemStr.trim().isEmpty()){
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Item item = generateItemObject(itemStr);
					if(item.getSellerId() == getUserId()){ //add to inventory if it is on both seller and buyer
						inventory.add(item);
						getSyncListener().onUploadItem(item);
					}
				}
			}
			System.out.println(Constants.UPLOAD_SUCCESSFUL_TIPS);
		}else{
			System.out.println(Constants.FILE_EMPTY_TIPS);
		}
	}
//generate item 
	private Item generateItemObject(String str) {
//generate item information needed
		String itemName = str.substring(str.indexOf("[ itemName: ") + "[ itemName: ".length(), str.indexOf("; description: "));
		String description = str.substring(str.indexOf("; description: ") + "; description: ".length(), str.indexOf("; sellerId: "));
		long sellerId = Long.valueOf(str.substring(str.indexOf("; sellerId: ") + "; sellerId: ".length(), str.indexOf("; quantity: ")));
		int quantity = Integer.valueOf(str.substring(str.indexOf("; quantity: ") + "; quantity: ".length(), str.indexOf("; price: $")));
		double price = Double.valueOf(str.substring(str.indexOf("; price: $") + "; price: $".length(), str.indexOf("; category: ")));
		String category = str.substring(str.indexOf("; category: ") + "; category: ".length(), str.indexOf(" ]"));
		return new Item(System.currentTimeMillis(), itemName, description, sellerId, quantity, price, category);
	}
//helper function to upload item
	private void uploadItemsByEntered() {
		Item item = new Item();
		if(setItemNameSuccess(item) && setDescriptionSuccess(item) && setQuantitySuccess(item)
				&& setPriceSuccess(item)){ //upload item if these conditions are true
			setCategorySuccess(item);
			item.setSellerId(getUserId());
			inventory.add(item);
			getSyncListener().onUploadItem(item);
			System.out.println(Constants.UPLOAD_SUCCESSFUL_TIPS);
		}
	}
//helper function for uploading item
//get which category does the item belong
	private boolean setCategorySuccess(Item item) {
		List<String> categoryList = getSyncListener().onGetCategoryList();
		String defultCategory = null == item.getCategory() ? Constants.DEFULT_CATEGORY : item.getCategory();
		item.setCategory(defultCategory);
		int size = categoryList.size();
		if(size > 0){
			for(int i = 1;i <= size;i++){
				System.out.println("\t" + i + "\t" + categoryList.get(i - 1));
			}
			System.out.println("\t" + (size + 1) + "\t" + Constants.DEFULT_CATEGORY);
			System.out.println(Constants.SELECT_CATEGORY_TIPS);
			String selectString = Constants.scanner.next();  //prompt the user to input a category
			while(!"0".equals(selectString)){
				if(!CommonUtils.isInteger(selectString) || Integer.valueOf(selectString) < 0 || Integer.valueOf(selectString) > size + 1){
					System.out.println(Constants.INVALID_INPUT_TIPS);
					selectString = Constants.scanner.next();
				}else{
					break;
				}
			}
			if(!"0".equals(selectString)){ //setting the category
				int select = Integer.valueOf(selectString);
				String categoryName = select == size + 1 ? Constants.DEFULT_CATEGORY : categoryList.get(select - 1);
				item.setCategory(categoryName);
				return true;
			}
		}
		return false;
	}
//helper function for uploading item
//set up the price
	private boolean setPriceSuccess(Item item) {
		System.out.println(Constants.ENTER_PRICE_TIPS);
		String priceEntered = Constants.scanner.next(); //get the price input
		while(!"0".equals(priceEntered) && !CommonUtils.isDouble(priceEntered)){ //invalid input
			System.out.println(Constants.INVALID_INPUT_TIPS);
			priceEntered = Constants.scanner.next();
		}
		if(!"0".equals(priceEntered)){ //set price
			item.setPrice(Double.valueOf(priceEntered));
			return true;
		}
		return false;
	}
//helper function for uploading item
//set quantity
	private boolean setQuantitySuccess(Item item) { 
		System.out.println(Constants.ENTER_QUANTITY_TIPS);
		String quantityEntered = Constants.scanner.next();
		while(!"0".equals(quantityEntered) && !CommonUtils.isInteger(quantityEntered)){ //invalid input
			System.out.println(Constants.INVALID_INPUT_TIPS);
			quantityEntered = Constants.scanner.next();
		}
		if(!"0".equals(quantityEntered)){ //set price
			item.setQuantity(Integer.valueOf(quantityEntered));
			return true;
		}
		return false;
	}
//helper function for uploading item
//set description
	private boolean setDescriptionSuccess(Item item) {
		System.out.println(Constants.ENTER_DESCRIPTION_TIPS);
		String descriptionEntered = Constants.scanner.next(); //set description
		if(!"0".equals(descriptionEntered)){
			item.setDescription(descriptionEntered);
			return true;
		}
		return false;
	}
//helper function for uploading item
//set item name
	private boolean setItemNameSuccess(Item item) {
		System.out.println(Constants.ENTER_ITEM_NAME_TIPS);
		String itemNameEntered = Constants.scanner.next();
		if(!"0".equals(itemNameEntered)){ //set item name
			item.setItemName(itemNameEntered);
			return true;
		}
		return false;
	}
//update menu
	public void updateItem(){
		if(inventory.size() > 0){
			System.out.println(Constants.ENTER_ITEM_NUMBER_TIPS);
			String itemNumber = Constants.scanner.next();
			if(!"0".equals(itemNumber)){
				boolean isFind = false;
				for(Item item : inventory){
					if(String.valueOf(item.getItemNumber()).equals(itemNumber)){ //set up for each item
						isFind = true;
						System.out.println(item);
						System.out.println(Constants.UPDATE_ITEM_MENU);
						String selectString = Constants.scanner.next();
						int select = CommonUtils.getValidSelection(selectString, Constants.UPDATE_ITEM_MENU_SELECTS);
						boolean isDelete = false;
						while(select != 0){
							switch (select) { //callining different function according to the menu option
							case 1:
								updateItemName(item);
								break;
							case 2:
								updateDescription(item);
								break;
							case 3:
								updateQuantity(item);
								break;
							case 4:
								updatePrice(item);
								break;
							case 5:
								updateCategory(item);
								break;
							case 6:
								deleteItem(item);
								isDelete = true;
								break;
							}
							if(isDelete){
								break;
							} //break the loop
							System.out.println(Constants.UPDATE_ITEM_MENU);
							selectString = Constants.scanner.next();
							select = CommonUtils.getValidSelection(selectString, Constants.UPDATE_ITEM_MENU_SELECTS);
						}
						break;
					}
				}
				if(!isFind){
					System.out.println(Constants.NO_FIND_TIPS);
				}
			}
		}else{
			System.out.println(Constants.INVENTORY_EMPTY_TIPS);
		}
	}
//delete item	
	private void deleteItem(Item item) {
		inventory.remove(item);
		getSyncListener().onDeleteItem(item);
		getSyncListener().onWriteItemsToFile();
		System.out.println(Constants.DELETE_SUCCESSFUL_TIPS);
	}
//update the category
	private void updateCategory(Item item) {
		if(setCategorySuccess(item)){ //check whether it is successful to update and set category
			getSyncListener().onWriteItemsToFile();
			System.out.println(Constants.UPDATE_SUCCESSFUL_TIPS);
		}
	}
//update price
	private void updatePrice(Item item) {
		System.out.println(Constants.ENTER_NEW_PRICE_TIPS);
		String newPriceStr = Constants.scanner.next();
		while(!"0".equals(newPriceStr) && !CommonUtils.isDouble(newPriceStr)){ //invalid input
			System.out.println(Constants.INVALID_INPUT_TIPS);
			newPriceStr = Constants.scanner.next();
		}
		if(!"0".equals(newPriceStr)){ //set price if valid
			item.setQuantity(Integer.valueOf(newPriceStr));
			getSyncListener().onWriteItemsToFile();
			System.out.println(Constants.UPDATE_SUCCESSFUL_TIPS);
		}
	}
//update quantity
	private void updateQuantity(Item item) {
		System.out.println(Constants.ENTER_NEW_QUANTITY_TIPS);
		String newQuantityStr = Constants.scanner.next();
		while(!"0".equals(newQuantityStr) && !CommonUtils.isInteger(newQuantityStr)){ //invalid input
			System.out.println(Constants.INVALID_INPUT_TIPS);
			newQuantityStr = Constants.scanner.next();
		}
		if(!"0".equals(newQuantityStr)){ //set price if valid
			item.setQuantity(Integer.valueOf(newQuantityStr));
			getSyncListener().onWriteItemsToFile();
			System.out.println(Constants.UPDATE_SUCCESSFUL_TIPS);
		}
	}
//update description
	private void updateDescription(Item item) {
		System.out.println(Constants.ENTER_NEW_DESCRIPTION_TIPS);
		String newDescription = Constants.scanner.next();
		if(!"0".equals(newDescription)){ //set description with the input
			item.setDescription(newDescription);
			getSyncListener().onWriteItemsToFile();
			System.out.println(Constants.UPDATE_SUCCESSFUL_TIPS);
		}
	}
//update item name
	private void updateItemName(Item item) {
		System.out.println(Constants.ENTER_NEW_ITEM_NAME_TIPS);
		String newItemName = Constants.scanner.next();
		if(!"0".equals(newItemName)){ //set item name with the input
			item.setItemName(newItemName);
			getSyncListener().onWriteItemsToFile();
			System.out.println(Constants.UPDATE_SUCCESSFUL_TIPS);
		}
	}
//shipping status	
	public void shipItems(){
		if(salesHistory.size() > 0){
			List<TransactionRecord> noShippingItems = new ArrayList<TransactionRecord>();
			for(TransactionRecord transactionRecord : salesHistory){
				if(transactionRecord.getShippingStatus() == Constants.SHIPPING_STATUS_NO_SHIP_SELLER_ALREADY_KONW){
					noShippingItems.add(transactionRecord); //add the transaction to list of the item is not shipped
				}
			}
			int size = noShippingItems.size();
			if(size > 0){ //get every item that is not shipped in the list
				for(int i = 1;i <= size;i++){
					System.out.println(i + "\t" + noShippingItems.get(i - 1));
				}
				System.out.println((size + 1) + "\tShip all.");
				System.out.println(Constants.SELECT_ITEM_TIPS);
				String selectString = Constants.scanner.next(); //ask user which one to ship 
				while(!"0".equals(selectString)){
					if(!CommonUtils.isInteger(selectString) || Integer.valueOf(selectString) < 0 || Integer.valueOf(selectString) > size + 1){ //invalid input
						System.out.println(Constants.INVALID_INPUT_TIPS);
						selectString = Constants.scanner.next();
					}else{
						break;
					}
				}
				if(!"0".equals(selectString)){  //ship the item and update status
					int select = Integer.valueOf(selectString);
					if(size + 1 == select){
						for(TransactionRecord transactionRecord : noShippingItems){
							transactionRecord.setShippingStatus(Constants.SHIPPING_STATUS_SHIPPING);
						}
					}else{ 
						noShippingItems.get(select - 1).setShippingStatus(Constants.SHIPPING_STATUS_SHIPPING);
					}
					getSyncListener().onWriteTransactionToFile();
					System.out.println(Constants.SHIP_SUCCESSFUL_TIPS);
				}
			}else{ //all shipped
				System.out.println(Constants.ALL_SHIPPED_TIPS);
			}
		}else{ //no sales history
			System.out.println(Constants.NO_SALES_HISTORY_TIPS);
		}
	}
}
