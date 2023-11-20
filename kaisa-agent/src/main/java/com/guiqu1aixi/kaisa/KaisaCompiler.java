package com.guiqu1aixi.kaisa;

import javax.tools.*;
import javax.tools.JavaCompiler.CompilationTask;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.guiqu1aixi.kaisa.ConstantPool.OPTIONS;

public class KaisaCompiler {

    private final File javaFile;

    private final String path;

    public KaisaCompiler(File file, String path) {
        this.javaFile = file;
        this.path = path;
    }

    public boolean compiler() {
        /* 获取系统默认的 Java 编译器 */
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (Objects.isNull(compiler)) {
            System.out.println("System Java Compiler is Null");
            return false;
        }

        CompilationTask task;
        /* 获取标准文件管理器 */
        try (
                StandardJavaFileManager fileManager =
                        compiler.getStandardFileManager(null, null, null)
        ) {
            fileManager.setLocation(StandardLocation.CLASS_OUTPUT, outputDirs());

            /* 创建编译任务 */
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(javaFile);
            List<String> options = Arrays.asList(OPTIONS, path);

            System.out.println("++++++++++++++++++++++++++++");
            System.out.println(path);
            System.out.println("++++++++++++++++++++++++++++");

            task = compiler.getTask(null, fileManager, null, options, null, compilationUnits);
        } catch (Exception e) {
            return false;
        }

        return task.call();
    }

    @SuppressWarnings("all")
    List<File> outputDirs() {
        String path = ConstantPool.KAISA_PATH;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        return Collections.singletonList(file);
    }

}
