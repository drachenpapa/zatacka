// JSpinner-Listener für Zatacka-GUI's
// Henning Wrede, Matthias Junge & René Marksteiner

package de.neusta.dialogs;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

	/**
	 *	ChangeListener that catches the ChangeEvents caused by the JSpinner.
	 */

// Implementation eines ChangeListeners für den JSpinner "Anzahl Spieler auf Serve"
class JSpinnerListener implements ChangeListener
{						
	GUI_server ref;  // Referenz auf die GUI_server Klasse
		
	// Konstruktor
	public JSpinnerListener( GUI_server ref )
	{
		this.ref = ref;
	}	
		
	public void stateChanged(ChangeEvent e) 				
	{
		int spinner  = ref.getAnzahl();		// Anzahl Spieler eingestellt im JSpinner
		int zahl = ref.getChecks( ref.getCheckArray(), ref.getMaxPlayers() );				// Anzahl an Checkboxen, die angewählt sind
		int diff = spinner - zahl;				// differenz zwischen Anzahl der Spieler und den ausgewählten Checkboxen
		
		// Mit Hilfe der Differenz zwischen den beiden wird geguckt, was gemacht werden muss:
		//		--> (1) Entweder werden weitere Chechboxen zum Anwählen freigegeben (wenn mehr Spieler zum Spielen freigegegen wurden)
		//		--> (2) Oder es wird der letzte Spieler (Zeile mit Checkbox) abgewählt und disabled
				
		// <--(1)-->
		if ( diff > 0 )
		{
			for ( int k = 0; k < ref.getMaxPlayers(); k++ )
			{
				this.ref.setCheckEnable(k);
			}											
		}
				
		// <--(2)-->
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