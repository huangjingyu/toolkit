package com.amazon.trans.dailywork.logpuller;

public class DefaultLogPuller extends AbstractLogPuller {

    @Override
    public void pull(String hostName, String remoteFilePath, String localDestDirPath) {
        System.out.println("pull " + remoteFilePath + " from " + hostName + " to " + localDestDirPath);
    }

}
