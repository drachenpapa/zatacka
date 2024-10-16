package de.zatacka.dialogs;

import java.awt.*;
import javax.swing.*;
import de.zatacka.game.*;
import java.awt.event.*;

public class GUI_lokal extends GUI implements ActionListener
{
	private static int max_players = 6;
	private JPanel pannel;

	public GUI_lokal()
	{
		super(max_players);
		pannel = drawRaw();
		pannel.setPreferredSize(new Dimension(600, 400));
		setSize(600, 400);
		setResizable(false);
		setLocation(300, 200);
		pannel.setLocation(400,200);
        
		pannel.setLayout( new FlowLayout(FlowLayout.LEFT,6,6 ));
		pannel.setBackground( new Color(205,205,205) );
        	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
		for ( int i = 0; i < max_players; i++ ){
			checks[i].addActionListener(this);
			colors[i].addActionListener(this);
			Lbuttons[i].addActionListener(this);
			Rbuttons[i].addActionListener(this);
		}

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
		
		add(pannel);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == start ){
			saveOptions();
	  		generatePlayers( colors, false );
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
				if ( getChecks( checks, max_players ) <= 1 ){
					start.setEnabled(false);
				}
				else if ( getChecks( checks, max_players ) > 1 ){
					start.setEnabled(true);
				}

				if (  checks[i].isSelected() )
				{
					enableRow(i);		
				}
				
				else if ( ! checks[i].isSelected() )
				{
					disableRow(i);
					checks[max_players].setSelected(false);	
				}
			}
						
			else if ( e.getSource() == checks[max_players] )
			{	
				if ( checks[max_players].isSelected() == true )
				{
					checks[i].setSelected(true);
					enableRow(i);
				}

				else if ( checks[max_players].isSelected() == false)
				{
					checks[i].setSelected(false);
					disableRow(i);																
				}

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