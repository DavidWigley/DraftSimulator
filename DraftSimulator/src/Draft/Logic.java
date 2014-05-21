package Draft;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Logic {
 
	private int month, day, year;
	public Logic() {
		
	}
	
	public int YearInput() {
		int year = -1;
		boolean done;
		
		
		return year;
	}
	
	public int MonthInput() {
		int num = -1;
		boolean done = false;
		while (!done) {
			String choice = JOptionPane.showInputDialog(null, "Enter a month. Please use numbers IE: Jan = 1, Feb = 2, etc");
			try	{
			num = Integer.parseInt(choice);
			} catch (NumberFormatException nfe) {
				num = -1;
			}
			if (num == -1 || num==0 || num > 12) {
				JOptionPane.showMessageDialog(null, "You did not enter a valid number for the month");
			}
		}
		month = num;
		return num;
		
	}
	
	public int DayInput() {
		boolean done = false;
		int num = -1;
		while (!done) {
			String choice = JOptionPane.showInputDialog(null, "Enter the day");
		}
		
		return num;
	}

}
