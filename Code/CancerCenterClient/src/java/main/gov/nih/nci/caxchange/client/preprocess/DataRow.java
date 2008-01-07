package gov.nih.nci.caxchange.client.preprocess;

/**
 * @author Jesse McKean
 *
 *	Custom data type to handle each row in the CSV
 */
public class DataRow {

	private String SEGMENT_ID = "LAB_DATA";
	private String[] row;
	private short[] ignoredRows;
		
	/**
	 * @param rowInfo, defaults, time, splits, columnOrder, columnNumber
	 * 
	 * Constructor
	 */
	public DataRow(String[] rowInfo, String[][] defaults, boolean[] time, short[] splits, short[] columnOrder, short columnNumber)
	{
		row = new String[columnNumber];
		ignoredRows = new short[splits.length/3];
		
		for(int y=0; y < columnOrder.length; y++)
		{			
			if (time[y] == true)
				row[columnOrder[y]] = leftPadTime((rowInfo[y]).trim());
			else
				row[columnOrder[y]] = (rowInfo[y]).trim();				
		}
		
		for (int z=0; z < defaults.length; z++)
			if (defaults[z][2] != null)
				row[Integer.parseInt(defaults[z][2])] = defaults[z][1];
		
		for (int x=0; x < splits.length; x+=3)
		{
			if (row[splits[x]] != null)
				formatResults(row[splits[x]], splits[x+1], splits[x+2]);	
			ignoredRows[x/3] = splits[x];
		}
	}
	
	/**
	 * @param TIME
	 * 
	 * Left pads the time with zeros
	 */
	private String leftPadTime(String TIME)
	{
		//Left Pads the time with zeros
		String LEFT_PADDED_TIME = TIME;
		while(LEFT_PADDED_TIME.length() < 4)
		{
			LEFT_PADDED_TIME = "0" + LEFT_PADDED_TIME;
		}
		return LEFT_PADDED_TIME;
	}
	
	/**
	 * @param value, valLow, textHigh
	 * 
	 * Determines if the results are text or numeric and splits low and
	 * high values into separate fields.
	 */
	private void formatResults(String value, short valLow, short textHigh)
	{
		if (value.contains("-"))
		{
			String[] temp = value.split("-");
			row[valLow] = temp[0];
			row[textHigh] = temp[1];	
		}
		else
		{		
			//tries to put the results into numeric form, if it fails, they must be text
			try
			{
				Double.parseDouble(value);
				row[valLow] = value;
				row[textHigh] = "";
			}
			catch(NumberFormatException ex)
			{
				row[textHigh] = value;
				row[valLow] = "";
			}	
		}
	}
	
	/**
	 * @param 
	 * 
	 * Returns a string formatted for a row in a CSV file
	 */
	public String getString()
	{
		String temp = new String();
		boolean ignore = false;
		
		temp = SEGMENT_ID;
		for (short x=0; x < row.length; x++)
		{
			for (short y=0; y < ignoredRows.length; y++)
				if (ignoredRows[y] == x)
					ignore = true;
			if ((x == (row.length - 1)) && !ignore)
				temp += "," + normalizeNull(row[x]) + "\r\n";
			else if ((x == (row.length - 1)))
				temp += "\r\n";
			else if (!ignore)
				temp += "," + normalizeNull(row[x]);
			ignore = false;
		}		
		return temp;
	}
	
	/**
	 * @param row
	 * 
	 * Removes null values from the output file
	 */
	public String normalizeNull(String row)
	{
		if (row == null)
			return "";
		else
			return row;
	}
}