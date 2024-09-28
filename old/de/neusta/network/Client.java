// Netzwerk-Methoden für Zatacka-Client
// Henning Wrede, Matthias Junge & René Marksteiner

//package network;

package de.neusta.network;

import java.io.*;
import java.net.*;

	/**
	 * Client defines a class representing the methods that were used for playing as a client.
	 * @author Henning Wrede
	 * @version 1.0
	 */

public class Client
{
	static int length  = 256;
	DatagramSocket socket;
	
	/**
     * Sends a String representing the object Spieler or the Keys which where pressed to the Server.
     * @param message the message which will be send.
     * @param ipAdress the ipAdress to which the message will be send.
     */
	public void send( String message, String ipAdress )
	{
		DatagramPacket packet;
		
		byte[] ba = message.getBytes();
		
		try
		{
			InetAddress ia =  InetAddress.getByName( ipAdress );
			packet = new DatagramPacket( ba, ba.length, ia, 3000);
			packet.setData( ba, 0, ba.length );
			socket.send( packet );
		}
		catch( IOException e )
		{  }
	}
	
	/**
	 * Receives a String which is representing the object Spieler or the Keys which where pressed.
	 * @return A String representing the object Spieler or the Keys which where pressed for the Client.
     */
	public String receive()
	{
		DatagramPacket packet = new DatagramPacket( new byte[length], length);
		while( true )
		{
			try
			{
				socket.receive( packet );
	      		String text = new String( packet.getData(), 0, packet.getLength() );
				return text;
			}
			catch( IOException e )
			{  }
		}
	}
}
