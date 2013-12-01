import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class LobbyServer extends Server{
	private int port;
	private ServerSocket server_socket;
	private Vector<SocketThread> socket_list;
	private int max_players;
	private int game_type;
	private int use_teams;
	
	public static void main(String args[])
	{
		int run = 1;
		LobbyServer server = new LobbyServer();
		while(run == 1)
		{
			// Block while awaiting connections. When a connection is made create a new SocketThread and start the thread
			if(server.getSocketList().size() < server.getMaxPlayers())
			{
				try
				{
					server.listen();
				}
				catch(Exception e)
				{
					System.out.println("Could not listen");
				}
			}
			else
			{
			
			}
		}
	}
	
	LobbyServer()
	{
		port = 23231;
		max_players = 8;
		game_type = 0;
		use_teams = 0;
		socket_list = new Vector<SocketThread>();
		// initialise ServerSocket ready for listening
		try
		{
			changePort();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	LobbyServer(int players)
	{
		port = 23231;
		max_players = players;
		game_type = 0;
		use_teams = 0;
		socket_list = new Vector<SocketThread>();
		// initialise ServerSocket ready for listening
		try
		{
			changePort();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public int getMaxPlayers()
	{
		return max_players;
	}
	
	public void handleMessage(String message,int index)
	{
		int option = 0;
		int count = 0;
		String text = "";
		String opt = "";
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
			
			if(opt.compareTo("/player") == 0)
			{
				option = 1;
			}
			else if(opt.compareTo("/team") == 0)
			{
				option = 2;
			}
			else if(opt.compareTo("/kick") == 0)
			{
				option = 3;
			}
			else if(opt.compareTo("/game") == 0)
			{
				option = 4;
			}
			else if(opt.compareTo("/score") == 0)
			{
				option = 5;
			}
			else if(opt.compareTo("/time") == 0)
			{
				option = 6;
			}
			else if(opt.compareTo("/useteams") == 0)
			{
				option = 7;
			}
			else if(opt.compareTo("/start") == 0)
			{
				option = 8;
			}
			else if(opt.compareTo("/close") == 0)
			{
				option = 9;
			}
			else if(opt.compareTo("/help") == 0)
			{
				option = 10;
			}
		}
		
		if(option == 1)
		{
			socket_list.elementAt(index).getSocket().out.println("Command not yet implemented");
		}
		else if(option == 2)
		{
			int num = Integer.parseInt(text); 
			socket_list.elementAt(index).setTeam(num);
			for(int i=0;i<socket_list.size();i++)
			{
				socket_list.elementAt(i).getSocket().out.println("Player "+index+" changed to team "+text);
			}
		}
		// Host commands
		else if(option == 3 || option == 4 || option == 5 || option == 6 || option == 7 || option == 8)
		{
			if(index == 0)
			{
				// Kick Command. Kicks selected player based on index
				if(option == 3)
				{
					// Close the socket, remove sockThread from sockets Vector and interrupt the thread
					int num = Integer.parseInt(text);
					if(num == 0)
					{
						socket_list.elementAt(index).getSocket().out.println("You can't kick yourself");
					}
					else
					{
						try
						{
							socket_list.elementAt(num).getSocket().socket.close();
						}
						catch(Exception e)
						{
							//server.writeLog("Socket cannot be closed. Please close manually");
						}
						try
						{
							socket_list.remove(num);
						}
						catch(Exception e)
						{
							//server.writeLog("Socket could not be removed from vector");
						}
						try
						{
							socket_list.elementAt(num).interrupt();
						}
						catch(Exception e)
						{
							//server.writeLog("Thread could not be interrupted");
						}
						for(int i=0;i<socket_list.size();i++)
						{
							if(socket_list.elementAt(i).getIndex() > num)
							{
								socket_list.elementAt(i).setIndex(socket_list.elementAt(i).getIndex()-1);
							}
							String s = Integer.toString(num+1);
							socket_list.elementAt(i).getSocket().out.println("Player "+s+" has been kicked");
						}
					}
				}
				// Game command. Changes game mode
				else if(option == 4)
				{
					socket_list.elementAt(index).getSocket().out.println("Command not yet implemented");
				}
				// Score command. Changes score limit if applicable
				else if(option == 5)
				{
					socket_list.elementAt(index).getSocket().out.println("Command not yet implemented");
				}
				// Time command. Changes time limit if applicable
				else if(option == 6)
				{
					socket_list.elementAt(index).getSocket().out.println("Command not yet implemented");
				}
				// Useteams command. Toggles whether to use teams or free for all
				else if(option == 7)
				{
					if(use_teams == 0)
					{
						use_teams = 1;
					}
					else
					{
						use_teams = 0;
					}
					for(int i=0;i<socket_list.size();i++)
					{
						if(use_teams == 0)
						{
							socket_list.elementAt(i).getSocket().out.println("Teams are now being used");
						}
						else
						{
							socket_list.elementAt(i).getSocket().out.println("Teams are no longer being used");
						}
					}
				}
				// Start command. Closes the lobby server and starts the game server using all parameters set
				else if(option == 8)
				{
					socket_list.elementAt(index).getSocket().out.println("Command not yet implemented");
				}
			}
			// If user is not the host notify them that the command is illegal
			else
			{
				socket_list.elementAt(index).getSocket().out.println("Only the Host can change this option");
			}
		}
		// Close command. Closes connection with the server
		else if(option == 9)
		{
			if(index == 0)
			{
				for(int i=0;i<socket_list.size();i++)
				{
					socket_list.elementAt(i).getSocket().out.println("The host has left, server has been closed");
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
						socket_list.elementAt(index).interrupt();
					}
					catch(Exception e)
					{
						//server.writeLog("Thread could not be interrupted");
					}
				}
			}
			else
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
					socket_list.elementAt(i).getSocket().out.println("Player "+index+" has left");	
				}
			}
		}
		// Help command
		else if(option == 10)
		{
			socket_list.elementAt(index).getSocket().out.println("Lobby Commands: /player # - change player number. /teams # - change team number. /close - closes connection to the server. /kick # - kick player # (host only). /useteams - toggles whether teams are used (host only). /start - starts the game (host only)");
		}
		// No valid option given
		else
		{
			socket_list.elementAt(index).getSocket().out.println("Invalid Command");
		}
		System.out.println("Raw Text: "+message);
		System.out.println("Option String: "+opt);
		System.out.println("Option Variable: "+text);
		System.out.println("Option code: "+option);
	}
}
/*
class sock{
	// Attributes
	Socket socket = null;
	PrintWriter out = null;
	BufferedReader in = null;
	
	// Constructor
	sock()
	{
	}
	// Send message through socket to client
	public void sendMessage(String message)
	{
		out.println(message);
	}
}*/