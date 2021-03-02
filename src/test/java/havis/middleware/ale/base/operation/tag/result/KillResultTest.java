package havis.middleware.ale.base.operation.tag.result;

import org.junit.Assert;
import org.junit.Test;

public class KillResultTest {
	@Test
	public void passwordResult(){
		KillResult test = new KillResult ();
		test.setState(ResultState.SUCCESS);
		KillResult  actual = new KillResult (ResultState.MISC_ERROR_TOTAL);
		Assert.assertEquals(ResultState.MISC_ERROR_TOTAL, actual.getState());
		Assert.assertNotSame(test.getState(), actual.getState());
	}
	
	@Test
	public void equals(){
		KillResult killResult = new KillResult (ResultState.MISC_ERROR_TOTAL);
		
		Assert.assertFalse(killResult.equals(new LockResult(ResultState.FIELD_EXISTS_ERROR)));
		Assert.assertTrue(killResult.equals(killResult));
	}
	
	@Test
	public void toStringTest(){
		KillResult killResult = new KillResult (ResultState.MISC_ERROR_TOTAL);
		
		Assert.assertEquals("KillResult [state=MISC_ERROR_TOTAL]", killResult.toString());
	}
}
