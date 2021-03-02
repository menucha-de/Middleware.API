package havis.middleware.ale.base.operation.port.result;

import havis.middleware.ale.base.operation.port.result.Result.State;

import org.junit.Assert;
import org.junit.Test;

public class ReadResultTest {

    @Test
    public void readResult() {
        ReadResult result = new ReadResult(State.PORT_NOT_FOUND_ERROR);
        Assert.assertEquals(State.PORT_NOT_FOUND_ERROR, result.getState());
        Assert.assertEquals((byte) 0x00, result.getData());
    }

    @Test
    public void readResultData() {
        ReadResult result = new ReadResult(State.OP_NOT_POSSIBLE_ERROR, (byte) 0x01);
        Assert.assertEquals(State.OP_NOT_POSSIBLE_ERROR, result.getState());
        Assert.assertEquals((byte) 0x01, result.getData());
    }

    @Test
    public void equals() {
        ReadResult result1 = new ReadResult(State.OP_NOT_POSSIBLE_ERROR, (byte) 0x01);
        ReadResult result1_1 = new ReadResult(State.OP_NOT_POSSIBLE_ERROR, (byte) 0x01);
        ReadResult result2 = new ReadResult(State.OP_NOT_POSSIBLE_ERROR, (byte) 0x02);
        ReadResult result3 = new ReadResult(State.SUCCESS, (byte) 0x01);
        
        Assert.assertTrue(result1.equals(result1));
        Assert.assertTrue(result1.equals(result1_1));
        Assert.assertTrue(result1_1.equals(result1));
        Assert.assertFalse(result1.equals(null));
        Assert.assertFalse(result1.equals(""));
        Assert.assertFalse(result1.equals(result2));
        Assert.assertFalse(result2.equals(result1));
        Assert.assertFalse(result1.equals(result3));
        Assert.assertFalse(result3.equals(result1));
        Assert.assertFalse(result1.equals(new WriteResult(State.OP_NOT_POSSIBLE_ERROR)));
        
        
    }

    @Test
    public void hashCodeTest() {
        ReadResult result1 = new ReadResult(State.OP_NOT_POSSIBLE_ERROR, (byte) 0x01);
        ReadResult result1_1 = new ReadResult(State.OP_NOT_POSSIBLE_ERROR, (byte) 0x01);
        ReadResult result2 = new ReadResult(State.OP_NOT_POSSIBLE_ERROR, (byte) 0x02);
        ReadResult result3 = new ReadResult(State.SUCCESS, (byte) 0x01);
        
        Assert.assertEquals(result1.hashCode(), result1_1.hashCode());
        Assert.assertNotEquals(result1.hashCode(), result2.hashCode());
        Assert.assertNotEquals(result1.hashCode(), result3.hashCode());
    }
    
    @Test
    public void toStringTest(){
    	ReadResult result = new ReadResult(State.OP_NOT_POSSIBLE_ERROR, (byte) 0x01);
    	
    	Assert.assertEquals("ReadResult [data=1, state()=OP_NOT_POSSIBLE_ERROR]", result.toString());
    }
}
