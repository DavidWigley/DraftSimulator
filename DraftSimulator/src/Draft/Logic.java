package Draft;
import javax.swing.JOptionPane;


public class Logic {
 
	private int month, day, year;
	
	public Logic() {
		
	}
	
	public int YearInput() {
		int year = -1;
		boolean done = false;
		while (!done) {
			String choice = JOptionPane.showInputDialog(null, "Enter a year. Above 1900");
			try	{
				year = Integer.parseInt(choice);
			} catch (NumberFormatException nfe) {
				year = -1;
			}
			if (year <= 1900) {
				JOptionPane.showMessageDialog(null, "You did not enter a valid number for the year. ");
			}else {
				done = true;
			}
		}
		this.year = year;
		return year;
	}
	
	public int MonthInput() {
		int month = -1;
		boolean done = false;
		while (!done) {
			String choice = JOptionPane.showInputDialog(null, "Enter a month. Please use numbers IE: Jan = 1, Feb = 2, etc");
			try	{
				month = Integer.parseInt(choice);
			} catch (NumberFormatException nfe) {
				month = -1;
			}
			if (month == -1 || month==0 || month > 12) {
				JOptionPane.showMessageDialog(null, "You did not enter a valid number for the month");
			} else {
				done = true;
			}
		}
		this.month = month;
		return month;
		
	}
	
	public int DayInput() {
		boolean done = false;
		int day = -1;
		boolean flag = true;
		while (!done) {
			String choice = JOptionPane.showInputDialog(null, "Enter the day");
			try	{
				day = Integer.parseInt(choice);
			} catch (NumberFormatException nfe) {
				day = -1;
			}
			if (day > 0 && day <32) {
				flag = ValidDay(day, month);
				if (!flag) {
					JOptionPane.showMessageDialog(null, "The month you entered does not have that day");
				}else {
					done = true;
				}
			}else {
				JOptionPane.showMessageDialog(null, "The day you entered is not in the valid range");
			}
			
		}
		
		return day;
	}
	
	public boolean IsLeapYear(int year) {
		boolean leapYear = false;;
		if (year % 4 == 0) {
			leapYear = true;
			if (year % 100 == 0) {
				if (year % 400 == 0) {
					leapYear = true;
				} else {
					leapYear = false;
				}
			}
		}
		return leapYear;
	}
	
	public boolean ValidDay(int day, int month) {
		boolean valid = false;
		//months with 31 days
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			if (day <= 31) {
				valid = true;
			}
		}
		//months with 30 days
		else if (month == 4 || month == 6 || month == 9 || month == 11) {
			if (day <= 30) {
				valid = true;
			}
		}
		//feb
		else {
			if (IsLeapYear(year)) {
				if (day <= 29) {
					valid = true;
				}
			}else {
				if (day <= 28) {
					valid = true;
				}
			}
		}
		return valid;
	}

}
