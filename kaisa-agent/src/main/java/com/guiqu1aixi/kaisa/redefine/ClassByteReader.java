package com.guiqu1aixi.kaisa.redefine;

import com.guiqu1aixi.kaisa.KaisaCompiler;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static com.guiqu1aixi.kaisa.ConstantPool.*;

public class ClassByteReader {

    private final File file;

    private final String path;

    public ClassByteReader(File file, String path) {
        this.file = file;
        this.path = path;
    }

    public byte[] readClass() {
        try {
            KaisaCompiler compiler = new KaisaCompiler(file, path);
            boolean success = compiler.compiler();
            if (!success) {
                return EMPTIES;
            }

            String filePath = className().replace(DOT, File.separatorChar);
            String compilerPath = KAISA_PATH + File.separatorChar + filePath + CLASS_SUFFIX;
            File compilerFile = new File(compilerPath);
            try (
                    RandomAccessFile accessFile = new RandomAccessFile(compilerFile, MODE)
            ) {
                byte[] bytes = new byte[(int) accessFile.length()];
                accessFile.readFully(bytes);
                return bytes;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return EMPTIES;
        }
    }

    public String className() {
        String filePath = file.getPath();
        filePath = filePath.replace(File.separatorChar, DOT);
        int index = filePath.indexOf(CLASS_PREFIX);
        return filePath.substring(index + CLASS_PREFIX.length()).replace(FILE_SUFFIX, EMPTY);
    }

}
