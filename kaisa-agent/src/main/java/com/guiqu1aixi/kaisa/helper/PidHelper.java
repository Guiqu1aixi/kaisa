package com.guiqu1aixi.kaisa.helper;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import static com.guiqu1aixi.kaisa.ConstantPool.AT;

public class PidHelper {

    public static String pid() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String processName = runtimeMXBean.getName();
        String pid = processName.split(AT)[0];
        System.out.println("Process Pid:" + pid);
        return pid;
    }

}
