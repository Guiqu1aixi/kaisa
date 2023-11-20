package com.guiqu1aixi.kaisa;

import com.guiqu1aixi.kaisa.watch.ChangeMonitor;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class SourcePool {

    private static final CountDownLatch LATCH;

    private static final AtomicReference<Instrumentation> INST_REF;

    private static final AtomicReference<ChangeMonitor> MONITOR_REF;

    static {
        INST_REF = new AtomicReference<>();
        MONITOR_REF = new AtomicReference<>();
        LATCH = new CountDownLatch(1);
    }

    public static void monitor(ChangeMonitor m) {
        MONITOR_REF.set(m);
    }

    public static Instrumentation inst() {
        try {
            LATCH.await();
        } catch (InterruptedException e) {
            throw new RuntimeException("Instrumentation获取失败");
        }
        return INST_REF.get();
    }

    public static void inst(Instrumentation inst) {
        INST_REF.set(inst);
        LATCH.countDown();
    }

}
