import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.lang.*;

class Player extends Entity{
	
	Player(int w, int h, int x, int y, int x_p, int y_p)
	{
		super(w,h,x,y,x_p,y_p,"square");
	}
	
	public void update(Graphics2D g)
	{
		g.drawImage(this.image,this.x_pos,this.y_pos,null);
	}
}