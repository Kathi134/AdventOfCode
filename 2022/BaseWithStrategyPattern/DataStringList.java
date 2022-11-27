package BaseWithStrategyPattern;

import java.io.IOException;
import java.util.ArrayList;


public class DataStringList extends DataBaseStrategy<String>
{

	public DataStringList()
	{
		data = new ArrayList<String>();
	}
	
	@Override
	public void read() throws IOException
	{
		for(int l=0; l<lines; l++)
		{
			String line = br.readLine();
			data.add(line);
		}
	}
	
}
