package havis.middleware.ale.base.operation.tag;

import org.junit.Assert;
import org.junit.Test;

public class OperationTest {

	@Test
	public void operationWithId() {
		Operation op = new Operation(1);
		Assert.assertEquals(op.getId(), new Operation(1).getId());
		Assert.assertEquals(op.getId(), 1);
	}

	@Test
	public void operationWithoutBitLength() {
		Field field = new Field("test", 3, 16, 8);
		Operation op = new Operation(1, OperationType.READ, field, new byte[] { 0x01, 0x02 });
		Assert.assertArrayEquals(new byte[] { 0x01, 0x02 }, op.getData());
		Assert.assertEquals(16, op.getBitLength());
		Assert.assertEquals(OperationType.READ, op.getType());
		Assert.assertEquals(new Field("test", 3, 16, 8), op.getField());
	}

	@Test
	public void operationWithoutFieldAndBitLength() {
		Operation op = new Operation(1, OperationType.READ, new byte[] { 0x03, 0x04, 0x05 });
		Assert.assertArrayEquals(new byte[] { 0x03, 0x04, 0x05 }, op.getData());
		Assert.assertEquals(24, op.getBitLength());
		Assert.assertEquals(OperationType.READ, op.getType());
	}

	@Test
	public void operationWithoutDataAndBitLength() {
		Field field = new Field("test", 3, 16, 8);
		Operation op = new Operation(1, OperationType.READ, field);
		Assert.assertEquals(OperationType.READ, op.getType());
		Assert.assertEquals(new Field("test", 3, 16, 8), op.getField());
	}

	@Test
	public void operationFull() {
		Field field = new Field("test", 3, 16, 8);
		Operation op = new Operation(1, OperationType.READ, field, new byte[] { 0x06, 0x07 }, 9);
		Assert.assertArrayEquals(new byte[] { 0x06, 0x07 }, op.getData());
		Assert.assertEquals(9, op.getBitLength());
		Assert.assertEquals(OperationType.READ, op.getType());
		Assert.assertEquals(new Field("test", 3, 16, 8), op.getField());
	}

	@Test
	public void operationWwithoutField() {
		Operation op = new Operation(1, OperationType.READ, new byte[] { 0x08, 0x09 }, 11);
		Assert.assertArrayEquals(new byte[] { 0x08, 0x09 }, op.getData());
		Assert.assertEquals(11, op.getBitLength());
		Assert.assertEquals(OperationType.READ, op.getType());
	}
	
	@Test 
	public void operationWithoutData(){
		Field field = new Field("test", 3, 16, 8);
		Operation op1 = new Operation(1, OperationType.READ, field, null);
		Operation op2 = new Operation(1, OperationType.READ, (byte[])null);
		
		Assert.assertEquals(0,op1.getBitLength());
		Assert.assertEquals(0,op2.getBitLength());
	}

	@Test
	public void setter() {
		Operation op = new Operation();
		op.setData(new byte[] { 0x01 });
		Assert.assertArrayEquals(new byte[] { 0x01 }, op.getData());

		Assert.assertEquals(0, op.getBitLength());
		op.setBitLength(7);
		Assert.assertEquals(7, op.getBitLength());

		op.setField(new Field("test", 3, 16, 8));
		Assert.assertEquals(new Field("test", 3, 16, 8), op.getField());

		op.setType(OperationType.READ);
		Assert.assertEquals(OperationType.READ, op.getType());

		op.setType(OperationType.WRITE);
		Assert.assertEquals(OperationType.WRITE, op.getType());

		op.setType(OperationType.KILL);
		Assert.assertEquals(OperationType.KILL, op.getType());

		op.setType(OperationType.LOCK);
		Assert.assertEquals(OperationType.LOCK, op.getType());

		op.setType(OperationType.PASSWORD);
		Assert.assertEquals(OperationType.PASSWORD, op.getType());

		op.setType(OperationType.CUSTOM);
		Assert.assertEquals(OperationType.CUSTOM, op.getType());

		Assert.assertEquals(0, op.getId());
		op.setId(2);
		Assert.assertEquals(2, op.getId());
	}
	
	@Test
	public void hashCodetest(){
		Field field = new Field("test", 3, 16, 8);
		Operation op1 = new Operation(1, OperationType.READ, field, new byte[] { 0x01, 0x02 });
		Operation op2 = new Operation(2, null, field, new byte[] { 0x01, 0x02 });
		Operation op3 = new Operation(1, OperationType.READ, null, new byte[] { 0x01, 0x02 });
		
		Assert.assertNotEquals(op1.hashCode(),op2.hashCode());
		Assert.assertNotEquals(op1.hashCode(),op3.hashCode());
	}
	
	@Test
	public void equals(){
		Field field1 = new Field("test", 3, 16, 8); 
		Field field2 = new Field("field", 4, 17, 9); 
		Operation op1 = new Operation(1, OperationType.READ, field1, new byte[] { 0x01, 0x02 });
		Operation op1_1 = new Operation(1, OperationType.READ, field1, new byte[] { 0x01, 0x02 });
		Operation op2 = new Operation(2, OperationType.READ, field1, new byte[] { 0x01, 0x02 });
		Operation op3 = new Operation(1, OperationType.WRITE, field1, new byte[] { 0x01, 0x02 });
		Operation op4 = new Operation(1, OperationType.READ, null, new byte[] { 0x01, 0x02 });
		Operation op5 = new Operation(1, OperationType.READ, field2, new byte[] { 0x01, 0x02 });
		Operation op6 = new Operation(1, OperationType.READ, field1, new byte[] { 0x01, 0x02, 0x03});
		Operation op7 = new Operation(1, OperationType.READ, field1, new byte[] { 0x02, 0x03});
	
		Assert.assertTrue(op1.equals(op1));
		Assert.assertFalse(op1.equals(null));
		Assert.assertFalse(op1.equals(""));
		
		Assert.assertFalse(op1.equals(op2));
		Assert.assertFalse(op1.equals(op3));
		Assert.assertFalse(op4.equals(op1));
		Assert.assertFalse(op1.equals(op5));
		Assert.assertFalse(op1.equals(op6));
		Assert.assertFalse(op1.equals(op7));
		
		Assert.assertTrue(op1.equals(op1_1));	
	}
}
