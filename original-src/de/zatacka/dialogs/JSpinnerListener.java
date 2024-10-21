package de.zatacka.dialogs;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class JSpinnerListener implements ChangeListener
{						
	GUI_server ref;

	public JSpinnerListener( GUI_server ref )
	{
		this.ref = ref;
	}	
		
	public void stateChanged(ChangeEvent e) 				
	{
		int spinner  = ref.getAnzahl();
		int zahl = ref.getChecks( ref.getCheckArray(), ref.getMaxPlayers() );
		int diff = spinner - zahl;

		if ( diff > 0 )
		{
			for ( int k = 0; k < ref.getMaxPlayers(); k++ )
			{
				this.ref.setCheckEnable(k);
			}											
		}

		else if ( diff < 0 )
		{
			int p = 0;
			for ( int y = 0; y < ref.getMaxPlayers(); y++ )
			{
				if ( ref.isChecked(y) )
				{
					p = y;
				}
			}
			ref.setUncheck(p);
		}
	}
}