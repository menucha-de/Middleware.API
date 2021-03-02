package havis.middleware.ale.base.po;

import org.junit.Assert;
import org.junit.Test;


public class OidTest {

	@Test
	public void oid(){
		OID oid = new OID("test");
		
		Assert.assertEquals("test", oid.getOid());
	}
	
	@Test
	public void createPrefixTest(){
		OID oid = new OID("urn:oid:1.0.15961.9.*");
		
		Assert.assertEquals("urn:oid:1.0.15961.9.*", oid.getOid());
	}
	
	@Test
	public void isPatternTest(){
		OID oid = new OID("urn:oid:1.0.15961.9.*");
		
		Assert.assertTrue(oid.isPattern());
	}
	
	@Test 
	public void equalsTest(){
		OID oid1 = new OID("urn:oid:1.0.15961.9.*");
		OID oid1_1 = new OID("urn:oid:1.0.15961.9.*");
		OID oid2 = new OID(null);
		OID oid3 = new OID("urn:oid:1.0.15961.8.*");
		
		Assert.assertTrue(oid1.equals(oid1));
		Assert.assertFalse(oid1.equals(null));
		Assert.assertFalse(oid1.equals(""));
		Assert.assertFalse(oid2.equals(oid1));
		Assert.assertFalse(oid1.equals(oid3));
		Assert.assertTrue(oid1.equals(oid1_1));
	}
	
	@Test 
	public void hashCodeTest(){
		OID oid1 = new OID("urn:oid:1.0.15961.9.*");
		OID oid2 = new OID(null);
		
		Assert.assertEquals(31, oid2.hashCode());
		Assert.assertNotEquals(oid1.hashCode(), oid2.hashCode());
	}
	
	@Test
	public void matchesTest(){
		OID oid1 = new OID("urn:oid:1.0.15961.9.*");
		OID oid2 = new OID(null);
		
		Assert.assertTrue(oid1.matches("urn:oid:1.0.15961.9.1"));
		Assert.assertFalse(oid1.matches("urn:oid:1.1.12345.6.7"));
		Assert.assertFalse(oid1.matches(null));
		Assert.assertFalse(oid2.matches("urn:oid:1.0.15961.9.21"));
	}
	
	@Test
	public void toStringTest(){
		OID oid = new OID("urn:oid:1.0.15961.9.*");
		
		Assert.assertEquals("OID [oid=urn:oid:1.0.15961.9.*]", oid.toString());
	}
}
