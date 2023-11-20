package com.guiqu1aixi.kaisa.helper;

import static com.guiqu1aixi.kaisa.ConstantPool.CLASS_PATH;
import static com.guiqu1aixi.kaisa.ConstantPool.JAVA_COMMAND;

public class CommandLineHelper {

    public static String mainClass() {
        String name = System.getProperty(JAVA_COMMAND);
        System.out.println(JAVA_COMMAND + name);
        return name;
    }

    public static String classPath() {
        String path = System.getProperty(CLASS_PATH);
        System.out.println(CLASS_PATH + path);
        return path;
    }

}
