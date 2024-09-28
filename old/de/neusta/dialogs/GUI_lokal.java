// GUI für Zatacka
// Henning Wrede, Matthias Junge & René Marksteiner

package de.neusta.dialogs;

import java.awt.*;
import javax.swing.*;
import de.neusta.game.*;
import java.awt.event.*;

	/**
	 * The graphical user interface for setting some settings before starting game.
	 * @author Matthias Junge
	 * @version 1.0
	 */

public class GUI_lokal extends GUI implements ActionListener
{
	
	// vordefinitionen der späteren instanzen
	private static int max_players = 6;
	private JPanel pannel;

	public GUI_lokal()
	{
		super(max_players);						// setzen des titels
		pannel = drawRaw();											// ein JPanel instantiieren
		pannel.setPreferredSize(new Dimension(600, 400));				// die größe des panels setzen
		setSize(600, 400);												// setze die größe des fensters
		setResizable(false);											// macht das fenster nicht maximierbar
		setLocation(300, 200);											// setzt die lokation des fensters
		pannel.setLocation(400,200);									// die anbindungspunkte des panels setzen
        
		pannel.setLayout( new FlowLayout(FlowLayout.LEFT,6,6 ));	// macht für das panel das layout
		pannel.setBackground( new Color(205,205,205) );				// setzt die hintergrundfarbe des panels
        	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				// setzt die default-schließ option
    
		for ( int i = 0; i < max_players; i++ ){
			checks[i].addActionListener(this);
			colors[i].addActionListener(this);
			Lbuttons[i].addActionListener(this);
			Rbuttons[i].addActionListener(this);
		}
        
		// weitere instanzen
    	checks[max_players].addActionListener(this);
		loadDefaults.addActionListener(this);
				
		speed = new JSpinner( new SpinnerNumberModel( 3,1,5,1 ) );
		speed.setPreferredSize( new Dimension( 40, 25 ) );
		text_speed = new JLabel("Speed-Level:");
		text_speed.setPreferredSize(new Dimension(90, 25));
				
		pannel.add( text_speed );
		pannel.add( speed );	
				
		leerfeld1 = new JLabel("");
		leerfeld1.setPreferredSize( new Dimension(1000, 25) );
		pannel.add(leerfeld1);
				
		leerfeld2 = new JLabel("");
		leerfeld2.setPreferredSize( new Dimension(220, 25) );
		pannel.add(leerfeld2);
				
		start = new JButton("Spiel starten !");
		start.addActionListener(this);
		start.setPreferredSize( new Dimension(120, 25));
		pannel.add(start);	

		checks[1].setEnabled(true);
		checks[1].setSelected(true);
		enableRow(1);
		
		add(pannel);																								// fügt das panel der gui hinzu
		setVisible(true);																						// lässt das fenster erscheinen
	}
	
	/**
	 * ActionListener that catches ActionEvents thrown by elements binded with the Listener.
	 */   
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == start ){
			// Hier muss das Spiel dann gestartet werden!
			saveOptions();
	  		generatePlayers( colors, false ); //getChecks(checks, max_players), getNames(), colors, true );
	  		Zatacka app = new Zatacka( spieler, Integer.parseInt( speed.getValue().toString() ) );
		}
	  	
		else if ( e.getSource() == loadDefaults ){
			loadDefaultOptions();
		}
	  
		for(int i = 0; i < max_players; i++)
		{
			if (e.getSource() == colors[i])
			{
				try
				{
					// auswahl der farbe eines spielers
					Color c = JColorChooser.showDialog(null,"choose a color",colors[i].getBackground());
					colors[i].setBackground(c);
				}
				catch(NullPointerException npe)
				{
					colors[i].setBackground(new Color(215, 215, 235));
				}
			}
						
			else if ( e.getSource() == Lbuttons[i] )
			{
				// einstellen des linken buttons
				Lbuttons[i].requestFocus();
				final int x = i;
				Lbuttons[i].addKeyListener(
						new KeyAdapter()
						{
							public void keyReleased(KeyEvent e)
							{
								Lbuttons[x].setText(KeyEvent.getKeyText(e.getKeyCode()));	
								lbutton_keys[x] = e.getKeyChar();
								Lbuttons[x].removeKeyListener(this);
							}
						}
				);			
			}
						
			else if ( e.getSource() == Rbuttons[i] )
			{
				// einstellen des rechten buttons
				Rbuttons[i].requestFocus();
				final int x = i;
				Rbuttons[i].addKeyListener(
						new KeyAdapter()
						{
							public void keyReleased(KeyEvent e)
							{
								Rbuttons[x].setText(KeyEvent.getKeyText(e.getKeyCode()));
								rbutton_keys[x] = e.getKeyChar();	
								Rbuttons[x].removeKeyListener(this);
							}
						}
				);			
			}
							
			else if ( e.getSource() == checks[i] )
			{
				// Wenn ein Spieler mit Hilfe der Checkboxen an bzw. abgewählt wurde,
				// wird überprüft, ob danach immer noch minimum zwei Spieler angewählt sind, wenn dies der Fall ist
				// wird der Start-Button enabled, wenn dieser diabled war, sonst, wenn nach dem Betätigen einer Checkbox, nicht
				// mehr genug Spieler angewählt sind (0 oder 1), dann wird der Start-Button diabled.
				if ( getChecks( checks, max_players ) <= 1 ){
					start.setEnabled(false);
				}
				else if ( getChecks( checks, max_players ) > 1 ){
					start.setEnabled(true);
				}
				
				// einen spieler hinzufügen
				if (  checks[i].isSelected() )
				{
					enableRow(i);		
				}
				
				// einen spieler deaktivieren
				else if ( ! checks[i].isSelected() )
				{
					disableRow(i);
					checks[max_players].setSelected(false);	
				}
			}
						
			else if ( e.getSource() == checks[max_players] )
			{	
				
				// wenn "All" aktiviert
				if ( checks[max_players].isSelected() == true )
				{
					checks[i].setSelected(true);
					enableRow(i);
				}
								
				// wenn "All" deaktiviert wird
				else if ( checks[max_players].isSelected() == false)
				{
					checks[i].setSelected(false);
					disableRow(i);																
				}
				// Überprüfung, ob vielleicht keine Spieler angewählt wurde. Dann
				// wird der Start-Button disabled, wenn dieser disabled war, bzw. alle Spieler
				// mit Hilfe der ALL-Checkbox angewählt wurden, wird der Start-Button wieder anwählbar
				if ( getChecks( checks, max_players ) <= 1 ){
					start.setEnabled(false);
				}
				else if ( getChecks( checks, max_players ) > 1 ){
					start.setEnabled(true);
				}
			}
		}
	}
}	