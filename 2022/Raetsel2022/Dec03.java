package Raetsel2022;
import Base.InputKind;

public class Dec03 extends Puzzle2022
{
	public Dec03()
	{
		super(3);
		readInput(InputKind.StringList);
		
		computeSolution(1);
		computeSolution(2);

		printSolution();
	}

	@Override
	public void solveTask1()
	{
		int sum = 0;
		
		for(int i=0; i<inputStringList.length; i++)
		{
			String rucksack = inputStringList[i];
			String f = rucksack.substring(0, rucksack.length()/2);
			String s = rucksack.substring(rucksack.length()/2);
			
			String common = "";
			for(int fi=0; fi<f.length(); fi++)
			{
				char fc = f.charAt(fi);
				if(s.contains(fc+""))
				{
					common += fc;
					s = s.replaceAll(fc+"", ".");
				}
				
			}
			sum += mapPoints(common);
		}
	
		erg1 = sum;
	}
	
	public int mapPoints(String commons)
	{
//		for(int i=0; )
		if(commons.length() == 1)
		{
			int p = 0;
			
			char c = commons.charAt(0);
			int ascii = (int) c;
			if(Character.isLowerCase(c))
			{
				p = ascii - 96; //(int)'a'-1
			}
			if(Character.isUpperCase(c))
			{
				p = ascii - 65 + 27; //(int)'A'-1
			}
			return p;
		}
		else
		{
			System.out.println("fail: " + commons);
			return 0;
		}
	}

	@Override
	public void solveTask2()
	{
		int sum = 0 ;
		for(int i=0; i<inputStringList.length; i+=3)
		{
			String f = inputStringList[i];
			String s = inputStringList[i+1];
			String t = inputStringList[i+2];
			
			String common = "";
			for(int fi=0; fi<f.length(); fi++)
			{
				char fc = f.charAt(fi);
				if(s.contains(fc+"") && t.contains(fc + ""))
				{
					common += fc;
					s = s.replaceAll(fc+"", ".");
					t= t.replaceAll(fc+"", ".");
				}
				
			}
			sum += mapPoints(common);
		}
		erg2 = sum;
	}
	
	public static void main(String[] args)
	{
		System.out.println((int)'a' + " " + (int)'A');
		new Dec03();
	}
}


