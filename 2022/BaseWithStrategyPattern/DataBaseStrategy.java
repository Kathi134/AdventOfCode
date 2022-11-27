package BaseWithStrategyPattern;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public abstract class DataBaseStrategy<T>
{
	public File f;
	
	public List<T> data;
	protected BufferedReader br = null;
	
	int records = 1; //wenn ein datensatz über mehrere zeilen geht, ist records die anzahl datensätze
	int lines = 0;
	int columns = 0; //anzahl chars in einer Zeile
	
	
	public void start(File file)
	{
		f = file;
		prepare();
	}
	
	
	/**
	 * create Reader on injected file, count lines, prepare reading
	 * @param file: the file the input data is stored in
	 */
	public void prepare()
	{
		try
		{
			FileReader fr = new FileReader(f);
			br = new BufferedReader(fr);
			
			lines = 0;
			
			while (br.readLine() != null)
			{
				lines++;
			}
			resetReader();
			
			columns = br.readLine().length();
			resetReader();
			
			read();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	//resetReader bringt den Reader an den Anfang des Dokuments
	private void resetReader() throws IOException
	{
		//der br1 ist schon mit fr ans ende des files durchgelaufen, reset() doesnt work, also neue Reader anlegen
		FileReader fr2 = new FileReader(f);
		br = new BufferedReader(fr2);	
	}
	
	/**
	 * reads the file into the strategy-specific format
	 * @param f
	 */
	public abstract void read() throws IOException;
	
}
