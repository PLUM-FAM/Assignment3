/*
 * Program requires only one argument: the size of the maze to be generated. For example: "5" would be a 5x5 cave/maze.
 */
public class Driver {
	
	
	public static void main(String args[])
	{
		int size = Integer.parseInt(args[0]);
		System.out.println("Running maze size: "+ size);
		Maze maze = new Maze(size);		
	}
}
