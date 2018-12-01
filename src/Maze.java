import java.util.*;
public class Maze {
	
	int size;
	
	String [][] maze; 
	


	public Maze(int size) //constructor for maze takes in a size
	{
		this.size = size;
		maze = new String[size][size];

		
		for(int i = 0; i < size; i++) //initialize the array to blank strings
		{
			for(int j = 0; j < size; j++)
			{
				maze[i][j] = "";
			}
		}
		
		
		generateGold();
		generateWumpus();		
		generatePits();
		solve();
	}
	
	
	private void generatePits()
	{
		Random rand = new Random();
		int r; 
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				r = rand.nextInt(10) + 1;
				if(r<=2) //the spot is a pit (20% probability)
				{
					if(maze[i][j].indexOf('G') != -1) //if gold is in same position do not spawn
					{
					}else { //if gold is not already there we can spawn a pit
						try {
							if(i != 0 && j != 0) //if not starting point (0,0)
							{
								if(!maze[i][j].contains(" BR")) {
									maze[i][j] = maze[i][j].concat(" P");
								}
								try {
									if(!maze[i-1][j].contains(" BR"))
									{
										maze[i-1][j] = maze[i-1][j].concat(" BR");
									}
								}catch(ArrayIndexOutOfBoundsException e){}
								try {
									if(!maze[i][j-1].contains(" BR"))
									{
										maze[i][j-1] = maze[i][j-1].concat(" BR");
									}
								}catch(ArrayIndexOutOfBoundsException e){}
								try {
									if(!maze[i+1][j].contains(" BR"))
									{
										maze[i+1][j] = maze[i+1][j].concat(" BR");
									}
								}catch(ArrayIndexOutOfBoundsException e){}
								try {
									if(!maze[i][j+1].contains(" BR"))
									{
										maze[i][j+1] = maze[i][j+1].concat(" BR");
									}
								}catch(ArrayIndexOutOfBoundsException e){}
								
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
					}
				}
			}
		}
	}
	
	
	private void generateGold()
	{
		Random rand = new Random();
		
		int randX = rand.nextInt(size);
		int randY = rand.nextInt(size);
		
		
		try {
		maze[randX][randY] = maze[randX][randY].concat(" G");

		}catch(ArrayIndexOutOfBoundsException e){}
		//generate glitter surrounding gold tile
		try {
			if(!maze[randX-1][randY].contains(" GL"))
			{
				maze[randX-1][randY] = maze[randX-1][randY].concat(" GL");
			}
			
		}catch(ArrayIndexOutOfBoundsException e){}
		try {
			if(!maze[randX+1][randY].contains(" GL"))
			{
				maze[randX+1][randY] = maze[randX+1][randY].concat(" GL");
			}			
		}catch(ArrayIndexOutOfBoundsException e){}
		try {
			if(!maze[randX][randY-1].contains(" GL"))
			{
				maze[randX][randY-1] = maze[randX][randY-1].concat(" GL");
			}
			
		}catch(ArrayIndexOutOfBoundsException e){}
		try {
			if(!maze[randX][randY+1].contains(" GL"))
			{
				maze[randX][randY+1] = maze[randX][randY+1].concat(" GL");
			}
		}catch(ArrayIndexOutOfBoundsException e){}

	}
	
	private void generateWumpus()
	{
		Random rand = new Random();
		
		int randX = rand.nextInt(size);
		int randY = rand.nextInt(size);
		
		while(maze[randX][randY].equals("G") || (randX == 0 && randY == 0)) //if same spot as gold pick new.
		{
			randX = rand.nextInt(size);
			randY = rand.nextInt(size);
		}
		try {
			maze[randX][randY] = maze[randX][randY].concat(" W");
			
		}catch(ArrayIndexOutOfBoundsException e){}
		try {
			if(!maze[randX-1][randY].contains(" SM"))
			{
				maze[randX-1][randY] = maze[randX-1][randY].concat(" SM");
			}
			
		}catch(ArrayIndexOutOfBoundsException e){}
		try {
			if(!maze[randX+1][randY].contains(" SM"))
			{
				maze[randX+1][randY] = maze[randX+1][randY].concat(" SM");
			}			
		}catch(ArrayIndexOutOfBoundsException e){}
		try {
			if(!maze[randX][randY-1].contains(" SM"))
			{
				maze[randX][randY-1] = maze[randX][randY-1].concat(" SM");
			}
			
		}catch(ArrayIndexOutOfBoundsException e){}
		try {
			if(!maze[randX][randY+1].contains(" SM"))
			{
				maze[randX][randY+1] = maze[randX][randY+1].concat(" SM");
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		

	}
	
	public void printMaze()
	{
		String format = "\n----------";
		for(int a = 0; a < size-1; a++)
		{
			format = format.concat("-----------------");
		}
		
		for (int i = 0; i < size; ++i) 
        {
			System.out.println(format);
			System.out.println("");
			System.out.print("|\t");
        
            for (int j = 0; j < size; ++j)
            {
				System.out.print(maze[i][j] + "\t|\t");
            }

        }
		System.out.println("\n" + format);


	}

	public void solve()
	{
		Wumpus wump = new Wumpus(size, maze);
		wump.solve();
	}
	
}
