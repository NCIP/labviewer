package test.gov.nih.nci.codegen;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.gov.nih.nci.codegen.validator.ValidatorTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { 
	ValidatorTestSuite.class
	}
)
public class SDKCodeGeneratorTestSuite {
}