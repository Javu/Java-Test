import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.lang.*;

class FastBullet extends Bullet{

	Bullet(int w, int h, int x, int y, int x_p, int y_p, double rot)
	{
		super(w,h,x,y,x_p,y_p,rot,0);
		sprite = "bullet_blue_special";
		bounding_box = new BoundingBox(0,0,5,5);
	}

	public void collide(Entity other)
	{
		super.collide(other);
	}
}