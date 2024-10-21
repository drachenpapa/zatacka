package de.zatacka.dialogs;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Predialog extends JFrame implements ActionListener
{
	private JButton lokal, server, join;
	private JLabel label, label1, label2, leerfeld;
	private JPanel pannel;

	public Predialog()
    {
		super( "Zatacka" );
      	pannel = new JPanel();
        pannel.setPreferredSize( new Dimension( 300, 200 ) );
        setSize( 300, 200 );
        setResizable( false );
        setLocation( 300, 200 );
        pannel.setLocation( 300,200 );  
        
        pannel.setLayout( new FlowLayout(FlowLayout.LEFT, 25, 10 ));
        pannel.setBackground( new Color(205,205,205) );
        	
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

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
        
        lokal = new JButton( "Lokal" );
        lokal.addActionListener( this );
        pannel.add( lokal );
        
        server = new JButton( "Host" );
        server.addActionListener( this );
        server.setEnabled( false );
        pannel.add( server );
        
        join = new JButton( "Client" );
        join.addActionListener( this );
        join.setEnabled( false );
        pannel.add( join );
        
        add( pannel );
        setVisible( true );
    }

    public static void main( String[] args )
    {
    	Predialog pd = new Predialog();
    }

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