package Raetsel2022;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Base.InputKind;

//1599
//14112

public class Dec19 extends Puzzle2022
{
	public Dec19()
	{
		super(19);
		readInput(InputKind.StringList);
		read();
		
		computeSolution(1);
		computeSolution(2);

		printSolution();
	}
	
	List<Blueprint> blueprints = new ArrayList<>();

	public void read()
	{
		for(String s: inputStringList)
		{
			String[] words = s.split(" ");
			int id = Integer.parseInt(words[1].replace(":", ""));
			
			Map<Material, Integer> oreRobot = new HashMap<>();
			int costs = Integer.parseInt(words[6]);
			oreRobot.put(Material.ORE, costs);
			
			Map<Material, Integer> clayRobot = new HashMap<>();
			costs = Integer.parseInt(words[12]);
			clayRobot.put(Material.CLAY, costs);
			
			Map<Material, Integer> obsidianRobot = new HashMap<>();
			costs = Integer.parseInt(words[18]);
			obsidianRobot.put(Material.ORE, costs);
			costs = Integer.parseInt(words[21]);
			clayRobot.put(Material.CLAY, costs);
						
			Map<Material, Integer> geodeRobot = new HashMap<>();
			costs = Integer.parseInt(words[27]);
			geodeRobot.put(Material.ORE, costs);
			costs = Integer.parseInt(words[30]);
			geodeRobot.put(Material.OBSIDIAN, costs);
			
			Map<Material, Map<Material, Integer>> map = new HashMap<>();
			map.put(Material.ORE, oreRobot);
			map.put(Material.CLAY, clayRobot);
			map.put(Material.OBSIDIAN, obsidianRobot);
			map.put(Material.GEODE, geodeRobot);
			
			Blueprint b = new Blueprint(id, map);
			blueprints.add(b);
		}
	}
	
	@Override
	public void solveTask1()
	{
		int sum = 0;
		for(Blueprint b: blueprints)
		{
			sum += b.simulate();
		}
		
		erg1 = sum;
	}

	@Override
	public void solveTask2()
	{
		
	}
	
	public static void main(String[] args)
	{
		new Dec19();
	}
}

class Blueprint
{
	Map<Material, Map<Material, Integer>> costs = new HashMap<>();
	int id;
	
	HashMap<Material, Integer> collectedMats = new HashMap<>();
	HashMap<Material, Integer> robots = new HashMap<>();
	
	int maxRobots = 0;
	
	public Blueprint(int id, Map<Material, Map<Material, Integer>> map)
	{
		this.id = id;
		this.costs = map;
		
		robots.put(Material.ORE, 1);
		robots.put(Material.CLAY, 0);
		robots.put(Material.OBSIDIAN, 0);
		robots.put(Material.GEODE, 0);
		
		collectedMats.put(Material.ORE, 0);
		collectedMats.put(Material.CLAY, 0);
		collectedMats.put(Material.OBSIDIAN, 0);
		collectedMats.put(Material.GEODE, 0);
	}
	
	public int simulate()
	{
		for(int i=0; i<=24; i++)
		{
			//add new robots to the list as soon as you can affort
			//get all existing robots and let them collect
			
			System.out.println(i + ":\nrobots" + robots + "\n" + "coMats" + collectedMats +"\n");
			HashMap<Material, Integer> collectedBefore = clone(collectedMats);
			
			for(Material robotType: robots.keySet())
			{
				int collected = collectedMats.get(robotType);
				collected += robots.get(robotType);
				collectedMats.put(robotType, collected);
			}
			createNewRobots(collectedBefore);
		}
		maxRobots = robots.size();
		return maxRobots * id;
	}
	
	private void createNewRobots(HashMap<Material, Integer> collected)
	{
		for(Material robotType: robots.keySet())
		{
			boolean enough = true;
			Map<Material, Integer> robotCosts = costs.get(robotType);
			while(enough)
			{
				for(Material costType: robotCosts.keySet())
				{
					int stored = collected.get(costType);
					if(stored < robotCosts.get(costType))
					{
						enough = false;
						break;
					}
				}
				
				if(enough)
				{
					for(Material costType: robotCosts.keySet())
					{
						int stored = collected.get(costType);
						stored -= robotCosts.get(costType);
						collected.put(robotType, stored);
					}
					int r = robots.get(robotType);
					r++;
					robots.put(robotType, r);
				}
			}
		}
	}
	
	private HashMap<Material, Integer> clone(HashMap<Material, Integer> original)
	{
		HashMap<Material, Integer> res = new HashMap<>();
		for(Material key: original.keySet())
		{
			res.put(key, original.get(key));
		}
		return res;
	}
}

enum Material
{
	ORE, CLAY, OBSIDIAN, GEODE;
}