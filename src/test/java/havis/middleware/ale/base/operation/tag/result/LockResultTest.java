package havis.middleware.ale.base.operation.tag.result;

import org.junit.Assert;

import org.junit.Test;

public class LockResultTest {
	@Test
	public void passwordResult(){
		LockResult test = new LockResult ();
		test.setState(ResultState.SUCCESS);
		LockResult  actual = new LockResult (ResultState.MISC_ERROR_TOTAL);
		Assert.assertEquals(ResultState.MISC_ERROR_TOTAL, actual.getState());
		Assert.assertNotSame(test.getState(), actual.getState());
	}
	
	@Test
	public void equals(){
		LockResult  lockResult = new LockResult (ResultState.MISC_ERROR_TOTAL);
		
		Assert.assertFalse(lockResult.equals(new KillResult(ResultState.FIELD_EXISTS_ERROR)));
		Assert.assertTrue(lockResult.equals(lockResult));
	}
	
	@Test
	public void toStringTest(){
		LockResult  lockResult = new LockResult (ResultState.MISC_ERROR_TOTAL);
		
		Assert.assertEquals("LockResult [state=MISC_ERROR_TOTAL]", lockResult.toString());
	}
}
