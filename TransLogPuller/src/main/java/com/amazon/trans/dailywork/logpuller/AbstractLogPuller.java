package com.amazon.trans.dailywork.logpuller;

public abstract class AbstractLogPuller implements LogPuller {

    @Override
    public void pull(ProgramArgs args) {
        String envName = args.getEnvName();
        String realm = args.getRealm();
        String domain = args.getDomain();
        String logTime = args.getLogTime();
        String destDirPath = args.getDestDirPath();

        String[] hosts = Configuration.getInstance().getHosts(envName, realm, domain);
        String[] logs = Configuration.getInstance().getLogs(envName);
        for (String host : hosts) {
            for (String logPrefix : logs) {
                String logFullName = logPrefix + "." + logTime;
                pull(host, logFullName, destDirPath);
            }
        }
    }

    public abstract void pull(String hostName, String remoteFilePath, String localDestDirPath);

}
