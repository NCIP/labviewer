package gov.nih.nci.caxchange.ctom.viewer.viewobjects;

public enum DateRangeFilter {
	
	ALL_DATES_TIMES("All dates/times"),
	LAST_12_HOURS("Last 12 hours"),
	LAST_24_HOURS("Last 24 hours"),
	LAST_7_DAYS("Last 7 days"),
	LAST_30_DAYS("Last 30 days"),
	WEEK_TO_DATE("Week to date"),
	MONTH_TO_DATE("Month to date"),
	YEAR_TO_DATE ("Year to date"),
	YESTERDAY("Yesterday"),
	PREVIOUS_WEEK("Previous week"),
	PREVIOUS_MONTH("Previous month"),
	PREVIOUS_YEAR("Previous year"),
	CUSTOM_DATES_TIMES("Custom dates/times");
	
	 private String code;
    /**
     * 
     * @param code
     */
    private DateRangeFilter(String code) {
        this.code = code;
       
    }
    /**
     * @return code code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * @return String name
     */
    public String getName() {
        return name();
    }

     /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
    	DateRangeFilter[] l = DateRangeFilter.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }
}
