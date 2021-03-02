package havis.middleware.ale.base.operation.port.result;

import havis.middleware.ale.base.operation.port.result.Result.State;

import org.junit.Assert;
import org.junit.Test;

public class ResultTest {

    @Test
    public void result() {
        Result result1 = new Result(State.MISC_ERROR_PARTIAL);
        Result result2 = new Result(State.MISC_ERROR_TOTAL);
        Result result3 = new Result(State.OP_NOT_POSSIBLE_ERROR);
        Result result4 = new Result(State.PORT_NOT_FOUND_ERROR);
        Result result5 = new Result(State.SUCCESS);

        Assert.assertEquals(State.MISC_ERROR_PARTIAL, result1.getState());
        Assert.assertEquals(State.MISC_ERROR_TOTAL, result2.getState());
        Assert.assertEquals(State.OP_NOT_POSSIBLE_ERROR, result3.getState());
        Assert.assertEquals(State.PORT_NOT_FOUND_ERROR, result4.getState());
        Assert.assertEquals(State.SUCCESS, result5.getState());
    }

    @Test
    public void equals() {
        Result result1 = new Result(State.MISC_ERROR_PARTIAL);
        Result result1_1 = new Result(State.MISC_ERROR_PARTIAL);
        Result result2 = new Result(State.MISC_ERROR_TOTAL);
        Result result3 = new Result(State.OP_NOT_POSSIBLE_ERROR);
        Result result4 = new Result(State.PORT_NOT_FOUND_ERROR);
        Result result5 = new Result(State.SUCCESS);

        Assert.assertTrue(result1.equals(result1_1));
        Assert.assertTrue(result1_1.equals(result1));
        Assert.assertTrue(result1.equals(result1));
        Assert.assertFalse(result1.equals(null));
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
        Result result1 = new Result(State.MISC_ERROR_PARTIAL);
        Result result1_1 = new Result(State.MISC_ERROR_PARTIAL);
        Result result2 = new Result(State.MISC_ERROR_TOTAL);
        Result result3 = new Result(State.OP_NOT_POSSIBLE_ERROR);
        Result result4 = new Result(State.PORT_NOT_FOUND_ERROR);
        Result result5 = new Result(State.SUCCESS);
        Result result6 = new Result(null);

        Assert.assertEquals(result1.hashCode(), result1_1.hashCode());
        Assert.assertNotEquals(result1.hashCode(), result2.hashCode());
        Assert.assertNotEquals(result1.hashCode(), result3.hashCode());
        Assert.assertNotEquals(result1.hashCode(), result4.hashCode());
        Assert.assertNotEquals(result1.hashCode(), result5.hashCode());
        Assert.assertNotEquals(result1.hashCode(), result6.hashCode());
    }
    
    @Test
    public void toStringTest(){
    	 Result result = new Result(State.SUCCESS);
    	 
    	 Assert.assertEquals("Result [state=SUCCESS]", result.toString());
    }
}
