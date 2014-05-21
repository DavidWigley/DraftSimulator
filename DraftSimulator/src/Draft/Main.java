package Draft;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class Main extends Canvas implements Runnable, KeyListener,MouseListener, MouseMotionListener{

	JFrame frame = new JFrame();
	ImageIcon frameIconI = new ImageIcon(getClass().getResource("/resources/uncleSam.jpg"));
	Image frameIcon = frameIconI.getImage();
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width;
	int height;
	private int bufNum = 2;
	private boolean running;

	
	public Main() {
		width = (int) screenSize.getWidth();
		height = (int) screenSize.getHeight();
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

	}
	
	public void start() {
		running = true;
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

	public void mousePressed(MouseEvent arg0) {

		
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
