/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.util;

/**
 * @author asharma
 */
import gov.nih.nci.caxchange.ctom.viewer.DAO.LabSearchDAO;
import gov.nih.nci.caxchange.ctom.viewer.forms.LabActivitiesSearchForm;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.DateRangeFilter;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.LabActivityResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateUtility
{
	private static final Logger logDB = Logger.getLogger(DateUtility.class);

	/**
	 * @param dateRange
	 * @param lar
	 * @param lForm
	 * @return
	 */
	public boolean buildDateRangeQuery(String dateRange, LabActivityResult lar,
			LabActivitiesSearchForm lForm)
	{
		boolean returnCondition = false;
		// Date curDate = new java.util.Date();
		Calendar rightNow = Calendar.getInstance();
		// Further computed based on the condition.
		Calendar offsetTime = Calendar.getInstance();
		Calendar offsetBegin = Calendar.getInstance();
		Calendar offsetEnd = Calendar.getInstance();

		Calendar labDate = Calendar.getInstance();
		labDate.setTime(lar.getActualDate());

		// is the incoming date within the last 12 hours

		if (dateRange.equals(DateRangeFilter.LAST_12_HOURS.getCode()))
		{
			// offset right now by 12 hours behind
			offsetTime.add(Calendar.HOUR_OF_DAY, -12);

		}
		else if (dateRange.equals(DateRangeFilter.LAST_24_HOURS.getCode()))
		{
			// offset right now by 24 hours behind
			offsetTime.add(Calendar.HOUR_OF_DAY, -24);

		}
		else if (dateRange.equals(DateRangeFilter.LAST_7_DAYS.getCode()))
		{
			// offset right now by 24 hours behind
			offsetTime.add(Calendar.DAY_OF_YEAR, -7);

		}
		else if (dateRange.equals(DateRangeFilter.LAST_30_DAYS.getCode()))
		{
			offsetTime.add(Calendar.DAY_OF_YEAR, -30);

		}
		else if (dateRange.equals(DateRangeFilter.MONTH_TO_DATE.getCode()))
		{
			int month = labDate.get(Calendar.MONTH);
			int year = labDate.get(Calendar.YEAR);
			if (month == 0 || month == 2 || month == 4 || month == 6
					|| month == 7 || month == 9 || month == 11)
			{
				offsetTime.add(Calendar.DAY_OF_MONTH, -31);
			}
			else if ((month == 1) && (year % 4 == 0))
			{// Leap year
				offsetTime.add(Calendar.DAY_OF_MONTH, -29);
			}
			else if ((month == 1) && (year % 4 != 0))
			{// Non leap
				offsetTime.add(Calendar.DAY_OF_MONTH, -28);
			}
			else
			{// even months
				offsetTime.add(Calendar.DAY_OF_MONTH, -30);

			}
		}
		else if (dateRange.equals(DateRangeFilter.PREVIOUS_MONTH.getCode()))
		{
			offsetTime.add(Calendar.MONTH, -1);

		}
		else if (dateRange.equals(DateRangeFilter.WEEK_TO_DATE.getCode()))
		{
			int today = labDate.get(Calendar.DAY_OF_WEEK);
			offsetTime.add(Calendar.DAY_OF_YEAR, -1 * today);

		}
		else if (dateRange.equals(DateRangeFilter.PREVIOUS_WEEK.getCode()))
		{
			offsetTime.add(Calendar.WEEK_OF_YEAR, -1);

		}
		else if (dateRange.equals(DateRangeFilter.PREVIOUS_YEAR.getCode()))
		{
			offsetTime.add(Calendar.YEAR, -1);

		}
		else if (dateRange.equals(DateRangeFilter.YESTERDAY.getCode()))
		{
			offsetTime.add(Calendar.DAY_OF_YEAR, -1);

		}
		else if (dateRange.equals(DateRangeFilter.YEAR_TO_DATE.getCode()))
		{
			int today = labDate.get(Calendar.DAY_OF_YEAR);
			offsetTime.add(Calendar.DAY_OF_YEAR, (-1) * today);
		}
		else if (dateRange.equals(DateRangeFilter.CUSTOM_DATES_TIMES.getCode()))
		{
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			try
			{
				Date beginDate = formatter.parse(lForm.getBeginDate());
				Date endDate = formatter.parse(lForm.getEndDate());
				offsetBegin.setTime(beginDate);
				offsetEnd.setTime(endDate);

			}
			catch (ParseException e)
			{
				logDB.error("Date Parse Exception" + e.getLocalizedMessage());
			}
		}
		logDB.info("Offset:" + offsetTime.getTimeInMillis() + " Lab Date="
				+ labDate.getTimeInMillis() + " Rightnow"
				+ rightNow.getTimeInMillis());
		if (!dateRange.equals(DateRangeFilter.CUSTOM_DATES_TIMES.getCode()))
		{
			if (labDate.after(offsetTime) && labDate.before(rightNow))
			{
				returnCondition = true;
			}
		}
		else
		{
			if (labDate.after(offsetBegin) && labDate.before(offsetEnd))
			{
				returnCondition = true;
			}
		}
		return returnCondition;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		DateUtility date = new DateUtility();
		LabActivityResult lar = new LabActivityResult();
		LabActivitiesSearchForm lForm = new LabActivitiesSearchForm();
		Date d = new Date(System.currentTimeMillis() - 14400000);
		lar.setActualDate(d);
		boolean ret =
				date.buildDateRangeQuery(DateRangeFilter.LAST_12_HOURS
						.getCode(), lar, lForm);
		System.out.println("LAST_12_HOURS Return:" + ret);

		d = new Date(System.currentTimeMillis() - 50400000);
		lar.setActualDate(d);
		ret =
				date.buildDateRangeQuery(DateRangeFilter.LAST_24_HOURS
						.getCode(), lar, lForm);
		System.out.println("LAST_24_HOURS Return:" + ret);

		// 24 days ago
		d = new Date(System.currentTimeMillis() - 2073600000);
		lar.setActualDate(d);
		ret =
				date.buildDateRangeQuery(
						DateRangeFilter.LAST_30_DAYS.getCode(), lar, lForm);
		System.out.println("Last 30 days Return:" + ret);

		// Week to date
		// 24 days ago
		d = new Date(System.currentTimeMillis() - 7200000);
		lar.setActualDate(d);
		ret =
				date.buildDateRangeQuery(
						DateRangeFilter.WEEK_TO_DATE.getCode(), lar, lForm);
		System.out.println("WEEK_TO_DATE:" + ret);
	}
}
