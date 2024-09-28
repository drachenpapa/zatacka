// Methoden für die Kurven
// Henning Wrede, Matthias Junge & René Marksteiner

package de.neusta.game;

import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

/**
 * This class is for the game-GUI. It contains everything for display.
 * @version 1.0
 * @author 	René Marksteiner
 */

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

	
	/**
	 * Constructs a Zatacka-instance, which controls the display and logic.
	 */
	public Zatacka( Spieler player[], int speed ) {
		//Größe Canvas
		setBounds(0,0,WIDTH,HEIGHT);
		//Hintergrundfarbe Canvas
		setBackground(Color.black);
		fm = new JFrame();
		//Frame ohne Titelleiste..
		fm.setUndecorated(true);
		JPanel pan = (JPanel) fm.getContentPane();
		pan.setLayout(null);
		//Panel zum Canvas hinzufügen
		pan.add(this);
		//Frame-Einstellungen
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
       	GraphicsDevice device = env.getDefaultScreenDevice();
       	//Vollbild
       	device.setFullScreenWindow(fm);
		//speichern des alten Displaymodus
       	old=device.getDisplayMode();
       	//setzen des neuen Displaymodus
       	device.setDisplayMode(new DisplayMode(WIDTH, HEIGHT, old.getBitDepth(), old.getRefreshRate()));
		//MausCursor verschwinden lassen
		BufferedImage cur = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
		setCursor(getToolkit().createCustomCursor(cur,new Point(0,0),""));
		//Fenster anzeigen
		fm.setVisible(true);
		//Tastenverarbeitung
		fm.addKeyListener(new Steuerung(this));
		//Spielerobjekte werden von der GUI übergeben

		this.player = player;
		this.statistik = new Statistik(player.length);			
		
		this.speed = 10*(6-speed);
		maxScore = (player.length-1)*10;

		//Schaffen eines neuen Threads, in dem das Spiel läuft
		th = new Thread (this);
		//Starten des Threads
		th.start();
	}
	
	/**
	 * Restores the graphical settings and closes the application.
	 */
	public void quit() {
		running = false;
		//Wiederherstellung des alten Displaymodus
		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setDisplayMode(old);
		//schließt das Fenster
		fm.dispose();
		System.exit(0);
	}

	/**
	 * Starts a new round.
	 */
	public void nextRound(){
		//Spielfeld zurücksetzen
		field = new boolean[G_HEIGHT][G_WIDTH];		
		
		for ( int i =0; i < player.length; i++ ){
			player[i].kurve =   new Kurve(Math.round(Math.random()*Zatacka.WIDTH)+100,
		            Math.round(Math.random()*Zatacka.HEIGHT)+100,
		            Math.random()*360,
		            Math.round(Math.random() * 10) + 1);
		}
		
		statistik.setAllAlive();
		
	}
	
	/**
	 * Checks if the curves got a collision.
	 */
	public  boolean isCollision(Kurve k) {
		//Koordinaten
		boolean erg;
		
		//System.out.println("Schreatth-Stelle-1 " + Thread.currentThread() );
		
		int xPos = k.getXPos();
		int yPos = k.getYPos();
		

		//Neupositionierung der Kurve beim Antreffen an der Begrenzung
		int a = 4;
		if(xPos >= G_WIDTH - a)
			k.setXPos(a);
		if(xPos <= a)
			k.setXPos( G_WIDTH - a);
	  	if(yPos >= G_HEIGHT - a)
			k.setYPos(a);
		if(yPos <= a)
			k.setYPos( G_HEIGHT - a);
		//System.out.println("Schreatth-Stelle-2 " + Thread.currentThread() );
		erg = checkCol(k);
		//System.out.println("Schreatth-Stelle-3 " + Thread.currentThread() );

		return erg;
	}

	/**
	 * Checks whether the curve got a collision with another one.
	 */
	public boolean checkCol( Kurve k ) {
		
		boolean erg = false;
		//aktuelle Positionen
		int xPos = k.getXPos();
		int yPos = k.getYPos();
			
		try {		
			//Kolissionserkennung
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

	/**
	 * Represents the game-loop.
	 */
	public void run() {
		//solange ein Spiel läuft ist läuft der Thread weiter
		
		while (running || ended) {
			//System.out.println("Schreatth-Stelle-run  " + Thread.currentThread() );
			try {
				//stoppen des Threads für die in den Klammern angegebene
				//Millisekunden --> Speed
				
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				//nichts machen
			}
			//weiterbewegen aller Kurven
			for(int i=0; i < player.length; i++) {
				player[i].kurve.move();

				if ( player[i].isLpressed() ){
					player[i].kurve.moveLeft();
				}
				else if ( player[i].isRpressed() ){
					player[i].kurve.moveRight();
				}

			}
			//updaten des Spiels
			paint(getGraphics());

		}
	}

	/** 
	 * The whole game-GUI containing the playing surface, the total points and
	 * optional Chatcomponenten will be drawn.
	 */
	public void drawField(Graphics g) {
  		//Rahmen
		g.setColor(Color.white);
  		g.fillRect(0, 0, WIDTH, HEIGHT);
  		//Spielfläche
  		g.setColor(Color.black);
  		g.fillRect(3, 3, G_WIDTH, G_HEIGHT);
	}

	/**
	 * All active curves will be drawn.
	 */
	public void drawCurves(Graphics g) {
		for(int i = 0; i<player.length; i++) {
			//Farbe der aktuellen Kurve holen
			g.setColor(player[i].getFarbe());	
			//nur zeichnen wenns die Kurve lebt und kein Loch hat
			if((!player[i].kurve.isDead())&&(!player[i].kurve.hasHole())){	
				//wenn eine Kollision vorgefallen ist
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

	/**
	 * The points a player got and his name will be shown in a separate area.
	 */
	public void drawScores(Graphics g) {
  		//Score-Bereich zeichnen
		g.setColor(Color.gray );
		g.fillRect(682,3,115,G_HEIGHT);
		//Punkte aller Spieler
  		int[] points = statistik.getPoints(); 
  		//Spielernamen und Punktestand zeichnen
		for (int i = 0; i < player.length; i++){
			g.setColor(player[i].getFarbe());
			g.setFont(new Font("SANS_SERIF", Font.BOLD,16));
			g.drawString(player[i].getName(),690,30+(i*50));
			g.drawString(points[i]+ " pts",690,50+(i*50));
			//wenn ein Spieler die höchstpunktzahl erreicht,
			//Flags setzen -> Spielstand zeichen
			if(points[i] >= maxScore) 
			{
				ended = true;
				running = false;
			}
		}
		//wenn nur noch ein Spieler
		//Flags setzen -> neue Runde
		if(statistik.getPlayersAlive()==1){
			next_round = true;
		}
	}

	/**
	 * Draws an Entrybox and shows the entered text.
	 */
	public void drawChatEntry(Graphics g) {
	}

	/**
	 * Shows the three last messages in an separate area.
	 */
	public void drawChatHistory(Graphics g) {
	}

	/**
	 * At the end of the game this method will draw the statistics.
	 */
	public void drawStatistics(Graphics g) {
		//Punkte aller Spieler
		int[] ranking = statistik.getPoints();
  		//schwarzen Bildschirm für das Ranking zeichnnen
		g.setColor(Color.black);
  		g.fillRect(0, 0, WIDTH, HEIGHT);
  		//Farbe und Schrifttyp setzen und anschließen Überschrift 
  		//zeichnen
  		g.setColor(Color.white);
  		g.setFont(new Font("SANS_SERIF", Font.BOLD,72));
  		g.drawString(" Spielstand",200,100);
  		//Spielernamen und Punktestand zeichnen
		for(int i=0; i<player.length; i++){
			g.setColor(player[i].getFarbe());
			g.setFont(new Font("SANS_SERIF", Font.BOLD,36));
			g.drawString(player[i].getName(),200,175+(i*75));
			g.drawString(ranking[i]+ " pts",500,175+(i*75));	
		}
	}

	/**
	 * Presents the game-area, the curves and the status informations.
	 */
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