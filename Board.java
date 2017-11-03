//
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private final int WIDTH = 500;
    private final int HEIGHT = 500;
    private final int ANT_SIZE = 10;
    private final int BOARD_SIZE = 25000;
	private final int randomize1 = 25;
	private final int randomize2 = 50;
	private final int randomize3 = 75;
	private final int randomize4 = 100;

    private final int x[] = new int[BOARD_SIZE];
    private final int y[] = new int[BOARD_SIZE];

	private int Timerdelay = 50;
    private int ants;
	private int coins;
	private int ant_x, ant_y;
    private int coin_x, coin_y;
    private int water_x, water_y;
	private int mud_x, mud_y;
	private int hill_x, hill_y;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;
	private Image ant;
    private Image coin;
    private Image water;
    private Image mud;
	private Image hill;

    public Board() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        loadImages();
        initGame();
    }

    private void loadImages() {

        ImageIcon a = new ImageIcon("coin.jpg");
        coin = a.getImage();

        ImageIcon b = new ImageIcon("water.jpg");
        water = b.getImage();

        ImageIcon c = new ImageIcon("mud.png");
        mud = c.getImage();
		
		ImageIcon d = new ImageIcon("hill.png");
        hill = d.getImage();
		
		ImageIcon e = new ImageIcon("ant.jpg");
		ant = e.getImage();
    }

    private void initGame() {
        ants = 1;
		coins = 0;
		
		for (int z = 0; z < ants; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }
		
        Reward();
		
        timer = new Timer(Timerdelay, this);
        timer.start();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
        
        if (inGame) {
			g.drawImage(coin, coin_x, coin_y, this);
			g.drawImage(water, water_x, water_y, this);
			g.drawImage(mud, mud_x, mud_y, this);
			g.drawImage(hill, hill_x, hill_y, this);
			
            for (int z = 0; z < ants; z++) {
                if (z == 0) {
                  g.drawImage(ant, x[z], y[z], this);  
                }
            }

            Toolkit.getDefaultToolkit().sync();

        }
			
         Decide(g);	
        	 
    }

    public void gameOver(Graphics g) {
        
        String msg = "Game Over";
        Font small = new Font("Arial", Font.BOLD, 20);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, HEIGHT / 2);
		inGame = false;
    }	
	
	public void Decide(Graphics g){
		
    if (coins == 4){
	   initGame();
    }
	
	else if (coins < 0 || inGame == false){
      gameOver(g);
	}
    }

    private void checkCoin() {

        if ((x[0] == coin_x) && (y[0] == coin_y)) {
            coins = coins + 1;
			Reward();
        }
		
		else if ((x[0] == water_x) && (y[0] == water_y)){
		   	coins = coins - 4;
			Reward();
	    }
        
		else if ((x[0] == mud_x) && (y[0] == mud_y)){
		   	coins = coins - 3;
			Reward();
	    }
		
		else if ((x[0] == hill_x) && (y[0] == hill_y)){
		   	coins = coins - 1;
			Reward();
	    }
        		
    }

    private void move() {

        for (int z = ants; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= ANT_SIZE;
        }

        if (rightDirection) {
            x[0] += ANT_SIZE;
        }

        if (upDirection) {
            y[0] -= ANT_SIZE;
        }

        if (downDirection) {
            y[0] += ANT_SIZE;
        }
    }

    private void checkCollision() {

        for (int z = ants; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }
        
        if(!inGame) {
            timer.stop();
        }
    }

    private void Reward() {

        int a = (int) (Math.random() * randomize1);
        coin_x = ((a * ANT_SIZE));

        a = (int) (Math.random() * randomize1);
        coin_y = ((a * ANT_SIZE));
		
		int b = (int) (Math.random() * randomize2);
        water_y = ((b * ANT_SIZE));
		
		b = (int) (Math.random() * randomize2);
        water_y = ((b * ANT_SIZE));
		
		int c = (int) (Math.random() * randomize3);
        mud_y = ((c * ANT_SIZE));
		
		c = (int) (Math.random() * randomize3);
        mud_y = ((c * ANT_SIZE));
		
		int d = (int) (Math.random() * randomize4);
        hill_y = ((d * ANT_SIZE));
		
		d = (int) (Math.random() * randomize4);
        hill_y = ((d * ANT_SIZE));
		
    }
	
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkCoin();
            checkCollision();
            move();
        }

        repaint();
    }
	private class TAdapter extends KeyAdapter {
		
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}


    
	