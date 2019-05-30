import java.util.regex.Pattern;

public final class CommonUtils {
	public static int getValidSelection(String selectString, int selections) { //return the valid input choice
		while(!isInteger(selectString) || Integer.valueOf(selectString) < 0 || Integer.valueOf(selectString) > selections){
			System.out.print(Constants.INVALID_INPUT_TIPS);
			selectString = Constants.scanner.next();
		}
		return Integer.valueOf(selectString);
	}
	
	public static boolean isDateValid(String dateString){ //check if the date is in the right format
		boolean isValid = false;
		if (Pattern.matches(Constants.DATE_PATTERN, dateString)) {
			int year = Integer.valueOf(dateString.substring(0, 4));
			if(isLeapYear(year)){ //check whether it is in the leap year
				Constants.DAYS[1] = 29;
			}else{
				Constants.DAYS[1] = 28;
			}
			int month = Integer.valueOf(dateString.substring(5, 7));
			if (month > 0 && month <= 12) { //check month is correct
				int day = Integer.valueOf(dateString.substring(8));
				if (day > 0 && day <= Constants.DAYS[month - 1]) {
					isValid = true;
				}
			}
		}
		return isValid;
	}
//check if it is a leap year
	private static boolean isLeapYear(int year) {
		return 0 == year % 4 && year % 100 != 0 || 0 == year % 400;
	}
//check if it is an integer	
	public static boolean isInteger(String str){
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
//check if it is a double	
	public static boolean isDouble(String str){
		try {
			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
