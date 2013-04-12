import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.lang.*;

class Renderer{
	private Map<String,BufferedImage> images;
	
	Renderer()
	{
		images = new HashMap<String,BufferedImage>();
	}
	
	public Graphics2D render(Graphics2D g, Vector<Entity> entities)
	{
		for(int i=0;i<entities.size();i++)
		{
			AffineTransform a = g.getTransform();
			AffineTransform b = new AffineTransform();
			b.translate(entities.elementAt(i).getXPos()+(entities.elementAt(i).getWidth()/2),entities.elementAt(i).getYPos()+(entities.elementAt(i).getHeight()/2));
			b.rotate(Math.toRadians(entities.elementAt(i).getRotation()));
			b.translate(-entities.elementAt(i).getXPos()-(entities.elementAt(i).getWidth()/2),-entities.elementAt(i).getYPos()-(entities.elementAt(i).getHeight()/2));
			g.setTransform(b);
			g.drawImage(images.get(entities.elementAt(i).getSprite()),entities.elementAt(i).getXPos(),entities.elementAt(i).getYPos(),null);
			g.setTransform(a);
		}
		return g;
	}
	
	public void loadImage(String sprite)
	{
		if(!images.containsKey(sprite))
		{
			BufferedImage image = null;
			try{
			File file = new File("sprites/"+sprite+".png");
			image = ImageIO.read(file);
			}catch(Exception e){
				System.out.println("LOL NO FILE");
			}
			for(int i=0;i < image.getWidth();i++)
			{
				for(int j=0;j < image.getHeight();j++)
				{
					
					if(image.getRGB(i,j) == new Color(255,0,255,255).getRGB())
					{
						image.setRGB(i,j,new Color(255,0,255,0).getRGB());
					}
				}
			}
			images.put(sprite,image);
		}
	}
	
	public void deleteImage(String sprite)
	{
		images.remove(sprite);
	}
}