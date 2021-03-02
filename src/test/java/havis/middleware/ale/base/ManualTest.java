/*
 package havis.middleware.ale.base;
 

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.junit.Ignore;

import com.sun.tools.xjc.XJC2Task;

public class ManualTest {

	@Ignore
	public void test(String schema, String pkg, String... bindings) {
		XJC2Task task = new XJC2Task();
		task.setSchema("file:src/main/resources/xsd/" + schema);
		File target = new File("target/src");
		target.mkdir();
		task.setDestdir(new File("target/jaxb/src"));
		task.setPackage(pkg);
		task.setExtension(true);
		task.createClasspath().createPath().setPath("src/main/java");
		task.createArg().setValue("-npa");
		task.createArg().setValue("-verbose");
		task.createArg().setValue("-Xinterface");
		task.createArg().setValue("-Xconstructor");
		task.createArg().setValue("-Xinject-code");
		task.createArg().setValue("-Xannotate");
		for (String binding : bindings) {
			task.setBinding(binding);
		}
		try {
			task.execute();
		} catch (BuildException e) {
			e.printStackTrace();
		}
	}

	@Ignore
	public void testTM() {
		test("EPCglobal-ale-1_1-aletm.xsd", "havis.middleware.ale.service.tm",
				"file:src/main/resources/binding/EPCglobal-ale-1_1-aletm.xml",
				"file:target/jaxb/binding/EPCglobal.xml");
	}

	@Ignore
	public void testEC() {
		test("EPCglobal-ale-1_1-ale.xsd", "havis.middleware.ale.service.ec",
				"file:src/main/resources/binding/EPCglobal-ale-1_1-ale.xml",
				"file:target/jaxb/binding/EPCglobal-ale-1_1-common.xml",
				"file:target/jaxb/binding/EPCglobal.xml");
	}

}
*/