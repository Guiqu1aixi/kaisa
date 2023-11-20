package com.guiqu1aixi.kaisa;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public interface ConstantPool {

    String EMPTY = "";

    String SPACE = " ";

    String TARGET = "target";

    String BUILD = "build";

    String PATH = "/src/main/java/com";

    String MODE = "r";

    char DOT = '.';

    byte[] EMPTIES = new byte[]{};

    String AT = "@";

    String FILE_SUFFIX = ".java";

    String CLASS_SUFFIX = ".class";

    String CLASS_PREFIX = "main" + DOT + "java" + DOT;

    String ROOT_PATH = System.getProperty("user.home");

    String KAISA_PATH = ROOT_PATH + File.separator + "kaisa";

    String JAVA_COMMAND = "sun.java.command";

    String CLASS_PATH = "java.class.path";

    String JAVA_HOME = "JAVA_HOME";

    List<String> SUN_TOOL = Arrays.asList("lib/tools.jar", "../lib/tools.jar", "../../lib/tools.jar");

    String OPTIONS = "-classpath";

    String MANIFEST = "META-INF/MANIFEST.MF";

    String JAR_ATTRIBUTES_KEY = "Class-Path";

    String WINDOWS_FILE_PROTOCOL = "file:/";

}
