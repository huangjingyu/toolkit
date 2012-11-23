package com.amazon.trans.dailywork.logpuller;

import java.io.File;
import java.util.regex.Pattern;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.apache.commons.lang.StringUtils;
import org.stringtemplate.v4.ST;

public class DefaultLogPuller extends AbstractLogPuller {

    @Override
    public void pull(String hostName, String remoteFilePath, String localDestDirPath) throws Exception {
        String action = "pull " + remoteFilePath + " from " + hostName + " to " + localDestDirPath;
        System.out.println(action);
        if (!StringUtils.endsWith(localDestDirPath, File.separator)) {
            localDestDirPath += File.separator;
        }
        boolean success = _pull(hostName, remoteFilePath, localDestDirPath);
        if (!success && !remoteFilePath.endsWith(".gz")) {
            remoteFilePath += ".gz";
            success = _pull(hostName, remoteFilePath, localDestDirPath);
        }
        if (success) {
            System.out.println("success to " + "pull " + remoteFilePath + " from " + hostName + " to "
                    + localDestDirPath);
        } else {
            System.err.println("fail to " + action);
        }
    }

    private static final Pattern FILE_SEP = Pattern.compile("[/\\\\]");

    private boolean _pull(String hostName, String remoteFilePath, String localDestDirPath) throws Exception {
        String cmdTemplate = "<scp> -o StrictHostKeyChecking=no <hostName>:<remoteFilePath> <localFilePath>";
        if (Util.isWindows()) {
            cmdTemplate = "cmd.exe /c echo y | <scp> <hostName>:<remoteFilePath> <localFilePath>";
        }
        String[] arr = FILE_SEP.split(remoteFilePath);
        String fileName = arr[arr.length - 1];
        ST st = new ST(cmdTemplate);
        String scpPath = "scp";
        if (Util.isWindows()) {
            scpPath = Configuration.getInstance().getWindowsScpPath();
        }
        st.add("scp", scpPath);
        st.add("hostName", hostName);
        st.add("remoteFilePath", remoteFilePath);
        st.add("localFilePath", localDestDirPath + hostName + "-" + fileName);

        String cmd = st.render();
        Executor executor = new DefaultExecutor();
        executor.setExitValues(new int[] { 0, 3 });
        CommandLine cmdLine = CommandLine.parse(cmd);
        try {
            int exitValue = executor.execute(cmdLine);
            return exitValue == 0 || exitValue == 3;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

}
