import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.lang.*;

class Player extends Entity{
	Player(int w, int h, int x, int y, int x_p, int y_p, double rot)
	{
		super(w,h,x,y,x_p,y_p,rot,"square_black");
		solid = true;
		bounding_box = new BoundingBox(0,0,32,32);
	}
	
	public void update()
	{
		super.update();
		if(animation_counter == 4)
		{
			if(sprite_num == 2)
			{
				sprite_num = 3;
			}
			else
			{
				sprite_num = 2;
			}
			animation_counter = 0;
		}
		animation_counter++;
	}
}