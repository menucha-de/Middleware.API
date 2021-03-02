package havis.middleware.ale.base.operation.port.result;

import havis.middleware.ale.base.operation.port.result.Result.State;

import org.junit.Assert;
import org.junit.Test;

public class WriteResultTest {
    @Test
    public void result() {
        WriteResult result1 = new WriteResult(State.MISC_ERROR_PARTIAL);
        WriteResult result2 = new WriteResult(State.MISC_ERROR_TOTAL);
        WriteResult result3 = new WriteResult(State.OP_NOT_POSSIBLE_ERROR);
        WriteResult result4 = new WriteResult(State.PORT_NOT_FOUND_ERROR);
        WriteResult result5 = new WriteResult(State.SUCCESS);

        Assert.assertEquals(State.MISC_ERROR_PARTIAL, result1.getState());
        Assert.assertEquals(State.MISC_ERROR_TOTAL, result2.getState());
        Assert.assertEquals(State.OP_NOT_POSSIBLE_ERROR, result3.getState());
        Assert.assertEquals(State.PORT_NOT_FOUND_ERROR, result4.getState());
        Assert.assertEquals(State.SUCCESS, result5.getState());
    }

    @Test
    public void equals() {
        WriteResult result1 = new WriteResult(State.MISC_ERROR_PARTIAL);
        WriteResult result1_1 = new WriteResult(State.MISC_ERROR_PARTIAL);
        WriteResult result2 = new WriteResult(State.MISC_ERROR_TOTAL);
        WriteResult result3 = new WriteResult(State.OP_NOT_POSSIBLE_ERROR);
        WriteResult result4 = new WriteResult(State.PORT_NOT_FOUND_ERROR);
        WriteResult result5 = new WriteResult(State.SUCCESS);

        Assert.assertTrue(result1.equals(result1_1));
        Assert.assertTrue(result1_1.equals(result1));
        Assert.assertTrue(result1.equals(result1));
        Assert.assertFalse(result1.equals(null));
        Assert.assertFalse(result1.equals(new Result(State.MISC_ERROR_PARTIAL)));
        Assert.assertFalse(result1.equals(""));
        Assert.assertFalse(result1.equals(result2));
        Assert.assertFalse(result2.equals(result1));
        Assert.assertFalse(result1.equals(result3));
        Assert.assertFalse(result3.equals(result1));
        Assert.assertFalse(result1.equals(result4));
        Assert.assertFalse(result4.equals(result1));
        Assert.assertFalse(result1.equals(result5));
        Assert.assertFalse(result5.equals(result1));
    }

    @Test
    public void hashCodeTest() {
        WriteResult result1 = new WriteResult(State.MISC_ERROR_PARTIAL);
        WriteResult result1_1 = new WriteResult(State.MISC_ERROR_PARTIAL);
        WriteResult result2 = new WriteResult(State.MISC_ERROR_TOTAL);
        WriteResult result3 = new WriteResult(State.OP_NOT_POSSIBLE_ERROR);
        WriteResult result4 = new WriteResult(State.PORT_NOT_FOUND_ERROR);
        WriteResult result5 = new WriteResult(State.SUCCESS);

        Assert.assertEquals(result1.hashCode(), result1_1.hashCode());
        Assert.assertNotEquals(result1.hashCode(), result2.hashCode());
        Assert.assertNotEquals(result1.hashCode(), result3.hashCode());
        Assert.assertNotEquals(result1.hashCode(), result4.hashCode());
        Assert.assertNotEquals(result1.hashCode(), result5.hashCode());
    }
    
    @Test 
    public void toStringTest(){
    	WriteResult result = new WriteResult(State.SUCCESS);
    	
    	Assert.assertEquals("WriteResult [state()=SUCCESS]", result.toString());
    }
}
