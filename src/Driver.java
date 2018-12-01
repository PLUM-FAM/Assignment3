
public class Driver {
	
	
	public static void main(String args[])
	{
		int size = Integer.parseInt(args[0]);
		System.out.println("Running maze size: "+ size);
		Maze maze = new Maze(size);
		maze.printMaze();
		
	}
}
