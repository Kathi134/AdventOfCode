package BaseWithStrategyPattern;

import java.io.IOException;
import java.util.ArrayList;


public class DataIntegerList extends DataBaseStrategy<Integer>
{
	public DataIntegerList()
	{
		data = new ArrayList<Integer>();
	}
	
	
	@Override
	public void read() throws IOException
	{
		for(int l=0; l<lines; l++)
		{
			String line = br.readLine();
			data.add(Integer.parseInt(line));
		}
		
	}



}
