package com.baidu.testframework;

import org.junit.Test;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;

import java.io.File;
import java.io.IOException;

/**
 * Created by edwardsbean on 14-11-10.
 */
public class TestProcess {

    @Test
    public void testRun() throws IOException {
        String workDir = "/home/edwardsbean";
        File executorDir = new File(workDir);
        ProcessBuilder builder = new ProcessBuilder("java","-version").directory(executorDir);
        builder.start();
    }

    @Test
    public void testRunExec() throws Exception{
        new ProcessExecutor().command("java", "-version")
                .redirectOutput(Slf4jStream.ofCaller().asInfo()).execute();
    }

    @Test
    public void testSetExec() throws Exception {
        File exe = new File("/home/edwardsbean/workspace/web-config/config-web/target/config-web/executor/executor-1416891672518/conf/package.sh");
        exe.setExecutable(true);
    }
}
