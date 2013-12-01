import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class Sock{
	// Attributes
	Socket socket = null;
	PrintWriter out = null;
	BufferedReader in = null;
	
	// Constructor
	Sock()
	{
	}
	// Send message through socket to client
	public void sendMessage(String message)
	{
		out.println(message);
	}
}