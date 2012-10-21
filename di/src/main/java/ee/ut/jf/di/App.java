package ee.ut.jf.di;

import ee.ut.A;
import ee.ut.jf.di.xml.BeanStore;

import javax.xml.bind.JAXBContext;
import java.io.File;

public class App {

  public static void main(String[] args) throws Exception {
      JAXBContext context = JAXBContext.newInstance(BeanStore.class);
      BeanStore beans = (BeanStore) context.createUnmarshaller().unmarshal(new File(args[0]));
      ComponentRepository cr = new ComponentRepository(beans);
      cr.autowire();

      Bean c = (Bean) cr.getComponent("A");
      c.toString();
  }

}
