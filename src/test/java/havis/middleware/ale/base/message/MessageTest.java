package havis.middleware.ale.base.message;

import org.junit.Assert;
import org.junit.Test;

public class MessageTest {

	@Test
	public void messageTest(){
		Message message = new Message();
		
		Assert.assertNull(message.getText());
		Assert.assertNull(message.getType());
		Assert.assertNull(message.getException());
	}
	
	@Test 
	public void messageWithTypeAndText(){
		Message message = new Message("Type","Test");
		
		Assert.assertEquals("Test", message.getText());
		Assert.assertEquals("Type", message.getType());
		Assert.assertNull(message.getException());
	}
	
	@Test
	public void messageWithTypeTextAndException(){
		Message message = new Message("Type","Text", new IllegalArgumentException());
		
		Assert.assertEquals("Text", message.getText());
		Assert.assertEquals("Type", message.getType());
		Assert.assertEquals("java.lang.IllegalArgumentException", message.getException().toString());
	}
	
	@Test
	public void messageWithTypeAndException(){
		Message message = new Message("Type", new IllegalArgumentException("message"));
		
		Assert.assertEquals("Type", message.getType());
		Assert.assertEquals("[class java.lang.IllegalArgumentException]:\n"
						   +"message\n"
				           +"[havis.middleware.ale.base.message.MessageTest.messageWithTypeAndException(MessageTest.java:37), ", message.getText().substring(0, 149));
		Assert.assertEquals("]", message.getText().substring(message.getText().length() - 1));
		Assert.assertEquals("java.lang.IllegalArgumentException: message", message.getException().toString());
	}
	
	@Test
	public void setTextTest(){
		Message message = new Message("Type", (String)null);
		
		message.setText("Test");
		Assert.assertEquals("Test", message.getText());
	}
	
	@Test
	public void setTypeTest(){
		Message message = new Message((String)null,"Text");
		
		message.setType("Test");
		Assert.assertEquals("Test", message.getType());
	}
	
}
