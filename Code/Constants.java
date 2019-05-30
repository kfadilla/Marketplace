import java.util.Scanner;

public interface Constants {
	public static Scanner scanner = new Scanner(System.in);
	
//messages used in different function
//can be easily read from its variable name	
	public static final String SELLERS_FILE_NAME = "sellers.txt";
	public static final String BUYERS_FILE_NAME = "buyers.txt";
	public static final String ITEMS_FILE_NAME = "items.txt";
	public static final String TRANSACTION_HISTORY_FILE_NAME = "transaction_history.txt";
	public static final String UPLOAD_ITEMS_FILE_NAME = "upload_items.txt";
	public static final String CATEGORY_FILE_NAME = "category.txt";
	
	
	public static final int ITEM = 1;
	public static final int TRANSACTION_HISTORY = 2;
	public static final int SELLERS = 3;
	public static final int BUYERS = 4;
	public static final int CATEGORY = 5;
	
	
	public static final String SHIPPING_STATUS_NO_SHIP_TIPS = "current status: no ship";
	public static final String SHIPPING_STATUS_SHIPPING_TIPS = "current status: shipping";
	public static final String SHIPPING_STATUS_RECEIVED_TIPS = "current status: received";
	public static final int SHIPPING_STATUS_NO_SHIP = 1;
	public static final int SHIPPING_STATUS_NO_SHIP_SELLER_ALREADY_KONW = 2;
	public static final int SHIPPING_STATUS_SHIPPING = 3;
	public static final int SHIPPING_STATUS_SHIPPING_BUYER_ALREADY_KNOW = 4;
	public static final int SHIPPING_STATUS_RECEIVED = 5;
	
	
	public static final String ADMINISTRATOR_PASSWORD = "admin";
	
	
	public static final String DEFULT_CATEGORY = "defult";
	
	
	public static final String WELCOME_TIPS = "Welcome to mini-Amazon!";
	public static final String END_TIPS = "Good Bye!";
	
	
	public static final String MAIN_MENU = "\n\t1\tAdministrator login.\n\t2\tSeller login." +
			"\n\t3\tBuyer login.\n\t4\tRegister seller." +
			"\n\t5\tRegister buyer.\n\t0\tQuit." +
			"\n\nPlease select: ";
	public static final int MAIN_MENU_SELECTS = 5;
	
	
	public static final String ADMINISTRATOR_MENU = "\n\t1\tView sellers.\n\t2\tView buyers." +
			"\n\t3\tView inventory.\n\t4\tView transaction history." +
			"\n\t5\tView shipping status." +
			"\n\t6\tUpdate seller.\n\t7\tUpdate buyer." +
			"\n\t8\tUpdate item.\n\t9\tUpdate category.\n\t0\tQuit." +
			"\n\nPlease select: ";
	public static final int ADMINISTRATOR_MENU_SELECTS = 9;
	
	
	public static final String SELLER_MENU = "\n\t1\tView inventory.\n\t2\tUpload items." +
			"\n\t3\tUpdate items.\n\t4\tUpdate self information." +
			"\n\t5\tShip items.\n\t0\tQuit.\n\nPlease select: ";
	public static final int SELLER_MENU_SELECTS = 5;
	
	
	public static final String BUYER_MENU = "\n\t1\tView inventory.\n\t2\tSearch item." +
			"\n\t3\tPurchase item.\n\t4\tUpdate self information." +
			"\n\t5\tReceive items.\n\t0\tQuit.\n\nPlease select: ";
	public static final int BUYER_MENU_SELECTS = 5;
	
	
	public static final String ADMIN_VIEW_INVENTORY_MENU = "\n\t1\tView by selected seller.\n\t2\tView by selected category." +
			"\n\t3\tView all.\n\t0\tQuit.\n\nPlease select: ";
	public static final int ADMIN_VIEW_INVENTORY_MENU_SELECTS = 3;
	
	
	public static final String ADMIN_VIEW_TRANSACTION_MENU = "\n\t1\tView by selected day.\n\t2\tView last week." +
			"\n\t3\tView last month.\n\t4\tView all." +
			"\n\t0\tQuit.\n\nPlease select: ";
	public static final int ADMIN_VIEW_TRANSACTION_MENU_SELECTS = 4;
	public static final int WEEK = 7;
	public static final int MONTH = 30;
	
	
	public static final String ADMIN_VIEW_SHIPPING_STATUS_MENU = "\n\t1\tView by transaction ID.\n\t2\tView all." +
			"\n\t0\tQuit.\n\nPlease select: ";
	public static final int ADMIN_VIEW_SHIPPING_STATUS_MENU_SELECTS = 2;
	
	
	public static final String ADMIN_UPDATE_USER_MENU = "\n\t1\tUpdate username.\n\t2\tUpdate password." +
			"\n\t3\tUpdate email.\n\t4\tDelete user." +
			"\n\t0\tQuit.\n\nPlease select: ";
	public static final int ADMIN_UPDATE_USER_MENU_SELECTS = 4;
	
	public static final String UPDATE_USER_MENU = "\n\t1\tUpdate username.\n\t2\tUpdate password." +
			"\n\t3\tUpdate email." +
			"\n\t0\tQuit.\n\nPlease select: ";
	public static final int UPDATE_USER_MENU_SELECTS = 3;
	
	
	public static final String UPDATE_ITEM_MENU = "\n\t1\tUpdate item name.\n\t2\tUpdate description." +
			"\n\t3\tUpdate quantity.\n\t4\tUpdate price." +
			"\n\t5\tUpdate category.\n\t6\tDelete item." +
			"\n\t0\tQuit.\n\nPlease select: ";
	public static final int UPDATE_ITEM_MENU_SELECTS = 6;
	
	
	public static final String SELLER_UPLOAD_ITEMS_MENU = "\n\t1\tUpload by entered.\n\t2\tUpload by file." +
			"\nThe format must be" +
			" \"[ itemName: xxx; description: xxx; sellerId: xxx; quantity: xxx; price: $xxx; category: xxx ]\" every line in file." +
			"\n\t0\tQuit.\n\nPlease select: ";
	public static final int SELLER_UPLOAD_ITEMS_MENU_SELECTS = 2;
	
	public static final String SEARCH_ITEMS_MENU = "\n\t1\tSearch by item number.\n\t2\tSearch by name." +
			"\n\t0\tQuit.\n\nPlease select: ";
	public static final int SEARCH_ITEMS_MENU_SELECTS = 2;
	
	
	public static final String UPDATE_CATEGORY_MENU = "\n\t1\tAdd category.\n\t2\tRename category." +
			"\n\t3\tDelete category." +
			"\n\t0\tQuit.\n\nPlease select: ";
	public static final int UPDATE_CATEGORY_MENU_SELECTS = 3;
	
	
	public static final String ENTER_DATE_TIPS = "Please enter date (yyyy-MM-dd, 0 to quit): ";
	
	
	public static final String ENTER_USER_ID_TIPS = "Please enter user ID (0 to quit): ";
	
	
	public static final String ENTER_ITEM_NUMBER_TIPS = "Please enter item number (0 to quit): ";
	
	
	public static final String ENTER_TRANSACTION_ID_TIPS = "Please enter transaction ID (0 to quit): ";
	
	
	public static final String SELECT_CATEGORY_TIPS = "Please select category (0 to quit): ";
	public static final String ENTER_CATEGORY_NAME_TIPS = "Please enter category name (0 to quit): ";
	public static final String CATEGORY_ALREADY_EXIST_TIPS = "Category already exist, please enter again (0 to quit): ";
	public static final String ENTER_NEW_CATEGORY_NAME_TIPS = "Please enter new name (0 to quit): ";
	
	public static final String DELETE_ITEMS_IN_CATEGORY_TIPS = "Delete items in this category? ('y' or 'Y' to delete): ";
	
	
	public static final String ENTER_USERNAME_TIPS = "Please enter username (0 to quit): ";
	public static final String ENTER_NEW_USERNAME_TIPS = "Please enter new username (0 to quit): ";
	public static final String USERNAME_WRONG_TIPS = "Username not exist, please enter again (0 to quit): ";
	public static final String USERNAME_ALREADY_EXIST_TIPS = "Username already exist, please enter again (0 to quit): ";
	
	
	public static final String ENTER_PASSWORD_TIPS = "Please enter password, length at least 3 (0 to quit): ";
	public static final String ENTER_NEW_PASSWORD_TIPS = "Please enter new password, length at least 3 (0 to quit): ";
	public static final String PASSWORD_WRONG_TIPS = "Password wrong, please enter again (0 to quit): ";
	
	
	public static final String ENTER_EMAIL_TIPS = "Please enter email (0 to quit): ";
	public static final String ENTER_NEW_EMAIL_TIPS = "Please enter new email (0 to quit): ";
	
	
	public static final String ENTER_ITEM_NAME_TIPS = "Please enter item name (0 to quit): ";
	public static final String ENTER_NEW_ITEM_NAME_TIPS = "Please enter new item name (0 to quit): ";
	
	
	public static final String ENTER_DESCRIPTION_TIPS = "Please enter description (0 to quit): ";
	public static final String ENTER_NEW_DESCRIPTION_TIPS = "Please enter new description (0 to quit): ";
	
	
	public static final String ENTER_QUANTITY_TIPS = "Please enter quantity (0 to quit): ";
	public static final String ENTER_NEW_QUANTITY_TIPS = "Please enter new quantity (0 to quit): ";
	
	
	public static final String ENTER_PRICE_TIPS = "Please enter price (0 to quit): ";
	public static final String ENTER_NEW_PRICE_TIPS = "Please enter new price (0 to quit): ";
	
	
	public static final String LOGIN_SUCCESSFUL_TIPS = "\nLogin successful!";
	public static final String REGISTER_SUCCESSFUL_TIPS = "\nRegister successful!";
	public static final String UPDATE_SUCCESSFUL_TIPS = "\nUpdate successful!";
	public static final String DELETE_SUCCESSFUL_TIPS = "\nDelete successful!";
	public static final String UPLOAD_SUCCESSFUL_TIPS = "\nUpload successful!";
	
	
	public static final String ENTER_PURCHASE_QUANTITY_TIPS = "Please enter purchase quantity (0 to quit): ";
	
	public static final String TRANSACTION_SUCCESSFUL_TIPS = "\nTransaction successful!";
	
	public static final String SELECT_ITEM_TIPS = "Please select item (0 to quit): ";
	
	public static final String RECEIVE_SUCCESSFUL_TIPS = "\nReceive successful!";
	
	public static final String SHIP_SUCCESSFUL_TIPS = "\nShip successful!";
	
	public static final String ALL_RECEIVED_TIPS = "All items are received or no shipped.";
	
	public static final String ALL_SHIPPED_TIPS = "All items are shipped.";
	
	
	public static final String FILE_EMPTY_TIPS = "\nSorry, file is empty.";
	
	
	public static final String NO_PURCHASE_HISTORY_TIPS = "\nSorry, no purchase history.";
	
	public static final String NO_SALES_HISTORY_TIPS = "\nSorry, no sales history.";
	
	public static final String NO_TRANSACTION_HISTORY_TIPS = "\nSorry, no transaction history.";
	
	public static final String NO_FIND_TIPS = "\nSorry, no find.";
	public static final String OUT_OF_STOCK_TIPS = "\nSorry, item is out of stock.";
	public static final String NO_CATEGORY_EXCEPT_DEFULT_TIPS = "\nSorry, no category, except defult.";
	
	
	public static final String INVENTORY_EMPTY_TIPS = "\nSorry, inventory is empty.";
	
	public static final String NO_SELLERS_TIPS = "\nSorry, no sellers.";
	public static final String NO_BUYERS_TIPS = "\nSorry, no buyers.";
	
	
	public static final String DATE_FORMAT = "yyyy-MM-dd/HH:mm:ss";
	public static final long MILLI_DAY = 24 * 60 * 60 * 1000;
	public static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";
	
	
	public static final String INVALID_INPUT_TIPS = "Invalid input, please enter again (0 to quit): ";
	
	
	public static int[] DAYS = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	
}
