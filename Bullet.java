import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.lang.*;

class Bullet extends Entity{
	
	Bullet(int w, int h, int x, int y, int x_p, int y_p)
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