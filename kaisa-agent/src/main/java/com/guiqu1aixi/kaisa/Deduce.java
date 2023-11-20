package com.guiqu1aixi.kaisa;

import com.guiqu1aixi.kaisa.helper.CommandLineHelper;
import com.guiqu1aixi.kaisa.helper.GradleCommandHelper;

import java.io.File;
import java.security.CodeSource;
import java.util.Objects;

import static com.guiqu1aixi.kaisa.ConstantPool.*;

public class Deduce {

    private final String mainName;

    private boolean isGrable;

    public Deduce(String mainName) {
        this.mainName = mainName;
    }

    public String codePath() {
        try {
            Class<?> mainClass = Class.forName(mainName);
            String sourcePath = sourcePath(mainClass);
            String path = codePath(sourcePath);
            System.out.println("源代码路径:" + path);
            return path;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String classPath() {
        if (isGrable) {
            System.out.println("Build Tool Gradle");
            return GradleCommandHelper.classPath();
        }

        System.out.println("Build Tool Maven");
        return CommandLineHelper.classPath();
    }

    private String sourcePath(Class<?> currentClass) {
        /* 获取当前类的保护域ProtectionDomain */
        CodeSource codeSource = currentClass
                .getProtectionDomain()
                .getCodeSource();

        return codeSource
                .getLocation()
                .getPath();
    }

    @SuppressWarnings("all")
    private String codePath(String sourcePath) {
        String path = EMPTY;

        File parent = new File(sourcePath);
        while (Objects.nonNull(parent)) {
            String name = parent.getName();
            parent = parent.getParentFile();
            if (Objects.equals(name, TARGET) || (isGrable = Objects.equals(name, BUILD))) {
                path = parent.getPath();
                break;
            }
        }

        if (path == EMPTY) {
            throw new RuntimeException("Class Direct Path解析失败");
        }

        return path + PATH;
    }

}
