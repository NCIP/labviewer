/**
 * 
 */
package gov.nih.nci.caxchange.ctom.viewer.logoninfo;

import java.util.HashMap;

/**
 * @author liany
 *
 */
public class LogonInfoManager implements Runnable {
	
	private static HashMap loginInfo = null;
	
	static {
		// self clean up
	}
	public void run() {
	    for(int i=0; i<100; i++) {
	        //System.out.print(c);
	        
	        try{ 
	           Thread.sleep((int)(Math.random() * 10));
	        } catch( InterruptedException e ) {
	            System.out.println("Interrupted Exception caught");
	        }
	    }

	}
   public static boolean isUserLockout (String userId) {
	   return true;
   }
   public static void userLogonFailed (String userId){
	   
   }
   private int getAllowedLogonAttempts(){
	   return 0;
   }
   private double getLockoutTime(){
	   return 0;
   }
   private double getAllowedLogonTime(){
	   return 0;
   }
}
	
