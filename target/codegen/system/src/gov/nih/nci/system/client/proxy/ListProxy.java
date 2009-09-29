package gov.nih.nci.system.client.proxy;

import gov.nih.nci.system.applicationservice.ApplicationService;
//import gov.nih.nci.system.applicationservice.ApplicationServiceProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * @author SDK Team
 */
public class ListProxy extends ArrayList implements Set {
	private static final long serialVersionUID = 1L;
	
	// the real size of actual list if competely materialized, the record count
	// from hibernate query
	private int realSize_ = -1;

	// a switch that allows shortcutting if we have all the records
	private boolean hasAllRecords_;

	// the chunk of the list we are working with
	private ListChunk listChunk_ = new ListChunk();

	// some stuff to tell us about the orginal query
	private Object originalCrit_;

	private int originalStart_;

	private int maxRecordsPerQuery_;

	private String targetClassName;

	private static Logger log = Logger.getLogger(ListProxy.class.getName());
	
	private transient ApplicationService appService;
	
	// ==================================================================================
	// make a inner class, there is no telling what we need to do
	private class ListChunk extends ArrayList {
		private static final long serialVersionUID = -1;
	}

	// end of inner class ListChunk
	// ==============================================================

	/**
	 * Returns the number of elements in this list.
	 * 
	 * @return size of the List
	 */
	public int size() {
		if (realSize_ == -1) {
			if (hasAllRecords_) {
				realSize_ = listChunk_.size();
			} else {
				int rowCount = listChunk_.size();
				//System.out.println("rowCount: " + rowCount);
// TODO ::				ApplicationService appService = ApplicationServiceProvider.getApplicationService();
				try {
					// Data larger than the max query size.  Must determine the 
					// actual size
					//System.out.println("rowCount: " + rowCount + "; " + "maxRecordsPerQuery_: " + maxRecordsPerQuery_);
					if (rowCount == maxRecordsPerQuery_)
						rowCount = appService.getQueryRowCount(originalCrit_,
								targetClassName);
				} catch (Exception ex) {
					log.error("Exception: ", ex);
				}
				realSize_ = rowCount;
				if (rowCount < maxRecordsPerQuery_)
					hasAllRecords_ = true;
				else
					hasAllRecords_ = false;
			}
		}
		return realSize_;
	}

	/**
	 * @see java.util.#isEmpty()
	 */
	public boolean isEmpty() {
		return listChunk_.isEmpty();
	}

	/**
	 * @see java.util.#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		if (hasAllRecords_) {
			return listChunk_.contains(o);
		} else {
			// step through the entire set of list chunks from
			// the appservice looking for the result.
			boolean computedResult = false;
			computedResult = listChunk_.contains(o);
			if (computedResult)
				return computedResult;
			else {
				int firstResult = 0;
//	TODO ::			ApplicationService appService = ApplicationServiceProvider.getApplicationService();

				for (;;) {
					List ls = new ArrayList();
					try {
						ls = appService.query(originalCrit_, firstResult, targetClassName);
						if (ls.size() <= 0) // there are no more records in
											// database
							break;
						computedResult = ls.contains(o);
						if (computedResult)
							break;
						else
							firstResult += maxRecordsPerQuery_;
					} catch (Exception ex) {
						log.error("Exception: " + ex.getMessage());
					}
				}
			}
			return computedResult;
		}
	}

	/**
	 * @see java.util.#toArray()
	 * 
	 */
	public Object[] toArray() {
		if (hasAllRecords_) {
			return listChunk_.toArray();
		} else {
			ArrayList wholeList = new ArrayList();
			try {
				throw new Exception(
						"Object[] toArray(): This feature is not yet implemented in this version.");
			} catch (Exception ex) {
				log.error("Exception: " + ex.getMessage());
			}
			return wholeList.toArray();
		}

	}

	/**
	 * @see java.util.#toArray(java.lang.Object[])
	 */
	public Object[] toArray(Object a[]) {
		if (hasAllRecords_) {
			return listChunk_.toArray();
		} else {
			ArrayList wholeList = new ArrayList();
			try {
				throw new Exception(
						"Object[] toArray(Object a[]): This feature is not yet implemented in this version.");

			} catch (Exception ex) {
				log.error("Exception: " + ex.getMessage());
			}
			return wholeList.toArray();
		}
	}

	/**
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(Object o) {
		if (hasAllRecords_) {
			boolean result=listChunk_.add(o);
			calculateRealSize();
			return result;
		} else {
			try {
				throw new Exception(
						"boolean add(Object o): This feature is not yet implemented in this version.");
			} catch (Exception ex) {
				log.error("Exception: " + ex.getMessage());
			}
			return false;
		}
	}

	/**
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object obj) {
		if (hasAllRecords_) {
			boolean result = listChunk_.remove(obj);
			calculateRealSize();
			return result;
		} else {
			try {
				throw new Exception(
						"boolean remove(Object obj): This feature is not yet implemented in this version.");
			} catch (Exception ex) {
				log.error("Exception: " + ex.getMessage());
			}
			return false;
		}

	}

	/**
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection c) {
		if (hasAllRecords_) {
			return listChunk_.containsAll(c);
		} else {
			// find if the entire collection is there via appservice and then
			boolean computedResult = false;
			int recordsCount = 0;
			computedResult = listChunk_.containsAll(c);

			if (computedResult)
				return computedResult;
			else {
				int collectionSize = c.size();
				if (collectionSize > maxRecordsPerQuery_)
					recordsCount = collectionSize;
				else
					recordsCount = maxRecordsPerQuery_;
				int firstResult = 0;
//	TODO ::			ApplicationService appService = ApplicationServiceProvider.getApplicationService();
				for (;;) {
					List ls = new ArrayList();
					try {
						ls = appService.query(originalCrit_, firstResult,targetClassName);
						if (ls.size() <= 0) // there are no more records in database
							break;
						computedResult = ls.contains(c);
						if (computedResult)
							break;
						else
							firstResult += recordsCount;
					} catch (Exception ex) {
						log.error("Exception: " + ex.getMessage());
					}
				}
			}
			return computedResult;
		}
	}

	/**
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection c) {
		if (hasAllRecords_) {
			boolean result = listChunk_.addAll(c);
			calculateRealSize();
			return result;
		} else {
			if (listChunk_.size() == 0){
				boolean result = listChunk_.addAll(c);
				calculateRealSize();
			}else {
				try {
					throw new Exception(
							"boolean addAll(Collection c): This feature is not yet implemented in this version.");
				} catch (Exception ex) {
					log.error("Exception: " + ex.getMessage());
				}
			}
			return false;
		}
	}

	/**
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public boolean addAll(int index, Collection c) {
		if (hasAllRecords_) {
			boolean result = listChunk_.addAll(c);
			calculateRealSize();
			return result;
		} else {
			try {
				throw new Exception(
						"boolean addAll(int index, Collection c): This feature is not yet implemented in this version.");
			} catch (Exception ex) {
				log.error("Exception: " + ex.getMessage());
				System.err.println(ex.getMessage());
			}

		}
		return false;
	}

	/**
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection c) {
		if (hasAllRecords_) {
			boolean result = listChunk_.removeAll(c);
			calculateRealSize();
			return result;
		} else {
			try {
				throw new Exception(
						"boolean removeAll(Collection c): This feature is not yet implemented in this version.");
			} catch (Exception ex) {
				log.error("Exception: " + ex.getMessage());
			}
		}
		return false;
	}

	/**
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection c) {
		if (hasAllRecords_) {
			return listChunk_.retainAll(c);
		} else {
			try {
				throw new Exception(
						"boolean retainAll(Collection c): This feature is not yet implemented in this version.");
			} catch (Exception ex) {
				log.error("Exception: " + ex.getMessage());
				System.err.println(ex.getMessage());
			}
			return false;
		}
	}

	/**
	 * @see java.util.List#clear()
	 */

	public void clear() {
		listChunk_.clear();
		hasAllRecords_ = false;
	}

	/**
	 * @param index
	 * @return Object at this index
	 * 
	 */
	public Object get(int index) {
		if (realSize_ == -1) {
			size();
		}
		if (hasAllRecords_) {
			return listChunk_.get(index);
		} else {
			// go through entire list from appservice taking into account
			// removed objects and added objects and get that object
			// log.debug("listChunk_ size is " + currentSize);
			int firstRow = originalStart_;
//TODO ::			ApplicationService appService = ApplicationServiceProvider.getApplicationService();

			if ((index >= (firstRow + maxRecordsPerQuery_))
					&& (index < realSize_)) {
				originalStart_ = index;
				try {

					List ls = appService.query(originalCrit_, originalStart_, targetClassName);
					listChunk_.clear();

					listChunk_.addAll(ls);
					return listChunk_.get(index - originalStart_);

				} catch (Exception ex) {
					log.error("Exception: " + ex.getMessage());
				}
			} else if (index < firstRow) {// first row is at 2003, index is 4
				originalStart_ = index;
				try {
					List ls1 = appService.query(originalCrit_, originalStart_,targetClassName);
					listChunk_.clear();
					listChunk_.addAll(ls1);
					return listChunk_.get(index - originalStart_);

				} catch (Exception ex) {
					log.error("Exception: " + ex.getMessage());
				}
			} else // within the currentwindow
			{
				return listChunk_.get(index - originalStart_);
			}
			return new Object();
		}
	}

	/**
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public Object set(int index, Object element) {
		RangeCheck(index);
		if (hasAllRecords_) {
			return listChunk_.set(index, element);
		} else {
			try {
				throw new Exception(
						"Object set(int index, Object element): This feature is not yet implemented in this version.");
			} catch (Exception ex) {
				log.error("Exception: " + ex.getMessage());
			}
		}
		return new Object();
	}

	/**
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	public void add(int index, Object element) {
		if (hasAllRecords_) {
			listChunk_.add(index, element);
			calculateRealSize();
		} else {
			try {
				throw new Exception(
						"void add(int index, Object element): This feature is not yet implemented in this version.");
			} catch (Exception ex) {
				log.error("Exception: " + ex.getMessage());
			}
			// note it added in the added objects hashmap
		}

	}
	/**
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	public Object remove(int index) {
		if (hasAllRecords_) {
			Object result = listChunk_.remove(index);
			calculateRealSize();
			return result;
		} else {
			try {
				throw new Exception(
						"Object remove(int index): This feature is not yet implemented in this version.");
			} catch (Exception ex) {
				log.error("Exception: " + ex.getMessage());
			}
			// find if it exists anywhere via appservice and then
			// track it in removed objects then return it
		}
		return new Object();
	}

	/**
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOf(Object o) {
		if (hasAllRecords_) {
			return listChunk_.indexOf(o);
		} else {
			if (o == null) {
				for (int i = 0; i < realSize_; i++) {
					Object obj = get(i);
					if (obj == null)
						return i;
				}
			} else {
				for (int i = 0; i < realSize_; i++) {
					Object obj = get(i);
					if (o.equals(obj))
						return i;
				}

			}
			return -1;
		}
	}

	/**
	 * @see java.util.yList#lastIndexOf(java.lang.Object)
	 */
	public int lastIndexOf(Object o) {
		if (hasAllRecords_) {
			return listChunk_.lastIndexOf(o);
		} else {
			if (o == null) {
				for (int i = realSize_ - 1; i >= 0; i--) {
					Object obj = get(i);
					if (obj == null)
						return i;
				}
			} else {
				for (int i = realSize_ - 1; i >= 0; i--) {
					Object obj = get(i);
					if (o.equals(obj))
						return i;
				}
			}
			return -1;
		}
	}

	/**
	 * @see java.util.List#subList(int, int)
	 */
	public List subList(int fromIndex, int toIndex) {
		if (hasAllRecords_) {
			return listChunk_.subList(fromIndex, toIndex);
		} else {
			try {
				throw new Exception("List subList(int fromIndex, int toIndex): This feature is not yet implemented in this version.");
			} catch (Exception e) {
				log.error(e);
			}
			return new ArrayList();
		}
	}

	/**
	 * @return Returns the hasAllRecords.
	 */
	public boolean isHasAllRecords() {
		return hasAllRecords_;
	}

	/**
	 * @param hasAllRecords
	 *            The hasAllRecords to set.
	 */
	public void setHasAllRecords(boolean hasAllRecords) {
		hasAllRecords_ = hasAllRecords;
	}

	/**
	 * @return Returns the maxRecordsPerQuery.
	 */
	public int getMaxRecordsPerQuery() {
		return maxRecordsPerQuery_;
	}

	/**
	 * @param maxRecordsPerQuery
	 *            The maxRecordsPerQuery to set.
	 */
	public void setMaxRecordsPerQuery(int maxRecordsPerQuery) {
		maxRecordsPerQuery_ = maxRecordsPerQuery;
	}

	/**
	 * @return Returns the orginalCriteria.
	 */
	public Object getOriginalCriteria() {
		return originalCrit_;
	}

	/**
	 * @param originalCriteria
	 *            The orginalCriteria to set.
	 */
	public void setOriginalCriteria(Object originalCriteria) {
		originalCrit_ = originalCriteria;
	}

	/**
	 * @return Returns the orginalStart.
	 */
	public int getOriginalStart() {
		return originalStart_;
	}

	/**
	 * @param orginalStart
	 *            The orginalStart to set.
	 */
	public void setOriginalStart(int orginalStart) {
		originalStart_ = orginalStart;
	}

	/**
	 * @return Returns the realSize.
	 */
	public int getRealSize() {
		return realSize_;
	}

	/**
	 * @param realSize
	 *            The realSize to set.
	 */
	public void setRealSize(int realSize) {
		realSize_ = realSize;
	}


	private void RangeCheck(int index) {
		if (index >= realSize_)
			throw new IndexOutOfBoundsException("Index: " + index + " Size: "
					+ realSize_);
	}

	/**
	 * @param className
	 */
	public void setTargetClassName(String className) {
		targetClassName = className;
	}

	/**
	 * @return String targetClassName
	 */
	public String getTargetClassName() {
		return targetClassName;
	}

	public void setAppService(ApplicationService appService) {
		this.appService = appService;
	}
	
	public List getListChunk()
	{
		return listChunk_;
	}

	public void setListChunk(List chunk)
	{
		listChunk_.clear();
		listChunk_.addAll(chunk);
	}
	
	public void calculateRealSize()
	{
		if(listChunk_.size()< maxRecordsPerQuery_)
		{
			realSize_ = listChunk_.size(); 
			hasAllRecords_ = true;
		}
	}
}
