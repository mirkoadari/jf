package ee.ut.jf.di;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DelegateProxy implements InvocationHandler {

    private final Object instance;
    private final String methodName;
    private final boolean trace;

    public DelegateProxy(Object instance, String method, boolean trace) {
        this.instance = instance;
        this.methodName = method;
        this.trace = trace;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Field delegateField = BeanImpl.class.getDeclaredField("delegate");
        delegateField.setAccessible(true);
        Bean delegate = (Bean) delegateField.get(instance);

        if (delegate != null)
            delegate.toString();

        if (trace)
            System.out.println("Calling " + instance.getClass().getSimpleName() + "." + methodName);

        return instance.getClass().getDeclaredMethod(methodName).invoke(instance, args);
    }
}
