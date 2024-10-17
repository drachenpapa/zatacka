package de.zatacka.game;

import java.awt.Color;

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

	public Spieler(String name, Color farbe, char links, char rechts, boolean client)
	{
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

	public void setName(String name) {
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	public void setFarbe(Color farbe) {
		this.farbe = farbe;
	}

	public Color getFarbe() {
		return this.farbe;
	}

	public char getLinks()
	{
		return this.links;
	}

	public char getRechts()
	{
		return this.rechts;
	}

	public boolean isClient() {
		return this.client;
	}

	public boolean isLpressed() {
		return lpressed;
	}

	public void setLpressed(boolean lpressed) {
		this.lpressed = lpressed;
	}

	public boolean isRpressed() {
		return rpressed;
	}

	public void setRpressed(boolean rpressed) {
		this.rpressed = rpressed;
	}
}