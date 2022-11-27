package Raetsel2021;

import java.io.IOException;

public class Dec17 extends Puzzle
{
	public int targetMinX;
	public int targetMaxX;
	public int targetMinY;
	public int targetMaxY;
	
	public int maxHeight;
	public int cntDirections;
	
	public void read() throws IOException
	{
		readInput("String");
		String[] parts = inputString.replace("target area: x=", "").replace(" y=", "").split(","); //splittet input in ein Feld: [175..227][-134..-79]
		int[] arrX = splitToIntegerArr(parts[0], "\\.\\."); //x und y werden in ein weiteres feld mit .. gesplittet
		int[] arrY = splitToIntegerArr(parts[1], "\\.\\.");
		
		targetMinX = arrX[0];
		targetMaxX = arrX[1];
		targetMinY = arrY[0];
		targetMaxY = arrY[1];
	}
	
	public Dec17() throws IOException
	{
		super(17);
		read();
		
		computeSolution(1);
		erg1 = maxHeight;
		computeSolution(2);
		erg2 = cntDirections;
		//printSolution();
		
		printFormattedSolution("Trick Shot", 
				"maximum reachable height", 
				"number of start velocities reaching the target");
	}
	
	public void solve()
	{
		//shoot(6, 0);
		maxHeight = Integer.MIN_VALUE;
		cntDirections = 0;
		
		//150 random brute force wert
		for(int y=targetMinY; y<150; y++) //probiere bis zur Einzel-Wurfrichtung y von 150, nach unten werfen macht kein sinn weil höchstes finden gesucht
		{
			for(int x=0; x<targetMaxX+1; x++) //wenn du mit einem schritt direkt aufs letzte targetX werfen kannst
			{
				shoot(x, y);
			}
		}
	}
	
	public void shoot(int xDirection, int yDirection) //brechnet die Flugbahn der angegebene anfacngs-rihctung
	{
		int thisMaxY = Integer.MIN_VALUE; //es wirft eh niemand nach unten
		int xPos = 0;
		int yPos = 0;
		
		while(true) //mach weiter solange nicht beendet
		{
			xPos += xDirection;
			yPos += yDirection;
			if(yPos > thisMaxY)
			{
				thisMaxY = yPos; //speichere höchste höhe für diesen schuss
			}
			
			if(xPos>=targetMinX && xPos<=targetMaxX && yPos>=targetMinY && yPos<=targetMaxY) //wenn ziel getroffen
			{
				cntDirections++; //zähle einen erfolgreichen schuss dazu
				if(thisMaxY>maxHeight) //prüfe ob neuer höhen rekord
				{
					maxHeight = thisMaxY;
				}
				break; //beende dann den schuss
			}
			else if(yPos<targetMinY || xPos>targetMaxX) //wenn am Ziel vorbei
			{		//kleiner weil negativ
				break;
			}
			
			yDirection--; //gravity
			xDirection -= xDirection==0 ? 0 : xDirection/Math.abs(xDirection); //drag
		}
	}
	
	public void solveTask1()
	{
		solve();
	}
	
	public void solveTask2()
	{
		solve();
	}
	
	public static void main(String[] args) throws IOException
	{
		new Dec17();
	}
}
