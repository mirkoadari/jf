package ee.ut.jf.concurrency;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class App {
    private static final String serviceUrl = "http://feeds.feedburner.com/r1_digitund?format=xml";
    public static void main(String[] args) throws MalformedURLException, SAXException, ParserConfigurationException {
      URL service = new URL(serviceUrl);
      try {
        SAXParser sax = SAXParserFactory.newInstance().newSAXParser();
        sax.parse(new InputSource(service.openStream()), new XMLHandler());
      }
      catch (IOException e) {
        System.out.println("Could not connect to " + serviceUrl);
      }
    }

}
