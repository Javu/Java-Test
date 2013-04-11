import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.lang.*;

class BasePanel extends JPanel{
	private Vector<Entity> entities;
	
	BasePanel()
	{
		entities = new Vector<Entity>();
		Player player = new Player(64,64,512,512,0,0);
		entities.addElement(player);
	}
	
	public void paintComponent(Graphics2D g)
	{
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0,0,getWidth(),getHeight());
		for(int i=0;i < entities.size();i++)
		{
			entities.get(i).paint(g);
		}
		g.dispose();
	}
	
	public void updateComponent()
	{
		for(int i=0;i < entities.size();i++)
		{
			entities.get(i).update();
		}
	}
	
	public Vector<Entity> getEntities()
	{
		return entities;
	}
	
	public void createEntity(Entity ent)
	{
		entities.addElement(ent);
	}
}