package havis.middleware.ale.base.operation.tag;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TagOperationTest {

    @Test
    public void tagOperation() {
        Field field = new Field("test", 3, 16, 8);
        Operation op = new Operation(1, OperationType.LOCK, field, new byte[] {});
        List<Operation> operations = Arrays.asList(op);
        TagOperation tagO = new TagOperation(operations);

        Filter filter = new Filter(3, 8, 16, new byte[] {});
        List<Filter> filters = Arrays.asList(filter);
        TagOperation tagO2 = new TagOperation(operations, filters);
        tagO2.setOperations(operations);
        tagO.setFilter(filters);
        Assert.assertEquals(filters.size(), tagO.getFilter().size());
        for (int i = 0; i < filters.size(); i++)
            Assert.assertEquals(filters.get(i), tagO.getFilter().get(i));
        Assert.assertEquals(filters.size(), tagO2.getFilter().size());
        for (int i = 0; i < filters.size(); i++)
            Assert.assertEquals(filters.get(i), tagO2.getFilter().get(i));
        Assert.assertEquals(op, tagO.getOperations().get(0));
        Assert.assertEquals(op, tagO2.getOperations().get(0));
    }
    
    @Test
    public void hashCodeTest(){
    	Field field = new Field("test", 3, 16, 8);
    	Operation op = new Operation(1, OperationType.LOCK, field, new byte[] {});
    	List<Operation> operations = Arrays.asList(op);
    	Filter filter = new Filter(3, 8, 16, new byte[] {});
        List<Filter> filters = Arrays.asList(filter);
       
        TagOperation tag1 = new TagOperation(operations, filters);
        TagOperation tag2 = new TagOperation(null, filters);
        TagOperation tag3 = new TagOperation(operations, null);
    	
    	Assert.assertNotEquals(tag1.hashCode(), tag2.hashCode());
    	Assert.assertNotEquals(tag1.hashCode(), tag3.hashCode());
    }
    
    @Test
    public void equals(){
    	Field field = new Field("test", 3, 16, 8);
    	Operation op1 = new Operation(1, OperationType.LOCK, field, new byte[] {});
    	Operation op2 = new Operation(2, OperationType.LOCK, field, new byte[] {});
    	List<Operation> operations1 = Arrays.asList(op1);
    	List<Operation> operations2 = Arrays.asList(op2);
    	Filter filter1 = new Filter(3, 8, 16, new byte[] {});
    	Filter filter2 = new Filter(4, 9, 17, new byte[] {});
        List<Filter> filters1 = Arrays.asList(filter1);
        List<Filter> filters2 = Arrays.asList(filter2);
       
        TagOperation tag1 = new TagOperation(operations1, filters1);
        TagOperation tag1_1 = new TagOperation(operations1, filters1);
        TagOperation tag2 = new TagOperation(operations2, filters1);
        TagOperation tag3 = new TagOperation(null, filters1);
        TagOperation tag4 = new TagOperation(operations1, filters2);
        TagOperation tag5 = new TagOperation(operations1, null);
        
        Assert.assertTrue(tag1.equals(tag1));
        Assert.assertFalse(tag1.equals(null));
        Assert.assertFalse(tag1.equals(""));
        
        Assert.assertFalse(tag1.equals(tag2));
        Assert.assertFalse(tag1.equals(tag2));
        Assert.assertFalse(tag3.equals(tag1));
        Assert.assertFalse(tag1.equals(tag4));
        Assert.assertFalse(tag5.equals(tag1));
        
        Assert.assertTrue(tag1.equals(tag1_1));   
    }
}
