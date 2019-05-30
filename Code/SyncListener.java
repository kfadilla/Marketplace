import java.util.List;
//method that is being override in other class
public interface SyncListener {
	void onUploadItem(Item item);
	void onDeleteItem(Item item);
	
	List<String> onGetCategoryList();
	
	boolean onSetUserNameSuccess(User user, String tips, int tag);  
	boolean onSetPasswordSuccess(User user, String tips);
	
	void onViewInventory();
	Item onSearchItemByItemNumber(String itemNumber);
	List<Item> onSearchItemByItemName(String itemName);
	
	void onTransaction(long itemNumber, int purchaseQuantity, TransactionRecord transactionRecord);
	
	void onWriteItemsToFile();
	void onWriteUsersToFile(int tag);
	void onWriteTransactionToFile();
}
