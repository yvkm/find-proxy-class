package com.github.yvkm.javaagent;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;


/**
 * @author Xie Jian Xun
 */
public class FindProxyClass implements ClassFileTransformer {
    private String[] args;

    public FindProxyClass(String args) {
        this.args = args.split("&");
    }

    /**
     * 对JVM类加载时读取到的class文件的字节码进入处理，这其实就是一个拦截器
     * <p>
     * 本方法只是将某些动态代理生成的类打印到指定的位置。
     *
     * @param loader              类加载器
     * @param className           JVM的类名如：java/lang/String
     * @param classBeingRedefined 被处理的类对象
     * @param protectionDomain
     * @param classFileBuffer     class文件存储在这个buffer里
     * @return
     * @throws IllegalClassFormatException
     */
    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classFileBuffer) throws IllegalClassFormatException {
        System.out.println("checking class:" + className);

        // 将配置的类输出到指定文件夹中
        if (className.contains(args[0]) || className.matches(args[0])) {
            try {
                String fileName = className.replaceAll("/", ".");
                writeByteArrayToFile(args[1], fileName, classFileBuffer);
            } catch (IOException e) {
                System.out.println("出现一些问题：" + e.getMessage());
            }
        }
        return classFileBuffer;
    }

    private void writeByteArrayToFile(String path, String fileName,
                                      byte[] classFileBuffer) throws IOException {
        File storePath = new File(path);
        if (!storePath.exists()) {
            storePath.mkdirs();
        }
        System.out.println(System.getProperty("user.dir"));
        String file = path + File.separator + fileName + ".class";

        System.out.println("writing file: " + file + "in " + System.getProperty("user.dir"));
        try (FileOutputStream fos =
                 new FileOutputStream(new File(file))) {
            fos.write(classFileBuffer);
        }
    }
}
