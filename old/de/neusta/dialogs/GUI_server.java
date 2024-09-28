// GUI für Zatacka
// Henning Wrede, Matthias Junge & René Marksteiner

package de.neusta.dialogs;

import java.awt.*;
import javax.swing.*;
import de.neusta.game.*;

import java.awt.event.*;

	/**
	 * The graphical user interface for setting some settings before hosting a server game.
	 * @author Matthias Junge
	 * @version 1.0
	 */

public class GUI_server extends GUI implements ActionListener
{
	// Statische Variablen; bleiben immer gleich
	private static int max_players = 6;
	// Nicht statische Variablen; werden dynamisch veraendert
	// private Variablen, welche nur intern "gesehen" werden
	private JLabel text_speed, text_anzahl, leerfeld1, leerfeld2, leerfeld3; // all, 
	private JPanel pannel;
	private JSpinner anzahl; // speed,   		
		
	// Konstruktor
	public GUI_server()
	{
  		super(max_players); // Namensuebergabe an Ueberklasse
  		pannel = drawRaw();						// erzeuge JPanel
  		pannel.setPreferredSize(new Dimension(600, 400));  // setzt die bevorzugte Groesse
  		setSize(600, 400);      // setzt Groesse des Frames (Fensters)
  		setResizable(false);    // verhindert es, dass das Fenster maximiert werden kann
  		setLocation(300, 200);  // Fensterplatzierung auf Bilschirm
  		pannel.setLocation(400,200); 
        
  		pannel.setLayout( new FlowLayout(FlowLayout.LEFT,6,6 )); // Festlegung des Layoutmanagers
  		pannel.setBackground( new Color(205,205,205) );  				// Hintergrundfabre wird gesetzt (helles Grau)
        	
  		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     // Beim Klick auf das Schließen-X, schließt sich die Anwendung

  		// bindet die einzelnen Einstellungselemente an ActionListener
  		for ( int i = 0; i < max_players; i++ ){
  			checks[i].addActionListener(this);
  			colors[i].addActionListener(this);
  			Lbuttons[i].addActionListener(this);
  			Rbuttons[i].addActionListener(this);
  		}
			        
  		// Hier wird die "All"-Checkbox erzeugt, mit dessen Hilfe alle Checkboxen auf einmal aktiviert werden können
  		checks[max_players].addActionListener(this);
  		loadDefaults.addActionListener(this);
			
  		// Hier wird ein JSpinner erzeugt, mit dessen Hilfe die Spielgeschwindigkeit eingestellt wird				
  		speed = new JSpinner( new SpinnerNumberModel( 3,1,5,1 ) ); // ( Startwert, von, bis, Größe der Zwischenschritte )
  		speed.setPreferredSize( new Dimension( 40, 25 ) ); // Setzen der Größe
				
  		text_speed = new JLabel("Speed-Level:"); // Das zum JSpinner dazugehörige Label wir erzeugt
  		text_speed.setPreferredSize(new Dimension(120, 25)); // und die Größe gesetzt
  		
  		// Nun wird beides dem JPanel hinzugefügt
  		pannel.add( text_speed );
  		pannel.add( speed );	
							
  		// Leerfelder zur Strukturierung des Layouts
  		leerfeld1 = new JLabel("");
  		leerfeld1.setPreferredSize( new Dimension(400, 25) );
  		pannel.add(leerfeld1);
				
  		// Label für den JSpinner "Anzahl der Spieler"
  		text_anzahl = new JLabel("Spieler auf Server:");
  		text_anzahl.setPreferredSize( new Dimension(120, 25) );
  		pannel.add(text_anzahl);
				
  		// JSpinner für Anzahl der Spieler
  		anzahl = new JSpinner( new SpinnerNumberModel( 6, 2, max_players, 1 ) );
  		anzahl.setPreferredSize( new Dimension( 40, 25 ) );
  		anzahl.addChangeListener(new JSpinnerListener(this)); // Muss noch geregelt werden mit dem ActionListener für den JSpinner
  		pannel.add(anzahl);
				
  		// Leerfelder zur Strukturierung des Layouts
  		leerfeld2 = new JLabel("");
  		leerfeld2.setPreferredSize( new Dimension(1000, 25) );
  		pannel.add(leerfeld2);
				
  		// Leerfelder zur Strukturierung des Layouts
  		leerfeld3 = new JLabel("");
  		leerfeld3.setPreferredSize( new Dimension(220, 25) );
  		pannel.add(leerfeld3);				
		
  		// Erzeugen und Hinzufügen des Start-Buttons
  		start = new JButton("Spiel starten !");
  		start.setPreferredSize( new Dimension(120, 25));
  		start.addActionListener(this);
  		pannel.add(start);	
						
  		add(pannel); // Hinzufügen des JPanels aufs Frame
  		setVisible(true); // Das Fenster (Frame) sichtbar machen
	}

// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
//
// METHODEN
//
// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
      
      
// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
      
	/**
	 * Sets the checkbox enabled.
	 */            
	public void setCheckEnable( int index )
	{
		checks[index].setEnabled(true);
	}
      
// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	
	/**
	 * Sets the checkbox unchecked and the parameter-elementes right from it disabled.
	 */      
	public void setUncheck( int index )
	{
		checks[index].setSelected(false);
		players[index].setEnabled(false);
		textfields[index].setEditable(false);
		colors[index].setEnabled(false);
		Lbuttons[index].setEnabled(false);
		Rbuttons[index].setEnabled(false);	
	}
      
// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
      
	/**
	 * Sets the checkbox checked and the parameter-elementes right from it enabled.
	 */      
	public void setCheck( int index )
	{
		checks[index].setSelected(true);
		players[index].setEnabled(true);
		textfields[index].setEditable(true);
		colors[index].setEnabled(true);
		Lbuttons[index].setEnabled(true);
		Rbuttons[index].setEnabled(true);
	}
     	
// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&      
      
	/**
	 * Getting the number shown in the JSpinner: anzahl.
	 * @return The number of players shown in the JSpinner: anzahl.
	 */      
	public int getAnzahl()
	{
		return Integer.parseInt( anzahl.getValue().toString() );
	}
      
// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
      
	/**
	 * Getting information about a checkbox wheather it's checked or not.
	 * @return Boolean wheather the checkbox at the index "index" is checked.
	 */            
	public boolean isChecked( int index )
	{
		return checks[index].isSelected();
	}
      
// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
      
	/**
	 * Getting the text written in the TextField.
	 * @return The text in TextField.
	 */       
	public String getText( JTextField TextField )
	{
		return TextField.getText();
	}
      
// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

	/**
	 * Getting the color from the color button chosen from the user.
	 * @return The color from the button.
	 */			
	public Color getColor( JButton cbutton )
	{
		return cbutton.getBackground();
	}
      
// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
			
	/**
	 * Getting the left control key.
	 * @return The left control key.
	 */			
	public String getLeftKey( JButton kbutton )
	{
		return kbutton.getText();
	}
	
// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

	/**
	 * Getting the right control key.
	 * @return The right control key.
	 */			
	public String getRightKey( JButton kbutton )
	{
		return kbutton.getText();
	}			
			
// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

	/**
	 * Getting the number of the maximum of players.
	 * @return The number of the maximum of players.
	 */			
	public int getMaxPlayers(  )
	{
		return max_players;
	}			
			
// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
      
      
	// Methode zum Abfangen vom ActionListener ausgeführten ActionEvents
	/**
	 * Catches ActionEvents caused by elements binded with this Listener.
	 */      
	public void actionPerformed(ActionEvent e)
	{
		if ( e.getSource() == start )
		{
			saveOptions();						
			generatePlayers( colors, false );//getChecks(checks, max_players), getNames(), colors, true );
	  		Zatacka app = new Zatacka( spieler, Integer.parseInt( speed.getValue().toString() ) );
		}
				
		else if ( e.getSource() == loadDefaults ){
			loadDefaultOptions();
		}
				
		// Mit Hilfe dieser For-Schleife wird wie beim Generieren der Elemente
		// auch bei den Events iterativ auf die Elemente in ihrem Array zugegriffen, um somit alle
		// erfassen zu können...
		for(int i = 0; i < max_players; i++)
		{
			// Falls jemand auf den Farbauswahlbutton colors[i] klickt...
			if (e.getSource() == colors[i])
			{
				// ... wird versucht ein Farbauswahldialog zu öffnen und mit ausgewählter Farbe
				// den Hintegrund des Knopfes zu bedecken
				try
				{
					Color c = JColorChooser.showDialog(null,"choose a color",colors[i].getBackground());
					colors[i].setBackground(c);
				}
				// Falls dieses nicht klapp ( z.B. wenn jemand den Farbauswahldialog abbricht), wird der Hintergund 
				// des Buttons auf ein Default-Farbwert gesetzt
				catch(NullPointerException npe)
				{
					colors[i].setBackground(new Color(215, 215, 235));
				}
			}
						
			// Falls jemand den linken Steuerkonfigurationsbutton Lbuttons[i] gedrückt hat...
			else if ( e.getSource() == Lbuttons[i] )
			{
				// ... wird gewartet bis eine Taste gedrückt wird ...
				Lbuttons[i].requestFocus(); 
				final int x = i;
				Lbuttons[i].addKeyListener(
						new KeyAdapter()
						{
							public void keyReleased(KeyEvent e)
							{
								// und diese dann abgefangen und auf den Button "geschrieben"
								Lbuttons[x].setText(KeyEvent.getKeyText(e.getKeyCode()));
								lbutton_keys[x] = e.getKeyChar();
								Lbuttons[x].removeKeyListener(this);
							}
						});			
			}
						
			// Falls jemand den rechten Steuerkonfigurationsbutton Lbuttons[i] gedrückt hat...
			else if ( e.getSource() == Rbuttons[i] )
			{
				// ... wird gewartet bis eine Taste gedrückt wird ...
				Rbuttons[i].requestFocus();
				final int x = i;
				Rbuttons[i].addKeyListener(
						new KeyAdapter()
						{
							public void keyReleased(KeyEvent e)
							{
								// und diese dann abgefangen und auf den Button "geschrieben"
								Rbuttons[x].setText(KeyEvent.getKeyText(e.getKeyCode()));	
								rbutton_keys[x] = e.getKeyChar();
								Rbuttons[x].removeKeyListener(this);
							}
						});			
			}
			
			// Falls eine Checkbox betätigt wird, ...
			else if ( e.getSource() == checks[i] )
			{
				// Wenn ein Spieler mit Hilfe der Checkboxen an bzw. abgewählt wurde,
				// wird überprüft, ob danach immer noch minimum zwei Spieler angewählt sind, wenn dies der Fall ist
				// wird der Start-Button enabled, wenn dieser diabled war, sonst, wenn nach dem Betätigen einer Checkbox, nicht
				// mehr genug Spieler angewählt sind (0 oder 1), dann wird der Start-Button diabled.
				if ( getChecks( checks, max_players ) == 0 ){
					start.setEnabled(false);
				}
				else if ( getChecks( checks, max_players ) > 0 ){
					start.setEnabled(true);
				}	
    			  
				// ... sie nach dem Betätigen gecheckt ist...
				if ( isChecked(i) )
				{	
					// ... und die Anzahl der gecheckten Checkboxen gleich der Anzahl der Spieler
					// ausgewählt im JSpinner ist, dann...
					if ( getChecks( checks, max_players ) == getAnzahl() )
					{
						// ... werden alle Checkboxen, die nicht gecheckt sind, 
						// disabled und können nicht mehr angewählt werden
						for ( int k = 0; k < max_players; k++ )
						{
							if ( ! isChecked(k) )
							{
								checks[k].setEnabled(false);
							}
						}
					}
					// Spieler mit der Nummer i + 1 wird enabled
					enableRow(i);																										
				}
    			  
				else if ( ! isChecked(i) )
				{
					if ( getChecks( checks, max_players ) < getAnzahl() )
					{
						for ( int k = 0; k < max_players; k++ )
						{
							if ( ! isChecked(k) )
							{
								checks[k].setEnabled(true);
							}
						}
					}	
					// Spieler mit der Nummer i + 1 wird disabled
					disableRow(i);
					checks[max_players].setSelected(false);	
				}
			}
										
			if ( e.getSource() == checks[max_players] )
			{	
				if ( isChecked(max_players) == true )
				{ 
					if ( getChecks( checks, max_players ) < getAnzahl() )
					{																						
						// alles enabled
						checks[i].setSelected(true);
						enableRow(i);
					}						
				}
				
				else if ( isChecked(max_players) == false)
				{
					// alles disabled
					checks[i].setSelected(false);
					disableRow(i);																
				}	
							
				// Überprüfung, ob vielleicht kein Spieler angewählt wurde. Dann
				// wird der Start-Button disabled, wenn dieser disabled war, bzw. alle Spieler
				// mit Hilfe der ALL-Checkbox angewählt wurden, wird der Start-Button wieder anwählbar
				if ( getChecks( checks, max_players ) == 0 ){
					start.setEnabled(false);
				}
				else if ( getChecks( checks, max_players ) > 0 ){
					start.setEnabled(true);
				}
			}
		}
	}
}


