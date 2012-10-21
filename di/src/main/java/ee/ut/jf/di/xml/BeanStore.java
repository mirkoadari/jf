package ee.ut.jf.di.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "beans")
public class BeanStore {

    @XmlElement(name = "bean")
    public List<XmlBean> beans;

}
