package ee.ut.jf.concurrency;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.net.MalformedURLException;
import java.net.URL;

public class XMLHandler extends DefaultHandler {
  private final TaskExecutor executor = new TaskExecutor();

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    if (qName.equals("enclosure")) {
      String url = attributes.getValue(attributes.getIndex("url"));
      try {
        executor.submit(new URL(url));
      }
      catch (MalformedURLException e) {
        System.out.println("ERROR! Malformed URL: " + url);
      }
    }
  }

  @Override
  public void endDocument() throws SAXException {
    executor.shutdown();
  }

}
