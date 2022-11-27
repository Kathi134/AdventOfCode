package Raetsel2021;

import java.io.IOException;
import java.util.ArrayList;

public class Dec21 extends Puzzle
{
	int pos1;
	int pos2;
	int nextDie = 1;
	
	ArrayList<Possibility> alreadyChecked = new ArrayList<Possibility>();
	
	public void format()
	{
		String playerOne = inputStringList[0];
		String playerTwo = inputStringList[1];
		pos1 = Character.getNumericValue(playerOne.charAt(playerOne.length()-1));
		pos2 = Character.getNumericValue(playerTwo.charAt(playerTwo.length()-1));
	}
	
	public Dec21() throws IOException
	{
		super(21);
		readInput("StringList");
		format();
		System.err.println("--takes about a half minute to compute--");
		
		computeSolution(1);
		computeSolution(2);
		//printSolution();
		
		printFormattedSolution("Dirac Dice",
				"times rolled * loser score",
				"number universes player with more wins wins");
	}
	
	public void solveTask1()
	{
		int score1 = 0;
		int score2 = 0;
		int loserScore = 0;
		
		while(true)
		{
			int roll1 = roll()+roll()+roll();
			pos1 += roll1;
			pos1 = pos1%10 == 0 ? 10 : pos1%10;
			score1 += pos1;
			if(score1 >= 1000)
			{
				loserScore = score2; 
				break;
			}
			
			int roll2 = roll()+roll()+roll();
			pos2 += roll2;
			pos2 = pos2%10 == 0 ? 10 : pos2%10;
			score2 += pos2;
			if(score2 >= 1000)
			{
				loserScore = score1; 
				break;
			}
		}
		
		erg1 = loserScore * (nextDie-1);
	}
	
	public int roll()
	{
		int tmp = nextDie;
		nextDie++;
		return tmp;
	}

	public void solveTask2()
	{
		format();
		long[] totalWins = countWins(pos1, 0, pos2, 0);
		erg2 = Long.max(totalWins[0], totalWins[1]);
	}
	
	//ein spiel-status wird übergeben (position und score beider spieler)
	//der spieler am zug ist immer der erstgenannnte
	public long[] countWins(int pos1, int score1, int pos2, int score2)
	{
		if(score1 >= 21)
		{
			return new long[] {1, 0}; 
		}
		else if(score2 >= 21)
		{
			return new long[] {0, 1};
		}
		
		Possibility p = new Possibility(pos1, score1, pos2, score2);
		if(alreadyChecked.contains(p))
		{
			int index = alreadyChecked.indexOf(p);
			return alreadyChecked.get(index).thisWins;
		}
		
		long[] wins = new long[] {0, 0};
		int[] dies = {1,2,3};
		for(int die1: dies)
		{
			for(int die2: dies)
			{
				for(int die3: dies)
				{
					int rollSum = die1+die2+die3;
					
					int newPos1 = pos1+rollSum;
					newPos1 = newPos1%10 == 0 ? 10 : newPos1%10;
					
					int newScore1 = score1 + newPos1;
					
					//jetzt ist spieler 2 dran -> vorne in die parameter
					long[] nextWins = countWins(pos2, score2, newPos1, newScore1); //zähle die siege ab jetzt
					
					wins = new long[] {wins[0]+nextWins[1], wins[1]+nextWins[0]};
				}
			}
		}
		
		p.thisWins = wins.clone();
		alreadyChecked.add(p);
		return wins;
	}
	
	public static void main(String[] args) throws IOException
	{
		new Dec21();
	}
}

class Possibility
{
	int[] status; //status = {pos1, score1, pos2, score2}
	
	long thisWins[];

	public Possibility(int pos1, int score1, int pos2, int score2, long[] thisWins)
	{
		this(pos1, score1, pos2, score2);
		this.thisWins = thisWins;
	}
	
	public Possibility(int pos1, int score1, int pos2, int score2)
	{
		status = new int[] {pos1, score1, pos2, score2};
	}

	@Override
	public boolean equals(Object obj)
	{
		Possibility other = (Possibility) obj;
		for(int i=0; i<status.length; i++)
		{
			if(this.status[i] != other.status[i])
			{
				return false;
			}
		}
		return true;
	}
}
