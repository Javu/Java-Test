import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class SquareWars extends JFrame{
	private BasePanel root;
	
	public static void main(String args[])
	{
		SquareWars program = new SquareWars();

		program.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		program.setVisible(true);
		program.getContentPane().setVisible(true);
	
		program.pack();

		java.util.Timer paintTimer = new java.util.Timer();
		java.util.Timer updateTimer = new java.util.Timer();
		PaintTask paint_task = new PaintTask(program.getRoot());
		UpdateTask update_task = new UpdateTask(program.getRoot());
		long paint_timer_delay = 16;
		long update_timer_delay = 16;
		paintTimer.schedule(paint_task, paint_timer_delay, paint_timer_delay);
		updateTimer.schedule(update_task, update_timer_delay, update_timer_delay);
		
/*		while(true){
			Program.getRoot().updateComponent();
			Program.getRoot().paintComponent((Graphics2D)Program.getRoot().getGraphics());
			try { Thread.sleep(10); } catch (Exception e) {}
		}*/
	}

	SquareWars()
	{
		super("JAVA GRAPHICS TEST");

		Dimension d = new Dimension(1024,1024);
		this.setPreferredSize(d);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		Point p = this.getLocation();
		this.setLocation((int)(p.getX()-512),(int)(p.getY()-512));
		root = new BasePanel();
		this.getContentPane().add(root);
		
		addKeyListener(new KeyInput());
		
		setIgnoreRepaint(true);
	}

	public BasePanel getRoot()
	{
		return root;
	}
	
	private class KeyInput extends KeyAdapter{

		boolean ctrl_flag = false;
		
		public void keyPressed(KeyEvent e)
		{
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				root.getEntities().elementAt(0).setx_dis(-5);
			}
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				root.getEntities().elementAt(0).setx_dis(5);
			}
			else if(e.getKeyCode() == KeyEvent.VK_UP)
			{
				root.getEntities().elementAt(0).sety_dis(-5);
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				root.getEntities().elementAt(0).sety_dis(5);
			}
			else if(e.getKeyCode() == KeyEvent.VK_CONTROL)
			{
				if(!ctrl_flag)
				{
					bullet bulL = new bullet(64,64,root.getEntities().elementAt(0).getx_pos(),root.getEntities().elementAt(0).gety_pos(),-10,0);
					bullet bulR = new bullet(64,64,root.getEntities().elementAt(0).getx_pos(),root.getEntities().elementAt(0).gety_pos(),10,0);
					bullet bulU = new bullet(64,64,root.getEntities().elementAt(0).getx_pos(),root.getEntities().elementAt(0).gety_pos(),0,-10);
					bullet bulD = new bullet(64,64,root.getEntities().elementAt(0).getx_pos(),root.getEntities().elementAt(0).gety_pos(),0,10);
					root.createEntity(bulL);
					root.createEntity(bulR);
					root.createEntity(bulU);
					root.createEntity(bulD);
					ctrl_flag = true;
				}
			}
		}
		
		public void keyReleased(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				root.getEntities().elementAt(0).setx_dis(0);
			}
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				root.getEntities().elementAt(0).setx_dis(0);
			}
			else if(e.getKeyCode() == KeyEvent.VK_UP)
			{
				root.getEntities().elementAt(0).sety_dis(0);
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				root.getEntities().elementAt(0).sety_dis(0);
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

class BasePanel extends JPanel{
	private Vector<entity> entities;
	
	BasePanel()
	{
		entities = new Vector<entity>();
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
	
	public Vector<entity> getEntities()
	{
		return entities;
	}
	
	public void createEntity(entity ent)
	{
		entities.addElement(ent);
	}
}

class PaintTask extends TimerTask{

	BasePanel paint;

	PaintTask(BasePanel to_paint)
	{
		paint = to_paint;
	}
	
	public void run()
	{
		paint.paintComponent((Graphics2D)paint.getGraphics());
	}
}

class UpdateTask extends TimerTask{

	BasePanel update;

	UpdateTask(BasePanel to_update)
	{
		update = to_update;
	}
	
	public void run()
	{
		update.updateComponent();
	}
}

class entity{
	protected int width;
	protected int height;
	protected Image image;
	protected int x_pos;
	protected int y_pos;
	protected int x_dis;
	protected int y_dis;
	
	entity()
	{
		width = 64;
		height = 64;
		x_pos = 512;
		y_pos = 512;
		
		try{
		File file = new File("sprites/square.bmp");
		image = ImageIO.read(file);
		}catch(Exception e){
			System.out.println("LOL NO FILE");
		}
	}
	
	entity(int w, int h, int x, int y, int x_p, int y_p, String s)
	{
		width = w;
		height = h;
		x_pos = x;
		y_pos = y;
		x_dis = x_p;
		y_dis = y_p;
		try{
		File file = new File("sprites/"+s+".bmp");
		image = ImageIO.read(file);
		}catch(Exception e){
			System.out.println("LOL NO FILE");
		}
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public int getx_pos()
	{
		return x_pos;
	}
	
	public int gety_pos()
	{
		return y_pos;
	}
	
	public void setXPos(int i)
	{
		x_pos = x_pos + i;
	}
	
	public void setYPos(int i)
	{
		y_pos = y_pos + i;
	}
	
	public void paint(Graphics2D g)
	{
		g.drawImage(this.image,this.x_pos,this.y_pos,null);
	}
	
	public void update()
	{
		x_pos += x_dis;
		y_pos += y_dis;
	}
	
	public void setx_dis(int x_d)
	{
		x_dis = x_d;
	}
	
	public void sety_dis(int y_d)
	{
		y_dis = y_d;
	}
}

class Player extends entity{
	
	Player(int w, int h, int x, int y, int x_p, int y_p)
	{
		super(w,h,x,y,x_p,y_p,"square");
	}
	
	public void update(Graphics2D g)
	{
		g.drawImage(this.image,this.x_pos,this.y_pos,null);
	}
}

class bullet extends entity{
	
	bullet(int w, int h, int x, int y, int x_p, int y_p)
	{
		super(w,h,x,y,x_p,y_p,"bullet");
	}
	
	public void paint(Graphics2D g)
	{
		AffineTransform a = g.getTransform();
		AffineTransform b = new AffineTransform();
		double r = 0;
		b.translate(x_pos+(width/2),y_pos+(height/2));
		if(y_dis < 0)
		{
			r = Math.toRadians(90);
		}
		else if(y_dis > 0)
		{
			r = Math.toRadians(270);
		}
		else if(x_dis > 0)
		{
			r = Math.toRadians(180);
		}
		b.rotate(r);
		b.translate(-x_pos-(width/2),-y_pos-(height/2));
		g.setTransform(b);
		g.drawImage(this.image,this.x_pos,this.y_pos,null);
		g.setTransform(a);
	}
}

class sprite{
	
}

class imageFactory{
	
}