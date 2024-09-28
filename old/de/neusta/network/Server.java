// Netzwerk-Methoden für Zatacka-Server
// Henning Wrede, Matthias Junge & René Marksteiner

//package network;

package de.neusta.network;

import java.net.*;
import java.io.*;
import java.util.*;

	/**
	 * Server defines a class representing the methods that were used for playing as server.
	 * @author Henning Wrede
	 * @version 1.0
	 */

public class Server
{
	static int length  = 256;
	DatagramSocket s;
	Vector clients;
    
	/**
     * Receives a String which is representing the object Spieler or the Keys which where pressed.
     * @return A String representing the object Spieler or the Keys which where pressed for the Server.
     */
	public String receive()
	{		
		// instances
		DatagramPacket packet = new DatagramPacket( new byte[length], length );

		try
		{
			// setting the DatagramSocket		
			DatagramSocket s = new DatagramSocket( 3000 );
			while( true )
			{
				// receives the packet
				s.receive( packet );
				// gets the InetSocketAddress
				InetSocketAddress add = (InetSocketAddress)packet.getSocketAddress();
				// checks if the client-address is already in the vector
				for(int i=0; i<6; i++)	
				{
					if( i != clients.indexOf( add ) )
					{
						// adding a client to the list
						clients.add( add );
					}
				}
				// stores the text
				String text = new String( packet.getData(), 0, packet.getLength() );
				return text;
			}
		}
		catch ( IOException e )
		{
			return "error";
		}
	}

    /**
     * Sends a String representing the object Spieler or the Keys which where pressed to the Clients.
     * @param message the message which will be send.
     */
	public void send( String message )
	{
		// instances
		DatagramPacket sending_packet = new DatagramPacket( new byte[length], length );

		byte[] ba = message.getBytes();
		// setting the data of the sending packet
		sending_packet.setData( ba, 0, ba.length );

		try
		{
			for( int i = 0; i < clients.size(); i++ )
			{
				// setting the destination adress
				InetSocketAddress dest = ( InetSocketAddress )clients.get( i );
				// sets the destination
				sending_packet.setSocketAddress( dest );
				// sending the packet
				s.send( sending_packet );
			}
		}
		catch( IOException ioe )
		{  }
	}
}