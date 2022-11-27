package Raetsel2021;

import java.io.IOException;
import java.util.*;

/*\ Beacon Scanner \*/
public class Dec19 extends Puzzle
{
	Coordinate[] coordinate;
	
	//parse the given input into a list of scanners
    public List<Scanner> read()
    {
    	List<Scanner> scanners = new ArrayList<Scanner>();
        
		for(int i=0; i<inputStringList.length; i++)
		{
			String currLine = inputStringList[i].replace("--- scanner " + i + " --- ", "");
			String[] triplets = currLine.split(" ");
			
			List<Coordinate> currBeacons = new ArrayList<Coordinate>();
			for(String str: triplets)
			{
				int[] tmp = splitToIntegerArr(str, ",");
				currBeacons.add(new Coordinate(tmp[0], tmp[1], tmp[2]));
			}
			scanners.add(new Scanner(currBeacons));
		}

        return scanners;
    }
	
	public Dec19() throws IOException
	{
		super(19);
		readInput("seperated StringList");
		
		computeSolution(1);
		computeSolution(2);
		//printSolution();
		
		printFormattedSolution("Beacon Scanner", 
				"number of beacons in the full map", 
				"biggest Manhattan distance");
	}
	
	//erstellt eine liste von scannerOrientations aus einer Liste scanner-Rohdaten
    public List<ScannerOrientation> orientations(List<Scanner> scanners) 
    {
        List<ScannerOrientation> orientationList = new ArrayList<>();
        
        for(int i=0; i<scanners.size(); i++)
        {
        	Scanner currScanner = scanners.get(i);
        	orientationList.add(new ScannerOrientation(currScanner));
        }
        return orientationList;
    }
    
    //müsste eig iwie mit source.toArray() gehen aber idk
    //konvertiert source-list von einem selbstdefinierten Typ in ein feld
    public ScannerOrientation[] toArray(List<ScannerOrientation> source)
    {
    	ScannerOrientation[] array = new ScannerOrientation[source.size()];
    	for(int i=0; i<array.length; i++)
    	{
    		array[i] = source.get(i);
    	}
    	return array;
    }

    public void solveTask1() 
    {
    	List<Scanner> s = read(); //liste mit scanner-rohdaten vom input
    	
    	List<ScannerOrientation> scannerOrientations = orientations(s);  	
    	ScannerOrientation[] scanners = toArray(scannerOrientations); //feld, das alle scanner mit stellungen speichert
    																  //bei initialisierung erst mal alle möglcihen 24
        Scanner[] orientation = new Scanner[scanners.length]; //feld, das die richtigen orientations aller scanner speichern soll
        coordinate = new Coordinate[scanners.length]; //feld, das die richtigen coordinates aller scanner speichern soll

        orientation[0] = scanners[0].get(0, 0); //der erste scanner ist in rotation 
        coordinate[0] = new Coordinate(0,0,0);  //und position der default wert an den alle angepasst werden

        ArrayList<Integer> borderQueue = new ArrayList<Integer>(); 
        borderQueue.add(0); //ursprungs scanner rein da
        
        while(!borderQueue.isEmpty())
        {
        	int first = borderQueue.remove(borderQueue.size()-1); //letztes element aka als erstes reingegebenes 
        	Scanner defaultScanner = scanners[first].get(0, 0); //immer der letzte overlappende scanenr
        	
        	for(int i=0; i<scanners.length; i++) //für jeden scanner
        	{
        		if(coordinate[i] == null) //wenn koordinate des scanners noch nciht gefunden
        		{
        			Scanner currScanner = scanners[i].get(0, 0);
        			if(defaultScanner.getOverlaps(currScanner)) //falls mind 12 overlaps
                    {
                        Pair<Scanner, Coordinate> match = orientation[first].match(scanners[i]); 
                        
                        if (match != null) 
                        {
                            orientation[i] = match.a; // correct orientation!
                            coordinate[i] = new Coordinate(coordinate[first], match.b);
                            borderQueue.add(i);
                        }
                    }
        		}
        	}
        }
        
        Scanner result = new Scanner(orientation[0]);
        for (int i = 1; i < scanners.length; i++) 
        {
            result.add(orientation[i], coordinate[i]);
        }
        erg1 = result.beacons.size();       
    }
    
    public void solveTask2()
    {
    	 int maxDist = Integer.MIN_VALUE;
         
         for (int i = 0; i < coordinate.length; i++) 
         {
             for (int j = 0; j < coordinate.length; j++) 
             {
            	 Coordinate one = coordinate[i];
            	 Coordinate two = coordinate[j];
                 int d = Math.abs(one.x-two.x) + Math.abs(one.y-two.y) + Math.abs(one.z-two.z);
                 
                 if(d > maxDist)
                 {
                 	maxDist = d;
                 }
             }
         }
         erg2 = maxDist;
    }
	
    public static void main(String[] args) throws IOException
    {
    	new Dec19();
    }
    
}

class Scanner
{
    final List<Coordinate> beacons;
    int[][] signature;

    public Scanner(Scanner other) 
    {
        this.beacons = new ArrayList<>(other.beacons);
    }
    
    public Scanner(List<Coordinate> beacons)
    {
    	this.beacons = beacons;
    }

    public Scanner(Scanner other, int up, int rot) 
    {
        this.beacons = new ArrayList<>();
        for(int i=0; i<other.beacons.size(); i++) 
        {
        	Coordinate c = other.beacons.get(i);
            this.beacons.add(c.up(up).rot(rot));
        }
    }

    public int[][] getSignature() 
    {
        if(signature == null) //wenn signature bzw stellung noch nciht festgelegt
        {
            signature = new int[beacons.size()][beacons.size()]; 
            for (int i=0; i<beacons.size(); i++) 
            {
                for (int j=0; j<beacons.size(); j++) 
                {
                    signature[i][j] = beacons.get(i).dist(beacons.get(j)); //für jeden beacon 
                }
                Arrays.sort(signature[i]);
            }
        }
        return signature;
    }

    public boolean getOverlaps(Scanner other) 
    {
        for(int i=0; i<beacons.size(); i++) //schaue jeden meiner beacons an
        {
            for(int j=0; j<other.beacons.size(); j++) //und gehe dafür jeden beacon des anderen durch
            {
                var p1 = getSignature()[i];
                var p2 = other.getSignature()[j];
                
                // check if fingerprint matches
                int x = 0;
                int y = 0;
                int count = 0;
                
                while(x < p1.length && y < p2.length) 
                {
                    if(p1[x] == p2[y]) 
                    {
                        x++;
                        y++;
                        count++;
                        
                        if (count >= 12) //wenn mindestens 12 overlaps gefunden
                        {
                        	return true;
                        }
                    } 
                    else if(p1[x] > p2[y]) 
                    {
                        y++;
                    } 
                    else if(p1[x] < p2[y]) 
                    {
                        x++;
                    }
                }
            }
        }
        return false;
    }

    
    
    private Coordinate test(Scanner other) 
    {
        for (int i = 0; i < beacons.size(); i++) 
        {
            for (int j = 0; j < other.beacons.size(); j++) 
            {
                var mine = beacons.get(i);
                var their = other.beacons.get(j);
                var relx = their.x - mine.x;
                var rely = their.y - mine.y;
                var relz = their.z - mine.z;
                int count = 0;
                
                for (int k = 0; k < beacons.size(); k++) 
                {
                    if ((count + beacons.size() - k) < 12) break; // not possible
                    for (int l = 0; l < other.beacons.size(); l++) 
                    {
                        var m = beacons.get(k);
                        var n = other.beacons.get(l);
                        if ((relx + m.x) == n.x && (rely + m.y) == n.y && (relz + m.z) == n.z) 
                        {
                            count++;
                            if (count >= 12) 
                            {
                            	return new Coordinate(relx, rely, relz);
                            }
                            break;
                        }
                    }
                }
            }
        }
        return null;
    }

    
    //match gegebenen scanner (für alle orientations) mit meinen beacons
    public Pair<Scanner, Coordinate> match(ScannerOrientation other) 
    {
        for (int i = 0; i < other.variations.length; i++) 
        {
            var sc = other.variations[i];
            var mat = test(sc);
            if (mat != null) 
           	{
            	return Pair.of(sc, mat);
            }
        }
        return null;
    }

    //füge alle beacons vom anderen in relativen koordinaten in meine beacon-lsite
    public void add(Scanner other, Coordinate relPos)
    {
        for (int l = 0; l < other.beacons.size(); l++) 
        {
            var n = other.beacons.get(l);
            n = new Coordinate(n.x - relPos.x, n.y - relPos.y, n.z - relPos.z);
            if (!beacons.contains(n)) beacons.add(n);
        }
    }
}


class ScannerOrientation 
{
    final Scanner[] variations; //alle möglichen orientations des übergebenen scanners

    public ScannerOrientation(Scanner sc) 
    {
        variations = new Scanner[24];
        for (int up = 0; up < 6; up++) //6 mögliche rihctungen, die für den scanner z sein können
        {
            for (int rot = 0; rot < 4; rot++) //4 mögliche rotationsvarianten für x und y
            {
                variations[rot + up*4] = new Scanner(sc, up, rot); //an der entsprechenden position in variations speicehrn
            }
        }
    }

    public Scanner get(int up, int rot) 
    {
        return variations[rot + up*4]; //hol den scanner mit der übergebenen variation raus 
    }
}



class Coordinate 
{
    int x;
    int y;
    int z;

    public Coordinate(int x, int y, int z) 
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Coordinate(Coordinate one, Coordinate two) //erstellt eine neue koordinate addiert aus den übergebenen
    {
        this(one.x+two.x, one.y+two.y, one.z+two.z);
    }

    public Coordinate up(int up) 
    {
        return switch (up) 
        {
            case 0 -> this;
            case 1 -> new Coordinate(x, -y, -z);
            case 2 -> new Coordinate(x, -z, y);
            case 3 -> new Coordinate(-y, -z, x);
            case 4 -> new Coordinate(-x, -z, -y);
            case 5 -> new Coordinate(y, -z, -x);
            default -> null;
        };
    }

    public Coordinate rot(int rot) 
    {
        return switch (rot) 
        {
            case 0 -> this;
            case 1 -> new Coordinate(-y, x, z);
            case 2 -> new Coordinate(-x, -y, z);
            case 3 -> new Coordinate(y, -x, z);
            default -> null;
        };
    }

    public int dist(Coordinate other) 
    {
        return Math.abs(other.x-x) + Math.abs(other.y-y) + Math.abs(other.z-z);
    }

    @Override
    public boolean equals(Object o) 
    {
        if (this == o) 
       	{
        	return true;
        }
        if (o == null || getClass() != o.getClass()) 
        {
        	return false;
        }
        Coordinate pos3 = (Coordinate) o;
        return x == pos3.x && y == pos3.y && z == pos3.z;
    }

    @Override
    public int hashCode() 
    {
        return Objects.hash(x, y, z);
    }
}

//idk anything bout this, Tom van Dijk aka "trolando" @github helped me out
@SuppressWarnings("hiding")
class Pair <A, B> 
{
    A a;
    B b;

    public Pair(A a, B b) 
    {
        this.a = a;
        this.b = b;
    }

    public static <A, B> Pair<A, B> of(A a, B b)
    {
        return new Pair<>(a, b);
    }
}
