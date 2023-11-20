package com.guiqu1aixi.kaisa;

import com.guiqu1aixi.kaisa.helper.CommandActuator;

import java.lang.instrument.Instrumentation;

public class JvmAgent {

    public static void premain(String args, Instrumentation inst) {
        System.out.println("Java 探针初始化开始");
        SourcePool.inst(inst);
        new CommandActuator().start();
        System.out.println(System.getProperty("sun.java.command"));
        System.out.println("Java 探针初始化完成");
    }

}
