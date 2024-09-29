// GUI-Oberklasse f�r Zatacka-GUI's
// Henning Wrede, Matthias Junge & Ren� Marksteiner

package de.drachenpapa.zatacka.dialogs;

import de.drachenpapa.zatacka.engine.Player;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.io.*;

	/**
	 * Superclass which contains all the main elements for the GUIs.
	 * All classes with GUI in name inherit from it.
	 * @author Matthias Junge
	 * @version 1.0
	 */

public class GUI extends JFrame
{
	protected final int max_players;
	protected JButton[] colors, Lbuttons, Rbuttons;
	protected JButton start, loadDefaults;
	protected JCheckBox[] checks;
	protected JLabel all, text_speed, leerfeld1, leerfeld2;
	protected JLabel[] players;
	protected JSpinner speed;   
	protected JTextField[] textfields;
	protected char[] lbutton_keys;
	protected char[] rbutton_keys;
	protected char[] defaultrbutton_keys;
	protected char[] defaultlbutton_keys; 
	protected String[] keys;
	protected Player[] player;
	protected String[] names;
	private JPanel pannel;
	private Properties options;
	private BufferedReader in;
	private StringBuffer sbufferTrue;
	private Color[] defaultColors;
	
	/**
	 * Constructor: Constructs the panel which is returned by calling the method rawDraw().
	 */
	public GUI (int mplayers)
	{
		super("Zatacka");
		
		max_players = mplayers;
		pannel = new JPanel();
		players = new JLabel[max_players];
		colors = new JButton[max_players];
		textfields = new JTextField[max_players];
		Lbuttons = new JButton[max_players];
		Rbuttons = new JButton[max_players];
		loadDefaults = new JButton("Load Defaults");
		
		defaultColors = new Color[6];
		defaultColors[0] = new Color(255,0,0);
		defaultColors[1] = new Color(0,255,0);
		defaultColors[2] = new Color(0,0,255);
		defaultColors[3] = new Color(255,0,255);
		defaultColors[4] = new Color(0,255,255);
		defaultColors[5] = new Color(255,255,0);
		
		defaultlbutton_keys = new char[6];
		defaultlbutton_keys[0] = '1';
		defaultlbutton_keys[1] = 'y';
		defaultlbutton_keys[2] = 'b';
		defaultlbutton_keys[3] = ',';
		defaultlbutton_keys[4] = 'o';
		defaultlbutton_keys[5] = '2';
		
		defaultrbutton_keys = new char[6];
		defaultrbutton_keys[0] = 'q';
		defaultrbutton_keys[1] = 'x';
		defaultrbutton_keys[2] = 'n';
		defaultrbutton_keys[3] = '.';
		defaultrbutton_keys[4] = '0';
		defaultrbutton_keys[5] = '3';
		
		lbutton_keys = new char[6];
		lbutton_keys[0] = '1';
		lbutton_keys[1] = 'y';
		lbutton_keys[2] = 'b';
		lbutton_keys[3] = ',';
		lbutton_keys[4] = 'o';
		lbutton_keys[5] = '2';

		rbutton_keys = new char[6];
		rbutton_keys[0] = 'q';
		rbutton_keys[1] = 'x';
		rbutton_keys[2] = 'n';
		rbutton_keys[3] = '.';
		rbutton_keys[4] = '0';
		rbutton_keys[5] = '3';
				
		checks = new JCheckBox[max_players + 1];
	}
	
	/**
	 * Draws the gui elements on the panel and returns it.
	 * @return The finished drawn panel.
	 */
	public JPanel drawRaw()
	{
		for(int i = 0; i < max_players; i++)
		{	
			checks[i] = new JCheckBox();
			
											
			players[i] = new JLabel("Name Player " + (i + 1) + ":" );
			players[i].setPreferredSize(new Dimension(100,25));
			players[i].setEnabled(false);
						
			textfields[i] = new JTextField();
			textfields[i].setPreferredSize(new Dimension(150,25));
			textfields[i].setEditable(false);
						
			colors[i] = new JButton("Color");
			colors[i].setPreferredSize(new Dimension(70,25));
			
			colors[i].setEnabled(false);
			
			String lbutton_key=String.valueOf(lbutton_keys[i]);		
			Lbuttons[i] = new JButton(lbutton_key); 
			Lbuttons[i].setPreferredSize( new Dimension(100, 25) );
			
			Lbuttons[i].setEnabled(false);
						
			String rbutton_key=String.valueOf(rbutton_keys[i]);		
			Rbuttons[i] = new JButton(rbutton_key);
			Rbuttons[i].setPreferredSize( new Dimension(100, 25) );
			
			Rbuttons[i].setEnabled(false);
						
			players[0].setEnabled(true);
			checks[0].setSelected(true);
			textfields[0].setEditable(true);
			colors[0].setEnabled(true);
			Lbuttons[0].setEnabled(true);
			Rbuttons[0].setEnabled(true);
			
			pannel.add(checks[i]);
			pannel.add(players[i]);
			pannel.add(textfields[i]);
			pannel.add(colors[i]);
			pannel.add(Lbuttons[i]);
			pannel.add(Rbuttons[i]);
		}
        checks[max_players] = new JCheckBox();        					
		all = new JLabel("All");
		all.setPreferredSize(new Dimension(412,25));	
		loadDefaults.setPreferredSize(new Dimension(120, 25));
		
        pannel.add(checks[max_players]);
        pannel.add(Box.createVerticalStrut(50));
		pannel.add(all);
		pannel.add(loadDefaults);
		
		loadOptions();	
		return pannel;	
	}
	
	/**
	 * Saves the options adjusted by the user like names, colors, control keys etc. ...
	 */
	public void saveOptions(){
		options = new Properties();
		for ( int i = 0; i < max_players; i++ ){
			options.setProperty("checks" + i, "" + checks[i].isSelected());
			options.setProperty("name" + i, textfields[i].getText());
			options.setProperty("colors" + i, colors[i].getBackground().getRed() + "," + colors[i].getBackground().getGreen() + "," + colors[i].getBackground().getBlue() );
			options.setProperty("lbutton" + i, Lbuttons[i].getText());
			options.setProperty("rbutton" + i, Rbuttons[i].getText());
		}
		try{
		options.store(new FileOutputStream("options.ini"), "zatacka");
		}
		catch ( IOException ioe )
		{  }
	}
	
	/**
	 * Loading the options stored in a configuration file.
	 */
	public void loadOptions()
	{
		try{
			options = new Properties();
			try{
				options.load(new FileInputStream("options.ini"));
			}
			catch ( Exception e )
			{  }
			
			sbufferTrue = new StringBuffer("true");
		
			for ( int i = 0; i < max_players; i++ ){
				checks[i].setSelected(options.getProperty("checks" + i).contentEquals(sbufferTrue));
				if( checks[i].isSelected() ){
					enableRow(i);
				}
				else{
					disableRow(i);
				}				
				textfields[i].setText(options.getProperty("name" + i));
				String tmp = options.getProperty("colors" + i);
				String[] tmp2 = tmp.split(",");
				colors[i].setBackground( new Color( Integer.parseInt((tmp2[0])), Integer.parseInt(tmp2[1]), Integer.parseInt(tmp2[2]) ) );
				Lbuttons[i].setText(options.getProperty("lbutton" + i));
				Rbuttons[i].setText(options.getProperty("rbutton" + i));

				lbutton_keys[i] = options.getProperty("lbutton" + i).charAt(0);
				rbutton_keys[i] = options.getProperty("rbutton" + i).charAt(0);
			}
		}
		catch ( Exception e ){
			loadDefaultOptions( );
		}
	}
	
	/**
	 * Loading the default options for the names, checkboxes checked, color and the control keys.
	 */
	public void loadDefaultOptions( ){
		for ( int i = 0; i < max_players; i++ ){
			String defaultlbutton=String.valueOf(defaultlbutton_keys[i]);		
			Lbuttons[i].setText(defaultlbutton);
			String defaultrbutton=String.valueOf(defaultrbutton_keys[i]);		
			Rbuttons[i].setText(defaultrbutton);
			colors[i].setBackground(defaultColors[i]);
			textfields[i].setText("Player-" + (i+1));
		}
	}

	/**
	 * Sets one of the rows enabled, so all the elements in the row.
	 * @param row - the row number.
	 */
	public void enableRow( int row )
	{
		players[row].setEnabled(true);
		textfields[row].setEditable(true);
		colors[row].setEnabled(true);
		Lbuttons[row].setEnabled(true);
		Rbuttons[row].setEnabled(true);
	}
	
	/**
	 * Sets one of the rows disabled, so all the elements in the row.
	 * @param row - the row number.
	 */
	public void disableRow( int row )
	{
		players[row].setEnabled(false);
		textfields[row].setEditable(false);
		colors[row].setEnabled(false);
		Lbuttons[row].setEnabled(false);
		Rbuttons[row].setEnabled(false);
	}
	
	/**
	 * Getting the number of checkbxes checked.
	 * @return The number of checkboxes checked
	 */
	public int getChecks( JCheckBox[] checks, int players )
	{
  		int boxes = 0;
  		for ( int i = 0; i < players; i++ )
  		{
  			if ( checks[i].isSelected() )
  			{
  				boxes += 1;
  			}
  		}
  		return boxes;
	}
	
	/**
	 * Getting the array with the checkboxes inside.
	 * @return The array with the checkboxes inside.
	 */
	public JCheckBox[] getCheckArray()
	{
		return checks;
	}
	
	/**
	 * Getting the number of maximum players associated with this class.
	 * @return The number of maximum players associated with this class.
	 */
	public int getMaxPlayers()
	{
		return max_players;
	}
	
	public void generatePlayers(JButton[] colors)
	{
		player = new Player[getChecks(checks, max_players)];
		int y = 0;
		for ( int i = 0; i < max_players; i++ ){
			if ( checks[i].isSelected() ){
				player[y] =  new Player(textfields[i].getText(), colors[i].getBackground(), lbutton_keys[i], rbutton_keys[i]) ;
				y += 1;
			}
		}
	}

	/**
	 * Getting the object spieler.
	 * @return The object spieler.
	 */
	public Player[] getPlayers()
	{
		return player;
	}
}
