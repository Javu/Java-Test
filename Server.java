import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class Server{
	private int port;
	private ServerSocket server_socket;
	private Vector<SocketThread> socket_list;
	
	// Basic Constructor
	Server()
	{
		port = 0;
		socket_list = new Vector<SocketThread>();
		server_socket = null;
	}
	
	// Destructor
	// Exceptions: RuntimeException
	public void close()
	{
		try
		{
			server_socket.close();
		}
		catch(Exception e)
		{
			throw new RuntimeException("Error: Faulting method Server.close()");
		}
		for(int i=0;i<socket_list.size();i++)
		{
			try
			{
				socket_list.elementAt(i).close();
			}
			catch(RuntimeException e)
			{
				throw new RuntimeException("Error: Faulting method SocketThread.close()\nCalling method Server.close()");
			}
			catch(Exception e)
			{
				throw new RuntimeException("Error: Faulting method Server.close()\nAction SocketThread.close()\n socket_list index "+ i);
			}
		}
		socket_list = null;
	}
	
	// Setter for attribute port
	public void setPort(int p)
	{
		port = p;
	}
	
	// Getter for attribute port
	public int getPort()
	{
		return port;
	}
	
	// Setter for attribute socket_list
	public void setSocketList(Vector<SocketThread> sl)
	{
		socket_list = sl;
	}
	
	// Getter for attribute socket_list
	public Vector<SocketThread> getSocketList()
	{
		return socket_list;
	}
	
	// Method used to create a new server_socket using the port attribute
	// Exceptions: RuntimeException
	public void changePort()
	{
		try
		{
			server_socket = new ServerSocket(port);
		}
		catch(Exception e)
		{
			throw new RuntimeException("Error: Faulting method Server.changePort()");
		}
	}
	
	public void removeSocket(int ind)
	{
		
		socket_list.elementAt(ind).close();
		
	}
	
	// Method used to create a connection using just an IP address (or hostname)
	// Exceptions: RuntimeException
	public void addSocketByIp(String ip)
	{
		try
		{
			Sock temp_socket = new Sock();
			temp_socket.socket = new Socket(ip, port);
			PrintWriter out = new PrintWriter(temp_socket.socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(temp_socket.socket.getInputStream()));
			socket_list.addElement(new SocketThread(temp_socket,socket_list,this,socket_list.size()));
		}
		catch(Exception e)
		{
			throw new RuntimeException("Error: Faulting method Server.addSocketByIp(String ip)");
		}
	}
	
	// Method used to create a connection using an IP address (or hostname) and port number
	// Exceptions: RuntimeException
	public void addSocketByIp(String ip, int p)
	{
		try
		{
			Sock temp_socket = new Sock();
			temp_socket.socket = new Socket(ip, p);
			PrintWriter out = new PrintWriter(temp_socket.socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(temp_socket.socket.getInputStream()));
			socket_list.addElement(new SocketThread(temp_socket,socket_list,this,socket_list.size()));
		}
		catch(Exception e)
		{
			throw new RuntimeException("Error: Faulting method Server.addSocketByIp(String ip, int p)");
		}
	}
	
	// Method used to listen for input on a server_scoket
	// Exceptions: RuntimeException
	public void listen()
	{
		try
		{
			Sock temp_socket = new Sock();
			temp_socket.socket = server_socket.accept();
			temp_socket.out = new PrintWriter(temp_socket.socket.getOutputStream(), true);
			temp_socket.in = new BufferedReader(new InputStreamReader(temp_socket.socket.getInputStream()));
			socket_list.addElement(new SocketThread(temp_socket,socket_list,this,socket_list.size()));
			socket_list.lastElement().start();
		}
		catch(Exception e)
		{
			throw new RuntimeException("Error: Faulting method Server.listen()");
		}
	}
	
	// Method to be overwritten by child class. Used to handle text inputs sent by connected entities.
	public void handleMessage(String message)
	{
	
	}
}