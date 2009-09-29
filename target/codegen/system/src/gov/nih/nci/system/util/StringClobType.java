package gov.nih.nci.system.util;
import java.io.Serializable;
import java.sql.*;
import org.hibernate.*;
import org.hibernate.usertype.*;

/**
 * @author Satish Patel
 */
/**
 * StringClobType serializes an instances of a Clob to a String. 
 * 
 */
public class StringClobType implements UserType{
	
	/**
	 * Restructs an object from the cachable representation
	 * @param cached 
	 * @owner
	 */
	public Object assemble(Serializable cached, Object owner){
		return cached;
		}
	/**
	 * Returns a deep copy of the persistent state
	 */
	 public Object deepCopy(Object value)
	    {
	        if (value == null) return null;
			return new String((String) value);
	    }
	 /**
	  * Transforms an object into it's cacheable representation
	  * @param value
	  */
	public Serializable disassemble (Object value){
		return (Serializable)value;
		}   
	
	/**
	 * Compares two instances of the class mapped by this type of persistance entity
	 * @param x
	 * @param y
	 */
	public boolean equals(Object x, Object y)
    {
        return (x == y)
            || (x != null
                && y != null
                && (x.equals(y)));
    }
	
	/**
	 * Returns a hashcode for the instance
	 * @param x
	 */
	   public int hashCode(Object x){
    	int code = 0;
    	if(x!= null){
    		code =  x.hashCode();	    	
    	}
    	return code;
    	}
    
	/**
	 * Checks if object is mutable
	 */
    public boolean isMutable()
    {
        return false;
    }
	
    /**
     * Retrieves an instance of the mapped class from a JDBC resultset
     * @param rs - resultset
     * @param names
     * @param owner
     * @return Object
     */
	 public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
     throws HibernateException, SQLException
	 {
     Clob clob = rs.getClob(names[0]);
     if(clob == null)
         return null;
     else     
         return clob.getSubString(1, (int) clob.length());
	 }

	 
	 /**
	  * Writes an instance of the mapped class to a prepared statement
	  * @param st - PreparedStatement
	  * @param value
	  * @param index
	  */
	   public void nullSafeSet(PreparedStatement st, Object value, int index)
       throws HibernateException, SQLException
	   {	             
		   
	        st.setClob(index, Hibernate.createClob((String) value));     
	   }
	   /**
	    * Replace the existing value with a new value
	    * @param original
	    * @param target
	    * @param owner
	    * @return
	    */
	   public Object replace(Object original, Object target, Object owner){
	   	target = original;
	   	return target;	   	
	   	}
	   
	   /**
	    * Class returned by nullSafeGet
	    */
	   public Class returnedClass()
	    {
	        return String.class;
	    }
	   
	   /**
	    * Returns the SQL tpe codes for the columns mapped by this type
	    */

	   public int[] sqlTypes()
	    {
	        return new int[] { Types.CLOB };
	    }

	   
	    

	   
	 
	   
	 

}
