import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.lang.*;

class CollisionDetector
{
	CollisionDetector()
	{

	}

	public void detectCollisions(Vector<Entity> moved_entities, Vector<Entity> all_entities)
	{
		for (int i=0; i<moved_entities.size(); i++)
		{
			Entity current_subject = moved_entities.get(i);
			BoundingBox subject_box = current_subject.getWorldBoundingPoints();

			for (int j=0; j<all_entities.size(); j++)
			{
				Entity current_object = all_entities.get(j);
				BoundingBox object_box = current_object.getWorldBoundingPoints();

				if (subject_box.min_x() >= object_box.min_x() && subject_box.min_y() >= object_box.min_y() && subject_box.max_x() <= object_box.max_x() && subject_box.max_y() <= object_box.max_y())
				{
					current_subject.collide(current_object);
					current_object.collide(current_subject);
				}
			}
		}
	}
}