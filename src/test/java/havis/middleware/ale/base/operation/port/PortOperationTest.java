package havis.middleware.ale.base.operation.port;

import havis.middleware.ale.base.operation.port.Operation.Type;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class PortOperationTest {

    @Test
    public void portOperation() {
        Operation operation = new Operation("test", Type.READ, Byte.valueOf((byte) 0x00), Long.valueOf(100L), new Pin(1,
                havis.middleware.ale.base.operation.port.Pin.Type.INPUT));
        PortOperation portOperation = new PortOperation(Arrays.asList(operation));
        Assert.assertNotNull(portOperation.getOperations());
        Assert.assertEquals(1, portOperation.getOperations().size());
        Assert.assertEquals(operation, portOperation.getOperations().get(0));
    }

    @Test
    public void equals() {
        PortOperation portOperation1 = new PortOperation(Arrays.asList(new Operation("test", Type.READ, Byte.valueOf((byte) 0x00), Long.valueOf(100L), new Pin(
                1, havis.middleware.ale.base.operation.port.Pin.Type.INPUT))));
        PortOperation portOperation2 = new PortOperation(Arrays.asList(new Operation("test", Type.READ, Byte.valueOf((byte) 0x00), Long.valueOf(100L), new Pin(
                1, havis.middleware.ale.base.operation.port.Pin.Type.INPUT))));
        PortOperation portOperation3 = new PortOperation(Arrays.asList(new Operation("test2", Type.READ, Byte.valueOf((byte) 0x00), Long.valueOf(100L),
                new Pin(1, havis.middleware.ale.base.operation.port.Pin.Type.INPUT))));
        Assert.assertTrue(portOperation1.equals(portOperation2));
        Assert.assertTrue(portOperation2.equals(portOperation1));
        Assert.assertFalse(portOperation1.equals(portOperation3));
        Assert.assertFalse(portOperation3.equals(portOperation1));
        Assert.assertFalse(portOperation1.equals(null));
        Assert.assertFalse(portOperation1.equals(""));
        
        Assert.assertTrue(portOperation1.equals(portOperation1));
    }

    @Test
    public void hashCodeTest() {
        PortOperation portOperation1 = new PortOperation(Arrays.asList(new Operation("test", Type.READ, Byte.valueOf((byte) 0x00), Long.valueOf(100L), new Pin(
                1, havis.middleware.ale.base.operation.port.Pin.Type.INPUT))));
        PortOperation portOperation2 = new PortOperation(Arrays.asList(new Operation("test", Type.READ, Byte.valueOf((byte) 0x00), Long.valueOf(100L), new Pin(
                1, havis.middleware.ale.base.operation.port.Pin.Type.INPUT))));
        PortOperation portOperation3 = new PortOperation(Arrays.asList(new Operation("test2", Type.READ, Byte.valueOf((byte) 0x00), Long.valueOf(100L),
                new Pin(1, havis.middleware.ale.base.operation.port.Pin.Type.INPUT))));
        Assert.assertEquals(portOperation1.hashCode(), portOperation2.hashCode());
        Assert.assertNotEquals(portOperation1.hashCode(), portOperation3.hashCode());
    }
}
