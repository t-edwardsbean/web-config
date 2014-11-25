package com.baidu.testframework.app;

import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;

import java.io.File;
import java.io.IOException;

/**
 * Created by edwardsbean on 14-11-10.
 */
public class ExecutorRunner {
    private Process process = null;
    private String workDir;

    public ExecutorRunner(String workDir) {
        this.workDir = workDir;
    }

    public void start() {
        Thread workerThread = new Thread("ExecutorRunner for ") {
            @Override
            public void run() {
                runProcess();
            }
        };
        workerThread.start();
        Thread shutdownHook = new Thread() {
            @Override
            public void run() {
                killProcess();
            }
        };
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    private void killProcess() {
        if (process != null) {
            process.destroy();
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void runProcess() {
        try {
            // 创建工作目录
            File executorDir = new File(workDir);
            if (!executorDir.mkdirs()) {
                throw new IOException("Failed to create directory" + executorDir);
            }
            new ProcessExecutor().command("java", "-cp","","com.baidu.deploy.ExecutorBackend").directory(executorDir)
                    .redirectOutput(Slf4jStream.ofCaller().asInfo()).execute();
        } catch (Exception e) {
        }
    }
}
