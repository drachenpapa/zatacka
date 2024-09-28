// Steuerungs-Methoden für Zatacka
// Henning Wrede, Matthias Junge & René Marksteiner

package de.neusta.game;

import java.awt.event.*;

	/**
	 * This Class controls the KeyEvents and calls the correct methods.
	 * It controls the movement of the Curves.
	 * @version 1.0
	 * @author 	René Marksteiner
	 */

public class Steuerung extends KeyAdapter
{
	/** 
	 * Reference to an object of the class Zatacka.
	 * With this reference you can call the methods for the Curve-movement.
	 */
	private Zatacka ref;

	/** 
 	 * Creates an Controlobject for controlling the Curves.		
     */
	public Steuerung(Zatacka ref) {
		this.ref = ref;
	}
  
	/** Reacts to a key which is pressed. */
	public void keyPressed(KeyEvent e) {
		//gedrückte Taste
		char c = e.getKeyChar();
		//alle Spieler durchgehen
		for(int i=0;i<ref.player.length;i++) {
			//wenn linke Taste gedrückt wurde
			if(ref.player[i].getLinks()==c){
				//Flag setzen, dass linke Taste gedrückt wurde
				ref.player[i].setLpressed(true);			
			}
			//wenn rechte Taste gedrückt wurde
			else if(ref.player[i].getRechts()==c) {
				//Flag setzen, dass rechte Taste gedrückt wurde
				ref.player[i].setRpressed(true);
			}
			//wenn Escape gedrückt wurde 
			else if(KeyEvent.VK_ESCAPE==c) {
				//Spiel beenden
				ref.quit();
			}
		}
	}
	
	/** Reacts of the release of a key. */
	public void keyReleased(KeyEvent e) {
		//losgelassene Taste
		char c = e.getKeyChar();
		//alle Spieler durchgehen
		for(int i=0;i<ref.player.length;i++) {
			//wenn linke Taste losgelassen wurde
			if(ref.player[i].getLinks()==c){
				//Flag setzen, dass linke Taste losgelassen wurde
				ref.player[i].setLpressed(false);
			}
			//wenn rechte Taste losgelassen wurde
			else if(ref.player[i].getRechts()==c) {
				//Flag setzen, dass rechte Taste losgelassen wurde
				ref.player[i].setRpressed(false);
			}
		}
	}
}