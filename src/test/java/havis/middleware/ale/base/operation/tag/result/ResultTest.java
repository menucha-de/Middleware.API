package havis.middleware.ale.base.operation.tag.result;

import org.junit.Assert;

import org.junit.Test;

public class ResultTest {
	@Test
	public void result(){
		Result actual = new Result();
		Result expected = new Result(ResultState.SUCCESS);
		actual.setState(ResultState.SUCCESS);
		Assert.assertEquals(expected.getState(), actual.getState());
	}
	
	@Test
	public void hashCodeTest(){
		Result result1 = new Result(ResultState.MISC_ERROR_TOTAL);
		Result result2 = new Result(ResultState.EPC_CACHE_DEPLETED);
		Result result3 = new Result(null);
		
		Assert.assertNotEquals(result1.hashCode(), result2.hashCode());
		Assert.assertNotEquals(result1.hashCode(), result3.hashCode());	
	}
	
	@Test 
	public void equals(){
		Result result1 = new Result(ResultState.MISC_ERROR_TOTAL);
		Result result1_1 = new Result(ResultState.MISC_ERROR_TOTAL);
		Result result2 = new Result(ResultState.EPC_CACHE_DEPLETED);
		
		Assert.assertTrue(result1.equals(result1));
		Assert.assertFalse(result1.equals(""));
		Assert.assertFalse(result1.equals(null));
		Assert.assertFalse(result1.equals(result2));
		Assert.assertTrue(result1.equals(result1_1));
	}
	
	@Test
	public void toStringTest(){
		Result result = new Result(ResultState.MISC_ERROR_TOTAL);
		
		Assert.assertEquals("Result [state=MISC_ERROR_TOTAL]",result.toString() );
	}
}
