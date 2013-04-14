import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.lang.*;

class Entity{
	protected int width;
	protected int height;
	protected String sprite;
	protected int x_pos;
	protected int y_pos;
	protected int x_dis;
	protected int y_dis;
	protected BoundingBox bounding_box;
	protected boolean destroyed;
	protected double rotation;
	
	Entity()
	{
		width = 64;
		height = 64;
		x_pos = 512;
		y_pos = 512;
		rotation = 0;

		bounding_box = new BoundingBox(0,0,width,height);
		destroyed = false;
	}
	
	Entity(int w, int h, int x, int y, int x_p, int y_p, double rot, String s)
	{
		width = w;
		height = h;
		x_pos = x;
		y_pos = y;
		x_dis = x_p;
		y_dis = y_p;
		rotation = rot;

		bounding_box = new BoundingBox(0,0,width,height);

		destroyed = false;

		sprite = s;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
	
	public int getXPos()
	{
		return x_pos;
	}
	
	public int getYPos()
	{
		return y_pos;
	}
	
	public double getRotation()
	{
		return rotation;
	}
	
	public String getSprite()
	{
		return sprite;
	}
	
	public void setXPos(int i)
	{
		x_pos = x_pos + i;
	}
	
	public void setYPos(int i)
	{
		y_pos = y_pos + i;
	}
	
	public void update()
	{
		x_pos += x_dis;
		y_pos += y_dis;
	}
	
	public void setXDis(int x_d)
	{
		x_dis = x_d;
	}
	
	public void setYDis(int y_d)
	{
		y_dis = y_d;
	}

	public BoundingBox getBoundingBox()
	{
		return bounding_box;
	}

	public BoundingBox setBoundingBox(int min_x, int min_y, int max_x, int max_y)
	{
		bounding_box = new BoundingBox(min_x, min_y, max_x, max_y);
		return bounding_box;
	}

	public BoundingBox getWorldBoundingPoints()
	{
		BoundingBox world_bounding_points = new BoundingBox(x_pos - bounding_box.min_x(), y_pos - bounding_box.min_y(), x_pos + bounding_box.max_x(), y_pos + bounding_box.max_y());

		return world_bounding_points;
	}

	public void collide(Entity other)
	{

	}
	
	public void setRotation(double r)
	{
		rotation = r;
	}
	
	public void setSprite(String image)
	{
		sprite = image;
	}
}
