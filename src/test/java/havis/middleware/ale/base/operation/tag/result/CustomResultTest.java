package havis.middleware.ale.base.operation.tag.result;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class CustomResultTest {
	@Test
	public void customResult() {
		CustomResult test = new CustomResult();
		test.setState(ResultState.MISC_ERROR_TOTAL);
		CustomResult actual = new CustomResult(ResultState.MISC_ERROR_TOTAL);
		CustomResult expected = new CustomResult(ResultState.MISC_ERROR_TOTAL, new byte[] { 1, 2 });
		Assert.assertNotSame(expected.getData(), actual.getData());
		test.setData(new byte[] { 1, 2 });
		Assert.assertTrue(Arrays.equals(new byte[] { 1, 2 }, expected.getData()));
		Assert.assertEquals(ResultState.MISC_ERROR_TOTAL, test.getState());
		Assert.assertTrue(Arrays.equals(test.getData(), expected.getData()));
	}
	
	@Test
	public void hashCodeTest(){
		CustomResult customResult1 = new CustomResult();
		CustomResult customResult2 = new CustomResult(ResultState.MISC_ERROR_TOTAL);
		
		Assert.assertNotEquals(customResult1.hashCode(), customResult2.hashCode());
	}
	
	@Test
	public void equals(){
		CustomResult customResult1 = new CustomResult(ResultState.MISC_ERROR_TOTAL,new byte[] { 1, 2 });
		CustomResult customResult1_1 = new CustomResult(ResultState.MISC_ERROR_TOTAL,new byte[] { 1, 2 });
		CustomResult customResult2 = new CustomResult(ResultState.MISC_ERROR_TOTAL,new byte[] { 3, 4 });
		
		Assert.assertTrue(customResult1.equals(customResult1));
		Assert.assertFalse(customResult1.equals(null));
		
		Assert.assertFalse(customResult1.equals(new KillResult(ResultState.MISC_ERROR_TOTAL)));
		Assert.assertFalse(customResult1.equals(customResult2));
		
		Assert.assertTrue(customResult1.equals(customResult1_1));
	}
	
	@Test
	public void toStringTest(){
		CustomResult customResult = new CustomResult(ResultState.MISC_ERROR_TOTAL,new byte[] { 1, 2 });
		
		Assert.assertEquals("CustomResult [data=[1, 2], state=MISC_ERROR_TOTAL]", customResult.toString());
	}
}
