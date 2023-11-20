package com.guiqu1aixi.kaisa.helper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import static com.guiqu1aixi.kaisa.ConstantPool.*;

public class GradleCommandHelper {

    public static String classPath() {
        String tempJarPath = tempJarPath();
        if (tempJarPath.isEmpty()) {
            return EMPTY;
        }

        return jarClassPath(tempJarPath);
    }

    private  static String tempJarPath() {
        String classPathLine = CommandLineHelper.classPath();
        if (Objects.isNull(classPathLine) || classPathLine.isEmpty()) {
            return EMPTY;
        }

        String[] split = classPathLine.split(File.pathSeparator);
        String tempJarPath = split[0];
        System.out.println("Gradle Temp Jar Path:" + tempJarPath);
        return tempJarPath;
    }

    private static String jarClassPath(String jarPath) {
        String res = EMPTY;

        try (
                JarFile jarFile = new JarFile(jarPath)
        ) {
            JarEntry manifestEntry = jarFile.getJarEntry(MANIFEST);
            if (Objects.isNull(manifestEntry)) {
                System.out.println("META-INF/MANIFEST.MF Not Exist");
                return res;
            }

            try (
                    InputStream inputStream = jarFile.getInputStream(manifestEntry)
            ) {
                Manifest manifest = new Manifest(inputStream);
                String paths = manifest.getMainAttributes().getValue(JAR_ATTRIBUTES_KEY);
                if (Objects.isNull(paths) || paths.isEmpty()) {
                    System.out.println("MANIFEST.MF Class-Path Is Null");
                    return res;
                }

                res = paths.replace(WINDOWS_FILE_PROTOCOL, EMPTY).replace(SPACE, File.pathSeparator);
                System.out.println("MANIFEST File ClassPath:" + res);
            }
        } catch (IOException e) {
            throw new RuntimeException("MANIFEST File Read Exception");
        }

        return res;
    }

}
