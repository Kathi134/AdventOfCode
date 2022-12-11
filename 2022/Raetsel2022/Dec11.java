package Raetsel2022;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Base.InputKind;

public class Dec11 extends Puzzle2022
{
	public Dec11()
	{
		super(11);
		readInput(InputKind.StringList);
		
		computeSolution(1);
		computeSolution(2);

		printSolution();
	}

	List<Monkey> monkeys;
	
	public void read(boolean task1)
	{
		monkeys = new ArrayList<>();
		
		int j = 0;
		for(int i=0; i<=7; i++)
		{
			String currLine = inputStringList[j++];
			int id = Integer.parseInt(currLine.replace(":", "").split(" ")[1]);
			
			currLine = inputStringList[j++];
			String[] tmp = currLine.replace(",", "").trim().split(" ");
			List<Long> items = new ArrayList<>();
			for(int t=2; t<tmp.length; t++)
			{
				items.add(Long.parseLong(tmp[t]));
			}
			
			j++; //skip operation line
			
			currLine = inputStringList[j++];
			tmp = currLine.split(" ");
			int divisor = Integer.parseInt(tmp[tmp.length-1]);
			
			currLine = inputStringList[j++];
			tmp = currLine.split(" ");
			int tmtmp = Integer.parseInt(tmp[tmp.length-1]);
			
			currLine = inputStringList[j++];
			tmp = currLine.split(" ");
			int fmtmp = Integer.parseInt(tmp[tmp.length-1]);
			
			monkeys.add(new Monkey(id, items, divisor, tmtmp, fmtmp, task1));
			
			j++; //skip empty line
		}
		
		for(Monkey m: monkeys)
		{
			m.setMonkeys(monkeys.get(m.trueMonkeyTmp), monkeys.get(m.falseMonkeyTmp));
		}
		
	}
	
	@Override
	public void solveTask1()
	{
		read(true);

		for(int round=0; round<20; round++)
		{
			for(Monkey m: monkeys)
			{
				m.executeTurn();
			}
		}
		
		List<Monkey> tmp = new ArrayList<>(monkeys);
		Collections.sort(tmp);
		
		Monkey mostBusy = tmp.get(tmp.size()-1);
		Monkey scndBusy = tmp.get(tmp.size()-2);
//		System.out.println(mostBusy.inspections +" * "+ scndBusy.inspections);
		erg1 = mostBusy.inspections * scndBusy.inspections;
	}

	@Override
	public void solveTask2()
	{
		read(false);

		for(int round=0; round<10000; round++)
		{
			for(Monkey m: monkeys)
			{
				m.executeTurn();
			}
		}
		
		List<Monkey> tmp = new ArrayList<>(monkeys);
		Collections.sort(tmp);
		
		Monkey mostBusy = tmp.get(tmp.size()-1);
		Monkey scndBusy = tmp.get(tmp.size()-2);
//		System.out.println(mostBusy.inspections +" * "+ scndBusy.inspections);
		erg2 = mostBusy.inspections * scndBusy.inspections;
	}
	
	public static void main(String[] args)
	{
		new Dec11();
	}
}

class Monkey implements Comparable<Monkey>
{
	public int id;
	public List<Long> items;
	public int divisor;
	public int trueMonkeyTmp;
	public int falseMonkeyTmp;
	public Monkey trueMonkey;
	public Monkey falseMonkey;
	public long inspections = 0;
	public boolean task1;
	
	public Monkey(int id, List<Long> items, int divisor, int tmtmp, int fmtmp, boolean task1)
	{
		this.id = id;
		this.items = items;
		this.divisor = divisor;
		this.trueMonkeyTmp = tmtmp;
		this.falseMonkeyTmp = fmtmp;
		this.task1 = task1;
	}
	
	public void setMonkeys(Monkey trueMonkey, Monkey falseMonkey)
	{
		this.trueMonkey = trueMonkey;
		this.falseMonkey = falseMonkey;
	}
	
	public static long operation(int id, long currItem)
	{		
		long newItem = 0;
		switch(id)
		{
			case 0: newItem = currItem * 11; break;
			case 1: newItem = currItem + 4; break;
			case 2: newItem = currItem * currItem; break;
			case 3: newItem = currItem + 2; break;
			case 4: newItem = currItem + 3; break;
			case 5: newItem = currItem + 1; break;
			case 6: newItem = currItem + 5; break;
			case 7: newItem = currItem * 19; break;
		}
		return newItem;
	}
	
	public void executeTurn()
	{
		while(!items.isEmpty()) 
		{
			long currItem = items.remove(0);
			inspections++;
			throwItem(currItem);
		}
	}
	
	public void throwItem(long item)
	{
		item = Monkey.operation(id, item);
		if(task1)
			item /= 3;
		else
			item %= (2*3*5*7*11*13*17*19);
		
		boolean test = item % divisor == 0;
		if(test)
			trueMonkey.catchItem(item);
		else
			falseMonkey.catchItem(item);
	}
	
	public void catchItem(long item)
	{
		items.add(item);
	}

	@Override
	public int compareTo(Monkey m)
	{
		return (int)inspections - (int)m.inspections;
	}
}
