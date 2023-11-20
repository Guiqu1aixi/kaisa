package com.guiqu1aixi.kaisa.helper;

import com.guiqu1aixi.kaisa.Deduce;
import com.guiqu1aixi.kaisa.SourcePool;
import com.guiqu1aixi.kaisa.watch.ChangeMonitor;

import java.util.Objects;

public class CommandActuator extends Thread {

    @Override
    public void run() {
        try {
            System.out.println("后台推断线程启动");
            Thread.sleep(3000);

            String mainClassName = CommandLineHelper.mainClass();
            if (Objects.isNull(mainClassName) || mainClassName.isEmpty()) {
                System.err.println("CommandActuator Match Fail");
                return;
            }

            new SunToolsLoader().load();

            Deduce deduce = new Deduce(mainClassName);
            String codePath = deduce.codePath();
            String classPath = deduce.classPath();
            ChangeMonitor start = new ChangeMonitor(classPath).start(codePath);
            SourcePool.monitor(start);
            System.out.println("后台推断线程完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
