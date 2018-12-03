import java.util.*;

class Wumpus 
{
	static int scream = 0; //keeping track of dead wumpus
	static int score = 0; //keeping track of overall score
	static int complete = 0; //keeping track of ended game/round
	
	int size;
	String[][] maze; //2d array of strings to represent the cave/maze, each location is a string that holds substring values for each sense/object it contains.
	
	
	/*
	 * Constructor just initalizes size and maze global variables.
	 */
	public Wumpus(int size, String[][] maze)
	{
		this.size = size;
		this.maze = maze;
	}

	
	/*
	 * helper method to notify the user of the score and the end of the game. Also ends the program if called.
	 */
	public void endGame()
	{
		System.out.println("Game over");
		System.out.println("Final Score: " + score);
		System.exit(0);
	}

	
	/*
	 * helper method to check the current tile/location for senses. See tiles class for the list of senses.
	 */
	static boolean check(tiles tiles) {
        int temp = tiles.sense();
        
        if (temp == 1 || temp == 2)
        {
			return false;
        }

		return true;
	}

	/*
	 * Mess of logical statments for the actual navigation of the maze and the prediction of tile contents.
	 * for reference from tile: 1 = breeze, 2 = smell, 3 = gold, 4 = wumpus, 5 = glitter, 6 = pit.
	 */
    public void solve() 
    {
		String w[][] = maze;
    
        System.out.println("\n\nSolving...");

		tiles tiles[] = new tiles[(size*size) + 1];
		
		int c = 1; //index for 1d tiles array
		
        out: for (int i = 0; i < size; i++) 
        {
            for (int j = 0; j < size; j++) 
            {
                if (c > (size*size)) //if larger than total instance size
                {
					break out;
                }

				tiles[c] = new tiles(w[i][j], c, size); //create a new instance of tile for this coordinate
				++c;
			}
		}

		//setting the start position to save and visited
		tiles[1].safe = 1;
		tiles[1].visited = 1;

		int pos = 1; //current position based on tile number
		int condition; //flag from sense()
		int limit = 0; //counter to help determine when we are stuck in an unsolveable instance.
		
		String temp1, temp2; //flags for logical statements
        
        do 
        {
			limit++;
			condition = -1;

            if (tiles[pos].env.contains("G")) 
            {
				complete = 1;
				System.out.println("Gold Found! Yippie kai ayyeeee");
				break;
			}
            if (tiles[pos].env.contains("W")) 
            {
            	System.out.println("Eaten by wumpus. Oops." );
            	score *= -1;
            	endGame();
            }

			if (tiles[pos].br != 1 && tiles[pos].r != 1 && tiles[pos + 1].doubt_pit < 1 && tiles[pos + 1].doubt_wumpus < 1
					&& tiles[pos + 1].pit != 1 && tiles[pos + 1].wump != 1 && !(tiles[pos].back.contains("r")
                            && (tiles[pos].l != 1 || tiles[pos].u != 1 || tiles[pos].d != 1) && check(tiles[pos]))) 
            {
			
				temp1 = "l";
				tiles[pos].r = 1;
                pos++;
                
                
                score++;
				
				tiles[pos].back += temp1;
				
				condition = tiles[pos].sense(); //condition is what sense/object is detected with the sense() method
                if (condition == 3) 
                {
					complete = 1;
					break;
                }

                else if (condition == 1 && tiles[pos].visited == 0) //logical statements for doubting whether there is a pit or not.
                {
					if (tiles[pos].br != 1 && tiles[pos + 1].safe != 1)
						tiles[pos + 1].doubt_pit += 1;
					if (tiles[pos].bu != 1 && (pos - size) >= 1 && tiles[pos - size].safe != 1)
						tiles[pos - size].doubt_pit += 1;
					if (tiles[pos].bl != 1 && tiles[pos - 1].safe != 1)
						tiles[pos - 1].doubt_pit += 1;
					if (tiles[pos].bd != 1 && (pos + size) <= size*size && tiles[pos + size].safe != 1)
						tiles[pos + size].doubt_pit += 1;

					tiles[pos].safe = 1;
                }

                else if (condition == 2 && tiles[pos].visited == 0) //logical statements for doubting whether there is a wumpus or not.
                {
                    if (tiles[pos].br != 1 && tiles[pos + 1].safe != 1)
                    {
						tiles[pos + 1].doubt_wumpus += 1;
                    }

                    if (tiles[pos].bu != 1 && (pos - size) >= 1 && tiles[pos - size].safe != 1)
                    {
						tiles[pos - size].doubt_wumpus += 1;
                    }

                    if (tiles[pos].bl != 1 && tiles[pos - 1].safe != 1)
                    {
						tiles[pos - 1].doubt_wumpus += 1;
                    }

					if (tiles[pos].bd != 1 && (pos + size) <= size*size && tiles[pos + size].safe != 1)
                    {
						tiles[pos + size].doubt_wumpus += 1;
                    }

					tiles[pos].safe = 1;
				}
                
                /*
				 * else if(condition==4) { score=score+100; tiles[pos].safe=1; }
				 */
                else if (condition == 0)
                {
					tiles[pos].safe = 1;
                }

				tiles[pos].visited = 1;
            }
            
			else if (tiles[pos].bl != 1 && tiles[pos].l != 1 && tiles[pos - 1].doubt_pit < 1 && tiles[pos - 1].doubt_wumpus < 1
					&& tiles[pos - 1].pit != 1 && tiles[pos - 1].wump != 1 && !(tiles[pos].back.contains("l")
                            && (tiles[pos].r != 1 || tiles[pos].u != 1 || tiles[pos].d != 1) && check(tiles[pos]))) 
            {
				
				temp1 = "r";
				
				tiles[pos].l = 1;
				pos = pos - 1;
				score++;

				tiles[pos].back += temp1;

				condition = tiles[pos].sense();
                if (condition == 3) 
                {
					complete = 1;
					break;
                } 
                
                else if (condition == 1 && tiles[pos].visited == 0) //logical statements determining that there is a pit.
                {
					if (tiles[pos].br != 1 && tiles[pos + 1].safe != 1)
						tiles[pos + 1].doubt_pit += 1;
					if (tiles[pos].bu != 1 && (pos - size) >= 1 && tiles[pos - size].safe != 1)
						tiles[pos - size].doubt_pit += 1;
					if (tiles[pos].bl != 1 && tiles[pos - 1].safe != 1)
						tiles[pos - 1].doubt_pit += 1;
					if (tiles[pos].bd != 1 && (pos + size) <=size*size && tiles[pos + size].safe != 1)
						tiles[pos + size].doubt_pit += 1;

					tiles[pos].safe = 1;
                } 
                
                else if (condition == 2 && tiles[pos].visited == 0) //logical statements determining if there is a wumpus
                {
					if (tiles[pos].br != 1 && tiles[pos + 1].safe != 1)
                    {
						tiles[pos + 1].doubt_wumpus += 1;
                    }
                        
                    if (tiles[pos].bu != 1 && (pos - size) >= 1 && tiles[pos - size].safe != 1)
                    {
						tiles[pos - size].doubt_wumpus += 1;
                    }
                        
                    if (tiles[pos].bl != 1 && tiles[pos - 1].safe != 1)
                    {
						tiles[pos - 1].doubt_wumpus += 1;
                    }
                        
                    if (tiles[pos].bd != 1 && (pos + size) <= size*size && tiles[pos + size].safe != 1)
                    {
						tiles[pos + size].doubt_wumpus += 1;
                    }

					tiles[pos].safe = 1;
                } 
                
                else if (condition == 0)
                {
					tiles[pos].safe = 1;
                }

				tiles[pos].visited = 1;

            } 
            
            else if (tiles[pos].bu != 1 && tiles[pos].u != 1 && (pos - size) >= 1 && tiles[pos - size].doubt_pit < 1
					&& tiles[pos - size].doubt_wumpus < 1 && tiles[pos - size].pit != 1 && tiles[pos - 1].wump != 1
					&& !(tiles[pos].back.contains("u") && (tiles[pos].l != 1 || tiles[pos].r != 1 || tiles[pos].d != 1)
                            && check(tiles[pos]))) 
            {
				temp1 = "d";
			
				tiles[pos].u = 1;
				pos = pos - size;
				score++;
	
				tiles[pos].back += temp1;
				
				condition = tiles[pos].sense(); //condition holds the sense/object returned from sense()
                if (condition == 3) 
                {
					complete = 1;
					break;
                } 
                
                else if (condition == 1 && tiles[pos].visited == 0) 
                {
                    if (tiles[pos].br != 1 && tiles[pos + 1].safe != 1)
                    {
						tiles[pos + 1].doubt_pit += 1;
                    }

					if (tiles[pos].bu != 1 && (pos - size) >= 1 && tiles[pos - size].safe != 1)
                    {
                        tiles[pos - size].doubt_pit += 1;
                    }   
                        
					if (tiles[pos].bl != 1 && tiles[pos - 1].safe != 1)
                    {
                        tiles[pos - 1].doubt_pit += 1;
                    }    
                        
					if (tiles[pos].bd != 1 && (pos + size) <= size*size && tiles[pos + size].safe != 1)
                    {
                        tiles[pos + size].doubt_pit += 1;
                    }

                    tiles[pos].safe = 1;
                } 
                
                else if (condition == 2 && tiles[pos].visited == 0) 
                {
					if (tiles[pos].br != 1 && tiles[pos + 1].safe != 1)
						tiles[pos + 1].doubt_wumpus += 1;
					if (tiles[pos].bu != 1 && (pos - size) >= 1 && tiles[pos - size].safe != 1)
						tiles[pos - size].doubt_wumpus += 1;
					if (tiles[pos].bl != 1 && tiles[pos - 1].safe != 1)
						tiles[pos - 1].doubt_wumpus += 1;
					if (tiles[pos].bd != 1 && (pos + size) <= size*size && tiles[pos + size].safe != 1)
						tiles[pos + size].doubt_wumpus += 1;

					tiles[pos].safe = 1;
                } 

                else if (condition == 0)
                {
					tiles[pos].safe = 1;
                }

				tiles[pos].visited = 1;
            } 
            
            else if (tiles[pos].bd != 1 && tiles[pos].d != 1 && (pos + size) <= size*size && tiles[pos + size].doubt_pit < 1
					&& tiles[pos + size].doubt_wumpus < 1 && tiles[pos + size].pit != 1 && tiles[pos + size].wump != 1) 
            {
				temp1 = "u";
		
				tiles[pos].d = 1;
				pos = pos + size;
        
				score++;
				// tiles[pos].visited=1;

				tiles[pos].back += temp1;
		
				condition = tiles[pos].sense();
                if (condition == 3) 
                {
					complete = 1;
					break;
                } 
                
                else if (condition == 1 && tiles[pos].visited == 0) 
                {
                    if (tiles[pos].br != 1 && tiles[pos + 1].safe != 1)
                    {
						tiles[pos + 1].doubt_pit += 1;
                    }

                    if (tiles[pos].bu != 1 && (pos - size) >= 1 && tiles[pos - size].safe != 1)
                    {
						tiles[pos - size].doubt_pit += 1;
                    }

                    if (tiles[pos].bl != 1 && tiles[pos - 1].safe != 1)
                    {
						tiles[pos - 1].doubt_pit += 1;
                    }

                    if (tiles[pos].bd != 1 && (pos + size) <= size*size && tiles[pos + size].safe != 1)
                    {
						tiles[pos + size].doubt_pit += 1;
                    }

					tiles[pos].safe = 1;
                } 
                else if (condition == 2 && tiles[pos].visited == 0) 
                {
                    if (tiles[pos].br != 1 && tiles[pos + 1].safe != 1)
                    {
						tiles[pos + 1].doubt_wumpus += 1;
                    }

                    if (tiles[pos].bu != 1 && (pos - size) >= 1 && tiles[pos - size].safe != 1)
                    {
						tiles[pos - size].doubt_wumpus += 1;
                    }

                    if (tiles[pos].bl != 1 && tiles[pos - 1].safe != 1)
                    {
						tiles[pos - 1].doubt_wumpus += 1;
                    }

                    if (tiles[pos].bd != 1 && (pos + size) <= size*size && tiles[pos + size].safe != 1)
                    {
						tiles[pos + size].doubt_wumpus += 1;
                    }

					tiles[pos].safe = 1;
                } 
                
                else if (condition == 0)
                {
					tiles[pos].safe = 1;
                }

				tiles[pos].visited = 1;
            } 
            
            else if (limit > (size*10)) 
            {
				int temp3 = pos;
				int flag_1 = 0, flag2 = 0, flag3 = 0, flag4 = 0;
				
                while (tiles[pos].visited == 1 && tiles[pos].br != 1) 
                {
					pos++;
					score++;
				}

                if (tiles[pos].pit == 1 || tiles[pos].wump == 1 || (tiles[pos].br == 1 && tiles[pos].visited == 1 && tiles[pos].safe != 1)) 
                {
					pos = temp3;
					flag_1 = 1;
				}

                if (flag_1 == 0)
                {
					tiles[pos].back += "l";
                }

                while (pos - size >= 1 && tiles[pos].bu != 1 && tiles[pos].visited == 1) 
                {
					pos -= size;
					score++;
				}

                if (tiles[pos].pit == 1 || tiles[pos].wump == 1	|| (tiles[pos].bu == 1 && tiles[pos].visited == 1 && tiles[pos].safe != 1)) 
                {
					pos = temp3;
					flag3 = 1;
				}

                if (flag3 == 0)
                {
					tiles[pos].back += "d";
                }

				// if(!(tiles[pos].back.contains("l") && (tiles[pos].r!=1 || tiles[pos].u!=1
				// || tiles[pos].d!=1) && check(tiles[pos]) ))
                while (tiles[pos].visited == 1 && tiles[pos].bl != 1) 
                {
					pos--;
					score++;
				}

                if (tiles[pos].pit == 1 || tiles[pos].wump == 1	|| (tiles[pos].bl == 1 && tiles[pos].visited == 1 && tiles[pos].safe != 1)) 
                {
					pos = temp3;
					flag2 = 1;
				}

                if (flag2 == 0)
                {
					tiles[pos].back += "r";
                }

				// if(!(tiles[pos].back.contains("d") && (tiles[pos].l!=1 || tiles[pos].r!=1
				// || tiles[pos].u!=1) && check(tiles[pos]) ))
                while (pos + size <= size*size && tiles[pos].bd != 1 && tiles[pos].visited == 1) 
                {
					pos += size;
					score++;
				}

                if (tiles[pos].pit == 1 || tiles[pos].wump == 1 || (tiles[pos].bd == 1 && tiles[pos].visited == 1 && tiles[pos].safe != 1)) 
                {
					pos = temp3;
					flag4 = 1;
                }

                if (flag4 == 0)
                {
					tiles[pos].back += "u";
                }

				tiles[pos].safe = 1;
				tiles[pos].visited = 1;
				limit = 0;
            }
            
            if (tiles[pos].env.contains("W") && scream != 1)  //if the current location contains the wumpus and the scream has been heard (it has been killed)
            {
				score += 100;
				scream = 1;
				tiles[pos].safe = 1;
				tiles[pos].env.replace("W", " ");
                
                for (int l = 1; l <= size*size; l++) 
                {
					tiles[l].doubt_wumpus = 0;
					tiles[l].env.replace("SM", " ");
				}
			}

            if (tiles[pos].env.contains("P")) //we are in the same location as a pit. therefore we die and the game is over.
            {
				score += 50;
				tiles[pos].pit = 1;
				System.out.println("\n\nFallen in pit of position " + pos + ".");
            	score *= -1;
				endGame();
			}

            for (int k = 1; k <= size*size; k++) 
            {
				if (tiles[k].doubt_pit == 1 && tiles[k].doubt_wumpus == 1) 
                {
					tiles[k].doubt_pit = 0;
					tiles[k].doubt_wumpus = 0;
					tiles[k].safe = 1;
				}
			}

            for (int y = 1; y <= size*size; y++) 
            {
				if (tiles[y].doubt_wumpus > 1) 
                {
					tiles[y].wump = 1;
                    for (int h = 1; h <= size*size; ++h) 
                    {
                        if (h != y) 
                        {
							tiles[h].doubt_wumpus
 = 0;
							tiles[h].env.replace("SM", " ");
						}
					}
				}
			}

            for (int y = 1; y <= size*size; y++) 
            {
                if (tiles[y].doubt_pit > 1) 
                {
					tiles[y].pit = 1;
					// System.out.println("\nPit confirmed at position "+y);
				}
            }

		} while (complete == 0); //end of do while loop

		if (complete == 1) {

			score *= -1; //negate the score (we were counting up initially)
			score *= 2; //double the score to take the same path back to the start
			score += 1000; //gold has been found only if complete flag is set.
		}
		System.out.println("Total score for adventurer is  " + score
				+ ".\n(This includes the path cost to get out.)");

	}
}
