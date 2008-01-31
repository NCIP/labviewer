/**
 * Copyright 2007-2008 America On Top corp.
 *
 *Copyright Notice.  The software subject to this notice and license includes both
 *human readable source code form and machine readable, binary, object code form
 *This Software was developed in conjunction with the
 *National Cancer Institute ('NCI') by NCI employees and employees of America on Top.  To
 *the extent government employees are authors, any rights in such works shall be
 *subject to Title 17 of the United States Code, section 105.
 *
 *This Software License (the 'License') is between NCI and You.  'You (or
 *'Your') shall mean a person or an entity, and all other entities that control,
 *are controlled by, or are under common control with the entity.  'Control' for
 *purposes of this definition means (i) the direct or indirect power to cause the
 *direction or management of such entity, whether by contract or otherwise, or
 *(ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *(iii) beneficial ownership of such entity.
 *
 *This License is granted provided that You agree to the conditions described
 *below.  NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 *no-charge, irrevocable, transferable and royalty-free right and license in its
 *rights in the Software to (i) use, install, access, operate, execute, copy,
 *modify, translate, market, publicly display, publicly perform, and prepare
 *derivative works of the Software; (ii) distribute and have distributed to
 *and by third parties the Software and any modifications and derivative works
 *thereof; and (iii) sublicense the foregoing rights set out in (i) and (ii) to
 *third parties, including the right to license such rights to further third
 *parties.  For sake of clarity, and not by way of limitation, NCI shall have no
 *right of accounting or right of payment from You or Your sublicensees for the
 *rights granted under this License.  This License is granted at no charge to You.
 *
 *1. Your redistributions of the source code for the Software must retain the
 *above copyright notice, this list of conditions and the disclaimer and
 *limitation of liability of Article 6 below.  Your redistributions in object code
 *form must reproduce the above copyright notice, this list of conditions and the
 *disclaimer of Article 6 in the documentation and/or other materials provided
 *with the distribution, if any.
 *2. Your end-user documentation included with the redistribution, if any, must
 *include the following acknowledgment: 'This product includes software developed
 *by ScenPro and the National Cancer Institute.'  If You do not include such
 *end-user documentation, You shall include this acknowledgment in the Software
 *itself, wherever such third-party acknowledgments normally appear.
 *
 *3. You may not use the names 'The National Cancer Institute', 'NCI' America on Top corp.
 * to endorse or promote products
 *derived from this Software.  This License does not authorize You to use any
 *trademarks, service marks, trade names, logos or product names of either NCI or
 *America on Top, except as required to comply with the terms of this License.
 *
 *4. For sake of clarity, and not by way of limitation, You may incorporate this
 *Software into Your proprietary programs and into any third party proprietary
 *programs.  However, if You incorporate the Software into third party proprietary
 *programs, You agree that You are solely responsible for obtaining any permission
 *from such third parties required to incorporate the Software into such third
 *party proprietary programs and for informing Your sublicensees, including
 *without limitation Your end-users, of their obligation to secure any required
 *permissions from such third parties before incorporating the Software into such
 *third party proprietary software programs.  In the event that You fail to obtain
 *such permissions, You agree to indemnify NCI for any claims against NCI by such
 *third parties, except to the extent prohibited by law, resulting from Your
 *failure to obtain such permissions.
 *
 *5. For sake of clarity, and not by way of limitation, You may add Your own
 *copyright statement to Your modifications and to the derivative works, and You
 *may provide additional or different license terms and conditions in Your
 *sublicenses of modifications of the Software, or any derivative works of the
 *Software as a whole, provided Your use, reproduction, and distribution of the
 *Work otherwise complies with the conditions stated in this License.
 *
 *6. THIS SOFTWARE IS PROVIDED 'AS IS,' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 *(INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 *NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN NO
 *EVENT SHALL THE NATIONAL CANCER INSTITUTE, EKAGRA, OR THEIR AFFILIATES BE LIABLE
 *FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 *DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 *CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 *TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package gov.nih.nci.caxchange.hl7v3datatype.encoding;

import org.hl7.meta.Datatype;
import org.hl7.meta.impl.ParametrizedDatatypeImpl;
import org.hl7.meta.impl.SimpleDatatypeImpl;
import org.hl7.rim.ActRelationship;
import org.hl7.rim.LivingSubject;
import org.hl7.rim.impl.AssociationSetImpl;
import org.hl7.rim.impl.LivingSubjectImpl;
import org.hl7.types.*;
import org.hl7.types.impl.BAGjuListAdapter;
import org.hl7.types.impl.SETjuSetAdapter;
import org.hl7.types.impl.TSjuDateAdapter;
import org.hl7.xml.parser.StandaloneDataTypeContentHandler;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.*;

/**
 * XML ANY datatype conversion utility class
 */

public class DatatypeUtil {

    private static Transformer transformer;

    static {
        try {
            final TransformerFactory _transformerFactory = TransformerFactory.newInstance();
            transformer = _transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        } catch (Exception e) {
        }
    }


    public static ANY getValueForXML(Datatype datatype, String input) {
        ANY any = null;
        try {
            InputStream is = new ByteArrayInputStream(input.trim().getBytes());
            any = (ANY) StandaloneDataTypeContentHandler.parseValue(is, datatype);

        } catch (NullPointerException e) {
            //System.out.println("setter empty:" );
        } catch (Exception e) {

        }
        return any;
    }

    public static String getXMLForValue(ANY value, Datatype datatype, String localName) throws Exception {
        StringWriter sw = new StringWriter();
        try {
            transformer.transform(new SAXSource(new DatatypeXMLSpeaker(),
                new DatatypeXMLSpeaker.DataValueInputSource(value, localName, datatype)), new StreamResult(sw));
        } catch (NullPointerException e) {
            //	System.out.println("getter empty:");
            //e.printStackTrace();
        }
        return sw.toString();
    }

    public static Set toSet(Set associationSet) {
        Set set = null;
        if(associationSet != null) {
        	set = new LinkedHashSet();

        	Iterator iter = associationSet.iterator();
        	while (iter.hasNext()) {
        		Object ob = iter.next();
        		System.out.println("object set :" + ob.getClass());
        		set.add(ob);
        	}

        }

        return set;

    }

    public static Set toAssociationSet(Set set) {  // XXX the method name is too generic if only for ActRelationship
    	Set<ActRelationship> associationSet = null;
    	ActRelationship actRelationshipAdaptor = null;
    	try {
    		associationSet = new AssociationSetImpl<ActRelationship>();
    		Iterator iter = set.iterator();
    		while(iter.hasNext()) {
    		//	convert adaptor class to actual rim object!!!
    			actRelationshipAdaptor = (ActRelationship)iter.next();
//    			ActRelationship actRelationship = actRelationshipAdaptor.getActRelationship();
//    			associationSet.add(actRelationship);

    			//System.out.println("rimObject :" + ob.getRimObject().getClass().getName());
    		}

    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}

    	return associationSet;
    }

    /*   public static AssociationSet toAssociationSet(Set set) {
           AssociationSet associationSet = new AssociationSetImpl();
           Iterator iter = set.iterator();
           while(iter.hasNext()) {
               Object ob = (Object)iter.next();
               associationSet.(ob);
           }
           return associationSet;

       }
       */
    public static SET getValueForXMLSet(Datatype datatype, String input) throws Exception { //todo: hardcode needs to be get rid of
        SET set = null;

        if ((datatype.getFullName().compareTo("SET<II>") == 0) && (input != null)) {
            //	System.out.println("input" + input);

            //	ArrayList sets = new ArrayList();
            /*   II example */
            //if datatype - II then take string and break it into arrayList of strings; iterate;
            Set<II> sets = new LinkedHashSet<II>();
            StringTokenizer st = new StringTokenizer(input, ">");
            //System.out.println(st.countTokens());

            while (st.hasMoreTokens()) {
                String token = st.nextToken() + ">";
                II ii = (II) DatatypeUtil.getValueForXML(new SimpleDatatypeImpl("II"), token);
                sets.add(ii);
            }
            ;
            //	Collection listSet = (Collection)LISTjuListAdapter.valueOf(sets);
            set = SETjuSetAdapter.valueOf(sets);

        } else if ((datatype.getFullName().compareTo("SET<PQ>") == 0) && (input != null)) {
            Set<PQ> sets = new LinkedHashSet<PQ>();
            //System.out.println("datatype: "+datatype.getFullName());
            StringTokenizer st = new StringTokenizer(input, ">");
            //System.out.println(st.countTokens());

            while (st.hasMoreTokens()) {
                String token = st.nextToken() + ">";
                //   System.out.println(token);
                PQ pq = (PQ) DatatypeUtil.getValueForXML(new SimpleDatatypeImpl("PQ"), token);
                sets.add(pq);
            }
            ;

            set = SETjuSetAdapter.valueOf(sets);

        } else if ((datatype.getFullName().compareTo("SET<CS>") == 0) && (input != null)) {
            Set<CS> sets = new LinkedHashSet<CS>();
            //    System.out.println("datatype: "+datatype.getFullName());
            StringTokenizer st = new StringTokenizer(input, ">");
            //	System.out.println(st.countTokens());

            while (st.hasMoreTokens()) {
                String token = st.nextToken() + ">";
                //	    System.out.println(token);
                CS cs = (CS) DatatypeUtil.getValueForXML(new SimpleDatatypeImpl("CS"), token);
                sets.add(cs);
            }
            ;

            set = SETjuSetAdapter.valueOf(sets);

        } else if ((datatype.getFullName().compareTo("SET<CE>") == 0) && (input != null)) {
            Set<CE> sets = new LinkedHashSet<CE>();
            // System.out.println("datatype: "+datatype.getFullName());
            StringTokenizer st = new StringTokenizer(input, ">");
            //System.out.println(st.countTokens());

            while (st.hasMoreTokens()) {
                String token = st.nextToken() + ">";
                //    System.out.println(token);
                CE ce = (CE) DatatypeUtil.getValueForXML(new SimpleDatatypeImpl("CE"), token);
                sets.add(ce);
            }
            ;

            set = SETjuSetAdapter.valueOf(sets);

        } else if ((datatype.getFullName().compareTo("SET<CD>") == 0) && (input != null)) {
            Set<CD> sets = new LinkedHashSet<CD>();
            // System.out.println("datatype: "+datatype.getFullName());
            StringTokenizer st = new StringTokenizer(input, ">");
            //System.out.println(st.countTokens());

            while (st.hasMoreTokens()) {
                String token = st.nextToken() + ">";
                //    System.out.println(token);
                CD cd = (CD) DatatypeUtil.getValueForXML(new SimpleDatatypeImpl("CD"), token);
                sets.add(cd);
            }
            ;

            set = SETjuSetAdapter.valueOf(sets);

        } else if ((datatype.getFullName().compareTo("SET<RTO>") == 0) && (input != null)) {
            Set<RTO> sets = new LinkedHashSet<RTO>();
            //  System.out.println("datatype: "+datatype.getFullName());
            StringTokenizer st = new StringTokenizer(input, ">");
            //	System.out.println(st.countTokens());

            while (st.hasMoreTokens()) {
                String token = st.nextToken() + ">";
                //   System.out.println(token);
                RTO rto = (RTO) DatatypeUtil.getValueForXML(new SimpleDatatypeImpl("RTO"), token);
                sets.add(rto);
            }
            ;

            set = SETjuSetAdapter.valueOf(sets);

        } else {
            System.out.println("unknown datatype: " + datatype.getFullName());
        }

        return set;
    }

    public static BAG getValueForXMLBag(Datatype datatype, String input) {
        BAG bag = null;

        if (datatype.getFullName().compareTo("BAG<AD>") == 0) { //todo: bug
            List<AD> list = new ArrayList<AD>();
            StringTokenizer st = new StringTokenizer(input, ">");
            while (st.hasMoreTokens()) {
                String token = st.nextToken() + ">";
                AD ad = (AD) DatatypeUtil.getValueForXML(new SimpleDatatypeImpl("AD"), token);
                if (ad != null) {
                list.add(ad);
                }
            }
            bag = BAGjuListAdapter.valueOf(list);
        } else if (datatype.getFullName().compareTo("BAG<EN>") == 0) {
            List<EN> list = new ArrayList<EN>();
            final String delim = "</" + "name" + ">";
            String[] ss = input.split(delim);

            for (int i = 0; i < ss.length; i++) {
                String value = ss[i] + delim;
                EN en = (EN) DatatypeUtil.getValueForXML(new SimpleDatatypeImpl("EN"), value);
                if (en != null) {
                    list.add(en);
                }
            }
            bag = BAGjuListAdapter.valueOf(list);

        } else if (datatype.getFullName().compareTo("BAG<TEL>") == 0) { //todo: bug
            List<TEL> list = new ArrayList<TEL>();
            StringTokenizer st = new StringTokenizer(input, ">");
            while (st.hasMoreTokens()) {
                String token = st.nextToken() + ">";
                TEL tel = (TEL) DatatypeUtil.getValueForXML(new SimpleDatatypeImpl("TEL"), token);
                if (tel != null) {
                list.add(tel);
                }
            }
            bag = BAGjuListAdapter.valueOf(list);

        }


        return bag;
    }


    public static IVL getValueForXMLIVL(Datatype datatype, String input) throws Exception {
        IVL ivl = null;
        if (datatype.getFullName().compareTo("IVL<TS>") == 0) {

        } else if (datatype.getFullName().compareTo("IVL<PQ>") == 0) {

        } else if (datatype.getFullName().compareTo("IVL<INT>") == 0) {

        }

        return ivl;
    }

    public static void main(String[] args) throws Exception {

        //   Datatype datatype = new SimpleDatatypeImpl("CS");

        final String HL7_URI = "urn:hl7-org:v3";
        //   final String input = "<statusCode xmlns=\"" + HL7_URI + "\" code=\"ACTIVE\" codeSystem=\"1.2.3.4\"/>";
        //    CS cs = (CS)getValueForXML(datatype, input);
        //    String setInput = "<entityId xmlns=\"urn:hl7-org:v3\" root=\"1.2.3.4.5\" extension=\"123\" displayable=\"false\"/>";

        //       SET setObject;
        //       String setInput = "<entityId xmlns=\"urn:hl7-org:v3\" root=\"1.2.3.4.5\" extension=\"123\" displayable=\"false\"/>\n"+
        //       "<entityId xmlns=\"urn:hl7-org:v3\" root=\"9.9.9.9.9\" extension=\"999\" displayable=\"false\"/>";
        //     II entityId = (II)DatatypeUtil.getValueForXML(new SimpleDatatypeImpl("II"), setInput);
        //      System.out.println(getXMLForValue(entityId, new SimpleDatatypeImpl("II"),  "entityId"));
        //      setObject = getValueForXMLSet(new ParametrizedDatatypeImpl("SET", new SimpleDatatypeImpl("II")), setInput);
        //     SET<II> entitySet = (SET<II>)DatatypeUtil.getValueForXML(new ParametrizedDatatypeImpl("SET", new SimpleDatatypeImpl("II")), setInput);
        //       System.out.println(DatatypeUtil.getXMLForValue(setObject, new ParametrizedDatatypeImpl("SET", new SimpleDatatypeImpl("II")), "entityId"));
        //   System.out.println(getXMLForValue(cs, datatype,  "statusCode"));
        String ivlInput = "<effectiveTime xmlns=\"urn:hl7-org:v3\"><low inclusive=\"true\" value=\"20050519000000\"/><high inclusive=\"true\" value=\"20050520000000\"/></effectiveTime>";
        IVL ivl = (IVL<TS>) DatatypeUtil.getValueForXML(new ParametrizedDatatypeImpl("IVL", new SimpleDatatypeImpl("TS")), ivlInput);
        System.out.println(DatatypeUtil.getXMLForValue(ivl,
            new ParametrizedDatatypeImpl("IVL", new SimpleDatatypeImpl("TS")), "effectiveTime"));

        String bTime = "<time xmlns=\"urn:hl7-org:v3\" value=\"20050519120500\"/>";
        System.out.println(" bTime: " + (TS)DatatypeUtil.getValueForXML( new SimpleDatatypeImpl("TS"), bTime));
        LivingSubject livingSubject = new LivingSubjectImpl();
        livingSubject.setBirthTime((TS)DatatypeUtil.getValueForXML( new SimpleDatatypeImpl("TS"), bTime));
        TS ts = livingSubject.getBirthTime();
        System.out.println("time : " + ts.toString());
        System.out.println("ts : " + TSjuDateAdapter.valueOf(ts.toString()));

    }

}

/**
 * HISTORY      : : SomeClass.java,v $
 */
