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
		
		//generate the layout of the maze
		generateGold();
		generateWumpus();		
		generatePits();
		printMaze();
		//then place adventerur and solve.
		solve();
	}
	
	/*
	 * generatePits places a pit or "P" on the approperate coordinate locations based on a 20% probability (see assignment description)
	 * it also generates the senses for the pit. I.E. the breezes in the 4 surrounding locations. The string "BR" represents a breeze.
	 * The implementation does not allow a pit to be spawned on the same location as the gold.
	 */
	private void generatePits()
	{
		Random rand = new Random(); //random number for probability user
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
								try {													//these try catch blocks are here to catch placement attempts that are out of the bounds of the 2D array/maze/cave
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
	
	/*
	 * generateGold picks a random location that is not the start location to place the gold represented with the string "G"
	 * it also places the string "GL" on the surrounding locations to represent the glitter sense.
	 * 
	 * Again, all of these try catch blocks are to prevent the attempted placement of a sense/object that is out of bounds (out of the range of the maze)
	 */
	private void generateGold()
	{
		Random rand = new Random();
		
		int randX = rand.nextInt(size); //random x coord for placement
		int randY = rand.nextInt(size); //random y coord for placement
		
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
	
	/*
	 * generateWumpus places a "W" on a random maze/cave location to represent the Wumpus, it also places the sense "smell" represented by
	 * the string "SM" in the surrounding locations.
	 * The implementation does not allow for the generation of a wumpus on the same location as gold has already been spawned.
	 * Again, all of these try catch blocks are to prevent the attempted placement of a sense/object that is out of bounds (out of the range of the maze)
	 */
	private void generateWumpus()
	{
		Random rand = new Random(); 
		
		int randX = rand.nextInt(size); //random x coord
		int randY = rand.nextInt(size); //random y coord
		
		while(maze[randX][randY].equals("G") || (randX == 0 && randY == 0)) //if same spot as gold pick new random values.
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
	
	
	/*
	 * print maze is a helper method to display the generated cave in the program's output/console. 
	 */
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

	
	/*
	 * solve is a middle man helper class to help faciliate the class/object separation of the program.
	 */
	public void solve()
	{	//create a new instance of wumpus 
		Wumpus wump = new Wumpus(size, maze);
		//and solve it
		wump.solve();
	}
	
}
