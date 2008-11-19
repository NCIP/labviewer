/**
 * 
 */
package gov.nih.nci.ctom.ctlab.domain;

import java.util.Date;

/**
 * @author asharma
 *
 */
public class ProtocolStatus {
	
	private Long id;
	private String status_code;
	private Date  status_date;
	private String status_date_orig;
	private Long protocol_id;
	private int security_key;
	private String  source ;
	private Date  source_extract_date;
	private Date  ctom_insert_date; 
	private Date  ctom_update_date;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the status_code
	 */
	public String getStatus_code() {
		return status_code;
	}
	/**
	 * @param status_code the status_code to set
	 */
	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}
	/**
	 * @return the status_date
	 */
	public Date getStatus_date() {
		return status_date;
	}
	/**
	 * @param status_date the status_date to set
	 */
	public void setStatus_date(Date status_date) {
		this.status_date = status_date;
	}
	/**
	 * @return the status_date_orig
	 */
	public String getStatus_date_orig() {
		return status_date_orig;
	}
	/**
	 * @param status_date_orig the status_date_orig to set
	 */
	public void setStatus_date_orig(String status_date_orig) {
		this.status_date_orig = status_date_orig;
	}
	/**
	 * @return the protocol_id
	 */
	public Long getProtocol_id() {
		return protocol_id;
	}
	/**
	 * @param protocol_id the protocol_id to set
	 */
	public void setProtocol_id(Long protocol_id) {
		this.protocol_id = protocol_id;
	}
	/**
	 * @return the security_key
	 */
	public int getSecurity_key() {
		return security_key;
	}
	/**
	 * @param security_key the security_key to set
	 */
	public void setSecurity_key(int security_key) {
		this.security_key = security_key;
	}
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * @return the source_extract_date
	 */
	public Date getSource_extract_date() {
		return source_extract_date;
	}
	/**
	 * @param source_extract_date the source_extract_date to set
	 */
	public void setSource_extract_date(Date source_extract_date) {
		this.source_extract_date = source_extract_date;
	}
	/**
	 * @return the ctom_insert_date
	 */
	public Date getCtom_insert_date() {
		return ctom_insert_date;
	}
	/**
	 * @param ctom_insert_date the ctom_insert_date to set
	 */
	public void setCtom_insert_date(Date ctom_insert_date) {
		this.ctom_insert_date = ctom_insert_date;
	}
	/**
	 * @return the ctom_update_date
	 */
	public Date getCtom_update_date() {
		return ctom_update_date;
	}
	/**
	 * @param ctom_update_date the ctom_update_date to set
	 */
	public void setCtom_update_date(Date ctom_update_date) {
		this.ctom_update_date = ctom_update_date;
	} 

}
