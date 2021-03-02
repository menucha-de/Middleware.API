package havis.middleware.ale.base;

import havis.middleware.ale.service.lr.LRSpec;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DateTypeAdapterTest {

	private static TimeZone DEFAULT;

	@BeforeClass
	public static void init() {
		// device is running in UTC
		DEFAULT = TimeZone.getDefault();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	@AfterClass
	public static void reset() {
		TimeZone.setDefault(DEFAULT);
	}

	private Date d(int year, int month, int date, int hourOfDay, int minute, int second) {
		return d(year, month, date, hourOfDay, minute, second, 0);
	}
	
	private Date d(int year, int month, int date, int hourOfDay, int minute, int second, int ms) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, date, hourOfDay, minute, second);
		cal.set(Calendar.MILLISECOND, ms);
		return cal.getTime();
	}

	@Test
	public void parseDate() {
		Assert.assertNull(DateTypeAdapter.parseDate(null));
		Assert.assertNull(DateTypeAdapter.parseDate(""));
		Assert.assertNull(DateTypeAdapter.parseDate("notadate"));

		Assert.assertNull(DateTypeAdapter.parseDate("2016-12-15T14:33:01"));
		Assert.assertNull(DateTypeAdapter.parseDate("2016-12-15T14:33Z"));
		Assert.assertNull(DateTypeAdapter.parseDate("2016-12-15Z"));

		Assert.assertEquals(d(2016, 07, 15, 14, 33, 01), DateTypeAdapter.parseDate("2016-07-15T14:33:01Z"));

		Assert.assertEquals(d(2016, 12, 15, 14, 33, 01), DateTypeAdapter.parseDate("2016-12-15T14:33:01Z"));
		Assert.assertEquals(d(2016, 12, 15, 14, 33, 01), DateTypeAdapter.parseDate("2016-12-15T14:33:01+00:00"));
		Assert.assertEquals(d(2016, 12, 15, 14, 33, 01), DateTypeAdapter.parseDate("2016-12-15T15:33:01+01:00"));
		Assert.assertEquals(d(2016, 12, 15, 14, 33, 01), DateTypeAdapter.parseDate("2016-12-15T13:33:01-01:00"));

		Assert.assertNull(DateTypeAdapter.parseDate("2016-12-15T15:33:01+0100"));
		Assert.assertNull(DateTypeAdapter.parseDate("2016-12-15T15:33:01+01"));

		Assert.assertEquals(d(2016, 12, 15, 14, 33, 01, 700), DateTypeAdapter.parseDate("2016-12-15T14:33:01.7Z"));
		Assert.assertEquals(d(2016, 12, 15, 14, 33, 01, 780), DateTypeAdapter.parseDate("2016-12-15T14:33:01.78Z"));
		Assert.assertEquals(d(2016, 12, 15, 14, 33, 01, 789), DateTypeAdapter.parseDate("2016-12-15T14:33:01.789Z"));
		Assert.assertEquals(d(2016, 12, 15, 14, 33, 01, 789), DateTypeAdapter.parseDate("2016-12-15T14:33:01.7897Z"));
		Assert.assertEquals(d(2016, 12, 15, 14, 33, 01, 789), DateTypeAdapter.parseDate("2016-12-15T14:33:01.78978Z"));
		Assert.assertEquals(d(2016, 12, 15, 14, 33, 01, 789), DateTypeAdapter.parseDate("2016-12-15T14:33:01.789789Z"));

		Assert.assertEquals(d(2016, 12, 15, 14, 33, 01, 700), DateTypeAdapter.parseDate("2016-12-15T14:33:01,7Z"));
		Assert.assertEquals(d(2016, 12, 15, 14, 33, 01, 780), DateTypeAdapter.parseDate("2016-12-15T14:33:01,78Z"));
		Assert.assertEquals(d(2016, 12, 15, 14, 33, 01, 789), DateTypeAdapter.parseDate("2016-12-15T14:33:01,789Z"));
		Assert.assertEquals(d(2016, 12, 15, 14, 33, 01, 789), DateTypeAdapter.parseDate("2016-12-15T14:33:01,7897Z"));
		Assert.assertEquals(d(2016, 12, 15, 14, 33, 01, 789), DateTypeAdapter.parseDate("2016-12-15T14:33:01,78978Z"));
		Assert.assertEquals(d(2016, 12, 15, 14, 33, 01, 789), DateTypeAdapter.parseDate("2016-12-15T14:33:01,789789Z"));
	}

	@Test
	public void printDate() {
		Assert.assertNull(DateTypeAdapter.printDate(null));

		Assert.assertEquals("2016-07-15T14:33:01.123Z", DateTypeAdapter.printDate(d(2016, 07, 15, 14, 33, 01, 123)));
		Assert.assertEquals("2016-12-15T14:33:01.123Z", DateTypeAdapter.printDate(d(2016, 12, 15, 14, 33, 01, 123)));
	}

	@Test
	public void testDateParsing() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(LRSpec.class);
		Marshaller marshaller = context.createMarshaller();
		Unmarshaller unmarshaller = context.createUnmarshaller();

		LRSpec spec = new LRSpec();
		spec.setCreationDate(new Date(1234567891123L));

		StringWriter writer = new StringWriter();
		marshaller.marshal(new havis.middleware.ale.service.lr.ObjectFactory().createLRSpec(spec), writer);

		String xml = writer.toString();

		LRSpec actual = unmarshaller.unmarshal(new StreamSource(new StringReader(xml)), LRSpec.class).getValue();
		Assert.assertEquals(spec.getCreationDate(), actual.getCreationDate());

		xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns3:LRSpec creationDate=\"2009-02-13T23:31:31.123Z\" xmlns:ns2=\"urn:epcglobal:alelr:wsdl:1\" xmlns:ns3=\"urn:epcglobal:ale:xsd:1\"/>";

		actual = unmarshaller.unmarshal(new StreamSource(new StringReader(xml)), LRSpec.class).getValue();
		Assert.assertEquals(spec.getCreationDate(), actual.getCreationDate());
	}
}
