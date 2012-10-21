package ee.ut.jf.di;

import ee.ut.jf.di.xml.BeanStore;
import ee.ut.jf.di.xml.XmlBean;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ComponentRepository {
    private final BeanStore store;
    private final Map<String, Bean> components = new HashMap<String, Bean>();

    public ComponentRepository(BeanStore store) {
        this.store = store;
    }

    public Bean getComponent(String id) {
        return components.get(id);
    }

    public void autowire() throws Exception {
        Map<String, BeanImpl> instances = new HashMap<String, BeanImpl>();

        for (XmlBean xml : store.beans) {
            Class<?> clazz = Class.forName(xml.className);
            Object instance = clazz.newInstance();
            instances.put(xml.id, (BeanImpl) instance);

            String delegateId = xml.dependency == null ? null : xml.dependency.id;

            DelegateProxy proxy = new DelegateProxy(instance, xml.method, xml.trace);

            Bean bean = (Bean) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{Bean.class}, proxy);

            components.put(xml.id, bean);
        }

        for (XmlBean xml : store.beans)
            if (xml.dependency != null)
                instances.get(xml.id).setDelegate(components.get(xml.dependency.id));
    }

}
