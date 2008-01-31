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

import org.apache.axis.Constants;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.wsdl.fromJava.Types;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hl7.meta.Datatype;
import org.hl7.meta.impl.SimpleDatatypeImpl;
import org.hl7.types.ANY;
import org.hl7.util.DatatypeAnalyzer;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;

/**
 * This class serializes a HL7 v3 data type java object to XML stream
 * XML data type element name should be the same as 
 *
 * @author OWNER: Eric Chen  Date: Nov 5, 2007
 * @author LAST UPDATE: $Author: chene $
 * @version $Revision: 1.3 $
 * @date $$Date: 2008-01-31 23:44:35 $
 */


public class DatatypeSerializer implements Serializer
{
    protected static Log LOG = LogFactory.getLog(DatatypeSerializer.class.getName());

    private static Transformer transformer;

    static
    {
        try
        {
            final TransformerFactory _transformerFactory = TransformerFactory.newInstance();
            transformer = _transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        }
        catch (Exception e)
        {
        }
    }

    public void serialize(QName name, Attributes attributes, Object value, SerializationContext context)
        throws IOException
    {
        long startTime = System.currentTimeMillis();
        if (value instanceof ANY)
        {
            ANY any = (ANY) value;
            try
            {
                String stringType = DatatypeAnalyzer.getTypeBase((ANY) value);
                Datatype datatype = new SimpleDatatypeImpl(stringType);

                StringWriter sw = new StringWriter();

                //TODO: Need to syn data stringType local name with schema name
                transformer.transform(new SAXSource(new DatatypeXMLSpeaker(),
                    new DatatypeXMLSpeaker.DataValueInputSource((ANY) any, stringType, datatype)), new StreamResult(sw));
                context.writeString(sw.toString());

            }
            catch (NullPointerException e)
            {
                LOG.error("Problem: getter empty value.", e);
            }
            catch (TransformerException e)
            {
                LOG.error("Problem: Tranformation Error.", e);
            }
            catch (DatatypeAnalyzer.AnalysisException e)
            {
                LOG.error("Problem: getter unknow type", e);
            }


        }

        long duration = System.currentTimeMillis() - startTime;
        LOG.debug("Total time to serialize(" + name.getLocalPart() + "):" + duration + " ms.");
    }

    public String getMechanismType()
    {
        return Constants.AXIS_SAX;
    }

    public Element writeSchema(Class aClass, Types types) throws Exception
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String serialize(ANY value, String datatypeName, String localName)
    {
        Datatype datatype = new SimpleDatatypeImpl(datatypeName);
        StringWriter sw = new StringWriter();
        try
        {
            transformer.transform(new SAXSource(new DatatypeXMLSpeaker(),
                new DatatypeXMLSpeaker.DataValueInputSource(value, localName, datatype)), new StreamResult(sw));
        }
        catch (NullPointerException e)
        {
            LOG.error("Problem: getter empty value.", e);
        }
        catch (TransformerException e)
        {
            LOG.error("Problem: Tranformation Error.", e);
        }
        return sw.toString();
    }

}

/**
 * HISTORY      : : SomeClass.java,v $
 */
