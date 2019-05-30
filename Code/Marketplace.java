import java.util.ArrayList;
import java.util.List;


public class Marketplace implements SyncListener {
	private Administrator administrator;
	private User currentUser;
	
	private List<User> sellers;
	private List<User> buyers;
	private List<Item> items;
	private List<TransactionRecord> transactionHistory;
	
	private List<String> categoryList; //instances
//constructor
	public Marketplace() {
		init();
		loadData();
	}
//start the program	
	public void start() {
		System.out.println(Constants.WELCOME_TIPS);
		System.out.println(Constants.MAIN_MENU);
		String selectString = Constants.scanner.next();
		int select = CommonUtils.getValidSelection(selectString, Constants.MAIN_MENU_SELECTS);
		while(select != 0){
			switch (select) { //calling different function according to the menu option
			case 1:
				administratorLogin();
				break;
			case 2:
				sellerLogin();
				break;
			case 3:
				buyerLogin();
				break;
			case 4:
				registerUser(Constants.SELLERS);
				break;
			case 5:
				registerUser(Constants.BUYERS);
				break;
			}
			System.out.println(Constants.MAIN_MENU);
			selectString = Constants.scanner.next();
			select = CommonUtils.getValidSelection(selectString, Constants.MAIN_MENU_SELECTS);
		}
		System.out.println(Constants.END_TIPS);
		Constants.scanner.close();
	}
//initializing 	
	private void init() {
		sellers = new ArrayList<User>();
		buyers = new ArrayList<User>();
		items = new ArrayList<Item>();
		transactionHistory = new ArrayList<TransactionRecord>();
		categoryList = new ArrayList<String>();
	}
//load the data
	private void loadData() {
		loadData(Constants.ITEMS_FILE_NAME, Constants.ITEM);
		loadData(Constants.TRANSACTION_HISTORY_FILE_NAME, Constants.TRANSACTION_HISTORY);
		loadData(Constants.SELLERS_FILE_NAME, Constants.SELLERS);
		loadData(Constants.BUYERS_FILE_NAME, Constants.BUYERS);
		loadData(Constants.CATEGORY_FILE_NAME, Constants.CATEGORY);
	}
//load the data from a file	
	private void loadData(String fileName, int tag){
		List<String> dataStrings = FileUtils.readFile(fileName);
		if(dataStrings != null && dataStrings.size() > 0){
			for(String dataStr : dataStrings){
				if(!dataStr.trim().isEmpty()){ 
					switch (tag) {  //adding different information according to the tag
					case Constants.ITEM:
						items.add(generateItemObject(dataStr)); //add item
						break;
					case Constants.TRANSACTION_HISTORY:
						transactionHistory.add(generateTransactionRecordObject(dataStr)); //add transaction history
						break;
					case Constants.SELLERS:
						sellers.add((Seller) generateUserObject(Constants.SELLERS, dataStr)); //add seller
						break;
					case Constants.BUYERS:
						buyers.add((Buyer) generateUserObject(Constants.BUYERS, dataStr)); //add buyer
						break;
					case Constants.CATEGORY:
						categoryList.add(dataStr);  //add category
						break;
					}
				}
			}
		}
	}
//generate user information	
	private User generateUserObject(int tag, String str) {
		long userId = Long.valueOf(str.substring(str.indexOf("[ userId: ") + "[ userId: ".length(), str.indexOf("; userName: "))); //generate user id
		String userName = str.substring(str.indexOf("; userName: ") + "; userName: ".length(), str.indexOf("; password: ")); //generate user name
		String password = str.substring(str.indexOf("; password: ") + "; password: ".length(), str.indexOf("; email: ")); //generate password
		String email = str.substring(str.indexOf("; email: ") + "; email: ".length(), str.indexOf(" ]")); //generate email
		return Constants.SELLERS == tag ? new Seller(userId, userName, password, email) : new Buyer(userId, userName, password, email); //return a new user
	}
//generate transaction record
	private TransactionRecord generateTransactionRecordObject(String str) {
		long buyerId = Long.valueOf(str.substring(str.indexOf("[ buyerId: ") + "[ buyerId: ".length(), str.indexOf("; sellerId: "))); //generate buyer id
		long sellerId = Long.valueOf(str.substring(str.indexOf("; sellerId: ") + "; sellerId: ".length(), str.indexOf("; itemNumber: "))); //generate seller id
		long itemNumber = Long.valueOf(str.substring(str.indexOf("; itemNumber: ") + "; itemNumber: ".length(), str.indexOf("; purchaseQuantity: "))); //generate item number
		int purchaseQuantity = Integer.valueOf(str.substring(str.indexOf("; purchaseQuantity: ") + "; purchaseQuantity: ".length(), str.indexOf("; price: $"))); //generate purchase quantity
		double price = Double.valueOf(str.substring(str.indexOf("; price: $") + "; price: $".length(), str.indexOf("; date: "))); //generate price
		String date = str.substring(str.indexOf("; date: ") + "; date: ".length(), str.indexOf("; shippingStatus: ")); //generate date 
		int shippingStatus = Integer.valueOf(str.substring(str.indexOf("; shippingStatus: ") + "; shippingStatus: ".length(), str.indexOf("; transactionId: "))); //generate shipping status
		long transactionId = Long.valueOf(str.substring(str.indexOf("; transactionId: ") + "; transactionId: ".length(), str.indexOf(" ]"))); //generate transaciton id
		return new TransactionRecord(buyerId, sellerId, itemNumber, purchaseQuantity, price, date, shippingStatus, transactionId);
	}

	private Item generateItemObject(String str) {
//generate things needed in a item, return an item
		long itemNumber = Long.valueOf(str.substring(str.indexOf("[ itemNumber: ") + "[ itemNumber: ".length(), str.indexOf("; itemName: ")));
		String itemName = str.substring(str.indexOf("; itemName: ") + "; itemName: ".length(), str.indexOf("; description: "));
		String description = str.substring(str.indexOf("; description: ") + "; description: ".length(), str.indexOf("; sellerId: "));
		long sellerId = Long.valueOf(str.substring(str.indexOf("; sellerId: ") + "; sellerId: ".length(), str.indexOf("; quantity: ")));
		int quantity = Integer.valueOf(str.substring(str.indexOf("; quantity: ") + "; quantity: ".length(), str.indexOf("; price: $")));
		double price = Double.valueOf(str.substring(str.indexOf("; price: $") + "; price: $".length(), str.indexOf("; category: ")));
		String category = str.substring(str.indexOf("; category: ") + "; category: ".length(), str.indexOf(" ]"));
		return new Item(itemNumber, itemName, description, sellerId, quantity, price, category);
	}
//register user	
	private void registerUser(int tag) {
		currentUser = Constants.SELLERS == tag ? new Seller() : new Buyer();
		if(setUserNameSuccess(currentUser, Constants.ENTER_USERNAME_TIPS, tag) && setPasswordSuccess(currentUser, Constants.ENTER_PASSWORD_TIPS)){ //registering
			System.out.print(Constants.ENTER_EMAIL_TIPS);
			String email = Constants.scanner.next(); //asking for the email
			if(!"0".equals(email)){ //set up emails
				currentUser.setEmail(email);
				System.out.print(Constants.REGISTER_SUCCESSFUL_TIPS);
				addUser(tag); 
				if(Constants.SELLERS == tag){
					sellerOperate(); //get seller menu
				}else{
					buyerOperate(); //get buyer menu
				}
			}
		}
	}
//buy menu
	private void buyerOperate() {
		currentUser.setSyncListener(this);
		loadPurchaseHistory();
		System.out.println(Constants.BUYER_MENU);
		String selectString = Constants.scanner.next();
		int select = CommonUtils.getValidSelection(selectString, Constants.BUYER_MENU_SELECTS);
		while(select != 0){
			switch (select) { //decide what to view according to the menu
			case 1:
				((Buyer)currentUser).viewInventory();
				break;
			case 2:
				((Buyer)currentUser).searchItem();
				break;
			case 3:
				((Buyer)currentUser).purchaseItem();
				break;
			case 4:
				((Buyer)currentUser).updateSelfInformation();
				break;
			case 5:
				((Buyer)currentUser).receiveItems();
				break;
			}
			System.out.println(Constants.BUYER_MENU);
			selectString = Constants.scanner.next();
			select = CommonUtils.getValidSelection(selectString, Constants.BUYER_MENU_SELECTS);
		}
	}
//loading puchase history
	private void loadPurchaseHistory() {
		List<TransactionRecord> purchaseHistory = new ArrayList<TransactionRecord>();
		boolean isChange = false;
		for(TransactionRecord transactionRecord : transactionHistory){
			if(transactionRecord.getBuyerId() == currentUser.getUserId()){ //find the transaction according to the user
				if(transactionRecord.getShippingStatus() == Constants.SHIPPING_STATUS_SHIPPING){ //set shipping status
					System.out.println(transactionRecord.print() + " is shipping!");
					transactionRecord.setShippingStatus(Constants.SHIPPING_STATUS_SHIPPING_BUYER_ALREADY_KNOW);
					isChange = true;
				}
				purchaseHistory.add(transactionRecord); //adding purchase history
			}
		}
		((Buyer) currentUser).setPurchaseHistory(purchaseHistory);
		if(isChange){
			onWriteTransactionToFile();
		}
	}
//seller menu
	private void sellerOperate() {
		currentUser.setSyncListener(this);
		loadInventory();
		loadSalesHistory(); 
		System.out.println(Constants.SELLER_MENU);
		String selectString = Constants.scanner.next();
		int select = CommonUtils.getValidSelection(selectString, Constants.SELLER_MENU_SELECTS);
		while(select != 0){
			switch (select) { //view different information according to the menu option
			case 1:
				((Seller)currentUser).viewInventory();
				break;
			case 2:
				((Seller)currentUser).uploadItems();
				break;
			case 3:
				((Seller)currentUser).updateItem();
				break;
			case 4:
				((Seller)currentUser).updateSelfInformation();
				break;
			case 5:
				((Seller)currentUser).shipItems();
				break;
			}
			System.out.println(Constants.SELLER_MENU);
			selectString = Constants.scanner.next();
			select = CommonUtils.getValidSelection(selectString, Constants.SELLER_MENU_SELECTS);
		}
	}
//loading sales history
	private void loadSalesHistory() {
		List<TransactionRecord> salesHistory = new ArrayList<TransactionRecord>();
		boolean isChange = false;
		for(TransactionRecord transactionRecord : transactionHistory){
			if(transactionRecord.getSellerId() == currentUser.getUserId()){ //match transaction record with the seller 
				if(transactionRecord.getShippingStatus() == Constants.SHIPPING_STATUS_NO_SHIP){ //set shipping status
					System.out.println("New order: " + transactionRecord.print());
					transactionRecord.setShippingStatus(Constants.SHIPPING_STATUS_NO_SHIP_SELLER_ALREADY_KONW);
					isChange = true;
				}
				salesHistory.add(transactionRecord); //add transaction record to the history
			}
		}
		((Seller) currentUser).setSalesHistory(salesHistory);
		if(isChange){
			onWriteTransactionToFile();
		}
	}
//load inventory
	private void loadInventory() {
		List<Item> inventory = new ArrayList<Item>();
		for(Item item : items){
			if(item.getSellerId() == currentUser.getUserId()){//check whether the user has the inventory
				inventory.add(item); 
			}
		}
		((Seller) currentUser).setInventory(inventory);		
	}
//adding user
	private void addUser(int tag) {
		List<String> list = new ArrayList<String>();
		list.add(currentUser.toString()); //add user to the list
		if(Constants.SELLERS == tag){
			sellers.add(currentUser);
			FileUtils.writeFile(Constants.SELLERS_FILE_NAME, list, true);
		}else{
			buyers.add(currentUser);
			FileUtils.writeFile(Constants.BUYERS_FILE_NAME, list, true);
		}
	}
//check whether set up password is successful
	private boolean setPasswordSuccess(User currUser, String tips) {
		System.out.print(tips);
		String password = Constants.scanner.next();
		boolean isValid = false;
		while(!"0".equals(password) && !isValid){ //if the input is invalid
			isValid = true;
			if(password.trim().length() < 3){ //password is too short
				System.out.print(Constants.INVALID_INPUT_TIPS);
				password = Constants.scanner.next();
				isValid = false;
			}
		}
		if(isValid){ //change the password if valid
			currUser.setPassword(password);
			return true;
		}
		return false;
	}
//check whether set up username is successful
	private boolean setUserNameSuccess(User currUser, String tips, int tag) {
		System.out.print(tips);
		String userName = Constants.scanner.next();
		boolean isExist = true;
		while(!"0".equals(userName) && isExist){ //if the username already exist, ask for another user name
			isExist = false;
			for(User user : (Constants.SELLERS == tag ? sellers : buyers)){
				if(user.getUserName().equals(userName)){
					System.out.print(Constants.USERNAME_ALREADY_EXIST_TIPS);
					userName = Constants.scanner.next();
					isExist = true;
					break;
				}
			}
		} //set the username
		if(!isExist){
			currUser.setUserName(userName);
			return true;
		}
		return false;
	}
//login to buyer account
	private void buyerLogin() {
		if(isUserNameExist(Constants.BUYERS) && isPasswordRight()){ //match the username with password
			System.out.println(Constants.LOGIN_SUCCESSFUL_TIPS);
			buyerOperate();
		}
	}
//login to seller account
	private void sellerLogin() {
		if(isUserNameExist(Constants.SELLERS) && isPasswordRight()){ //match username with password
			System.out.println(Constants.LOGIN_SUCCESSFUL_TIPS);
			sellerOperate();
		}
	}
//check whether the password is correct
	private boolean isPasswordRight() {
		System.out.print(Constants.ENTER_PASSWORD_TIPS);
		String password = Constants.scanner.next();
		while(!currentUser.getPassword().equals(password) && !"0".equals(password)){ //check the password
			System.out.print(Constants.PASSWORD_WRONG_TIPS);
			password = Constants.scanner.next();
		}
		return currentUser.getPassword().equals(password);
	}
//check if the user name exist 
	private boolean isUserNameExist(int tag) {
		System.out.print(Constants.ENTER_USERNAME_TIPS);
		String userName = Constants.scanner.next();
		boolean isExist = false;
		while(!"0".equals(userName)){ 
			for(User user : (Constants.SELLERS == tag ? sellers : buyers)){ //check the list for the username
				if(user.getUserName().equals(userName)){
					currentUser = user;
					isExist = true;
					break;
				}
			}
			if(!isExist){ //prompt the user that username does not exist
				System.out.print(Constants.USERNAME_WRONG_TIPS);
				userName = Constants.scanner.next();
			}else{
				break;
			}
		}
		return isExist;
	}
//login to the administrator
	private void administratorLogin() {
		System.out.print(Constants.ENTER_PASSWORD_TIPS);
		String password = Constants.scanner.next();
//match the user name and password
		while(!Constants.ADMINISTRATOR_PASSWORD.equals(password) && !"0".equals(password)){ //match the user name and password
			System.out.print(Constants.PASSWORD_WRONG_TIPS);
			password = Constants.scanner.next();
		}
		if(Constants.ADMINISTRATOR_PASSWORD.equals(password)){
			administrator = new Administrator(sellers, buyers, items, transactionHistory, categoryList);
			System.out.println(Constants.LOGIN_SUCCESSFUL_TIPS);
			administratorOperate();
		}
	}
//admin menu
	private void administratorOperate() {
		administrator.setSyncListener(this);
		System.out.println(Constants.ADMINISTRATOR_MENU);
		String selectString = Constants.scanner.next();
		int select = CommonUtils.getValidSelection(selectString, Constants.ADMINISTRATOR_MENU_SELECTS);
		while(select != 0){
			switch (select) { //viewing different information according to the menu option
			case 1:
				administrator.viewUsers(sellers, Constants.SELLERS);
				break;
			case 2:
				administrator.viewUsers(buyers, Constants.BUYERS);
				break;
			case 3:
				administrator.viewInventory();
				break;
			case 4:
				administrator.viewTransactionHistory();
				break;
			case 5:
				administrator.viewShippingStatus();
				break;
			case 6:
				administrator.updateUser(Constants.SELLERS);
				break;
			case 7:
				administrator.updateUser(Constants.BUYERS);
				break;
			case 8:
				administrator.updateItem();
				break;
			case 9:
				administrator.updateCategoryList();
				break;
			}
			System.out.println(Constants.ADMINISTRATOR_MENU);
			selectString = Constants.scanner.next();
			select = CommonUtils.getValidSelection(selectString, Constants.ADMINISTRATOR_MENU_SELECTS);
		}
	}

	@Override
	public void onDeleteItem(Item item) {
		items.remove(item);
	}

	@Override
	public void onWriteItemsToFile() {
		List<String> contents = new ArrayList<String>();
		for(Item item : items){
			contents.add(item.toString());
		}
		FileUtils.writeFile(Constants.ITEMS_FILE_NAME, contents, false);
	}
	

	@Override
	public void onUploadItem(Item item) {
		items.add(item);
		List<String> contents = new ArrayList<String>();
		contents.add(item.toString());
		FileUtils.writeFile(Constants.ITEMS_FILE_NAME, contents, true);
	}

	@Override
	public boolean onSetUserNameSuccess(User user, String tips, int tag) {
		return setUserNameSuccess(user, tips, tag);
	}

	@Override
	public void onWriteUsersToFile(int tag) {
		List<String> contents = new ArrayList<String>();
		for(User user : (Constants.SELLERS == tag ? sellers : buyers)){
			contents.add(user.toString());
		}
		FileUtils.writeFile(Constants.SELLERS == tag ? Constants.SELLERS_FILE_NAME : Constants.BUYERS_FILE_NAME, contents, false);
	}

	@Override
	public boolean onSetPasswordSuccess(User user, String tips) {
		return setPasswordSuccess(user, tips);
	}

	@Override
	public void onViewInventory() {
		for(Item item : items){
			System.out.println(item);
		}
	}

	@Override
	public Item onSearchItemByItemNumber(String itemNumber) {
		Item findItem = null;
		for(Item item : items){
			if(String.valueOf(item.getItemNumber()).equals(itemNumber)){
				findItem = item;
				break;
			}
		}
		return findItem;
	}

	@Override
	public List<Item> onSearchItemByItemName(String itemName) {
		List<Item> findItems = new ArrayList<Item>();
		for(Item item : items){
			if(item.getItemName().equals(itemName)){
				findItems.add(item);
			}
		}
		return findItems;
	}

	@Override
	public void onTransaction(long itemNumber, int purchaseQuantity, TransactionRecord transactionRecord) {
		transactionHistory.add(transactionRecord);
		List<String> contents = new ArrayList<String>();
		contents.add(transactionRecord.toString());
		FileUtils.writeFile(Constants.TRANSACTION_HISTORY_FILE_NAME, contents, true);
		for(Item item : items){
			if(item.getItemNumber() == itemNumber){
				item.setQuantity(item.getQuantity() - purchaseQuantity);
				break;
			}
		}
	}

	@Override
	public void onWriteTransactionToFile() {
		List<String> contents = new ArrayList<String>();
		for(TransactionRecord transactionRecord : transactionHistory){
			contents.add(transactionRecord.toString());
		}
		FileUtils.writeFile(Constants.TRANSACTION_HISTORY_FILE_NAME, contents, false);
	}

	@Override
	public List<String> onGetCategoryList() {
		return categoryList;
	}
}
