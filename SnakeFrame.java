
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//
//Main program

public class SnakeFrame extends Frame{

	public static final int BLOCK_WIDTH = 15 ;
	public static final int BLOCK_HEIGHT = 15 ;

	public static final int ROW = 40;
	public static final int COL = 40;
	

	private int score = 0;
	
	//constructors
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	private MyPaintThread paintThread = new MyPaintThread();

	private Image offScreenImage = null;
	
	private Snake snake = new Snake(this);
	
	private Egg egg = new Egg();
	
	private static SnakeFrame sf =null;
	
	public static void main(String[] args) {
		sf = new SnakeFrame();
		sf.launch();
	}
	
	public void launch(){
		
		this.setTitle("Snake");
		this.setSize(ROW*BLOCK_HEIGHT, COL*BLOCK_WIDTH);
		this.setLocation(30, 40);
		this.setBackground(Color.WHITE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		this.setResizable(false);
		this.setVisible(true);

		this.addKeyListener(new KeyMonitor());
		
		new Thread(paintThread).start();
	}
	
	
	private boolean b_gameOver = false;

	public void gameOver(){
		b_gameOver = true;
	}

	@Override
	public void update(Graphics g) {
		if(offScreenImage==null){
			offScreenImage = this.createImage(ROW*BLOCK_HEIGHT, COL*BLOCK_WIDTH);
		}
		Graphics offg = offScreenImage.getGraphics();

		paint(offg);

		g.drawImage(offScreenImage, 0, 0, null);
		
		if(b_gameOver){
			g.drawString("Game Over", ROW/2*BLOCK_HEIGHT, COL/2*BLOCK_WIDTH);
			paintThread.dead();
		}
		
		snake.draw(g);
		boolean b_Success=snake.eatEgg(egg);

		if(b_Success){
			score+=5;
		}
		egg.draw(g);
		displaySomeInfor(g);
		
		
	}
	
	public void displaySomeInfor(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.drawString("Your Score:"+score, 5*BLOCK_HEIGHT, 5*BLOCK_WIDTH);		
		g.setColor(c);
		
	}
	//Drawing the background.
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GREEN);

		for(int i = 0;i<ROW;i++){
			g.drawLine(0, i*BLOCK_HEIGHT, COL*BLOCK_WIDTH,i*BLOCK_HEIGHT ); //drawing rows. 
		}
		for(int i=0;i<COL;i++){
			g.drawLine(i*BLOCK_WIDTH, 0 , i*BLOCK_WIDTH ,ROW*BLOCK_HEIGHT); //drawing cols
		}
		
		g.setColor(c);
	}
	
	
	
	private class MyPaintThread implements Runnable{

		private static final boolean  running = true;
		private boolean  pause = false;
		@Override
		public void run() {
			while(running){
		
				if(pause){
					continue;
				}
				repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		//handling other request : pause, restart, resume

		public void pause(){
			pause = true;
		}
		
		public void recover(){
			pause = false;
		}
	
		public void dead(){
			pause = true;
		}

		public void reStart(){
			sf.b_gameOver = false;
			this.pause = false;
			snake = new Snake(sf);
	
		}
		
	}
	//keyboard handler class
	private class KeyMonitor extends KeyAdapter{
		
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_SPACE){
				paintThread.pause();  // "space" pause
			}
			else if(key == KeyEvent.VK_B){
				paintThread.recover(); // resume "B"
			}
			else if(key == KeyEvent.VK_F2){
				paintThread.reStart();  // restart the game "F2"
			}
			else{
				snake.keyPressed(e);
			}			
		}
		
	}
	
}
