package ee.ut.jf.di;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DelegateProxy implements InvocationHandler {

    private final ComponentRepository cr;
    private final Object instance;
    private final String methodName;
    private final boolean trace;
    private final String delegateId;

    public DelegateProxy(ComponentRepository cr, Object instance, String method, boolean trace, String delegate) {
        this.cr = cr;
        this.instance = instance;
        this.methodName = method;
        this.trace = trace;
        this.delegateId = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (delegateId != null)
            cr.getComponent(delegateId).toString();

        if (trace)
            System.out.println("Calling " + instance.getClass().getSimpleName() + "." + methodName);

        return instance.getClass().getDeclaredMethod(methodName).invoke(instance, args);
    }
}
