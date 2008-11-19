/**
 
* Copyright Notice.  Copyright 2008  Scenpro, Inc (“caBIG™ Participant”). caXchange was created with NCI funding and is part of the caBIG™ initiative. The software subject to this notice and license includes both human readable source code form and machine readable, binary, object code form (the “caBIG™ Software”).
* This caBIG™ Software License (the “License”) is between caBIG™ Participant and You.  “You (or “Your”) shall mean a person or an entity, and all other entities that control, are controlled by, or are under common control with the entity.  “Control” for purposes of this definition means (i) the direct or indirect power to cause the direction or management of such entity, whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) beneficial ownership of such entity.  
* License.  Provided that You agree to the conditions described below, caBIG™ Participant grants You a non-exclusive, worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights in the caBIG™ Software, including any copyright or patent rights therein, to (i) use, install, disclose, access, operate, execute, reproduce, copy, modify, translate, market, publicly display, publicly perform, and prepare derivative works of the caBIG™ Software in any manner and for any purpose, and to have or permit others to do so; (ii) make, have made, use, practice, sell, and offer for sale, import, and/or otherwise dispose of caBIG™ Software (or portions thereof); (iii) distribute and have distributed to and by third parties the caBIG™ Software and any modifications and derivative works thereof; and (iv) sublicense the foregoing rights set out in (i), (ii) and (iii) to third parties, including the right to license such rights to further third parties.  For sake of clarity, and not by way of limitation, caBIG™ Participant shall have no right of accounting or right of payment from You or Your sublicensees for the rights granted under this License.  This License is granted at no charge to You.  Your downloading, copying, modifying, displaying, distributing or use of caBIG™ Software constitutes acceptance of all of the terms and conditions of this Agreement.  If you do not agree to such terms and conditions, you have no right to download, copy, modify, display, distribute or use the caBIG™ Software.  
* 1.	Your redistributions of the source code for the caBIG™ Software must retain the above copyright notice, this list of conditions and the disclaimer and limitation of liability of Article 6 below.  Your redistributions in object code form must reproduce the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials provided with the distribution, if any.
* 2.	Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: “This product includes software developed by Scenpro, Inc [insert name of organization funded to participate in caBIG™].”  If You do not include such end-user documentation, You shall include this acknowledgment in the caBIG™ Software itself, wherever such third-party acknowledgments normally appear.
* 3.	You may not use the names  “Scenpro, Inc ”, “The National Cancer Institute”, “NCI”, “Cancer Bioinformatics Grid” or “caBIG™” to endorse or promote products derived from this caBIG™ Software.  This License does not authorize You to use any trademarks, service marks, trade names, logos or product names of either caBIG™ Participant, NCI or caBIG™, except as required to comply with the terms of this License.
* 4.	For sake of clarity, and not by way of limitation, You may incorporate this caBIG™ Software into Your proprietary programs and into any third party proprietary programs.  However, if You incorporate the caBIG™ Software into third party proprietary programs, You agree that You are solely responsible for obtaining any permission from such third parties required to incorporate the caBIG™ Software into such third party proprietary programs and for informing Your sublicensees, including without limitation Your end-users, of their obligation to secure any required permissions from such third parties before incorporating the caBIG™ Software into such third party proprietary software programs.  In the event that You fail to obtain such permissions, You agree to indemnify caBIG™ Participant for any claims against caBIG™ Participant by such third parties, except to the extent prohibited by law, resulting from Your failure to obtain such permissions.
* 5.	For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses of modifications of the caBIG™ Software, or any derivative works of the caBIG™ Software as a whole, provided Your use, reproduction, and distribution of the Work otherwise complies with the conditions stated in this License.
* 6.	THIS caBIG™ SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN NO EVENT SHALL THE Scenpro, Inc [insert name of name of organization funded to participate in caBIG™] OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG™ SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* 
 */
package gov.nih.nci.caxchange.ctom.viewer.util;

/**
 * @author asharma
 *
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

public class DateUtility {
	private static final Logger logDB = Logger.getLogger(DateUtility.class);
    public boolean buildDateRangeQuery(String dateRange, LabActivityResult lar,LabActivitiesSearchForm lForm) {
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

        if (dateRange.equals(DateRangeFilter.LAST_12_HOURS.getCode())) {
            // offset right now by 12 hours behind
            offsetTime.add(Calendar.HOUR_OF_DAY, -12);

        } else if (dateRange.equals(DateRangeFilter.LAST_24_HOURS.getCode())) {
            // offset right now by 24 hours behind
            offsetTime.add(Calendar.HOUR_OF_DAY, -24);

        } else if (dateRange.equals(DateRangeFilter.LAST_7_DAYS.getCode())) {
            // offset right now by 24 hours behind
            offsetTime.add(Calendar.DAY_OF_YEAR, -7);

        } else if (dateRange.equals(DateRangeFilter.LAST_30_DAYS.getCode())) {
            offsetTime.add(Calendar.DAY_OF_YEAR, -30);

        } else if (dateRange.equals(DateRangeFilter.MONTH_TO_DATE.getCode())) {
            int month = labDate.get(Calendar.MONTH);
            int year = labDate.get(Calendar.YEAR);
            if (month == 0 || month == 2 || month == 4 || month == 6 || month == 7 || month == 9 || month == 11) {
                offsetTime.add(Calendar.DAY_OF_MONTH, -31);
            } else if ((month == 1) && (year % 4 == 0)) {// Leap year
                offsetTime.add(Calendar.DAY_OF_MONTH, -29);
            } else if ((month == 1) && (year % 4 != 0)) {// Non leap
                offsetTime.add(Calendar.DAY_OF_MONTH, -28);
            } else {// even months
                offsetTime.add(Calendar.DAY_OF_MONTH, -30);

            }
        } else if (dateRange.equals(DateRangeFilter.PREVIOUS_MONTH.getCode())) {
            offsetTime.add(Calendar.MONTH, -1);

        } else if (dateRange.equals(DateRangeFilter.WEEK_TO_DATE.getCode())) {
            int today = labDate.get(Calendar.DAY_OF_WEEK);
            offsetTime.add(Calendar.DAY_OF_YEAR, -1 * today);

        } else if (dateRange.equals(DateRangeFilter.PREVIOUS_WEEK.getCode())) {
            offsetTime.add(Calendar.WEEK_OF_YEAR, -1);

        } else if (dateRange.equals(DateRangeFilter.PREVIOUS_YEAR.getCode())) {
            offsetTime.add(Calendar.YEAR, -1);

        } else if (dateRange.equals(DateRangeFilter.YESTERDAY.getCode())) {
            offsetTime.add(Calendar.DAY_OF_YEAR, -1);

        } else if (dateRange.equals(DateRangeFilter.YEAR_TO_DATE.getCode())) {
            int today = labDate.get(Calendar.DAY_OF_YEAR);
            offsetTime.add(Calendar.DAY_OF_YEAR, (-1) * today);
        } else if (dateRange.equals(DateRangeFilter.CUSTOM_DATES_TIMES.getCode())) {
        	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        	try {
				Date beginDate = formatter.parse(lForm.getBeginDate());
				Date endDate = formatter.parse(lForm.getEndDate());
				offsetBegin.setTime(beginDate);
				offsetEnd.setTime(endDate);
				
			} catch (ParseException e) {
				logDB.error("Date Parse Exception"+ e.getLocalizedMessage());
			}
        }
        logDB.info("Offset:" + offsetTime.getTimeInMillis() + " Lab Date=" + labDate.getTimeInMillis()
                        + " Rightnow" + rightNow.getTimeInMillis());
        if (!dateRange.equals(DateRangeFilter.CUSTOM_DATES_TIMES.getCode()))
        {
        	if (labDate.after(offsetTime) && labDate.before(rightNow)) {
        		returnCondition = true;
        	}
        }else
        {
            if (labDate.after(offsetBegin) && labDate.before(offsetEnd)) {
            	returnCondition = true;
            }
        }      
        return returnCondition;
    }

    public static void main(String[] args) {
        DateUtility date = new DateUtility();
        LabActivityResult lar = new LabActivityResult();
        LabActivitiesSearchForm lForm = new LabActivitiesSearchForm();
        Date d = new Date(System.currentTimeMillis() - 14400000);
        lar.setActualDate(d);
        boolean ret = date.buildDateRangeQuery(DateRangeFilter.LAST_12_HOURS.getCode(), lar,lForm);
        System.out.println("LAST_12_HOURS Return:" + ret);

        d = new Date(System.currentTimeMillis() - 50400000);
        lar.setActualDate(d);
        ret = date.buildDateRangeQuery(DateRangeFilter.LAST_24_HOURS.getCode(), lar,lForm);
        System.out.println("LAST_24_HOURS Return:" + ret);

        // 24 days ago
        d = new Date(System.currentTimeMillis() - 2073600000);
        lar.setActualDate(d);
        ret = date.buildDateRangeQuery(DateRangeFilter.LAST_30_DAYS.getCode(), lar,lForm);
        System.out.println("Last 30 days Return:" + ret);

        // Week to date
        // 24 days ago
        d = new Date(System.currentTimeMillis() - 7200000);
        lar.setActualDate(d);
        ret = date.buildDateRangeQuery(DateRangeFilter.WEEK_TO_DATE.getCode(), lar,lForm);
        System.out.println("WEEK_TO_DATE:" + ret);
    }
}  
