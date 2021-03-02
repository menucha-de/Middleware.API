package havis.middleware.ale.base.operation.tag;

import org.junit.Assert;
import org.junit.Test;

public class FieldTest {

    @Test
    public void field() {
        Field field = new Field("test", 3, 16, 8);
        Assert.assertEquals(3, field.getBank());
        Assert.assertEquals(8, field.getLength());
        Assert.assertEquals(16, field.getOffset());
        Assert.assertEquals("test", field.getName());
        field.setName("tset");
        field.setBank(1);
        field.setLength(16);
        field.setOffset(8);
        Assert.assertEquals(1, field.getBank());
        Assert.assertEquals(16, field.getLength());
        Assert.assertEquals(8, field.getOffset());
        Assert.assertEquals("tset", field.getName());
    }

    @Test
    public void equalsField() {
        Field actual = new Field("test", 3, 16, 8);
        Field expected = new Field("test", 3, 16, 8);
        Assert.assertTrue(actual.equals(expected));
        actual.setBank(1);
        Assert.assertFalse(actual.equals(expected));
        actual.setBank(3);
        actual.setName("tset");
        Assert.assertFalse(actual.equals(expected));
        actual.setName("test");
        actual.setOffset(8);
        Assert.assertFalse(actual.equals(expected));
        actual.setOffset(16);
        actual.setLength(16);
        Assert.assertFalse(actual.equals(expected));
    }

    @Test
    public void equalsObj() {
        Field actual = new Field("test", 3, 16, 8);
        
        Assert.assertFalse(actual.equals(""));
        Assert.assertTrue(actual.equals(new Field("test", 3, 16, 8)));
        Assert.assertFalse(actual.equals(new Field("test", 3, 16, 16)));
    }

    @Test
    public void hashCodeTest() {
        Field actual = new Field("test", 3, 16, 8);
        Assert.assertEquals(3777365, actual.hashCode());

        actual.setBank(1);
        Assert.assertEquals(3639523, actual.hashCode());
        actual.setOffset(2);
        Assert.assertEquals(3638949, actual.hashCode());
        actual.setLength(3);
        Assert.assertEquals(3630544, actual.hashCode());

        Field testField = new Field("test", 1, 2, 3);
        Assert.assertEquals(testField.hashCode(), actual.hashCode());
        
        Field field = new Field(null,1,2,3);
        Assert.assertNotEquals(actual, field);
    }
}
