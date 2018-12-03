import java.util.*;

class Wumpus 
{
	static int scream = 0;
	static int score = 0;
	static int complete = 0;
	
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
	static boolean check(tiles t) {
        int temp = t.sense();
        
        if (temp == 1 || temp == 2)
        {
			return false;
        }

		return true;
	}

    public void solve() 
    {
		String w[][] = maze;
    
        System.out.println("\n\nSolving...");

		tiles t[] = new tiles[(size*size) + 1];
		
		int c = 1; //index for 1d tiles array
		
        out: for (int i = 0; i < size; i++) 
        {
            for (int j = 0; j < size; j++) 
            {
                if (c > (size*size))
                {
					break out;
                }

				t[c] = new tiles(w[i][j], c, size);
				++c;
			}
		}

		//setting the start position to save and visited
		t[1].safe = 1;
		t[1].visited = 1;

		int pos = 1;
		int condition;
		int limit = 0;
		
		String temp1, temp2; //flags for logical statements
        
        do 
        {
			limit++;
			condition = -1;

            if (t[pos].env.contains("G")) 
            {
				complete = 1;
				System.out.println("Gold Found! Yippie kai ayyeeee");
				break;
			}
            if (t[pos].env.contains("W")) 
            {
            	System.out.println("Eaten by wumpus. Oops." );
            	score *= -1;
            	endGame();
            }

			if (t[pos].br != 1 && t[pos].r != 1 && t[pos + 1].doubt_pit < 1 && t[pos + 1].doubt_wump < 1
					&& t[pos + 1].pit != 1 && t[pos + 1].wump != 1 && !(t[pos].back.contains("r")
                            && (t[pos].l != 1 || t[pos].u != 1 || t[pos].d != 1) && check(t[pos]))) 
            {
			
				temp1 = "l";
				t[pos].r = 1;
                pos++;
                
                
                score++;
				
				t[pos].back += temp1;
				
				condition = t[pos].sense();
                if (condition == 3) 
                {
					complete = 1;
					break;
                }

                else if (condition == 1 && t[pos].visited == 0) 
                {
					if (t[pos].br != 1 && t[pos + 1].safe != 1)
						t[pos + 1].doubt_pit += 1;
					if (t[pos].bu != 1 && (pos - size) >= 1 && t[pos - size].safe != 1)
						t[pos - size].doubt_pit += 1;
					if (t[pos].bl != 1 && t[pos - 1].safe != 1)
						t[pos - 1].doubt_pit += 1;
					if (t[pos].bd != 1 && (pos + size) <= size*size && t[pos + size].safe != 1)
						t[pos + size].doubt_pit += 1;

					t[pos].safe = 1;
                }

                else if (condition == 2 && t[pos].visited == 0) 
                {
                    if (t[pos].br != 1 && t[pos + 1].safe != 1)
                    {
						t[pos + 1].doubt_wump += 1;
                    }

                    if (t[pos].bu != 1 && (pos - size) >= 1 && t[pos - size].safe != 1)
                    {
						t[pos - size].doubt_wump += 1;
                    }

                    if (t[pos].bl != 1 && t[pos - 1].safe != 1)
                    {
						t[pos - 1].doubt_wump += 1;
                    }

					if (t[pos].bd != 1 && (pos + size) <= size*size && t[pos + size].safe != 1)
                    {
                        t[pos + size].doubt_wump += 1;
                    }

					t[pos].safe = 1;
				}
                
                /*
				 * else if(condition==4) { score=score+100; t[pos].safe=1; }
				 */
                else if (condition == 0)
                {
					t[pos].safe = 1;
                }

				t[pos].visited = 1;
            }
            
            else if (t[pos].bl != 1 && t[pos].l != 1 && t[pos - 1].doubt_pit < 1 && t[pos - 1].doubt_wump < 1
					&& t[pos - 1].pit != 1 && t[pos - 1].wump != 1 && !(t[pos].back.contains("l")
                            && (t[pos].r != 1 || t[pos].u != 1 || t[pos].d != 1) && check(t[pos]))) 
            {
				
				temp1 = "r";
				
				t[pos].l = 1;
				pos = pos - 1;
				score++;

				t[pos].back += temp1;

				condition = t[pos].sense();
                if (condition == 3) 
                {
					complete = 1;
					break;
                } 
                
                else if (condition == 1 && t[pos].visited == 0) 
                {
					if (t[pos].br != 1 && t[pos + 1].safe != 1)
						t[pos + 1].doubt_pit += 1;
					if (t[pos].bu != 1 && (pos - size) >= 1 && t[pos - size].safe != 1)
						t[pos - size].doubt_pit += 1;
					if (t[pos].bl != 1 && t[pos - 1].safe != 1)
						t[pos - 1].doubt_pit += 1;
					if (t[pos].bd != 1 && (pos + size) <=size*size && t[pos + size].safe != 1)
						t[pos + size].doubt_pit += 1;

					t[pos].safe = 1;
                } 
                
                else if (condition == 2 && t[pos].visited == 0) 
                {
					if (t[pos].br != 1 && t[pos + 1].safe != 1)
                    {
                        t[pos + 1].doubt_wump += 1;
                    }
                        
                    if (t[pos].bu != 1 && (pos - size) >= 1 && t[pos - size].safe != 1)
                    {
                        t[pos - size].doubt_wump += 1;
                    }
                        
                    if (t[pos].bl != 1 && t[pos - 1].safe != 1)
                    {
                        t[pos - 1].doubt_wump += 1;
                    }
                        
                    if (t[pos].bd != 1 && (pos + size) <= size*size && t[pos + size].safe != 1)
                    {
						t[pos + size].doubt_wump += 1;
                    }

					t[pos].safe = 1;
                } 
                
                else if (condition == 0)
                {
					t[pos].safe = 1;
                }

				t[pos].visited = 1;

            } 
            
            else if (t[pos].bu != 1 && t[pos].u != 1 && (pos - size) >= 1 && t[pos - size].doubt_pit < 1
					&& t[pos - size].doubt_wump < 1 && t[pos - size].pit != 1 && t[pos - 1].wump != 1
					&& !(t[pos].back.contains("u") && (t[pos].l != 1 || t[pos].r != 1 || t[pos].d != 1)
                            && check(t[pos]))) 
            {
				temp1 = "d";
			
				t[pos].u = 1;
				pos = pos - size;
				++score;
	
				t[pos].back += temp1;
				
				condition = t[pos].sense();
                if (condition == 3) 
                {
					complete = 1;
					break;
                } 
                
                else if (condition == 1 && t[pos].visited == 0) 
                {
                    if (t[pos].br != 1 && t[pos + 1].safe != 1)
                    {
						t[pos + 1].doubt_pit += 1;
                    }

					if (t[pos].bu != 1 && (pos - size) >= 1 && t[pos - size].safe != 1)
                    {
                        t[pos - size].doubt_pit += 1;
                    }   
                        
					if (t[pos].bl != 1 && t[pos - 1].safe != 1)
                    {
                        t[pos - 1].doubt_pit += 1;
                    }    
                        
					if (t[pos].bd != 1 && (pos + size) <= size*size && t[pos + size].safe != 1)
                    {
                        t[pos + size].doubt_pit += 1;
                    }

                    t[pos].safe = 1;
                } 
                
                else if (condition == 2 && t[pos].visited == 0) 
                {
					if (t[pos].br != 1 && t[pos + 1].safe != 1)
						t[pos + 1].doubt_wump += 1;
					if (t[pos].bu != 1 && (pos - size) >= 1 && t[pos - size].safe != 1)
						t[pos - size].doubt_wump += 1;
					if (t[pos].bl != 1 && t[pos - 1].safe != 1)
						t[pos - 1].doubt_wump += 1;
					if (t[pos].bd != 1 && (pos + size) <= size*size && t[pos + size].safe != 1)
						t[pos + size].doubt_wump += 1;

					t[pos].safe = 1;
                } 

                else if (condition == 0)
                {
					t[pos].safe = 1;
                }

				t[pos].visited = 1;
            } 
            
            else if (t[pos].bd != 1 && t[pos].d != 1 && (pos + size) <= size*size && t[pos + size].doubt_pit < 1
                    && t[pos + size].doubt_wump < 1 && t[pos + size].pit != 1 && t[pos + size].wump != 1) 
            {
				temp1 = "u";
		
				t[pos].d = 1;
				pos = pos + size;
        
				score++;
				// t[pos].visited=1;

				t[pos].back += temp1;
		
				condition = t[pos].sense();
                if (condition == 3) 
                {
					complete = 1;
					break;
                } 
                
                else if (condition == 1 && t[pos].visited == 0) 
                {
                    if (t[pos].br != 1 && t[pos + 1].safe != 1)
                    {
						t[pos + 1].doubt_pit += 1;
                    }

                    if (t[pos].bu != 1 && (pos - size) >= 1 && t[pos - size].safe != 1)
                    {
						t[pos - size].doubt_pit += 1;
                    }

                    if (t[pos].bl != 1 && t[pos - 1].safe != 1)
                    {
						t[pos - 1].doubt_pit += 1;
                    }

                    if (t[pos].bd != 1 && (pos + size) <= size*size && t[pos + size].safe != 1)
                    {
						t[pos + size].doubt_pit += 1;
                    }

					t[pos].safe = 1;
                } 
                else if (condition == 2 && t[pos].visited == 0) 
                {
                    if (t[pos].br != 1 && t[pos + 1].safe != 1)
                    {
						t[pos + 1].doubt_wump += 1;
                    }

                    if (t[pos].bu != 1 && (pos - size) >= 1 && t[pos - size].safe != 1)
                    {
						t[pos - size].doubt_wump += 1;
                    }

                    if (t[pos].bl != 1 && t[pos - 1].safe != 1)
                    {
						t[pos - 1].doubt_wump += 1;
                    }

                    if (t[pos].bd != 1 && (pos + size) <= size*size && t[pos + size].safe != 1)
                    {
						t[pos + size].doubt_wump += 1;
                    }

					t[pos].safe = 1;
                } 
                
                else if (condition == 0)
                {
					t[pos].safe = 1;
                }

				t[pos].visited = 1;
            } 
            
            else if (limit > (size*10)) 
            {
				int temp3 = pos;
				int flag_1 = 0, flag2 = 0, flag3 = 0, flag4 = 0;

				//System.out.println("\nCurrently at position " + temp3);
				
                while (t[pos].visited == 1 && t[pos].br != 1) 
                {
					pos++;
					++score;
				}

                if (t[pos].pit == 1 || t[pos].wump == 1 || (t[pos].br == 1 && t[pos].visited == 1 && t[pos].safe != 1)) 
                {
					pos = temp3;
					flag_1 = 1;
				}

                if (flag_1 == 0)
                {
					t[pos].back += "l";
                }

                while (pos - size >= 1 && t[pos].bu != 1 && t[pos].visited == 1) 
                {
					pos -= size;
					++score;
				}

                if (t[pos].pit == 1 || t[pos].wump == 1	|| (t[pos].bu == 1 && t[pos].visited == 1 && t[pos].safe != 1)) 
                {
					// System.out.println("\nUnsuccessful at pos "+pos);
					pos = temp3;
					// System.out.println("\nBack at pos "+pos);
					flag3 = 1;
				}

                if (flag3 == 0)
                {
					t[pos].back += "d";
                }

				// if(!(t[pos].back.contains("l") && (t[pos].r!=1 || t[pos].u!=1
				// || t[pos].d!=1) && check(t[pos]) ))
                while (t[pos].visited == 1 && t[pos].bl != 1) 
                {
					--pos;
					++score;
				}

                if (t[pos].pit == 1 || t[pos].wump == 1	|| (t[pos].bl == 1 && t[pos].visited == 1 && t[pos].safe != 1)) 
                {
					// System.out.println("\nUnsuccessful at pos "+pos);
					pos = temp3;
					// System.out.println("\nBack at pos "+pos);
					flag2 = 1;
				}

                if (flag2 == 0)
                {
					t[pos].back += "r";
                }

				// if(!(t[pos].back.contains("d") && (t[pos].l!=1 || t[pos].r!=1
				// || t[pos].u!=1) && check(t[pos]) ))
                while (pos + size <= size*size && t[pos].bd != 1 && t[pos].visited == 1) 
                {
					pos += size;
					++score;
				}

                if (t[pos].pit == 1 || t[pos].wump == 1 || (t[pos].bd == 1 && t[pos].visited == 1 && t[pos].safe != 1)) 
                {
					pos = temp3;
					flag4 = 1;
                }

                if (flag4 == 0)
                {
					t[pos].back += "u";
                }

				t[pos].safe = 1;
				t[pos].visited = 1;
				limit = 0;
            }
            
            if (t[pos].env.contains("W") && scream != 1)  //if the current location contains the wumpus and the scream has been heard (it has been killed)
            {
				score += 100;
				scream = 1;
				t[pos].safe = 1;
				t[pos].env.replace("W", " ");
                
                for (int l = 1; l <= size*size; ++l) 
                {
					t[l].doubt_wump = 0;
					t[l].env.replace("SM", " ");
				}
			}

            if (t[pos].env.contains("P")) //we are in the same location as a pit. therefore we die and the game is over.
            {
				score += 50;
				t[pos].pit = 1;
				System.out.println("\n\nFallen in pit of position " + pos + ".");
            	score *= -1;
				endGame();
			}

            for (int k = 1; k <= size*size; ++k) 
            {
                if (t[k].doubt_pit == 1 && t[k].doubt_wump == 1) 
                {
					t[k].doubt_pit = 0;
					t[k].doubt_wump = 0;
					t[k].safe = 1;
				}
			}

            for (int y = 1; y <= size*size; ++y) 
            {
                if (t[y].doubt_wump > 1) 
                {
					t[y].wump = 1;
                    for (int h = 1; h <= size*size; ++h) 
                    {
                        if (h != y) 
                        {
							t[h].doubt_wump = 0;
							t[h].env.replace("SM", " ");
						}
					}
				}
			}

            for (int y = 1; y <= size*size; ++y) 
            {
                if (t[y].doubt_pit > 1) 
                {
					t[y].pit = 1;
					// System.out.println("\nPit confirmed at position "+y);
				}
            }

		} while (complete == 0); //end of do while loop

		if (complete == 1) {
			// score=score*2;
			// if(scream==1)
			// score-=100;

			score *= -1;
			score *= 2; //double the score to take the same path back to the start
			score += 1000;
		}
		System.out.println("Total score for adventurer is  " + score
				+ ".\n(This includes the path cost to get out.)");

	}
}
