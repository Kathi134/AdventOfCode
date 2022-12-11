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

	List<Monkey> monkeys = new ArrayList<>();
	
	public void read()
	{
		int j = 0;
		for(int i=0; i<=7; i++)
		{
			String currLine = inputStringList[j++];
			int id = Integer.parseInt(currLine.replace(":", "").split(" ")[1]);
			
			currLine = inputStringList[j++];
			String[] tmp = currLine.replace(",", "").trim().split(" ");
			List<Integer> items = new ArrayList<>();
			for(int t=2; t<tmp.length; t++)
			{
				items.add(Integer.parseInt(tmp[t]));
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
			
			monkeys.add(new Monkey(id, items, divisor, tmtmp, fmtmp));
			
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
		read();

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
		erg1 =  mostBusy.inspections * scndBusy.inspections;
	}

	@Override
	public void solveTask2()
	{
		
	}
	
	public static void main(String[] args)
	{
		new Dec11();
	}
}

class Monkey implements Comparable<Monkey>
{
	public int id;
	public List<Integer> items;
	public int divisor;
	public int trueMonkeyTmp;
	public int falseMonkeyTmp;
	public Monkey trueMonkey;
	public Monkey falseMonkey;
	public int inspections = 0;
	
	public Monkey(int id, List<Integer> items, int divisor, int tmtmp, int fmtmp)
	{
		this.id = id;
		this.items = items;
		this.divisor = divisor;
		this.trueMonkeyTmp = tmtmp;
		this.falseMonkeyTmp = fmtmp;
	}
	
	public void setMonkeys(Monkey trueMonkey, Monkey falseMonkey)
	{
		this.trueMonkey = trueMonkey;
		this.falseMonkey = falseMonkey;
	}
	
	public static int operation(int id, int currItem)
	{		
		int newItem = 0;
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
			int currItem = items.remove(0);
			inspections++;
			throwItem(currItem);
		}
	}
	
	public void throwItem(int item)
	{
		item = Monkey.operation(id, item);
		item /= 3;
		boolean test = item%divisor == 0;
		if(test)
			trueMonkey.catchItem(item);
		else
			falseMonkey.catchItem(item);
	}
	
	public void catchItem(int item)
	{
		items.add(item);
	}

	@Override
	public int compareTo(Monkey m)
	{
		return inspections - m.inspections;
	}
}



