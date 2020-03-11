import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Xie Jian Xun
 */
public class Test {
    public static void main(String[] args) {
        Runnable runnable =
            (Runnable) Proxy.newProxyInstance(Test.class.getClassLoader(),
                new Class[]{Runnable.class}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("running ....");
                        return null;
                    }
                });
        runnable.run();
        System.out.println("Testing ........");
    }
}
