package unitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import pullUpExample.SubClassA;

class PullUpTest {
	SubClassA a = new SubClassA();	

	// SubclassA.function() should call the function from SuperDuperClass. 
	// The test underneath will pass prior to refactoring. 
	// After pulling up the method from SubClassB to SuperClass this test will fail. 
	@Test
	void subClassAFunction_ShouldReturnSuperDuperClass() {
		assertEquals("SuperDuperFunction", a.function());
	}

}
