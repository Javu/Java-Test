import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class SquareWars extends Canvas{
	private BufferStrategy strategy;
	private Vector<Entity> entities;
	private CollisionDetector collision_detector;
	private Renderer renderer;
	
	public static void main(String args[])
	{
		SquareWars program = new SquareWars();
		
		java.util.Timer paintTimer = new java.util.Timer();
		java.util.Timer updateTimer = new java.util.Timer();
		PaintTask paint_task = new PaintTask(program);
		UpdateTask update_task = new UpdateTask(program);
		long paint_timer_delay = 16;
		long update_timer_delay = 16;
		Vector<String> tempppp = new Vector<String>();
		paintTimer.schedule(paint_task, paint_timer_delay, paint_timer_delay);
		updateTimer.schedule(update_task, update_timer_delay, update_timer_delay);
	}

	SquareWars()
	{
		int X_SIZE = 1024;
		int Y_SIZE = 1024;
		
		this.setBounds(0,0,X_SIZE,Y_SIZE);
		
		JFrame frame = new JFrame("JAVA GRAPHICS TEST");
		Dimension d = new Dimension(X_SIZE,Y_SIZE);
		frame.setPreferredSize(d);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		Point p = frame.getLocation();
		frame.setLocation((int)(p.getX()-(X_SIZE/2)),(int)(p.getY()-(Y_SIZE/2)));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.add(this);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.getContentPane().setVisible(true);
	
		frame.pack();
		
		addKeyListener(new KeyInput());
		
		setIgnoreRepaint(true);
		
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		entities = new Vector<Entity>();
		Player player = new Player(64,64,512,512,0,0,0);
		entities.addElement(player);

		int wall_distance = 500;
		Wall wall1 = new Wall(64,64,512-wall_distance,512,0,0,0);
		Wall wall2 = new Wall(64,64,512+wall_distance,512,0,0,0);
		Wall wall3 = new Wall(64,64,512,512-wall_distance,0,0,0);
		Wall wall4 = new Wall(64,64,512,512+wall_distance,0,0,0);
		entities.add(wall1);
		entities.add(wall2);
		entities.add(wall3);
		entities.add(wall4);

		collision_detector = new CollisionDetector();
		
		renderer = new Renderer();
		renderer.loadImage("square_black",33);
	}
	
	public Vector<Entity> getEntities()
	{
		return entities;
	}
	
	public void createEntity(Entity ent)
	{
		entities.addElement(ent);
	}
	
	public void updateComponent()
	{
		Vector<Entity> moved_entities = new Vector<Entity>();

		for(int i=0;i < entities.size();i++)
		{
			BoundingBox current_position = entities.get(i).getWorldBoundingPoints();
			entities.get(i).update();
			BoundingBox new_position = entities.get(i).getWorldBoundingPoints();

			if (!current_position.equals(new_position))
			{
				moved_entities.add(entities.get(i));
			}
		}

		collision_detector.detectCollisions(moved_entities, entities);
		collectGarbage();
	}
	
	public void paintComponent()
	{
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0,0,getWidth(),getHeight());
		g = renderer.render(g,entities);
		g.dispose();
		strategy.show();
	}

	public void collectGarbage()
	{
		int current_size = entities.size();
		for (int i=0; i<current_size; i++)
		{
			if (entities.get(i).destroyed == true)
			{
				entities.remove(i);
				i -= 1;
				current_size -= 1;
			}
		}
	}
	
	private class KeyInput extends KeyAdapter{

		boolean ctrl_flag = false;
		
		public void keyPressed(KeyEvent e)
		{
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				entities.elementAt(0).setXDis(-5);
			}
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				entities.elementAt(0).setXDis(5);
			}
			else if(e.getKeyCode() == KeyEvent.VK_UP)
			{
				entities.elementAt(0).setYDis(-5);
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				entities.elementAt(0).setYDis(5);
			}
			else if(e.getKeyCode() == KeyEvent.VK_CONTROL)
			{
				if(!ctrl_flag)
				{
					renderer.loadImage("bullet",5);
					Bullet bullet_left = new Bullet(5,5,entities.elementAt(0).getXPos()-3,entities.elementAt(0).getYPos()+15,-10,0,0);
					Bullet bullet_right = new Bullet(5,5,entities.elementAt(0).getXPos()+36,entities.elementAt(0).getYPos()+15,10,0,180);
					Bullet bullet_up = new Bullet(5,5,entities.elementAt(0).getXPos()+15,entities.elementAt(0).getYPos()-3,0,-10,90);
					Bullet bullet_down = new Bullet(5,5,entities.elementAt(0).getXPos()+15,entities.elementAt(0).getYPos()+33,0,10,270);
					createEntity(bullet_left);
					createEntity(bullet_right);
					createEntity(bullet_up);
					createEntity(bullet_down);
					ctrl_flag = true;
				}
			}
		}
		
		public void keyReleased(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				entities.elementAt(0).setXDis(0);
			}
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				entities.elementAt(0).setXDis(0);
			}
			else if(e.getKeyCode() == KeyEvent.VK_UP)
			{
				entities.elementAt(0).setYDis(0);
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				entities.elementAt(0).setYDis(0);
			}
			else if(e.getKeyCode() == KeyEvent.VK_CONTROL)
			{
				ctrl_flag = false;
			}
		}
		
		public void keyTyped(KeyEvent e)
		{
			if (e.getKeyChar() == 27)
			{
				System.exit(0);
			}			
		}
	}
}
