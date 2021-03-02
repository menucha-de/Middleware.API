package havis.middleware.ale.base.operation.tag.result;

import org.junit.Assert;

import org.junit.Test;

public class FaultResultTest {
	@Test
	public void passwordResult(){
		FaultResult test = new FaultResult(ResultState.MISC_ERROR_PARTIAL);
		Assert.assertEquals(ResultState.MISC_ERROR_PARTIAL, test.getState());
		test.setState(ResultState.SUCCESS);
		Assert.assertEquals(ResultState.SUCCESS, test.getState());
	}
	
	@Test 
	public void equals(){
		FaultResult faultResult = new FaultResult(ResultState.MISC_ERROR_PARTIAL);
		
		Assert.assertFalse(faultResult.equals(new KillResult(ResultState.MISC_ERROR_TOTAL)));
		Assert.assertTrue(faultResult.equals(faultResult));
	}
	
	@Test
	public void toStringTest(){
		FaultResult faultResult = new FaultResult(ResultState.MISC_ERROR_PARTIAL);
		
		Assert.assertEquals("FaultResult [state=MISC_ERROR_PARTIAL]", faultResult.toString());
	}
}
