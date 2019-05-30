import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Administrator {
	private List<User> sellers;
	private List<User> buyers;
	private List<Item> items;
	private List<TransactionRecord> transactionHistory;
	
	private SyncListener syncListener;
	
	private List<String> categoryList; //instances 
//constructor	
	public Administrator(List<User> sellers, List<User> buyers,
			List<Item> items, List<TransactionRecord> transactionHistory, List<String> categoryList) {
		this.sellers = sellers;
		this.buyers = buyers;
		this.items = items;
		this.transactionHistory = transactionHistory;
		this.categoryList = categoryList;
	}
//setting the synclistener	
	public void setSyncListener(SyncListener syncListener) {
		this.syncListener = syncListener;
	}
//print each user status
	public void viewUsers(List<User> users, int tag){
		if(users.size() > 0){ //check if users has data
			for(User user : users){
				System.out.println(user);
			}
		}else{
			System.out.println(Constants.SELLERS == tag ? Constants.NO_SELLERS_TIPS : Constants.NO_BUYERS_TIPS);
		}
	}
	
	public void viewInventory(){
		if(items.size() > 0){ //show the item list
			System.out.println(Constants.ADMIN_VIEW_INVENTORY_MENU);
			String selectString = Constants.scanner.next();
			int select = CommonUtils.getValidSelection(selectString, Constants.ADMIN_VIEW_INVENTORY_MENU_SELECTS); //call the function from CommonUtil
			while(select != 0){  //select the menu choice by input
				switch (select) {
				case 1:
					viewInventoryBySelectedSeller();
					break;
				case 2:
					viewInventoryBySelectedCategory();
					break;
				case 3:
					viewInventoryAll();
					break;
				}
				System.out.println(Constants.ADMIN_VIEW_INVENTORY_MENU);
				selectString = Constants.scanner.next(); //continue on the the input
				select = CommonUtils.getValidSelection(selectString, Constants.ADMIN_VIEW_INVENTORY_MENU_SELECTS);
			}
		}else{
			System.out.println(Constants.INVENTORY_EMPTY_TIPS);
		}
	}
	//viewing inventory according to the category
	private void viewInventoryBySelectedCategory() {
		int size = categoryList.size();
		if(size > 0){
			for(int i = 1;i <= size;i++){
				System.out.println("\t" + i + "\t" + categoryList.get(i - 1));
			 } //getting the list from categoryList
			System.out.println("\t" + (size + 1) + "\t" + Constants.DEFULT_CATEGORY); 
			System.out.println(Constants.SELECT_CATEGORY_TIPS);  
			String selectString = Constants.scanner.next(); //ask user to input a category choice
			while(!"0".equals(selectString)){ //while invalid input
				if(!CommonUtils.isInteger(selectString) || Integer.valueOf(selectString) < 0 || Integer.valueOf(selectString) > size + 1){
					System.out.println(Constants.INVALID_INPUT_TIPS);
					selectString = Constants.scanner.next(); //accept another input
				}else{
					break;
				}
			}
			if(!"0".equals(selectString)){ //print out the category information
				int select = Integer.valueOf(selectString);
				String category = select == size + 1 ? Constants.DEFULT_CATEGORY : categoryList.get(select - 1);
				boolean isFind = false;
				for(Item item : items){
					if(item.getCategory().equals(category)){
						isFind = true;
						System.out.println(item);
					}
				}
				if(!isFind){ //if the category is not found
					System.out.println(Constants.NO_FIND_TIPS);
				}
			}
		}else{
			System.out.println(Constants.NO_CATEGORY_EXCEPT_DEFULT_TIPS);
		}
	}
//view all category items
	private void viewInventoryAll() {
		for(Item item : items){
			System.out.println(item);
		}
	} 
//find the item according to the seller
	private void viewInventoryBySelectedSeller() {
		System.out.println(Constants.ENTER_USER_ID_TIPS);
		String userId = Constants.scanner.next();  //get the seller id
		if(!"0".equals(userId)){ //print the item that is associated with the seller
			boolean isFind = false;
			for(Item item : items){
				if(String.valueOf(item.getSellerId()).equals(userId)){
					isFind = true;
					System.out.println(item);
				}
			}
			if(!isFind){
				System.out.println(Constants.NO_FIND_TIPS);
			}
		}
	}
//view transaction history
	public void viewTransactionHistory(){
		if(transactionHistory.size() > 0){
			System.out.println(Constants.ADMIN_VIEW_TRANSACTION_MENU);
			String selectString = Constants.scanner.next(); //accpet input 
			int select = CommonUtils.getValidSelection(selectString, Constants.ADMIN_VIEW_TRANSACTION_MENU_SELECTS);
			while(select != 0){ //calling different function with different output
				switch (select) {
				case 1:
					viewTransactionBySelectedDay();
					break;
				case 2:
					viewTransactionWithinDays(Constants.WEEK);
					break;
				case 3:
					viewTransactionWithinDays(Constants.MONTH);
					break;
				case 4:
					viewTransactionAll();
					break;
				}
				System.out.println(Constants.ADMIN_VIEW_TRANSACTION_MENU); 
				selectString = Constants.scanner.next(); //accpet another input
				select = CommonUtils.getValidSelection(selectString, Constants.ADMIN_VIEW_TRANSACTION_MENU_SELECTS);
			}
		}else{
			System.out.println(Constants.NO_TRANSACTION_HISTORY_TIPS);
		}
	}
//view all transaction	
	private void viewTransactionAll() {
		for(TransactionRecord transactionRecord : transactionHistory){ //printing all the transaction history 
			System.out.println(transactionRecord.print()); 
		}
	}
//printing the transaction occured in the day interval
	private void viewTransactionWithinDays(int days) {
		Date currDate = new Date();
		boolean isFind = false;
		for(TransactionRecord transactionRecord : transactionHistory){
			int interval = 0;
			try {
				interval = (int)((currDate.getTime() - new SimpleDateFormat(Constants.DATE_FORMAT).parse(transactionRecord.getDate()).getTime()) 
						/ Constants.MILLI_DAY); //to get how many days the item is bought
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(interval <= days){ //printnig the record
				isFind = true;
				System.out.println(transactionRecord.print());
			}
		}
		if(!isFind){
			System.out.println(Constants.NO_FIND_TIPS);
		}
	}
//printing the transaction occured in that day
	private void viewTransactionBySelectedDay() {
		System.out.println(Constants.ENTER_DATE_TIPS);
		String date = Constants.scanner.next(); //accpeting a date variable
		while(!"0".equals(date) && !CommonUtils.isDateValid(date)){ //prompt the user to input another date if the input is invalid
			System.out.println(Constants.INVALID_INPUT_TIPS);
			date = Constants.scanner.next();
		}
		if(!"0".equals(date)){ //if the day matches, print the transaction
			boolean isFind = false;
			for(TransactionRecord transactionRecord : transactionHistory){
				if(transactionRecord.getDate().substring(0, 10).equals(date)){
					isFind = true;
					System.out.println(transactionRecord.print());
				}
			}
			if(!isFind){
				System.out.println(Constants.NO_FIND_TIPS);
			}
		}
	}
//view shipping status
	public void viewShippingStatus(){
		if(transactionHistory.size() > 0){
			System.out.println(Constants.ADMIN_VIEW_SHIPPING_STATUS_MENU);
			String selectString = Constants.scanner.next(); 
			int select = CommonUtils.getValidSelection(selectString, Constants.ADMIN_VIEW_SHIPPING_STATUS_MENU_SELECTS);
			while(select != 0){
				switch (select) { //calling the function with different input
				case 1:
					viewShippingStatusByTransactionId();
					break;
				case 2:
					viewShippingStatusAll();
					break;
				}
				System.out.println(Constants.ADMIN_VIEW_SHIPPING_STATUS_MENU);
				selectString = Constants.scanner.next();
				select = CommonUtils.getValidSelection(selectString, Constants.ADMIN_VIEW_SHIPPING_STATUS_MENU_SELECTS);
			}
		}else{ //if there is no assoicated shipping status
			System.out.println(Constants.NO_TRANSACTION_HISTORY_TIPS);
		}
	}
//view all shipping status	
	private void viewShippingStatusAll() {
		for(TransactionRecord transactionRecord : transactionHistory){ //printing the shipping status
			System.out.print("ID: " + transactionRecord.getTransactionId() + "; ");
			switch (transactionRecord.getShippingStatus()) {
			case Constants.SHIPPING_STATUS_NO_SHIP: //case 1
			case Constants.SHIPPING_STATUS_NO_SHIP_SELLER_ALREADY_KONW: //case 2
				System.out.println(Constants.SHIPPING_STATUS_NO_SHIP_TIPS); //if there is no ship
				break;
			case Constants.SHIPPING_STATUS_SHIPPING: //case 3
			case Constants.SHIPPING_STATUS_SHIPPING_BUYER_ALREADY_KNOW: //case 4
				System.out.println(Constants.SHIPPING_STATUS_SHIPPING_TIPS);
				break;
			case Constants.SHIPPING_STATUS_RECEIVED://case 5
				System.out.println(Constants.SHIPPING_STATUS_RECEIVED_TIPS); 
				break;
			}
			break;
		}
	}
//view the shipping status by the transaction id
	private void viewShippingStatusByTransactionId() {
		System.out.println(Constants.ENTER_TRANSACTION_ID_TIPS);
		String id = Constants.scanner.next();
		if(!"0".equals(id)){ //finding the id
			boolean isFind = false;
			for(TransactionRecord transactionRecord : transactionHistory){ //checking each transaction history
				if(String.valueOf(transactionRecord.getTransactionId()).equals(id)){ //find the id
					isFind = true;
					System.out.print("ID: " + id + "; ");
					switch (transactionRecord.getShippingStatus()) { //print shipping status
					case Constants.SHIPPING_STATUS_NO_SHIP:
					case Constants.SHIPPING_STATUS_NO_SHIP_SELLER_ALREADY_KONW:
						System.out.println(Constants.SHIPPING_STATUS_NO_SHIP_TIPS);
						break;
					case Constants.SHIPPING_STATUS_SHIPPING:
					case Constants.SHIPPING_STATUS_SHIPPING_BUYER_ALREADY_KNOW:
						System.out.println(Constants.SHIPPING_STATUS_SHIPPING_TIPS);
						break;
					case Constants.SHIPPING_STATUS_RECEIVED:
						System.out.println(Constants.SHIPPING_STATUS_RECEIVED_TIPS);
						break;
					}
					break;
				}
			}
			if(!isFind){
				System.out.println(Constants.NO_FIND_TIPS);
			}
		}
	}
//update user status
	public void updateUser(int tag){ 
		if((Constants.SELLERS == tag ? sellers.size() : buyers.size()) > 0){
			System.out.println(Constants.ENTER_USER_ID_TIPS);
			String userId = Constants.scanner.next();  //asking the user id
			if(!"0".equals(userId)){//finding the user 
				boolean isFind = false;
				for(User user : (Constants.SELLERS == tag ? sellers : buyers)){
					if(String.valueOf(user.getUserId()).equals(userId)){ //if user found
						isFind = true;
						System.out.println(user);
						System.out.println(Constants.ADMIN_UPDATE_USER_MENU);
						String selectString = Constants.scanner.next(); //selecting what to update
						int select = CommonUtils.getValidSelection(selectString, Constants.ADMIN_UPDATE_USER_MENU_SELECTS);
						boolean isDelete = false;
						while(select != 0){
							switch (select) { //calling different function according to the menu
							case 1:
								updateUserName(tag, user);
								break;
							case 2:
								updatePassword(tag, user);
								break;
							case 3:
								updateEmail(tag, user);
								break;
							case 4:
								deleteUser(tag, user);
								isDelete = true;
								break;
							}
							if(isDelete){
								break;
							}
							System.out.println(Constants.ADMIN_UPDATE_USER_MENU);
							selectString = Constants.scanner.next();
							select = CommonUtils.getValidSelection(selectString, Constants.ADMIN_UPDATE_USER_MENU_SELECTS);
						}
						break;
					}
				}
				if(!isFind){ //not found
					System.out.println(Constants.NO_FIND_TIPS);
				}
			}
		}else{
			System.out.println(Constants.SELLERS == tag ? Constants.NO_SELLERS_TIPS : Constants.NO_BUYERS_TIPS);
		}
	}
//removing the user	
	private void deleteUser(int tag, User user) {
		if(Constants.SELLERS == tag){
			sellers.remove(user);
		}else{
			buyers.remove(user);
		}
		syncListener.onWriteUsersToFile(tag);
		System.out.println(Constants.DELETE_SUCCESSFUL_TIPS);
	}
//updating email
	private void updateEmail(int tag, User user) {
		System.out.println(Constants.ENTER_NEW_EMAIL_TIPS);
		String newEmail = Constants.scanner.next();
		if(!"0".equals(newEmail)){
			user.setEmail(newEmail);
			syncListener.onWriteUsersToFile(tag);
			System.out.println(Constants.UPDATE_SUCCESSFUL_TIPS);
		}
	}
//updateing password
	private void updatePassword(int tag, User user) {
		if(syncListener.onSetPasswordSuccess(user, Constants.ENTER_NEW_PASSWORD_TIPS)){
			syncListener.onWriteUsersToFile(tag); //calling the function from synclistener to corporate login
			System.out.println(Constants.UPDATE_SUCCESSFUL_TIPS);
		}
	}
//update user name
	private void updateUserName(int tag, User user) {
		if(syncListener.onSetUserNameSuccess(user, Constants.ENTER_NEW_USERNAME_TIPS, tag)){
			syncListener.onWriteUsersToFile(tag); //calling the function from synclistener to corporate login
			System.out.println(Constants.UPDATE_SUCCESSFUL_TIPS);
		}
	}
//updating item information	
	public void updateItem(){
		if(items.size() > 0){
			System.out.println(Constants.ENTER_ITEM_NUMBER_TIPS); 
			String itemNumber = Constants.scanner.next(); //to get which one to find
			if(!"0".equals(itemNumber)){  
				boolean isFind = false;
				for(Item item : items){
					if(String.valueOf(item.getItemNumber()).equals(itemNumber)){ //finding the item
						isFind = true;
						System.out.println(item);
						System.out.println(Constants.UPDATE_ITEM_MENU);
						String selectString = Constants.scanner.next(); //select what to update
						int select = CommonUtils.getValidSelection(selectString, Constants.UPDATE_ITEM_MENU_SELECTS);
						boolean isDelete = false;
						while(select != 0){
							switch (select) { //calling different function according to different menu choice
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
							}
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
//delete the item
	private void deleteItem(Item item) {
		items.remove(item);
		syncListener.onWriteItemsToFile();
		System.out.println(Constants.DELETE_SUCCESSFUL_TIPS);
	}
//updating the category
	private void updateCategory(Item item) {
		int size = categoryList.size();
		if(size > 0){
			for(int i = 1;i <= size;i++){
				System.out.println("\t" + i + "\t" + categoryList.get(i - 1));
			}
			System.out.println("\t" + (size + 1) + "\t" + Constants.DEFULT_CATEGORY);
			System.out.println(Constants.SELECT_CATEGORY_TIPS); //select category
			String selectString = Constants.scanner.next();
			while(!"0".equals(selectString)){ 
				if(!CommonUtils.isInteger(selectString) || Integer.valueOf(selectString) < 0 || Integer.valueOf(selectString) > size + 1){ //if the input is invalid
					System.out.println(Constants.INVALID_INPUT_TIPS);
					selectString = Constants.scanner.next();
				}else{
					break;
				} //break he loop
			}
			if(!"0".equals(selectString)){ //if category matches
				int select = Integer.valueOf(selectString);
				item.setCategory(select == size + 1 ? Constants.DEFULT_CATEGORY : categoryList.get(select - 1));
				syncListener.onWriteItemsToFile();
				System.out.println(Constants.UPDATE_SUCCESSFUL_TIPS);
			}
		}else{
			System.out.println(Constants.NO_CATEGORY_EXCEPT_DEFULT_TIPS);
		}
	}
//update price
	private void updatePrice(Item item) {
		System.out.println(Constants.ENTER_NEW_PRICE_TIPS);
		String newPriceStr = Constants.scanner.next();
		while(!"0".equals(newPriceStr) && !CommonUtils.isDouble(newPriceStr)){ //if the input is invalid
			System.out.println(Constants.INVALID_INPUT_TIPS);
			newPriceStr = Constants.scanner.next();
		}
		if(!"0".equals(newPriceStr)){ //setting new price
			item.setQuantity(Integer.valueOf(newPriceStr));
			syncListener.onWriteItemsToFile();
			System.out.println(Constants.UPDATE_SUCCESSFUL_TIPS);
		}
	}
//updating the quantity
	private void updateQuantity(Item item) {
		System.out.println(Constants.ENTER_NEW_QUANTITY_TIPS);
		String newQuantityStr = Constants.scanner.next();
		while(!"0".equals(newQuantityStr) && !CommonUtils.isInteger(newQuantityStr)){ //if the input is invalid
			System.out.println(Constants.INVALID_INPUT_TIPS);
			newQuantityStr = Constants.scanner.next();
		}
		if(!"0".equals(newQuantityStr)){ //setting the new quantity
			item.setQuantity(Integer.valueOf(newQuantityStr));
			syncListener.onWriteItemsToFile();
			System.out.println(Constants.UPDATE_SUCCESSFUL_TIPS);
		}
	}
//updating the description
	private void updateDescription(Item item) {
		System.out.println(Constants.ENTER_NEW_DESCRIPTION_TIPS);
		String newDescription = Constants.scanner.next();
		if(!"0".equals(newDescription)){
			item.setDescription(newDescription);
			syncListener.onWriteItemsToFile();
			System.out.println(Constants.UPDATE_SUCCESSFUL_TIPS);
		}
	}
//updating item name
	private void updateItemName(Item item) {
		System.out.println(Constants.ENTER_NEW_ITEM_NAME_TIPS);
		String newItemName = Constants.scanner.next();
		if(!"0".equals(newItemName)){
			item.setItemName(newItemName);
			syncListener.onWriteItemsToFile();
			System.out.println(Constants.UPDATE_SUCCESSFUL_TIPS);
		}
	}
//updating category list	
	public void updateCategoryList(){
		System.out.println(Constants.UPDATE_CATEGORY_MENU);
		String selectString = Constants.scanner.next();
		int select = CommonUtils.getValidSelection(selectString, Constants.UPDATE_CATEGORY_MENU_SELECTS);
		while(select != 0){
			switch (select) { //calling different function with different menu option
			case 1:
				addCategory();
				break;
			case 2:
				renameCategory();
				break;
			case 3:
				deleteCategory();
				break;
			}
			System.out.println(Constants.UPDATE_CATEGORY_MENU);
			selectString = Constants.scanner.next();
			select = CommonUtils.getValidSelection(selectString, Constants.UPDATE_CATEGORY_MENU_SELECTS);
		}
	}
//deleting the category
	private void deleteCategory() {
		int size = categoryList.size();
		if(size > 0){
			for(int i = 1;i <= size;i++){
				System.out.println("\t" + i + "\t" + categoryList.get(i - 1));
			}
			System.out.println(Constants.SELECT_CATEGORY_TIPS);
			String selectString = Constants.scanner.next();
			while(!"0".equals(selectString)){
				if(!CommonUtils.isInteger(selectString) || Integer.valueOf(selectString) < 0 || Integer.valueOf(selectString) > size){ //if the input is invalid
					System.out.println(Constants.INVALID_INPUT_TIPS);
					selectString = Constants.scanner.next();
				}else{
					break;
				}
			}
			if(!"0".equals(selectString)){ //delete the category
				int select = Integer.valueOf(selectString);
				System.out.println(Constants.DELETE_ITEMS_IN_CATEGORY_TIPS);
				String s = Constants.scanner.next();
				if("y".equals(s) || "Y".equals(s)){ //confirming 
					deleteItemsInCategory(categoryList.get(select - 1));
				}else{
					updateItemsCategory(categoryList.get(select - 1), Constants.DEFULT_CATEGORY);
				}
				categoryList.remove(select - 1);
				FileUtils.writeFile(Constants.CATEGORY_FILE_NAME, categoryList, false);
				syncListener.onWriteItemsToFile();
				System.out.println(Constants.DELETE_SUCCESSFUL_TIPS);
			}
		}else{
			System.out.println(Constants.NO_CATEGORY_EXCEPT_DEFULT_TIPS);
		}
	}
//deleting the items in the category
	private void deleteItemsInCategory(String categoryName) {
		for(Item item : items){ //remove each item
			if(item.getCategory().equals(categoryName)){
				items.remove(item);
			}
		}
	}
//rename the category
	private void renameCategory() {
		int size = categoryList.size();
		if(size > 0){
			for(int i = 1;i <= size;i++){
				System.out.println("\t" + i + "\t" + categoryList.get(i - 1));
			}
			System.out.println(Constants.SELECT_CATEGORY_TIPS);
			String selectString = Constants.scanner.next();
			while(!"0".equals(selectString)){
				if(!CommonUtils.isInteger(selectString) || Integer.valueOf(selectString) < 0 || Integer.valueOf(selectString) > size){ //if the input is invalid
					System.out.println(Constants.INVALID_INPUT_TIPS);
					selectString = Constants.scanner.next();
				}else{
					break;
				}
			}
			if(!"0".equals(selectString)){
				System.out.println(Constants.ENTER_NEW_CATEGORY_NAME_TIPS);
				String categoryName = Constants.scanner.next();
				while(!"0".equals(categoryName) && isCategoryAlreadyExist(categoryName)){ //if entered a name that is already existed
					System.out.println(Constants.CATEGORY_ALREADY_EXIST_TIPS);
					categoryName = Constants.scanner.next();
				}
				if(!"0".equals(categoryName)){ //update the category name
					int select = Integer.valueOf(selectString);
					updateItemsCategory(categoryList.get(select - 1), categoryName);
					categoryList.set(select - 1, categoryName);
					FileUtils.writeFile(Constants.CATEGORY_FILE_NAME, categoryList, false);
					syncListener.onWriteItemsToFile();
					System.out.println(Constants.UPDATE_SUCCESSFUL_TIPS);
				}
			}
		}else{
			System.out.println(Constants.NO_CATEGORY_EXCEPT_DEFULT_TIPS);
		}
	}
//update items Category
	private void updateItemsCategory(String categoryName, String newCategoryName) {
		for(Item item : items){
			if(item.getCategory().equals(categoryName)){
				item.setCategory(newCategoryName);
			}
		}
	}
//add a category
	private void addCategory() {
		System.out.println(Constants.ENTER_CATEGORY_NAME_TIPS);
		String categoryName = Constants.scanner.next();
		while(!"0".equals(categoryName) && isCategoryAlreadyExist(categoryName)){ //if ths category already exist
			System.out.println(Constants.CATEGORY_ALREADY_EXIST_TIPS);
			categoryName = Constants.scanner.next();
		}
		if(!"0".equals(categoryName)){ //add the category
			categoryList.add(categoryName);
			List<String> contents = new ArrayList<String>();
			contents.add(categoryName);
			FileUtils.writeFile(Constants.CATEGORY_FILE_NAME, contents, true);
			System.out.println(Constants.UPDATE_SUCCESSFUL_TIPS);
		}
	}
//check wether the category already existed
	private boolean isCategoryAlreadyExist(final String categoryName) {
		if(Constants.DEFULT_CATEGORY.equals(categoryName)){
			return true;
		}
		for(String string : categoryList){
			if(categoryName.equals(string)){
				return true;
			}
		}
		return false;
	}
}
