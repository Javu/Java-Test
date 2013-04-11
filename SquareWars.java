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
			program.getRoot().updateComponent();
			program.getRoot().paintComponent((Graphics2D)program.getRoot().getGraphics());
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
					Bullet bullet_left = new Bullet(64,64,root.getEntities().elementAt(0).getXPos()-64,root.getEntities().elementAt(0).getYPos(),-10,0);
					Bullet bullet_right = new Bullet(64,64,root.getEntities().elementAt(0).getXPos()+64,root.getEntities().elementAt(0).getYPos(),10,0);
					Bullet bullet_up = new Bullet(64,64,root.getEntities().elementAt(0).getXPos(),root.getEntities().elementAt(0).getYPos()-64,0,-10);
					Bullet bullet_down = new Bullet(64,64,root.getEntities().elementAt(0).getXPos(),root.getEntities().elementAt(0).getYPos()+64,0,10);
					root.createEntity(bullet_left);
					root.createEntity(bullet_right);
					root.createEntity(bullet_up);
					root.createEntity(bullet_down);
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