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

import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.DeserializerImpl;
import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.message.MessageElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hl7.meta.Datatype;
import org.hl7.meta.impl.SimpleDatatypeImpl;
import org.hl7.types.ANY;
import org.hl7.xml.parser.StandaloneDataTypeContentHandler;
import org.xml.sax.SAXException;


import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * HISTORY      : : SomeClass.java,v $
 */

/**
 * This class deserializes a XML to HL7 v3 data type java object
 *
 * @author OWNER: Eric Chen  Date: Nov 5, 2007
 * @author LAST UPDATE: $Author: chene $
 * @version $Revision: 1.3 $
 * @date $$Date: 2008-01-31 23:44:33 $
 */


public class DatatypeDeserializer extends DeserializerImpl implements Deserializer
{
    public static final String HL7_URI = "urn:hl7-org:v3";

    protected static Log LOG = LogFactory.getLog(DatatypeDeserializer.class.getName());    

    public void onEndElement(String namespace, String localName, DeserializationContext context) {
        long startTime=System.currentTimeMillis();

        System.out.println("************localname:" + localName);
        //TODO Only Support Simple Data type 
        Datatype datatype = new SimpleDatatypeImpl(localName);
        System.out.println("************datatype:" + datatype);

        MessageElement msgElem = context.getCurElement();
        String asString = null;

        try
        {
            asString = msgElem.getAsString();
        }
        catch (Exception e)
        {
            LOG.error("Problem extracting message type! Result will be null!", e);
        }


        InputStream is = new ByteArrayInputStream(asString.getBytes());
        try
        {
            value = StandaloneDataTypeContentHandler.parseValue(is, datatype);
            System.out.println("value = " + value);
        }
        catch (SAXException e)
        {
            LOG.error("Problem with JavaSIG data type desirization!", e);
        }

        long duration=System.currentTimeMillis()- startTime;
        LOG.debug("Total time to deserialize("+localName+"):"+duration+" ms.");
    }

    /**
     * For own test purpose
     * @param is
     * @param datatypeName
     * @return
     */
    private ANY deserialize(InputStream is, String datatypeName) {
        Datatype datatype = new SimpleDatatypeImpl(datatypeName);
        ANY value = null;
        try
        {
            value = (ANY)StandaloneDataTypeContentHandler.parseValue(is, datatype);
        }
        catch (SAXException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return value;
    }

    public static void main(String[] args)
    {
        final String input = "<II xmlns=\"" + HL7_URI + "\" "
            + "assigningAuthorityName=\"authName\" root=\"2.16.840.1.113883.19\" displayable=\"true\" extension=\"c266\"/>";
        // TODO: Maybe using DocumentInpurSource object instead input stream
        InputStream is = new ByteArrayInputStream(input.getBytes());
        DatatypeDeserializer deserializerFactory = new DatatypeDeserializer();
        ANY ii = deserializerFactory.deserialize(is, "II");
        DatatypeSerializer serializerFactory = new DatatypeSerializer();
        System.out.println(serializerFactory.serialize(ii, "II", "identifier"));
   }
    
}