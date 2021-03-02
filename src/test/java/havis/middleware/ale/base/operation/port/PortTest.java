package havis.middleware.ale.base.operation.port;

import havis.middleware.ale.base.operation.Event;
import havis.middleware.ale.base.operation.port.Pin.Type;
import havis.middleware.ale.base.operation.port.result.Result;
import havis.middleware.ale.base.operation.port.result.Result.State;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class PortTest {
    @Test
    public void portConstructor() {
        Port actual = new Port();
        actual.setCompleted(true);
        Assert.assertTrue(actual.isCompleted());
        actual.setCompleted(false);
        Assert.assertFalse(actual.isCompleted());
        actual.setUri("test");
        Assert.assertEquals("test", actual.getUri());

        Map<Integer, Result> map1 = new HashMap<Integer, Result>();
        map1.put(Integer.valueOf(1), new Result(State.SUCCESS));
        Port actual1 = new Port(new Pin(1, Type.INPUT), "somename", map1);
        Assert.assertEquals(new Pin(1, Type.INPUT), actual1.getPin());
        Assert.assertEquals("somename", actual1.getName());
        Assert.assertEquals(1, actual1.getResult().size());
        Assert.assertEquals(new Result(State.SUCCESS), actual1.getResult().get(Integer.valueOf(1)));

        Map<Integer, Result> map2 = new HashMap<Integer, Result>();
        map2.put(Integer.valueOf(1), new Result(State.PORT_NOT_FOUND_ERROR));
        map2.put(Integer.valueOf(2), new Result(State.MISC_ERROR_PARTIAL));
        Port actual2 = new Port(new Pin(1, Type.OUTPUT), "somename", map2);
        Assert.assertEquals(new Pin(1, Type.OUTPUT), actual2.getPin());
        Assert.assertEquals("somename", actual2.getName());
        Assert.assertEquals(2, actual2.getResult().size());
        Assert.assertEquals(new Result(State.PORT_NOT_FOUND_ERROR), actual2.getResult().get(Integer.valueOf(1)));
        Assert.assertEquals(new Result(State.MISC_ERROR_PARTIAL), actual2.getResult().get(Integer.valueOf(2)));

        Map<Integer, Result> map3 = new HashMap<Integer, Result>();
        map3.put(Integer.valueOf(1), new Result(State.MISC_ERROR_TOTAL));
        Port actual3 = new Port(new Pin(1, Type.OUTPUT), null, map3);
        Assert.assertEquals(new Pin(1, Type.OUTPUT), actual3.getPin());
        Assert.assertNull(actual3.getName());
        Assert.assertEquals(new Result(State.MISC_ERROR_TOTAL), actual3.getResult().get(Integer.valueOf(1)));

        Map<Integer, Result> map4 = new HashMap<Integer, Result>();
        map4.put(Integer.valueOf(1), new Result(State.PORT_NOT_FOUND_ERROR));
        Port actual4 = new Port(map4);
        Assert.assertNull(actual4.getPin());
        Assert.assertNull(actual4.getName());
        Assert.assertEquals(new Result(State.PORT_NOT_FOUND_ERROR), actual4.getResult().get(Integer.valueOf(1)));
    }

    @Test
    public void toStringTest() {
        Result result1 = new Result(State.SUCCESS);
        Map<Integer, Result> map1 = new HashMap<Integer, Result>();
        map1.put(Integer.valueOf(1), result1);
        Port actual1 = new Port(new Pin(1, Type.INPUT), "somename", map1);
        Assert.assertEquals("urn:havis:ale:port:somename.in.1", actual1.toString());

        Result result2 = new Result(State.PORT_NOT_FOUND_ERROR);
        Map<Integer, Result> map2 = new HashMap<Integer, Result>();
        map2.put(Integer.valueOf(1), result2);
        Port actual2 = new Port(new Pin(1, Type.OUTPUT), "somename", map2);
        Assert.assertEquals("urn:havis:ale:port:somename.out.1", actual2.toString());

        Result result3 = new Result(State.MISC_ERROR_TOTAL);
        Map<Integer, Result> map3 = new HashMap<Integer, Result>();
        map3.put(Integer.valueOf(1), result3);
        Port actual3 = new Port(new Pin(1, Type.OUTPUT), null, map3);
        Assert.assertEquals("urn:havis:ale:port:.out.1", actual3.toString());

        Port nulled = new Port();
        Assert.assertEquals(null, nulled.toString());
    }

    @Test
    public void port() {
        Result result = new Result(State.SUCCESS);
        Map<Integer, Result> map = new HashMap<Integer, Result>();
        map.put(Integer.valueOf(1), result);
        Port port = new Port(new Pin(1, Type.INPUT), "somename", map);
        havis.middleware.ale.base.Port converted = port.port();
        Assert.assertEquals("somename", converted.getReader());
        Assert.assertEquals(havis.middleware.ale.base.IO.INPUT, converted.getPin().getType());
        Assert.assertEquals(1, converted.getPin().getId());

        port = new Port(map);
        converted = port.port();
        Assert.assertNull(converted.getReader());
        Assert.assertNull(converted.getPin());
        
        Port port2 = new Port(new Pin(1, Type.OUTPUT), "somename", map);
        converted = port2.port();
        Assert.assertEquals(havis.middleware.ale.base.IO.OUTPUT,converted.getPin().getType());
        
    }

    @Test
    public void equals() {
        Map<Integer, Result> map1 = new HashMap<Integer, Result>();
        map1.put(Integer.valueOf(1), new Result(State.SUCCESS));

        Map<Integer, Result> map2 = new HashMap<Integer, Result>();
        map2.put(Integer.valueOf(2), new Result(State.SUCCESS));

        Port actual1 = new Port(new Pin(1, Type.INPUT), "somename", map1);
        Port actual1_1 = new Port(new Pin(1, Type.INPUT), "somename", map1);
        Port actual2 = new Port(new Pin(1, Type.INPUT), "somename", map2);
        Port actual3 = new Port(new Pin(1, Type.INPUT), "someothername", map1);
        Port actual4 = new Port(new Pin(2, Type.INPUT), "somename", map1);
        Port actual5 = new Port(new Pin(1, Type.INPUT), null, map1);
        Port actual6 = new Port(null, "somename", map1);
        Port actual7 = new Port(new Pin(1, Type.INPUT), "somename", null);

        Assert.assertTrue(actual1.equals(actual1_1));
        Assert.assertTrue(actual1_1.equals(actual1));
        Assert.assertTrue(actual1.equals(actual1));
        Assert.assertFalse(actual1.equals(null));
        Assert.assertFalse(actual1.equals(""));
        Assert.assertFalse(actual1.equals(actual2));
        Assert.assertFalse(actual2.equals(actual1));
        Assert.assertFalse(actual1.equals(actual3));
        Assert.assertFalse(actual3.equals(actual1));
        Assert.assertFalse(actual1.equals(actual4));
        Assert.assertFalse(actual4.equals(actual1));
        Assert.assertFalse(actual4.equals(new Event()));
        Assert.assertFalse(actual5.equals(actual1));
        Assert.assertFalse(actual6.equals(actual1));
        Assert.assertFalse(actual7.equals(actual1));
    }

    @Test
    public void hashCodeTest() {
        Map<Integer, Result> map1 = new HashMap<Integer, Result>();
        map1.put(Integer.valueOf(1), new Result(State.SUCCESS));

        Map<Integer, Result> map2 = new HashMap<Integer, Result>();
        map2.put(Integer.valueOf(2), new Result(State.SUCCESS));

        Port actual1 = new Port(new Pin(1, Type.INPUT), "somename", map1);
        Port actual1_1 = new Port(new Pin(1, Type.INPUT), "somename", map1);
        Port actual2 = new Port(new Pin(1, Type.INPUT), "somename", map2);
        Port actual3 = new Port(new Pin(1, Type.INPUT), "someothername", map1);
        Port actual4 = new Port(new Pin(2, Type.INPUT), "somename", map1);
        Port actual5 = new Port(new Pin(1, Type.INPUT), null, map1);

        Assert.assertEquals(actual1.hashCode(), actual1_1.hashCode());
        Assert.assertNotEquals(actual1.hashCode(), actual2.hashCode());
        Assert.assertNotEquals(actual1.hashCode(), actual3.hashCode());
        Assert.assertNotEquals(actual1.hashCode(), actual4.hashCode());
        Assert.assertNotEquals(actual1.hashCode(), actual5.hashCode());
    }

    @Test
    public void cloneTest() {
        Result result = new Result(State.SUCCESS);
        Map<Integer, Result> map = new HashMap<Integer, Result>();
        map.put(Integer.valueOf(1), result);
        Port port = new Port(new Pin(1, Type.INPUT), "somename", map);
        Port cloned = port.clone();
        Assert.assertEquals(port.getPin(), cloned.getPin());
        Assert.assertNotSame(port.getPin(), cloned.getPin());
        Assert.assertEquals("somename", cloned.getName());
        Assert.assertEquals(port.getResult(), cloned.getResult());
        Assert.assertNotSame(port.getResult(), cloned.getResult());

        port = new Port(map);
        cloned = port.clone();
        Assert.assertEquals(port.getPin(), cloned.getPin());
        Assert.assertNull(cloned.getPin());
        Assert.assertEquals(port.getName(), cloned.getName());
        Assert.assertNull(cloned.getName());
        Assert.assertEquals(port.getResult(), cloned.getResult());
        Assert.assertNotSame(port.getResult(), cloned.getResult());
    }
    
    @Test
    public void setPinTest(){
    	Pin pin = new Pin(1, Type.INPUT);
    	Port port = new Port();
    	
    	port.setPin(pin);
    	Assert.assertEquals(pin,port.getPin());
    }
}