import java.util.*;


/*
 * tiles class is for the strucutre of each instance of a tile. Each instance of a tile holds many flags so that the adventerur can determine which are
 * safe, which are dangerous, and which hold the gold he is searching for.
 */
class tiles 
{
	//variation of flags that the adventurer logic uses.
	int safe = 0;
	int unsafe = 0;
	int wump = 0;
	int pit = 0;
	int gold = 0;
	int doubt_pit = 0;
	int doubt_wumpus = 0;
	String env;
	int num = 0;
	int br = 0; //right
	int bl = 0; //left
	int bu = 0; //up
	int bd = 0; //down
	int visited = 0;
	int l, r, u, d; //left right up and down
	String back = "";

	
	/*
	 * constructor for tile, handles the outer edges of the maze right at initialization so that we do not have to calculate for them later.
	 * I.E. looking at the top left coordinate of the maze we know that the tile above it and to the left do not exist. We flag them as such so that
	 * our adventerur does not attempt to venture there (out of bounds).
	 */
    tiles(String s, int n, int size) 
    {
		env = s;
		num = n;
		l = r = u = d = 0;
        if ((n-1) % size == 0) //left wall
        {
			bl = 1;
        }

		if (n % size == 0) //right wall
        {
            br = 1;
        }

        if (n == 1) //top left
        {
			bu = 1;
			bl = 1;
        }
        
        if (n == size*size - (size-1)) 	//bottom left 
        {
			bd = 1;
			bl = 1;
        }
        
        if (n == size) 	//top right
        {
			bu = 1;
			br = 1;
        }
        
        if (n == size*size) //bottom right
        {
			bd = 1;
			br = 1;
		}
	}

    
    /*
     * sense returns an integer flag according to the sense/string found in the current location.
     */
    int sense() 
    {
        if (env.contains("BR")) //breeze
        {
            return 1;
        }
            
        else if (env.contains("SM")) //smell
        {
            return 2;
        }
            
        else if (env.contains("G")) //gold
            return 3;
            
        if (env.contains("W")) //wumpus
        {
            return 4;
        }
            
        else
        {
			return 0;
        }
	}
}