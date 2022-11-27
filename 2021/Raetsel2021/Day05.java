package Raetsel2021;

public class Day05
{	
	public static void main(String[] args)
	{
		String data = "0,9 -> 5,9\n"
				+ "8,0 -> 0,8\n"
				+ "9,4 -> 3,4\n"
				+ "2,2 -> 2,1\n"
				+ "7,0 -> 7,4\n"
				+ "6,4 -> 2,0\n"
				+ "0,9 -> 2,9\n"
				+ "3,4 -> 1,4\n"
				+ "0,0 -> 8,8\n"
				+ "5,5 -> 8,2";
		Day05 d = new Day05();
		
		d.inputToArray(data);
		d.createSolutionTable();
		
		/*TODO: 1 
		 *	rufe für jede input-zeile die methode drawLine auf
		 */
		
		/*TODO: 5
		 * lass dir die Lösung (die gezählten Overlaps) anzeigen
		 */
	}
	
	
	int[][] input;
	int[][] solutionTable;
	
	
	/**
	 * liest den Text-Input in ein int-Array ein, auf dem das Programm arbeiten kann
	 * @param data: der input von der AoC-Website
	 */
	public void inputToArray(String data)
	{	
		data = data.replace(" -> ", ",");
		String dataArr[] = data.split("\n");
		
		input = new int[dataArr.length][];
		for(int i=0; i<dataArr.length; i++)
		{
			String curr = dataArr[i];
			String[] split = curr.split(",");
			input[i] = new int[]{Integer.parseInt(split[0]),
	                Integer.parseInt(split[1]),
	                Integer.parseInt(split[2]),
	                Integer.parseInt(split[3])};
		}
	}
	
	
	/**
	 * erstellt das Feld auf dem die Linien eingezeichnet werden sollen
	 */
	public void createSolutionTable()
	{
		int biggestNumber = Integer.MIN_VALUE;
		for(int i=0; i<input.length; i++)
		{
			for(int j=0; j<input[i].length; j++)
			{
				if(input[i][j] > biggestNumber)
					biggestNumber = input[i][j];
			}
		}
	}
	
	
	/**
	 * Meta-Methode, die entsprechende Tochter-Methoden zum Zeichnen aufruft
	 * @param numbers: die Koordinaten in der aktuellen Zeile
	 */
	public void drawLine(int[] numbers) 
	{
		/*TODO: 2
		 * Fallunterscheidung: erkenne ein Muster, anhand dem du feststellst, wann es eine
		 *	a) horizontale Linie (zb. 1,0 -> 3,0) 
		 *	b) vertikale Linie   (zb. 0,1 -> 0,3)
		 *	c) diagonale Linie   (zb. 0,1 -> 2,3)
		 * ist. rufe entsprechend die Methoden drawVertical und drawHorizontal auf
		 * [diagonale müssen für die aufgabe 1 nicht beachtet werden]
		 */
		
		//Tipp: welche zahlen müssen identisch sein, damit welcher fall gegeben ist?
	}
	
	
	/**
	 * Zeichnet eine Horizontale Linie 
	 * @param numbers: die Koordinaten in der aktuellen Zeile
	 */
	public void drawHorizontal(int[] numbers)
	{
		/*TODO: 3
		 * Iteration über alle x-Werte, die markiert werden müssen (der y-Wert ist fix.)
		 * Zeichne die Linie jeweils in solutionTable[y][iteration]
		 */
		
		//Tipp1: An welcher Stelle im übergebenen Array findest du x-Anfang und x-Ende der Linie? 
		//Tipp2: Baue eine Schleife, die alle x-Werte die zwischen Anfang und Ende liegen abklappert.
		//Tipp3: Denke vorausschauend und arbeite beim Linie-eintragen mit Inkrementierung.
	}
	
	
	/**
	 * Zeichnet eine Vertikale Linie 
	 * @param numbers: die Koordinaten in der aktuellen Zeile
	 */
	public void drawVertical(int[] numbers)
	{
		/*TODO: 3
		 * Iteration über alle y-Werte, die markiert werden müssen (der x-Wert ist fix.)
		 * Zeichne die Linie jeweils in solutionTable[iteration][x]
		 */
		
		//Tipp1: An welcher Stelle im übergebenen Array findest du y-Anfang und y-Ende der Linie?
		//Tipp2: Baue eine Schleife, die alle y-Werte, die zwischen Anfang und Ende liegen, abklappert.
		//Tipp3: Denke vorausschauend und arbeite beim Linie-eintragen mit Inkrementierung.
	}
	
	
	/**
	 * Gibt die Anzahl aller Mehrfach-Linien in SolutionTable zurück
	 */
	public int countOverlaps()
	{
		int overlaps = 0;
		/* TODO: 4
		 * Iteration über das ganze SolutionTable-Feld
		 * Immer wenn ein Wert >1 gefunden, zähle den Counter eins hoch
		 */
		
		//Tipp: zum Iterieren über ein mehrdimensionales Feld braucht man immer eine geschachtelte Schleife
		
		return overlaps;
	}
}