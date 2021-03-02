package havis.middleware.ale.base.operation.tag.result;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;


public class VirtualReadResultTest {
	
	@Test
	public void virtualReadResult(){
		VirtualReadResult virtualReadResult = new VirtualReadResult();
		
		Assert.assertNull(virtualReadResult.getState());
		Assert.assertNull(virtualReadResult.getData());
		
		virtualReadResult.setData(new byte[]{1, 2});
		Assert.assertTrue(Arrays.equals(new byte[]{1, 2}, virtualReadResult.getData()));
		
		virtualReadResult.setState(ResultState.MISC_ERROR_TOTAL);
		Assert.assertEquals(ResultState.MISC_ERROR_TOTAL, virtualReadResult.getState());
	}
	
	@Test
	public void virtualReadResultState(){
		VirtualReadResult virtualReadResult = new VirtualReadResult(ResultState.MISC_ERROR_TOTAL);
		
		Assert.assertEquals(ResultState.MISC_ERROR_TOTAL, virtualReadResult.getState());
		Assert.assertNull(virtualReadResult.getData());
	}
	
	@Test
	public void virtualReadResultStateAndData(){
		VirtualReadResult virtualReadResult = new VirtualReadResult(ResultState.MISC_ERROR_TOTAL, new byte[]{1, 2});
	
		Assert.assertEquals(ResultState.MISC_ERROR_TOTAL, virtualReadResult.getState());
		Assert.assertTrue(Arrays.equals(new byte[]{1, 2}, virtualReadResult.getData()));
	}
	
	@Test 
	public void equals(){
		VirtualReadResult virtualReadResult = new VirtualReadResult(ResultState.MISC_ERROR_TOTAL, new byte[]{1, 2});
		
		Assert.assertFalse(virtualReadResult.equals(new LockResult(ResultState.MISC_ERROR_TOTAL)));
		Assert.assertTrue(virtualReadResult.equals(virtualReadResult));
	}
	
	@Test
	public void toStringTest(){
		VirtualReadResult virtualReadResult = new VirtualReadResult(ResultState.MISC_ERROR_TOTAL, new byte[]{1, 2});
		
		Assert.assertEquals("VirtualReadResult [data=[1, 2], state=MISC_ERROR_TOTAL]", virtualReadResult.toString());
	}
}

