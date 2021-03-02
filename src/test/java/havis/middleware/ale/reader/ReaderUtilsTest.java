package havis.middleware.ale.reader;

import havis.middleware.ale.base.operation.tag.Field;
import havis.middleware.ale.base.operation.tag.Operation;
import havis.middleware.ale.base.operation.tag.OperationType;
import havis.middleware.ale.base.operation.tag.result.ReadResult;
import havis.middleware.ale.base.operation.tag.result.Result;
import havis.middleware.ale.base.operation.tag.result.ResultState;

import java.util.Arrays;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class ReaderUtilsTest {

	@Test
	public void toResultOperationArrayResultArray(){
		Operation operation1 = new Operation(1,OperationType.READ,new Field("field1", 0, 0, 8));
		Operation operation2 = new Operation(2,OperationType.WRITE,new Field("field2", 1, 16, 8));
		Operation operation3 = new Operation(3,OperationType.WRITE,new Field("field3", 2, 16, 8));
		Operation[] operations  = new Operation[]{operation1, operation2,operation3};
		ReadResult readResult1 = new ReadResult(ResultState.SUCCESS, new byte[]{0x01});
		ReadResult readResult2 = new ReadResult(ResultState.SUCCESS, new byte[]{0x02});
		ReadResult readResult3 = new ReadResult(ResultState.MISC_ERROR_TOTAL, new byte[]{0x01});
		ReadResult[] results = new ReadResult[]{readResult1,readResult2,readResult3};
	
		Map<Integer, Result> result = ReaderUtils.toResult(results, operations);

		Assert.assertEquals(readResult1, result.get(Integer.valueOf(1)));
		Assert.assertEquals(new ReadResult(ResultState.FIELD_NOT_FOUND_ERROR,null), result.get(Integer.valueOf(2)));
		Assert.assertEquals(new ReadResult(ResultState.MISC_ERROR_TOTAL,null), result.get(Integer.valueOf(3)));
	}
	
	
    @Test
    public void toResultOperationResult() {
        // [11111111] -> 11111111
        toResult(ResultState.SUCCESS, new Field("field", 0, 0, 8), new byte[] { (byte) 0xFF }, ResultState.SUCCESS, new byte[] { (byte) 0xFF });
        // 10101010 [10111011] - 10111011
        toResult(ResultState.SUCCESS, new Field("field", 0, 8, 8), new byte[] { (byte) 0xAA, (byte) 0xBB }, ResultState.SUCCESS, new byte[] { (byte) 0xBB });
        // 10101010 1[0111011 1]1001100 -> 01110111
        toResult(ResultState.SUCCESS, new Field("field", 0, 9, 8), new byte[] { (byte) 0xAA, (byte) 0xBB, (byte) 0xCC }, ResultState.SUCCESS, new byte[] { (byte) 0x77 });
        // 1010[1010 10111011 1100]1100 -> 10101011 10111100
        toResult(ResultState.SUCCESS, new Field("field", 0, 4, 16), new byte[] { (byte) 0xAA, (byte) 0xBB, (byte) 0xCC }, ResultState.SUCCESS, new byte[] { (byte) 0xAB, (byte) 0xBC });
        // [1010101]0 -> 10101010
        toResult(ResultState.SUCCESS, new Field("field", 0, 0, 7), new byte[] { (byte) 0xAA }, ResultState.SUCCESS, new byte[] { (byte) 0xAA });
        // 1[0101010] -> 01010100
        toResult(ResultState.SUCCESS, new Field("field", 0, 1, 7), new byte[] { (byte) 0xAA }, ResultState.SUCCESS, new byte[] { (byte) 0x54 });
        toResult(ResultState.MISC_ERROR_TOTAL, new Field("field", 0, 0, 8), new byte[] { (byte) 0xFF }, ResultState.MISC_ERROR_TOTAL, new byte[0]);
        toResult(ResultState.SUCCESS,new Field("test",0,0,0),new byte[] { (byte) 0xFF },ResultState.SUCCESS,new byte[] { (byte) 0xFF });   
    }

    private void toResult(ResultState resultState, Field field, byte[] data, ResultState expectedResultState, byte[] expectedData) {
        ReadResult result = new ReadResult(resultState, data);
        Operation operation = new Operation();
        operation.setField(field);
        Result actual = ReaderUtils.toResult(result, operation);
        Assert.assertTrue(actual instanceof ReadResult);
        Assert.assertEquals(expectedResultState, actual.getState());
        Assert.assertTrue(Arrays.equals(expectedData, ((ReadResult) actual).getData()));
    }
}
