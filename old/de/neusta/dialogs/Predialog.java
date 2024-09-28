// Pre-Dialog für Zatacka
// Henning Wrede, Matthias Junge & René Marksteiner

package de.neusta.dialogs;

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
		super( "Zatacka" ); // Namensübergabe an Überklasse
      	pannel = new JPanel(); // erzeuge JPanel
        pannel.setPreferredSize( new Dimension( 300, 200 ) ); // setzt die bevorzugte Größe
        setSize( 300, 200 ); // setzt Größe des Frames (Fensters)
        setResizable( false ); // verhindert es, dass das Fenster maximiert werden kann
        setLocation( 300, 200 ); // Fensterplatzierung auf Bilschirm
        pannel.setLocation( 300,200 );  
        
        pannel.setLayout( new FlowLayout(FlowLayout.LEFT, 25, 10 )); // Festlegung des Layoutmanagers
        pannel.setBackground( new Color(205,205,205) ); // Hintergrundfabre wird gesetzt (helles Grau)
        	
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); // Beim Klick auf das Schließen-X, schließt sich die Anwendung

        // die Einleitung für den User, was er machen soll
        label = new JLabel( "Willkommen bei Zatacka!" );
        label.setPreferredSize( new Dimension( 200, 12 ) );
        pannel.add( label );
        label1 = new JLabel( "Wählen Sie bitte aus," );
        label1.setPreferredSize( new Dimension( 300, 12 ) );
        pannel.add( label1 );
        label2 = new JLabel( "was für ein Spiel Sie Starten möchten" );
        label2.setPreferredSize( new Dimension( 300, 12 ) );
        pannel.add( label2 );
        leerfeld = new JLabel( "" );
        leerfeld.setPreferredSize( new Dimension( 300, 40 ) );
        pannel.add( leerfeld );
        
        lokal = new JButton( "Lokal" ); // der Button für das lokale Spiel
        lokal.addActionListener( this ); // Hinzufügen des ActionListeners, der auf Aktionen des Users mit diesem Element reagiert
        pannel.add( lokal );
        
        server = new JButton( "Host" ); // Button für das Spielen im Netzwerk als Server/Host
        server.addActionListener( this ); // Hinzufügen des ActionListeners, der auf Aktionen des Users mit diesem Element reagiert
        server.setEnabled( false ); // noch nicht fertig implementiert
        pannel.add( server );
        
        join = new JButton( "Client" ); // der Button damit man als Client spielt und zu einem Server joinen kann
        join.addActionListener( this ); // Hinzufügen des ActionListeners, der auf Aktionen des Users mit diesem Element reagiert
        join.setEnabled( false ); // noch nicht fertig implementiert
        pannel.add( join );
        
        add( pannel ); // Hinzufügen des JPanels aufs Frame
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
      
    // Methode, die je nachdem welcher Knopf gedrückt wurde, die richtige GUI aufruft
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
    		GUI_server gs = new GUI_server();
    	}
    	else if ( e.getSource() == join )
    	{
       		lokal.setEnabled( false );
       		join.setEnabled( false );
       		server.setEnabled( false );
    		GUI_join gj = new GUI_join();
       	}
    }
}