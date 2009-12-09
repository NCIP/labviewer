package gov.nih.nci.caxchange.client.preprocess;
/**
 * Copyright Notice.  Copyright 2008  Scenpro, Inc (“caBIG™ Participant”).caXchange
 * was created with NCI funding and is part of the caBIG™ initiative. 
 * The software subject to this notice and license includes both human readable source code form and 
 * machine readable, binary, object code form (the “caBIG™ Software”).
 * This caBIG™ Software License (the “License”) is between caBIG™ Participant and You.  
 * “You (or “Your”) shall mean a person or an entity, and all other entities that control, 
 * are controlled by, or are under common control with the entity.  “Control” for purposes of this 
 * definition means (i) the direct or indirect power to cause the direction or management of such entity, 
 * whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, 
 * or (iii) beneficial ownership of such entity.  
 * License.  Provided that You agree to the conditions described below, caBIG™ Participant grants 
 * You a non-exclusive, worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and 
 * royalty-free right and license in its rights in the caBIG™ Software, including any copyright or patent rights therein, to 
 * (i) use, install, disclose, access, operate, execute, reproduce, copy, modify, translate, market, publicly display, 
 * publicly perform, and prepare derivative works of the caBIG™ Software in any manner and for any purpose, and to have 
 * or permit others to do so; (ii) make, have made, use, practice, sell, and offer for sale, import, and/or otherwise 
 * dispose of caBIG™ Software (or portions thereof); (iii) distribute and have distributed to and by third parties the 
 * caBIG™ Software and any modifications and derivative works thereof; and (iv) sublicense the foregoing rights 
 * set out in (i), (ii) and (iii) to third parties, including the right to license such rights to further third parties.  
 * For sake of clarity, and not by way of limitation, caBIG™ Participant shall have no right of accounting or right of payment
 *  from You or Your sublicensees for the rights granted under this License.  This License is granted at no charge to You.  
 *  Your downloading, copying, modifying, displaying, distributing or use of caBIG™ Software constitutes acceptance of all 
 *  of the terms and conditions of this Agreement.  If you do not agree to such terms and conditions, you have no right to 
 *  download, copy, modify, display, distribute or use the caBIG™ Software.  
 * 1.	Your redistributions of the source code for the caBIG™ Software must retain the above copyright notice, 
 * 		this list of conditions and the disclaimer and limitation of liability of Article 6 below.  
 * 		Your redistributions in object code form must reproduce the above copyright notice, this list of conditions and 
 * 		the disclaimer of Article 6 in the documentation and/or other materials provided with the distribution, if any.
 * 2.	Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: 
 * 		“This product includes software developed by Scenpro, Inc.”  
 * 		If You do not include such end-user documentation, You shall include this acknowledgment in the caBIG™ Software itself, 
 * 		wherever such third-party acknowledgments normally appear.
 * 3.	You may not use the names  “Scenpro, Inc”, 
 * 		“The National Cancer Institute”, “NCI”, “Cancer Bioinformatics Grid” or “caBIG™” to endorse or promote products 
 * 		derived from this caBIG™ Software.  This License does not authorize You to use any trademarks, service marks, trade names,
 * 		logos or product names of either caBIG™ Participant, NCI or caBIG™, except as required to comply with the terms of this 
 * 		License.
 * 4.	For sake of clarity, and not by way of limitation, You may incorporate this caBIG™ Software into Your proprietary 
 * 		programs and into any third party proprietary programs.  However, if You incorporate the caBIG™ Software into third party 
 * 		proprietary programs, You agree that You are solely responsible for obtaining any permission from such third parties 
 * 		required to incorporate the caBIG™ Software into such third party proprietary programs and for informing Your sublicensees, 
 * 		including without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
 * 		before incorporating the caBIG™ Software into such third party proprietary software programs.  In the event that You fail to 
 * 		obtain such permissions, You agree to indemnify caBIG™ Participant for any claims against caBIG™ Participant by such third 
 * 		parties, except to the extent prohibited by law, resulting from Your failure to obtain such permissions.
 * 5.	For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
 * 		to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses of 
 * 		modifications of the caBIG™ Software, or any derivative works of the caBIG™ Software as a whole, provided Your use, reproduction, 
 * 		and distribution of the Work otherwise complies with the conditions stated in this License.
 * 6.	THIS caBIG™ SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, 
 * 		THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  
 * 		IN NO EVENT SHALL THE Scenpro, Inc OR ITS AFFILIATES 
 * 		BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * 		PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
 * 		ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY 
 * 		OUT OF THE USE OF THIS caBIG™ SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * 
 */
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
					logger.debug(String.valueOf(counter) + " row(s) writen");
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