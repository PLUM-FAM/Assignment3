
// Aim:To implement Wumpus world problem in Java
//Program:

import java.util.*;





class wumpus 
{
	static int scream = 0;
	static int score = 0;
	static int complete = 0;

	static boolean check(tiles t) {
        int temp = t.sense();
        
        if (temp == 1 || temp == 2)
        {
			return false;
        }

		return true;
	}

    public static void main(String args[]) 
    {
		Scanner scr = new Scanner(System.in);
		Environment e = new Environment();
		String w[][] = new String[5][5];
		e.accept(w);
    
        System.out.println("\n\nFinding the solution...");

		tiles t[] = new tiles[17];
		int c = 1;
        out: for (int i = 1; i < 5; ++i) 
        {
            for (int j = 1; j < 5; ++j) 
            {
                if (c > 16)
                {
					break out;
                }

				t[c] = new tiles(w[i][j], c);
				++c;
			}
		}

		t[13].safe = 1;
		t[13].visited = 1;

		int pos = 13;
		int condition;
		int limit = 0;
		String temp1, temp2;
        
        do 
        {
			++limit;
			condition = -1;

            if (t[pos].env.contains("G")) 
            {
				complete = 1;
				System.out.println("Gold Found!!");
				break;
			}

			if (t[pos].br != 1 && t[pos].r != 1 && t[pos + 1].doubt_pit < 1 && t[pos + 1].doubt_wump < 1
					&& t[pos + 1].pit != 1 && t[pos + 1].wump != 1 && !(t[pos].back.contains("r")
                            && (t[pos].l != 1 || t[pos].u != 1 || t[pos].d != 1) && check(t[pos]))) 
            {
			
				temp1 = "l";
				t[pos].r = 1;
                ++pos;
                
				System.out.println("\nfront pos=" + pos);
                
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
						t[pos + 1].doubt_pit += 1;
					if (t[pos].bu != 1 && (pos - 4) >= 1 && t[pos - 4].safe != 1)
						t[pos - 4].doubt_pit += 1;
					if (t[pos].bl != 1 && t[pos - 1].safe != 1)
						t[pos - 1].doubt_pit += 1;
					if (t[pos].bd != 1 && (pos + 4) <= 16 && t[pos + 4].safe != 1)
						t[pos + 4].doubt_pit += 1;

					t[pos].safe = 1;
                }

                else if (condition == 2 && t[pos].visited == 0) 
                {
                    if (t[pos].br != 1 && t[pos + 1].safe != 1)
                    {
						t[pos + 1].doubt_wump += 1;
                    }

                    if (t[pos].bu != 1 && (pos - 4) >= 1 && t[pos - 4].safe != 1)
                    {
						t[pos - 4].doubt_wump += 1;
                    }

                    if (t[pos].bl != 1 && t[pos - 1].safe != 1)
                    {
						t[pos - 1].doubt_wump += 1;
                    }

					if (t[pos].bd != 1 && (pos + 4) <= 16 && t[pos + 4].safe != 1)
                    {
                        t[pos + 4].doubt_wump += 1;
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
				System.out.println("\nback pos= " + pos);
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
						t[pos + 1].doubt_pit += 1;
					if (t[pos].bu != 1 && (pos - 4) >= 1 && t[pos - 4].safe != 1)
						t[pos - 4].doubt_pit += 1;
					if (t[pos].bl != 1 && t[pos - 1].safe != 1)
						t[pos - 1].doubt_pit += 1;
					if (t[pos].bd != 1 && (pos + 4) <= 16 && t[pos + 4].safe != 1)
						t[pos + 4].doubt_pit += 1;

					t[pos].safe = 1;
                } 
                
                else if (condition == 2 && t[pos].visited == 0) 
                {
					if (t[pos].br != 1 && t[pos + 1].safe != 1)
                    {
                        t[pos + 1].doubt_wump += 1;
                    }
                        
                    if (t[pos].bu != 1 && (pos - 4) >= 1 && t[pos - 4].safe != 1)
                    {
                        t[pos - 4].doubt_wump += 1;
                    }
                        
                    if (t[pos].bl != 1 && t[pos - 1].safe != 1)
                    {
                        t[pos - 1].doubt_wump += 1;
                    }
                        
                    if (t[pos].bd != 1 && (pos + 4) <= 16 && t[pos + 4].safe != 1)
                    {
						t[pos + 4].doubt_wump += 1;
                    }

					t[pos].safe = 1;
                } 
                
                else if (condition == 0)
                {
					t[pos].safe = 1;
                }

				t[pos].visited = 1;

            } 
            
            else if (t[pos].bu != 1 && t[pos].u != 1 && (pos - 4) >= 1 && t[pos - 4].doubt_pit < 1
					&& t[pos - 4].doubt_wump < 1 && t[pos - 4].pit != 1 && t[pos - 1].wump != 1
					&& !(t[pos].back.contains("u") && (t[pos].l != 1 || t[pos].r != 1 || t[pos].d != 1)
                            && check(t[pos]))) 
            {
				temp1 = "d";
			
				t[pos].u = 1;
				pos = pos - 4;
				System.out.println("\nUp pos= " + pos);
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

					if (t[pos].bu != 1 && (pos - 4) >= 1 && t[pos - 4].safe != 1)
                    {
                        t[pos - 4].doubt_pit += 1;
                    }   
                        
					if (t[pos].bl != 1 && t[pos - 1].safe != 1)
                    {
                        t[pos - 1].doubt_pit += 1;
                    }    
                        
					if (t[pos].bd != 1 && (pos + 4) <= 16 && t[pos + 4].safe != 1)
                    {
                        t[pos + 4].doubt_pit += 1;
                    }

                    t[pos].safe = 1;
                } 
                
                else if (condition == 2 && t[pos].visited == 0) 
                {
					if (t[pos].br != 1 && t[pos + 1].safe != 1)
						t[pos + 1].doubt_wump += 1;
					if (t[pos].bu != 1 && (pos - 4) >= 1 && t[pos - 4].safe != 1)
						t[pos - 4].doubt_wump += 1;
					if (t[pos].bl != 1 && t[pos - 1].safe != 1)
						t[pos - 1].doubt_wump += 1;
					if (t[pos].bd != 1 && (pos + 4) <= 16 && t[pos + 4].safe != 1)
						t[pos + 4].doubt_wump += 1;

					t[pos].safe = 1;
                } 

                else if (condition == 0)
                {
					t[pos].safe = 1;
                }

				t[pos].visited = 1;
            } 
            
            else if (t[pos].bd != 1 && t[pos].d != 1 && (pos + 4) <= 16 && t[pos + 4].doubt_pit < 1
                    && t[pos + 4].doubt_wump < 1 && t[pos + 4].pit != 1 && t[pos + 4].wump != 1) 
            {
				temp1 = "u";
		
				t[pos].d = 1;
				pos = pos + 4;
        
                System.out.println("\ndown pos= " + pos);
				++score;
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

                    if (t[pos].bu != 1 && (pos - 4) >= 1 && t[pos - 4].safe != 1)
                    {
						t[pos - 4].doubt_pit += 1;
                    }

                    if (t[pos].bl != 1 && t[pos - 1].safe != 1)
                    {
						t[pos - 1].doubt_pit += 1;
                    }

                    if (t[pos].bd != 1 && (pos + 4) <= 16 && t[pos + 4].safe != 1)
                    {
						t[pos + 4].doubt_pit += 1;
                    }

					t[pos].safe = 1;
                } 
                else if (condition == 2 && t[pos].visited == 0) 
                {
                    if (t[pos].br != 1 && t[pos + 1].safe != 1)
                    {
						t[pos + 1].doubt_wump += 1;
                    }

                    if (t[pos].bu != 1 && (pos - 4) >= 1 && t[pos - 4].safe != 1)
                    {
						t[pos - 4].doubt_wump += 1;
                    }

                    if (t[pos].bl != 1 && t[pos - 1].safe != 1)
                    {
						t[pos - 1].doubt_wump += 1;
                    }

                    if (t[pos].bd != 1 && (pos + 4) <= 16 && t[pos + 4].safe != 1)
                    {
						t[pos + 4].doubt_wump += 1;
                    }

					t[pos].safe = 1;
                } 
                
                else if (condition == 0)
                {
					t[pos].safe = 1;
                }

				t[pos].visited = 1;
            } 
            
            else if (limit > 50) 
            {
				int temp3 = pos;
				int flag_1 = 0, flag2 = 0, flag3 = 0, flag4 = 0;

				System.out.println("\nCurrently at position " + temp3 + ".\nThinking....");

				// if(!(t[pos].back.contains("r") && (t[pos].l!=1 || t[pos].u!=1
				// || t[pos].d!=1) && check(t[pos]) ))
                while (t[pos].visited == 1 && t[pos].br != 1) 
                {
					++pos;
					++score;
				}

                if (t[pos].pit == 1 || t[pos].wump == 1 || (t[pos].br == 1 && t[pos].visited == 1 && t[pos].safe != 1)) 
                {
					// System.out.println("\nUnsuccessful at pos "+pos);
					pos = temp3;
					// System.out.println("\nBack at pos "+pos);
					flag_1 = 1;
				}

                if (flag_1 == 0)
                {
					t[pos].back += "l";
                }

				// if(!(t[pos].back.contains("u") && (t[pos].l!=1 || t[pos].r!=1
				// || t[pos].d!=1) && check(t[pos]) ))
                while (pos + 4 >= 1 && t[pos].bu != 1 && t[pos].visited == 1) 
                {
					pos -= 4;
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
                while (pos + 4 <= 16 && t[pos].bd != 1 && t[pos].visited == 1) 
                {
					pos += 4;
					++score;
				}

                if (t[pos].pit == 1 || t[pos].wump == 1 || (t[pos].bd == 1 && t[pos].visited == 1 && t[pos].safe != 1)) 
                {
					// System.out.println("\nUnsuccessful at pos "+pos);
					pos = temp3;
					// System.out.println("\nBack at pos "+pos);
					flag4 = 1;
                }
                
				// t[pos-1].r=0;
				// ++pos;
				// if(!t[pos].env.contains("P") && !t[pos].env.contains("W"))

                if (flag4 == 0)
                {
					t[pos].back += "u";
                }

				t[pos].safe = 1;
				t[pos].visited = 1;
				System.out.println("reached at position " + pos);
				limit = 0;
            }
            
            if (t[pos].env.contains("W") && scream != 1) 
            {
				score += 100;
				scream = 1;
				t[pos].safe = 1;
				System.out.println("\n\nWumpus killed >--0-->");
				t[pos].env.replace("W", " ");
                
                for (int l = 1; l <= 16; ++l) 
                {
					t[l].doubt_wump = 0;
					t[l].env.replace("S", " ");
				}
			}

            if (t[pos].env.contains("P")) 
            {
				score += 50;
				t[pos].pit = 1;
				System.out.println("\n\nFallen in pit of position " + pos + ".");
			}

            for (int k = 1; k <= 16; ++k) 
            {
                if (t[k].doubt_pit == 1 && t[k].doubt_wump == 1) 
                {
					t[k].doubt_pit = 0;
					t[k].doubt_wump = 0;
					t[k].safe = 1;
				}
			}

            for (int y = 1; y <= 16; ++y) 
            {
                if (t[y].doubt_wump > 1) 
                {
					t[y].wump = 1;
                    for (int h = 1; h <= 16; ++h) 
                    {
                        if (h != y) 
                        {
							t[h].doubt_wump = 0;
							t[h].env.replace("S", " ");
						}
					}
				}
			}

            for (int y = 1; y <= 16; ++y) 
            {
                if (t[y].doubt_pit > 1) 
                {
					t[y].pit = 1;
					// System.out.println("\nPit confirmed at position "+y);
				}
			}
			
            try 
            {
				Thread.sleep(200);
			} catch (Exception p) {}

		} while (complete == 0); //end of do while loop

		if (complete == 1) {
			// score=score*2;
			// if(scream==1)
			// score-=100;

			score *= -1;
			score += 1000;
		}
		System.out.println("The score of the agent till he reaches gold is " + score
				+ ".\nNow he will return back following the best explored path.");

	}
}

/*
 * OUTPUT 1
 * 
 * C:\Users\Aditya\Desktop\aditya>javac wumpus.java
 * 
 * C:\Users\Aditya\Desktop\aditya>java wumpus
 ********* 
 * 
 * Wumpus World Problem *********
 * 
 * 
 * The positions are as follows.
 * 
 * ----------------------------------------------------------------- | 1 | 2 | 3
 * | 4 | ----------------------------------------------------------------- | 5 |
 * 6 | 7 | 8 | -----------------------------------------------------------------
 * | 9 | 10 | 11 | 12 |
 * ----------------------------------------------------------------- | 13 | 14 |
 * 15 | 16 | -----------------------------------------------------------------
 * 
 * Agent start position: 13
 * 
 * Enter the number of pits. 3 Positions of pit, gold and wumpus should not
 * overlap. Enter the position of pits. 4 7 15 Enter the position of wumpus. 5
 * Enter the position of gold. 6
 * 
 * The environment for problem is as follows.
 * 
 * 
 * ----------------------------------------------------------------- | S | | B |
 * P | ----------------------------------------------------------------- | W |
 * BSG | P | B |
 * ----------------------------------------------------------------- | S | | B |
 * | ----------------------------------------------------------------- | A | B |
 * P | B | -----------------------------------------------------------------
 * 
 * 
 * Finding the solution...
 * 
 * front pos=14
 * 
 * back pos= 13
 * 
 * Up pos= 9
 * 
 * front pos=10
 * 
 * front pos=11
 * 
 * back pos= 10
 * 
 * Up pos= 6 Gold Found!! The score of the agent till he reaches gold is 993.
 * Now he will return back following the best explored path.
 * 
 * 
 * 
 * OUTPUT 2
 * 
 * C:\Users\Aditya\Desktop\aditya>javac wumpus.java
 * 
 * C:\Users\Aditya\Desktop\aditya>java wumpus
 ********* 
 * 
 * Wumpus World Problem *********
 * 
 * 
 * The positions are as follows.
 * 
 * ----------------------------------------------------------------- | 1 | 2 | 3
 * | 4 | ----------------------------------------------------------------- | 5 |
 * 6 | 7 | 8 | -----------------------------------------------------------------
 * | 9 | 10 | 11 | 12 |
 * ----------------------------------------------------------------- | 13 | 14 |
 * 15 | 16 | -----------------------------------------------------------------
 * 
 * Agent start position: 13
 * 
 * Enter the number of pits. 2 Positions of pit, gold and wumpus should not
 * overlap. Enter the position of pits. 5 11 Enter the position of wumpus. 6
 * Enter the position of gold. 4
 * 
 * The environment for problem is as follows.
 * 
 * 
 * ----------------------------------------------------------------- | B | S | |
 * G | ----------------------------------------------------------------- | SP |
 * BW | BS | | -----------------------------------------------------------------
 * | B | BS | P | B |
 * ----------------------------------------------------------------- | A | | B |
 * | -----------------------------------------------------------------
 * 
 * 
 * Finding the solution...
 * 
 * front pos=14
 * 
 * front pos=15
 * 
 * back pos= 14
 * 
 * Up pos= 10
 * 
 * down pos= 14
 * 
 * Currently at position 14. Thinking.... reached at position 16
 * 
 * Currently at position 16. Thinking.... reached at position 12
 * 
 * Up pos= 8
 * 
 * back pos= 7
 * 
 * front pos=8
 * 
 * Up pos= 4 The score of the agent till he reaches gold is 988. Now he will
 * return back following the best explored path.
 * 
 */