package com.guiqu1aixi.kaisa.watch;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

public class ChangeMonitor {

    private static final long INTERVAL = 3000L;

    private final FileAlterationMonitor monitor;

    private final ChangeListener listener;

    public ChangeMonitor(String classPath) {
        monitor = new FileAlterationMonitor(INTERVAL);
        listener = new ChangeListener(classPath);
    }

    public ChangeMonitor start(String path) {
        try {
            File directory = new File(path);
            FileAlterationObserver observer = new FileAlterationObserver(directory);
            observer.addListener(listener);
            monitor.addObserver(observer);
            monitor.start();
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
