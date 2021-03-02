package havis.middleware.ale.base.operation.port;

import havis.middleware.ale.base.operation.port.Pin.Type;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class PortObservationTest {

    @Test
    public void portObservation() {
        PortObservation observation = new PortObservation();
        Assert.assertNotNull(observation.getPins());
        Assert.assertEquals(0, observation.getPins().size());
    }

    @Test
    public void portObservationPins() {
        PortObservation observation = new PortObservation(Arrays.asList(new Pin(1, Type.INPUT), new Pin(2, Type.OUTPUT)));
        Assert.assertNotNull(observation.getPins());
        Assert.assertEquals(2, observation.getPins().size());
        Assert.assertEquals(new Pin(1, Type.INPUT), observation.getPins().get(0));
        Assert.assertEquals(new Pin(2, Type.OUTPUT), observation.getPins().get(1));
    }

    @Test
    public void equals() {
        PortObservation observation1 = new PortObservation(Arrays.asList(new Pin(1, Type.INPUT), new Pin(2, Type.OUTPUT)));
        PortObservation observation1_1 = new PortObservation(Arrays.asList(new Pin(1, Type.INPUT), new Pin(2, Type.OUTPUT)));
        PortObservation observation2 = new PortObservation();
        PortObservation observation3 = new PortObservation(Arrays.asList(new Pin(1, Type.INPUT)));
        PortObservation observation4 = new PortObservation(Arrays.asList(new Pin(2, Type.INPUT), new Pin(3, Type.OUTPUT)));
        
        Assert.assertTrue(observation1.equals(observation1_1));
        Assert.assertTrue(observation1_1.equals(observation1));
        Assert.assertTrue(observation1.equals(observation1));
        Assert.assertFalse(observation1.equals(null));
        Assert.assertFalse(observation1.equals(""));
        Assert.assertFalse(observation1.equals(observation2));
        Assert.assertFalse(observation2.equals(observation1));
        Assert.assertFalse(observation1.equals(observation3));
        Assert.assertFalse(observation3.equals(observation1));
        Assert.assertFalse(observation1.equals(observation4));
        Assert.assertFalse(observation4.equals(observation1));
    }

    @Test
    public void hashCodeTest() {
        PortObservation observation1 = new PortObservation(Arrays.asList(new Pin(1, Type.INPUT), new Pin(2, Type.OUTPUT)));
        PortObservation observation1_1 = new PortObservation(Arrays.asList(new Pin(1, Type.INPUT), new Pin(2, Type.OUTPUT)));
        PortObservation observation2 = new PortObservation();
        PortObservation observation3 = new PortObservation(Arrays.asList(new Pin(1, Type.INPUT)));
        PortObservation observation4 = new PortObservation(Arrays.asList(new Pin(2, Type.INPUT), new Pin(3, Type.OUTPUT)));
        
        Assert.assertEquals(observation1.hashCode(), observation1_1.hashCode());
        Assert.assertNotEquals(observation1.hashCode(), observation2.hashCode());
        Assert.assertNotEquals(observation1.hashCode(), observation2.hashCode());
        Assert.assertNotEquals(observation1.hashCode(), observation3.hashCode());
        Assert.assertNotEquals(observation1.hashCode(), observation4.hashCode());
    }
}