package havis.middleware.ale.base.operation.tag;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class SightingTest {

	@Test
	public void sighting() {
		Sighting sighting = new Sighting("test", (short) 1, 2, new Date(0));
		Assert.assertEquals(1, sighting.getAntenna());
		Assert.assertEquals("test", sighting.getHost());
		Assert.assertEquals(2, sighting.getStrength());
		Assert.assertEquals(new Date(0), sighting.getTimestamp());
		Sighting expected = new Sighting();
		expected.setAntenna((short) 1);
		expected.setHost("test");
		expected.setStrength(2);
		expected.setTimestamp(new Date(0));
		Assert.assertEquals(expected.getAntenna(), sighting.getAntenna());
		Assert.assertEquals(expected.getHost(), sighting.getHost());
		Assert.assertEquals(expected.getStrength(), sighting.getStrength());
		Assert.assertEquals(expected.getTimestamp(), sighting.getTimestamp());

		Date expectedTimestamp = new Date();
		sighting = new Sighting("test", (short) 1, 2);
		Assert.assertEquals("test", sighting.getHost());
		Assert.assertEquals(1, sighting.getAntenna());
		Assert.assertEquals(2, sighting.getStrength());
		long diff = sighting.getTimestamp().getTime() - expectedTimestamp.getTime();
		Assert.assertTrue("Unexpected timestamp", diff >= 0 && diff <= 10);
	}

	@Test
	public void hashCodeTest() {
		Sighting sighting1 = new Sighting("test", (short) 1, 2, new Date(100000));
		Sighting sighting2 = new Sighting(null, (short) 1, 2, new Date(100000));

		Assert.assertNotEquals(sighting1.hashCode(), sighting2.hashCode());
	}

	@Test
	public void equals() {
		Sighting sighting1 = new Sighting("test", (short) 1, 2, new Date(0));
		Sighting sighting1_1 = new Sighting("test", (short) 1, 2, new Date(0));
		Sighting sighting2 = new Sighting(null, (short) 1, 2, new Date(0));
		Sighting sighting3 = new Sighting("test", (short) 2, 2, new Date(0));
		Sighting sighting4 = new Sighting("test", (short) 1, 3, new Date(0));
		Sighting sighting5 = new Sighting("test", (short) 1, 2, new Date(1));

		Assert.assertTrue(sighting1.equals(sighting1));
		Assert.assertFalse(sighting1.equals(null));
		Assert.assertFalse(sighting1.equals(""));

		Assert.assertFalse(sighting1.equals(sighting2));
		Assert.assertFalse(sighting2.equals(sighting1));
		Assert.assertFalse(sighting1.equals(sighting3));
		Assert.assertFalse(sighting1.equals(sighting4));
		Assert.assertFalse(sighting1.equals(sighting5));

		Assert.assertTrue(sighting1.equals(sighting1_1));
	}
}
