class BoundingBox{
	private int min_x;
	private int min_y;
	private int max_x;
	private int max_y;

	BoundingBox()
	{
		min_x = 0;
		min_y = 0;
		max_x = 0;
		max_y = 0;
	}

	BoundingBox(int new_min_x, int new_min_y, int new_max_x, int new_max_y)
	{	
		min_x = new_min_x;
		min_y = new_min_y;
		max_x = new_max_x;
		max_y = new_max_y;

		if (max_x < min_x)
		{
			max_x = min_x;
		}

		if (max_y < min_y)
		{
			max_y = min_y;
		}
	}

	public int min_x()
	{
		return min_x;
	}

	public int min_y()
	{
		return min_y;
	}

	public int max_x()
	{
		return max_x;
	}

	public int max_y()
	{
		return max_y;
	}

	public boolean min_x(int new_min_x)
	{
		if (new_min_x <= max_x)
		{
			min_x = new_min_x;
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean min_y(int new_min_y)
	{
		if (new_min_y <= max_y)
		{
			min_y = new_min_y;
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean max_x(int new_max_x)
	{
		if (new_max_x >= min_x)
		{
			max_x = new_max_x;
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean max_y(int new_max_y)
	{
		if (new_max_y >= min_y)
		{
			max_y = new_max_y;
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean equals(BoundingBox other)
	{
		if (min_x == other.min_x() && min_y == other.min_y() && max_x == other.max_x() && max_y == other.max_y())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}