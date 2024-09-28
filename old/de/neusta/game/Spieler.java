// Spieler-Klasse für Zatacka
// Henning Wrede, Matthias Junge & René Marksteiner

package de.neusta.game;

import java.awt.Color;

	/**
	 * Class, which models a player (Spieler).
	 * @author Matthias Junge
	 * @version 1.0
	 */

public class Spieler
{
	private boolean client;
	private Color farbe;
	public Kurve kurve =  new Kurve(Math.round(Math.random()*Zatacka.WIDTH)+100,
            Math.round(Math.random()*Zatacka.HEIGHT)+100,
            Math.random()*360, Math.round(Math.random() * 10) + 1);
	private char links;
	private String name;
	private char rechts;
	private boolean lpressed = false;
	private boolean rpressed = false;

	// Konstruktor mit Klassenvariablen
	/**
	 * @param name
	 *            Name of the player.
	 * @param farbe
	 *            Color of the curve.
	 * @param links
	 *            The Left-button to play with.
	 * @param rechts
	 *            The right-button.
	 * @param client
	 *            Says if the player is a client or the server.
	 */
	public Spieler(String name, Color farbe, char links, char rechts, boolean client)
	{
		// Zuweisung der Klassenvariablen, die mithilfe von Methoden geändert
		// bzw. abgerufen werden können
		this.name = name;
		this.farbe = farbe;
		this.links = links;
		this.rechts = rechts;
		this.kurve = new Kurve(Math.round(Math.random()*Zatacka.WIDTH)+100,
				               Math.round(Math.random()*Zatacka.HEIGHT)+100,
				               Math.random()*360,
				               Math.round(Math.random() * 10) + 1);
		this.client = client;
	}

	// ----------------------------------------------------------------------------//
	// Spielername setzen und zurückgeliefert bekommen //
	// ----------------------------------------------------------------------------//

	/**
	 * Sets the name of the player.
	 */
	public void setName(String name) {
		this.name = name; // Zuweisung
	}

	/**
	 * Getting the name of the player.
	 * @return The name of the player.
	 */
	public String getName()
	{
		return this.name; // return-statement
	}

	// ----------------------------------------------------------------------------//
	// Spielerfarbe setzen und zurückgeliefert bekommen //
	// ----------------------------------------------------------------------------//

	/**
	 * Sets the color of the player.
	 */
	public void setFarbe(Color farbe) {
		this.farbe = farbe;
	}

	/**
	 * Getting the name of the player. 
	 * @return The name of the player.
	 */
	public Color getFarbe() {
		return this.farbe;
	}

	// ----------------------------------------------------------------------------//
	// Steuerung zurückgeliefert bekommen //
	// ----------------------------------------------------------------------------//

	/**
	 * Getting the left control button of the player.
	 * @return The left control key.
	 */
	public char getLinks()
	{
		return this.links;
	}

	/**
	 * Getting the right control button of the player.
	 * @return The right control key.
	 */
	public char getRechts()
	{
		return this.rechts;
	}

	// ----------------------------------------------------------------------------//
	// Überprüfen, ob Spieler Server oder client ist //
	// ----------------------------------------------------------------------------//

	/**
	 * Getting information about whether the player is a client or not.
	 * @return Boolean which says whether the player is a client or not.
	 */
	public boolean isClient() {
		return this.client;
	}

	/**
	 * Getting information about whether the left control key from the user is
	 * pressed.
	 * @return The lpressed.
	 */
	public boolean isLpressed() {
		return lpressed;
	}

	/**
	 * @param lpressed The lpressed to set.
	 */
	public void setLpressed(boolean lpressed) {
		this.lpressed = lpressed;
	}

	/**
	 * Getting information about whether the right control key from the user is
	 * pressed.
	 * @return The rpressed.
	 */
	public boolean isRpressed() {
		return rpressed;
	}

	/**
	 * @param rpressed
	 *            The rpressed to set.
	 */
	public void setRpressed(boolean rpressed) {
		this.rpressed = rpressed;
	}
	
}