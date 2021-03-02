package havis.middleware.ale.base.operation.tag.result;

import org.junit.Assert;
import org.junit.Test;

public class WriteResultTest {

    @Test
    public void writeResult() {
        WriteResult result = new WriteResult();
        Assert.assertNull(result.getState());
        Assert.assertEquals(0, result.getNumWordsWritten());

        result.setState(ResultState.MISC_ERROR_TOTAL);
        Assert.assertEquals(ResultState.MISC_ERROR_TOTAL, result.getState());

        result.setNumWordsWritten(25);
        Assert.assertEquals(25, result.getNumWordsWritten());
    }

    @Test
    public void writeResultState() {
        WriteResult result = new WriteResult(ResultState.SUCCESS);
        Assert.assertEquals(ResultState.SUCCESS, result.getState());
        Assert.assertEquals(0, result.getNumWordsWritten());
    }

    @Test
    public void writeResultStateAndNumWordsWritten() {
        WriteResult result = new WriteResult(ResultState.ASSOCIATION_TABLE_VALUE_MISSING, 17);
        Assert.assertEquals(ResultState.ASSOCIATION_TABLE_VALUE_MISSING, result.getState());
        Assert.assertEquals(17, result.getNumWordsWritten());
    }

    @Test
    public void hashCodeTest(){
    	 WriteResult writeResult1 = new WriteResult(ResultState.ASSOCIATION_TABLE_VALUE_MISSING, 17);
    	 WriteResult writeResult2 = new WriteResult(ResultState.ASSOCIATION_TABLE_VALUE_MISSING, 20);
    	 
    	 Assert.assertNotEquals(writeResult1.hashCode(), writeResult2.hashCode());
    }
    
    @Test 
    public void equals(){
    	WriteResult writeResult1 = new WriteResult(ResultState.ASSOCIATION_TABLE_VALUE_MISSING, 17);
    	WriteResult writeResult1_1 = new WriteResult(ResultState.ASSOCIATION_TABLE_VALUE_MISSING, 17);
   	    WriteResult writeResult2 = new WriteResult(ResultState.ASSOCIATION_TABLE_VALUE_MISSING, 20);
    	
   	    Assert.assertTrue(writeResult1.equals(writeResult1));
   	    Assert.assertFalse(writeResult1.equals(null));
   	    Assert.assertFalse(writeResult1.equals(new KillResult(ResultState.ASSOCIATION_TABLE_VALUE_MISSING)));
   	    Assert.assertFalse(writeResult1.equals(writeResult2));
   	    Assert.assertTrue(writeResult1.equals(writeResult1_1));
    }
    
    @Test 
    public void toStringTest(){
    	WriteResult writeResult1 = new WriteResult(ResultState.ASSOCIATION_TABLE_VALUE_MISSING, 17);
    
    	Assert.assertEquals("WriteResult [numWordsWritten=17, state=ASSOCIATION_TABLE_VALUE_MISSING]", writeResult1.toString());
    }
}
