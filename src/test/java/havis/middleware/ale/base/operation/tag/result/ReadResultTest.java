package havis.middleware.ale.base.operation.tag.result;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class ReadResultTest {
	@Test
	public void readResult(){
		ReadResult test = new ReadResult();
		test.setState(ResultState.MISC_ERROR_TOTAL);
		ReadResult actual = new ReadResult(ResultState.MISC_ERROR_TOTAL);
		ReadResult expected = new ReadResult(ResultState.MISC_ERROR_TOTAL, new byte[]{1, 2});
		Assert.assertNotSame(expected.getData(), actual.getData());
		test.setData(new byte[]{1, 2});
		Assert.assertTrue(Arrays.equals(new byte[]{1, 2}, expected.getData()));
		Assert.assertEquals(ResultState.MISC_ERROR_TOTAL, test.getState());
		Assert.assertTrue(Arrays.equals(test.getData(), expected.getData()));
	}
	
	@Test
	public void hashCodeTest(){
		ReadResult readResult1 = new ReadResult(ResultState.MISC_ERROR_TOTAL, new byte[]{1, 2});
		ReadResult readResult2 = new ReadResult(ResultState.MISC_ERROR_TOTAL, new byte[]{3, 4});
		
		Assert.assertNotEquals(readResult1.hashCode(), readResult2.hashCode());
	}
	
	@Test
	public void equals(){
		ReadResult readResult1 = new ReadResult(ResultState.MISC_ERROR_TOTAL, new byte[]{1, 2});
		ReadResult readResult1_1 = new ReadResult(ResultState.MISC_ERROR_TOTAL, new byte[]{1, 2});
		ReadResult readResult2 = new ReadResult(ResultState.MISC_ERROR_TOTAL, new byte[]{3, 4});

		Assert.assertTrue(readResult1.equals(readResult1));
		Assert.assertFalse(readResult1.equals(null));
		Assert.assertFalse(readResult1.equals(new LockResult(ResultState.MISC_ERROR_TOTAL)));
		Assert.assertFalse(readResult1.equals(readResult2));
		Assert.assertTrue(readResult1.equals(readResult1_1));
	}
	
	@Test 
	public void toStringTest(){
		ReadResult readResult = new ReadResult(ResultState.MISC_ERROR_TOTAL, new byte[]{1, 2});
		
		Assert.assertEquals("ReadResult [data=[1, 2], state=MISC_ERROR_TOTAL]", readResult.toString());
	}
}
