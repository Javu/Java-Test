import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.lang.*;

class Square extends Entity{
	protected int shoot_cooldown;
	protected int special_cooldown;
	protected int max_ammo;
	protected int current_ammo;
	protected int max_health;
	protected int current_health;
	protected int ammo_reload_time;
	protected int current_reload_time;

	Square(int w, int h, int x, int y, int x_p, int y_p, double rot, String s)
	{
		super(w,h,x,y,x_p,y_p,rot,s);
		solid = false;
		bounding_box = new BoundingBox(0,0,32,32);
		shoot_cooldown = 0;
		special_cooldown = 0;
		max_ammo = 0;
		current_ammo = 0;
		max_health = 0;
		current_health = 0;
		ammo_reload_time = 0;
		current_reload_time = 0;
	}
	
	public void update()
	{
		if (shoot_cooldown > 0)
		{
			shoot_cooldown--;
		}
		if (special_cooldown > 0)
		{
			special_cooldown--;
		}
		if (current_ammo < max_ammo)
		{
			current_reload_time++;
			if (current_reload_time == ammo_reload_time)
			{
				current_ammo++;
				current_reload_time = 0;
			}
		}

		super.update();
	}

	public void collision(Entity other)
	{
		super.collision(other);
		if (other.solid == true)
		{
			if (x_dis > 0)
			{
				x_pos = other.getWorldBoundingPoints().minX() - 1 - bounding_box.maxX();
			}
			else if (x_dis < 0)
			{
				x_pos = other.getWorldBoundingPoints().maxX() + 1;
			}
			else if (y_dis > 0)
			{
				y_pos = other.getWorldBoundingPoints().minY() - 1 - bounding_box.maxY();
			}
			else if (y_dis < 0)
			{
				y_pos = other.getWorldBoundingPoints().maxY() + 1;
			}
		}
	}

	public void shoot(Vector<Entity> entities)
	{

	}
}