package Raetsel2021;

import java.io.IOException;
import java.util.ArrayList;
@SuppressWarnings("unlikely-arg-type")

public class Dec14 extends Puzzle
{
	private Rule[] rules;
	private ArrayList<Letter> countLetters = new ArrayList<Letter>(); 
	
	public void initializeCountLetters()
	{
		countLetters = new ArrayList<Letter>();
		for(int i=0; i<rules.length; i++)
		{
			Letter l = new Letter(rules[i].addedLetter, 0);
			
			if(!countLetters.contains(l))
			{
				countLetters.add(l);
			}
		}
	}
	
	public void read() throws IOException
	{
		prepare();
		br.readLine(); //überspringe die ersten zwei zeilen
		br.readLine(); 
		
		rules = new Rule[lines-2];
		for(int i=0; i<rules.length; i++)
		{
			String line = br.readLine();
			Rule r = new Rule(line.substring(0, 2), line.charAt(6)+"");
			rules[i] = r;
		}
	}
	
	public void initializeRuleCounts()
	{
		for(int i=0; i<inputString.length()-1; i++)
		{
			String pairPattern = inputString.charAt(i) + "" + inputString.charAt(i+1);
			incrementQuantity(pairPattern);
		}
	}
	

	public void incrementQuantity(String pair)
	{
		for(int i=0; i<rules.length; i++)
		{
			if(rules[i].equals(pair))
			{
				rules[i].incrementTimesApplied();
				break;
			}
		}
	}
	
	public void setQuantity(String pair, int newQuantity)
	{
		for(int i=0; i<rules.length; i++)
		{
			if(rules[i].equals(pair))
			{
				rules[i].incrementTimesApplied();
			}
		}
	}
	
	public Dec14() throws IOException, CloneNotSupportedException
	{
		super(14);
		readInput("String");
		
		computeSolution(1);
		computeSolution(2);
		//printSolution();
		
		printFormattedSolution("Extended Polymerization", 
				"most common - least common after 10 steps", 
				"most common - least common after 40 steps");
	}
	
	public long solve(int steps) throws IOException, CloneNotSupportedException
	{
		read();
		initializeRuleCounts();
		for(int step=0; step<steps; step++)
		{
			//merke alle änderungen in einem neuen Rule array, wo erst mal alles 0 ist
			Rule[] changes = clone(rules);	
			for(int i=0; i<changes.length; i++)
			{
				changes[i].setTimesApplied(0);
			}
			
			for(int i=0; i<rules.length; i++)
			{
				if(rules[i].getTimesApplied()>0)
				{
					applyRule(rules[i], changes);
				}
			}
			rules = clone(changes);			
		}
		return computeFinalAnswer();
	}
	
	public Rule[] clone(Rule[] original) throws CloneNotSupportedException
	{
		Rule[] clone = new Rule[original.length];
		for(int i=0; i<clone.length; i++)
		{
			clone[i] = original[i].clone();
		}
		return clone;
	}
	
	public void applyRule(Rule r, Rule[] changes)
	{
		long tmp = r.getTimesApplied();
		String pair1 = r.newPair1;
		String pair2 = r.newPair2;
		
		for(int i=0; i<rules.length; i++)
		{
			if(rules[i].equals(pair1))
			{
				changes[i].addTimesApplied(tmp);
			}
			if(rules[i].equals(pair2))
			{
				changes[i].addTimesApplied(tmp);
			}
		}
	}
	
	public int getIndexLetterInCountList(Letter l)
	{
		for(int i=0; i<countLetters.size(); i++)
		{
			if(countLetters.get(i).equals(l))
			{
				return i;
			}
		}
		return -1;
	}
	
	public long computeFinalAnswer()
	{
		initializeCountLetters();

		for(int i=0; i<rules.length; i++)
		{
			long sCount = rules[i].getTimesApplied();
			
			Letter l1 = new Letter(rules[i].searchPattern.charAt(0)+"");
			int i1 = getIndexLetterInCountList(l1);
			countLetters.get(i1).addToQuantity(sCount);
			
			Letter l2 = new Letter(rules[i].searchPattern.charAt(1)+"");
			int i2 = getIndexLetterInCountList(l2);
			countLetters.get(i2).addToQuantity(sCount);
		}
		
		wipeOutDuplicates();
		
		long min = Long.MAX_VALUE;
		long max = Long.MIN_VALUE;
		for(int i=0; i<countLetters.size(); i++)
		{
			Letter l = countLetters.get(i);
			long q = l.getQuantity();
			
			if(min>q)
			{
				min = q;				
			}
			if(max < q)
			{
				max = q;				
			}
		}
		return max - min;
	}
	
	public void wipeOutDuplicates()
	{
		//damit alle als duplikate vorliegen, anfang und ende auch doppelt rein geben
		Letter firstLetter = new Letter(inputString.charAt(0)+"");
		int iFirst = getIndexLetterInCountList(firstLetter);
		countLetters.get(iFirst).addToQuantity(1);
		
		Letter lastLetter = new Letter(inputString.charAt(inputString.length()-1)+"");
		int iLast = getIndexLetterInCountList(lastLetter);
		countLetters.get(iLast).addToQuantity(1);
				
		for(int i=0; i<countLetters.size(); i++)
		{
			countLetters.get(i).halveQuantity();
		}
	}
	
	public static void main(String[] args) throws IOException, CloneNotSupportedException
	{
		new Dec14();
	}
	
	public void solveTask1()
	{
		try
		{
			erg1 = solve(10);
		} 
		catch (IOException | CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
	}
	
	public void solveTask2() 
	{
		try
		{
			erg2 = solve(40);
		} 
		catch (IOException | CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
	}
}

class Rule implements Cloneable
{
	public final String searchPattern;
	public final String addedLetter;
	
	public final String newPair1;
	public final String newPair2;
	
	private long timesApplied;
	
	public Rule(String pattern, String letter)
	{
		searchPattern = pattern;
		addedLetter = letter;
		newPair1 = pattern.charAt(0) + letter;
		newPair2 = letter + pattern.charAt(1);
		timesApplied = 0;
	}
	
	public long getTimesApplied()
	{
		return timesApplied;
	}
	
	public void setTimesApplied(long timesApplied)
	{
		this.timesApplied = timesApplied;
	}
	
	public void addTimesApplied(long addition)
	{
		this.timesApplied += addition;
	}
	
	public void incrementTimesApplied()
	{
		this.timesApplied++;
	}
	
	@Override
	public boolean equals(Object givenPattern)
	{
		String s = (String) givenPattern;
		if(this.searchPattern.equals(s))
		{
			return true;
		}
		return false;
	}

	@Override
	protected Rule clone() throws CloneNotSupportedException
	{
		Rule r = new Rule(this.searchPattern, this.addedLetter);
		r.setTimesApplied(this.timesApplied);
		return r;
	}	
}

class Letter
{
	public final String letter;
	private long quantity;
	
	public Letter(String l)
	{
		letter = l;
	}
	
	public Letter(String l, int q)
	{
		this(l);
		quantity = q;
	}

	public long getQuantity()
	{
		return quantity;
	}

	public void addToQuantity(long q)
	{
		quantity += q;
	}
	
	public void halveQuantity()
	{
		quantity /= 2;
	}

	@Override
	public boolean equals(Object obj)
	{
		Letter l = (Letter) obj;
		if(this.letter.equals(l.letter))
		{
			return true;
		}
		return false;
	}
}