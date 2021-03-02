package havis.middleware.ale.base.operation.tag;

import java.util.Arrays;

import org.junit.Assert;

import org.junit.Test;

public class FilterTest {

	@Test
	public void filter(){
		Filter actual = new Filter(3, 8, 16, new byte[]{});
		Assert.assertEquals(3, actual.getBank());
		Assert.assertEquals(8, actual.getLength());
		Assert.assertEquals(16, actual.getOffset());
		Assert.assertTrue(Arrays.equals(new byte[]{}, actual.getMask()));
	}
	
	@Test
	public void hashCodeTest(){
		Filter filter = new Filter(3,8,16,new byte[]{});
		
		Assert.assertEquals(1020629, filter.hashCode());
	}
	
	@Test 
	public void equals(){
		Filter filter1 = new Filter(3,8,16,new byte[]{});
		Filter filter2 = new Filter(2,8,16,new byte[]{});
		Filter filter3 = new Filter(3,9,16,new byte[]{});
		Filter filter4 = new Filter(3,8,16,new byte[]{0x01});
		Filter filter5 = new Filter(3,8,17,new byte[]{});
		Filter filter6 = new Filter(3,8,16,new byte[]{});
		
		Assert.assertTrue(filter1.equals(filter1));
		Assert.assertFalse(filter1.equals(null));
		Assert.assertFalse(filter1.equals(new Field(null, 0, 0, 0)));
		
		Assert.assertFalse(filter1.equals(filter2));
		Assert.assertFalse(filter1.equals(filter3));
		Assert.assertFalse(filter1.equals(filter4));
		Assert.assertFalse(filter1.equals(filter5));
		
		Assert.assertTrue(filter1.equals(filter6));
		
	}
}
