package Raetsel2022;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import Base.InputKind;
import Base.Position;

public class Dec22 extends Puzzle2022
{
	public Dec22()
	{
		super(22);
		readInput(InputKind.charTable);
		read();
		
		computeSolution(1);
		computeSolution(2);

		printSolution();
	}
	
	//!42528, !86368
	
	public void read()
	{
		try
		{
			BufferedReader r = new BufferedReader(new FileReader(new File("2022\\Raetsel2022\\inputs\\Dec22instr")));
			inputString = r.readLine();
		}
		catch (IOException e)
		{
			// TODO: handle exception
		}
		
		horizontalBounds = new int[lines][];
		for(int i=0; i<lines; i++)
		{
			int firstFree = 0;
			while(true)
			{
				if(inputCharTable[i][firstFree] == '.' || inputCharTable[i][firstFree] == '#')
				{
					break;
				}
				firstFree++;
			}
			
			int lastFree = firstFree;
			while(true)
			{
				if(lastFree == inputCharTable[i].length-1)
				{
					break;
				}
				if(inputCharTable[i][lastFree] != '#' && inputCharTable[i][lastFree] != '.')
				{
					lastFree--;
					break;
				}
				lastFree++;
			}
			horizontalBounds[i] = new int[] {firstFree, lastFree};
		}
		
//		columns = 16;
		verticalBounds = new int[columns][];
		for(int i=0; i<columns; i++)
		{
			int firstFree = 0;
			while(true)
			{
				if(inputCharTable[firstFree][i] == '.' || inputCharTable[firstFree][i] == '#')
				{
					break;
				}
				firstFree++;
			}
			
			int lastFree = firstFree;
			while(true)
			{
				if(lastFree == inputCharTable.length-1)
				{
					break;
				}
				if(inputCharTable[lastFree][i] != '#' && inputCharTable[lastFree][i] != '.')
				{
					lastFree--;
					break;
				}
				lastFree++;
			}
			verticalBounds[i] = new int[] {firstFree, lastFree};
		}
	}

	int[][] horizontalBounds;
	int[][] verticalBounds;
	Position pos;
	// 10R5L5R10L4R5L5
	
	@Override
	public void solveTask1()
	{
		pos = new Position(0, horizontalBounds[0][0]);
		char facing = 'R';

		String buffer = "";
		for(int i=0; i<inputString.length(); i++)
		{
			char curr = inputString.charAt(i);
			if(Character.isDigit(curr))
			{
				buffer += curr;
			}
			else
			{
				int number = Integer.parseInt(buffer);
				buffer = "";
				System.err.println(number+""+facing);
				
				for(int step=0; step<number; step++)
				{
					if(facing == 'R')
					{
						if(pos.col+1 == inputCharTable[pos.row].length || pos.col+1 > horizontalBounds[pos.row][1])
						{
							int col = horizontalBounds[pos.row][0];
							if(inputCharTable[pos.row][col] != '#')
							{
								inputCharTable[pos.row][pos.col] = '>';
								pos.col = col;
								continue;
							}
							else
							{
								break;
							}
						}
						
						char dest = inputCharTable[pos.row][pos.col+1];
//						if(dest == '.' || dest == '>' || dest == '<' || dest =='^' || dest == 'v')
						if(dest != '#')
						{
							inputCharTable[pos.row][pos.col] = '>';
							pos.col++;
						}
						else if(dest == '#')
						{
							inputCharTable[pos.row][pos.col] = '>';
							break;
						}
					}
					if(facing == 'L')
					{
						if(pos.col-1 == -1 || pos.col-1 < horizontalBounds[pos.row][0])
						{
							int col = horizontalBounds[pos.row][1];
							if(inputCharTable[pos.row][col] != '#')
							{
								inputCharTable[pos.row][pos.col] = '<';
								pos.col = col;
								continue;
							}
							else
							{
								break;
							}
						}
						
						char dest = inputCharTable[pos.row][pos.col-1];
//						if(dest == '.' || dest == '>' || dest == '<' || dest =='^' || dest == 'v')
						if(dest != '#')
						{
							inputCharTable[pos.row][pos.col] = '<';
							pos.col--;
						}
						else if(dest == '#')
						{
							inputCharTable[pos.row][pos.col] = '<';
							break;
						}
					}
					if(facing == 'U')
					{
						if(pos.row-1 == -1 || pos.row-1 < verticalBounds[pos.col][0])
						{
							int row = verticalBounds[pos.col][1];
							if(inputCharTable[row][pos.col] != '#')
							{
								inputCharTable[pos.row][pos.col] = '^';
								pos.row = row;
								continue;
							}
							else
							{
								break;
							}
						}
						
						char dest = inputCharTable[pos.row-1][pos.col];
//						if(dest == '.' || dest == '>' || dest == '<' || dest =='^' || dest == 'v')
						if(dest != '#')
						{
							inputCharTable[pos.row][pos.col] = '^';
							pos.row--;
						}
						else if(dest == '#')
						{
							inputCharTable[pos.row][pos.col] = '^';
							break;
						}
					}
					if(facing == 'D')
					{
						if(pos.row+1 == inputCharTable.length || pos.row+1 > verticalBounds[pos.col][1])
						{
							int row = verticalBounds[pos.col][0];
							if(inputCharTable[row][pos.col] != '#')
							{
								inputCharTable[pos.row][pos.col] = 'v';
								pos.row = row;
								continue;
							}
							else
							{
								break;
							}
						}
						
						char dest = inputCharTable[pos.row+1][pos.col];
//						if(dest == '.' || dest == '>' || dest == '<' || dest =='^' || dest == 'v')
						if(dest != '#')
						{
							inputCharTable[pos.row][pos.col] = 'v';
							pos.row++;
						}
						else if(dest == '#')
						{	
							inputCharTable[pos.row][pos.col] = 'v';
							break;
						}
					}
				}
				
				facing = updateFacing(facing, curr=='R');
				
//				printInput();
//				System.err.println();
			}
		}
		
		pos.row++;
		pos.col++;
		System.out.println(pos);
		
		erg1 = 1000 * (pos.row) + 4 * (pos.col) + (facing == 'R' ? 0 : facing == 'D' ? 1 : facing =='L' ? 2 : 3);
		
	}
	
	private char updateFacing(char before, boolean right)
	{
		if(right)
		{
			switch(before)
			{
				case 'R': return 'D';
				case 'D': return 'L';
				case 'L': return 'U';
				case 'U': return 'R';
			}
		}
		
		if(!right)
		{
			switch(before)
			{
				case 'R': return 'U';
				case 'U': return 'L';
				case 'L': return 'D';
				case 'D': return 'R';
			}
		}
		
		return ' ';
	}

	@Override
	public void solveTask2()
	{
		
	}
	
	public static void main(String[] args)
	{
		new Dec22();
	}
}


