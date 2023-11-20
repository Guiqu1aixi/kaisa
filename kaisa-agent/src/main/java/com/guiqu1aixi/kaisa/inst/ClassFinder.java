package com.guiqu1aixi.kaisa.inst;

import com.guiqu1aixi.kaisa.SourcePool;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ClassFinder {

    private static final ConcurrentHashMap<String, Class<?>> TABLE;

    static {
        TABLE = new ConcurrentHashMap<>();
    }

    private final String name;

    private final byte[] classBytes;

    public ClassFinder(String name, byte[] classBytes) {
        this.name = name;
        this.classBytes = classBytes;
    }

    private Class<?> findClass() {
        if (TABLE.containsKey(name)) {
            return TABLE.get(name);
        }

        Class<?>[] classes = SourcePool.inst().getAllLoadedClasses();
        for (Class<?> clazz : classes) {
            String clazzName = clazz.getName();
            if (Objects.equals(name, clazzName)) {
                TABLE.put(name, clazz);
                return clazz;
            }
        }

        throw new RuntimeException("JVM 当前没有加载:" + name);
    }

    public void redefine() {
        try {
            System.out.println("******Class Redefine Start******");
            Instrumentation inst = SourcePool.inst();
            Class<?> clazz = findClass();
            System.out.println("Class Name:" + clazz.getName());
            ClassDefinition definition = new ClassDefinition(clazz, classBytes);
            inst.redefineClasses(definition);
            System.out.println("******Class Redefine End ******");
        } catch (Exception e) {
            System.err.println("Class Redefine Error:" + name);
        }
    }

}
