package ee.ut.jf.di.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="bean")
public class Dependency {

    @XmlAttribute(name = "id")
    public String id;

}
