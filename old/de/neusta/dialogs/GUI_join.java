// GUI für Zatacka
// Henning Wrede, Matthias Junge & René Marksteiner

package de.neusta.dialogs;

import java.awt.*;
import javax.swing.*;
import de.neusta.game.*;
import java.awt.event.*;

	/**
	 * The graphical user interface for setting some settings before joining a server hosted game.
	 * @author Matthias Junge
	 * @version 1.0
	 */

public class GUI_join extends GUI implements ActionListener
{
	public static int max_players = 6;
	// private Variablen, welche nur intern "gesehen" werden	
	private GUI_server server_instance;
	private JLabel all, ip;
	private JLabel leerfeld1, leerfeld2;
	private JPanel pannel;
	private JTextField ipfield;
	private String ip_eingabe;
     
    public GUI_join()
    {
    	super(max_players);
      	// Fenster- und Paneleinstellungen
      	pannel = drawRaw();
        pannel.setPreferredSize(new Dimension(600, 400));
        setSize(600, 400);
        setResizable(false);
        setLocation(300, 200);
        pannel.setLocation(400,200);    
        pannel.setLayout( new FlowLayout(FlowLayout.LEFT,6,6 ));
        pannel.setBackground( new Color(205,205,205) );
        	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Beim Klick aufs X wird das Fenster geschlossen

        for ( int i = 0; i < max_players; i++ ){
        	checks[i].addActionListener(this);
        	colors[i].addActionListener(this);
        	Lbuttons[i].addActionListener(this);
        	Rbuttons[i].addActionListener(this);
        }
        
        // weitere Instanzen
        checks[max_players].addActionListener(this);
        loadDefaults.addActionListener(this);		

		ip = new JLabel(" IP-Adress Server: ");
		ip.setPreferredSize( new Dimension(125, 25) );
		pannel.add(ip);
				
		ipfield = new JTextField();
		ipfield.setPreferredSize( new Dimension(120, 25) );
		pannel.add(ipfield);
				
		leerfeld1 = new JLabel("");
		leerfeld1.setPreferredSize( new Dimension(1120, 25) );
		pannel.add(leerfeld1);				
				
		leerfeld2 = new JLabel("");
		leerfeld2.setPreferredSize( new Dimension(220, 25) );
		pannel.add(leerfeld2);
				
		start = new JButton("Join server !");
		start.setPreferredSize( new Dimension(120, 25));
		start.addActionListener(this);
		pannel.add(start);	
				
		server_instance = new GUI_server();     	 // GUI_server Instanz erzeugen um auf die Methoden der Klasse
		server_instance.dispose();					// zugreifen zu können
				
		// Hinzufügen des Panels aufs Fenster und Sichtbarmachen des Fensters																														
		add(pannel);
        setVisible(true);
    }
			
    /**
     * Catches ActionEvents caused by elements binded with this Listener.
     */      
    // ActionListener, welcher die ActionEvents von den GUI-Elementen abfängt      
    public void actionPerformed(ActionEvent e)
    {				
    	if ( e.getSource() == start )
    	{						
    		ip_eingabe = server_instance.getText(ipfield);																													
    		generatePlayers( colors, true );
    		Zatacka app = new Zatacka( spieler, 0 );
    		saveOptions();			
    	}
    	  
    	else if ( e.getSource() == loadDefaults ){
    		loadDefaultOptions();
    	}
				
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
    							Lbuttons[x].setText( KeyEvent.getKeyText(e.getKeyCode()) );
    							lbutton_keys[x] = e.getKeyChar();
    							Lbuttons[x].removeKeyListener(this);
    						}
    					});	
    		}
    		
    		// Falls jemand den rechten Steuerkonfigurationsbutton Rbuttons[i] gedrückt hat...
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
							
    		else if ( e.getSource() == checks[i] )
    		{
    			if (  checks[i].isSelected() )
    			{
    				// Spieler mit der Nummer i + 1 wird enabled
    				enableRow(i);	
    			}
    			
    			else if ( ! checks[i].isSelected() )
    			{
    				// Spieler mit der Nummer i + 1 wird disabled
    				disableRow(i);
    				checks[max_players].setSelected(false);	
    			}
    		}
    		  
    		else if ( e.getSource() == checks[max_players] )
    		{	
    			if ( checks[max_players].isSelected() == true )
    			{
    				// alles enabled
    				checks[i].setSelected(true);	
    				enableRow(i);
    			}
    			  
    			else if ( checks[max_players].isSelected() == false)
    			{
    				// alles disabled
    				checks[i].setSelected(false);	
    				disableRow(i);																
    			}
    		}
    	}
    }	
}		
