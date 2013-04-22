import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.lang.*;

class BlueSquare extends Entity{
	BlueSquare(int w, int h, int x, int y, int x_p, int y_p, double rot)
	{
		super(w,h,x,y,x_p,y_p,rot,"square_blue");
		shoot_cooldown = 0;
		special_cooldown = 0;
		max_health = 3;
		current_health = 3;
		max_ammo = 3;
		current_ammo = 3;
		ammo_reload_time = 60;
		current_reload_time = 0;
	}
	
	public void update()
	{
		super.update();
		if(animation_counter == 4)
		{
			if(sprite_num == 2)
			{
				sprite_num = 1;
			}
			else
			{
				sprite_num = 0;
			}
			animation_counter = 0;
		}
		animation_counter++;
	}

	public void shoot(Vector<Entity> entities)
	{
		if (shoot_cooldown == 0 && current_ammo > 0)
		{
			Bullet bullet_left = new Bullet(5,5,x_pos-3,y_pos+15,-10,0,0,0);
			Bullet bullet_right = new Bullet(5,5,x_pos+36,y_pos+15,10,0,180,0);
			Bullet bullet_up = new Bullet(5,5,x_pos+15,y_pos-3,0,-10,90,0);
			Bullet bullet_down = new Bullet(5,5,x_pos+15,y_pos+33,0,10,270,0);
			entities.add(bullet_left);
			entities.add(bullet_right);
			entities.add(bullet_up);
			entities.add(bullet_down);

			current_ammo--;
		}	
	}

	public void special(Vector<Entity> entities)
	{
		if (special_cooldown == 0)
		{
			special_cooldown = 60 * 5;
			FastBullet bullet_left = new FastBullet(12,9,x_pos-3,y_pos+15,-10,0,0,0);
			FastBullet bullet_right = new FastBullet(12,9,x_pos+36,y_pos+15,10,0,180,0);
			FastBullet bullet_up = new FastBullet(12,9,x_pos+15,y_pos-3,0,-10,90,0);
			FastBullet bullet_down = new FastBullet(12,9,x_pos+15,y_pos+33,0,10,270,0);
			entities.add(bullet_left);
			entities.add(bullet_right);
			entities.add(bullet_up);
			entities.add(bullet_down);
		}
	}
}