// Pre-Dialog f�r Zatacka
// Henning Wrede, Matthias Junge & Ren� Marksteiner

package de.drachenpapa.zatacka.dialogs;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

	/**
	 * Pre-Dialog for the game Zatacka.
	 * @author Henning Wrede
	 * @version 1.0
	 */

public class Predialog extends JFrame implements ActionListener
{
	// private Variablen, welche nur intern "gesehen" werden
	private JButton lokal, server, join;
	private JLabel label, label1, label2, leerfeld;
	private JPanel pannel;
	
	// Konstruktor
	public Predialog()
    {
		super( "Zatacka" ); // Namens�bergabe an �berklasse
      	pannel = new JPanel(); // erzeuge JPanel
        pannel.setPreferredSize( new Dimension( 300, 200 ) ); // setzt die bevorzugte Gr��e
        setSize( 300, 200 ); // setzt Gr��e des Frames (Fensters)
        setResizable( false ); // verhindert es, dass das Fenster maximiert werden kann
        setLocation( 300, 200 ); // Fensterplatzierung auf Bilschirm
        pannel.setLocation( 300,200 );  
        
        pannel.setLayout( new FlowLayout(FlowLayout.LEFT, 25, 10 )); // Festlegung des Layoutmanagers
        pannel.setBackground( new Color(205,205,205) ); // Hintergrundfabre wird gesetzt (helles Grau)
        	
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); // Beim Klick auf das Schlie�en-X, schlie�t sich die Anwendung

        // die Einleitung f�r den User, was er machen soll
        label = new JLabel( "Willkommen bei Zatacka!" );
        label.setPreferredSize( new Dimension( 200, 12 ) );
        pannel.add( label );
        label1 = new JLabel( "W�hlen Sie bitte aus," );
        label1.setPreferredSize( new Dimension( 300, 12 ) );
        pannel.add( label1 );
        label2 = new JLabel( "was f�r ein Spiel Sie Starten m�chten" );
        label2.setPreferredSize( new Dimension( 300, 12 ) );
        pannel.add( label2 );
        leerfeld = new JLabel( "" );
        leerfeld.setPreferredSize( new Dimension( 300, 40 ) );
        pannel.add( leerfeld );
        
        lokal = new JButton( "Lokal" ); // der Button f�r das lokale Spiel
        lokal.addActionListener( this ); // Hinzuf�gen des ActionListeners, der auf Aktionen des Users mit diesem Element reagiert
        pannel.add( lokal );
        
        server = new JButton( "Host" ); // Button f�r das Spielen im Netzwerk als Server/Host
        server.addActionListener( this ); // Hinzuf�gen des ActionListeners, der auf Aktionen des Users mit diesem Element reagiert
        server.setEnabled( false ); // noch nicht fertig implementiert
        pannel.add( server );
        
        join = new JButton( "Client" ); // der Button damit man als Client spielt und zu einem Server joinen kann
        join.addActionListener( this ); // Hinzuf�gen des ActionListeners, der auf Aktionen des Users mit diesem Element reagiert
        join.setEnabled( false ); // noch nicht fertig implementiert
        pannel.add( join );
        
        add( pannel ); // Hinzuf�gen des JPanels aufs Frame
        setVisible( true ); // Das Fenster (Frame) sichtbar machen
    }

	// Hauptmethode...
    public static void main( String[] args )
    {
    	Predialog pd = new Predialog();
    }
      
    // &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
    // METHODEN
    // &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
      
    // Methode, die je nachdem welcher Knopf gedr�ckt wurde, die richtige GUI aufruft
    /**
     * Shows the correct graphical user interface after a button is pressed.
     */
    public void actionPerformed( ActionEvent e )
    {
    	if ( e.getSource() == lokal )
    	{
    		lokal.setEnabled( false );
       		join.setEnabled( false );
       		server.setEnabled( false );
       		GUI_lokal gl = new GUI_lokal();
       	}
    	else if ( e.getSource() == server )
    	{
       		lokal.setEnabled( false );
       		join.setEnabled( false );
       		server.setEnabled( false );
    	}
    	else if ( e.getSource() == join )
    	{
       		lokal.setEnabled( false );
       		join.setEnabled( false );
       		server.setEnabled( false );
       	}
    }
}