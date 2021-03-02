package havis.middleware.ale.base.operation.port;

import havis.middleware.ale.base.operation.port.Pin.Type;

import org.junit.Assert;
import org.junit.Test;

public class PinTest {

	@Test
	public void pin(){
		Pin pin = new Pin(1, Type.INPUT);
		Assert.assertEquals(1, pin.getId());
		Assert.assertEquals(Type.INPUT, pin.getType());
	}

	@Test
	public void matches() {
	    Pin pin1 = new Pin(1, Type.INPUT);
	    Pin pin2 = new Pin(1, Type.INPUT);
	    Pin pin3 = new Pin(-1, Type.INPUT);
	    Pin pin4 = new Pin(-1, Type.INPUT);
	    Pin pin5 = new Pin(2, Type.INPUT);
	    Pin pin6 = new Pin(1, Type.OUTPUT);
	    Assert.assertTrue(pin1.matches(pin2));
	    Assert.assertTrue(pin2.matches(pin1));
	    Assert.assertTrue(pin1.matches(pin3));
	    Assert.assertTrue(pin3.matches(pin1));
	    Assert.assertTrue(pin3.matches(pin4));
	    Assert.assertTrue(pin4.matches(pin3));
	    Assert.assertFalse(pin1.matches(pin6));
	    Assert.assertFalse(pin6.matches(pin1));
	    Assert.assertFalse(pin1.matches(pin5));
	    Assert.assertFalse(pin5.matches(pin1));
	    Assert.assertTrue(pin3.matches(pin5));
	    Assert.assertTrue(pin5.matches(pin3));
	    Assert.assertFalse(pin5.matches(null));
	}

	@Test
	public void equals() {
        Pin pin1 = new Pin(1, Type.INPUT);
        Pin pin1_1 = new Pin(1, Type.INPUT);
        Pin pin2 = new Pin(-1, Type.INPUT);
        Pin pin3 = new Pin(2, Type.INPUT);
        Pin pin4 = new Pin(1, Type.OUTPUT);
        Assert.assertTrue(pin1.equals(pin1_1));
        Assert.assertTrue(pin1_1.equals(pin1));
        Assert.assertTrue(pin1.equals(pin1));
        Assert.assertFalse(pin1.equals(null));
        Assert.assertFalse(pin1.equals(""));
        Assert.assertFalse(pin1.equals(pin2));
        Assert.assertFalse(pin2.equals(pin1));
        Assert.assertFalse(pin1.equals(pin3));
        Assert.assertFalse(pin3.equals(pin1));
        Assert.assertFalse(pin1.equals(pin4));
        Assert.assertFalse(pin4.equals(pin1));
	}

	@Test
	public void hashCodeTest() {
        Pin pin1 = new Pin(1, Type.INPUT);
        Pin pin1_1 = new Pin(1, Type.INPUT);
        Pin pin2 = new Pin(-1, Type.INPUT);
        Pin pin3 = new Pin(2, Type.INPUT);
        Pin pin4 = new Pin(1, Type.OUTPUT);
        Pin pin5 = new Pin(1,null);
        Assert.assertEquals(pin1.hashCode(), pin1_1.hashCode());
        Assert.assertNotEquals(pin1.hashCode(), pin2.hashCode());
        Assert.assertNotEquals(pin1.hashCode(), pin3.hashCode());
        Assert.assertNotEquals(pin1.hashCode(), pin4.hashCode());
        Assert.assertNotEquals(pin5.hashCode(), pin2.hashCode());
	}
}
