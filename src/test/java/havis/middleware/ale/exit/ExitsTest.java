package havis.middleware.ale.exit;

import mockit.Deencapsulation;

import org.junit.Assert;
import org.junit.Test;

public class ExitsTest {

	@Test
	public void getName(){
		Assert.assertEquals("Common", Deencapsulation.invoke(Exits.class, "getName", new Class<?>[] {Class.class}, Exits.Common.class));
		Assert.assertEquals("Service.LR", Deencapsulation.invoke(Exits.class, "getName", new Class<?>[] {Class.class}, Exits.Service.LR.class));
		Assert.assertEquals("Core.Cycle.EventCycle.Trigger", Deencapsulation.invoke(Exits.class, "getName", new Class<?>[] {Class.class}, Exits.Core.Cycle.EventCycle.Trigger.class));
	}
}
