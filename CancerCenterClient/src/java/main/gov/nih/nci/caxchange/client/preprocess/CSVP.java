package gov.nih.nci.caxchange.client.preprocess;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Properties;
import javax.swing.*;

import org.apache.log4j.*;

/**
 * @author Jesse McKean
 *
 * This program will process a CSV file and convert it into a valid
 * caAdapter CSV file.
 */
@SuppressWarnings("serial")

public class CSVP
{
	//log4j logger for non GUI use.
	private static Logger logger = Logger.getLogger(CSVP.class.getName());
		
	private static short[] columnOrder;
	private static short[] splitFields;
	private static boolean[] timeFields;
	private static String[][] defaultValues;
	
	private static Properties properties;
	private static JTextField fromFile = new JTextField(50);
	private static JTextField toFile = new JTextField(50);
	private JButton browseTo = new JButton("Browse");
	  
	/**
	 * @param row
	 * 
	 * Custom spliter for CSV that the normal string split won't do
	 * This handles the ',' inside a field
	 */
	private static String[] CSVSplitter(String row)
	{
		String[] split = new String[columnOrder.length];
		split[0] = "";		
		
		int stringCounter = 0;
		int charCounter = 0;
		boolean inQuotes = false;
		char temp;
		
		while(charCounter < row.length())
		{
			temp = row.charAt(charCounter);
			
			if (temp == ',' && !inQuotes)
			{
				stringCounter++;
				split[stringCounter] = "";
			}
			else if (temp == '"')
			{
				inQuotes = !inQuotes;
				split[stringCounter] += temp;
			}
			else
			{
				split[stringCounter] += temp;
			}
			charCounter++;
		}
		return split;
	}
	
	/**
	 * @param path
	 * 
	 * Reads in the properties file
	 * @throws Exception
	 */
	private static void readProperties(String path) throws Exception
	{
	    File propertiesFile = new File(path);
	    properties = new Properties();
	    try
	    {
	    	properties.load(new FileInputStream(propertiesFile));
	    }
		catch (FileNotFoundException e) 
		{
			logger.fatal("Cannot find the preprocessor properties file");
			throw new Exception(e.getMessage());
		}
		catch (IOException ex)
		{
			
			logger.fatal(ex.getMessage());
			throw new Exception(ex.getMessage());
		}	
	}
	
	/**
	 * @param inputFile, outputFile, propertiesFile
	 * Construtor for use without the GUI
	 * @throws Exception
	 */
	
	public CSVP(String inputFile, String outputFile, String propertiesFile) throws Exception
	{
		BasicConfigurator.configure();
		//Configuration from a log4j properties file
		//PropertyConfigurator.configure("log4j.properties");
		toFile.setText(outputFile);
		fromFile.setText(inputFile);
		readProperties(propertiesFile);
		ProcessFile();
	}
	
	/**
	 * @param 
	 * 
	 * This functions handles all the file IO for the program
	 * @throws Exception
	 */
	private static void ProcessFile() throws Exception
	{
		String text = new String();
		DataRow data;
		int counter = 0;

		//This reads in one line from one file and then outputs the modified line to a new file
		try 
		{
			BufferedReader buf = new BufferedReader(new FileReader(new File(fromFile.getText())));
			BufferedWriter out = new BufferedWriter(new FileWriter(new File(toFile.getText())));
			
			if(columnLayout((buf.readLine()).split(",")))
			{
				while ((text = buf.readLine()) != null) 
				{
					//Uses a custom data type to process each line from the file
					data = new DataRow(CSVSplitter(text), defaultValues, timeFields, splitFields, columnOrder, Short.parseShort(properties.getProperty("column.count")));
					out.write(data.getString());
					counter++;
					System.out.println(String.valueOf(counter) + " row(s) writen");
				}
				logger.info("File finished processing successfully");
			}
			else {
				buf.close();
				out.close();
				throw new Exception("Problem with the column layout");
			}
				
	
            buf.close();
            out.close();
		} 
		catch (FileNotFoundException e) 
		{
		    logger.fatal(e.getMessage());
			throw new Exception(e.getMessage());
		}
		catch (IOException ex)
		{
			logger.fatal(ex.getMessage());
			throw new Exception(ex.getMessage());
		}		
	}

	/**
	 * @param validation
	 * 
	 * This functions verifies that all required columns are present
	 * in the input file
	 */
	private static boolean defaultColumnsPresent(boolean[] validation)
	{
		boolean check = false;
		for (int x=0; x < Integer.parseInt(properties.getProperty("required.count")); x++)
		{
			if (validation[x] != true)
			{
				logger.error("Missing required column " + properties.getProperty("required.column" + (x+1)));
				return false;
			}
		}
		if (Integer.parseInt(properties.getProperty("column.pair.count")) == 0)
			check = true;
		else
			for (int x=Integer.parseInt(properties.getProperty("required.count")); x < validation.length; x+=2)
				if (!(validation[x] != true && validation[x+1] != true))
					check = true;
		
		if (!check)
			logger.error("Missing a required Data Time pair");
		
		return check;
	}
	
	/**
	 * @param combinedFields
	 * 
	 * This functions verifies that the combined fields (either for
	 * text/numeric or low/high) and the separate value fields are not
	 * both present in the input file
	 */
	private static boolean checkSplitFields(boolean[] combinedField)
	{
		for (int p=0; p < splitFields.length; p+=3)
		{
			if (combinedField[p] && (combinedField[p+1] || combinedField[p+2]))
			{
				logger.error("Both combined and separate value fields present");
				return false;				
			}
			if (!combinedField[p] && !(combinedField[p+1] && combinedField[p+2]))
			{
				logger.error("One or both of the separate value fields are missing");
				return false;				
			}
		}
		return true;
	}
	
	/**
	 * @param
	 * 
	 * This function returns an array of all the valid column headings.  This array
	 * is built from the properties file that was read in.
	 */
	private static String[] getValidColumns()
	{
		defaultValues = new String[Integer.parseInt(properties.getProperty("default.values"))][3];
		splitFields = new short[Integer.parseInt(properties.getProperty("split.column.count"))];
		short counter = 0;
		
		String[] columnList = new String[Integer.parseInt(properties.getProperty("column.count"))];
		for (short x=0; x < columnList.length; x++)
		{
			columnList[x] = (properties.getProperty("column.heading" + (x+1))).trim();
			
			if ((properties.getProperty(columnList[x])) != null)
			{
				defaultValues[counter][0] = columnList[x];
				defaultValues[counter][1] = (properties.getProperty(columnList[x])).trim();
				defaultValues[counter][2] = String.valueOf(x);
				counter++;
			}
			
			for (short y=0; y < splitFields.length; y++)
				if ((columnList[x].toUpperCase()).equals(((properties.getProperty("split.column" + (y+1))).toUpperCase()).trim()))
					splitFields[y] = x;						
		}		
		return columnList;	
	}
	
	/**
	 * @param
	 * 
	 * This function returns an array of all the required column headings.  This array
	 * is built from the properties file that was read in.
	 */
	private static String[] getRequiredColumns()
	{
		String[] columnList = new String[Integer.parseInt(properties.getProperty("required.count")) 
		                                 + Integer.parseInt(properties.getProperty("column.pair.count"))];

		for (int x=0; x < Integer.parseInt(properties.getProperty("required.count")); x++)
			columnList[x] = (properties.getProperty("required.column" + (x+1))).trim();
		
		for (int x=Integer.parseInt(properties.getProperty("required.count")); x < columnList.length; x++)
			columnList[x] = (properties.getProperty("column.pair" + (x - Integer.parseInt(properties.getProperty("required.count")) + 1))).trim();
		
		return columnList;	
	}
	
	/**
	 * @param headings
	 * 
	 * This function is where most the magic happens.  It builds an array that maps the columns in the
	 * input file to the required columns that will go in the output file.  It also located the time fields, 
	 * marks default column headings and identifies the combined data fields.  The function will return false
	 * if a invalid column name is found, if a required field is missing, or if combined and separate data fields
	 * are both present.
	 */
	private static boolean columnLayout(String[] headings)
	{
		String[] validColumns = getValidColumns();
		String[] requiredColumns = getRequiredColumns();
		boolean[] requiredFields = new boolean[Integer.parseInt(properties.getProperty("required.count")) + Integer.parseInt(properties.getProperty("column.pair.count"))];
		boolean valid = false;
		boolean[] combinedField = new boolean[splitFields.length];
		
		columnOrder = new short[headings.length];
		timeFields = new boolean[headings.length];

		for (short x=0; x < headings.length; x++)
		{
			for (short y=0; y < validColumns.length; y++)
			{
				if ((headings[x].toUpperCase()).equals((validColumns[y].toUpperCase())))					
				{
					columnOrder[x] = y;
					if ((headings[x].toUpperCase()).contains("TIME"))
						timeFields[x] = true;
					valid = true;

					for (short q=0; q < defaultValues.length; q++)
					{
						if ((headings[x].toUpperCase()).equals((defaultValues[q][0]).toUpperCase()))
							defaultValues[q][2] = null;
					}

					for (short r=0; r < requiredColumns.length; r++)
					{
						if ((headings[x].toUpperCase()).equals((requiredColumns[r]).toUpperCase()))
							requiredFields[r] = true;
					}	
					
					for (int p=0; p < splitFields.length; p++)
					{
						if (y == splitFields[p])
							combinedField[p] = true;
					}
					break;
				}
			}
			if (valid == false)
			{
				logger.error("Bad column name: " + headings[x]);
				return false;
			}
			valid = false;				
		}
		return (defaultColumnsPresent(requiredFields) && checkSplitFields(combinedField));
	}
}