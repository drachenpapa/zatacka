package de.zatacka.network;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server
{
	static int length  = 256;
	DatagramSocket s;
	Vector clients;

	public String receive()
	{
		DatagramPacket packet = new DatagramPacket( new byte[length], length );

		try
		{	
			DatagramSocket s = new DatagramSocket( 3000 );
			while( true )
			{
				s.receive( packet );

				InetSocketAddress add = (InetSocketAddress)packet.getSocketAddress();

				for(int i=0; i<6; i++)	
				{
					if( i != clients.indexOf( add ) )
					{
						clients.add( add );
					}
				}

				String text = new String( packet.getData(), 0, packet.getLength() );
				return text;
			}
		}
		catch ( IOException e )
		{
			return "error";
		}
	}

	public void send( String message )
	{
		DatagramPacket sending_packet = new DatagramPacket( new byte[length], length );

		byte[] ba = message.getBytes();
		sending_packet.setData( ba, 0, ba.length );

		try
		{
			for( int i = 0; i < clients.size(); i++ )
			{
				InetSocketAddress dest = ( InetSocketAddress )clients.get( i );
				sending_packet.setSocketAddress( dest );
				s.send( sending_packet );
			}
		}
		catch( IOException ioe )
		{  }
	}
}