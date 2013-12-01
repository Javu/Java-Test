import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class SocketThread extends Thread{
	private Sock socket;
	private Vector<SocketThread> sockets;
	private Server server;
	private int index;
	private int team;
	private int player_number;
	
	SocketThread(Sock inSock, Vector<SocketThread> inSockets, Server ser, int ind)
	{
		// Initialise attributes
		socket = inSock;
		sockets = inSockets;
		server = ser;
		index = ind;
		team = 0;
		player_number = 0;
	}
	
	public void run()
	{
		int run = 1;
		while(run == 1)
		{
			String message = "";
			// Block and wait for input from the socket
			try
			{
				message = socket.in.readLine();
				if(message.compareTo("-1") == 0)
				{
					close();
				}
				else
				{
					server.handleMessage(message);
				}
			}
			catch(Exception e)
			{
				// If socket is closed pass special command to message handler and end thread loop
				close();
				run = 0;
			}
		}
	}
	
	public void close()
	{
		// Close the socket, remove sockThread from sockets Vector and interrupt the thread
		try
		{
			socket.socket.close();
		}
		catch(Exception e)
		{
			throw new RuntimeException("Error: Faulting method SocketThread.close())\nAction: Close socket "+index);
			//server.writeLog("Socket cannot be closed. Please close manually");
		}
		try
		{
			server.getSocketList().remove(index);
		}
		catch(Exception e)
		{
			throw new RuntimeException("Error: Faulting method SocketThread.close()\nAction: Remove socket "+index+" from vector");
			//server.writeLog("Socket could not be removed from vector");
		}
		try
		{
			this.interrupt();
		}
		catch(Exception e)
		{
			throw new RuntimeException("Error: Faulting method SocketThread.close()\nAction: Interrupt thread "+index);
			//server.writeLog("Thread could not be interrupted");
		}
	}
	
	public Sock getSocket()
	{
		return socket;
	}
	
	public int getTeam()
	{
		return team;
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public int getPlayerNumber()
	{
		return player_number;
	}
	
	public void setTeam(int t)
	{
		team = t;
	}
	
	public void setIndex(int i)
	{
		index = i;
	}
	
	public void setPlayerNumber(int p)
	{
		player_number = p;
	}
}