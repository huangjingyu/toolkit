package com.amazon.trans.dailywork.logpuller;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.apache.commons.lang.StringUtils;
import org.stringtemplate.v4.ST;

public class DefaultLogPuller extends AbstractLogPuller {

    @Override
    public void pull(String hostName, String remoteFilePath, String localDestDirPath) throws Exception {
        String action = "pull " + remoteFilePath + " from " + hostName + " to " + localDestDirPath;
        System.out.println();
        if (!StringUtils.endsWith(localDestDirPath, "/")) {
            localDestDirPath += "/";
        }
        String cmdTemplate = "<scp> -o StrictHostKeyChecking=no <hostName>:<remoteFilePath> <localDestDirPath>";
        ST st = new ST(cmdTemplate);
        st.add("scp", "scp");
        st.add("hostName", hostName);
        st.add("remoteFilePath", remoteFilePath);
        st.add("localDestDirPath", localDestDirPath);

        String cmd = st.render();
        Executor executor = new DefaultExecutor();
        CommandLine cmdLine = CommandLine.parse(cmd);
        int exitValue = executor.execute(cmdLine);
        if (exitValue == 1) {
            throw new Exception("fail to " + action);
        } else {
            System.out.println("success to " + action);
        }
    }

}
