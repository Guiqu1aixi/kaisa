package com.guiqu1aixi.kaisa.helper;

import com.guiqu1aixi.kaisa.SourcePool;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.jar.JarFile;

import static com.guiqu1aixi.kaisa.ConstantPool.JAVA_HOME;
import static com.guiqu1aixi.kaisa.ConstantPool.SUN_TOOL;

public class SunToolsLoader {

    private static final SimpleImmutableEntry<Boolean, JarFile> ENTRY = new SimpleImmutableEntry<>(false, null);

    public void load() {
        Entry<Boolean, JarFile> entry = findSunTools();
        if (!entry.getKey()) {
            return;
        }

        SourcePool.inst().appendToBootstrapClassLoaderSearch(entry.getValue());
    }

    private Entry<Boolean, JarFile> findSunTools() {
        try {
            String javaHome = javaHome();
            File toolsFile = null;

            for (String child : SUN_TOOL) {
                toolsFile = new File(javaHome, child);
                if (toolsFile.exists()) {
                    System.out.println("Sun Tools:" + child);
                    break;
                }
            }

            if (Objects.isNull(toolsFile) || !toolsFile.exists()) {
                System.out.println("JAVA Sun Tools Find Fail");
                return ENTRY;
            }

            JarFile file = new JarFile(toolsFile);
            return new SimpleImmutableEntry<>(true, file);
        } catch (IOException e) {
            System.out.println("JAVA Sun Tools Find Error");
            e.printStackTrace();
        }

        return ENTRY;
    }

    private String javaHome() {
        return System.getenv(JAVA_HOME);
    }

}
