package com.github.yvkm.javaagent;

import java.lang.instrument.Instrumentation;

/**
 * @author Xie Jian Xun
 */
public class TransformerRegister {

    /**
     * 在JVM运行前指定参数：-javaagent:/path/xxx.jar=arg0&args1&...
     * JVM会启动时注册这些java代理，并存加载类时触发这些代理的transform方法
     * @param agentArgs
     * @param inst
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("register FindProxyClass to find proxy class");
        inst.addTransformer(new FindProxyClass(agentArgs));
    }
}
