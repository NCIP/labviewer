/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.client.preprocess;
/**
 * Copyright Notice.  Copyright 2008  Scenpro, Inc ("caBIG(TM) Participant").caXchange
 * was created with NCI funding and is part of the caBIG(TM) initiative. 
 * The software subject to this notice and license includes both human readable source code form and 
 * machine readable, binary, object code form (the "caBIG(TM) Software").
 * This caBIG(TM) Software License (the "License") is between caBIG(TM) Participant and You.  
 * "You (or "Your") shall mean a person or an entity, and all other entities that control, 
 * are controlled by, or are under common control with the entity.  "Control" for purposes of this 
 * definition means (i) the direct or indirect power to cause the direction or management of such entity, 
 * whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, 
 * or (iii) beneficial ownership of such entity.  
 * License.  Provided that You agree to the conditions described below, caBIG(TM) Participant grants 
 * You a non-exclusive, worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and 
 * royalty-free right and license in its rights in the caBIG(TM) Software, including any copyright or patent rights therein, to 
 * (i) use, install, disclose, access, operate, execute, reproduce, copy, modify, translate, market, publicly display, 
 * publicly perform, and prepare derivative works of the caBIG(TM) Software in any manner and for any purpose, and to have 
 * or permit others to do so; (ii) make, have made, use, practice, sell, and offer for sale, import, and/or otherwise 
 * dispose of caBIG(TM) Software (or portions thereof); (iii) distribute and have distributed to and by third parties the 
 * caBIG(TM) Software and any modifications and derivative works thereof; and (iv) sublicense the foregoing rights 
 * set out in (i), (ii) and (iii) to third parties, including the right to license such rights to further third parties.  
 * For sake of clarity, and not by way of limitation, caBIG(TM) Participant shall have no right of accounting or right of payment
 *  from You or Your sublicensees for the rights granted under this License.  This License is granted at no charge to You.  
 *  Your downloading, copying, modifying, displaying, distributing or use of caBIG(TM) Software constitutes acceptance of all 
 *  of the terms and conditions of this Agreement.  If you do not agree to such terms and conditions, you have no right to 
 *  download, copy, modify, display, distribute or use the caBIG(TM) Software.  
 * 1.	Your redistributions of the source code for the caBIG(TM) Software must retain the above copyright notice, 
 * 		this list of conditions and the disclaimer and limitation of liability of Article 6 below.  
 * 		Your redistributions in object code form must reproduce the above copyright notice, this list of conditions and 
 * 		the disclaimer of Article 6 in the documentation and/or other materials provided with the distribution, if any.
 * 2.	Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: 
 * 		"This product includes software developed by Scenpro, Inc."  
 * 		If You do not include such end-user documentation, You shall include this acknowledgment in the caBIG(TM) Software itself, 
 * 		wherever such third-party acknowledgments normally appear.
 * 3.	You may not use the names  "Scenpro, Inc", 
 * 		"The National Cancer Institute", "NCI", "Cancer Bioinformatics Grid" or "caBIG(TM)" to endorse or promote products 
 * 		derived from this caBIG(TM) Software.  This License does not authorize You to use any trademarks, service marks, trade names,
 * 		logos or product names of either caBIG(TM) Participant, NCI or caBIG(TM), except as required to comply with the terms of this 
 * 		License.
 * 4.	For sake of clarity, and not by way of limitation, You may incorporate this caBIG(TM) Software into Your proprietary 
 * 		programs and into any third party proprietary programs.  However, if You incorporate the caBIG(TM) Software into third party 
 * 		proprietary programs, You agree that You are solely responsible for obtaining any permission from such third parties 
 * 		required to incorporate the caBIG(TM) Software into such third party proprietary programs and for informing Your sublicensees, 
 * 		including without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
 * 		before incorporating the caBIG(TM) Software into such third party proprietary software programs.  In the event that You fail to 
 * 		obtain such permissions, You agree to indemnify caBIG(TM) Participant for any claims against caBIG(TM) Participant by such third 
 * 		parties, except to the extent prohibited by law, resulting from Your failure to obtain such permissions.
 * 5.	For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
 * 		to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses of 
 * 		modifications of the caBIG(TM) Software, or any derivative works of the caBIG(TM) Software as a whole, provided Your use, reproduction, 
 * 		and distribution of the Work otherwise complies with the conditions stated in this License.
 * 6.	THIS caBIG(TM) SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, 
 * 		THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  
 * 		IN NO EVENT SHALL THE Scenpro, Inc OR ITS AFFILIATES 
 * 		BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * 		PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
 * 		ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY 
 * 		OUT OF THE USE OF THIS caBIG(TM) SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * 
 */
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
