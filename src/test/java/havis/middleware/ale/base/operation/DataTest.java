package havis.middleware.ale.base.operation;

import org.junit.Assert;
import org.junit.Test;

public class DataTest {

    @Test
    public void data() {
        Data data = new Data();
        Assert.assertFalse(data.isCompleted());
        data.setCompleted(true);
        Assert.assertTrue(data.isCompleted());
        data.setCompleted(false);
        Assert.assertFalse(data.isCompleted());
        Assert.assertNull(data.getUri());
        data.setUri("test");
        Assert.assertEquals("test", data.getUri());

        Data data2 = new Data();
        data2.setCompleted(false);
        data2.setUri("test");
        Assert.assertTrue(data.equals(data));
        Assert.assertFalse(data.equals(null));
        Assert.assertFalse(data.equals(""));
        Assert.assertTrue(data.equals(data2));
        Assert.assertTrue(data2.equals(data));
        Assert.assertEquals(data.hashCode(), data2.hashCode());
        data2.setUri("whatever");
        Assert.assertFalse(data.equals(data2));
        Assert.assertFalse(data2.equals(data));
        Assert.assertNotEquals(data.hashCode(), data2.hashCode());
        data2.setUri(null);
        Assert.assertFalse(data.equals(data2));
        Assert.assertFalse(data2.equals(data));
        Assert.assertNotEquals(data.hashCode(), data2.hashCode());
    }

    @Test
    public void equals() {
        Data data = new Data();
        data.setUri("test");
        Data data2 = new Data();
        data2.setUri("test");
        Assert.assertTrue(data.equals(data));
        Assert.assertFalse(data.equals(null));
        Assert.assertFalse(data.equals(""));
        Assert.assertTrue(data.equals(data2));
        Assert.assertTrue(data2.equals(data));
        data2.setUri("whatever");
        Assert.assertFalse(data.equals(data2));
        Assert.assertFalse(data2.equals(data));
        data2.setUri(null);
        Assert.assertFalse(data.equals(data2));
        Assert.assertFalse(data2.equals(data));
    }

    @Test
    public void hashCodeTest() {
        Data data = new Data();
        data.setUri("test");
        Data data2 = new Data();
        data2.setUri("test");
        Assert.assertEquals(data.hashCode(), data2.hashCode());
        data2.setUri("whatever");
        Assert.assertNotEquals(data.hashCode(), data2.hashCode());
        data2.setUri(null);
        Assert.assertNotEquals(data.hashCode(), data2.hashCode());
    }
}
