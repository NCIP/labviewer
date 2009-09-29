package test.gov.nih.nci.codegen.validator;

import static org.easymock.classextension.EasyMock.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import gov.nih.nci.codegen.GenerationException;
import gov.nih.nci.codegen.GeneratorError;
import gov.nih.nci.codegen.GeneratorErrors;
import gov.nih.nci.codegen.util.TransformerUtils;
import gov.nih.nci.codegen.validator.PKGeneratorProperty;
import gov.nih.nci.codegen.validator.PKGeneratorValidator;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAttribute;
import gov.nih.nci.ncicb.xmiinout.domain.UMLClass;
import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PKGeneratorValidatorTest{

	private PKGeneratorValidator validator;
	private UMLModel model;
	private UMLAttribute classIdAttribute;
	private UMLAttribute tableIdAttribute;
	private TransformerUtils transformerUtils;
	private UMLClass table;
	private UMLClass klass;
	private HashMap<String, PKGeneratorProperty> pkGeneratorPropertyMap = new HashMap<String, PKGeneratorProperty>();

	public static String getTestCaseName() {
		return "PKGeneratorValidatorTest Test Case";
	}
	
	@Before
	public void setUp() {
		
		validator = new PKGeneratorValidator();
		model = createMock(UMLModel.class);
		table= createMock(UMLClass.class);
		klass= createMock(UMLClass.class);
		transformerUtils=createMock(TransformerUtils.class);
		classIdAttribute = createMock(UMLAttribute.class);
		tableIdAttribute = createMock(UMLAttribute.class);
		
		List<String> supportedDataTypes = new ArrayList<String>();
		supportedDataTypes.add("int");
		List<String> supportedDatabaseTypes = new ArrayList<String>();
		supportedDatabaseTypes.add("oracle");
		List<String> supportedPKGeneratorParams = new ArrayList<String>();
		supportedPKGeneratorParams.add("table");
		PKGeneratorProperty generatorProperty = new PKGeneratorProperty("hilo",supportedDataTypes, supportedDatabaseTypes,supportedPKGeneratorParams);
		pkGeneratorPropertyMap.put("hilo", generatorProperty);
		
		validator.setPkGeneratorPropertyMap(pkGeneratorPropertyMap);
		validator.setTransformerUtils(transformerUtils);
	}

	@After
	public void tearDown() {
		validator = null;
	}

	@Test
	public void validateWithhiloPKGeneratorClassAndOracleDB() throws Exception {
		String databaseType="oracle";
		String dataType="int";
		String generatorClass="hilo";
		HashMap<String,String> pkGeneratorParams=new HashMap<String,String>();
		pkGeneratorParams.put("table","hi_value");
		
		setExpectations(databaseType, dataType, generatorClass,pkGeneratorParams);

		GeneratorErrors errors = validator.validate(model);
		Assert.assertEquals(0, errors.getErrors().size());
 
		verifyAndReset();
	}

	@Test
	public void validateWithInvalidPKGeneratorClass() throws Exception {
		String databaseType="oracle";
		String dataType="int";
		String generatorClass="invalidKlass";
		HashMap<String,String> pkGeneratorParams=new HashMap<String,String>();
		pkGeneratorParams.put("table","hi_value");
		
		setExpectations(databaseType, dataType, generatorClass,pkGeneratorParams);

		GeneratorErrors errors = validator.validate(model);
		GeneratorError actualError = errors.getErrors().iterator().next();
		Assert.assertEquals(1, errors.getErrors().size());
		String expectedMessage = "invalid primary key generator class name invalidKlass for table NO_ID_KEY with column MY_KEY";
		Assert.assertEquals(expectedMessage, actualError.toString());

		verifyAndReset();
	}
	
	@Test
	public void validateWithInvalidDatabase() throws Exception {		
		String databaseType="oracle2";
		String dataType="int";
		String generatorClass="hilo";
		HashMap<String,String> pkGeneratorParams=new HashMap<String,String>();
		pkGeneratorParams.put("table","hi_value");
		
		setExpectations(databaseType, dataType, generatorClass,pkGeneratorParams);

		GeneratorErrors errors = validator.validate(model);
		GeneratorError actualError = errors.getErrors().iterator().next();
		Assert.assertEquals(1, errors.getErrors().size());
		String expectedMessage = "invalid database oracle2 for table NO_ID_KEY with column MY_KEY";
		Assert.assertEquals(expectedMessage, actualError.toString());

		verifyAndReset();
	}
	
	@Test
	public void validateWithInvalidDataType() throws Exception {		
		String databaseType="oracle";
		String dataType="int2";
		String generatorClass="hilo";
		HashMap<String,String> pkGeneratorParams=new HashMap<String,String>();
		pkGeneratorParams.put("table","hi_value");
		
		setExpectations(databaseType, dataType, generatorClass,pkGeneratorParams);

		GeneratorErrors errors = validator.validate(model);
		GeneratorError actualError = errors.getErrors().iterator().next();
		Assert.assertEquals(1, errors.getErrors().size());
		String expectedMessage = "invalid dataType int2 for table NO_ID_KEY with column MY_KEY";
		Assert.assertEquals(expectedMessage, actualError.toString());

		verifyAndReset();
	}
	
	@Test
	public void validateWithInvalidDataTypeAndDatabase() throws Exception {
		String databaseType="oracle2";
		String dataType="int2";
		String generatorClass="hilo";
		HashMap<String,String> pkGeneratorParams=new HashMap<String,String>();
		pkGeneratorParams.put("table","hi_value");
		
		setExpectations(databaseType, dataType, generatorClass,pkGeneratorParams);

		GeneratorErrors errors = validator.validate(model);
		Iterator<GeneratorError> iterator = errors.getErrors().iterator();
		GeneratorError actualError1 = iterator.next();
		GeneratorError actualError2 = iterator.next();
		Assert.assertEquals(2, errors.getErrors().size());
		String expectedMessage1 = "invalid database oracle2 for table NO_ID_KEY with column MY_KEY";
		String expectedMessage2 = "invalid dataType int2 for table NO_ID_KEY with column MY_KEY";
		Assert.assertEquals(expectedMessage1, actualError1.toString());
		Assert.assertEquals(expectedMessage2, actualError2.toString());
	
		verifyAndReset();
	}
	
	@Test
	public void validateWithInvalidPkGeneratorParams() throws Exception {
		String databaseType="oracle";
		String dataType="int";
		String generatorClass="hilo";
		HashMap<String,String> pkGeneratorParams=new HashMap<String,String>();
		pkGeneratorParams.put("table2","hi_value");
		
		setExpectations(databaseType, dataType, generatorClass,pkGeneratorParams);

		GeneratorErrors errors = validator.validate(model);
		GeneratorError actualError = errors.getErrors().iterator().next();
		Assert.assertEquals(1, errors.getErrors().size());
		String expectedMessage = "invalid params [table2]. required params [table] for primary key generator class hilo in table NO_ID_KEY with column MY_KEY";
		Assert.assertEquals(expectedMessage, actualError.toString());

		verifyAndReset();
		
	}
	
	private void setExpectations(String databaseType, String dataType, String generatorClass, HashMap<String, String> pkGeneratorParams) throws GenerationException {
		String fqcn = "NoIdKey";
		Collection<UMLClass> parentClasses = new ArrayList<UMLClass>();
		parentClasses.add(klass);
		String pkgenClassKey = TransformerUtils.TV_PK_GENERATOR + databaseType;
		UMLTaggedValue taggedValue = createMock(UMLTaggedValue.class);
		Collection<UMLTaggedValue> tagValues = new ArrayList<UMLTaggedValue>();
		tagValues.add(taggedValue);;
		
		expect(transformerUtils.getDatabaseType()).andReturn(databaseType);
		expect(transformerUtils.getClassIdAttr(klass)).andReturn(classIdAttribute);
		expect(transformerUtils.getAllParentClasses(model)).andReturn(parentClasses);
		expect(transformerUtils.getFQCN(klass)).andReturn(fqcn);
		expect(transformerUtils.getTable(klass)).andReturn(table);
		expect(table.getName()).andReturn("NO_ID_KEY");
		expect(classIdAttribute.getName()).andReturn("mykey");
		expect(transformerUtils.getMappedColumn(table, fqcn+".mykey")).andReturn(tableIdAttribute);
		expect(transformerUtils.getPKGeneratorTags(table, fqcn, classIdAttribute)).andReturn(pkGeneratorParams);
		expect(tableIdAttribute.getName()).andReturn("MY_KEY");
		expect(transformerUtils.getHibernateDataType(klass,classIdAttribute)).andReturn(dataType);
		expect(transformerUtils.getTagValue(tagValues, pkgenClassKey, 1)).andReturn(generatorClass);
		expect(tableIdAttribute.getTaggedValues()).andReturn(tagValues);
		
		replay(transformerUtils);
		replay(klass);
		replay(table);
		replay(classIdAttribute);
		replay(tableIdAttribute);
	}

	private void verifyAndReset() {
		verify(transformerUtils);
		verify(classIdAttribute);
		verify(klass);
		verify(table);
		verify(tableIdAttribute);

		reset(transformerUtils);
		reset(classIdAttribute);
		reset(table);
		reset(klass);
		reset(tableIdAttribute);
	}
}
