package gov.nih.nci.cagrid.coppa.dto.transform;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.axis.message.MessageElement;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.coppa.iso.SetOfAny;
import gov.nih.nci.cagrid.coppa.iso.Tel;
import gov.nih.nci.cagrid.coppa.iso.TelEmail;
import gov.nih.nci.cagrid.coppa.iso.TelPerson;
import gov.nih.nci.cagrid.coppa.iso.TelPhone;
import gov.nih.nci.cagrid.coppa.iso.TelUrl;
import gov.nih.nci.coppa.iso.Any;
import gov.nih.nci.coppa.iso.DSet;

public  class DSETTransformer<G extends Any> implements Transformer<gov.nih.nci.cagrid.coppa.iso.DSet,DSet<G>> {
	 protected static Logger logger = LogManager.getLogger(DSETTransformer.class);
    static final QName telQName = new QName("http://isodatatypes.coppa.nci.nih.gov", "Tel");
    static final QName telEmailQName = new QName("http://isodatatypes.coppa.nci.nih.gov", "TelEmail");
    static final QName telPersonQName = new QName("http://isodatatypes.coppa.nci.nih.gov", "TelPerson");
    static final QName telPhoneQName = new QName("http://isodatatypes.coppa.nci.nih.gov", "TelPhone");
    static final QName telUrlQName = new QName("http://isodatatypes.coppa.nci.nih.gov", "TelUrl");
    static final Set<QName> telQNameSet = new HashSet<QName>();
    static{
    	telQNameSet.add(telQName);
    	telQNameSet.add(telPhoneQName);
    	telQNameSet.add(telEmailQName);
    	telQNameSet.add(telPersonQName);
    	telQNameSet.add(telUrlQName);
    }

	public DSet<G> transform(gov.nih.nci.cagrid.coppa.iso.DSet input)
			throws DtoTransformException {
		DSet<G> res = new DSet<G>();
		res = transform(input,res);
		return res;
	}
	/*
	public DSet<G> transform(gov.nih.nci.cagrid.coppa.iso.DSet input,
			DSet<G> res) throws DtoTransformException {
		if (input==null) return null;
		try {
		gov.nih.nci.cagrid.coppa.iso.Any[] setOfAny = input.getItem();
		if (setOfAny==null) return res;
		Set<G> results = new HashSet<G>();
		javax.xml.transform.Transformer stringTransformer = TransformerFactory.newInstance().newTransformer();
		for (gov.nih.nci.cagrid.coppa.iso.Any any:setOfAny){
			if (any instanceof Tel){
				gov.nih.nci.coppa.iso.Tel tel_iso = new TELTransformer().transform((Tel)any);
                results.add((G)tel_iso);
			}
		}
		res.setItem(results);
		return res;
		}catch(Exception e){
			logger.error("Error transforming DSet",e);
			throw new DtoTransformException("Error transforming DSet",e);
		}
	} */	

	public DSet<G> transform(gov.nih.nci.cagrid.coppa.iso.DSet input,
			DSet<G> res) throws DtoTransformException {
		if (input==null) return null;
		try {
		SetOfAny setOfAny = input.getItem();
		if (setOfAny==null) return res;
		MessageElement[] elements = setOfAny.get_any();
		Set<G> results = new HashSet<G>();
		javax.xml.transform.Transformer stringTransformer = TransformerFactory.newInstance().newTransformer();
		for (MessageElement element:elements){
			logger.debug("element :"+element.getAsString()+" "+element.getQName());
			if (telQNameSet.contains(element.getQName())){
				logger.debug("Transforming elements");
				Element el = element.getAsDOM();
				StringWriter sw = new StringWriter();
				stringTransformer.transform(new DOMSource(el), new StreamResult(sw));
				Tel tel = null;
				if (telPhoneQName.equals(element.getQName())) {
				   tel = (Tel)Utils.deserializeObject(new StringReader(sw.toString()), TelPhone.class);
				}else if (telEmailQName.equals(element.getQName())) {
				   tel = (Tel)Utils.deserializeObject(new StringReader(sw.toString()), TelEmail.class);
				}else if (telPersonQName.equals(element.getQName())) {
				   tel = (Tel)Utils.deserializeObject(new StringReader(sw.toString()), TelPerson.class);
				}else if (telUrlQName.equals(element.getQName())) {
				   tel = (Tel)Utils.deserializeObject(new StringReader(sw.toString()), TelUrl.class);
				}else  {
				   tel = (Tel)Utils.deserializeObject(new StringReader(sw.toString()), Tel.class);
				}
				gov.nih.nci.coppa.iso.Tel tel_iso = new TELTransformer().transform(tel);
                results.add((G)tel_iso);
			}
		}
		res.setItem(results);
		return res;
		}catch(Exception e){
			logger.error("Error transforming DSet",e);
			throw new DtoTransformException("Error transforming DSet",e);
		}
	}
    
	public gov.nih.nci.cagrid.coppa.iso.DSet  transform(DSet<G> input)
	throws DtoTransformException {
		gov.nih.nci.cagrid.coppa.iso.DSet res = new gov.nih.nci.cagrid.coppa.iso.DSet();
		res = transform(input,res);
		return res;
	}
	/*
	public gov.nih.nci.cagrid.coppa.iso.DSet transform(DSet<G> input,
			gov.nih.nci.cagrid.coppa.iso.DSet res) throws DtoTransformException {
		if (input==null) return null;	
		try {
			Set<G> set = input.getItem();
			gov.nih.nci.cagrid.coppa.iso.Any[] listOfAny = new gov.nih.nci.cagrid.coppa.iso.Any[set.size()];
			int i=0;
			for (Any any_iso:set){
				StringWriter sw = new StringWriter();
				if (any_iso instanceof gov.nih.nci.coppa.iso.Tel){
					Tel tel = new TELTransformer().transform((gov.nih.nci.coppa.iso.Tel)any_iso);
					listOfAny[i++] = tel;
				}
				
			}
			res.setItem(listOfAny);
			return res;
		}catch(Exception e){
			logger.error("Error transforming DSet",e);
			throw new DtoTransformException("Error transforming DSet",e);
		}
	}	*/

	public gov.nih.nci.cagrid.coppa.iso.DSet transform(DSet<G> input,
			gov.nih.nci.cagrid.coppa.iso.DSet res) throws DtoTransformException {
		if (input==null) return null;	
		try {
			SetOfAny results = new SetOfAny();
			Set<G> set = input.getItem();
			List<MessageElement> listOfAny = new ArrayList<MessageElement>();
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			for (Any any_iso:set){
				StringWriter sw = new StringWriter();
				if (any_iso instanceof gov.nih.nci.coppa.iso.Tel){
					Tel tel = new TELTransformer().transform((gov.nih.nci.coppa.iso.Tel)any_iso);
					if (tel instanceof TelPhone) {
					   Utils.serializeObject(tel, telPhoneQName, sw);
					}else if (tel instanceof TelEmail) {
					   Utils.serializeObject(tel, telEmailQName, sw);
					}else if (tel instanceof TelPerson) {
					   Utils.serializeObject(tel, telPersonQName, sw);
					}else if (tel instanceof TelUrl) {
					   Utils.serializeObject(tel, telUrlQName, sw);
					}else  {
					   Utils.serializeObject(tel, telQName, sw);
					}
				}
				Document document=documentBuilder.parse(new ByteArrayInputStream(sw.toString().getBytes()));
				MessageElement element = new MessageElement(document.getDocumentElement());
				listOfAny.add(element);				
			}
			results.set_any(listOfAny.toArray(new MessageElement[listOfAny.size()]));
			res.setItem(results);
			return res;
		}catch(Exception e){
			logger.error("Error transforming DSet",e);
			throw new DtoTransformException("Error transforming DSet",e);
		}
	} 
}
