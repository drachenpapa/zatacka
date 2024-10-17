package de.zatacka.game;

import java.awt.event.*;

public class Steuerung extends KeyAdapter
{
	private Zatacka ref;

	public Steuerung(Zatacka ref) {
		this.ref = ref;
	}

	public void keyPressed(KeyEvent e) {
		char c = e.getKeyChar();

		for(int i=0;i<ref.player.length;i++) {
			if(ref.player[i].getLinks()==c){
				ref.player[i].setLpressed(true);			
			}
			else if(ref.player[i].getRechts()==c) {
				ref.player[i].setRpressed(true);
			} 
			else if(KeyEvent.VK_ESCAPE==c) {
				ref.quit();
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		char c = e.getKeyChar();

		for(int i=0;i<ref.player.length;i++) {
			if(ref.player[i].getLinks()==c){
				ref.player[i].setLpressed(false);
			}
			else if(ref.player[i].getRechts()==c) {
				ref.player[i].setRpressed(false);
			}
		}
	}
}