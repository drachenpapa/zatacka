package de.zatacka.network;

import java.io.*;
import java.net.*;

public class Client
{
	static int length  = 256;
	DatagramSocket socket;

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