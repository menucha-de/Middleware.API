package havis.middleware.ale.base.operation.tag.result;

import org.junit.Assert;

import org.junit.Test;

public class PasswordResultTest {
	
	@Test
	public void passwordResult(){
		PasswordResult test = new PasswordResult();
		test.setState(ResultState.SUCCESS);
		PasswordResult actual = new PasswordResult(ResultState.MISC_ERROR_TOTAL);
		Assert.assertEquals(ResultState.MISC_ERROR_TOTAL, actual.getState());
		Assert.assertNotSame(test.getState(), actual.getState());
	}
	
	@Test
	public void equals(){
		PasswordResult passwordResult = new PasswordResult(ResultState.MISC_ERROR_TOTAL);
		
		Assert.assertFalse(passwordResult.equals(new LockResult(ResultState.FIELD_EXISTS_ERROR)));
		Assert.assertTrue(passwordResult.equals(passwordResult));
	}
	
	@Test
	public void toStringTest(){
		PasswordResult passwordResult = new PasswordResult(ResultState.MISC_ERROR_TOTAL);
		
		Assert.assertEquals("PasswordResult [state=MISC_ERROR_TOTAL]", passwordResult.toString());
	}
}
