import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class GameServer extends Server{
	private int port;
	private ServerSocket server_socket;
	private Vector<SocketThread> socket_list;
	private Vector<String> player_keys;
	private Vector<Boolean> player_valid;
	private int max_players;
	private int game_type;
	private int use_teams;
	private boolean ready;

	public static void main(String args[])
	{
		int run = 1;
		GameServer server = new GameServer(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		Vector<String> keys = server.getPlayerKeys();
		Vector<Boolean> valid = server.getPlayerValid();
		for(int i=0;i<server.getMaxPlayers();i++)
		{
			keys.addElement(args[i+3]);
			valid.addElement(Boolean.valueOf(false));
		}
		server.setPlayerKeys(keys);
		server.setPlayerValid(valid);
		while(run == 1)
		{
			// Block while awaiting connections. When a connection is made create a new SocketThread and start the thread
			// Only accept new connections until all players have connected and have been validated
			if(server.getReady() == false)
			{
				try
				{
					Sock temp_socket = new Sock();
					temp_socket.socket = server.server_socket.accept();
					temp_socket.out = new PrintWriter(temp_socket.socket.getOutputStream(), true);
					temp_socket.in = new BufferedReader(new InputStreamReader(temp_socket.socket.getInputStream()));
					server.socket_list.addElement(new SocketThread(temp_socket,server.socket_list,server,server.socket_list.size()));
					server.socket_list.lastElement().start();
				}
				catch(Exception e)
				{
					System.out.println("Could not listen");
				}
			}
			// Once all players are validated begin running the standard game loop, checking for win conditions and keeping players updated
			else
			{
			
			}
		}
	}
	
	GameServer()
	{
		port = 23232;
		max_players = 8;
		game_type = 0;
		use_teams = 0;
		socket_list = new Vector<SocketThread>();
		player_keys = new Vector<String>();
		player_valid = new Vector<Boolean>();
		ready = false;
		// initialise ServerSocket ready for listening
		try
		{
			server_socket = new ServerSocket(port);
		}
		catch(Exception e)
		{
			System.out.println("Could not create listen socket. GameServer will terminate");
		}
	}
	
	GameServer(int g_type, int teams, int max_p)
	{
		port = 23232;
		max_players = max_p;
		game_type = g_type;
		use_teams = teams;
		socket_list = new Vector<SocketThread>();
		player_keys = new Vector<String>();
		player_valid = new Vector<Boolean>();
		ready = false;
		// initialise ServerSocket ready for listening
		try
		{
			server_socket = new ServerSocket(port);
		}
		catch(Exception e)
		{
			System.out.println("Could not create listen socket. GameServer will terminate");
		}
	}
	
	public int getMaxPlayers()
	{
		return max_players;
	}
	
	public boolean getReady()
	{
		return ready;
	}
	
	public Vector<SocketThread> getSocketList()
	{
		return socket_list;
	}
	
	public Vector<String> getPlayerKeys()
	{
		return player_keys;
	}
	
	public Vector<Boolean> getPlayerValid()
	{
		return player_valid;
	}
	
	public void setPlayerKeys(Vector<String> s)
	{
		player_keys = s;
	}
	
	public void setPlayerValid(Vector<Boolean> v)
	{
		player_valid = v;
	}
	
	public void handleMessage(String message,int index)
	{
		int option = 0;
		int count = 0;
		String text = "";
		String opt = "";
		if(ready == false)
		{	
			Vector<String> messages = new Vector<String>();
			for(char n : message.toCharArray())
			{
				// If a special command is found extract the attributes of the command
				if(option != 0)
				{
					if(count != 0)
					{
						if(n == '|')
						{
							messages.addElement(text);
							text = "";
						}
						else
						{
							text+=n;
						}
					}
					else
					{
						count+=1;
					}
				}
				else
				{
					opt+=n;
				}
			}
			
			boolean p_valid = false;
			if(opt.compareTo("/connect") == 0)
			{
				for(int i=0;i<player_keys.size();i++)
				{
					if(messages.elementAt(0) == player_keys.elementAt(i) && player_valid.elementAt(i).booleanValue() != true)
					{
						socket_list.elementAt(index).setPlayerNumber(Integer.parseInt(messages.elementAt(1)));
						socket_list.elementAt(index).setTeam(Integer.parseInt(messages.elementAt(2)));
						player_valid.setElementAt(Boolean.valueOf(true),i);
						p_valid = true;
						
						boolean ready_valid = true;
						for(int j=0;j<player_valid.size();j++)
						{
							if(player_valid.elementAt(j).booleanValue() == false)
							{
								ready_valid = false;
							}
						}
						if(ready_valid == true)
						{
							ready = true;
						}
					}
				}
			}
			
			if(p_valid == false)
			{
				try
				{
					socket_list.elementAt(index).getSocket().socket.close();
				}
				catch(Exception e)
				{
					//server.writeLog("Socket cannot be closed. Please close manually");
				}
				try
				{
					socket_list.remove(index);
				}
				catch(Exception e)
				{
					//server.writeLog("Socket could not be removed from vector");
				}
				try
				{
					socket_list.elementAt(index).interrupt();
				}
				catch(Exception e)
				{
					//server.writeLog("Thread could not be interrupted");
				}
				for(int i=0;i<socket_list.size();i++)
				{
					if(socket_list.elementAt(i).getIndex() > index)
					{
						socket_list.elementAt(i).setIndex(socket_list.elementAt(i).getIndex()-1);
					}
				}
			}
		}
		else
		{
			for(char n : message.toCharArray())
			{
				// If a special command is found extract the attributes of the command
				if(option != 0)
				{
					if(count != 0)
					{
							text+=n;
					}
					else
					{
						count+=1;
					}
				}
				else
				{
					opt+=n;
				}
			}
		}
	}
}