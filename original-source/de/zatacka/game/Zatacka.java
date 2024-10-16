package de.zatacka.game;

import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

public class Zatacka extends Canvas implements Runnable
{ 
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	private final int G_WIDTH = 676;
	private final int G_HEIGHT = 594;
	private boolean field[][] = new boolean[G_HEIGHT][G_WIDTH];
	
	public Spieler player[];  
	private Statistik statistik;
	
	private boolean next_round =  false;	
	private boolean started = true;
	private boolean running = true;
	private boolean ended = false;
	
	private int speed;
	private int maxScore;
	
	private Thread  th;
	private JFrame fm;
	private DisplayMode old;

	public Zatacka( Spieler player[], int speed ) {
		setBounds(0,0,WIDTH,HEIGHT);
		setBackground(Color.black);
		fm = new JFrame();
		fm.setUndecorated(true);
		JPanel pan = (JPanel) fm.getContentPane();
		pan.setLayout(null);
		pan.add(this);

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
       	GraphicsDevice device = env.getDefaultScreenDevice();
       	device.setFullScreenWindow(fm);
       	old=device.getDisplayMode();
       	device.setDisplayMode(new DisplayMode(WIDTH, HEIGHT, old.getBitDepth(), old.getRefreshRate()));

		BufferedImage cur = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
		setCursor(getToolkit().createCustomCursor(cur,new Point(0,0),""));

		fm.setVisible(true);
		fm.addKeyListener(new Steuerung(this));

		this.player = player;
		this.statistik = new Statistik(player.length);			
		
		this.speed = 10*(6-speed);
		maxScore = (player.length-1)*10;

		th = new Thread (this);
		th.start();
	}

	public void quit() {
		running = false;

		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setDisplayMode(old);

		fm.dispose();
		System.exit(0);
	}

	public void nextRound(){
		field = new boolean[G_HEIGHT][G_WIDTH];		
		
		for ( int i =0; i < player.length; i++ ){
			player[i].kurve =   new Kurve(Math.round(Math.random()*Zatacka.WIDTH)+100,
		            Math.round(Math.random()*Zatacka.HEIGHT)+100,
		            Math.random()*360,
		            Math.round(Math.random() * 10) + 1);
		}
		
		statistik.setAllAlive();
	}

	public  boolean isCollision(Kurve k) {
		boolean erg;

		int xPos = k.getXPos();
		int yPos = k.getYPos();

		int a = 4;
		if(xPos >= G_WIDTH - a)
			k.setXPos(a);
		if(xPos <= a)
			k.setXPos( G_WIDTH - a);
	  	if(yPos >= G_HEIGHT - a)
			k.setYPos(a);
		if(yPos <= a)
			k.setYPos( G_HEIGHT - a);

		erg = checkCol(k);

		return erg;
	}

	public boolean checkCol( Kurve k ) {
		boolean erg = false;

		int xPos = k.getXPos();
		int yPos = k.getYPos();
			
		try {
			for(int y = yPos; y < ( yPos + 4 ); y++ ) {
				for(int x = xPos; x<(xPos+4); x++) {
					if (field[y][x] == true){
						erg = true;
						break;
					}
					else{
						field[y][x]=true;
					}
						
				}
			}
		} catch(Exception npe){
		}
		return erg;
	}

	public void run() {
		while (running || ended) {
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
			}

			for(int i=0; i < player.length; i++) {
				player[i].kurve.move();

				if ( player[i].isLpressed() ){
					player[i].kurve.moveLeft();
				}
				else if ( player[i].isRpressed() ){
					player[i].kurve.moveRight();
				}
			}
			paint(getGraphics());
		}
	}

	public void drawField(Graphics g) {
		g.setColor(Color.white);
  		g.fillRect(0, 0, WIDTH, HEIGHT);
  		g.setColor(Color.black);
  		g.fillRect(3, 3, G_WIDTH, G_HEIGHT);
	}

	public void drawCurves(Graphics g) {
		for(int i = 0; i<player.length; i++) {
			g.setColor(player[i].getFarbe());	

			if((!player[i].kurve.isDead())&&(!player[i].kurve.hasHole())){	
				if(isCollision(player[i].kurve)){
					player[i].kurve.kill();
					statistik.setDead(i);
					statistik.increasePoints(player);
					drawScores(g);
				} else {
					g.fillRect(player[i].kurve.getXPos(),player[i].kurve.getYPos(),4,4);
				}
			}
		}
		g.setColor(Color.black);
	}

	public void drawScores(Graphics g) {
		g.setColor(Color.gray );
		g.fillRect(682,3,115,G_HEIGHT);

  		int[] points = statistik.getPoints(); 

		for (int i = 0; i < player.length; i++){
			g.setColor(player[i].getFarbe());
			g.setFont(new Font("SANS_SERIF", Font.BOLD,16));
			g.drawString(player[i].getName(),690,30+(i*50));
			g.drawString(points[i]+ " pts",690,50+(i*50));

			if(points[i] >= maxScore) 
			{
				ended = true;
				running = false;
			}
		}

		if(statistik.getPlayersAlive()==1){
			next_round = true;
		}
	}

	public void drawChatEntry(Graphics g) {
	}

	public void drawChatHistory(Graphics g) {
	}

	public void drawStatistics(Graphics g) {
		int[] ranking = statistik.getPoints();
		g.setColor(Color.black);
  		g.fillRect(0, 0, WIDTH, HEIGHT);
  		g.setColor(Color.white);
  		g.setFont(new Font("SANS_SERIF", Font.BOLD,72));
  		g.drawString(" Spielstand",200,100);

		for(int i=0; i<player.length; i++){
			g.setColor(player[i].getFarbe());
			g.setFont(new Font("SANS_SERIF", Font.BOLD,36));
			g.drawString(player[i].getName(),200,175+(i*75));
			g.drawString(ranking[i]+ " pts",500,175+(i*75));	
		}
	}

	public void paint(Graphics g) {
		if (next_round){
			nextRound();
			next_round = false;
			started = true;
			try {
				th.sleep(1000);
			}catch(Exception e){	
			}
		}
		
		else if(ended){
			drawStatistics(g);
			ended = false;
		}
		
		else if(started){
			drawField(g);
			drawScores(g);
			started = false;
			running = true;
		}

		else if(running){
			drawCurves(g);	
		}
	}
}