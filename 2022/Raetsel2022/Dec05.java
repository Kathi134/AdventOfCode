package Raetsel2022;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import Base.InputKind;

public class Dec05 extends Puzzle2022
{
	List<Stack<Character>> cargo;
	int[][] data;
	
	public Dec05()
	{
		super(5);
		readInputDivided(InputKind.StringTable, " ");
		
		try
		{
			read();
		}
		catch(IOException e) {e.printStackTrace();}
		
		computeSolution(1);
		computeSolution(2);

		printSolution();
	}
	
	private void read() throws IOException
	{
		data = new int[lines][3];
		for(int i=0; i<lines; i++)
		{
			data[i][0] = Integer.parseInt(inputStringTable[i][1]);
			data[i][1] = Integer.parseInt(inputStringTable[i][3]);
			data[i][2] = Integer.parseInt(inputStringTable[i][5]);
		}
		
		cargo = new ArrayList<>();
		
		List<String> rows = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(new File("2022\\Raetsel" + 2022 +"\\inputs\\Dec" + 5+"cargo")));
		
		for(int i=0; i<8; i++)
		{
			rows.add(0,br.readLine());
		}
		
		for(int i=0; i<9; i++)
		{
			cargo.add(new Stack<Character>());
		}
		
		for(String s: rows)
		{
			for(int i=0; i<s.length(); i++)
			{
				char c = s.charAt(i);
				if(c != ' ')
				{
					cargo.get(i).push(c);
				}
			}
		}
		System.out.println(cargo);
	}
	
	@Override
	public void solveTask1()
	{
		for(int i=0; i<data.length; i++)
		{
			int from = data[i][1]-1;
			int to = data[i][2]-1;
			
			Stack<Character> tmp = new Stack<>();
			for(int a=0; a<data[i][0]; a++)
			{
//				move(data[i][1]-1, data[i][2]-1);

				tmp.push(cargo.get(from).pop());				
			}
			
			while(!tmp.isEmpty())
			{
				cargo.get(to).push(tmp.pop());
			}
		}
		
		String res = "";
		for(int i=0; i<cargo.size(); i++)
		{
			char c = cargo.get(i).pop();
			cargo.get(i).push(c);
			res+= c;
		}
		System.out.println(res);
	}
	
	public void move(int from, int to)
	{
		char c = cargo.get(from).pop();
		cargo.get(to).push(c);
	}
	
	public void moveAtOnce(int from, int to)
	{
		char c = cargo.get(from).lastElement();
		cargo.get(to).push(c);
	}
	
	@Override
	public void solveTask2()
	{

	}
	
	public static void main(String[] args)
	{
		new Dec05();
	}
}

