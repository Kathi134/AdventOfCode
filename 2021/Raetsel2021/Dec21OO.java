package Raetsel2021;

import java.io.File;
import java.io.IOException;

public class Dec21OO extends Puzzle
{
	int erg1;
	
	public Dec21OO() throws IOException
	{
		super(2);
		f = new File("2021\\Raetsel2021\\inputs\\Dec" + 21);
		prepare();
		readInput("StringList");
		
		solveTask1();
		solveTask2();
		System.out.println("Task 1 -- " + erg1);
		System.out.println("Task 2 -- ");
	}
	
	public void solveTask1()
	{
		DeterministicGame game = new DeterministicGame(inputStringList);
		erg1 = game.letPlay();
	}
	
	public static void main(String[] args) throws IOException
	{
		new Dec21();
	}

	@Override
	public void solveTask2(){}
}

class DeterministicGame
{
	Player p1;
	Player p2;
	Player loser;
	Dice dice;
	
	public DeterministicGame(String[] players)
	{
		dice = new Dice(1, 100);
		
		String playerOne = players[0];
		int positionOne = Character.getNumericValue(playerOne.charAt(playerOne.length()-1));
		p1 = new Player(1, positionOne, dice);
		
		String playerTwo = players[1];
		int positionTwo = Character.getNumericValue(playerTwo.charAt(playerTwo.length()-1));
		p2 = new Player(2, positionTwo, dice);
	}
	
	public int letPlay()
	{
		Player loser = null;
		while(true)
		{
			if(p1.play1())
			{
				loser = p2;
				break;
			}
			if(p2.play1())
			{
				loser = p1;
				break;
			}
		}
		return loser.getScore() * dice.getAnswerArgument();
	}
	
}


class Dice 
{
	int max;
	int next; 
	int timesRolled = 0;
	
	public Dice(int start, int max)
	{
		this.max = max;
		this.next = start;
	}
	
	public int rollDice()
	{
		timesRolled++;
		
		int rolled = next;
		
		next++;
		if(next % max == 0)
		{
			next = max;
		}
		else
		{
			next = next % max;
		}
		
		return rolled;
	}

	public int getAnswerArgument()
	{
		return timesRolled;
	}
}

class Player
{
	public final int name;
	private int position;
	private int score;
	private Dice dice;
	
	public Player(int name, int position, Dice dice)
	{
		this.name = name;
		this.position = position;
		this.dice = dice;
		score = 0;
	}

	public int getScore()
	{
		return score;
	}
	
	public boolean play1()
	{
		int rolled = 0;
		for(int i=0; i<3; i++)
		{
			rolled += dice.rollDice();
		}
		
		position += rolled; 
		if(position % 10 == 0) //pos gehen von [1;10]
		{
			position = 10;
		}
		else
		{
			position = position % 10;
		}
		
		score += position;
		if(score >= 1000)
		{
			return true; //falls gewonnen
		}
		
		return false;
	}
	
}