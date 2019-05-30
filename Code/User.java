
public class User {
	private long userId;
	private String userName;
	private String password;
	private String email;
	
	private SyncListener syncListener;
	
	private int tag;
	//constructor
	public User(int tag) {
		this.userId = System.currentTimeMillis();
		this.tag = tag;
	}
	
	public User(long userId, String userName, String password, String email, int tag) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.tag = tag;
	}
//get user id
	public long getUserId() {
		return userId;
	}
//set user id
	public void setUserId(long userId) {
		this.userId = userId;
	}
//get user name
	public String getUserName() {
		return userName;
	}
//set user name
	public void setUserName(String userName) {
		this.userName = userName;
	}
//get password
	public String getPassword() {
		return password;
	}
//set password
	public void setPassword(String password) {
		this.password = password;
	}
//get email
	public String getEmail() {
		return email;
	}
//set email
	public void setEmail(String email) {
		this.email = email;
	}
//get information of user	
	@Override
	public String toString() {
		return "[ userId: " + userId + "; userName: " + userName + "; password: " + password + "; email: " + email + " ]";
	}
//set sync listener	
	public void setSyncListener(SyncListener syncListener) {
		this.syncListener = syncListener;
	}
//get sync listener	
	public SyncListener getSyncListener() {
		return syncListener;
	}
//update personal information	
	public void updateSelfInformation(){
		System.out.println(this);
		System.out.println(Constants.UPDATE_USER_MENU);
		String selectString = Constants.scanner.next();
		int select = CommonUtils.getValidSelection(selectString, Constants.UPDATE_USER_MENU_SELECTS);
		while(select != 0){
			switch (select) { //calling different function according to menu option
			case 1:
				updateUserName();
				break;
			case 2:
				updatePassword();
				break;
			case 3:
				updateEmail();
				break;
			}
			System.out.println(Constants.UPDATE_USER_MENU);
			selectString = Constants.scanner.next();
			select = CommonUtils.getValidSelection(selectString, Constants.UPDATE_USER_MENU_SELECTS);
		}
	}
//update email with user input	
	private void updateEmail() {
		System.out.println(Constants.ENTER_NEW_EMAIL_TIPS);
		String newEmail = Constants.scanner.next();
		if(!"0".equals(newEmail)){
			setEmail(newEmail);
			syncListener.onWriteUsersToFile(tag);
			System.out.println(Constants.UPDATE_SUCCESSFUL_TIPS);
		}
	}

//update password with user input
	private void updatePassword() {
		if(syncListener.onSetPasswordSuccess(this, Constants.ENTER_NEW_PASSWORD_TIPS)){
			syncListener.onWriteUsersToFile(tag);
			System.out.println(Constants.UPDATE_SUCCESSFUL_TIPS);
		}
	}
//update username with user input
	private void updateUserName() {
		if(syncListener.onSetUserNameSuccess(this, Constants.ENTER_NEW_USERNAME_TIPS, tag)){
			syncListener.onWriteUsersToFile(tag);
			System.out.println(Constants.UPDATE_SUCCESSFUL_TIPS);
		}
	}
}
