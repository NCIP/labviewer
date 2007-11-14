package gov.nih.nci.caxchange.servicemix.bean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import java.util.Map;

import javax.xml.namespace.NamespaceContext;

public class CaXchangeNamespaceContext implements NamespaceContext{
    Map<String, String> namespaces = new HashMap<String, String>();
    
    public CaXchangeNamespaceContext() {
    }

    public String getNamespaceURI(String prefix) {
        return namespaces.get(prefix);
    }

    public String getPrefix(String namespaceURI) {
         for (Iterator it = namespaces.keySet().iterator(); it.hasNext();) {
                        Map.Entry entry = (Map.Entry) it.next();
                        if (entry.getValue().equals(namespaceURI)) {
                                return (String) entry.getKey();
                        }
                }
        return null;
    }

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
    
    public void addNameSpace(String prefix, String uri) {
        namespaces.put(prefix, uri);
    }
}
