package Raetsel2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import Base.InputKind;
import Base.Position;

public class Dec16 extends Puzzle2022
{
	public Dec16()
	{
		super(16);
		readInputDivided(InputKind.StringTable, "; ");

		computeSolution(1);
		computeSolution(2);

		printSolution();
	}

	String[] ids;
	int[] flows;
	String[][] neighbours;

	Node AA;
	List<Node> nodes = new ArrayList<>();
	ArrayList<Node> information = new ArrayList<>();

	public void read()
	{
		int AApos = -1;

		ids = new String[lines];
		flows = new int[lines];
		neighbours = new String[lines][];

		for (int i = 0; i < inputStringTable.length; i++)
		{
			String words = inputStringTable[i][0];
			String[] tmp = words.split(" ");
			ids[i] = tmp[1];
			if (tmp[1].equals("AA"))
			{
				AApos = i;
			}

			flows[i] = Integer.parseInt(tmp[4].replace("rate=", ""));

			words = inputStringTable[i][1];
			tmp = words.split(" ");

			neighbours[i] = new String[tmp.length - 4];
			for (int p = 4; p < tmp.length; p++)
			{
				neighbours[i][p - 4] = tmp[p].replace(",", "");
			}
		}

		AA = readNodes("AA");
	}

	public Node readNodes(String id)
	{
		int pos = Arrays.asList(ids).indexOf(id);

		Node res = new Node(ids[pos], flows[pos], pos);
		nodes.add(res);

		for (int i = 0; i < neighbours[pos].length; i++)
		{
			String neighbourId = neighbours[pos][i];
			int indexOfId = indexOfId(neighbourId);
			if (indexOfId < 0)
			{
				res.neighbours.add(readNodes(neighbourId));
			} else
			{
				res.neighbours.add(nodes.get(indexOfId));
			}
		}

		return res;
	}

	public int indexOfId(String id)
	{
		int i = 0;
		for (Node n : nodes)
		{
			if (n.id.equals(id))
			{
				return i;
			}
			i++;
		}
		return -1;
	}

	@Override
	public void solveTask1()
	{
		read();

		visit(AA, new ArrayList<>());

		System.out.println();
	}

	int minute = 0; // zum gehen in AA rechne eins drauf
	int[] releasePerMinute = new int[31]; 

	public void visit(Node node, List<Node> path)
	{
		minute++;
		node.visited = true;
		path.add(node);
		releasePerMinute[minute] = releasePerMinute[minute-1];

		if (node.rate > 0)
		{
			minute++;
			node.release += minute * node.rate;
			releasePerMinute[minute] = releasePerMinute[minute-1] + node.rate;
		}

		for (Node n : node.neighbours)
		{
			if (!n.visited)
			{
				visit(node, path);
			}
		}
	}

	@Override
	public void solveTask2()
	{

	}

	public static void main(String[] args)
	{
		String str = "Valve AA has flow rate=0; tunnels lead to valves DD, II, BB";
		String[] split = str.split("; tunnels lead to valves ");
		new Dec16();
	}

	class Node // implements Comparable<Node>
	{
		int release;
		boolean visited;

		String id;
		List<Node> neighbours = new ArrayList<>();
		int rate;
		int index;

		public Node(String id, int rate, int index)
		{
			this.id = id;
			this.rate = rate;
			this.index = index;
			this.visited = false;
//			this.gesamtDistanz = Integer.MAX_VALUE;
			this.release = 0;
		}

		public boolean isVisited()
		{
			return visited;
		}

		public void setVisited(boolean visited)
		{
			this.visited = visited;
		}

//		public int getGesamtDistanz()
//		{
//			return gesamtDistanz;
//		}
//
//		public void setGesamtDistanz(int gesamtDistanz)
//		{
//			this.gesamtDistanz = gesamtDistanz;
//		}

		public String toString()
		{
			return "Node " + id + ": " + release;
		}

//		@Override
//		public int compareTo(Node o)
//		{
//			Integer thisGesDis = gesamtDistanz;
//			Integer nextGesDis = o.gesamtDistanz;
//			return thisGesDis.compareTo(nextGesDis);
//		}

		@Override
		public boolean equals(Object obj)
		{
			return this.id.equals(((Node) obj).id);
		}

	}
}
