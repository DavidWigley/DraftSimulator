package Draft;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Main extends Canvas implements Runnable, KeyListener,MouseListener, MouseMotionListener{

	Logic logic = new Logic();
	Random generator = new Random();
	JFrame frame = new JFrame();
	ImageIcon frameIconI = new ImageIcon(getClass().getResource("/resources/uncleSam.jpg"));
	Image frameIcon = frameIconI.getImage();
	ImageIcon playButtonIcon = new ImageIcon(getClass().getResource("/resources/draft.png"));
	Image playButton = playButtonIcon.getImage();
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width;
	int height;
	private int bufNum = 2;
	private boolean running;
	Graphics g;
	BufferStrategy bf;
	int xLine = 0, yLine = 0;
	private static final int NUM_COL = 13, NUM_ROW = 32;
	int gameState = 1;
	boolean begin = false;
	boolean initDrawing = false;
	ArrayList <Integer> availableNums = new ArrayList <Integer>();
	Integer[] pickedNums;
	int year;
	boolean currentlyLeapYear = false;
	int draftNum;
	private Color green;
	
	public Main() {
		width = (int) screenSize.getWidth();
		height = (int) screenSize.getHeight();
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setMinimumSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.addMouseListener(this);
		frame.addMouseMotionListener(this);
		frame.addKeyListener(this);
		frame.setVisible(true);
		frame.createBufferStrategy(bufNum);
		frame.setIconImage(frameIcon);
		xLine = width / NUM_COL;
		yLine = height / NUM_ROW;
		bf =  frame.getBufferStrategy();
		
		//adding 1 - 366 to the array
		for (int i = 1; i < 367; i++) {
			availableNums.add(i);
		}
	}
	
	public void start() {
		running = true;
		Run();
	}
	
	public void Run() {
		while (running) {
			if (gameState == 1) {
				if (begin) {
					//gets year and if its a leap year
					year = logic.YearInput();
					currentlyLeapYear = logic.IsLeapYear(year);
					//randomly generate a draft #
					int choice = generator.nextInt(61);
					draftNum = 140 + choice;
					JOptionPane.showMessageDialog(null, "Anyone with a number below " + draftNum + " gets drafted. Red is drafted, green is undrafted.");
					gameState =2;
				}
				paint();
			}else if (gameState == 2) {
				if (!initDrawing) {
					DrawNums();
				}
				if(availableNums.size() == 0) {
					paint();
				}
			}
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				
			}
		}
	}
	/**
	 * Method that randomly draws the numbers and assigns them to tiles. Takes leap year into account
	 */
	public void DrawNums() {
		int count = 0;
		int num;
		if (currentlyLeapYear) {
			pickedNums = new Integer[366];
			while (availableNums.size() != 0) {
				num = generator.nextInt(availableNums.size());
				num = availableNums.get(num);
				//redundancy dont ask
				while (availableNums.indexOf(num) == -1) {
					num = generator.nextInt(availableNums.size());
				}
				availableNums.remove(availableNums.indexOf(num));
				pickedNums[count]= num;
				count++;
			}
			initDrawing = true;
		}else {
			pickedNums = new Integer[365];
			availableNums.remove(availableNums.indexOf(366));
			while (availableNums.size() != 0) {
				num = generator.nextInt(availableNums.size());
				num = availableNums.get(num);
				//redundancy dont ask
				while (availableNums.indexOf(num) == -1) {
					num = generator.nextInt(availableNums.size());
				}
				availableNums.remove(availableNums.indexOf(num));
				pickedNums[count]= num;
				count++;
			}
			initDrawing = true;
		}
	}
	
	public void paint() {
		try {
			g = bf.getDrawGraphics();
			g.clearRect(0, 0, width, height);
			if (gameState == 2 && initDrawing) {
				String currentNum;
				int xCount = -1, yCount = 0;
				//logic for drawing lines
				for (int col = 0; col < NUM_COL; col++) {
					xCount++;
					g.drawLine(xLine * xCount, 0, xLine * xCount, height);
					for (int row = 0; row <= NUM_ROW; row ++){
						if (row == 0) {
							yCount = 0;
						}else {
							yCount++;
						}
						g.drawLine(0, yLine * yCount, width, yLine * yCount);
					}
				}
				
				//logic for adding nums in
				int rowNum;
				int count = 0;
				for (int col = 1; col < NUM_COL; col++) {
					if (col == 1|| col == 3 || col == 5 || col == 7 || col == 8 || col == 10 || col == 12) {
						rowNum = 31;
					}else if (col == 4 || col == 6 || col == 9 || col == 11) {
						rowNum = 30;
					}else {
						if (currentlyLeapYear) {
							rowNum = 29;
						}else {
							rowNum = 28;
						}
					}
					for (int row = 1; row <= rowNum; row++) {
						System.out.println("The draft num is: " + draftNum);
						System.out.println("The currentNum is: " + pickedNums[count]);
						if (pickedNums[count] <= draftNum) {
							g.setColor(Color.RED);
						}else {
							green = new Color(0,139,69);
							g.setColor(green);
						}
						currentNum = Integer.toString(pickedNums[count]);
						g.drawString(currentNum, xLine * col + 55, yLine * row + 20);
						count++;
					}
				}
				g.setColor(Color.BLACK);
				
				//draws nums for days
				for (int i = 1 ; i < NUM_ROW; i++) {
					//setting color to red, to show drafted
					currentNum = Integer.toString(i);
					g.drawString(currentNum, 55, yLine * i + 20);
				}

				//draws month names in
				for (int i = 1; i < NUM_COL; i++) {
					String choice = null;
					if (i ==1) {
						choice = "January";
					}else if (i == 2) {
						choice = "February";
					}else if (i == 3) {
						choice = "March";
					}else if (i == 4) {
						choice = "April";
					}else if (i == 5){
						choice = "May";
					}else if (i == 6) {
						choice = "June";
					}else if (i == 7) {
						choice = "July";
					}else if (i == 8) {
						choice = "August";
					}else if (i == 9){
						choice = "September";
					}else if (i == 10){
						choice = "October";
					}else if (i == 11) {
						choice = "November";
					}else {
						choice = "December";
					}
					g.drawString(choice, xLine * i + 30,20);
				}
				String number = Integer.toString(draftNum);
				g.setColor(Color.red);
				g.drawString(number, 40, 20);
				g.setColor(Color.yellow);
				g.drawRect(35, 5, 30, 25);
			}else {
				g.drawString("Made by David Wigley", 5, height - 5);
				g.drawImage(playButton, 0, 0, playButton.getWidth(this) *2 , playButton.getHeight(this) * 2, this);
			}
		}finally {
			g.dispose();
		}
		// Shows the contents of the backbuffer on the screen.
		bf.show();
	}
	public void mouseDragged(MouseEvent arg0) {}

	public void mouseMoved(MouseEvent arg0) {}

	public void mouseClicked(MouseEvent arg0) {}

	public void mouseEntered(MouseEvent arg0) {}

	public void mouseExited(MouseEvent arg0) {

		
	}

	public void mousePressed(MouseEvent e) {
		if (e.getX() > 0 && e.getX() < playButton.getWidth(this) * 2) {
			if (e.getY() > 0 && e.getY() < playButton.getHeight(this) * 2){
				begin = true;
			}
		}
		
	}

	public void mouseReleased(MouseEvent arg0) {}

	//closes the app
	public void keyPressed(KeyEvent key) {
		if (key.getKeyCode() == key.VK_ESCAPE){
			System.exit(0);
		}
	}

	public void keyReleased(KeyEvent arg0) {}

	public void keyTyped(KeyEvent arg0) {}

	public void run() {}

}
