package havis.middleware.ale.base;

import havis.middleware.ale.base.report.ReportConstants;
import havis.middleware.ale.service.ECReaderStat;
import havis.middleware.ale.service.ECSightingStat;
import havis.middleware.ale.service.EPC;
import havis.middleware.ale.service.cc.CCCmdReport;
import havis.middleware.ale.service.cc.CCCmdReport.TagReports;
import havis.middleware.ale.service.cc.CCOpReport;
import havis.middleware.ale.service.cc.CCReports;
import havis.middleware.ale.service.cc.CCReports.CmdReports;
import havis.middleware.ale.service.cc.CCTagCountStat;
import havis.middleware.ale.service.cc.CCTagReport;
import havis.middleware.ale.service.cc.CCTagReport.OpReports;
import havis.middleware.ale.service.cc.CCTagStat;
import havis.middleware.ale.service.cc.CCTagTimestampStat;
import havis.middleware.ale.service.ec.ECReport;
import havis.middleware.ale.service.ec.ECReportGroup;
import havis.middleware.ale.service.ec.ECReportGroupList;
import havis.middleware.ale.service.ec.ECReportGroupListMember;
import havis.middleware.ale.service.ec.ECReportGroupListMemberExtension;
import havis.middleware.ale.service.ec.ECReports;
import havis.middleware.ale.service.ec.ECReports.Reports;
import havis.middleware.ale.service.ec.ECSightingSignalStat;
import havis.middleware.ale.service.ec.ECTagCountStat;
import havis.middleware.ale.service.ec.ECTagStat;
import havis.middleware.ale.service.ec.ECTagTimestampStat;
import havis.middleware.ale.service.pc.Execute;
import havis.middleware.ale.service.pc.PCEventCountStat;
import havis.middleware.ale.service.pc.PCEventReport;
import havis.middleware.ale.service.pc.PCEventStat;
import havis.middleware.ale.service.pc.PCEventTimestampStat;
import havis.middleware.ale.service.pc.PCOpReport;
import havis.middleware.ale.service.pc.PCOpReports;
import havis.middleware.ale.service.pc.PCOpSpec;
import havis.middleware.ale.service.pc.PCOpSpecs;
import havis.middleware.ale.service.pc.PCReport;
import havis.middleware.ale.service.pc.PCReport.EventReports;
import havis.middleware.ale.service.pc.PCReports;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializationTest {

	@Test
	public void testSubClassSerialization() throws Exception {
		// test if ECTagCountStat (which is a sub class of ECTagStat) is correctly serialized and deserialized.

		// first try deserialization
		JAXBContext context = JAXBContext.newInstance(ECReport.class);
		StringReader reader = new StringReader(
				"<report xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" reportName=\"Report\"><group><groupList><member><epc>urn:epc:id:sgtin:426025746.0001.1</epc><extension><stats><stat xsi:type=\"q1:ECTagCountStat\" xmlns:q1=\"urn:havis:ale:xsd:1\"><profile>TagCount</profile><count>12</count></stat></stats></extension></member></groupList></group></report>");
		ECReport report = context.createUnmarshaller().unmarshal(new StreamSource(reader), ECReport.class).getValue();

		Assert.assertEquals("ECTagCountStat", report.getGroup().get(0).getGroupList().getMember().get(0).getExtension().getStats().getStat().get(0).getClass()
				.getSimpleName());

		// now try serialization
		ECTagCountStat stat = new ECTagCountStat();
		stat.setProfile("TagCount");
		stat.setCount(12);
		report.getGroup().get(0).getGroupList().getMember().get(0).getExtension().getStats().getStat().set(0, stat);

		Assert.assertEquals("ECTagCountStat", report.getGroup().get(0).getGroupList().getMember().get(0).getExtension().getStats().getStat().get(0).getClass()
				.getSimpleName());

		StringWriter writer = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<ECReport>(new QName("urn:epcglobal:ale:xsd:1", "ECReport"), ECReport.class, null, report), writer);

		Assert.assertTrue(writer.toString().contains("<profile>TagCount</profile><count>12</count>"));
	}

	@Test
	public void testPcExecute() throws Exception {
		JAXBContext context = JAXBContext.newInstance(Execute.class);

		Execute execute = new Execute();
		execute.setOpSpecs(new PCOpSpecs());
		PCOpSpec spec1 = new PCOpSpec();
		spec1.setOpName("test1");
		execute.getOpSpecs().getOpSpec().add(spec1);
		PCOpSpec spec2 = new PCOpSpec();
		spec2.setOpName("test2");
		execute.getOpSpecs().getOpSpec().add(spec2);

		StringWriter writer = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<Execute>(new QName("urn:epcglobal:ale:xsd:1", "Execute"), Execute.class, null, execute), writer);

		Assert.assertTrue(writer.toString().contains("<opSpecs><opSpec><opName>test1</opName></opSpec><opSpec><opName>test2</opName></opSpec></opSpecs>"));
	}

	@Test
	public void testEcStatistics() throws Exception {
		ECReports report = new ECReports();
		report.setReports(new Reports());
		ECReport r = new ECReport();
		ECReportGroup g = new ECReportGroup();
		ECReportGroupList l = new ECReportGroupList();
		ECReportGroupListMember m = new ECReportGroupListMember();
		m.setEpc(new EPC("test"));
		ECReportGroupListMemberExtension e = new ECReportGroupListMemberExtension();

		ArrayList<ECTagStat> stats = new ArrayList<ECTagStat>();
		ECTagTimestampStat time = new ECTagTimestampStat();
		time.setProfile(ReportConstants.TagTimestampsProfileName);
		time.setFirstSightingTime(new Date(0));
		time.setLastSightingTime(new Date(1000));
		stats.add(time);

		ECTagCountStat count = new ECTagCountStat();
		count.setProfile(ReportConstants.TagCountProfileName);
		count.setCount(3);
		stats.add(count);

		ECTagStat names = new ECTagStat();
		names.setProfile(ReportConstants.ReaderNamesProfileName);
		ArrayList<ECReaderStat> readerStats = new ArrayList<ECReaderStat>();
		ECReaderStat readerStat1 = new ECReaderStat();
		readerStat1.setReaderName("reader1");
		readerStats.add(readerStat1);
		ECReaderStat readerStat2 = new ECReaderStat();
		readerStat2.setReaderName("reader2");
		readerStats.add(readerStat2);
		names.setStatBlockList(readerStats);
		stats.add(names);

		ECTagStat sightings = new ECTagStat();
		sightings.setProfile(ReportConstants.ReaderSightingSignalsProfileName);
		ArrayList<ECReaderStat> sightingStats = new ArrayList<ECReaderStat>();
		ArrayList<ECSightingStat> sightings1 = new ArrayList<ECSightingStat>();
		sightings1.add(new ECSightingSignalStat("reader1", 1, 10, new Date(0)));
		sightings1.add(new ECSightingSignalStat("reader1", 2, 11, new Date(0)));
		sightings1.add(new ECSightingSignalStat("reader1", 3, 12, new Date(0)));
		sightingStats.add(new ECReaderStat("reader1", sightings1));
		ArrayList<ECSightingStat> sightings2 = new ArrayList<ECSightingStat>();
		sightings2.add(new ECSightingSignalStat("reader2", 1, 13, new Date(0)));
		sightings2.add(new ECSightingSignalStat("reader2", 2, 14, new Date(0)));
		sightings2.add(new ECSightingSignalStat("reader2", 3, 15, new Date(0)));
		sightingStats.add(new ECReaderStat("reader2", sightings2));
		sightings.setStatBlockList(sightingStats);
		stats.add(sightings);

		e.setECTagStatList(stats);
		m.setExtension(e);
		l.getMember().add(m);
		g.setGroupList(l);
		r.getGroup().add(g);
		report.getReports().getReport().add(r);

		JAXBContext context = JAXBContext.newInstance(ECReports.class);
		StringWriter writer = new StringWriter();
		context.createMarshaller()
				.marshal(new JAXBElement<ECReports>(new QName("urn:epcglobal:ale:xsd:1", "ECReports"), ECReports.class, null, report), writer);
		String xml1 = writer.toString();

		ECReports resultXml = context.createUnmarshaller().unmarshal(new StreamSource(new StringReader(writer.toString())), ECReports.class).getValue();
		writer = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<ECReports>(new QName("urn:epcglobal:ale:xsd:1", "ECReports"), ECReports.class, null, resultXml),
				writer);
		String xml2 = writer.toString();
		Assert.assertEquals(xml1, xml2);

		ObjectMapper mapper = new ObjectMapper();
		writer = new StringWriter();
		mapper.writeValue(writer, report);
		String json1 = writer.toString();

		ECReports resultJson = mapper.readValue(json1, ECReports.class);
		writer = new StringWriter();
		mapper.writeValue(writer, resultJson);
		String json2 = writer.toString();
		Assert.assertEquals(json1, json2);
	}

	@Test
	public void testPcStatistics() throws Exception {
		PCReports report = new PCReports();
		report.setReports(new havis.middleware.ale.service.pc.PCReports.Reports());
		PCReport r = new PCReport();
		r.setEventReports(new EventReports());
		PCEventReport e = new PCEventReport();
		e.setOpReports(new PCOpReports());
		PCOpReport op = new PCOpReport();
		op.setOpName("OP1");
		op.setOpStatus("SUCCESS");
		op.setState(Boolean.TRUE);
		e.getOpReports().getOpReport().add(op);

		ArrayList<PCEventStat> stats = new ArrayList<PCEventStat>();
		PCEventTimestampStat time = new PCEventTimestampStat();
		time.setProfile(ReportConstants.EventTimestampsProfileName);
		time.setFirstSightingTime(new Date(0));
		time.setLastSightingTime(new Date(1000));
		stats.add(time);

		PCEventCountStat count = new PCEventCountStat();
		count.setProfile(ReportConstants.EventCountProfileName);
		count.setCount(3);
		stats.add(count);

		PCEventStat names = new PCEventStat();
		names.setProfile(ReportConstants.ReaderNamesProfileName);
		ArrayList<ECReaderStat> readerStats = new ArrayList<ECReaderStat>();
		ECReaderStat readerStat1 = new ECReaderStat();
		readerStat1.setReaderName("reader1");
		readerStats.add(readerStat1);
		ECReaderStat readerStat2 = new ECReaderStat();
		readerStat2.setReaderName("reader2");
		readerStats.add(readerStat2);
		names.setStatBlockList(readerStats);
		stats.add(names);

		e.setPCEventStatList(stats);
		r.getEventReports().getEventReport().add(e);
		report.getReports().getReport().add(r);

		JAXBContext context = JAXBContext.newInstance(PCReports.class);
		StringWriter writer = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<PCReports>(new QName("urn:havis:ale:xsd:1", "PCReports"), PCReports.class, null, report), writer);
		String xml1 = writer.toString();

		PCReports resultXml = context.createUnmarshaller().unmarshal(new StreamSource(new StringReader(writer.toString())), PCReports.class).getValue();
		writer = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<PCReports>(new QName("urn:havis:ale:xsd:1", "PCReports"), PCReports.class, null, resultXml), writer);
		String xml2 = writer.toString();
		Assert.assertEquals(xml1, xml2);

		ObjectMapper mapper = new ObjectMapper();
		writer = new StringWriter();
		mapper.writeValue(writer, report);
		String json1 = writer.toString();

		PCReports resultJson = mapper.readValue(json1, PCReports.class);
		writer = new StringWriter();
		mapper.writeValue(writer, resultJson);
		String json2 = writer.toString();
		Assert.assertEquals(json1, json2);
	}

	@Test
	public void testCcStatistics() throws Exception {
		CCReports report = new CCReports();
		report.setCmdReports(new CmdReports());
		CCCmdReport r = new CCCmdReport();
		r.setTagReports(new TagReports());
		CCTagReport t = new CCTagReport();
		t.setOpReports(new OpReports());
		CCOpReport op = new CCOpReport();
		op.setOpName("OP1");
		op.setOpStatus("SUCCESS");
		op.setData("8:xFF");
		t.getOpReports().getOpReport().add(op);

		ArrayList<CCTagStat> stats = new ArrayList<CCTagStat>();
		CCTagTimestampStat time = new CCTagTimestampStat();
		time.setProfile(ReportConstants.TagTimestampsProfileName);
		time.setFirstSightingTime(new Date(0));
		time.setLastSightingTime(new Date(1000));
		stats.add(time);

		CCTagCountStat count = new CCTagCountStat();
		count.setProfile(ReportConstants.TagCountProfileName);
		count.setCount(3);
		stats.add(count);

		CCTagStat names = new CCTagStat();
		names.setProfile(ReportConstants.ReaderNamesProfileName);
		ArrayList<ECReaderStat> readerStats = new ArrayList<ECReaderStat>();
		ECReaderStat readerStat1 = new ECReaderStat();
		readerStat1.setReaderName("reader1");
		readerStats.add(readerStat1);
		ECReaderStat readerStat2 = new ECReaderStat();
		readerStat2.setReaderName("reader2");
		readerStats.add(readerStat2);
		names.setStatBlockList(readerStats);
		stats.add(names);

		CCTagStat sightings = new CCTagStat();
		sightings.setProfile(ReportConstants.ReaderSightingSignalsProfileName);
		ArrayList<ECReaderStat> sightingStats = new ArrayList<ECReaderStat>();
		ArrayList<ECSightingStat> sightings1 = new ArrayList<ECSightingStat>();
		sightings1.add(new ECSightingSignalStat("reader1", 1, 10, new Date(0)));
		sightings1.add(new ECSightingSignalStat("reader1", 2, 11, new Date(0)));
		sightings1.add(new ECSightingSignalStat("reader1", 3, 12, new Date(0)));
		sightingStats.add(new ECReaderStat("reader1", sightings1));
		ArrayList<ECSightingStat> sightings2 = new ArrayList<ECSightingStat>();
		sightings2.add(new ECSightingSignalStat("reader2", 1, 13, new Date(0)));
		sightings2.add(new ECSightingSignalStat("reader2", 2, 14, new Date(0)));
		sightings2.add(new ECSightingSignalStat("reader2", 3, 15, new Date(0)));
		sightingStats.add(new ECReaderStat("reader2", sightings2));
		sightings.setStatBlockList(sightingStats);
		stats.add(sightings);

		t.setCCTagStatList(stats);
		r.getTagReports().getTagReport().add(t);
		report.getCmdReports().getCmdReport().add(r);

		JAXBContext context = JAXBContext.newInstance(CCReports.class);
		StringWriter writer = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<CCReports>(new QName("urn:epcglobal:ale:xsd:1", "CCReports"), CCReports.class, null, report), writer);
		String xml1 = writer.toString();

		CCReports resultXml = context.createUnmarshaller().unmarshal(new StreamSource(new StringReader(writer.toString())), CCReports.class).getValue();
		writer = new StringWriter();
		context.createMarshaller().marshal(new JAXBElement<CCReports>(new QName("urn:epcglobal:ale:xsd:1", "CCReports"), CCReports.class, null, resultXml), writer);
		String xml2 = writer.toString();
		Assert.assertEquals(xml1, xml2);

		ObjectMapper mapper = new ObjectMapper();
		writer = new StringWriter();
		mapper.writeValue(writer, report);
		String json1 = writer.toString();

		CCReports resultJson = mapper.readValue(json1, CCReports.class);
		writer = new StringWriter();
		mapper.writeValue(writer, resultJson);
		String json2 = writer.toString();
		Assert.assertEquals(json1, json2);
	}
}
