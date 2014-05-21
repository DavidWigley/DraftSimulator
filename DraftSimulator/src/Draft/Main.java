package Draft;
import java.awt.Canvas;
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
					year = logic.YearInput();
					currentlyLeapYear = logic.IsLeapYear(year);
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
				Thread.sleep(500);
			} catch (InterruptedException e) {
				
			}
		}
	}
	
	public void DrawNums() {
		System.out.println("Drew these "+ availableNums);
		int count = 0;
		int num;
		System.out.println("Running draw nums");
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
		for (int i = 0; i < pickedNums.length; i++) {
			System.out.println(pickedNums[i] +  "  " + i);
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
//						System.out.println(count + "col Num: " + col + "row Num: " + row);
						currentNum = Integer.toString(pickedNums[count]);
						g.drawString(currentNum, xLine * col + 55, yLine * row + 20);
						count++;
					}
				}
				
				//draws nums for days
				for (int i = 1 ; i < NUM_ROW; i++) {
					currentNum = Integer.toString(i);
					g.drawString(currentNum, 55, yLine * i + 20);
				}
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

	public void mouseMoved(MouseEvent arg0) {
		
	}

	public void mouseClicked(MouseEvent arg0) {
		
	}

	public void mouseEntered(MouseEvent arg0) {

		
	}

	public void mouseExited(MouseEvent arg0) {

		
	}

	public void mousePressed(MouseEvent e) {
		if (e.getX() > 0 && e.getX() < playButton.getWidth(this) * 2) {
			if (e.getY() > 0 && e.getY() < playButton.getHeight(this) * 2){
				begin = true;
			}
		}
		
	}

	public void mouseReleased(MouseEvent arg0) {

		
	}

	public void keyPressed(KeyEvent arg0) {
		
		
	}

	public void keyReleased(KeyEvent arg0) {

		
	}

	public void keyTyped(KeyEvent arg0) {

		
	}

	public void run() {

		
	}

}
