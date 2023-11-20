package com.guiqu1aixi.kaisa.watch;

import com.guiqu1aixi.kaisa.inst.ClassFinder;
import com.guiqu1aixi.kaisa.redefine.ClassByteReader;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

import java.io.File;

import static com.guiqu1aixi.kaisa.ConstantPool.EMPTIES;
import static com.guiqu1aixi.kaisa.ConstantPool.FILE_SUFFIX;

public class ChangeListener extends FileAlterationListenerAdaptor {

    private final String path;

    public ChangeListener(String path) {
        this.path = path;
    }

    public void onFileChange(File file) {
        System.out.println("监控到文件变化:" + file.getPath());

        try {
            if (!file.getName().contains(FILE_SUFFIX)) {
                System.out.println("非Java文件:" + file.getPath());
                return;
            }

            ClassByteReader reader = new ClassByteReader(file, path);
            byte[] bytes = reader.readClass();
            String name = reader.className();

            if (bytes == EMPTIES) {
                System.out.println("类文件校验失败:" + file.getPath());
                return;
            }

            ClassFinder classFinder = new ClassFinder(name, bytes);
            classFinder.redefine();
        } catch (Exception e) {
            System.err.println("Class Bytes读取失败");
            e.printStackTrace();
        }
    }

}
