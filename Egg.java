

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;


/*
This class initialies and set egg in the game and randomlizes its location
*/
public class Egg {

	private int row;
	private int col;

	private static final int BLOCK_WIDTH = SnakeFrame.BLOCK_WIDTH;
	private static final int BLOCK_HEIGHT = SnakeFrame.BLOCK_HEIGHT;
	
	private static final Random r = new Random();
	
	private Color color = Color.RED;
	//constructor: create an egg
	public Egg(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public Egg() {
		this((r.nextInt(SnakeFrame.ROW-2))+2,(r.nextInt(SnakeFrame.COL-2))+2);
	}
	
	public void reAppear(){
		this.row = (r.nextInt(SnakeFrame.ROW-2))+2;
		this.col = (r.nextInt(SnakeFrame.COL-2))+2;
	} 
	
	public void draw(Graphics g){
	//	Color c= g.getColor();
		g.setColor(Color.PINK);
		g.fillOval(col*BLOCK_WIDTH, row*BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT);


		
	}

	public Rectangle getRect(){
		return new Rectangle(col*BLOCK_WIDTH, row*BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT);
	}
	
}
