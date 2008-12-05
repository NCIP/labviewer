package gov.nih.nci.caxchange.security;

import java.security.Principal;

public class CaXchangePrincipal implements Principal {
        String name;
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name=name;
        }
    }