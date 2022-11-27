package Raetsel2020;

import java.io.File;
import java.io.IOException;

public class December6th extends Puzzle
{
	private static int sumOfGroupYesses1 = 0;
	private static int sumOfGroupYesses2 = 0;
	
	public static void main(String[] args) throws IOException
	{
		f = new File("src\\Raetsel2020\\inputs\\December6th_Input");
		prepare();
		readInput("seperatedStringList");
		solveTask1();
		solveTask2();
		System.out.println("Task 1 -- number of any affirmed Questions: " + sumOfGroupYesses1);
		System.out.println("Task 2 -- number of unified affirmed Questions: " + sumOfGroupYesses2);
	}
	
	public static void solveTask1()
	{
		for (int i=0; i<inputStringList.length; i++)
		{
			int groupYesses = getAnyYesses(inputStringList[i].replaceAll(" ", ""));
			sumOfGroupYesses1 += groupYesses;
		}
	}
	
	public static void solveTask2()
	{
		for (int i=0; i<inputStringList.length; i++) //für jede Gruppe
		{
			String[] groupAnswers = inputStringList[i].split(" "); //mache ein Feld mit den einzelnen MemberAntworten
			int groupYesses = getUnifiedYesses(groupAnswers);			
			sumOfGroupYesses2 += groupYesses;
		}
	}
	
	public static int getUnifiedYesses(String[] groupAnswers)
	{
		String included = "";
		
		for (int cm=0; cm<groupAnswers.length; cm++) //für jedes gruppenmitglied
		{
			String currMember = groupAnswers[cm]; //ci=charIndex
			for (int ci=0; ci<currMember.length(); ci++) //für jedes Zeichen
			{
				String c = currMember.charAt(ci)+"";
				boolean all = true; //ist in allen Fragebögen
				for(int j=0; j<groupAnswers.length; j++) //prüfe, ob in allen einträgen von groupanswers enthalten
				{
					if(!groupAnswers[j].contains(c))
					{
						all = false; //sobald es in einem nicht ist, false
					}
				}
				if(all && !included.contains(c)) //wenn in allen bejaht und noch nicht in included
				{
					included += c; //füge hinzu
				}
			}
		}
		return included.length();
	}
	
	public static int getAnyYesses(String groupAnswers)
	{
		String included = "";
		
		for(int i=0; i<groupAnswers.length(); i++)
		{
			String c = groupAnswers.charAt(i)+"";
			if(!included.contains(c)) //noch niemand in der gruppe hat ja geantwortet
			{
				included+=c;
			}
		}
		
		return included.length();
	}
}
