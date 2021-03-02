package havis.middleware.ale.base.operation;

import havis.middleware.ale.base.operation.port.Port;
import havis.middleware.ale.base.operation.port.result.Result;
import havis.middleware.ale.base.operation.port.result.Result.State;
import havis.middleware.ale.base.operation.tag.Tag;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.junit.Assert;
import org.junit.Test;

public class EventTest {

    @Test
    public void event() {
        Event event = new Event();
        Assert.assertFalse(event.isCompleted());
        Assert.assertNotNull(event.getResult());
        Assert.assertEquals(0, event.getResult().size());
        long time = new Date().getTime();
        Assert.assertTrue("Expected event.getFirstTime() " + time + " but was " + event.getFirstTime().getTime(), event.getFirstTime().getTime() >= time - 10 && event.getFirstTime().getTime() <= time);
        Assert.assertEquals(event.getLastTime(), event.getFirstTime());
        Assert.assertEquals(0, event.getCount());

        event.setCompleted(true);
        Assert.assertTrue(event.isCompleted());

        event.setCount(2);
        Assert.assertEquals(2, event.getCount());

        event.setFirstTime(new Date(time));
        Assert.assertEquals(new Date(time), event.getFirstTime());

        event.setLastTime(new Date(time));
        Assert.assertEquals(new Date(time), event.getLastTime());

        Map<Integer, Result> result = new HashMap<Integer, Result>();
        result.put(Integer.valueOf(0), new Result(State.SUCCESS));
        event.setResult(result);
        Assert.assertEquals(result, event.getResult());
    }

    @Test
    public void eventTag(@Mocked final Tag tag, @Mocked final Tag otherTag) {
        final Date firstTime = new Date();
        final Date lastTime = new Date(firstTime.getTime() + 50);
        final Date firstOtherTime = new Date();
        final Date lastOtherTime = new Date(firstOtherTime.getTime() + 50);
        new NonStrictExpectations() {
            {
                tag.getUri();
                result = "test";

                tag.getFirstTime();
                result = firstTime;

                tag.getLastTime();
                result = lastTime;

                otherTag.getUri();
                result = "test2";

                otherTag.getFirstTime();
                result = firstOtherTime;

                otherTag.getLastTime();
                result = lastOtherTime;
            }
        };
        Event event = new Event(tag);
        Assert.assertFalse(event.isCompleted());
        Assert.assertNotNull(event.getResult());
        Assert.assertEquals(0, event.getResult().size());
        Assert.assertEquals(0, event.getCount());
        Assert.assertSame("test", event.getUri());
        Assert.assertSame(tag, event.getTag());
        Assert.assertEquals(firstTime, event.getFirstTime());
        Assert.assertEquals(lastTime, event.getLastTime());
    }

    @Test
    public void eventUri() {
        Event event = new Event("test");
        Assert.assertFalse(event.isCompleted());
        Assert.assertNotNull(event.getResult());
        Assert.assertEquals(0, event.getResult().size());
        Assert.assertEquals(0, event.getCount());
        Assert.assertSame("test", event.getUri());
        long time = new Date().getTime();
        Assert.assertTrue("Expected event.getFirstTime() " + time + " but was " + event.getFirstTime().getTime(), event.getFirstTime().getTime() >= time - 10 && event.getFirstTime().getTime() <= time);
        Assert.assertEquals(event.getLastTime(), event.getFirstTime());
    }

    @Test
    public void stat(@Mocked final Tag sourceTag, @Mocked final Tag targetTag) throws InterruptedException {
        final Date sourceFirstTime = new Date(1451606400);
        final Date sourceLastTime = new Date(1451610000);
        new NonStrictExpectations() {
            {
                sourceTag.getUri();
                result = "sourceUri";

                sourceTag.getFirstTime();
                result = sourceFirstTime;

                sourceTag.getLastTime();
                result = sourceLastTime;

                targetTag.getUri();
                result = "targetUri";

                targetTag.getFirstTime();
                result = null;

                targetTag.getLastTime();
                result = null;
            }
        };

        Event source = new Event(sourceTag);
        Assert.assertEquals(sourceFirstTime, source.getFirstTime());
        Assert.assertEquals(sourceLastTime, source.getLastTime());
        Assert.assertEquals(0, source.getCount());

        Event target = new Event(targetTag);
        Assert.assertNull(target.getFirstTime());
        Assert.assertNull(target.getLastTime());
        Assert.assertEquals(0, target.getCount());

        target.stat(source, "reader");

        Assert.assertEquals(sourceFirstTime, target.getFirstTime());
        Assert.assertEquals(sourceLastTime, target.getLastTime());
        Assert.assertEquals(1, target.getCount());

        new Verifications() {
            {
                targetTag.stat(withEqual("reader"), withSameInstance(sourceTag));
                times = 1;
            }
        };
    }

    @Test
    public void statNull(@Mocked final Tag sourceTag, @Mocked final Tag targetTag) throws InterruptedException {
        final Date sourceFirstTime = new Date(1451606400);
        final Date sourceLastTime = new Date(1451610000);
        final Date targetFirstTime = new Date(1452606400);
        final Date targetLastTime = new Date(1452610000);
        new NonStrictExpectations() {
            {
                sourceTag.getUri();
                result = "sourceUri";

                sourceTag.getFirstTime();
                result = sourceFirstTime;

                sourceTag.getLastTime();
                result = sourceLastTime;

                targetTag.getUri();
                result = "targetUri";

                targetTag.getFirstTime();
                result = targetFirstTime;

                targetTag.getLastTime();
                result = targetLastTime;
            }
        };

        Event source = new Event(sourceTag);
        Assert.assertEquals(sourceFirstTime, source.getFirstTime());
        Assert.assertEquals(sourceLastTime, source.getLastTime());
        Assert.assertEquals(0, source.getCount());

        Event target = new Event(targetTag);
        Assert.assertEquals(targetFirstTime, target.getFirstTime());
        Assert.assertEquals(targetLastTime, target.getLastTime());
        Assert.assertEquals(0, target.getCount());

        target.stat(source);

        Assert.assertEquals(targetFirstTime, target.getFirstTime());
        Assert.assertEquals(sourceLastTime, target.getLastTime());
        Assert.assertEquals(1, target.getCount());

        new Verifications() {
            {
                targetTag.stat(withEqual("reader"), withSameInstance(sourceTag));
                times = 0;
            }
        };
    }

    @Test
    public void equals() {
        Event event1 = new Event("uri");
        Event event2 = new Event("uri");

        Assert.assertTrue(event1.equals(event2));
        Assert.assertTrue(event2.equals(event1));
        Assert.assertFalse(event1.equals(null));
        Assert.assertFalse(event1.equals(""));

        event2.setUri("uri2");
        Assert.assertFalse(event1.equals(event2));
        Assert.assertFalse(event2.equals(event1));
        
        Assert.assertTrue(event1.equals(event1));
        
        Port port = new Port();
        port.setUri("uri");
        Assert.assertFalse(event1.equals(port));
    }

    @Test
    public void hashCodeTest() {
        Event event1 = new Event("uri");
        Event event2 = new Event("uri");
        Assert.assertEquals(event1.hashCode(), event2.hashCode());

        event2.setUri("uri2");
        Assert.assertNotEquals(event1.hashCode(), event2.hashCode());
    }
    
    @Test
    public void toStringTest(){
    	Event event = new Event("uri");
    	Date date = new Date();
    	Map<Integer,Result> result = new HashMap<Integer, Result>();
    	
    	result.put(new Integer(1), new Result(Result.State.SUCCESS));
    	event.setResult(result);
    	
    	Assert.assertEquals("Event [uri=uri, result={1=Result [state=SUCCESS]}, firstTime="+date+", lastTime="+date+", count=0]",event.toString());
    }
}
