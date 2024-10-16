package de.zatacka.dialogs;

import java.awt.*;
import javax.swing.*;
import de.zatacka.game.*;

import java.awt.event.*;

public class GUI_server extends GUI implements ActionListener
{
	private static int max_players = 6;

	private JLabel text_speed, text_anzahl, leerfeld1, leerfeld2, leerfeld3;
	private JPanel pannel;
	private JSpinner anzahl;

	public GUI_server()
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
  		text_speed.setPreferredSize(new Dimension(120, 25));

  		pannel.add( text_speed );
  		pannel.add( speed );	

  		leerfeld1 = new JLabel("");
  		leerfeld1.setPreferredSize( new Dimension(400, 25) );
  		pannel.add(leerfeld1);

  		text_anzahl = new JLabel("Spieler auf Server:");
  		text_anzahl.setPreferredSize( new Dimension(120, 25) );
  		pannel.add(text_anzahl);

  		anzahl = new JSpinner( new SpinnerNumberModel( 6, 2, max_players, 1 ) );
  		anzahl.setPreferredSize( new Dimension( 40, 25 ) );
  		anzahl.addChangeListener(new JSpinnerListener(this));
  		pannel.add(anzahl);

  		leerfeld2 = new JLabel("");
  		leerfeld2.setPreferredSize( new Dimension(1000, 25) );
  		pannel.add(leerfeld2);

  		leerfeld3 = new JLabel("");
  		leerfeld3.setPreferredSize( new Dimension(220, 25) );
  		pannel.add(leerfeld3);				

  		start = new JButton("Spiel starten !");
  		start.setPreferredSize( new Dimension(120, 25));
  		start.addActionListener(this);
  		pannel.add(start);	
						
  		add(pannel);
  		setVisible(true);
	}

	public void setCheckEnable( int index )
	{
		checks[index].setEnabled(true);
	}

	public void setUncheck( int index )
	{
        checks[index].setSelected(false);
		players[index].setEnabled(false);
		textfields[index].setEditable(false);
		colors[index].setEnabled(false);
		Lbuttons[index].setEnabled(false);
		Rbuttons[index].setEnabled(false);	
	}

	public void setCheck( int index )
	{
		checks[index].setSelected(true);
		players[index].setEnabled(true);
		textfields[index].setEditable(true);
		colors[index].setEnabled(true);
		Lbuttons[index].setEnabled(true);
		Rbuttons[index].setEnabled(true);
	}
      
	public int getAnzahl()
	{
		return Integer.parseInt( anzahl.getValue().toString() );
	}

	public boolean isChecked( int index )
	{
		return checks[index].isSelected();
	}

	public String getText( JTextField TextField )
	{
		return TextField.getText();
	}

	public Color getColor( JButton cbutton )
	{
		return cbutton.getBackground();
	}

	public String getLeftKey( JButton kbutton )
	{
		return kbutton.getText();
	}

	public String getRightKey( JButton kbutton )
	{
		return kbutton.getText();
	}			

	public int getMaxPlayers(  )
	{
		return max_players;
	}			

	public void actionPerformed(ActionEvent e)
	{
		if ( e.getSource() == start )
		{
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
						});			
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
						});			
			}

			else if ( e.getSource() == checks[i] )
			{
				if ( getChecks( checks, max_players ) == 0 ){
					start.setEnabled(false);
				}
				else if ( getChecks( checks, max_players ) > 0 ){
					start.setEnabled(true);
				}	

				if ( isChecked(i) )
				{	
					if ( getChecks( checks, max_players ) == getAnzahl() )
					{
						for ( int k = 0; k < max_players; k++ )
						{
							if ( ! isChecked(k) )
							{
								checks[k].setEnabled(false);
							}
						}
					}
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
						checks[i].setSelected(true);
						enableRow(i);
					}						
				}
				
				else if ( isChecked(max_players) == false)
				{
					checks[i].setSelected(false);
					disableRow(i);																
				}	

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