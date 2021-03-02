package havis.middleware.ale.base.operation.tag;

import havis.middleware.ale.base.operation.tag.Tag.Decoder;
import havis.middleware.ale.base.operation.tag.Tag.Property;
import havis.middleware.ale.base.operation.tag.result.Result;
import havis.middleware.ale.base.operation.tag.result.ResultState;
import havis.middleware.misc.TdtInitiator;
import havis.middleware.misc.TdtInitiator.SCHEME;
import havis.middleware.misc.TdtWrapper;
import havis.middleware.tdt.ItemData;
import havis.middleware.tdt.TdtTranslationException;
import havis.middleware.tdt.TdtTranslator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TagTest {

	@BeforeClass
	public static void init() throws IOException {
		TdtTranslator tdt = TdtWrapper.getTdt();
		tdt.getTdtDefinitions().getDefinitions().clear();
		for (SCHEME scheme : SCHEME.values()) {
			tdt.getTdtDefinitions().add(TdtInitiator.get(scheme));
		}
		Tag.setExtended(true);
	}

	private static byte[] decode(String urn) throws TdtTranslationException {
		return TdtWrapper.getTdt().translate(urn).getEpcData();
	}

	private static Decoder<byte[]> decoder() {
		return new Decoder<byte[]>() {
			@Override
			public Object decode(int bank, byte[] data) {
				try {
					return TdtWrapper.getTdt().translate(data);
				} catch (TdtTranslationException e) {
					return null;
				}
			}
		};
	}

	private static Decoder<byte[]> packedObjectDecoder() {
		return new Decoder<byte[]>() {
			@Override
			public Object decode(int bank, byte[] data) {
				return TdtWrapper.getItemDataDecoder().decode(bank, data);
			}
		};
	}

	@Test
	public void tag() {

		Calendar expectedFirstTime = Calendar.getInstance();
		expectedFirstTime.set(2015, 8 + 1, 7);
		Calendar expectedLastTime = Calendar.getInstance();
		expectedLastTime.set(2015, 9 + 1, 7);

		Tag tag = new Tag();
		Assert.assertEquals(0, tag.getCount());

		tag.setCount(2);
		tag.setPc(new byte[] { 0x03, 0x04 });
		tag.setTid(new byte[] { 0x01, 0x02 });
		tag.setLength(8);
		tag.setTimeout(2000);
		tag.setCompleted(true);
		tag.setFirstTime(expectedFirstTime.getTime());
		tag.setLastTime(expectedLastTime.getTime());
		Map<Integer, Result> result = new HashMap<Integer, Result>();
		result.put(Integer.valueOf(1), new Result(ResultState.SUCCESS));
		tag.setResult(result);
		tag.setSighting(new Sighting("host", (short) 1, 1, new Date(0)));
		Map<String, List<Sighting>> sightings = new HashMap<String, List<Sighting>>();
		sightings.put("reader", new ArrayList<Sighting>());
		sightings.get("reader").add(new Sighting("host2", (short) 2, 2, new Date(1)));
		tag.setSightings(sightings);

		Assert.assertEquals(2, tag.getCount());
		Assert.assertEquals(8, tag.getLength());
		Assert.assertEquals(2000, tag.getTimeout());
		Assert.assertTrue(Arrays.equals(new byte[] { 0x01, 0x02 }, tag.getTid()));
		Assert.assertTrue(Arrays.equals(new byte[] { 0x43, 0x04 }, tag.getPc()));
		Assert.assertTrue(tag.isCompleted());
		Assert.assertEquals(expectedFirstTime.getTime(), tag.getFirstTime());
		Assert.assertEquals(expectedLastTime.getTime(), tag.getLastTime());
		Assert.assertNotNull(tag.getResult());
		Assert.assertEquals(1, tag.getResult().size());
		Assert.assertEquals(ResultState.SUCCESS, tag.getResult().get(Integer.valueOf(1)).getState());
		Assert.assertNotNull(tag.getSighting());
		Assert.assertEquals("host", tag.getSighting().getHost());
		Assert.assertEquals(1, tag.getSighting().getAntenna());
		Assert.assertEquals(1, tag.getSighting().getStrength());
		Assert.assertEquals(new Date(0), tag.getSighting().getTimestamp());
		Assert.assertNotNull(tag.getSightings());
		Assert.assertEquals(1, tag.getSightings().size());
		Assert.assertEquals(1, tag.getSightings().get("reader").size());
		Assert.assertEquals("host2", tag.getSightings().get("reader").get(0).getHost());
		Assert.assertEquals(2, tag.getSightings().get("reader").get(0).getAntenna());
		Assert.assertEquals(2, tag.getSightings().get("reader").get(0).getStrength());
		Assert.assertEquals(new Date(1), tag.getSightings().get("reader").get(0).getTimestamp());

	}

	@Test
	public void tagUrn() throws TdtTranslationException {
		Tag tag = new Tag(decode("urn:epc:tag:sgtin-96:3.0036000.123456.400"));
		tag.setTagInfoDecoder(decoder());
		Assert.assertNotNull(tag.getProperty(Property.TAG_INFO));
		Assert.assertEquals("urn:epc:tag:sgtin-96:3.0036000.123456.400", tag.toString());
		Assert.assertNotNull(tag.getEpc());
		Assert.assertTrue(Arrays.equals(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 },
				tag.getEpc()));
	}

	@Test
	public void tagCode() throws TdtTranslationException {
		Tag tag = new Tag(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 });
		tag.setTagInfoDecoder(decoder());
		Assert.assertEquals("urn:epc:tag:sgtin-96:3.0036000.123456.400", tag.toString());
		Assert.assertTrue(Arrays.equals(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 },
				tag.getEpc()));
	}

	@Test
	public void tagUrnTid() throws TdtTranslationException {
		Tag tag = new Tag(decode("urn:epc:tag:sgtin-96:3.0036000.123456.400"), new byte[] { 0x01, 0x02 });
		tag.setTagInfoDecoder(decoder());
		Assert.assertNotNull(tag.getProperty(Property.TAG_INFO));
		Assert.assertEquals("urn:epc:tag:sgtin-96:3.0036000.123456.400", tag.toString());
		Assert.assertNotNull(tag.getEpc());
		Assert.assertTrue(Arrays.equals(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 },
				tag.getEpc()));
		Assert.assertTrue(Arrays.equals(new byte[] { 0x01, 0x02 }, tag.getTid()));
	}

	@Test
	public void tagFilters() throws TdtTranslationException {
		Filter filter = new Filter(1, 1, 0, new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 });
		Tag tag = new Tag(Arrays.asList(filter));
		tag.setTagInfoDecoder(decoder());
		Assert.assertNotNull(tag.getProperty(Property.TAG_INFO));
		Assert.assertEquals("urn:epc:tag:sgtin-96:3.0036000.123456.400", tag.toString());
		Assert.assertNotNull(tag.getEpc());
		Assert.assertTrue(Arrays.equals(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 },
				tag.getEpc()));
	}

	@Test
	public void getLength() {
		Tag tag = new Tag();
		Assert.assertEquals(0, tag.getLength());

		tag.setPc(new byte[0]);
		Assert.assertEquals(0, tag.getLength());

		tag.setPc(new byte[] { (byte) 0xFF });
		Assert.assertEquals(31, tag.getLength());

		tag.setPc(new byte[] { (byte) 0xFF, (byte) 0xFF });
		Assert.assertEquals(31, tag.getLength());

		tag.setPc(new byte[] { (byte) (0xFF >> 5), (byte) 0xFF });
		tag.setLength(31);
		Assert.assertTrue(Arrays.equals(new byte[] { (byte) 0xFF, (byte) 0xFF }, tag.getPc()));
		Assert.assertEquals(31, tag.getLength());

		tag.setLength(30);
		Assert.assertTrue(Arrays.equals(new byte[] { (byte) 0xF7, (byte) 0xFF }, tag.getPc()));
		Assert.assertEquals(30, tag.getLength());

		tag.setPc(new byte[] { (byte) 0x00, (byte) 0x00 });
		tag.setLength(31);
		Assert.assertTrue(Arrays.equals(new byte[] { (byte) 0xF8, (byte) 0x00 }, tag.getPc()));
		Assert.assertEquals(31, tag.getLength());

		Tag tag2 = new Tag();
		tag2.setLength(30);
		Assert.assertEquals(30, tag2.getLength());
	}

	@Test
	public void stat() throws TdtTranslationException {
		Tag tag = new Tag(decode("urn:epc:tag:sgtin-96:3.0036000.123456.400"));
		tag.setSighting(null);

		Assert.assertEquals(0, tag.getCount());
		Assert.assertNotNull(tag.getSightings());
		Assert.assertTrue(tag.getSightings().isEmpty());

		tag.stat("reader");

		Assert.assertEquals(1, tag.getCount());
		Assert.assertNotNull(tag.getSightings());
		Assert.assertTrue(tag.getSightings().isEmpty());

		tag.setSighting(new Sighting("host", (short) 1, 1, new Date(0)));
		tag.stat("reader");

		Assert.assertEquals(1, tag.getCount());
		Assert.assertNotNull(tag.getSightings());
		Assert.assertEquals(1, tag.getSightings().size());
		Assert.assertEquals(1, tag.getSightings().get("reader").size());
		Assert.assertEquals("host", tag.getSightings().get("reader").get(0).getHost());
		Assert.assertEquals(1, tag.getSightings().get("reader").get(0).getAntenna());
		Assert.assertEquals(1, tag.getSightings().get("reader").get(0).getStrength());
		Assert.assertEquals(new Date(0), tag.getSightings().get("reader").get(0).getTimestamp());

		tag.setSighting(new Sighting("host2", (short) 2, 2, new Date(1)));
		tag.stat("reader");

		Assert.assertEquals(1, tag.getCount());
		Assert.assertNotNull(tag.getSightings());
		Assert.assertEquals(1, tag.getSightings().size());
		Assert.assertEquals(1, tag.getSightings().get("reader").size());
		Assert.assertEquals("host2", tag.getSightings().get("reader").get(0).getHost());
		Assert.assertEquals(2, tag.getSightings().get("reader").get(0).getAntenna());
		Assert.assertEquals(2, tag.getSightings().get("reader").get(0).getStrength());
		Assert.assertEquals(new Date(1), tag.getSightings().get("reader").get(0).getTimestamp());

		tag.setSighting(new Sighting("host3", (short) 3, 3, new Date(2)));
		tag.stat("reader2");

		Assert.assertEquals(1, tag.getCount());
		Assert.assertNotNull(tag.getSightings());
		Assert.assertEquals(2, tag.getSightings().size());
		Assert.assertEquals(1, tag.getSightings().get("reader").size());
		Assert.assertEquals("host2", tag.getSightings().get("reader").get(0).getHost());
		Assert.assertEquals(2, tag.getSightings().get("reader").get(0).getAntenna());
		Assert.assertEquals(2, tag.getSightings().get("reader").get(0).getStrength());
		Assert.assertEquals(new Date(1), tag.getSightings().get("reader").get(0).getTimestamp());
		Assert.assertEquals(1, tag.getSightings().get("reader2").size());
		Assert.assertEquals("host3", tag.getSightings().get("reader2").get(0).getHost());
		Assert.assertEquals(3, tag.getSightings().get("reader2").get(0).getAntenna());
		Assert.assertEquals(3, tag.getSightings().get("reader2").get(0).getStrength());
		Assert.assertEquals(new Date(2), tag.getSightings().get("reader2").get(0).getTimestamp());
	}

	@Test
	public void statWithTag() throws TdtTranslationException {

		Date expectedFirstTime = new Date();

		Tag tag = new Tag(decode("urn:epc:tag:sgtin-96:3.0036000.123456.400"));
		tag.setSighting(null);
		tag.setFirstTime(null);

		Assert.assertEquals(0, tag.getCount());
		Assert.assertNotNull(tag.getSightings());
		Assert.assertTrue(tag.getSightings().isEmpty());

		Tag tagSighting = new Tag(decode("urn:epc:tag:sgtin-96:3.0036000.123456.400"));
		tagSighting.setFirstTime(expectedFirstTime);
		tagSighting.setLastTime(new Date());
		tag.stat("reader", tagSighting);

		Assert.assertEquals(1, tag.getCount());
		Assert.assertNotNull(tag.getSightings());
		Assert.assertTrue(tag.getSightings().isEmpty());
		Assert.assertEquals(1, tag.getCount());
		Assert.assertEquals(expectedFirstTime, tag.getFirstTime());
		Assert.assertEquals(tagSighting.getLastTime(), tag.getLastTime());

		tagSighting = new Tag(decode("urn:epc:tag:sgtin-96:3.0036000.123456.400"));
		tagSighting.setFirstTime(new Date());
		tagSighting.setLastTime(new Date());
		tagSighting.setSighting(new Sighting("host", (short) 1, 1, new Date(0)));
		tag.stat("reader", tagSighting);

		Assert.assertEquals(2, tag.getCount());
		Assert.assertNotNull(tag.getSightings());
		Assert.assertEquals(1, tag.getSightings().size());
		Assert.assertEquals(1, tag.getSightings().get("reader").size());
		Assert.assertEquals("host", tag.getSightings().get("reader").get(0).getHost());
		Assert.assertEquals(1, tag.getSightings().get("reader").get(0).getAntenna());
		Assert.assertEquals(1, tag.getSightings().get("reader").get(0).getStrength());
		Assert.assertEquals(new Date(0), tag.getSightings().get("reader").get(0).getTimestamp());
		Assert.assertEquals(2, tag.getCount());
		Assert.assertEquals(expectedFirstTime, tag.getFirstTime());
		Assert.assertEquals(tagSighting.getLastTime(), tag.getLastTime());

		tagSighting = new Tag(decode("urn:epc:tag:sgtin-96:3.0036000.123456.400"));
		tagSighting.setFirstTime(new Date());
		tagSighting.setLastTime(new Date());
		tagSighting.setSighting(new Sighting("host2", (short) 2, 2, new Date(1)));
		tag.stat("reader", tagSighting);

		Assert.assertEquals(3, tag.getCount());
		Assert.assertNotNull(tag.getSightings());
		Assert.assertEquals(1, tag.getSightings().size());
		Assert.assertEquals(2, tag.getSightings().get("reader").size());
		Assert.assertEquals("host", tag.getSightings().get("reader").get(0).getHost());
		Assert.assertEquals(1, tag.getSightings().get("reader").get(0).getAntenna());
		Assert.assertEquals(1, tag.getSightings().get("reader").get(0).getStrength());
		Assert.assertEquals(new Date(0), tag.getSightings().get("reader").get(0).getTimestamp());
		Assert.assertEquals("host2", tag.getSightings().get("reader").get(1).getHost());
		Assert.assertEquals(2, tag.getSightings().get("reader").get(1).getAntenna());
		Assert.assertEquals(2, tag.getSightings().get("reader").get(1).getStrength());
		Assert.assertEquals(new Date(1), tag.getSightings().get("reader").get(1).getTimestamp());
		Assert.assertEquals(3, tag.getCount());
		Assert.assertEquals(expectedFirstTime, tag.getFirstTime());
		Assert.assertEquals(tagSighting.getLastTime(), tag.getLastTime());

		tagSighting = new Tag(decode("urn:epc:tag:sgtin-96:3.0036000.123456.400"));
		tagSighting.setFirstTime(new Date());
		tagSighting.setLastTime(new Date());
		tagSighting.setSighting(new Sighting("host3", (short) 3, 3, new Date(2)));
		tag.stat("reader2", tagSighting);

		Assert.assertEquals(4, tag.getCount());
		Assert.assertNotNull(tag.getSightings());
		Assert.assertEquals(2, tag.getSightings().size());
		Assert.assertEquals(2, tag.getSightings().get("reader").size());
		Assert.assertEquals("host", tag.getSightings().get("reader").get(0).getHost());
		Assert.assertEquals(1, tag.getSightings().get("reader").get(0).getAntenna());
		Assert.assertEquals(1, tag.getSightings().get("reader").get(0).getStrength());
		Assert.assertEquals(new Date(0), tag.getSightings().get("reader").get(0).getTimestamp());
		Assert.assertEquals("host2", tag.getSightings().get("reader").get(1).getHost());
		Assert.assertEquals(2, tag.getSightings().get("reader").get(1).getAntenna());
		Assert.assertEquals(2, tag.getSightings().get("reader").get(1).getStrength());
		Assert.assertEquals(new Date(1), tag.getSightings().get("reader").get(1).getTimestamp());
		Assert.assertEquals(1, tag.getSightings().get("reader2").size());
		Assert.assertEquals("host3", tag.getSightings().get("reader2").get(0).getHost());
		Assert.assertEquals(3, tag.getSightings().get("reader2").get(0).getAntenna());
		Assert.assertEquals(3, tag.getSightings().get("reader2").get(0).getStrength());
		Assert.assertEquals(new Date(2), tag.getSightings().get("reader2").get(0).getTimestamp());
		Assert.assertEquals(4, tag.getCount());
		Assert.assertEquals(expectedFirstTime, tag.getFirstTime());
		Assert.assertEquals(tagSighting.getLastTime(), tag.getLastTime());
	}

	@Test
	public void merge() throws TdtTranslationException {
		Tag tag = new Tag(new byte[] {});
		Tag tagWithInfo = new Tag(decode("urn:epc:tag:sgtin-96:3.0036000.123456.400"));
		tagWithInfo.setTagInfoDecoder(decoder());
		Tag actual = tag.merge(tagWithInfo);
		Assert.assertSame(tag, actual);
		Assert.assertEquals(tagWithInfo.toString(), tag.toString());
	}

	@Test
	public void clear() {
		Tag tag = new Tag();
		Map<String, List<Sighting>> sightings = new HashMap<String, List<Sighting>>();
		sightings.put("reader", new ArrayList<Sighting>());
		sightings.get("reader").add(new Sighting("host2", (short) 2, 2, new Date(0)));
		tag.setSightings(sightings);
		Assert.assertNotNull(tag.getFirstTime());
		Assert.assertNotNull(tag.getSightings());
		Assert.assertEquals(1, tag.getSightings().size());
		Assert.assertEquals(1, tag.getSightings().get("reader").size());
		Assert.assertEquals("host2", tag.getSightings().get("reader").get(0).getHost());
		Assert.assertEquals(2, tag.getSightings().get("reader").get(0).getAntenna());
		Assert.assertEquals(2, tag.getSightings().get("reader").get(0).getStrength());
		Assert.assertEquals(new Date(0), tag.getSightings().get("reader").get(0).getTimestamp());
		Assert.assertEquals(0, tag.getCount());

		tag.clear();

		Assert.assertEquals(0, tag.getCount());
		Assert.assertNull(tag.getFirstTime());
		Assert.assertNotNull(tag.getSightings());
		Assert.assertTrue(tag.getSightings().isEmpty());

	}

	@Test
	public void getFilter() throws TdtTranslationException {
		Tag tag = new Tag(decode("urn:epc:tag:sgtin-96:3.0036000.123456.400"), new byte[] { 1, 2 });
		List<Filter> filter = tag.getFilter();

		Assert.assertNotNull(filter);
		Assert.assertEquals(2, filter.size());
		Assert.assertEquals(1, filter.get(0).getBank());
		Assert.assertEquals(96, filter.get(0).getLength());
		Assert.assertEquals(32, filter.get(0).getOffset());
		Assert.assertTrue(Arrays.equals(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 },
				filter.get(0).getMask()));
		Assert.assertEquals(2, filter.get(1).getBank());
		Assert.assertEquals(16, filter.get(1).getLength());
		Assert.assertEquals(0, filter.get(1).getOffset());
		Assert.assertTrue(Arrays.equals(new byte[] { 1, 2 }, filter.get(1).getMask()));

		Tag.setExtended(false);
		Assert.assertNotEquals(filter, tag.getFilter());
		Tag.setExtended(true);
	}

	@Test
	public void apply() throws TdtTranslationException {
		Tag tag = new Tag(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 });
		tag.setTagInfoDecoder(decoder());
		Assert.assertTrue(Arrays.equals(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 },
				tag.getEpc()));
		Assert.assertEquals("urn:epc:tag:sgtin-96:3.0036000.123456.400", tag.toString());

		tag.apply(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x91 });

		Assert.assertTrue(Arrays.equals(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x91 },
				tag.getEpc()));
		Assert.assertEquals("urn:epc:tag:sgtin-96:3.0036000.123456.400", tag.toString()); // original
																						  // code
																						  // is
																						  // used

		tag.apply(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x92 });

		Assert.assertTrue(Arrays.equals(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x92 },
				tag.getEpc()));
		Assert.assertEquals("urn:epc:tag:sgtin-96:3.0036000.123456.400", tag.toString()); // original
																						  // code
																						  // is
																						  // used
	}

	@Test
	public void applyFilter() throws TdtTranslationException {
		Tag tag = new Tag(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 });
		tag.setTagInfoDecoder(decoder());
		Assert.assertTrue(Arrays.equals(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 },
				tag.getEpc()));
		Assert.assertEquals("urn:epc:tag:sgtin-96:3.0036000.123456.400", tag.toString());

		Filter filter1 = new Filter(1, 96, 0, new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x91 });
		Filter filter2 = new Filter(1, 8, 0, new byte[] { 0x01, 0x02 });
		tag.apply(Arrays.asList(filter1, filter2));

		Assert.assertTrue(Arrays.equals(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x91 },
				tag.getEpc()));
		Assert.assertTrue(Arrays.equals(new byte[] { 0x01, 0x02 }, tag.getTid()));
		Assert.assertEquals("urn:epc:tag:sgtin-96:3.0036000.123456.400", tag.toString()); // info
																						  // is
																						  // not
																						  // reset
	}

	@Test
	public void applyOperation() throws TdtTranslationException {
		Tag tag = new Tag(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 });
		tag.setTagInfoDecoder(decoder());
		Tag tag2 = new Tag(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 });
		tag2.setTagInfoDecoder(decoder());
		Tag tag3 = new Tag(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 });
		tag3.setTagInfoDecoder(decoder());
		Operation operation2 = new Operation(1, OperationType.WRITE, new Field("epc", 1, 16, 0), new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78,
				(byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x92 });
		Operation operation3 = new Operation(1, OperationType.WRITE, new Field("epc", 1, 32, 0), new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78,
				(byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x92 });

		Assert.assertTrue(Arrays.equals(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 },
				tag.getEpc()));
		Assert.assertEquals("urn:epc:tag:sgtin-96:3.0036000.123456.400", tag.toString());

		Assert.assertFalse(tag.apply(new Operation(1, OperationType.READ, new byte[0])));

		Assert.assertTrue(Arrays.equals(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 },
				tag.getEpc()));
		Assert.assertEquals("urn:epc:tag:sgtin-96:3.0036000.123456.400", tag.toString());

		Operation operation = new Operation(1, OperationType.WRITE, new Field("epc", 1, 0, 128), new byte[] { 0x00, 0x00, 0x00, 0x00, 0x30, 0x74, 0x02, 0x32,
				(byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x91 });
		Assert.assertTrue(tag.apply(operation));

		Assert.assertTrue(Arrays.equals(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x91 },
				tag.getEpc()));
		Assert.assertEquals("urn:epc:tag:sgtin-96:3.0036000.123456.400", tag.toString()); // original
																						  // code
																						  // is
																						  // used

		operation = new Operation(1, OperationType.WRITE, new Field("epc", 1, 32, 96), new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90,
				0x00, 0x00, 0x00, 0x01, (byte) 0x92 });
		Assert.assertTrue(tag.apply(operation));

		Assert.assertTrue(Arrays.equals(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x92 },
				tag.getEpc()));
		Assert.assertEquals("urn:epc:tag:sgtin-96:3.0036000.123456.400", tag.toString()); // original
																						  // code
																						  // is
																						  // used

		tag2.apply(operation2);
		Assert.assertEquals(0, tag2.getLength());

		tag3.apply(operation3);
		Assert.assertEquals(0, tag3.getLength());
	}

	@Test
	public void applyTagForce() throws TdtTranslationException {
		Tag tag = new Tag(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 });
		tag.setTagInfoDecoder(decoder());
		Tag tagToApply = new Tag(decode("urn:epc:tag:sgtin-96:3.0036000.123456.401"), new byte[] { 0x01, 0x02 });
		tagToApply.setTagInfoDecoder(decoder());
		tagToApply.setPc(new byte[] { 0x03, 0x04 });
		Map<Integer, Result> result = new HashMap<Integer, Result>();
		result.put(Integer.valueOf(1), new Result(ResultState.SUCCESS));
		tagToApply.setResult(result);
		tag.apply(tagToApply, true);

		Assert.assertTrue(Arrays.equals(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x91 },
				tag.getEpc()));
		Assert.assertEquals("urn:epc:tag:sgtin-96:3.0036000.123456.400", tag.toString()); // original
																						  // code
																						  // is
																						  // used
		Assert.assertTrue(Arrays.equals(new byte[] { 0x01, 0x02 }, tag.getTid()));
		Assert.assertTrue(Arrays.equals(new byte[] { 0x03, 0x04 }, tag.getPc()));
		Assert.assertNotNull(tag.getResult());
		Assert.assertEquals(1, tag.getResult().size());
		Assert.assertEquals(ResultState.SUCCESS, tag.getResult().get(Integer.valueOf(1)).getState());
	}

	@Test
	public void applyTagImplicitForce() throws TdtTranslationException {
		Tag tag = new Tag(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 });
		tag.setTagInfoDecoder(decoder());
		Tag tagToApply = new Tag(decode("urn:epc:tag:sgtin-96:3.0036000.123456.401"), new byte[] { 0x01, 0x02 });
		tagToApply.setTagInfoDecoder(decoder());
		tagToApply.setPc(new byte[] { 0x03, 0x04 });
		Map<Integer, Result> result = new HashMap<Integer, Result>();
		result.put(Integer.valueOf(1), new Result(ResultState.SUCCESS));
		tagToApply.setResult(result);
		tag.apply(tagToApply, false);

		Assert.assertTrue(Arrays.equals(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x91 },
				tag.getEpc()));
		Assert.assertEquals("urn:epc:tag:sgtin-96:3.0036000.123456.400", tag.toString()); // original
																						  // code
																						  // is
																						  // used
		Assert.assertTrue(Arrays.equals(new byte[] { 0x01, 0x02 }, tag.getTid()));
		Assert.assertTrue(Arrays.equals(new byte[] { 0x03, 0x04 }, tag.getPc()));
		Assert.assertNotNull(tag.getResult());
		Assert.assertEquals(1, tag.getResult().size());
		Assert.assertEquals(ResultState.SUCCESS, tag.getResult().get(Integer.valueOf(1)).getState());
	}

	@Test
	public void applyTagNoForceIgnore() throws TdtTranslationException {
		Tag tag = new Tag(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 });
		tag.setTagInfoDecoder(decoder());
		Map<Integer, Result> result = new HashMap<Integer, Result>();
		result.put(Integer.valueOf(1), new Result(ResultState.SUCCESS));
		tag.setResult(result);
		Tag tagToApply = new Tag(decode("urn:epc:tag:sgtin-96:3.0036000.123456.401"), new byte[] { 0x01, 0x02 });
		tagToApply.setTagInfoDecoder(decoder());
		tagToApply.setPc(new byte[] { 0x03, 0x04 });
		Map<Integer, Result> applyResult = new HashMap<Integer, Result>();
		applyResult.put(Integer.valueOf(2), new Result(ResultState.SUCCESS));
		tagToApply.setResult(applyResult);
		tag.apply(tagToApply, false);
		Tag tag2 = new Tag(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 });
		tag2.setTagInfoDecoder(decoder());
		Map<Integer, Result> resultMap = new HashMap<Integer, Result>();
		resultMap.put(Integer.valueOf(1), new Result(ResultState.MEMORY_CHECK_ERROR));
		tag2.setResult(resultMap);
		tag.apply(tag2, false);

		Assert.assertTrue(Arrays.equals(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 },
				tag.getEpc()));
		Assert.assertEquals("urn:epc:tag:sgtin-96:3.0036000.123456.400", tag.toString());
		Assert.assertNull(tag.getTid());
		Assert.assertNull(tag.getPc());
		Assert.assertNotNull(tag.getResult());
		Assert.assertEquals(1, tag.getResult().size());
		Assert.assertEquals(ResultState.SUCCESS, tag.getResult().get(Integer.valueOf(1)).getState());
		Assert.assertEquals("Result [state=SUCCESS]", tag.getResult().get(Integer.valueOf(1)).toString());
	}

	@Test
	public void applyTagNoForceNoBetterResult() throws TdtTranslationException {
		Tag tag = new Tag(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 });
		tag.setTagInfoDecoder(decoder());
		Map<Integer, Result> result = new HashMap<Integer, Result>();
		result.put(Integer.valueOf(1), new Result(ResultState.SUCCESS));
		tag.setResult(result);

		Tag tagToApply = new Tag(decode("urn:epc:tag:sgtin-96:3.0036000.123456.401"), new byte[] { 0x01, 0x02 });
		tagToApply.setTagInfoDecoder(decoder());
		tagToApply.setPc(new byte[] { 0x03, 0x04 });
		Map<Integer, Result> applyResult = new HashMap<Integer, Result>();
		applyResult.put(Integer.valueOf(1), new Result(ResultState.SUCCESS));
		tagToApply.setResult(applyResult);
		tag.apply(tagToApply, false);

		Assert.assertTrue(Arrays.equals(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 },
				tag.getEpc()));
		Assert.assertEquals("urn:epc:tag:sgtin-96:3.0036000.123456.400", tag.toString());
		Assert.assertNull(tag.getTid());
		Assert.assertNull(tag.getPc());
		Assert.assertNotNull(tag.getResult());
		Assert.assertEquals(1, tag.getResult().size());
		Assert.assertEquals(ResultState.SUCCESS, tag.getResult().get(Integer.valueOf(1)).getState());
	}

	@Test
	public void applyTagNoForceBetterResult() throws TdtTranslationException {
		Tag tag = new Tag(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 });
		tag.setTagInfoDecoder(decoder());
		Map<Integer, Result> result = new HashMap<Integer, Result>();
		result.put(Integer.valueOf(1), new Result(ResultState.MISC_ERROR_TOTAL));
		tag.setResult(result);
		Tag tagToApply = new Tag(decode("urn:epc:tag:sgtin-96:3.0036000.123456.401"), new byte[] { 0x01, 0x02 });
		tagToApply.setPc(new byte[] { 0x03, 0x04 });
		tagToApply.setTagInfoDecoder(decoder());
		Map<Integer, Result> applyResult = new HashMap<Integer, Result>();
		applyResult.put(Integer.valueOf(1), new Result(ResultState.SUCCESS));
		tagToApply.setResult(applyResult);
		tag.apply(tagToApply, false);

		Assert.assertTrue(Arrays.equals(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x91 },
				tag.getEpc()));
		Assert.assertEquals("urn:epc:tag:sgtin-96:3.0036000.123456.400", tag.toString()); // original
																						  // code
																						  // is
																						  // used
		Assert.assertTrue(Arrays.equals(new byte[] { 0x01, 0x02 }, tag.getTid()));
		Assert.assertTrue(Arrays.equals(new byte[] { 0x03, 0x04 }, tag.getPc()));
		Assert.assertNotNull(tag.getResult());
		Assert.assertEquals(1, tag.getResult().size());
		Assert.assertEquals(ResultState.SUCCESS, tag.getResult().get(Integer.valueOf(1)).getState());
	}

	@Test
	public void applyTagTest() {
		Tag tag1 = new Tag(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 });
		Tag tag2 = new Tag(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 });

		tag1.apply(tag2);
		Assert.assertEquals(0, tag1.getCount());
	}

	@Test
	public void equalsTest() {
		Tag one = new Tag(new byte[] { 1, 1 });
		Tag two = new Tag(new byte[] { 1, 1 });
		Assert.assertTrue(one.equals(two));
		one = new Tag(new byte[] { 1, 1 });
		one.setTid(new byte[] { 1, 1 });
		Assert.assertFalse(one.equals(two));
		one = new Tag(new byte[] { 1, 1 });
		two = new Tag(new byte[] { 1, 1 });
		one.setTid(new byte[] { 1, 1 });
		two.setTid(new byte[] { 1, 1 });
		Assert.assertTrue(one.equals(two));
		one = new Tag(new byte[] { 1, 1 });
		two = new Tag(new byte[] { 1, 1 });
		one.setTid(new byte[] { 1, 0 });
		two.setTid(new byte[] { 0, 1 });
		Assert.assertFalse(one.equals(two));
	}

	@Test
	public void compare() {
		byte[] a = new byte[] { 1 };
		byte[] b = a;
		Assert.assertTrue(Tag.compare(a, b));
		a = null;
		Assert.assertFalse(Tag.compare(a, b));
		a = new byte[] { 1 };
		b = null;
		Assert.assertFalse(Tag.compare(a, b));
		b = new byte[] { 1, 2, 3 };
		Assert.assertFalse(Tag.compare(a, b));
		a = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
		b = new byte[] { 1, 2, 3, 4, 5, 6, 7, 7, 9, 0 };
		Assert.assertFalse(Tag.compare(a, b));
		b = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
		Assert.assertTrue(Tag.compare(a, b));
	}

	@Test
	public void hashCodeTest() {
		Tag one = new Tag(new byte[] { 1, 1 });
		Tag two = new Tag(new byte[] { 1, 1 });
		Assert.assertEquals(one.hashCode(), two.hashCode());
		one = new Tag(new byte[] { 1, 1 });
		one.setTid(new byte[] { 1, 1 });
		Assert.assertNotEquals(one.hashCode(), two.hashCode());
		one = new Tag(new byte[] { 1, 1 });
		two = new Tag(new byte[] { 1, 1 });
		one.setTid(new byte[] { 1, 1 });
		two.setTid(new byte[] { 1, 1 });
		Assert.assertEquals(one.hashCode(), two.hashCode());

		one = new Tag(new byte[] { 1, 1 });
		two = new Tag(new byte[] { 1, 1 });
		one.setTid(new byte[] { 1, 0 });
		two.setTid(new byte[] { 0, 1 });
		Assert.assertNotEquals(one.hashCode(), two.hashCode());
	}

	@Test
	public void getId() {
		Tag one = new Tag(new byte[] { 1, 1 });
		Tag two = new Tag(new byte[] { 1, 1 });
		Assert.assertNotEquals(one.getId(), two.getId());
	}

	@Test
	public void getUri() throws TdtTranslationException {
		Tag tag = new Tag(decode("urn:epc:tag:sgtin-96:3.0036000.123456.400"));
		tag.setTagInfoDecoder(decoder());
		Assert.assertEquals("urn:epc:tag:sgtin-96:3.0036000.123456.400", tag.getUri());
	}

	@Test
	public void toStringTest() throws TdtTranslationException {
		Tag tag = new Tag(decode("urn:epc:tag:sgtin-96:3.0036000.123456.400"));
		tag.setTagInfoDecoder(decoder());
		Assert.assertEquals("urn:epc:tag:sgtin-96:3.0036000.123456.400", tag.toString());
	}

	@Test
	public void tagTest() throws TdtTranslationException {
		Tag tag = new Tag(decode("urn:epc:tag:sgtin-96:3.0036000.123456.400"), new byte[] { 0x01, 0x02 });
		tag.setCount(2);
		tag.setLastTime(new Date());
		havis.middleware.ale.base.Tag actual = tag.tag();
		Assert.assertTrue(Arrays.equals(new byte[] { 0x30, 0x74, 0x02, 0x32, (byte) 0x80, 0x78, (byte) 0x90, 0x00, 0x00, 0x00, 0x01, (byte) 0x90 },
				actual.getEpc()));
		Assert.assertTrue(Arrays.equals(new byte[] { 0x01, 0x02 }, actual.getTid()));
		Assert.assertEquals(tag.getFirstTime(), actual.getFirstSeen());
		Assert.assertEquals(2, actual.getCount());
	}

	@Test
	public void clone1() throws TdtTranslationException {
		Tag tag = new Tag(new byte[] { 0x01 });
		tag.setTagInfoDecoder(decoder());
		tag.setTid(new byte[] { 0x02 });
		tag.setPc(new byte[] { 0x03 });
		tag.setXPC(new byte[] { 0x04 });
		tag.setCount(2);
		tag.apply(new byte[] { 0x02 }); // sets originalCode
		tag.setSighting(new Sighting("a", (short) 2, 3, new Date(1)));
		tag.getSightings().put("x", new ArrayList<Sighting>());
		tag.getSightings().get("x").add(new Sighting("b", (short) 3, 4, new Date(0)));
		tag.setTimeout(1111);
		tag.setResult(new HashMap<Integer, Result>());
		tag.getResult().put(Integer.valueOf(0), new Result(ResultState.MISC_ERROR_TOTAL));
		tag.decodeItemData(3, new byte[] { (byte) 0x89, 0x22, 0x47, (byte) 0xE4, 0x08, (byte) 0xF1, (byte) 0x83, 0x03, 0x40 }, packedObjectDecoder());

		Tag cloned = tag.clone();

		Assert.assertEquals(tag.getId(), cloned.getId());
		Assert.assertEquals(tag.getTid(), cloned.getTid());
		Assert.assertEquals(tag.getPc(), cloned.getPc());
		Assert.assertEquals(tag.getXPC(), cloned.getXPC());
		Assert.assertEquals(tag.getCount(), cloned.getCount());
		Assert.assertEquals(tag.getSighting(), cloned.getSighting());
		Assert.assertNotSame(tag.getSighting(), cloned.getSighting());
		Assert.assertEquals(tag.getSightings(), cloned.getSightings());
		Assert.assertNotSame(tag.getSightings(), cloned.getSightings());
		Assert.assertEquals(tag.getTimeout(), cloned.getTimeout());
		Assert.assertEquals(tag.getResult(), cloned.getResult());
		Assert.assertNotSame(tag.getResult(), cloned.getResult());
		Assert.assertEquals(tag.getItemData(3), cloned.getItemData(3));
		Assert.assertNotSame(tag.getItemData(3), cloned.getItemData(3));
		Assert.assertEquals(tag.<ItemData>getItemData(3).getDataElements().get(0), cloned.<ItemData>getItemData(3).getDataElements().get(0));
		Assert.assertNotSame(tag.<ItemData>getItemData(3).getDataElements().get(0), cloned.<ItemData>getItemData(3).getDataElements().get(0));

		Assert.assertEquals("urn:epc:raw:8.x01", cloned.getUri());
	}

	@Test
	public void clone2() {
		Tag tag = new Tag(new byte[] { 0x01 });
		tag.setTagInfoDecoder(decoder());
		tag.setTid(new byte[] { 0x02 });
		tag.setPc(new byte[] { 0x03 });
		tag.setXPC(new byte[] { 0x04 });
		tag.setCount(2);
		tag.setSighting(new Sighting("a", (short) 2, 3, new Date(1)));
		tag.getSightings().put("x", new ArrayList<Sighting>());
		tag.getSightings().get("x").add(new Sighting("b", (short) 3, 4, new Date(0)));
		tag.setTimeout(1111);
		tag.setResult(new HashMap<Integer, Result>());
		tag.getResult().put(Integer.valueOf(0), new Result(ResultState.MISC_ERROR_TOTAL));

		Tag cloned = tag.clone();

		Assert.assertEquals(tag.getId(), cloned.getId());
		Assert.assertEquals(tag.getTid(), cloned.getTid());
		Assert.assertEquals(tag.getPc(), cloned.getPc());
		Assert.assertEquals(tag.getXPC(), cloned.getXPC());
		Assert.assertEquals(tag.getCount(), cloned.getCount());
		Assert.assertEquals(tag.getSighting(), cloned.getSighting());
		Assert.assertNotSame(tag.getSighting(), cloned.getSighting());
		Assert.assertEquals(tag.getSightings(), cloned.getSightings());
		Assert.assertNotSame(tag.getSightings(), cloned.getSightings());
		Assert.assertEquals(tag.getTimeout(), cloned.getTimeout());
		Assert.assertEquals(tag.getResult(), cloned.getResult());
		Assert.assertNotSame(tag.getResult(), cloned.getResult());

		Assert.assertEquals("urn:epc:raw:8.x01", cloned.getUri()); // code
	}

	@Test
	public void hasItemData() {
		Tag tag = new Tag();
		tag.decodeItemData(3, new byte[] { (byte) 0x89, 0x22, 0x47, (byte) 0xE4, 0x08, (byte) 0xF1, (byte) 0x83, 0x03, 0x40 }, packedObjectDecoder());

		Assert.assertTrue(tag.hasItemData(3));
	}

	@Test
	public void resetItemData() {
		Tag tag = new Tag();
		tag.decodeItemData(3, new byte[] { (byte) 0x89, 0x22, 0x47, (byte) 0xE4, 0x08, (byte) 0xF1, (byte) 0x83, 0x03, 0x40 }, packedObjectDecoder());

		Assert.assertTrue(tag.hasItemData(3));

		tag.resetItemData(3);
		Assert.assertFalse(tag.hasItemData(3));
	}

	@Test
	public void isExtended() {
		Tag.setExtended(false);
		Assert.assertFalse(Tag.isExtended());

		Tag.setExtended(true);
		Assert.assertTrue(Tag.isExtended());
	}

	@Test
	public void decodeItemDataForUserBank() {
		Tag tag = new Tag();

		tag.decodeItemData(3, new byte[] { (byte) 0x89, 0x1A, 0x10, 0x18, (byte) 0xF1, 0x20, 0x40, 0x00 }, packedObjectDecoder());

		Assert.assertTrue(tag.hasItemData(3));
	}

	@Test
	public void decodeItemDataWithException() {
		Tag tag = new Tag();

		try {
			tag.decodeItemData(1, new byte[] { (byte) 0x89, 0x16, 0x02, (byte) 0xC7, (byte) 0x89, 0x02, 0x00 }, packedObjectDecoder());
		} catch (Exception e) {
			Assert.assertSame(new UnsupportedOperationException(), e);
		}

	}
}
