package havis.middleware.ale.base.operation.port;

import havis.middleware.ale.base.operation.port.Operation.Type;

import org.junit.Assert;
import org.junit.Test;

public class OperationTest {

    @Test
    public void operation() {
        Pin pin = new Pin(1, Pin.Type.INPUT);
        Operation operation = new Operation("operation", Type.READ, Byte.valueOf((byte) 0x01), Long.valueOf(2000), pin);
       
        Assert.assertEquals("operation", operation.getName());
        Assert.assertEquals(Type.READ, operation.getType());
        Assert.assertEquals(Byte.valueOf((byte) 0x01), operation.getData());
        Assert.assertEquals(Long.valueOf(2000), operation.getDuration());
        Assert.assertEquals(0, operation.getId());
        Assert.assertEquals(pin, operation.getPin());

        operation.setId(2);
        Assert.assertEquals(2, operation.getId());
    }

    @Test
    public void equals() {
        Pin pin1 = new Pin(1, Pin.Type.INPUT);
        Operation operation1 = new Operation("operation", Type.READ, Byte.valueOf((byte) 0x01), Long.valueOf(2000), pin1);
        operation1.setId(2);

        Pin pin2 = new Pin(1, Pin.Type.INPUT);
        Operation operation2 = new Operation("operation", Type.READ, Byte.valueOf((byte) 0x01), Long.valueOf(2000), pin2);
        operation2.setId(2);
        
        Operation operation3 = new Operation(null, Type.READ, Byte.valueOf((byte) 0x01), Long.valueOf(2000), pin1);
        operation3.setId(1);
        
        Operation operation4 = new Operation("otherOperation", Type.READ, Byte.valueOf((byte) 0x01), Long.valueOf(2000), pin1);
        operation4.setId(1);
 
        Operation operation5 = new Operation("operation", Type.READ, Byte.valueOf((byte) 0x01), Long.valueOf(2000), null);
        operation5.setId(1);
    	
        Pin pin3 = new Pin(3, Pin.Type.OUTPUT);
        Operation operation6 = new Operation("operation", Type.READ, Byte.valueOf((byte) 0x01), Long.valueOf(2000), pin3);
        operation6.setId(1);
        
        Operation operation7 = new Operation("operation", Type.WRITE, Byte.valueOf((byte) 0x01), Long.valueOf(2000), pin1);
        operation7.setId(1);
        
        
        Assert.assertTrue(operation1.equals(operation2));
        Assert.assertTrue(operation2.equals(operation1));

        operation1.setId(1);
        Assert.assertFalse(operation1.equals(operation2));
        Assert.assertFalse(operation2.equals(operation1));

        Assert.assertTrue(operation1.equals(operation1));
        Assert.assertFalse(operation1.equals(null));
        Assert.assertFalse(operation1.equals(""));
         
        Assert.assertFalse(new Operation("operation", Type.READ, null, Long.valueOf(2000), pin2).equals(operation2));
        Assert.assertFalse(new Operation("operation", Type.READ, Byte.valueOf((byte) 0x02), Long.valueOf(2000), pin2).equals(operation2));  
        Assert.assertFalse(new Operation("operation", Type.READ, Byte.valueOf((byte) 0x01), null, pin2).equals(operation2));
        Assert.assertFalse(new Operation("operation", Type.READ, Byte.valueOf((byte) 0x01), Long.valueOf(3000), pin2).equals(operation2));
     
        Assert.assertFalse(operation3.equals(operation1));
        Assert.assertFalse(operation4.equals(operation1)); 
        Assert.assertFalse(operation5.equals(operation1)); 
        Assert.assertFalse(operation6.equals(operation1));
        Assert.assertFalse(operation7.equals(operation1));
    }

    @Test
    public void hashCodeTest() {
        Pin pin1 = new Pin(1, Pin.Type.INPUT);
        Operation operation1 = new Operation("operation", Type.READ, Byte.valueOf((byte) 0x01), Long.valueOf(2000), pin1);
        operation1.setId(2);

        Pin pin2 = new Pin(1, Pin.Type.INPUT);
        Operation operation2 = new Operation("operation", Type.READ, Byte.valueOf((byte) 0x01), Long.valueOf(2000), pin2);
        operation2.setId(2);
        
        Operation operation3 = new Operation(null,null,null,null,null);

        Assert.assertEquals(operation1.hashCode(), operation2.hashCode());

        operation1.setId(1);
        Assert.assertNotEquals(operation1.hashCode(), operation2.hashCode());
        
        Assert.assertEquals(887503681, operation3.hashCode());

    }
}
