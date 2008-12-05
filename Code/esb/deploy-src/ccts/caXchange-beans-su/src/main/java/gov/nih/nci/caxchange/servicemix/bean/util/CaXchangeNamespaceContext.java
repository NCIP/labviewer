package gov.nih.nci.caxchange.servicemix.bean.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;

public class CaXchangeNamespaceContext implements NamespaceContext{
    Map<String, String> namespaces = new HashMap<String, String>();
    /**
     * Default constructor
     */
    public CaXchangeNamespaceContext() {
    }

    /**
     * Gets namespace URI
     * @param prefix
     * @return returns the value to which specified key is mapped
     * @throws
     */
    public String getNamespaceURI(String prefix) {
        return namespaces.get(prefix);
    }
    
    /**
     * Get prefix bound to namespace uri in current scope
     * @param namespaceURI
     * @return prefix bound to namespace uri in current context
     * @throws
     */
    public String getPrefix(String namespaceURI) {
         for (Iterator it = namespaces.keySet().iterator(); it.hasNext();) {
                        Map.Entry entry = (Map.Entry) it.next();
                        if (entry.getValue().equals(namespaceURI)) {
                                return (String) entry.getKey();
                        }
                }
        return null;
    }
    
    /**
     * Get all prefixes bound to a namespace URI in the current scope
     * @param namespaceURI
     * @return iterator for all prefixes bound to the namespace uri  in the current scope
     * @throws 
     */
    public Iterator getPrefixes(String namespaceURI) {
        HashSet prefixes = new HashSet();
        for (Iterator it = namespaces.keySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                if (entry.getValue().equals(namespaceURI)) {
                        prefixes.add(entry.getKey());
                }
        }
        return prefixes.iterator();
    }
    
    /**
     * This method adds namespace to the caxchange namespace context
	 * @param prefix
	 * @param uri
	 * @return
	 * @throws
	 */
    
    public void addNameSpace(String prefix, String uri) {
        namespaces.put(prefix, uri);
    }
}
