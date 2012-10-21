package ee.ut.jf.di.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "bean")
public class XmlBean {

    @XmlAttribute
    public String id;
    
    @XmlAttribute(name = "class")
    public String className;

    @XmlAttribute
    public String method;

    @XmlAttribute
    public boolean trace;

    @XmlElement(name = "bean")
    public Dependency dependency;

}
