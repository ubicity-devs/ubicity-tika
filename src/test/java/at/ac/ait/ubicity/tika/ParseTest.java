package at.ac.ait.ubicity.tika;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Test;
import org.xml.sax.SAXException;

public class ParseTest {

	private static Logger logger = Logger.getLogger(ParseTest.class);

	@Test
	public void parseDocx() throws IOException, SAXException, TikaException {
		InputStream stream = new FileInputStream("C:\\temp\\inka\\Beispieldokument.docx");
		AutoDetectParser parser = new AutoDetectParser();
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		try {
			parser.parse(stream, handler, metadata);

			logger.info("-----------   DOCX  ------------");
			logger.info(handler.toString());
		} finally {
			stream.close();
		}
	}

	@Test
	public void parsePdf() throws IOException, SAXException, TikaException {
		InputStream stream = new FileInputStream("C:/temp/inka/Beispieldokument.pdf");
		AutoDetectParser parser = new AutoDetectParser();
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		try {
			parser.parse(stream, handler, metadata);

			logger.info("-----------   PDF  ------------");
			logger.info(handler.toString());
		} finally {
			stream.close();
		}
	}
}