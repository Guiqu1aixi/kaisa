package com.guiqu1aixi.kaisa.redefine;

import java.util.Objects;

import static com.guiqu1aixi.kaisa.ConstantPool.EMPTIES;

/**
 * class隔离,避免污染
 */
public class KaisaClassLoader extends ClassLoader {

    static final ThreadLocal<byte[]> LOCAL;

    static {
        LOCAL = ThreadLocal.withInitial(() -> EMPTIES);
    }

    public KaisaClassLoader() {
        super();
    }

    @Override
    protected Class<?> findClass(String name) {
        System.out.println("Find Class:" + name);
        byte[] bytes = LOCAL.get();
        if ((bytes == EMPTIES) || Objects.isNull(bytes)) {
            throw new RuntimeException("Class Byte[]上下文不存在");
        }

        int len = bytes.length;
        return defineClass(name, bytes, 0, len);
    }

    public KaisaClassLoader fillClassBytes(byte[] array) {
        LOCAL.set(array);
        return this;
    }

    public boolean legalCheck(String name) {
        try {
            findClass(name);
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }

        LOCAL.remove();
        return true;
    }

}
